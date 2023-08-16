package com.ivansh;

import com.ivansh.entity.*;
import com.ivansh.util.HibernateUtil;
import com.ivansh.util.HibernateUtilTest;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.joining;
import static org.junit.jupiter.api.Assertions.*;

class HibernateRunnerTest {

    @Test
    void testHql() {

        try (var sessionFactory = HibernateUtilTest.buildSessionFactory();
             Session session = sessionFactory.openSession();) {
            session.beginTransaction();

            String name = "Ivan";
            String companyName = "Google";

            var result = session.createQuery("select u from User u " +
                            "join u.company c " +
                            "where u.personalInfo.firstname = :firstname and c.name = :companyName", User.class)
                    .setParameter("firstname", name)
                    .setParameter("companyName", companyName)
                    .list();

            session.getTransaction().commit();
        }

    }

//    @Test
//    void testInheritance() {
//        try (var sessionFactory = HibernateUtilTest.buildSessionFactory();
//             Session session = sessionFactory.openSession();) {
//            session.beginTransaction();
//
//            Company company = Company.builder()
//                    .name("Sony")
//                    .build();
//            session.persist(company);
//
//            Programmer programmer = Programmer.builder()
//                    .username("ivan121@gmail.com")
//                    .personalInfo(PersonalInfo.builder()
//                            .firstname("Vlad")
//                            .lastname("Vladov")
//                            .birthdate(LocalDate.of(1999, 1, 2))
//                            .build())
//                    .company(company)
//                    .language(Language.JAVA)
//                    .build();
//
//            Profile programmerProfile = Profile.builder()
//                    .language("RU")
//                    .street("Lomonosova St 99")
//                    .build();
//            programmerProfile.setUser(programmer);
//
//            session.persist(programmer);
//
//            Manager manager = Manager.builder()
//                    .username("semen1221@gmail.com")
//                    .personalInfo(PersonalInfo.builder()
//                            .firstname("Semen")
//                            .lastname("Semenov")
//                            .birthdate(LocalDate.of(1995, 11, 21))
//                            .build())
//                    .company(company)
//                    .projectName("PS 6")
//                    .build();
//
//            Profile managerProfile = Profile.builder()
//                    .language("RU")
//                    .street("Kirova St 13")
//                    .build();
//            managerProfile.setUser(manager);
//
//            session.persist(manager);
//
//            session.flush();
//            session.clear();
//
//            Programmer programmer1 = session.get(Programmer.class, programmer.getId());
//            User user = session.get(User.class, manager.getId());
//
//            session.getTransaction().commit();
//        }
//    }

    @Test
    void testInMemoryDB() {
        try (var sessionFactory = HibernateUtilTest.buildSessionFactory();
             Session session = sessionFactory.openSession();) {
            session.beginTransaction();

            Company company = Company.builder()
                    .name("Amazon")
                    .build();

            session.persist(company);

            session.getTransaction().commit();


        }

    }

    @Test
    void testOneToOne() {
        try (var sessionFactory = HibernateUtil.buildSessionFactory();
             Session session = sessionFactory.openSession();) {
            session.beginTransaction();


            User user = null;

            Profile profile = Profile.builder()
                    .street("Romanova st 39")
                    .language("RU")
                    .build();
            profile.setUser(user);

            session.persist(user);

            User user1 = session.get(User.class, user.getId());
            System.out.println();


            session.getTransaction().commit();
        }
    }


    @Test
    void testReflectionApi() {
        User user = null;

        String sql = """
                insert
                into
                %s
                (%s)
                values
                (%s)
                """;

        String table = ofNullable(user.getClass().getAnnotation(Table.class))
                .map(tableAnnotation -> tableAnnotation.schema() + "." + tableAnnotation.name())
                .orElse(user.getClass().getName());

        Field[] declaredFields = user.getClass().getDeclaredFields();

        String columns = Arrays.stream(declaredFields)
                .map(field -> ofNullable(field.getAnnotation(Column.class))
                        .map(Column::name)
                        .orElse(field.getName()))
                .collect(joining(", "));

        String values = Arrays.stream(declaredFields)
                .map(field -> "?")
                .collect(joining(", "));

        System.out.printf((sql) + "%n", table, columns, values);
    }

}