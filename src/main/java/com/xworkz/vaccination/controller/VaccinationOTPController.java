package com.xworkz.vaccination.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.xworkz.vaccination.service.VaccinationOTPService;
import com.xworkz.vaccination.util.EmailValidator;
import com.xworkz.vaccination.util.RandomNumberGenerator;

@Controller
public class VaccinationOTPController {

	private static Logger log;
	@Autowired
	private VaccinationOTPService vaccinationOTPService;

	public VaccinationOTPController() {
		log = log.getLogger(getClass());
		log.debug("Created " + this.getClass().getSimpleName());
	}

	@RequestMapping("/getOTPPage.do")
	public String onOTPVaccination() {
		log.info("Invoked onOTPVaccination");
		return "OTPVaccination";
	}

	@RequestMapping(value = "/GetOtp.do", method = RequestMethod.GET)
	public String onGetVaccinationOtp(@RequestParam String email, Model model) {
		log.info("Invoked onGetVaccinationOtp() ");

		boolean result = this.vaccinationOTPService.validateEmail(email);

		if (result) {
			boolean emailSpellVerify = EmailValidator.emailValidate(email);
			if (emailSpellVerify) {
				boolean outcome = this.vaccinationOTPService.verifyEmailFromDB(email);
				if (outcome) {
					model.addAttribute("EmailVerifyMessage", "email already exists.. Please Login");
					return "OTPVaccination";
				} else {
					boolean otpsent = this.vaccinationOTPService.onVaccinationOTPSend(email);
					if (otpsent) {
						return "OTPVerification";
					} else
						model.addAttribute("MailSenderMessage",
								"Something went wrong OTP Not sent to mail EmailId.. Try after sometime");
					return "OTPVaccination";
				}
			} else {
				model.addAttribute("EmailSpellVerifyMessage", "Invalid Email Entered..");
				return "OTPVaccination";
			}

		} else {
			model.addAttribute("EmailValidationMessage", "Email cannot be empty");
			return "OTPVaccination";
		}
	}

	@RequestMapping(value = "/OTPVerify.do", method = RequestMethod.GET)
	public String onOTPVerify(@RequestParam Integer enteredOtp, Model model) {

		log.info("Invoked onOTPVerify");
		boolean verifyOtp = this.vaccinationOTPService.verifyOTP(enteredOtp);
		if (verifyOtp) {
			model.addAttribute("OtpVerificationMessage", " Your OTP Verification is Successfull!!");
			log.info("OTP Verified");
			return "Register";
		} else {
			model.addAttribute("OtpVerificationMessage", "Incorrect OTP");
			log.info("Incorrect OTP Entered");
			return "OTPVerification";
		}

	}
}
