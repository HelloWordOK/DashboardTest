package com.nokia.charts.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nokia.charts.constant.SystemConstant.QAClosedSLAType;
import com.nokia.charts.constant.SystemConstant.QASourceType;
import com.nokia.charts.constant.SystemConstant.QATicketPriorityType;
import com.nokia.charts.dao.IncidentImportDao;
import com.nokia.charts.dao.ProblemImportDao;
import com.nokia.charts.dao.SRImportDao;
import com.nokia.charts.dto.qa.IncidentResultDto;
import com.nokia.charts.dto.qa.ProblemResultDto;
import com.nokia.charts.dto.qa.QAResultDataDto;
import com.nokia.charts.dto.qa.SRResultDto;
import com.nokia.charts.dto.qa.data.ServiceDataSearchDto;
import com.nokia.charts.entity.admin.Domain;
import com.nokia.charts.entity.admin.ServiceEntity;
import com.nokia.charts.entity.qa.IncidentImport;
import com.nokia.charts.entity.qa.ProblemImport;
import com.nokia.charts.entity.qa.SRImport;
import com.nokia.charts.exception.MonitorException;
import com.nokia.charts.service.QAService;
import com.nokia.charts.util.DateUtils;
import com.nokia.charts.util.MathUtil;

@Service
public class QAServciceImpl implements QAService {

	@Autowired
	IncidentImportDao incidentImportDao;

	@Autowired
	ProblemImportDao problemImportDao;

	@Autowired
	SRImportDao sRImportDao;

	@Override
	public List<ServiceEntity> getQAServiceList() throws MonitorException {
		return incidentImportDao.getQAServiceList();
	}
	
	@Override
	public List<ServiceEntity> getServiceListToMajor() throws MonitorException {
		return incidentImportDao.getServiceListToMajor();
	}

	@Override
	public List<Domain> getQADomainList() throws MonitorException {
		return incidentImportDao.getQADomainList();
	}
	
	@Override
	public ServiceEntity getQAServiceByCode(String serviceCode) throws MonitorException {
		return incidentImportDao.getQAServiceByCode(serviceCode);
	}

