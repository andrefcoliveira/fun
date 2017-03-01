package org.academiadecodigo.bytenavoid.client;

import java.io.*;
import java.net.Socket;

/**
 * Created by codecadet on 01/03/17.
 */
public class Client implements Serializable {

    private String name;
    private String userName;
    private String password;
    private String email;
    private int phoneNumber;
    private PrintWriter output = null;
    private BufferedReader input = null;

    public Client(Socket socket) {
        try {
            output = new PrintWriter(socket.getOutputStream(), true);
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void init() {

        try {
            output.println("Please, enter your name: ");
            name = input.readLine();

            output.println("Please, choose an username: ");
            userName = input.readLine();

            output.println("Please, choose a password: ");
            password = input.readLine();

            output.println("Please, enter your email: ");
            email = input.readLine();

            output.println("Please, enter your phone number: ");
            phoneNumber = Integer.parseInt(input.readLine());

            output.println("Welcome to the amazing booking world!");


        } catch (IOException e) {
            e.printStackTrace();
        }

        output.println("Please, enter your name: ");
        try {
            name = input.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }


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
