package org.anoop.ola.messenger.dto;

import java.util.List;
import java.util.logging.Logger;

import org.anoop.ola.messenger.model.Profile;
import org.anoop.ola.messenger.utils.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javassist.bytecode.stackmap.TypeData.ClassName;

public class ProfileDTOImpl implements ProfileDTO{
	
	private static final Logger log = Logger.getLogger(ClassName.class.getName());

	@Override
	public List<Profile> getAllProfiles() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Query query = session.createQuery("from Profile");
		return query.list();
	}

	@Override
	public Profile getProfile(String profileName) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Query query = session.createQuery("from Profile p where p.profileName=:profileName");
		query.setParameter("profileName", profileName);
		List<Profile> profiles = query.list();
		return profiles.get(0);
	}

	@Override
	public Profile insertProfile(Profile profile) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction txn = null;
		
		try{
			txn = session.beginTransaction();
			long id = (long) session.save(profile);
			profile.setId(id);
			txn.commit();
		} catch (Exception ex){
			if( txn != null){
				txn.rollback();
			}
			ex.printStackTrace();
		} finally {
			session.close();
		}
		return profile;
	}

	@Override
	public Profile updateProfile(Profile profile, String profileName) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction txn = null;
		
		try{
			txn = session.beginTransaction();
			Query query = session.createQuery("from Profile p where p.profileName=:profileName");
			query.setParameter("profileName", profileName);
			Profile prof = (Profile) query.uniqueResult();
			
			prof.setFirstName(profile.getFirstName());
			prof.setLastName(profile.getLastName());
			session.update(prof);
			txn.commit();
		} catch(Exception ex){
			if(txn != null){
				txn.rollback();
			}
			ex.printStackTrace();
		} finally{
			session.close();
		}
		return profile;
	}

	@Override
	public void removeProfile(String profileName) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction txn = null;
		try{
			txn = session.beginTransaction();
			Query query = session.createQuery("from Profile p where profileName=:profileName");
			query.setParameter("profileName", profileName);
			Profile profile = (Profile) query.uniqueResult();
			session.delete(profile);
			txn.commit();
		} catch (Exception exc) {
			if (txn != null){
				txn.rollback();
			}
			exc.printStackTrace();
		} finally {
			session.close();
		}	
	}
}
