package org.academiadecodigo.bytenavoid.facility;

import org.academiadecodigo.bytenavoid.reservation.Reservation;
import org.academiadecodigo.bytenavoid.util.Manager;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * Created by codecadet on 01/03/17.
 */
public class Facility implements Serializable {

    private int ID;
    private String name;
    private FacilityType type;
    private String info;
    private String address;
    private int phone;
    private String pw;

    public Facility(int ID, String pw, FacilityType type, String name, String address, int phone, String info){

        this.ID = ID;
        this.pw = pw;
        this.type = type;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.info = info;

    }

    public FacilityType getType() {
        return type;
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public int getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getInfo() {
        return info;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
        Manager.saveFacilities();
    }

    public void setName(String name) {
        this.name = name;
        Manager.saveFacilities();
    }

    public void setInfo(String info) {
        this.info = info;
        Manager.saveFacilities();
    }

    public void setAddress(String address) {
        this.address = address;
        Manager.saveFacilities();
    }

    public void setPhone(int phone) {
        this.phone = phone;
        Manager.saveFacilities();
    }
    //lkzsgh
}
