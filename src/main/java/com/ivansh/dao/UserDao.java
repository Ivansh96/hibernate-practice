package com.ivansh.dao;

import com.ivansh.entity.Payment;
import com.ivansh.entity.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Session;

;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserDao {

    private static final UserDao INSTANCE = new UserDao();

    public List<User> findAllUsers(Session session) {
        return session.createQuery("select u from User u", User.class)
                .list();
    }

    public List<User> findAllUsersByFirstName(Session session, String firstname) {
        return session.createQuery("select u from User u " +
                        "where u.personalInfo.firstname = :firstname", User.class)
                .setParameter("firstname", firstname)
                .list();
    }

    public List<User> findLimitedUsersOrderedByBirthday(Session session, int limit) {
        return session.createQuery("select u from User u " +
                        "order by u.personalInfo.birthdate asc ", User.class)
                .setMaxResults(limit)
                .list();
    }

    public List<User> findAllUsersByCompanyName(Session session, String companyName) {
        return session.createQuery("select u from User u " +
                        "join u.company c where c.name = :companyName", User.class)
                .setParameter("companyName", companyName)
                .list();
    }

    public List<Payment> findAllPaymentsByCompanyName(Session session, String companyName) {
        return session.createQuery("select p from Payment p " +
                        "join p.receiver u " +
                        "join u.company c " +
                        "where c.name = :companyName " +
                        "order by u.personalInfo.firstname, p.amount ", Payment.class)
                .setParameter("companyName", companyName)
                .list();
    }

    public Double findAveragePaymentAmountByFirstAndLastNames(Session session, String firstname, String lastname) {
        return session.createQuery("select avg (p.amount) from Payment p " +
                        "join p.receiver u " +
                        "where u.personalInfo.firstname = :firstname " +
                        "and u.personalInfo.lastname = :lastname ", Double.class)
                .setParameter("firstname", firstname)
                .setParameter("lastname", lastname)
                .uniqueResult();
    }

    public List<Object[]> findCompanyNamesWithAvgUserPaymentOrderByCompanyName(Session session) {
        return session.createQuery("select c.name, avg (p.amount) from Company c " +
                        "join c.users u " +
                        "join u.payments p " +
                        "group by c.name " +
                        "order by c.name ", Object[].class)
                .list();
    }

    public List<Object[]> findBestAvgPayment(Session session) {
        return session.createQuery("select u, avg (p.amount) from User u " +
                        "join u.payments p " +
                        "group by u.personalInfo.firstname " +
                        "having avg (p.amount) > (select avg (p.amount) from Payment p) " +
                        "order by u.personalInfo.firstname", Object[].class)
                .list();
    }

    public static UserDao getInstance() {
        return INSTANCE;
    }
}
