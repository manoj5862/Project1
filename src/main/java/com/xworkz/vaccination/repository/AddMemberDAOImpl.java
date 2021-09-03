package com.xworkz.vaccination.repository;

import java.util.List;
import java.util.Objects;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.QueryProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.xworkz.vaccination.entity.AddMemberEntity;
@Repository
public class AddMemberDAOImpl implements AddMemberDAO {

	private static Logger log;
	
	@Autowired
	private SessionFactory factory;
	
	public AddMemberDAOImpl() {
		log = Logger.getLogger(getClass());
		log.debug("Created "+this.getClass().getSimpleName());
	}
	
	@Override
	public boolean AddMemberDTOSave(AddMemberEntity addMemberEntity) {
		log.info("Invoked AddMemberDTOSave");
		Session session = this.factory.openSession();
		try {
			Transaction transaction = session.beginTransaction();
			session.save(addMemberEntity);
			transaction.commit();
			return true;
		} catch (Exception e) {
			Transaction transaction = session.getTransaction();
			transaction.rollback();
			return false;
		} finally {
			if (session != null) {
				session.close();
				log.info("Session closed");
			} else {
				log.error("session is not closed");
			}
		}
		
	}

	@Override
	public List<AddMemberEntity> FetchAllEntities() {
		log.info("Invoked FetchAllEntities");
		List<AddMemberEntity> listOfEnrollEntities = null;
		Session session = null;
		try {
			session = this.factory.openSession();
			String hql = "from AddMemberEntity";
			Query<AddMemberEntity> query = session.createQuery(hql);
			listOfEnrollEntities = query.list();
			return listOfEnrollEntities;
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
	public int CheckAddMemberCountByMail(String emailId) {
		log.info("Invoked CheckAddMemberCountByMail");
		Session session = null;
		try {
			session = factory.openSession();
			Query<Object> query = session.getNamedQuery("getNoOfCheckAddmembersByMail");
			query.setParameter("em", emailId);
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
		return 0;

	}

	@Override
	public void updateAddMemberCountByEmailId(int addMemberCount,  String emailId) {
		log.info("Invoked updateAddMemberCountByEmailId");
		Session session = null;
		try {

			session = factory.openSession();
			session.beginTransaction();
			session.getNamedQuery("updateAddMemberCountByEmail").setParameter("addMemberCount", addMemberCount)
					.setParameter("emailId", emailId).executeUpdate();
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
		
}
