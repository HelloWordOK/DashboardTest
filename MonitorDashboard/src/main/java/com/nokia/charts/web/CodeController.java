package com.nokia.charts.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nokia.charts.util.MailUtils;
import com.nokia.charts.web.base.EchartController;

@Controller
@RequestMapping("/code")
public class CodeController extends EchartController {

	@RequestMapping("")
	public String codeInput(Model model) {

//		List<SelectionOptionDto> solutionList = new ArrayList<SelectionOptionDto>();
//		try {
//			solutionList = statisticsService.selectAllSolutionList();
//		} catch (MonitorException e) {
//			logger.error(e);
//		}
//		model.addAttribute("solutionList", solutionList);
		return "/code/code_input";
	}

	@RequestMapping("/export")
	public String codeExport(Model model) {

		return "/code/code_export";
	}

	@RequestMapping("/control")
	public String codeControl(Model model) {

		return "/code/code_control";
	}
	public Boolean isMail(String mail) {
		String[] mailList = new String[] {};
		// split the target mail list
		mailList = mail.split(",");

		for (int i = 0; i < mailList.length; i++) {
			String mail_single = mailList[i];
			// Check the mail address format
			if (!MailUtils.checkEmail(mail_single)) {
				System.out.println("The value of " + mail_single
						+ "is incorrect.");
				return false;
			}
		}
		return true;
	}
}