	@Override
	public Domain getQADomainById(String domainId) throws MonitorException {
		return incidentImportDao.getQADomainById(domainId);
	}
	/**
	 * QA Service Incident 根据月查询
	 * @param monthList
	 * @param groupList
	 * @return
	 */
	@Override
	public List<QAResultDataDto> getIncidentMonthlyList(List<Object> monthList, List<String> groupList)
			throws MonitorException {
		List<QAResultDataDto> incidentList = new ArrayList<QAResultDataDto>();
		Map<String, Object> sourceMap = new LinkedHashMap<String, Object>();
		sourceMap.put("groupList", groupList);
		String startMonth = (String) monthList.get(0);
		String endMonth = (String) monthList.get(12);
		sourceMap.put("startMonth", startMonth);
		sourceMap.put("endMonth", endMonth);
		List<IncidentResultDto> allMonthCreatedList = incidentImportDao
				.getIncidentAllMonthOrWeekCreatedList(sourceMap);
		List<IncidentResultDto> allMonthClosedList = incidentImportDao
				.getIncidentAllMonthOrWeekClosedList(sourceMap);
		int first_month_backlog_opened = incidentImportDao.getOpendBacklogCount(sourceMap);
		int _first_month_backlog_closed = incidentImportDao.getClosedBacklogCount(sourceMap);
		int i = 0;
		int resolved = 0;
		int pending = 0;
		int workingInProgress = 0;
		int ownerAssinged = 0;
		QAResultDataDto ticketResultDto = new QAResultDataDto();
		for (Object monthOrWeek : monthList) {
			if (monthOrWeek.equals(monthList.get(12))) {
				continue;
			}
			QAResultDataDto resultDto = new QAResultDataDto();
			int created = 0;
			for (IncidentResultDto createdIncident : allMonthCreatedList) {
				if (createdIncident.getOpen_Time().contains((String) monthOrWeek)) {
					created++;
					if (createdIncident.getStatus().toLowerCase().contains("resolved")) {
						resolved++;
					} else if (createdIncident.getStatus().toLowerCase().contains("pending")) {
						pending++;
					} else if (createdIncident.getStatus().toLowerCase().contains("work in progress")) {
						workingInProgress++;
					} else if (createdIncident.getStatus().toLowerCase().contains("owner assigned")) {
						ownerAssinged++;
					}
				}
			}
			resultDto.setCreated(created);
			int closed = 0, closedP1 = 0, closedP2 = 0, closedP3 = 0, closedP4 = 0;
			int closedSLA = 0, closedSLAP1 = 0, closedSLAP2 = 0, closedSLAP3 = 0, closedSLAP4 = 0;
			for (IncidentResultDto closedIncident : allMonthClosedList) {
				if (closedIncident.getClose_Time().contains((String) monthOrWeek)) {
					closed++;
					closedSLA = generateIncidentSLARealBreach(closedSLA, closedIncident);
					
					if (closedIncident.getPriority().contains(QATicketPriorityType.P1)) {
						closedP1++;
						closedSLAP1 = generateIncidentSLARealBreach(closedSLAP1, closedIncident);
					} else if (closedIncident.getPriority().contains(QATicketPriorityType.P2)) {
						closedP2++;
						closedSLAP2 = generateIncidentSLARealBreach(closedSLAP2, closedIncident);
					} else if (closedIncident.getPriority().contains(QATicketPriorityType.P3)) {
						closedP3++;
						closedSLAP3 = generateIncidentSLARealBreach(closedSLAP3, closedIncident);
					} else if (closedIncident.getPriority().contains(QATicketPriorityType.P4)) {
						closedP4++;
						closedSLAP4 = generateIncidentSLARealBreach(closedSLAP4, closedIncident);
					}
				}
			}
			resultDto.setClosed(closed);
			int backlog = 0;
			if (monthOrWeek.equals(monthList.get(0))) {
				int first_month_backlog = first_month_backlog_opened - _first_month_backlog_closed;
				backlog = first_month_backlog + created - closed;
			} else {
				backlog = incidentList.get(i - 1).getBacklog() + created - closed;
			}
			if (backlog < 0) {
				backlog = 0;
			}
			resultDto.setBacklog(backlog);
			float sla = 100;
			if (closed != 0) {
				sla = MathUtil.formatFloat((Float.valueOf(closedSLA) / Float.valueOf(closed)) * 100);
			}
			resultDto.setSla(sla);

			float slaP1 = 0;
			if (closedP1 != 0) {
				slaP1 = MathUtil.formatFloat((Float.valueOf(closedSLAP1) / Float.valueOf(closedP1)) * 100);
			}
			resultDto.setSlaP1(slaP1);

			float slaP2 = 0;
			if (closedP2 != 0) {
				slaP2 = MathUtil.formatFloat((Float.valueOf(closedSLAP2) / Float.valueOf(closedP2)) * 100);
			}
			resultDto.setSlaP2(slaP2);
			float slaP3 = 0;
			if (closedP3 != 0) {
				slaP3 = MathUtil.formatFloat((Float.valueOf(closedSLAP3) / Float.valueOf(closedP3)) * 100);
			}
			resultDto.setSlaP3(slaP3);
			float slaP4 = 0;
			if (closedP4 != 0) {
				slaP4 = MathUtil.formatFloat((Float.valueOf(closedSLAP4) / Float.valueOf(closedP4)) * 100);
			}
			resultDto.setSlaP4(slaP4);
			incidentList.add(resultDto);
			i++;
		}
		ticketResultDto.setResolved(resolved);
		ticketResultDto.setPending(pending);
		ticketResultDto.setWorkInProgress(workingInProgress);
		ticketResultDto.setOwnerAssinged(ownerAssinged);
		incidentList.add(ticketResultDto);
		return incidentList;
	}

	private int generateIncidentSLARealBreach(int closedSLA, IncidentResultDto closedIncident) {
		if(closedIncident.getReal_breach()!=null&&!closedIncident.getReal_breach().isEmpty()){
			if(closedIncident.getReal_breach().equalsIgnoreCase(QAClosedSLAType.BREACH)){
				closedSLA++;
			}
		}else{
			if (closedIncident.getMs_Ttr_Breach().equalsIgnoreCase(QAClosedSLAType.BREACH)) {
				closedSLA++;
			}
		}
		return closedSLA;
	}
	
	private int generateSRSLARealBreach(int closedSLA, SRResultDto closedSR) {
		if(closedSR.getReal_breach()!=null&&!closedSR.getReal_breach().isEmpty()){
			if(closedSR.getReal_breach().equalsIgnoreCase(QAClosedSLAType.BREACH)){
				closedSLA++;
			}
		}else{
			if (closedSR.getSla_Breach().equalsIgnoreCase(QAClosedSLAType.BREACH)) {
				closedSLA++;
			}
		}
		return closedSLA;
	}
	
