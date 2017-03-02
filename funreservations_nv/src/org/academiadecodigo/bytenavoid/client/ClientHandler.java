package org.academiadecodigo.bytenavoid.client;

import org.academiadecodigo.bytenavoid.util.Manager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by codecadet on 01/03/17.
 */
public class ClientHandler {

    private Socket socket;
    private PrintWriter output = null;
    private BufferedReader input = null;

    public ClientHandler(Socket socket) {
        this.socket = socket;
        try {
            output = new PrintWriter(socket.getOutputStream(), true);
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void startClientAccess() {

        String answer = "";

        output.println("(L)og In or (S)ign Up? ");
        try {
            answer = input.readLine().toUpperCase();
        } catch (IOException e) {
            e.printStackTrace();
        }

        switch (answer) {
            case "L":
                logInClient("");
                break;

            case "S":
                signUpClient();
                break;

            default:
                output.println("Please, choose a valid option. ");
                startClientAccess();

        }
    }


    public void signUpClient() {
        Client client = null;
        String name = "";
        String userName = "";
        String password = "";
        String email = "";
        int phoneNumber;

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

            client = new Client(name, userName, password, email, phoneNumber);
            Manager.getClientList().add(client);


        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private void logInClient(String username) {

        if(username.equals("")) {
            output.println("Please, enter your username: ");
            try {
                username = input.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


        if (clientExists(username)) {

            //TODO: Verificar lista vazia (validacao).
/*            while (!passwordCorrect(answer)) {

                output.println("Please, enter your password: ");
                try {
                    answer = input.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }*/
            System.out.println("Client Exists");
            chooseAction();

        }

        output.println("This username does not exist. Please enter a valid one or (S)ign Up.");
        String newUserName="";

        try {
            newUserName = input.readLine();

        } catch (IOException e) {
            e.printStackTrace();
        }

        switch (username) {
            case "S":
                signUpClient();
                break;
            default:
                logInClient(newUserName);

        }


    }

    private void chooseAction() {
        String answer = "";
        output.println("What do you want to do? \n" +
                "Make a (R)eservation \n" + "(M)anage a reservation \n"
                + "(C)ancell a reservation ");

        switch (answer) {
            case "R":
                makeReservation();
          /*      break;
            case "M":
                manageReservation();
                break;
            case "C":
                cancellReservation();
                break;
            default:
                errorMessage();
        }*/
        }
    }

    private void makeReservation() {
        String answer = "";
        output.println("What kind of facility do you want to book? \n" +
                "(1) Soccer \n" + "(2) Tennis \n" + "(3) Swimming pool \n" +
                "(4) Volley \n" + "(5) Running \n" + "(6) Sports Space ");

     /*   switch (answer) {
            case "1":
                choose(EntityType.SOCCER);
        }
*/    }


    private boolean clientExists(String username) {
        for (Client c : Manager.getClientList()) {

            if (c.getUserName().equals(username)) {
                return true;
            }
        }
        return false;

    }

}



