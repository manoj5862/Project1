package com.xworkz.vaccination.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.xworkz.vaccination.service.RegisterService;
import com.xworkz.vaccination.service.VaccinationLoginService;
import com.xworkz.vaccination.service.VaccinationLoginServiceImpl;
import com.xworkz.vaccination.service.VaccinationOTPService;

import javassist.bytecode.stackmap.BasicBlock.Catch;
import jdk.internal.org.jline.utils.Log;

@Controller
public class LoginController {

	private static Logger log;
	@Autowired
	private VaccinationLoginService loginService;

	@Autowired
	private VaccinationOTPService vaccinationOTPService;

	public LoginController() {
		log = Logger.getLogger(getClass());
		log.debug("Created" + this.getClass().getSimpleName());
	}

	@RequestMapping("getLoginPage.do")
	public String onGetLoginPage() {
		Log.info("Invoked onGetLoginPage");
		return "Login";
	}

	@RequestMapping("/Login.do")
	public String onLogin(@RequestParam String email, @RequestParam String password, Model model) {
		log.info("Invoked Login method");
		try {
			boolean outcome = this.loginService.loginEmailAndPasswordValidate(email, password);
			if (outcome) {
				boolean result = this.loginService.validateEmail(email);
				if (result) {
					boolean verifyEmailFromDb = this.vaccinationOTPService.verifyEmailFromDB(email);
					if (verifyEmailFromDb) {
						boolean checkNoOfLoginAttempts = this.loginService.CheckNoOfLoginAttempts(email);
						if (checkNoOfLoginAttempts) {
							boolean loginSuccessResult = this.loginService.loginService(email, password, model);
							if (loginSuccessResult) {
								model.addAttribute("LoginSuccessMessage", "Login Successfull");
								model.addAttribute("UserName", VaccinationLoginServiceImpl.UserName);
								System.out.println("Successfully Logged In");
								return "LoginSuccess";
							} else {
								model.addAttribute("LoginFailMessage", "Incorrect Password Entered");
								System.out.println("Incorrect Password Entered");
								return "Login";
							}
						} else {
							model.addAttribute("LoginBlockMessage", "User is blocked exceeded no of login attempts");
							System.out.println("User is blocked exceeded no of login attempts");
							return "Login";
						}
					} else {
						model.addAttribute("EmailVerifyMessage", "The Mail You Entered Is Not Exist");
						return "Login";
					}

				} else {
					model.addAttribute("LoginEmailVerifyMessage", "The Mail You Entered is not correct");
					return "Login";
				}

			} else {
				model.addAttribute("LoginEmailAndPasswordEmptyValidateMessage", "Email or Password Cannot be empty");
				return "Login";
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.error("You have an exception " + e.getMessage());
		}
		return null;

	}
}
