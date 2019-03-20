package com.whizdm.dao;

import com.opencsv.CSVWriter;
import com.whizdm.model.Events;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Repository
@Transactional
public class EventsDAO  {
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

    public String execManual(Date date, Date date1, String userId) {
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
        return store;
    }
    public void writeToCsv(List<Events> eventsList)
    {
        String header="event_type,event_timestamp,attributes,user_id,session_id,device_model";

        Iterator<Events> itr=eventsList.listIterator();
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(new File("/Users/moneyview/Desktop/output.csv")));
            writer.writeNext(header.split(","));
            while (itr.hasNext()) {
                Events e = itr.next();
                String eventType = e.getEventType();
                String attributes = e.getAttributes();
                String userId = e.getUserId();
                Date eventTimestamp = e.getEventTimestamp();
                String deviceModel = e.getDeviceModel();
                String sessionId = e.getSessionId();
                ArrayList<String> arr = new ArrayList<>();
                arr.add(eventType);
                arr.add(eventTimestamp.toString());
                arr.add(attributes);
                arr.add(userId);
                arr.add(sessionId);
                arr.add(deviceModel);
                String[] array = arr.toArray(new String[0]);
                writer.writeNext(array);

            }
            writer.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public List<Events> getFilteredEventsByDateAndUserId(Date startDate, Date endDate, String userIds) {
        Session s = getSession();
        Criteria c = s.createCriteria(Events.class);
        c.add(Restrictions.between("eventTimestamp", startDate, endDate));
        c.add(Restrictions.in("userId", userIds.split(",")));
        return c.list();
    }

    @SuppressWarnings("unchecked")
    public List<Events> getAllEvents() {
        return getSession().createQuery("from Events").list();
    }

}