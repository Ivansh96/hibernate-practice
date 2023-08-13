package com.ivansh;


import com.ivansh.entity.Company;
import com.ivansh.entity.PersonalInfo;
import com.ivansh.entity.User;
import com.ivansh.util.HibernateUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.time.LocalDate;


@Slf4j
public class HibernateRunner {
    public static void main(String[] args) {

        Company company = Company.builder()
                .name("Yandex")
                .build();

        User user = User.builder()
                .username("mrmr13@gmail.com")
                .personalInfo(PersonalInfo.builder()
                        .firstname("Maria")
                        .lastname("Makarova")
                        .birthdate(LocalDate.of(1998, 3, 1))
                        .build())
                .company(company)
                .build();
        log.info("Entity is in transient state. Object: {}", user);

        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {
            Session session1 = sessionFactory.openSession();
            try (session1) {

                session1.beginTransaction();

                session1.persist(company);
                session1.persist(user);

                session1.getTransaction().commit();


            }
        }
    }
}
