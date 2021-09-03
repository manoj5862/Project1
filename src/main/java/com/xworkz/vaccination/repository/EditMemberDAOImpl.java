package com.xworkz.vaccination.repository;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.xworkz.vaccination.entity.AddMemberEntity;

@Repository
public class EditMemberDAOImpl implements EditMemberDAO {
	
	private static Logger log;

	@Autowired
	private SessionFactory factory;

	public EditMemberDAOImpl() {
		log = Logger.getLogger(getClass());
		log.debug("Created " + this.getClass().getSimpleName());
	}

	@Override
	public List<AddMemberEntity> getListOfMembers() {
		log.info("Invoked getListOfMembers");
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
	public void updateRecordByName(AddMemberEntity addMemberEntity) {
		log.info("Created "+this.getClass().getSimpleName());
		Session session = null;
		try {

			session = factory.openSession();
			session.beginTransaction();
			session.getNamedQuery("updateAddMemberEntityByName")
			.setParameter("Aadhaar", addMemberEntity.getAadhar())
			.setParameter("Gender", addMemberEntity.getGender())
			.setParameter("vaccineType", addMemberEntity.getVaccineType())
			.setParameter("nm", addMemberEntity.getName()).executeUpdate();
			session.getTransaction().commit();
			System.out.println("Updated Successfully");

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
