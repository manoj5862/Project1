package com.xworkz.vaccination.repository;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ResetPasswordDAOImpl implements ResetPasswordDAO {
	
	private static Logger log;
	
	@Autowired
	private SessionFactory factory;

	public ResetPasswordDAOImpl() {
		log = Logger.getLogger(getClass());
		log.debug("Created "+this.getClass().getSimpleName());
	}
	
	@Override
	public boolean resetPasswordByEmailId(String email, String password) {
		log.info("Invoked resetPasswordByEmailId");
		Session session = null;
		try {

			session = factory.openSession();
			session.beginTransaction();
			session.getNamedQuery("updatePasswordByEmail").setParameter("pass", password)
					.setParameter("emailId", email).executeUpdate();
			session.getTransaction().commit();
            return true;
		} catch (HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();	
			log.error("You have an exception " + e.getMessage());
			return false;
		} finally {
			if (session != null) {
				session.close();
			}

		}
		
	}

	@Override
	public boolean resetNoOfLoginAttemptsByEmail(String email, int noOfLoginAttempts) {
		log.info("Invoked "+this.getClass().getSimpleName());
		Session session = null;
		try {

			session = factory.openSession();
			session.beginTransaction();
			session.getNamedQuery("updateNoOfLoginAttemptsByEmail").setParameter("loginAttempts", noOfLoginAttempts)
					.setParameter("emailId", email).executeUpdate();
			session.getTransaction().commit();
            return true;
		} catch (HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
			log.error("You have an exception " + e.getMessage());
			return false;
		} finally {
			if (session != null) {
				session.close();
			}

		}
	}

	

}
