package com.whizdm.dao;

import com.whizdm.model.Events;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.*;

@Repository
@Transactional
public class EventsDAO {
    @Autowired
    private SessionFactory sessionFactory;
    private String store = "";

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    public String saveEvents(Events events) {
        Long isSuccess = (Long) getSession().save(events);
        if (isSuccess >= 1) {
            return "Success";
        } else {
            return "Error while Saving Person";
        }

    }

    public boolean delete(Events events) {
        getSession().delete(events);
        return true;
    }

    public String execManual(String date, String date1, String userId) {
        Query query = getSession().createSQLQuery(
                "select * from aws_events_loans_v2 where event_timestamp between :date and :date1 and user_id in(:userId)  limit 20")
                .addEntity(Events.class)
                .setParameter("date", date)
                .setParameter("date1", date1)
                .setParameter("userId", userId);

        List result = query.list();
        Iterator itr = result.listIterator();
        while (itr.hasNext()) {
            store = store + itr.next().toString();
        }
        Criteria criteria = getSession().createCriteria(Events.class);

        List list = criteria.list();
        return store;
    }


    public List<Object[]> getFilteredEventsByDateAndUserId(String startDate, String endDate, String userIds) {
        Session s = getSession();
        Criteria c = s.createCriteria(Events.class);
        Projection projection = Projections.property("eventType");
        Projection projection2 = Projections.property("eventTimestamp");
        Projection projection3 = Projections.property("userId");
        Projection projection4 = Projections.property("attributes");
        Projection projection5 = Projections.property("sessionId");

        ProjectionList pList = Projections.projectionList();
        pList.add(projection);
        pList.add(projection2);
        pList.add(projection3);
        pList.add(projection4);
        pList.add(projection5);
        c.setProjection(pList);
        Criterion cr1 = Restrictions.between("eventTimestamp", startDate, endDate);
        Criterion cr2 = Restrictions.in("userId", userIds.split(","));

        LogicalExpression andExp = Restrictions.and(cr1, cr2);
        c.add(andExp);

        return c.list();
    }



    @SuppressWarnings("unchecked")
    public List<Events> getAllEvents() {
        return getSession().createQuery("from Events").list();
    }

}