	private int generateProblemSLARealBreach(int closedSLA, ProblemResultDto closedProblem) {
		if(closedProblem.getReal_breach()!=null&&!closedProblem.getReal_breach().isEmpty()){
			if(closedProblem.getReal_breach().equalsIgnoreCase(QAClosedSLAType.BREACH)){
				closedSLA++;
			}
		}else{
			if (closedProblem.getSla_Breach().equalsIgnoreCase(QAClosedSLAType.BREACH)) {
				closedSLA++;
			}
		}
		return closedSLA;
	}

	/**
	 * QA Service Incident 根据周查询
	 * @param weekMap
	 * @param groupList
	 * @return
	 */
	@Override
	public List<QAResultDataDto> getIncidentWeeklyList(Map<Object, List<String>> weekMap, List<String> groupList)
			throws MonitorException {
		List<QAResultDataDto> incidentList = new ArrayList<QAResultDataDto>();
		Map<String, Object> sourceMap = new LinkedHashMap<String, Object>();
		sourceMap.put("groupList", groupList);
		Object[] weeks = weekMap.keySet().toArray();
		String startWeekDay = weekMap.get(weeks[0]).get(0);
		String endWeekDay = weekMap.get(weeks[11]).get(6);
		sourceMap.put("startWeekDay", startWeekDay);
		sourceMap.put("endWeekDay", endWeekDay);
		List<IncidentResultDto> allWeekCreatedList = incidentImportDao
				.getIncidentAllMonthOrWeekCreatedList(sourceMap);
		List<IncidentResultDto> allWeekClosedList = incidentImportDao.getIncidentAllMonthOrWeekClosedList(sourceMap);
		int first_week_backlog_opened = incidentImportDao.getOpendBacklogCount(sourceMap);
		int first_week_backlog_closed = incidentImportDao.getClosedBacklogCount(sourceMap);
		int i = 0;
		int resolved = 0;
		int pending = 0;
		int workingInProgress = 0;
		int ownerAssinged = 0;
		QAResultDataDto titcketResultDto = new QAResultDataDto();
		for (Object week : weekMap.keySet()) {
			QAResultDataDto resultDto = new QAResultDataDto();
			int created = 0;
			for (String weekDay : weekMap.get(week)) {
				for (IncidentResultDto incidentCreated : allWeekCreatedList) {
					if (incidentCreated.getOpen_Time().contains(weekDay)) {
						created++;
						if (incidentCreated.getStatus().toLowerCase().contains("resolved")) {
							resolved++;
						} else if (incidentCreated.getStatus().toLowerCase().contains("pending")) {
							pending++;
						} else if (incidentCreated.getStatus().toLowerCase().contains("work in progress")) {
							workingInProgress++;
						} else if (incidentCreated.getStatus().toLowerCase().contains("owner assigned")) {
							ownerAssinged++;
						}
					}
				}
			}
			resultDto.setCreated(created);
			int closed = 0, closedP1 = 0, closedP2 = 0, closedP3 = 0, closedP4 = 0;
			int closedSLA = 0, closedSLAP1 = 0, closedSLAP2 = 0, closedSLAP3 = 0, closedSLAP4 = 0;
			for (String weekDay : weekMap.get(week)) {
				for (IncidentResultDto closedIncident : allWeekClosedList) {
					if (closedIncident.getClose_Time().contains(weekDay)) {
						closed++;
						closedSLA = generateIncidentSLARealBreach(closedSLA, closedIncident);
						if (closedIncident.getPriority().contains(QATicketPriorityType.P1)) {
							closedP1++;
							closedSLAP1 = generateIncidentSLARealBreach(closedSLAP1, closedIncident);
						} else if (closedIncident.getPriority().contains(QATicketPriorityType.P2)) {
							closedP2++;
							closedSLAP2 = generateIncidentSLARealBreach(closedSLAP2, closedIncident);
						} else if (closedIncident.getPriority().contains(QATicketPriorityType.P3)) {
							closedP3++;
							closedSLAP3 = generateIncidentSLARealBreach(closedSLAP3, closedIncident);
						} else if (closedIncident.getPriority().contains(QATicketPriorityType.P4)) {
							closedP4++;
							closedSLAP4 = generateIncidentSLARealBreach(closedSLAP4, closedIncident);
						}
					}
				}
			}
			resultDto.setClosed(closed);
			int backlog = 0;
			if (weekMap.keySet().toArray()[0].equals(week)) {
				int first_month_backlog = first_week_backlog_opened - first_week_backlog_closed;
				backlog = first_month_backlog + created - closed;
			} else {
				backlog = incidentList.get(i - 1).getBacklog() + created - closed;
			}
			if (backlog < 0) {
				backlog = 0;
			}
			resultDto.setBacklog(backlog);
			float sla = 100;
			if (closed != 0) {
				sla = MathUtil.formatFloat((Float.valueOf(closedSLA) / Float.valueOf(closed)) * 100);
			}
			resultDto.setSla(sla);

			float slaP1 = 0;
			if (closedP1 != 0) {
				slaP1 = MathUtil.formatFloat((Float.valueOf(closedSLAP1) / Float.valueOf(closedP1)) * 100);
			}
			resultDto.setSlaP1(slaP1);

			float slaP2 = 0;
			if (closedP2 != 0) {
				slaP2 = MathUtil.formatFloat((Float.valueOf(closedSLAP2) / Float.valueOf(closedP2)) * 100);
			}
			resultDto.setSlaP2(slaP2);
			float slaP3 = 0;
			if (closedP3 != 0) {
				slaP3 = MathUtil.formatFloat((Float.valueOf(closedSLAP3) / Float.valueOf(closedP3)) * 100);
			}
			resultDto.setSlaP3(slaP3);
			float slaP4 = 0;
			if (closedP4 != 0) {
				slaP4 = MathUtil.formatFloat((Float.valueOf(closedSLAP4) / Float.valueOf(closedP4)) * 100);
			}
			resultDto.setSlaP4(slaP4);
			incidentList.add(resultDto);
			i++;
		}
		titcketResultDto.setResolved(resolved);
		titcketResultDto.setPending(pending);
		titcketResultDto.setWorkInProgress(workingInProgress);
		titcketResultDto.setOwnerAssinged(ownerAssinged);
		incidentList.add(titcketResultDto);
		return incidentList;
	}

	
	/**
	 * QA Service SR 根据月查询
	 * @param monthList
	 * @param groupList
	 * @return
	 */
	@Override
	public List<QAResultDataDto> getSRMonthlyList(List<Object> monthList, List<String> groupList)
			throws MonitorException {
		List<QAResultDataDto> sRList = new ArrayList<QAResultDataDto>();
		Map<String, Object> sourceMap = new LinkedHashMap<String, Object>();
		sourceMap.put("groupList", groupList);
		String startMonth = (String) monthList.get(0);
		String endMonth = (String) monthList.get(12);
		sourceMap.put("startMonth", startMonth);
		sourceMap.put("endMonth", endMonth);
		List<SRResultDto> allMonthCreatedList = sRImportDao.getSRAllMonthOrWeekCreatedList(sourceMap);
		List<SRResultDto> allMonthClosedList = sRImportDao.getSRAllMonthOrWeekClosedList(sourceMap);
		int first_month_backlog_opened = sRImportDao.getOpendBacklogCount(sourceMap);
		int first_month_backlog_closed = sRImportDao.getClosedBacklogCount(sourceMap);
		int i = 0;
		for (Object monthOrWeek : monthList) {
			if (monthOrWeek.equals(monthList.get(12))) {
				continue;
			}
			QAResultDataDto resultDto = new QAResultDataDto();
			int created = 0;
			for (SRResultDto createdSR : allMonthCreatedList) {
				if (createdSR.getSubmit_Date().contains((String) monthOrWeek)) {
					created++;
				}
			}
			resultDto.setCreated(created);
			int closed = 0;
			int closedSLA = 0;
			for (SRResultDto closedSR : allMonthClosedList) {
				if (closedSR.getClose_Date().contains((String) monthOrWeek)) {
					closed++;
					closedSLA = generateSRSLARealBreach(closedSLA, closedSR);
				}
			}
			resultDto.setClosed(closed);
			int backlog = 0;
			if (monthOrWeek.equals(monthList.get(0))) {
				int first_month_backlog = first_month_backlog_opened - first_month_backlog_closed;
				backlog = first_month_backlog + created - closed;
			} else {
				backlog = sRList.get(i - 1).getBacklog() + created - closed;
			}

			if (backlog < 0) {
				backlog = 0;
			}
			resultDto.setBacklog(backlog);
			float sla = 100;
			if (closed != 0) {
				sla = MathUtil.formatFloat((Float.valueOf(closedSLA) / Float.valueOf(closed)) * 100);
			}
			resultDto.setSla(sla);
			sRList.add(resultDto);
			i++;
		}
		return sRList;
	}

	
	/**
	 * QA Service SR 根据周查询
	 * @param weekMap
	 * @param groupList
	 * @return
	 */
	@Override
	public List<QAResultDataDto> getSRWeeklyList(Map<Object, List<String>> weekMap, List<String> groupList)
			throws MonitorException {
		List<QAResultDataDto> sRList = new ArrayList<QAResultDataDto>();
		Map<String, Object> sourceMap = new LinkedHashMap<String, Object>();
		sourceMap.put("groupList", groupList);
		Object[] weeks = weekMap.keySet().toArray();
		String startWeekDay = weekMap.get(weeks[0]).get(0);
		String endWeekDay = weekMap.get(weeks[11]).get(6);
		sourceMap.put("startWeekDay", startWeekDay);
		sourceMap.put("endWeekDay", endWeekDay);
		List<SRResultDto> allWeekCreatedList = sRImportDao.getSRAllMonthOrWeekCreatedList(sourceMap);
		List<SRResultDto> allWeekClosedList = sRImportDao.getSRAllMonthOrWeekClosedList(sourceMap);
		int first_week_backlog_opened = sRImportDao.getOpendBacklogCount(sourceMap);
		int first_week_backlog_closed = sRImportDao.getClosedBacklogCount(sourceMap);
		int i = 0;
		for (Object week : weekMap.keySet()) {
			QAResultDataDto resultDto = new QAResultDataDto();
			int created = 0;
			for (String weekDay : weekMap.get(week)) {
				for (SRResultDto sRCreated : allWeekCreatedList) {
					if (sRCreated.getSubmit_Date().contains(weekDay)) {
						created++;
					}
				}
			}
			resultDto.setCreated(created);
			int closed = 0;
			int closedSLA = 0;
			for (String weekDay : weekMap.get(week)) {
				for (SRResultDto closedSR : allWeekClosedList) {
					if (closedSR.getClose_Date().contains(weekDay)) {
						closed++;
						closedSLA = generateSRSLARealBreach(closedSLA, closedSR);
					}
				}
			}
			resultDto.setClosed(closed);
			int backlog = 0;
			if (weekMap.keySet().toArray()[0].equals(week)) {
				int first_month_backlog = first_week_backlog_opened - first_week_backlog_closed;
				backlog = first_month_backlog + created - closed;
			} else {
				backlog = sRList.get(i - 1).getBacklog() + created - closed;
			}
			if (backlog < 0) {
				backlog = 0;
			}
			resultDto.setBacklog(backlog);
			float sla = 100;
			if (closed != 0) {
				sla = MathUtil.formatFloat((Float.valueOf(closedSLA) / Float.valueOf(closed)) * 100);
			}
			resultDto.setSla(sla);

			sRList.add(resultDto);
			i++;
		}
		return sRList;
	}

	
	/**
	 * QA Service Problem 根据月查询
	 * @param monthList
	 * @param groupList
	 * @return
	 */
	@Override
	public List<QAResultDataDto> getProblemMonthlyList(List<Object> monthList, List<String> groupList)
			throws MonitorException {
		List<QAResultDataDto> problemList = new ArrayList<QAResultDataDto>();
		Map<String, Object> sourceMap = new LinkedHashMap<String, Object>();
		sourceMap.put("groupList", groupList);
		String startMonth = (String) monthList.get(0);
		String endMonth = (String) monthList.get(12);
		sourceMap.put("startMonth", startMonth);
		sourceMap.put("endMonth", endMonth);
		List<ProblemResultDto> allMonthCreatedList = problemImportDao.getProblemAllMonthOrWeekCreatedList(sourceMap);
		List<ProblemResultDto> allMonthClosedList = problemImportDao.getProblemAllMonthOrWeekClosedList(sourceMap);
		int first_month_backlog_opened = problemImportDao.getOpendBacklogCount(sourceMap);
		int first_month_backlog_closed = problemImportDao.getClosedBacklogCount(sourceMap);
		int i = 0;
		for (Object monthOrWeek : monthList) {
			if (monthOrWeek.equals(monthList.get(12))) {
				continue;
			}
			QAResultDataDto resultDto = new QAResultDataDto();
			int created = 0;
			for (ProblemResultDto problemCreated : allMonthCreatedList) {
				if (problemCreated.getOpen_Time().contains((String) monthOrWeek)) {
					created++;
				}
			}
			resultDto.setCreated(created);
			int closed = 0, closedP1 = 0, closedP2 = 0, closedP3 = 0, closedP4 = 0;
			int closedSLA = 0, closedSLAP1 = 0, closedSLAP2 = 0, closedSLAP3 = 0, closedSLAP4 = 0;
			for (ProblemResultDto problemClosed : allMonthClosedList) {
				if (problemClosed.getClose_Time().contains((String) monthOrWeek)) {
					closed++;
					closedSLA = generateProblemSLARealBreach(closedSLA, problemClosed);
					if (problemClosed.getPriority_Code().contains(QATicketPriorityType.P1)) {
						closedP1++;
						closedSLAP1 = generateProblemSLARealBreach(closedSLAP1, problemClosed);
					} else if (problemClosed.getPriority_Code().contains(QATicketPriorityType.P2)) {
						closedP2++;
						closedSLAP2 = generateProblemSLARealBreach(closedSLAP2, problemClosed);
					} else if (problemClosed.getPriority_Code().contains(QATicketPriorityType.P3)) {
						closedP3++;
						closedSLAP3 = generateProblemSLARealBreach(closedSLAP3, problemClosed);
					} else if (problemClosed.getPriority_Code().contains(QATicketPriorityType.P4)) {
						closedP4++;
						closedSLAP4 = generateProblemSLARealBreach(closedSLAP4, problemClosed);
					}
				}
			}
			resultDto.setClosed(closed);
			int backlog = 0;
			if (monthOrWeek.equals(monthList.get(0))) {
				int first_month_backlog = first_month_backlog_opened - first_month_backlog_closed;
				backlog = first_month_backlog + created - closed;
			} else {
				backlog = problemList.get(i - 1).getBacklog() + created - closed;
			}

			if (backlog < 0) {
				backlog = 0;
			}
			resultDto.setBacklog(backlog);
			float sla = 100;
			if (closed != 0) {
				sla = MathUtil.formatFloat((Float.valueOf(closedSLA) / Float.valueOf(closed)) * 100);
			}
			resultDto.setSla(sla);

			float slaP1 = 0;
			if (closedP1 != 0) {
				slaP1 = MathUtil.formatFloat((Float.valueOf(closedSLAP1) / Float.valueOf(closedP1)) * 100);
			}
			resultDto.setSlaP1(slaP1);

			float slaP2 = 0;
			if (closedP2 != 0) {
				slaP2 = MathUtil.formatFloat((Float.valueOf(closedSLAP2) / Float.valueOf(closedP2)) * 100);
			}
			resultDto.setSlaP2(slaP2);
			float slaP3 = 0;
			if (closedP3 != 0) {
				slaP3 = MathUtil.formatFloat((Float.valueOf(closedSLAP3) / Float.valueOf(closedP3)) * 100);
			}
			resultDto.setSlaP3(slaP3);
			float slaP4 = 0;
			if (closedP4 != 0) {
				slaP4 = MathUtil.formatFloat((Float.valueOf(closedSLAP4) / Float.valueOf(closedP4)) * 100);
			}
			resultDto.setSlaP4(slaP4);
			problemList.add(resultDto);
			i++;
		}
		return problemList;
	}

	
	/**
	 * QA Service Problem 根据周查询
	 * @param weekMap
	 * @param groupList
	 * @return
	 */
	@Override
	public List<QAResultDataDto> getProblemWeeklyList(Map<Object, List<String>> weekMap, List<String> groupList)
			throws MonitorException {
		List<QAResultDataDto> problemList = new ArrayList<QAResultDataDto>();
		Map<String, Object> sourceMap = new LinkedHashMap<String, Object>();
		sourceMap.put("groupList", groupList);
		Object[] weeks = weekMap.keySet().toArray();
		String startWeekDay = weekMap.get(weeks[0]).get(0);
		String endWeekDay = weekMap.get(weeks[11]).get(6);
		sourceMap.put("startWeekDay", startWeekDay);
		sourceMap.put("endWeekDay", endWeekDay);
		List<ProblemResultDto> allWeekCreatedList = problemImportDao.getProblemAllMonthOrWeekCreatedList(sourceMap);
		List<ProblemResultDto> allWeekClosedList = problemImportDao.getProblemAllMonthOrWeekClosedList(sourceMap);
		int first_week_backlog_opened = problemImportDao.getOpendBacklogCount(sourceMap);
		int first_week_backlog_closed = problemImportDao.getClosedBacklogCount(sourceMap);
		int i = 0;
		for (Object week : weekMap.keySet()) {
			QAResultDataDto resultDto = new QAResultDataDto();
			int created = 0;
			for (String weekDay : weekMap.get(week)) {
				for (ProblemResultDto problemCreated : allWeekCreatedList) {
					if (problemCreated.getOpen_Time().contains(weekDay)) {
						created++;
					}
				}
			}
			resultDto.setCreated(created);
			int closed = 0, closedP1 = 0, closedP2 = 0, closedP3 = 0, closedP4 = 0;
			int closedSLA = 0, closedSLAP1 = 0, closedSLAP2 = 0, closedSLAP3 = 0, closedSLAP4 = 0;
			for (String weekDay : weekMap.get(week)) {
				for (ProblemResultDto problemClosed : allWeekClosedList) {
					if (problemClosed.getClose_Time().contains(weekDay)) {
						closed++;
						closedSLA = generateProblemSLARealBreach(closedSLA, problemClosed);
						if (problemClosed.getPriority_Code().contains(QATicketPriorityType.P1)) {
							closedP1++;
							closedSLAP1 = generateProblemSLARealBreach(closedSLAP1, problemClosed);
						} else if (problemClosed.getPriority_Code().contains(QATicketPriorityType.P2)) {
							closedP2++;
							closedSLAP2 = generateProblemSLARealBreach(closedSLAP2, problemClosed);
						} else if (problemClosed.getPriority_Code().contains(QATicketPriorityType.P3)) {
							closedP3++;
							closedSLAP3 = generateProblemSLARealBreach(closedSLAP3, problemClosed);
						} else if (problemClosed.getPriority_Code().contains(QATicketPriorityType.P4)) {
							closedP4++;
							closedSLAP4 = generateProblemSLARealBreach(closedSLAP4, problemClosed);
						}
					}
				}
			}
			resultDto.setClosed(closed);
			int backlog = 0;
			if (weekMap.keySet().toArray()[0].equals(week)) {
				int first_month_backlog = first_week_backlog_opened - first_week_backlog_closed;
				backlog = first_month_backlog + created - closed;
			} else {
				backlog = problemList.get(i - 1).getBacklog() + created - closed;
			}
			if (backlog < 0) {
				backlog = 0;
			}
			resultDto.setBacklog(backlog);
			float sla = 100;
			if (closed != 0) {
				sla = MathUtil.formatFloat((Float.valueOf(closedSLA) / Float.valueOf(closed)) * 100);
			}
			resultDto.setSla(sla);

			float slaP1 = 0;
			if (closedP1 != 0) {
				slaP1 = MathUtil.formatFloat((Float.valueOf(closedSLAP1) / Float.valueOf(closedP1)) * 100);
			}
			resultDto.setSlaP1(slaP1);

			float slaP2 = 0;
			if (closedP2 != 0) {
				slaP2 = MathUtil.formatFloat((Float.valueOf(closedSLAP2) / Float.valueOf(closedP2)) * 100);
			}
			resultDto.setSlaP2(slaP2);
			float slaP3 = 0;
			if (closedP3 != 0) {
				slaP3 = MathUtil.formatFloat((Float.valueOf(closedSLAP3) / Float.valueOf(closedP3)) * 100);
			}
			resultDto.setSlaP3(slaP3);
			float slaP4 = 0;
			if (closedP4 != 0) {
				slaP4 = MathUtil.formatFloat((Float.valueOf(closedSLAP4) / Float.valueOf(closedP4)) * 100);
			}
			resultDto.setSlaP4(slaP4);
			problemList.add(resultDto);
			i++;
		}
		return problemList;
	}

