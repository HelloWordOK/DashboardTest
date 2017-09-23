package com.nokia.charts.util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class StringSplitUtils {

	public static Double SplitData(String obj, String splitParam, String split) {
		String[] valueList = obj.split("\\|");
		Double temp = null;
		for (String firstvalue : valueList) {
			if (firstvalue.startsWith(splitParam)) {
				String[] secondList = firstvalue.split(";");
				for (String secondvalue : secondList) {
					if (secondvalue.startsWith(splitParam)) {
						String[] thirdList = secondvalue.split("=");
						for (String thirdvalue : thirdList) {
							if (!thirdvalue.startsWith(splitParam)) {
								String[] fourList = thirdvalue.split(split);
								temp = Double.valueOf(fourList[0]);
							}
						}
					}
				}
			}
		}
		return temp;
	}

	public static String SplitMessage(String obj) {
		String temp = "";
		if (obj.contains("|")) {
			String[] valueList = obj.split("\\|");
			temp = valueList[0];
		} else {
			temp = obj;
		}
		return temp;
	}

	public static Double SplitThreshold(String obj) {
		String[] threshold = obj.split("\\,");
		return Double.valueOf(threshold[0]);
	}

	public static String upperFirstNumber(String name) {
		// name = name.substring(0, 1).toUpperCase() + name.substring(1);
		// return name;
		char[] cs = name.toCharArray();
		cs[0] -= 32;
		return String.valueOf(cs);
	}

	public static void main(String[] args) {
		String s = "11%";
		String y = "11%";
		int result = s.compareTo(y);
		if (result < 0) {
			System.out.println("s小于y");
		} else if (result > 0) {
			System.out.println("s大于y");
		} else if (result == 0) {
			System.out.println("s等于y");
		}
	}

	/**
	 * Split the targetMail address which contains "@alcatel-sbell.com.cn" 
	 * @param mail
	 * @return Alu mail address
	 */
	public static String splitAluMailAddress(String mail) {
		String[] mailList = new String[] {};
		List<String> alcatelList = new ArrayList<String>();
		// split the target mail list
		mailList = mail.split(",");
		for (int i = 0; i < mailList.length; i++) {
			String mail_single = mailList[i];
			mail_single = mail_single.trim();

			if (mail_single.indexOf("@alcatel-sbell.com.cn") != -1) {
				alcatelList.add(mail_single);
			}
		}
		mail = ListToString(alcatelList);

		return mail;
	}

	/**
	 * Split the targetMail address which contains "@nokia.com" 
	 * @param mail
	 * @return NOKIA mail address
	 */
	public static String splitNokiaMailAddress(String mail) {
		String[] mailList = new String[] {};
		List<String> nokiaList = new ArrayList<String>();
		// split the target mail list
		mailList = mail.split(",");
		for (int i = 0; i < mailList.length; i++) {
			String mail_single = mailList[i];
			mail_single = mail_single.trim();

			if (mail_single.indexOf("@nokia.com") != -1) {
				nokiaList.add(mail_single);
			}
		}
		mail = ListToString(nokiaList);

		return mail;
	}
	
	public static Map<String, String> splitMailAddress(String mail) {
		Map<String, String> map = new LinkedHashMap<String, String>();
		String[] mailList = new String[] {};
		List<String> alcatelList = new ArrayList<String>();
		List<String> nokiaList = new ArrayList<String>();
		// split the target mail list
		mailList = mail.split(",");

		for (int i = 0; i < mailList.length; i++) {
			String mail_single = mailList[i];
			mail_single = mail_single.trim();

			// Depends on different mail suffix, add them in different way
			if (mail_single.indexOf("@nokia.com") != -1) {
				nokiaList.add(mail_single);
			} else if (mail_single.indexOf("@alcatel-sbell.com.cn") != -1) {
				alcatelList.add(mail_single);
			}
		}

		// List to string
		String nokia = ListToString(nokiaList);
		String alcate = ListToString(alcatelList);
		
		// Add values to map
		map.put("nokia", nokia);
		map.put("alcate", alcate);
		return map;
	}

	/**
	 * List To String
	 * 
	 * @param list
	 *            :targetList
	 * @return String
	 */
	public static String ListToString(List<String> list) {
		StringBuffer sb = new StringBuffer();
		String str = "";
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i) == null || list.get(i) == "") {
					continue;
				}
				sb.append(list.get(i));
				sb.append(",");
			}

			str = sb.toString();
			return str.substring(0, str.length()-1);
		} else {
			return str;
		}
	}
}
