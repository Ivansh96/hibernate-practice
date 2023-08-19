package com.ivansh.util;

import com.ivansh.entity.Audit;
import com.ivansh.entity.User;
import com.ivansh.listener.AuditTableListener;
import lombok.experimental.UtilityClass;
import org.hibernate.SessionFactory;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.cfg.Configuration;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.internal.SessionFactoryImpl;

@UtilityClass
public class HibernateUtil {

    public static SessionFactory buildSessionFactory() {
        Configuration configuration = buildConfiguration();
        configuration.configure();

        var sessionFactory = configuration.buildSessionFactory();
//        registerListeners(sessionFactory);


        return sessionFactory;
    }

    private static void registerListeners(SessionFactory sessionFactory) {
        var factory = sessionFactory.unwrap(SessionFactoryImpl.class);
        var listenerService = factory.getServiceRegistry().getService(EventListenerRegistry.class);

        var auditTableListener = new AuditTableListener();

        listenerService.appendListeners(EventType.PRE_DELETE, auditTableListener);
        listenerService.appendListeners(EventType.PRE_INSERT, auditTableListener);
    }

    public static Configuration buildConfiguration() {
        Configuration configuration = new Configuration();
        configuration.setPhysicalNamingStrategy(new CamelCaseToUnderscoresNamingStrategy());
        configuration.addAnnotatedClass(User.class);
        configuration.addAnnotatedClass(Audit.class);
        return configuration;
    }
}
