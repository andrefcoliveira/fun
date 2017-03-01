package org.academiadecodigo.bytenavoid.client;

import java.io.Serializable;

/**
 * Created by codecadet on 01/03/17.
 */
public class Client implements Serializable {

    private String name;
    private int ID;
    private String password;
    private String address;
    private int phoneNumber;

    public Client() {

    }

    public void setName(String name) {
        this.name = name;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public int getID() {
        return ID;
    }

    public String getAddress() {
        return address;
    }

    public String getPassword() {
        return password;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }
}
