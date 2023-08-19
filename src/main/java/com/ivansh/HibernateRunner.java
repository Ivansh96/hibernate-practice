package com.ivansh;


import com.ivansh.entity.*;
import com.ivansh.util.HibernateUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Slf4j
public class HibernateRunner {
    public static void main(String[] args) {

        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
             Session session = sessionFactory.openSession()) {

            session.beginTransaction();

            Company microsoft = saveCompany(session, "Microsoft");
            Company apple = saveCompany(session, "Apple");
            Company google = saveCompany(session, "Google");



            User billGates = saveUser(session, "Bill", "Gates",
                    LocalDate.of(1955, Month.OCTOBER, 28), microsoft);
            User steveJobs = saveUser(session, "Steve", "Jobs",
                    LocalDate.of(1955, Month.FEBRUARY, 24), apple);
            User sergeyBrin = saveUser(session, "Sergey", "Brin",
                    LocalDate.of(1973, Month.AUGUST, 21), google);
            User timCook = saveUser(session, "Tim", "Cook",
                    LocalDate.of(1960, Month.NOVEMBER, 1), apple);
            User dianeGreene = saveUser(session, "Diane", "Greene",
                    LocalDate.of(1955, Month.JANUARY, 1), google);

            savePayment(session, billGates, 100);
            savePayment(session, billGates, 300);

            savePayment(session, steveJobs, 250);
            savePayment(session, steveJobs, 600);

            savePayment(session, timCook, 400);
            savePayment(session, timCook, 300);

            savePayment(session, sergeyBrin, 500);
            savePayment(session, sergeyBrin, 500);

            savePayment(session, dianeGreene, 300);
            savePayment(session, dianeGreene, 300);


            session.getTransaction().commit();
        }
    }


    private static Company saveCompany(Session session, String name) {
        Company company = Company.builder()
                .name(name)
                .build();
        session.persist(company);

        return company;
    }

    private static User saveUser(Session session,
                                 String firstname,
                                 String lastname,
                                 LocalDate birthdate,
                                 Company company
    ) {
        User user = User.builder()
                .username(firstname + lastname)
                .personalInfo(PersonalInfo.builder()
                        .firstname(firstname)
                        .lastname(lastname)
                        .birthdate(birthdate)
                        .build())
                .company(company)
                .build();
        session.persist(user);

        return user;
    }

    private static void savePayment(Session session, User user, Integer payment) {
        Payment payment1 = Payment.builder()
                .receiver(user)
                .amount(payment)
                .build();
        session.persist(payment1);
    }
}
