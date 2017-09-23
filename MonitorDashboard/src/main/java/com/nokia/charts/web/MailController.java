package com.nokia.charts.web;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nokia.charts.dto.MailTemplateDto;
import com.nokia.charts.dto.SelectionOptionDto;
import com.nokia.charts.dto.shinepoint.MajorIncidentMailDto;
import com.nokia.charts.exception.MonitorException;
import com.nokia.charts.service.MailTemplateService;
import com.nokia.charts.util.DateUtils;
import com.nokia.charts.util.MailUtils;
import com.nokia.charts.web.base.EchartController;

@Controller
@RequestMapping("/mail")
public class MailController extends EchartController {

	@Autowired
	private MailTemplateService mailTemplateService;

	/**
	 * Send Mail page init
	 * @param model
	 * @return
	 */
	@RequestMapping("")
	public String sendMail(Model model) {

		List<SelectionOptionDto> serviceList = new ArrayList<SelectionOptionDto>();
		try {
			serviceList = mailTemplateService.getServiceList();
		} catch (MonitorException e) {
			logger.error(e);
		}
		model.addAttribute("serviceList", serviceList);
		//0:normal send way
		model.addAttribute("mtype","0");
		return "/mail/mail_send";
	}

	/**
	 * Save template page init
	 * @param model
	 * @return
	 */
	@RequestMapping("/template")
	public String saveTemp(Model model) {

		List<SelectionOptionDto> serviceList = new ArrayList<SelectionOptionDto>();
		try {
			serviceList = mailTemplateService.getServiceList();
		} catch (MonitorException e) {
			logger.error(e);
		}
		model.addAttribute("serviceList", serviceList);
		return "/mail/mail_template";
	}

	/** 
	 * Sending mails function by input data
	 * 
	 * @param to targetEmail address(Required)
	 * @param cc carbon copy mail address
	 * @param subject the subject of mail(Required)
	 * @param content the mail body details 
	 * @return return the status of function execution:<p>
	 * "0"  <b>Mail send successful</b></br>
	 * "-1" <b>Mail format error</b></br>
	 * "-2" <b>AddressException error </b></br>
	 * "-3" <b>MessagingException </b></br>
	 */
	@RequestMapping("/sendMail")
	@ResponseBody
	public String sendMail(HttpServletRequest request) {

		String to = request.getParameter("to");
		String cc = request.getParameter("cc");
		String subject = request.getParameter("subject");
		String content = request.getParameter("content");
//		String timer = request.getParameter("timer");
		String ip = "";
		String status = "0";

		// Check mail format
		if (null != cc && !cc.isEmpty()) {
			if (isMail(to) && isMail(cc)) {

				// Normal mails send
				status = MailUtils.sendMail(to, cc, subject, content);

				// Record send history
				ip = getIpAddr(request);
				recordSendMailHistory(to, cc, subject,ip);

				// Mail send successful...
				return status;
			} else {
				// Mail format error
				status = "-1";
				return status;
			}
		} else {
			if (isMail(to)) {

				// Normal mails send
				status = MailUtils.sendMail(to, cc, subject, content);

				// Record send history
				ip = getIpAddr(request);
				recordSendMailHistory(to, cc, subject,ip);

				// Mail send successful...
				return status;
			} else {
				// Mail format error
				status = "-1";
				return status;
			}
		}
	}

	/**
	 * Check Mail Address Format
	 * @param mail
	 * @return True Format Correct </br>
	 * False Format Incorrect
	 */
	public Boolean isMail(String mail) {
		if (null == mail) {
			return false;
		}

		String[] mailList = new String[] {};
		// split the target mail list
		mailList = mail.split(",");

		for (int i = 0; i < mailList.length; i++) {
			String mail_single = mailList[i];
			// Remove whitespace in the mail address
			mail_single = mail_single.trim();
			// Check the mail address format
			if (!MailUtils.checkEmail(mail_single)) {
				logger.error("The value of " + mail_single + "is incorrect.");
				return false;
			}
		}
		return true;
	}

