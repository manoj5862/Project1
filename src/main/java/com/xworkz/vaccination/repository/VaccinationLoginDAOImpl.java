package com.xworkz.vaccination.repository;

import java.util.Objects;

import javax.servlet.jsp.tagext.TryCatchFinally;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.QueryProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.xworkz.vaccination.entity.RegisterEntity;
import com.xworkz.vaccination.util.PasswordEncryptorAndDecryptor;

@Repository
public class VaccinationLoginDAOImpl implements VaccinationLoginDAO {

	private static Logger log;

	@Autowired
	private SessionFactory factory;

	public VaccinationLoginDAOImpl() {
		log = Logger.getLogger(getClass());
		log.debug("Created " + this.getClass().getSimpleName());
	}

	@Override
	public RegisterEntity getEmailByEntity(String email) {
		log.info("Invoked getEmailByEntity");
		RegisterEntity enrollEntity = null;
		Object session = null;
		try {
			session = this.factory.openSession();
			Query<RegisterEntity> query = ((QueryProducer) session).createNamedQuery("GetRecordByMail");
			query.setParameter("em", email);
			enrollEntity = query.uniqueResult();
			return enrollEntity;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("You have an exception " + e.getMessage());
			return null;
		} finally {
			if (session != null) {
				log.info("Session is closed");
			} else {
				log.error("Session is not closed");
			}
		}

	}

	@Override
	public void updateAttempt(int noOfUnsuccesfulLoginAttemps, String email) {
		log.info("Invoked updateAttempt");
		Session session = null;
		try {

			session = factory.openSession();
			session.beginTransaction();
			session.getNamedQuery("updateNoOfLoginAttempByEmail").setParameter("login", noOfUnsuccesfulLoginAttemps)
					.setParameter("emailId", email).executeUpdate();
			session.getTransaction().commit();

		} catch (HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
			log.error("You have an exception " + e.getMessage());
		} finally {
			if (session != null) {
				session.close();
			}

		}

	}

	@Override
	public Integer getNoOFLoginAttemptsByEmailId(String mail) {
		log.info("Invoked getNoOFLoginAttemptsByEmailId");
		Session session = null;
		try {
			session = factory.openSession();
			Query<Object> query = session.getNamedQuery("getNoOfLoginAttemptsByMail");
			query.setParameter("em", mail);
			Object object = query.uniqueResult();
			if (object != null) {
				Integer noOfLoginAttempts = (Integer) object;
				return noOfLoginAttempts;
			}

		} catch (HibernateException e) {
			log.error(e.getMessage());
		} finally {
			if (Objects.nonNull(session)) {
				session.close();
				log.info("Session is closed");
			} else {
				log.error("Session is not closed");
			}
		}
		return null;

	}

}
