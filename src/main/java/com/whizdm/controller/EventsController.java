package com.whizdm.controller;


import java.text.ParseException;
import java.util.List;
import com.whizdm.Action;
import com.whizdm.dao.EventsDAO;
import com.whizdm.model.Events;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/events")
public class EventsController {
    @Autowired
    private EventsDAO eventsDAO;

    @Autowired
    private Action action;

    @RequestMapping(value = "/allEvents")
    @ResponseBody
    public List<Events> getAllEvents() {
        try {
            return eventsDAO.getAllEvents();
        } catch (Exception ex) {
            System.out.print("Catch block");
            ex.printStackTrace();
            return null;
        }
    }
    @RequestMapping(value = "/filterEvents")
    @ResponseBody
    public String getFilteredEventsByDateandUserId(String startDate,String endDate,String userIds) throws ParseException
    {
        /*
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse(startDate);
        Date date1=sdf.parse(endDate);*/
        try {
            action.writeToCsv(eventsDAO.getFilteredEventsByDateAndUserId(startDate, endDate, userIds));
            return "Success";

        }catch (Exception e)
        {
            e.printStackTrace();
            return "Failure";
        }

    }
}
