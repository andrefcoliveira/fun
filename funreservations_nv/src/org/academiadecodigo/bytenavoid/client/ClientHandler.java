package org.academiadecodigo.bytenavoid.client;

import org.academiadecodigo.bytenavoid.facility.Facility;
import org.academiadecodigo.bytenavoid.facility.FacilityType;
import org.academiadecodigo.bytenavoid.util.Manager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

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

        if (username.equals("")) {
            output.println("Please, enter your username: ");
            try {
                username = input.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


        if (clientExists(username)) {
            System.out.println("Client Exists");
            chooseAction();

        } else {

            output.println("This username does not exist. Please enter a valid one or (S)ign Up.");
            String newUserName = "";

            try {
                newUserName = input.readLine();

            } catch (IOException e) {
                e.printStackTrace();
            }

            switch (newUserName) {
                case "S":
                    signUpClient();
                    break;
                default:
                    logInClient(newUserName);

            }

        }
    }

    private void chooseAction() {
        String answer = "";
        try {
            output.println("What do you want to do? \n" +
                    "Make a (R)eservation \n" + "(M)anage a reservation \n"
                    + "(C)ancell a reservation ");

            System.out.println("TESTING");


            answer = input.readLine();
            System.out.println("ANSWER " + answer);

        } catch (IOException e) {
            e.printStackTrace();
        }

        switch (answer) {
            case "R":
                System.out.println("DENTRO DO R ");
                makeReservation();
                break;
         /*   case "M":
                manageReservation();
                break;
            case "C":
                cancellReservation();
                break;
                */
            default:
                chooseAction();
        }
    }

    private void makeReservation() {
        String answer = "";
        output.println("What kind of facility do you want to book? \n" +
                "(1) Soccer \n" + "(2) Tennis \n" + "(3) Swimming pool \n" +
                "(4) Volley \n" + "(5) Running \n" + "(6) Sports Space ");

        try {
            answer = input.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        switch (answer) {
            case "1":
                choose(FacilityType.SOCCER);
                break;
            case "2":
                choose(FacilityType.TENIS);
                break;
            case "3":
                choose(FacilityType.SWIMMINGPOOL);
                break;
            case "4":
                choose(FacilityType.VOLEY);
                break;
            case "5":
                choose(FacilityType.RUNNING);
                break;
            case "6":
                choose(FacilityType.SPORTSSPACE);
            default:
                output.println("Please, enter a valid option.");
                makeReservation();
        }

    }

    private void choose(FacilityType facilityType) {
        String answer1 = "";
        int counter = 1;
        ArrayList<Integer> facilityPos = new ArrayList<>();

        output.println("Which facility would you like to book?");


        System.out.println("ANTES DO FOR DO CHOOSE");

        System.out.println("TAMANHO DA LISTA " + Manager.getFacilities().size());


        for (int i = 0; i < Manager.getFacilities().size(); i++) {
            System.out.println(Manager.getFacilities().get(i).getName());

            if (Manager.getFacilities().get(i).getType().equals(facilityType)) {
                output.println("(" + (counter++) + ") " + Manager.getFacilities().get(i).getName());
                System.out.println("DENTRO DO FOR DO CHOOSE");
                facilityPos.add(i);
            }
        }
        try {
            answer1 = input.readLine();
            System.out.println("Answer " + answer1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < facilityPos.size(); i++) {
            if (Integer.parseInt(answer1) == (i + 1)) {
                output.println("You have chosen the facility " + Manager.getFacilities().get(facilityPos.get(i)).getName());
                chooseMonth(Manager.getFacilities().get(facilityPos.get(i)));
                break;
            }

        }


    }

    private void chooseMonth(Facility facility) {
        String month = "";

        output.println("Choose a month: \n" + "(1) January \n" + "(2) February \n" + "(3) March \n"
                + "(4) April \n" + "(5) May \n" + "(6) June \n" + "(7) July \n" + "(8) August \n" + "(9) September \n"
                + "(10) October \n" + "(11) November \n" + "(12) December \n");

        try {
            month = input.readLine();

            if (Integer.parseInt(month) >= 1 && Integer.parseInt(month) <= 12) {
                chooseDay(facility, month);

            }
            output.println("Please, choose a valid option");
            chooseMonth(facility);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void chooseDay(Facility facility, String month) {
        String day = "";
        output.println("Choose a day: ");

        try {
            day = input.readLine();
        } catch (IOException e) {
            e.printStackTrace();

        }

        switch (month) {
            case "1":
            case "3":
            case "5":
            case "7":
            case "8":
            case "10":
            case "12":
                if (Integer.parseInt(day) >= 1 && Integer.parseInt(day) <= 31) {
                    chooseHour(facility, month, day);
                }
                break;

            case "2":
                if (Integer.parseInt(day) >= 1 && Integer.parseInt(day) <= 28) {
                    chooseHour(facility, month, day);
                }
                break;
            case "4":
            case "6":
            case "9":
            case "11":
                if (Integer.parseInt(day) >= 1 && Integer.parseInt(day) <= 30) {
                    chooseHour(facility, month, day);
                }
                break;

                default:
                    output.println("Please, enter a valid option. ");
                    chooseDay(facility,month);
        }


    }

    private void chooseHour(Facility facility, String month, String day) {
        String hour = "";

        output.println("We already have this hours reserved for this day. Please ");

    }


    private boolean clientExists(String username) {
        for (Client c : Manager.getClientList()) {

            if (c.getUserName().equals(username)) {
                return true;
            }
        }
        return false;

    }

}



