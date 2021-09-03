package com.xworkz.vaccination.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LogoutController {
	
	private static Logger log;

	public LogoutController() {
		log = Logger.getLogger(getClass());
		log.debug("Created "+this.getClass().getSimpleName());
	}
	
	 @RequestMapping("/logout.do")
	    public String logout(HttpServletRequest request){
		 log.info("logout");
	        request.getSession().invalidate();
	        return "Login";
	    }
	}

