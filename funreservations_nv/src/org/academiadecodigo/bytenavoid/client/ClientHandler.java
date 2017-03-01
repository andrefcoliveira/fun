package org.academiadecodigo.bytenavoid.client;

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
            answer = input.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        switch (answer) {
            case "L":

                logInClient();
                break;

            case "S":
                signUpClient();
                break;

           /* default:
                errorMessage();

*/
        }
    }

    private void signUpClient() {
        Client client = new Client(socket);
        client.init();

       /* private String name;
        private int ID;
        private String password;
        private String address;
        private int phoneNumber;
    */
    }

    private void logInClient() {
        String answer = "";

        output.println("Please, enter your name: ");
        try {
            answer = input.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }


        /*if (clientExists()) {

            while (!passwordCorrect(answer)) {

                output.println("Please, enter your password: ");
                try {
                    answer = input.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }*/
      /*      chooseAction();
        }


    private void chooseAction() {
        String answer = "";
        output.println("What do you want to do? \n " +
                "Make a (R)eservation \n" + "(M)anage a reservation \n"
                + "(C)ancell a reservation ");

        switch (answer) {
            case "R":
                makeReservation();
                break;
            case "M":
                manageReservation();
                break;
            case "C":
                cancellReservation();
                break;
            default:
                errorMessage();
        }
    }
    private void makeReservation() {
        String answer = "";
        output.println("What kind of facility do you want to book? \n" +
                "(1) Soccer \n" + "(2) Tennis \n" + "(3) Swimming pool \n" +
                "(4) Volley \n" + "(5) Running \n" + "(6) Sports Space ");

        switch (answer) {
            case "1":
                choose(EntityType.SOCCER);
        }

    }*/

    }
}


