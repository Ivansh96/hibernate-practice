package com.ivansh.util;

import com.ivansh.entity.Company;
import com.ivansh.entity.Payment;
import com.ivansh.entity.PersonalInfo;
import com.ivansh.entity.User;
import lombok.Cleanup;
import lombok.experimental.UtilityClass;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.time.LocalDate;

@UtilityClass
public class TestDataImporter {

    public void importData(SessionFactory sessionFactory) {
       @Cleanup Session session = sessionFactory.openSession();

       Company microsoft = saveCompany(session, "Microsoft");
       Company apple = saveCompany(session, "Apple");
       Company amazon = saveCompany(session, "Amazon");

       User billGates = saveUser(session, PersonalInfo.builder()
               .firstname("Bill")
               .lastname("Gates")
               .birthdate(LocalDate.of(1955, 10, 28))
               .build(), microsoft);
        User steveJobs = saveUser(session, PersonalInfo.builder()
                .firstname("Steve")
                .lastname("Jobs")
                .birthdate(LocalDate.of(1955, 11, 29))
                .build(), apple);
        User timCook = saveUser(session, PersonalInfo.builder()
                .firstname("Tim")
                .lastname("Cook")
                .birthdate(LocalDate.of(1955, 12, 24))
                .build(), apple);
        User jeffBezos = saveUser(session, PersonalInfo.builder()
                .firstname("Jeff")
                .lastname("Bezos")
                .birthdate(LocalDate.of(1952, 4, 22))
                .build(), amazon);

        savePayment(session, billGates, 200);
        savePayment(session, billGates, 500);
        savePayment(session, billGates, 600);

        savePayment(session, steveJobs, 250);
        savePayment(session, steveJobs, 550);
        savePayment(session, steveJobs, 650);

        savePayment(session, timCook, 300);
        savePayment(session, timCook, 400);
        savePayment(session, timCook, 700);

        savePayment(session, jeffBezos, 350);
        savePayment(session, jeffBezos, 450);
        savePayment(session, jeffBezos, 750);

    }

    private Company saveCompany(Session session, String name) {
        Company company = Company.builder()
                .name(name)
                .build();
        session.persist(company);

        return company;
    }

    private User saveUser(Session session, PersonalInfo personalInfo, Company company) {
        User user = User.builder()
                .personalInfo(personalInfo)
                .company(company)
                .build();
        session.persist(user);

        return user;
    }

    private Payment savePayment(Session session, User user, Integer payment) {
        Payment payment1 = Payment.builder()
                .receiver(user)
                .amount(payment)
                .build();
        session.persist(payment1);

        return payment1;
    }
}
