package org.anoop.ola.messenger.utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
	private static SessionFactory sessionFactory;
	
	static {
		try {
				System.out.println("*********************************************");
				// Create the SessionFactory from standard (hibernate.cfg.xml)
				// config file.
				sessionFactory = new Configuration().configure().buildSessionFactory();
			} catch (Throwable ex) {
				System.out.println("********************************************&&&&&&&&&&&&&&&&&&&&");
				// Log the exception.
				System.err.println("Initial SessionFactory creation failed." + ex);
				throw new ExceptionInInitializerError(ex);
			}
	}
	
	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
}
