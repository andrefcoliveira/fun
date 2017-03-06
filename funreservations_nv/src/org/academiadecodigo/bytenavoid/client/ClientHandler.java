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

/**
 * Class responsible for performing actions concerning the client.
 */
public class ClientHandler {

    private Socket socket;
    private PrintWriter output = null;
    private BufferedReader input = null;
    private Client client;
    private boolean isNewReservation = true;
    private int passAttempt = 0;

    public ClientHandler(Socket socket) {
        this.socket = socket;

        try {
            output = new PrintWriter(socket.getOutputStream(), true);
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method asks the client to choose between (L)og in, if it has already been registered, or (S)ign Up, if
     * it is a new client.
     * Log In {@link #logInClient(String)}
     * Sign Up {@link #signUpClient()}
     */
    public void startClientAccess() {

        passAttempt = 0;
        String answer = "";

        output.println("+-----------------------------------------------------------------------------+\n" +
                "|                            (L)og In | (S)ign Up                             |\n" +
                "+-----------------------------------------------------------------------------+");

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
                output.println("*** Please, choose a valid option. *** ");
                startClientAccess();
        }
    }

    /**
     * This method logs the Client onto the application.
     *
     * @param username is used to check if the username is valid and exists on the database.
     * @see #clientExists(String).
     * If the username exists, it asks for the password.
     * @see #askPassword()
     */
    private void logInClient(String username) {

        if (username.equals("")) {
            output.println("+-----------------------------------------------------------------------------+\n" +
                    "|                            Enter your username:                             |\n" +
                    "+-----------------------------------------------------------------------------+");

            try {
                username = input.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (clientExists(username)) {
            System.out.println("Client Exists");
            askPassword();

        } else {

            output.println("*** This username does not exist. Please enter a valid one or (S)ign Up. *** ");
            String newUserName = "";

            try {
                newUserName = input.readLine();

            } catch (IOException e) {
                e.printStackTrace();
            }

            switch (newUserName) {
                case "S":
                case "s":
                    signUpClient();
                    break;
                default:
                    logInClient(newUserName);
            }
        }
    }

    /**
     * This method iterates over the Client List and checks if there's a client with that username.
     *
     * @param username
     * @returns true if there is that username and false if it doesn't exist.
     */
    private boolean clientExists(String username) {

        for (Client c : Manager.getClientList()) {
            if (c.getUserName().equals(username)) {
                client = c;
                return true;
            }
        }
        return false;
    }

    /**
     * This method gets the password input from the Client and compares it to the password that the client
     * had set on its register.
     * The client has the maximum password attempts of 3. If it fails the 3 times, it is redirected
     * to the {@link #startClientAccess()} method.
     * If the password is correct it is redirected to the {@link #chooseAction()} method.
     */
    private void askPassword() {

        try {
            output.println("+-----------------------------------------------------------------------------+\n" +
                    "|                            Enter your password:                             |\n" +
                    "+-----------------------------------------------------------------------------+");

            String pass = input.readLine();

            if (!client.getPassword().equals(pass)) {
                passAttempt++;


                if (passAttempt >= 3) {
                    output.println("*** You've reached the attempts limit. ***");
                    client = null;
                    passAttempt = 0;
                    startClientAccess();
                } else {
                    output.println("\n! Wrong password. Please try again. !\n");
                    askPassword();
                }

            } else {
                output.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n" +
                        "+                          Welcome to FUNdão Reservations                     +\n" +
                        "+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

                chooseAction();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Asks the client data, such as Name, Username - verifies if the username is unique -, Password, Email and
     * Phone number.
     * Instantiates a new Client and add it to the CopyOnWriteArrayList<Client> clientList.
     * Then, it is redirected to the {@link #chooseAction()} method.
     */
    public void signUpClient() {

        Client client = null;
        String name = "";
        String userName = "";
        String password = "";
        String email = "";

        int phoneNumber;

        try {

            output.println("+-----------------------------------------------------------------------------+\n" +
                    "|                              Enter your name:                               |\n" +
                    "+-----------------------------------------------------------------------------+");
            name = input.readLine();

            output.println("+-----------------------------------------------------------------------------+\n" +
                    "|                            Choose an Username:                              |\n" +
                    "+-----------------------------------------------------------------------------+");
            userName = input.readLine();

            while (clientExists(userName)) {
                output.println("*** This username has already been taken. Please choose another one: ***");
                userName = input.readLine();
            }

            output.println("+-----------------------------------------------------------------------------+\n" +
                    "|                             Choose a password:                              |\n" +
                    "+-----------------------------------------------------------------------------+");
            password = input.readLine();

            output.println("+-----------------------------------------------------------------------------+\n" +
                    "|                              Enter an email:                                |\n" +
                    "+-----------------------------------------------------------------------------+");
            email = input.readLine();

            output.println("+-----------------------------------------------------------------------------+\n" +
                    "|                              Enter a phone number:                          |\n" +
                    "+-----------------------------------------------------------------------------+");
            phoneNumber = Integer.parseInt(input.readLine());

            output.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n" +
                    "+                          Welcome to FUNdão Reservations                     +\n" +
                    "+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

            client = new Client(name, userName, password, email, phoneNumber);
            Manager.addClientToList(client);
            this.client = client;
            chooseAction();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This methods gives some options to the client as:
     * {@link #makeReservation()}
     * {@link #manageReservation(ArrayList)}
     * {@link #cancelReservation(ArrayList)}
     * or exiting the program, based on its inputs.
     */
    private void chooseAction() {

        String answer = "";

        try {
            output.println("+-----------------------------------------------------------------------------+\n" +
                    "|                            Choose an option:                                |\n" +
                    "+-----------------------------------------------------------------------------+\n" +
                    "- Make a (R)eservation \n" + "- (M)anage a reservation \n"
                    + "- (C)ancel a reservation \n" + "- E(X)it\n");

            answer = input.readLine().toUpperCase();

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
                    printReservations(false);
                    break;
                case "C":
                    printReservations(true);
                    break;
                default:
                    chooseAction();
            }
        }
    }

    /**
     * This method gives the facility options so the client can choose its preferred facility to book
     * {@link #choose(FacilityType)} or
     * to return to the Main menu {@link #chooseAction()},
     * based on its inputs.
     */
    private void makeReservation() {

        String answer = "";
        output.println("+-----------------------------------------------------------------------------+\n" +
                "|               Choose the kind of facility you want to book:                 |\n" +
                "+-----------------------------------------------------------------------------+\n" +
                "- (1) Soccer \n" + "- (2) Tennis \n" + "- (3) Swimming pool \n" +
                "- (4) Andebol \n" + "- (5) Basket \n" + "- (6) Squash \n" + "- (7) BTT \n" + "- (8) Escalada \n" + "- Back to (M)ain menu\n");

        try {
            answer = input.readLine();

        } catch (IOException e) {
            e.printStackTrace();
        }

        switch (answer) {
            case "M":
            case "m":
                chooseAction();
                break;
            case "1":
                choose(FacilityType.SOCCER);
                break;
            case "2":
                choose(FacilityType.TENNIS);
                break;
            case "3":
                choose(FacilityType.SWIMMINGPOOL);
                break;
            case "4":
                choose(FacilityType.ANDEBOL);
                break;
            case "5":
                choose(FacilityType.BASKET);
                break;
            case "6":
                choose(FacilityType.SQUASH);
                break;
            case "7":
                choose(FacilityType.BTT);
                break;
            case "8":
                choose(FacilityType.ESCALADA);
                break;
            default:
                output.println("*** Please, enter a valid option. ***");
                makeReservation();
        }
    }

    /**
     * This method lets the client edit its reservations. It shows the reservations under
     * the client username and lets it choose one to be managed.
     * The client must choose a new month {@link #chooseMonth(Facility)},
     * day {@link #chooseDay(Facility, String)}, and hour {@link #chooseHour(Facility, String, String)}
     * for its reservation.
     *
     * @param auxIndexList is the list of the reservations that the client has.
     */
    private void manageReservation(ArrayList<Integer> auxIndexList) {

        String answer = "";
        Facility facility;

        try {
            answer = input.readLine().toUpperCase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (answer.equals("M")) {
            chooseAction();

        } else if (answer.matches("[0-9]+")) {
            System.out.println(answer);
            int numAnswer = Integer.parseInt(answer);
            if (numAnswer < 1 || numAnswer > auxIndexList.size()) {
                output.println("*** Please write a valid answer (a number from the list): ***");
                printReservations(false);
            } else {
                numAnswer--;
                facility = Manager.getReservations().get(auxIndexList.get(numAnswer)).getFacility();

                output.println("*** Reservation on "
                        + facility.getName()
                        + " on " + Manager.getReservations().get(auxIndexList.get(numAnswer)).getCalendar().getTime()
                        + " was chosen to be managed. ***");

                isNewReservation = false;
                Manager.removeReservation(Manager.getReservations().get(auxIndexList.get(numAnswer)));
                chooseMonth(facility);
            }


        } else {
            output.println("*** Please write a valid answer (a number from the list): ***");
            printReservations(false);
        }

    }

    /**
     * This is an auxiliary method to show the right message weather the client is managing a
     * reservation {@link #makeReservation()}
     * or cancelling it {@link #cancelReservation(ArrayList)}
     *
     * @param isCancelReservation is to check if it is to cancel or to manage the reservation.
     */
    private void printReservations(boolean isCancelReservation) {
        Reservation reservation;
        int counter = 1;
        ArrayList<Integer> auxIndexList = new ArrayList<>();

        if (isCancelReservation) {
            output.println("+-----------------------------------------------------------------------------+\n" +
                    "|                     Choose a reservation to cancel:                         |\n" +
                    "+-----------------------------------------------------------------------------+");
        } else {
            output.println("+-----------------------------------------------------------------------------+\n" +
                    "|               Choose the reservation you want to modify:                    |\n" +
                    "+-----------------------------------------------------------------------------+");
        }

        for (int i = 0; i < Manager.getReservations().size(); i++) {

            if (Manager.getReservations().get(i).getClient().getName().equals(client.getName())) {
                reservation = Manager.getReservations().get(i);

                auxIndexList.add(i);
                output.println("(" + counter++ + ") Reservation to: " + reservation.getFacility().getName() + " on: " +
                        reservation.getCalendar().getTime());

            }
        }
        output.println(" - Back to (M)ain menu");

        if (isCancelReservation) {
            cancelReservation(auxIndexList);
        } else {
            manageReservation(auxIndexList);
        }


    }


    /**
     * This method is used to cancel the reservation that is under the client.
     *
     * @param auxIndexList is the list of the reservations that the client has.
     */
    private void cancelReservation(ArrayList<Integer> auxIndexList) {

        String answer = "";

        try {
            answer = input.readLine().toUpperCase();

        } catch (IOException e) {
            e.printStackTrace();
        }

        if (answer.equals("M")) {
            chooseAction();

        } else if (!answer.matches("\\d+")) {
            output.println("*** Please write a valid answer (a number from the list): ***");
            printReservations(true);

        } else if (Integer.parseInt(answer) < 1 || Integer.parseInt(answer) > auxIndexList.size()) {
            output.println("*** Please write a valid answer (a number from the list): ***");
            printReservations(true);

        } else {
            System.out.println(answer);
            int numAnswer = Integer.parseInt(answer) - 1;
            System.out.println(numAnswer);
            output.println("*** Reservation on "
                    + Manager.getReservations().get(auxIndexList.get(numAnswer)).getFacility().getName()
                    + " on " + Manager.getReservations().get(auxIndexList.get(numAnswer)).getCalendar().getTime()
                    + " was deleted. ***" + "\nPress any key to continue...");
            Manager.removeReservation(Manager.getReservations().get(auxIndexList.get(numAnswer)));
            try {
                input.readLine();

            } catch (IOException e) {
                e.printStackTrace();
            }
            chooseAction();
        }
    }


    /**
     * This method allows the choosing of a facility type.
     *
     * @param facilityType is the type of existing facilities to be booked.
     */
    private void choose(FacilityType facilityType) {

        String answer1 = "";
        int counter = 1;
        ArrayList<Facility> chosenFacilities = new ArrayList<>();

        output.println("+-----------------------------------------------------------------------------+\n" +
                "|                          Choose facility to book:                           |\n" +
                "+-----------------------------------------------------------------------------+\n");

        for (int i = 0; i < Manager.getFacilities().size(); i++) {

            if (Manager.getFacilities().get(i).getType().equals(facilityType)) {
                output.println("- (" + (counter++) + ") " + Manager.getFacilities().get(i).getName());
                chosenFacilities.add(Manager.getFacilities().get(i));
            }
        }
        output.println("- Back to (M)ain menu");

        try {
            answer1 = input.readLine();

        } catch (IOException e) {
            e.printStackTrace();
        }

        if (answer1.equals("M") || answer1.equals("m")) {
            chooseAction();
        } else if (!answer1.matches("\\d+")) {
            output.println("*** Please write a valid answer (a number from the list): ***");
            choose(facilityType);
        } else if (Integer.parseInt(answer1) < 1 || Integer.parseInt(answer1) > chosenFacilities.size()) {
            output.println("*** Please write a valid answer (a number from the list): ***");
            choose(facilityType);
        } else {
            for (int i = 0; i < chosenFacilities.size(); i++) {
                if (Integer.parseInt(answer1) == (i + 1)) {
                    output.println("*** You have chosen the facility " + chosenFacilities.get(i).getName() + " ***");
                    chooseMonth(chosenFacilities.get(i));
                    break;
                }
            }
        }
    }

    /**
     * This method allows the client to choose a month for its reservation.
     *
     * @param facility is the facility chosen by the client.
     */
    private void chooseMonth(Facility facility) {

        String month = "";

        output.println("+-----------------------------------------------------------------------------+\n" +
                "|                    Choose a month for your reservation                      |\n" +
                "+-----------------------------------------------------------------------------+\n" + "- (1) January\n" + "- (2) February \n" + "- (3) March \n"
                + "- (4) April \n" + "- (5) May \n" + "- (6) June \n" + "- (7) July \n" + "- (8) August \n" + "- (9) September \n"
                + "- (10) October \n" + "- (11) November \n" + "- (12) December\n" + "- Back to (M)ain menu");

        try {
            month = input.readLine();

            if (month.equals("M") || month.equals("m")) {
                chooseAction();

            } else if (!month.matches("\\d+")) {
                output.println("*** Please write a valid answer (a number from the list): ***");
                chooseMonth(facility);

            } else if (Integer.parseInt(month) >= 1 && Integer.parseInt(month) <= 12) {
                month = Integer.toString(Integer.parseInt(month) - 1);
                chooseDay(facility, month);

            } else {
                output.println("*** Please, choose a valid option ***");
                chooseMonth(facility);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method allows the client to choose a day for its reservation.
     *
     * @param facility is the facility chosen by the client.
     * @param month    is the month chosen by the client.
     */
    private void chooseDay(Facility facility, String month) {

        String day = "";

        output.println("+-----------------------------------------------------------------------------+\n" +
                "|                    Type the chosen day for your reservation                 |\n" +
                "+-----------------------------------------------------------------------------+");

        try {
            day = input.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (day.equals("M") || day.equals("m")) {
            chooseAction();

        } else if (!day.matches("\\d+")) {
            output.println("*** Please write a valid answer (a day of the month): ***");
            chooseDay(facility, month);

        } else {

            switch (month) {
                case "0":
                case "2":
                case "4":
                case "6":
                case "7":
                case "9":
                case "11":
                    if (Integer.parseInt(day) >= 1 && Integer.parseInt(day) <= 31) {
                        chooseHour(facility, month, day);
                        chooseAction();//TESTING
                    } else {
                        chooseDay(facility, month);
                    }
                    break;

                case "1":
                    if (Integer.parseInt(day) >= 1 && Integer.parseInt(day) <= 28) {
                        chooseHour(facility, month, day);
                        chooseAction();//TESTING
                    } else {
                        chooseDay(facility, month);
                    }
                    break;
                case "3":
                case "5":
                case "8":
                case "10":
                    if (Integer.parseInt(day) >= 1 && Integer.parseInt(day) <= 30) {

                        chooseHour(facility, month, day);
                        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
                        for (StackTraceElement element : stack) {
                            System.out.println(element);
                        }
                        chooseAction();//TESTING
                    } else {
                        chooseDay(facility, month);
                    }
                    break;

                default:
                    output.println("*** Please, enter a valid option. ***");
            }

        }
    }

    /**
     * This method allows the client to choose an hour for its reservation.
     *
     * @param facility is the facility chosen by the client.
     * @param month    is the month chosen by the client.
     * @param day      is the day chosen by the client.
     *                 <p>
     *                 Then, a new reservation is made.
     * @see #createNewReservation(Facility, String, String, String).
     */
    private void chooseHour(Facility facility, String month, String day) {

        String hour = "";
        boolean[] hours = new boolean[13];
        for (boolean b : hours) {
            b = false;
        }

        output.println("Processing...");

        synchronized (Manager.getReservations()) {

            output.println("+-----------------------------------------------------------------------------+\n" +
                    "|          These are the available hours for this day. Choose one:            |\n" +
                    "+-----------------------------------------------------------------------------+");

            System.out.println(facility.getName());
            for (int i = 0; i < Manager.getReservations().size(); i++) {
                System.out.println(Manager.getReservations().get(i).getFacility().getName());

                if (Manager.getReservations().get(i).getFacility().getName().equals(facility.getName())) {
                    if (Manager.getReservations().get(i).getCalendar().get((Calendar.MONTH)) == Integer.parseInt(month)) {
                        if (Manager.getReservations().get(i).getCalendar().get((Calendar.DAY_OF_MONTH)) == Integer.parseInt(day)) {
                            hours[Manager.getReservations().get(i).getCalendar().get(Calendar.HOUR_OF_DAY) - 9] = true;
                        }
                    }
                }
            }
            for (int i = 0; i < hours.length; i++) {
                if (!hours[i]) {
                    output.print("(" + (i + 9) + ")h00 | ");
                }
            }
            output.println("- Back to (M)ain menu");
            output.println("");

            try {
                hour = input.readLine();

            } catch (IOException e) {
                e.printStackTrace();
            }

            if (hour.equals("M") || hour.equals("m")) {
                //chooseAction();

            } else if (!hour.matches("\\d+")) {
                output.println("*** Please write a valid answer (a number from the list): ***");
                chooseHour(facility, month, day);

            } else if (Integer.parseInt(hour) >= 9 && Integer.parseInt(hour) <= 21) {
                if (hours[Integer.parseInt(hour) - 9]) {
                    output.println("*** That time slot is already taken. Please choose another one: ***");
                    chooseHour(facility, month, day);
                } else {
                    createNewReservation(facility, month, day, hour);
                }
            } else {
                output.println("*** That's not a valid answer. ***");
                chooseHour(facility, month, day);
            }
        }
    }

    /**
     * This method creates a new reservation based on the client choices for:
     *
     * @param facility is the facility chosen by the client.
     * @param month    is the month chosen by the client.
     * @param day      is the day chosen by the client.
     * @param hour     is the hour chosen by the client.
     *                 <p>
     *                 Then add the reservation for the Reservation List on the Manager Class.
     */
    private void createNewReservation(Facility facility, String month, String day, String hour) {

        Reservation reservation = new Reservation(client, facility, Integer.parseInt(month), Integer.parseInt(day),
                Integer.parseInt(hour));

        Manager.addReservationToList(reservation);

        if (!isNewReservation) {
            output.println("*** Changed reservation on " + facility.getName() + " to "
                    + reservation.getCalendar().getTime() + "\nPress any key to continue... ***");

            try {
                input.readLine();

            } catch (IOException e) {
                e.printStackTrace();
            }
            //chooseAction();
        } else {
            output.println("*** New reservation to: " + facility.getName() + " on: " +
                    reservation.getCalendar().getTime() + "\nPress any key to continue... ***");

            try {
                input.readLine();

            } catch (IOException e) {
                e.printStackTrace();
            }
            //chooseAction();
        }
    }
}