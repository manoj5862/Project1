package com.xworkz.vaccination.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xworkz.vaccination.repository.DeleteMemberDAO;

@Service
public class DeleteMemberServiceImpl implements DeleteMemberService {

	private static Logger log;
	@Autowired
	private DeleteMemberDAO dao;

	public DeleteMemberServiceImpl() {
		log = Logger.getLogger(getClass());
		log.debug("Invoked " + this.getClass().getSimpleName());
	}

	@Override
	public boolean deleteMemberService(String name) {
		log.info("Invoked deleteMemberService");
		boolean result = this.dao.deleteMemberByName(name);
		log.info("Deleted Successfully");
		if (result) {
			return true;
		}
		return false;
	}

}
