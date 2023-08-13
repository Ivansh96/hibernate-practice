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


            User user = User.builder()
                    .username("test9@gmail.com")
                    .build();

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
        User user = User.builder()
                .username("ivansh@gmail.com")
                .personalInfo(PersonalInfo.builder()
                        .firstname("Ivan")
                        .lastname("Shavshin")
                        .birthdate(LocalDate.of(1996, 6, 17))
                        .build())
                .build();

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