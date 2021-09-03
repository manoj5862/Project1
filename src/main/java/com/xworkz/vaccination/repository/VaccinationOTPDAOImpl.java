package com.xworkz.vaccination.repository;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.xworkz.vaccination.entity.RegisterEntity;

@Repository
public class VaccinationOTPDAOImpl implements VaccinationOTPDao {
	
	private static Logger log;

	@Autowired
	private SessionFactory factory;

	public VaccinationOTPDAOImpl() {
		log = Logger.getLogger(getClass());
		log.debug("Created" + this.getClass().getSimpleName());
	}

	@Override
	public RegisterEntity verifyEmailFromDBByEmail(String email) {
		log.info("Invoked verifyEmailFromDBByEmail");
		try {
			Session session = this.factory.openSession();
			org.hibernate.Query<RegisterEntity> query = session.createNamedQuery("GetRecordByMail");
			query.setParameter("em", email);
			RegisterEntity entity = query.uniqueResult();
			return entity;

		} catch (Exception e) {
			e.printStackTrace();
			log.error("You have an exception "+e.getMessage());
			return null; 
		}

		
	}

}
