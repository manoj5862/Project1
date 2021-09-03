package com.xworkz.vaccination.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xworkz.vaccination.dto.AddMemberDTO;
import com.xworkz.vaccination.entity.AddMemberEntity;
import com.xworkz.vaccination.entity.RegisterEntity;
import com.xworkz.vaccination.repository.AddMemberDAO;

import jdk.internal.org.jline.utils.Log;

@Service
public class AddMemberServiceImpl implements AddMemberService {

	private static Logger log;

	@Autowired
	private AddMemberDAO addMemberDAO;

	RegisterEntity entity = new RegisterEntity();

	int addmemberCountCheck;

	public static Map<String, String> map = new HashMap<String, String>();

	public AddMemberServiceImpl() {
		log = Logger.getLogger(getClass());
		log.debug("Created " + this.getClass().getSimpleName());
	}

	@Override
	public boolean ValidateAddMemberDTO(AddMemberDTO addMemberDTO) {
		log.info("Invoked ValidateAddMemberDTO");
		boolean flag = false;

		if (addMemberDTO.getName() != null && !addMemberDTO.getName().isEmpty() && addMemberDTO.getAadhar() != null
				&& addMemberDTO.getGender() != null && !addMemberDTO.getGender().isEmpty()
				&& addMemberDTO.getVaccineType() != null && !addMemberDTO.getVaccineType().isEmpty()) {

			flag = true;

		} else {
			map.put("name", "*Name field cant be empty!!!!");
			map.put("gender", "*Gender cant be empty!!!!");
			map.put("type", "*Type of field cant be empty!!!!");
			map.put("aadhaar", "*Invalid Aadhar");
		}
		return flag;

	}

	@Override
	public List<Object> AddMemberDTOSave(AddMemberDTO addMemberDTO) {
		log.info("Invoked AddMemberDTOSave");

		AddMemberEntity addMemberEntity = new AddMemberEntity();
		BeanUtils.copyProperties(addMemberDTO, addMemberEntity);
		boolean result = this.addMemberDAO.AddMemberDTOSave(addMemberEntity);
		if (result) {
			log.info("Successfully Saved Into Db");
			int addmemberCount = addmemberCountCheck;
			addmemberCount++;
			this.addMemberDAO.updateAddMemberCountByEmailId(addmemberCount, VaccinationLoginServiceImpl.emailId);
			List<AddMemberEntity> listOfAddMemberEntities = this.addMemberDAO.FetchAllEntities();
			if (listOfAddMemberEntities != null) {
				log.info("Fetched All the records Successfully");
				return new ArrayList<Object>(listOfAddMemberEntities);
			} else {
				log.error("Not able to fetch the records");
				return null;
			}

		} else {
			log.error("Not Saved Into Db");
			return null;
		}

	}

	@Override
	public boolean checkAddMemberCount(String EmailId) {
		log.info("Invoked checkAddMemberCount");
		addmemberCountCheck = this.addMemberDAO.CheckAddMemberCountByMail(EmailId);
		if (addmemberCountCheck < 4) {
			return true;
		} else {
			return false;
		}

	}

}
