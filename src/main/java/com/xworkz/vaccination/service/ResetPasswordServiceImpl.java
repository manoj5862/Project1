package com.xworkz.vaccination.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xworkz.vaccination.dto.ResetPasswordDTO;
import com.xworkz.vaccination.repository.ResetPasswordDAO;
import com.xworkz.vaccination.util.PasswordEncryptorAndDecryptor;

@Service
public class ResetPasswordServiceImpl implements ResetPasswordService {

	private static Logger log;

	@Autowired
	private PasswordEncryptorAndDecryptor passwordEncryptDecrypt;

	@Autowired
	private ResetPasswordDAO resetPasswordDAO;

	public ResetPasswordServiceImpl() {
		log = Logger.getLogger(getClass());
		log.info("Created " + this.getClass().getSimpleName());
	}

	@Override
	public boolean ValidateResetPasswordDTO(ResetPasswordDTO resetPasswordDTO) {
		log.info("Invoked ValidateResetPasswordDTO");
		if (!resetPasswordDTO.getEmail().isEmpty() && resetPasswordDTO.getEmail() != null
				&& !resetPasswordDTO.getPassword().isEmpty() && resetPasswordDTO.getPassword() != null
				&& !resetPasswordDTO.getConfirmpassword().isEmpty() && resetPasswordDTO.getConfirmpassword() != null) {
			return true;
		} else {
			return false;
		}

	}

	@Override
	public boolean resetPassword(String email, String password) {
		log.info("Invoked resetPassword");
		String encryptPassword = this.passwordEncryptDecrypt.encrypt(password);
		boolean result = this.resetPasswordDAO.resetPasswordByEmailId(email, encryptPassword);
		if (result) {
			return true;
		} else {
			return false;
		}

	}

	@Override
	public boolean resetNoOfLoginAttempts(String email) {
		log.info("Invoked resetNoOfLoginAttempts");
		int noOfLoginAttempts = 0;
		boolean result = this.resetPasswordDAO.resetNoOfLoginAttemptsByEmail(email, noOfLoginAttempts);
		if (result) {
			return true;
		} else {
			return false;
		}

	}

}