	/**
	 * Save template data from web
	 * 
	 * @param serviceId
	 * @param temp
	 * @param to
	 * @param cc
	 * @param subject
	 * @param content
	 * @return
	 */
	@RequestMapping("/saveTemp")
	@ResponseBody
	public String saveTemplate(String serviceId, String temp, String to, String cc, String subject, String content) {
		boolean chkFlg = false;

		// If parameter "to" is not empty, check the mail address format
		if (null != to && !to.isEmpty()) {
			chkFlg = isMail(to);
		}

		if (null != cc && !cc.isEmpty()) {
			chkFlg = isMail(cc);
		}

		if (chkFlg) {
			// Save data to DB
			Map<String, Object> sourceMap = new LinkedHashMap<String, Object>();
			sourceMap.put("tempName", temp);
			sourceMap.put("serviceId", serviceId);
			sourceMap.put("mailTo", to);
			sourceMap.put("mailCc", cc);
			sourceMap.put("mailSubject", subject);
			sourceMap.put("mailContent", content);
			try {
				mailTemplateService.insertTemplate(sourceMap);
				return "1";
			} catch (MonitorException e) {
				e.printStackTrace();
				logger.error(e);
				return "-1";
			}
		}
		return "0";
	}

	/**
	 * Search all templates by solutionId<br/>
	 * 
	 * @param model
	 * @param solutionId
	 * @return
	 */
	@RequestMapping("/searchTemplate")
	public String searchTemplate(Model model, String serviceId) {
		
		List<MailTemplateDto> tempList = new ArrayList<MailTemplateDto>();
		try {
			tempList = mailTemplateService.selecTemplateByServiceId(serviceId);
		} catch (MonitorException e) {
			logger.error(e);
		}
		model.addAttribute("templateList", tempList);
		return "/mail/search_template_result";
	}

	/**
	 * Search template contents by templateId
	 * 
	 * @param request
	 * @param tempId
	 * @return
	 * @throws MonitorException 
	 */
	@RequestMapping("/searchTemplateById")
	public String searchTemplateById(Model model, String tempId,HttpSession session) throws MonitorException {

		MailTemplateDto templateContent = new MailTemplateDto();
		try {
			templateContent = mailTemplateService.selecTemplateByTemplateId(tempId);
		} catch (MonitorException e) {
			logger.error(e);
		}
			
		//from IncidentList page——send mail to change the mail template dynamically
		if(session.getAttribute("majorIncidentMailList")!=null) {
			MajorIncidentMailDto mimList = (MajorIncidentMailDto) session.getAttribute("majorIncidentMailList");
			String serviceName = mailTemplateService.selectServieNameByServiceId(mimList.getServiceId());
			String content = templateContent.getMailContent();
			content = content.replace("${serviceName}", serviceName);
			content = content.replace("${startDateTime}", mimList.getStartDateTime());
			content = content.replace("${endDateTime}", mimList.getEndDateTime());
			content = content.replace("${responsible}", mimList.getResponsible());
			content = content.replace("${srNumber}", mimList.getSrNumber());
			content = content.replace("${severity}", mimList.getSeverity());
			content = content.replace("${issueDescription}", mimList.getIssueDescription());
			content = content.replace("${problemId}", mimList.getProblemId());
			content = content.replace("${rootCause}", mimList.getRootCause());
			content = content.replace("${status}", mimList.getStatus());
			content = content.replace("${sourceFrom}", mimList.getSourceFrom());
			content = content.replace("${resolutionTime}", mimList.getResolutionTime());
			
			templateContent.setMailContent(content);
			model.addAttribute("templateContent", templateContent);
			session.removeAttribute("majorIncidentMailList");
			
			return "/mail/autofill_template_result";
		}
		
		
		model.addAttribute("templateContent", templateContent);
		return "/mail/autofill_template_result";
	}

	/**
	 * Recording mail sending history
	 * @param to
	 * @param cc
	 * @param subject
	 * @param ip
	 */
	private void recordSendMailHistory(String to, String cc, String subject, String ip) {
		// Get current time
		String sendTime = DateUtils.getCurDateTime(DateUtils.DATE_FROMAT_CN);

		Map<String, Object> sourceMap = new LinkedHashMap<String, Object>();
		sourceMap.put("mailTo", to);
		sourceMap.put("mailCc", cc);
		sourceMap.put("mailSubject", subject);
		sourceMap.put("ipAddress", ip);
		sourceMap.put("sendTime", sendTime);
		try {
			mailTemplateService.insertSendMailHistory(sourceMap);
		} catch (MonitorException e) {
			logger.error(e);
		}
	}
	
