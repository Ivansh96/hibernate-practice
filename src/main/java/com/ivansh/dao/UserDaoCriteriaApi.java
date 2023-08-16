package com.ivansh.dao;

import com.ivansh.dto.CompanyDto;
import com.ivansh.entity.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Session;


import java.util.List;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserDaoCriteriaApi {

    private static final UserDaoCriteriaApi INSTANCE = new UserDaoCriteriaApi();

    public List<User> findAllUsers(Session session) {

        var cb = session.getCriteriaBuilder();

        var criteria = cb.createQuery(User.class);
        var user = criteria.from(User.class);

        criteria.select(user);

        return session.createQuery(criteria)
                .list();
    }

    public List<User> findAllUsersByFirstName(Session session, String firstname) {

        var cb = session.getCriteriaBuilder();

        var criteria = cb.createQuery(User.class);
        var user = criteria.from(User.class);

        criteria.select(user)
                .where(cb.equal(user.get(User_.personalInfo).get(PersonalInfo_.firstname), firstname));

        return session.createQuery(criteria)
                .list();
    }

    public List<User> findLimitedUsersOrderedByBirthday(Session session, int limit) {

        var cb = session.getCriteriaBuilder();

        var criteria = cb.createQuery(User.class);
        var user = criteria.from(User.class);

        criteria.select(user)
                .orderBy(cb.asc(user.get(User_.personalInfo).get(PersonalInfo_.birthdate)));

        return session.createQuery(criteria)
                .setMaxResults(limit)
                .list();
    }

    public List<User> findAllUsersByCompanyName(Session session, String companyName) {

        var cb = session.getCriteriaBuilder();

        var criteria = cb.createQuery(User.class);
        var company = criteria.from(Company.class);
        var users = company.join(Company_.users);

        criteria.select(users)
                .where(cb.equal(company.get(Company_.name), companyName));

        return session.createQuery(criteria)
                .list();


    }

    public List<Payment> findAllPaymentsByCompanyName(Session session, String companyName) {

        var cb = session.getCriteriaBuilder();

        var criteria = cb.createQuery(Payment.class);
        var payment = criteria.from(Payment.class);
        var user = payment.join(Payment_.receiver);
        var company = user.join(User_.company);

        criteria.select(payment)
                .where(cb.equal(company.get(Company_.name), companyName))
                .orderBy(cb.asc(user.get(User_.personalInfo).get(PersonalInfo_.firstname)),
                        cb.asc(payment.get(Payment_.amount)));

        return session.createQuery(criteria)
                .list();


    }

    public Double findAveragePaymentAmountByFirstAndLastNames(Session session, String firstname, String lastname) {

        var cb = session.getCriteriaBuilder();

        var criteria = cb.createQuery(Double.class);
        var payment = criteria.from(Payment.class);
        var user = payment.join(Payment_.receiver);

        criteria.select(cb.avg(payment.get(Payment_.amount))).where(
                cb.equal(user.get(User_.personalInfo).get(PersonalInfo_.firstname), firstname),
                cb.equal(user.get(User_.personalInfo).get(PersonalInfo_.lastname), lastname));

        return session.createQuery(criteria)
                .uniqueResult();
    }

    public List<CompanyDto> findCompanyNamesWithAvgUserPaymentOrderByCompanyName(Session session) {

        var cb = session.getCriteriaBuilder();

        var criteria = cb.createQuery(CompanyDto.class);
        var company = criteria.from(Company.class);
        var user = company.join(Company_.users);
        var payment = user.join(User_.payments);

        criteria.select(cb.construct(CompanyDto.class,
                        company.get(Company_.name),
                        cb.avg(payment.get(Payment_.amount)))
                )

                .groupBy(company.get(Company_.name))
                .orderBy(cb.asc(company.get(Company_.name)));

        return session.createQuery(criteria)
                .list();
    }

    public List<Object[]> findBestAvgPayment(Session session) {

        var cb = session.getCriteriaBuilder();

        var criteria = cb.createQuery(Object[].class);
        var user = criteria.from(User.class);
        var payment = user.join(User_.payments);

        var subQuery = criteria.subquery(Double.class);
        var subPayment = subQuery.from(Payment.class);

        criteria.multiselect(
                        user,
                        cb.avg(payment.get(Payment_.amount)))
                .groupBy(user.get(User_.personalInfo).get(PersonalInfo_.firstname))
                .having(cb.gt(
                        cb.avg(payment.get(Payment_.amount)),
                        subQuery.select(cb.avg(subPayment.get(Payment_.amount)))
                ))
                .orderBy(cb.asc(user.get(User_.personalInfo).get(PersonalInfo_.firstname)));

        return session.createQuery(criteria)
                .list();
    }

    public static UserDaoCriteriaApi getInstance() {
        return INSTANCE;
    }
}
