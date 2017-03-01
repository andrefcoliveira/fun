package org.academiadecodigo.bytenavoid.facility;

import org.academiadecodigo.bytenavoid.reservation.Reservation;

import java.util.LinkedList;

/**
 * Created by codecadet on 01/03/17.
 */
public class Facility {

    private int ID;
    private String name;
    private LinkedList<Reservation> reservationList;
    private FacilityType type;
    private String info;
    private String address;
    private int phone;


    public Facility(int ID, FacilityType type, String name, String address, int phone, String info){

        this.ID = ID;
        this.type = type;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.info = info;

    }
}