	@Override
	public List<String> getQAGroupNameByServiceCode(String serviceCode) throws MonitorException {
		return incidentImportDao.getQAGroupNameByServiceCode(serviceCode);
	}

	@Override
	public List<String> getQAGroupNameByDomain(String domainId) throws MonitorException {
		return incidentImportDao.getQAGroupNameByDomain(domainId);
	}
	
	@Override
	public void insertProblemList(List<ProblemImport> problemImportList) throws MonitorException {
		problemImportDao.insertProblemList(problemImportList);
	}

	@Override
	public void insertIncidentList(List<IncidentImport> incidentImportList) throws MonitorException {
		incidentImportDao.insertIncidentList(incidentImportList);
	}

	@Override
	public void insertSRList(List<SRImport> sRImportList) throws MonitorException {
		sRImportDao.insertSRList(sRImportList);
	}

	/**
	 * @param monthList
	 * @param groupList
	 * @param chartType
	 * @author xigao
	 * @return Result list of current chartType
	 */
	@Override
	public List<Map<String, Object>> getResultList(List<Object> monthList, List<String> groupList, String chartType)
			throws MonitorException {
		List<Map<String, Object>> resultList = new ArrayList<>();
		Map<String, Object> sourceMap = new LinkedHashMap<String, Object>();
		parameterSet(monthList, groupList, sourceMap);
		if (QASourceType.INCIDENT.equalsIgnoreCase(chartType)) {
			resultList = incidentImportDao.getAllIncidentMonthlyList(sourceMap);
		} else if (QASourceType.SR.equalsIgnoreCase(chartType)) {
			resultList = sRImportDao.getAllSRMonthlyList(sourceMap);
		} else if (QASourceType.PROBLEM.equalsIgnoreCase(chartType)) {
			resultList = problemImportDao.getAllProblemMonthlyList(sourceMap);
		}
		return resultList;
	}

