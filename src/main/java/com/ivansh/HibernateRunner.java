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

        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {
            Session session = sessionFactory.getCurrentSession();

            session.beginTransaction();


            session.getTransaction().commit();
        }
    }
}
