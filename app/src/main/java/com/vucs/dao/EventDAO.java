package com.vucs.dao;


import com.vucs.model.EventModel;

import java.util.List;

public interface EventDAO {


    public void addEvent(EventModel eventModel);

    public void addEvents(List<EventModel> eventModels);

    public List<EventModel> getAllEvent();
}
