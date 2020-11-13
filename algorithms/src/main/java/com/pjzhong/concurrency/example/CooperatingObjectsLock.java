package com.pjzhong.concurrency.example;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

class Taxi {
    private Point location, desctination;
    private final Dispatcher dispatcher;

    public Taxi(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    public synchronized Point getLocation() {
        return location;
    }

    public synchronized void setLocation(Point location) {
        this.location = location;
        if(location.equals(desctination)) {
            dispatcher.notifyAvailable(this);
        }
    }
}

class Dispatcher {

    private final Set<Taxi> taxis;
    private final Set<Taxi> availableTaxis;

    public Dispatcher() {
        this.taxis = new HashSet<>();
        this.availableTaxis = new HashSet<>();
    }

    public synchronized void notifyAvailable(Taxi taxi) {
        availableTaxis.add(taxi);
    }

    public synchronized Image getImage() {
        Image image = new Image();
        for(Taxi t : taxis) {
            image.drawMarker(t.getLocation());
        }
        return image;
    }

    private static class Image {
        public void drawMarker(Point point) {}
    }
}



public class CooperatingObjectsLock {
}
