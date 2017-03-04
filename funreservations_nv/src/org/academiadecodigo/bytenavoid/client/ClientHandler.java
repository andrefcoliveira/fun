package org.academiadecodigo.bytenavoid.client;

import org.academiadecodigo.bytenavoid.facility.Facility;
import org.academiadecodigo.bytenavoid.facility.FacilityType;
import org.academiadecodigo.bytenavoid.reservation.Reservation;
import org.academiadecodigo.bytenavoid.util.Manager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by codecadet on 01/03/17.
 */
public class ClientHandler {

    private Socket socket;
    private PrintWriter output = null;
    private BufferedReader input = null;
    private Client client;
    private boolean isNewReservation = true;

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
            Manager.addClientToList(client);

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
                    + "(C)ancel a reservation \n" + "E(X)it");

            System.out.println("TESTING");


            answer = input.readLine();
            System.out.println("ANSWER " + answer);

        } catch (IOException e) {
            e.printStackTrace();
        }

        if (answer.equals("X")) {
            output.println("Have a nice day!!! Byeeeee...");
        } else {
            switch (answer) {
                case "R":
                    makeReservation();
                    break;
                case "M":
                    manageReservation();
                    break;
                case "C":
                    cancelReservation();
                    break;
                default:
                    chooseAction();
            }
        }
    }

    private void manageReservation() {
        String answer = "";
        Reservation reservation;
        Facility facility;
        int counter = 1;
        ArrayList<Integer> auxIndexList = new ArrayList<>();

        output.println("Which reservation do you want to modify: \n(M)ain menu");

        for (int i = 0; i < Manager.getReservations().size(); i++) {

            if (Manager.getReservations().get(i).getClient().getName().equals(client.getName())) {
                reservation = Manager.getReservations().get(i);

                auxIndexList.add(i);
                output.println("(" + counter++ + ") reservation to: " + reservation.getFacility().getName() + " on: " +
                        reservation.getCalendar().getTime());

            }
        }
        try {
            answer = input.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (answer.equals("M")) {
            chooseAction();
        } else if (!answer.matches("\\d+")) {
            output.println("Please write a valid answer (a number from the list): ");
            cancelReservation();
        } else if (Integer.parseInt(answer) < 1 && Integer.parseInt(answer) > auxIndexList.size()) {
            output.println("Please write a valid answer (a number from the list): ");
            cancelReservation();
        } else {
            System.out.println(answer);
            int numAnswer = Integer.parseInt(answer) - 1;
            System.out.println(numAnswer);
            facility = Manager.getReservations().get(auxIndexList.get(numAnswer)).getFacility();

            output.println("Please, manage your reservation: \n ");

            output.println("Reservation on "
                    + facility.getName()
                    + " on " + Manager.getReservations().get(auxIndexList.get(numAnswer)).getCalendar().getTime()
                    + " was chosen to be managed.");

            isNewReservation = false;
            Manager.removeReservation(Manager.getReservations().get(auxIndexList.get(numAnswer)));
            chooseMonth(facility);


        }


    }

    private void cancelReservation() {

        String answer = "";
        Reservation reservation;
        int counter = 1;
        ArrayList<Integer> auxIndexList = new ArrayList<>();

        output.println("Which reservation do you want to cancel: \n(M)ain menu");

        for (int i = 0; i < Manager.getReservations().size(); i++) {

            if (Manager.getReservations().get(i).getClient().getName().equals(client.getName())) {
                reservation = Manager.getReservations().get(i);

                auxIndexList.add(i);
                output.println("(" + counter++ + ") reservation to: " + reservation.getFacility().getName() + " on: " +
                        reservation.getCalendar().getTime());

            }
        }
        try {
            answer = input.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (answer.equals("M")) {
            chooseAction();
        } else if (!answer.matches("\\d+")) {
            output.println("Please write a valid answer (a number from the list): ");
            cancelReservation();
        } else if (Integer.parseInt(answer) < 1 && Integer.parseInt(answer) > auxIndexList.size()) {
            output.println("Please write a valid answer (a number from the list): ");
            cancelReservation();
        } else {
            System.out.println(answer);
            int numAnswer = Integer.parseInt(answer) - 1;
            System.out.println(numAnswer);
            output.println("Reservation on "
                    + Manager.getReservations().get(auxIndexList.get(numAnswer)).getFacility().getName()
                    + " on " + Manager.getReservations().get(auxIndexList.get(numAnswer)).getCalendar().getTime()
                    + " was deleted.");
            Manager.removeReservation(Manager.getReservations().get(auxIndexList.get(numAnswer)));
        }

    }

    private void makeReservation() {

        String answer = "";
        output.println("What kind of facility do you want to book? \n" +
                "(M)ain menu\n" +
                "(1) Soccer \n" + "(2) Tennis \n" + "(3) Swimming pool \n" +
                "(4) Volley \n" + "(5) Running \n" + "(6) Sports Space ");

        try {
            answer = input.readLine();

        } catch (IOException e) {
            e.printStackTrace();
        }

        switch (answer) {
            case "M":
                chooseAction();
                break;
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
                break;
            default:
                output.println("Please, enter a valid option.");
                makeReservation();
        }
    }

    private void choose(FacilityType facilityType) {

        String answer1 = "";
        int counter = 1;
        ArrayList<Facility> chosenFacilities = new ArrayList<>();

        output.println("Which facility would you like to book? \n(M)ain menu");

        for (int i = 0; i < Manager.getFacilities().size(); i++) {

            if (Manager.getFacilities().get(i).getType().equals(facilityType)) {
                output.println("(" + (counter++) + ") " + Manager.getFacilities().get(i).getName());
                chosenFacilities.add(Manager.getFacilities().get(i));
            }
        }
        try {
            answer1 = input.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (answer1.equals("M")) {
            chooseAction();
        } else {
            for (int i = 0; i < chosenFacilities.size(); i++) {
                if (Integer.parseInt(answer1) == (i + 1)) {
                    output.println("You have chosen the facility " + chosenFacilities.get(i).getName());
                    chooseMonth(chosenFacilities.get(i));
                    break;
                }
            }
        }
    }

    private void chooseMonth(Facility facility) {

        // TODO: 04/03/17 os meses não estão a bater certo, a opção 3 está a escolher abril
        String month = "";

        output.println("Choose a month: \n" + "(1) January \n" + "(2) February \n" + "(3) March \n"
                + "(4) April \n" + "(5) May \n" + "(6) June \n" + "(7) July \n" + "(8) August \n" + "(9) September \n"
                + "(10) October \n" + "(11) November \n" + "(12) December\n" + "(M)ain menu");

        try {
            month = input.readLine();

            if (month.equals("M")) {
                chooseAction();

            } else if (Integer.parseInt(month) >= 1 && Integer.parseInt(month) <= 12) {
                chooseDay(facility, month);

            } else {
                output.println("Please, choose a valid option");
                chooseMonth(facility);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void chooseDay(Facility facility, String month) {
        String day = "";
        output.println("Choose a day: \n" + "(M)ain menu");

        try {
            day = input.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        switch (month) {
            case "M":
                chooseAction();
                break;
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
                chooseDay(facility, month);
        }
    }

    private void chooseHour(Facility facility, String month, String day) {

        String hour = "";
        boolean[] hours = new boolean[24];
        for (boolean b : hours) {
            b = false;
        }
        output.println("These are the available hours. Enter your option: " + "\n(M)ain menu");
        for (int i = 0; i < Manager.getReservations().size(); i++) {

            if (Manager.getReservations().get(i).getFacility().getName().equals(facility.getName())) {
                if (Manager.getReservations().get(i).getCalendar().get((Calendar.MONTH)) == Integer.parseInt(month)) {
                    if (Manager.getReservations().get(i).getCalendar().get((Calendar.DAY_OF_MONTH)) == Integer.parseInt(day)) {
                        hours[Manager.getReservations().get(i).getCalendar().get(Calendar.HOUR_OF_DAY)] = true;
                    }
                }
            }
        }
        for (int i = 0; i < hours.length; i++) {
            if (!hours[i]) {
                output.print("(" + i + ")h00 | ");
            }
        }
        output.println("");

        try {
            hour = input.readLine();

        } catch (IOException e) {
            e.printStackTrace();
        }

        if (hour.equals("M")) {
            chooseAction();
        } else if (Integer.parseInt(hour) >= 0 && Integer.parseInt(hour) <= 23) {
            if (hours[Integer.parseInt(hour)]) {
                output.println("That time slot is already taken. Please choose another one: ");
                chooseHour(facility, month, day);
            } else {
                createNewReservation(facility, month, day, hour);
            }
        } else {
            output.println("That's not a valid expression");
            chooseHour(facility, month, day);
        }
    }

    private void createNewReservation(Facility facility, String month, String day, String hour) {

        Reservation reservation = new Reservation(client, facility, Integer.parseInt(month), Integer.parseInt(day),
                Integer.parseInt(hour));

        Manager.addReservationToList(reservation);

        if (!isNewReservation) {
            output.println("You have changed your reservation on " + facility.getName() + " to "
                    + reservation.getCalendar().getTime() + "\nPress any key to continue...");
            try {
                input.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            chooseAction();
        } else {
            output.println("You have made a new reservation to: " + facility.getName() + " on: " +
                    reservation.getCalendar().getTime() + "\nPress any key to continue...");
            try {
                input.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            chooseAction();
        }
    }

    private boolean clientExists(String username) {

        for (Client c : Manager.getClientList()) {
            if (c.getUserName().equals(username)) {
                client = c;
                return true;
            }
        }
        return false;
    }
}



