package com.ivansh.dao;

import com.ivansh.dto.PaymentFilter;
import com.ivansh.entity.*;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Session;

import java.util.List;

import static com.ivansh.entity.QCompany.company;
import static com.ivansh.entity.QPayment.payment;
import static com.ivansh.entity.QUser.user;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserDaoQueryDsl {

    private static final UserDaoQueryDsl INSTANCE = new UserDaoQueryDsl();

    public List<User> findAllUsers(Session session) {

        return new JPAQuery<User>(session)
                .select(user)
                .from(user)
                .fetch();
    }

    public List<User> findAllUsersByFirstName(Session session, String firstname) {

        return new JPAQuery<User>(session)
                .select(user)
                .from(user)
                .where(user.personalInfo.firstname.eq(firstname))
                .fetch();
    }

    public List<User> findLimitedUsersOrderedByBirthday(Session session, int limit) {

        return new JPAQuery<User>(session)
                .select(user)
                .from(user)
                .orderBy(user.personalInfo.birthdate.asc())
                .limit(limit)
                .fetch();
    }

    public List<User> findAllUsersByCompanyName(Session session, String companyName) {

        return new JPAQuery<User>(session)
                .select(user)
                .from(company)
                .join(company.users, user)
                .where(company.name.eq(companyName))
                .fetch();
    }

    public List<Payment> findAllPaymentsByCompanyName(Session session, String companyName) {

        return new JPAQuery<Payment>(session)
                .select(payment)
                .from(payment)
                .join(payment.receiver, user)
                .join(user.company, company)
                .where(company.name.eq(companyName))
                .orderBy(user.personalInfo.firstname.asc(), payment.amount.asc())
                .fetch();


    }

    public Double findAveragePaymentAmountByFirstAndLastNames(Session session, PaymentFilter filter) {

        var predicate = QPredicate.builder()
                .add(filter.getFirstname(), user.personalInfo.firstname::eq)
                .add(filter.getLastname(), user.personalInfo.lastname::eq)
                .buildAnd();

        return new JPAQuery<Double>(session)
                .select(payment.amount.avg())
                .from(payment)
                .join(payment.receiver, user)
                .where(predicate)
                .fetchOne();
    }

    public List<Tuple> findCompanyNamesWithAvgUserPaymentOrderByCompanyName(Session session) {

        return new JPAQuery<Tuple>(session)
                .select(company.name, payment.amount.avg())
                .from(company)
                .join(company.users, user)
                .join(user.payments, payment)
                .groupBy(company.name)
                .orderBy(company.name.asc())
                .fetch();

    }

    public List<Tuple> findBiggestAvgPayment(Session session) {

        return new JPAQuery<Tuple>(session)
                .select(user, payment.amount.avg())
                .from(user)
                .join(user.payments, payment)
                .groupBy(user.id)
                .having(payment.amount.avg().gt(new JPAQuery<Double>(session)
                        .select(payment.amount.avg())
                        .from(payment)))
                .orderBy(user.personalInfo.firstname.asc())
                .fetch();
    }

    public static UserDaoQueryDsl getInstance() {
        return INSTANCE;
    }
}