	/**
	 * Get client IP address
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ip = "";
		if (System.getProperty("os.name").toLowerCase().indexOf("windows") > -1) {
			try {
				InetAddress address = InetAddress.getLocalHost();
				ip = address.getHostAddress().concat("/").concat(address.getHostName());
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
		} else {
			// If Linux system
			ip = request.getHeader("x-forwarded-for");
			if (ip == null || ip.length() == 0
					|| "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0
					|| "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("WL-Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0
					|| "unknown".equalsIgnoreCase(ip)) {
				ip = request.getRemoteAddr();
			}
		}
		return ip;
	}
	
	@RequestMapping("/mailPreview")
	public String mailPreview(HttpServletRequest request, Model model) {

		String subject = request.getParameter("subject");
		String content = request.getParameter("content");

		model.addAttribute("psubject", subject);
		model.addAttribute("pcontent", content);
		return "/mail/mail_preview";
	}
	
	@RequestMapping("/majorIncidentMail")
	public String MajorIncidentMail(Model model,MajorIncidentMailDto majorIncidentMailDto,HttpSession session) {
		List<SelectionOptionDto> serviceList = new ArrayList<SelectionOptionDto>();
		try {
			serviceList = mailTemplateService.getServiceList();
		} catch (MonitorException e) {
			logger.error(e);
		}
		model.addAttribute("serviceList", serviceList);
		validation(majorIncidentMailDto);
		session.setAttribute("majorIncidentMailList", majorIncidentMailDto);
		//1:majorIncidentList to send 
		model.addAttribute("mtype","1");
		model.addAttribute("serviceId",majorIncidentMailDto.getServiceId());
		model.addAttribute("templateId",majorIncidentMailDto.getTemplateId());
		return "/mail/mail_send";
		
	}
	
	//from majorIncidentList to valid
	public MajorIncidentMailDto validation(MajorIncidentMailDto majorIncidentMailDto) {
		if(majorIncidentMailDto.getStartDateTime() == "" || majorIncidentMailDto.getStartDateTime() == null) {
			majorIncidentMailDto.setStartDateTime(" ");
		}
		if(majorIncidentMailDto.getEndDateTime() == "" || majorIncidentMailDto.getEndDateTime() == null) {
			majorIncidentMailDto.setEndDateTime(" ");
		}
		if(majorIncidentMailDto.getResponsible() == "" || majorIncidentMailDto.getResponsible() == null) {
			majorIncidentMailDto.setResponsible(" ");
		}
		if(majorIncidentMailDto.getSrNumber() == "" || majorIncidentMailDto.getSrNumber() == null) {
			majorIncidentMailDto.setSrNumber(" ");
		}
		if(majorIncidentMailDto.getSeverity() == "" || majorIncidentMailDto.getSeverity() == null) {
			majorIncidentMailDto.setSeverity(" ");
		}
		if(majorIncidentMailDto.getIssueDescription() == "" || majorIncidentMailDto.getIssueDescription() == null) {
			majorIncidentMailDto.setIssueDescription(" ");
		}
		if(majorIncidentMailDto.getProblemId() == "" || majorIncidentMailDto.getProblemId() == null) {
			majorIncidentMailDto.setProblemId(" ");
		}
		if(majorIncidentMailDto.getRootCause() == "" || majorIncidentMailDto.getRootCause() == null) {
			majorIncidentMailDto.setRootCause(" ");
		}
		if(majorIncidentMailDto.getStatus() == "" || majorIncidentMailDto.getStatus() == null) {
			majorIncidentMailDto.setStatus(" ");
		}
		if(majorIncidentMailDto.getSourceFrom() == "" || majorIncidentMailDto.getSourceFrom() == null) {
			majorIncidentMailDto.setSourceFrom(" ");
		}
		if(majorIncidentMailDto.getResolutionTime() == "" || majorIncidentMailDto.getResolutionTime() == null) {
			majorIncidentMailDto.setResolutionTime(" ");
		}
		
		return majorIncidentMailDto;
	}
	
	 
}
