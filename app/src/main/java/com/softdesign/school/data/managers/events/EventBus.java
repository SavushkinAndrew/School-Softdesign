package com.softdesign.school.data.managers.events;


import com.squareup.otto.Bus;

public class EventBus  extends Bus {
    private static EventBus bus = new EventBus();

    public static EventBus getInstance() {
        return bus;
    }

    private EventBus() {
    }
}
