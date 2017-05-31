package de.htwg.se.moerakikemu.persistence.hibernate.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import de.htwg.se.moerakikemu.persistence.hibernate.HibernateDAO;

public class sessionFactory {

    private static SessionFactory sessionFactory = buildSessionFactoryHibernate();
    
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void tearDownHibernate() {
        if ( sessionFactory != null ) {
            sessionFactory.close();
        }
    }

    private static SessionFactory buildSessionFactoryHibernate() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure(HibernateDAO.class.getResource("/hibernate.cfg.xml"))
                .build();
        try {
            return new MetadataSources( registry ).buildMetadata().buildSessionFactory();
        } catch (Exception e) {
            StandardServiceRegistryBuilder.destroy( registry );
        }
        return null;
    }
}
