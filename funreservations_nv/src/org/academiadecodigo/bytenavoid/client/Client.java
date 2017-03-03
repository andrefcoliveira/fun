package org.academiadecodigo.bytenavoid.client;

import java.io.Serializable;

/**
 * Created by codecadet on 01/03/17.
 */
public class Client implements Serializable {

    private String name;
    private String userName;
    private String password;
    private String email;
    private int phoneNumber;

    public Client(String name, String userName, String password, String email, int phoneNumber) {
        this.name = name;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }
}
