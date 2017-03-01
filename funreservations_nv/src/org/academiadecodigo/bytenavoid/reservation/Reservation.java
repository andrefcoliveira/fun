package org.academiadecodigo.bytenavoid.reservation;

import org.academiadecodigo.bytenavoid.client.Client;
import org.academiadecodigo.bytenavoid.facility.Facility;
import java.io.Serializable;
import java.util.GregorianCalendar;


/**
 * Created by codecadet on 01/03/17.
 */
public class Reservation implements Serializable{

    private Client client;
    private Facility facility;
    private GregorianCalendar calendar;


    public Reservation(Client client, Facility facility, int month, int day, int hour) {
        this.client = client;
        this.facility = facility;
        calendar = new GregorianCalendar(2017, month, day, hour, 0);
    }

    public GregorianCalendar getCalendar() {
        return calendar;
    }

    public Client getClient() {
        return client;
    }

    public Facility getFacility() {
        return facility;
    }
}

