package com.nokia.charts.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

public class DateUtils {

	public static final Logger logger = Logger.getLogger(DateUtils.class);
	public static final String DATE_FROMAT_CN = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE_FROMAT_YYYY_MM_dd = "yyyy-MM-dd";
	public static final String DATE_FROMAT_YYYY_MM = "yyyy-MM";

	public static final String DATE_FROMAT_EN = "dd/MM/yyyy HH:mm:ss";

	/**
	 * 获得格式化对象
	 * 
	 * @param pattern
	 * @return
	 */
	public static DateFormat getDateFormat(String pattern) {
		return new SimpleDateFormat(pattern);
	}

	/**
	 * 获得当前时间
	 * 
	 * @return
	 */
	public static String getCurDateTime(String fromatStr) {
		DateFormat df = new SimpleDateFormat(fromatStr);
		return df.format(new Date().getTime());
	}

	/**
	 * 时间加减
	 * 
	 * @param amount
	 * @return
	 */
	public static Date dateSub(int amount) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DATE, amount);
		return calendar.getTime();
	}

	public static Date dateChange(Date currentDate, int changeType, int amount) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(currentDate);
		calendar.add(changeType, amount);
		return calendar.getTime();
	}

	// 按格式取系统日期
	public static String getDateWithYMD() {
		Calendar now = Calendar.getInstance();
		int month = now.get(Calendar.MONTH) + 1;
		int day = now.get(Calendar.DAY_OF_MONTH);
		int year = now.get(Calendar.YEAR);
		String date = year + "-" + month + "-" + day;
		return date;
	}

	// 按格式取系统前一个月的年月
	public static String getDateWithBYM() {
		Calendar now = Calendar.getInstance();
		int month = now.get(Calendar.MONTH);
		int year = now.get(Calendar.YEAR);
		String date = year + "-" + month;
		return date;
	}

	/**
	 * 获得前天
	 * 
	 * @return
	 */
	public static String getdayBeforeYesterday() {
		Date date = new Date();// 取时间
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, -2);//
		date = calendar.getTime();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(date);
		return dateString;
	}

	/**
	 * 根据输入的日期字符串 和 提前天数 ， * 获得 指定日期提前几天的日期对象 *
	 * 
	 * @param dateString
	 *            日期对象 ，格式如1-31-1900 *
	 * @param lazyDays
	 *            倒推的天数
	 * @return 指定日期倒推指定天数后的日期对象 *
	 * @throws ParseException
	 */
	public static Date getDate(String dateString, int beforeDays) throws ParseException {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date inputDate = dateFormat.parse(dateString);
		Calendar cal = Calendar.getInstance();
		cal.setTime(inputDate);
		int inputDayOfYear = cal.get(Calendar.DAY_OF_YEAR);
		cal.set(Calendar.DAY_OF_YEAR, inputDayOfYear - beforeDays);
		return cal.getTime();
	}

	public static Date getDateStr(Date dateString, int beforeDays) throws ParseException {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dateString);
		int inputDayOfYear = cal.get(Calendar.DAY_OF_YEAR);
		cal.set(Calendar.DAY_OF_YEAR, inputDayOfYear - beforeDays);
		return cal.getTime();
	}

	/**
	 * 日期转换为字符串
	 * 
	 * @param date
	 * @return
	 */
	public static String date2String(Date date) {
		return date2String(date, "yyyy-MM-dd");
	}

	/**
	 * 日期转换为字符串
	 * 
	 * @param date
	 * @return
	 */
	public static String date2String(Date date, String fromatStr) {
		DateFormat df = new SimpleDateFormat(fromatStr);
		return df.format(date);
	}

	/**
	 * 字符串转为日期
	 * 
	 * @param dateStr
	 * @param fromatStr
	 * @return
	 * @throws ParseException
	 */
	public static Date String2Date(String dateStr, String fromatStr) {
		SimpleDateFormat sdf = new SimpleDateFormat(fromatStr);
		try {
			return sdf.parse(dateStr);
		} catch (ParseException e) {
			logger.error(e);
		}
		return null;
	}

	/**
	 * 字符串转为日期
	 * 
	 * @param dateStr
	 * @param fromatStr
	 * @return
	 * @throws ParseException
	 */
	public static Date String2Date(String dateStr) {
		return String2Date(dateStr, "yyyy-MM-dd");
	}

	/**
	 * 获得当前日期
	 * 
	 * @return
	 */
	public static String getCurDate() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.format(new Date());
	}

	/**
	 * 获得当前日期
	 * 
	 * @return
	 */
	public static String getCurTime() {
		DateFormat df = new SimpleDateFormat("yyyyMMddhhmmss");
		return df.format(new Date());
	}

	/**
	 * 获得当前日期
	 * 
	 * @return
	 */
	public static String getCurDate(String fromatStr) {
		DateFormat df = new SimpleDateFormat(fromatStr);
		return df.format(new Date());
	}

	/**
	 * 格式化时间
	 * 
	 * @param dateStr
	 * @return
	 */
	public static String formatDate(String dateStr, String tofromatStr) {
		return formatDate(dateStr, DateUtils.DATE_FROMAT_CN, tofromatStr);
	}

	/**
	 * 格式化时间
	 * 
	 * @param dateStr
	 * @return
	 */
	public static String formatDate(String dateStr, String fromformatStr, String toformatSrr) {
		DateFormat df = new SimpleDateFormat(fromformatStr);
		try {
			Date date = df.parse(dateStr);
			return date2String(date, toformatSrr);
		} catch (ParseException e) {
			logger.error(e);
		}
		return null;
	}

	/**
	 * 将日期字符串转日期类型
	 * 
	 * @param date
	 * @return
	 */
	public static Date parseDate(String date, String pattern) {
		DateFormat df = new SimpleDateFormat(pattern);
		try {
			return df.parse(date);
		} catch (ParseException e) {
			logger.error(e);
		}
		return null;
	}

	/**
	 * 将日期字符串转日期类型
	 * 
	 * @param date
	 * @return
	 */
	public static Date parseDate(String date) {
		DateFormat df = new SimpleDateFormat();
		try {
			return df.parse(date);
		} catch (ParseException e) {
			logger.error(e);
		}
		return null;
	}

	public static final int FIRST_DAY_CN = Calendar.MONDAY;
	public static final int FIRST_DAY_EN = Calendar.SUNDAY;

	public static List<String> getWeekDaysByCurrDate(Date curDate, String formatStr, int firstDay) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(curDate);
		setToFirstDay(calendar, firstDay);
		List<String> weekList = new ArrayList<String>();
		for (int i = 0; i < 7; i++) {
			String week = calendarToString(calendar, formatStr);
			weekList.add(week);
			calendar.add(Calendar.DATE, 1);
		}
		return weekList;
	}

	private static void setToFirstDay(Calendar calendar, int firstDay) {
		while (calendar.get(Calendar.DAY_OF_WEEK) != firstDay) {
			calendar.add(Calendar.DATE, -1);
		}
	}

	public static String calendarToString(Calendar calendar, String formatStr) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(formatStr);
		return dateFormat.format(calendar.getTime());
	}

	public static int getWeek(Date curDate, int firstDay) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(curDate);
		setToFirstDay(calendar, firstDay);
		return calendar.get(Calendar.WEEK_OF_YEAR);
	}

	public static int getMonthEndDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DATE, 1);// 把日期设置为当月第一天
		calendar.roll(Calendar.DATE, -1);// 日期回滚一天，也就是最后一天
		int maxDay = calendar.get(Calendar.DATE);
		return maxDay;
	}

	public static Integer getYear(Date date) {
		Calendar now = Calendar.getInstance();
		now.setTime(date);
		int year = now.get(Calendar.YEAR);
		return year;
	}

	public static Map<Integer, List<Integer>> getDateToDateWeekList(String startDate, String endDate,
			String dateTemplate,int first_day) {
		Calendar c_begin = Calendar.getInstance();
		Calendar c_end = Calendar.getInstance();
		c_begin.setTime(String2Date(startDate, dateTemplate));
		c_end.setTime(String2Date(endDate, dateTemplate));
		Map<Integer, List<Integer>> weekMap = new LinkedHashMap<Integer, List<Integer>>();
		c_end.add(Calendar.DAY_OF_YEAR, 1); // 结束日期下滚一天是为了包含最后一天
		Calendar c_temp = Calendar.getInstance();
		c_temp.setTime(c_begin.getTime());
		setToFirstDay(c_temp, first_day);
		int begin_year = c_begin.get(Calendar.YEAR);
		List<Integer> weekList = new ArrayList<Integer>();
		while (c_temp.before(c_end)) {
			int begin_week_year = c_temp.get(Calendar.YEAR);
			int yearWeek = c_temp.getActualMaximum(Calendar.WEEK_OF_YEAR);
			int c_week = c_temp.get(Calendar.WEEK_OF_YEAR);
			if (begin_week_year < begin_year) {
				if(weekMap.get(begin_week_year)!=null){
					if(!weekMap.get(begin_week_year).contains(yearWeek)){
						weekMap.get(begin_week_year).add(yearWeek);
					}
				}else{
					weekList.add(yearWeek);
					weekMap.put(begin_week_year, weekList);
				}
				weekList = new ArrayList<Integer>();
				begin_week_year = begin_year;
				if(c_week==yearWeek){
					c_temp.add(Calendar.WEEK_OF_YEAR, 1);
					continue;
				}
			}
			if (c_week == yearWeek) {
				weekList.add(c_week);
				weekMap.put(begin_week_year, weekList);
				weekList = new ArrayList<Integer>();
				begin_year = begin_week_year+1;
			} else {
				weekList.add(c_week);
				weekMap.put(begin_week_year, weekList);
			}
			c_temp.add(Calendar.WEEK_OF_YEAR, 1);
		}
		return weekMap;
	}
	
	public static List<Object> getDateToDateMonthList(String startDate, String endDate,
			String dateTemplate) {
		Calendar c_begin = Calendar.getInstance();
		Calendar c_end = Calendar.getInstance();
		c_begin.setTime(String2Date(startDate, dateTemplate));
		c_end.setTime(String2Date(endDate, dateTemplate));
		c_end.add(Calendar.MONTH, 1);
		List<Object> monthList = new ArrayList<Object>();
		while (c_begin.before(c_end)) {
			int month = c_begin.get(Calendar.MONTH)+1;
			if(month<10){
				monthList.add(c_begin.get(Calendar.YEAR)+"-0"+month);
			}else{
				monthList.add(c_begin.get(Calendar.YEAR)+"-"+month);
			}
			c_begin.add(Calendar.MONTH, 1);
		}
		return monthList;
	}

	public static void main(String[] args) {
		getDateToDateWeekList("2017-01-01", "2017-12-31", DATE_FROMAT_YYYY_MM_dd,FIRST_DAY_CN);
	}
}