	private void parameterSet(List<Object> monthList, List<String> groupList, Map<String, Object> sourceMap) {
		sourceMap.put("groupList", groupList);
		String startMonth = (String) monthList.get(0);
		String endMonth = (String) monthList.get(1);
		sourceMap.put("startMonth", startMonth);
		sourceMap.put("endMonth", endMonth);
	}
	
	/**
	 * QA Service 根据条件查询closed状态
	 * @param searchDto
	 * @return
	 */
	@Override
	public List<Map<String, Object>> getServiceDataClosedList(ServiceDataSearchDto searchDto) throws MonitorException {
		Map<String,Object> sourceMap = new LinkedHashMap<String, Object>();
		String serviceCode = searchDto.getServiceCode();
		List<String> groupList = this.getQAGroupNameByServiceCode(serviceCode);
		sourceMap.put("groupList", groupList);
		sourceMap.put("startMonth", searchDto.getStartMonth());
		String endMonth = searchDto.getEndMonth();
		Date dateChange = DateUtils.dateChange(DateUtils.String2Date(endMonth, DateUtils.DATE_FROMAT_YYYY_MM_dd), Calendar.DAY_OF_MONTH, +1);
		sourceMap.put("endMonth", DateUtils.date2String(dateChange, DateUtils.DATE_FROMAT_YYYY_MM_dd));
		List<String> slaBreachList = new ArrayList<String>();
		Collections.addAll(slaBreachList, searchDto.getSlaBreach());
		if(!slaBreachList.contains(QAClosedSLAType.BREACH)){
			sourceMap.put("real_breach", "true");
		}
		sourceMap.put("slaBreachList", slaBreachList);
		String dataType= searchDto.getDataType();
		List<Map<String, Object>> serviceDataClosedList = new ArrayList<Map<String,Object>>();
		if(dataType.equalsIgnoreCase(QASourceType.INCIDENT)){
			serviceDataClosedList = incidentImportDao.getIncidentServiceClosedList(sourceMap);
		}else if(dataType.equalsIgnoreCase(QASourceType.SR)){
			serviceDataClosedList = sRImportDao.getSRServiceClosedList(sourceMap);
		}else if(dataType.equalsIgnoreCase(QASourceType.PROBLEM)){
			serviceDataClosedList = problemImportDao.getProblemServiceClosedList(sourceMap);
		}
		return serviceDataClosedList;
	}
	
	/**
	 * QA Service 根据条件更新closed状态
	 * @param searchDto
	 * @return
	 */
	@Override
	public void updateServiceRealBreach(ServiceDataSearchDto searchDto) throws MonitorException {
		String dataType= searchDto.getDataType();
		Map<String, Object> sourceMap = new LinkedHashMap<String, Object>();
		sourceMap.put("id", searchDto.getId());
		sourceMap.put("real_breach", searchDto.getReal_breach());
		sourceMap.put("comment", searchDto.getComment());
		if(dataType.equalsIgnoreCase(QASourceType.INCIDENT)){
			incidentImportDao.updateIncidentRealBreach(sourceMap);
		}else if(dataType.equalsIgnoreCase(QASourceType.SR)){
			sRImportDao.updateSRRealBreach(sourceMap);
		}else if(dataType.equalsIgnoreCase(QASourceType.PROBLEM)){
			problemImportDao.updateProblemRealBreach(sourceMap);
		}
	}
}
