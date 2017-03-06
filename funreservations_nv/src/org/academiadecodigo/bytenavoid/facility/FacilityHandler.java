package org.academiadecodigo.bytenavoid.facility;


import org.academiadecodigo.bytenavoid.client.ClientHandler;
import org.academiadecodigo.bytenavoid.reservation.Reservation;
import org.academiadecodigo.bytenavoid.util.Manager;

import java.io.*;
import java.net.Socket;

/**
 * Created by codecadet on 01/03/17.
 */
public class FacilityHandler {

    private Socket socket;
    private PrintWriter output = null;
    private BufferedReader input = null;
    private ClientHandler clientHandler;

    public FacilityHandler(Socket socket) {
        this.socket = socket;
        try {
            output = new PrintWriter(socket.getOutputStream(), true);
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * starts by calling the askID to the user in order to make the login and to know what facility is browsing,
     * when done, calls the askPw for that facility to complete the login. When login is completed, calls the method
     * manageOptions for that facility.
     */
    public void startFacilityAccess() {

        Facility facility = askID();

        if (askPw(facility)) {

            manageOptions(facility);

        }

    }



    private volatile String idString;

    /**
     * Ask the user for input in order to start the login, if it doesn't receive an integer or an unvalid, calls
     * the method again until a valid one is provided. When the valid ID is given,
     * @return the facility that wants to login
     */
    private Facility askID() {

        try {
            output.println("+-----------------------------------------------------------------------------+\n" +
                    "|                       Please, enter your facility ID:                       |\n" +
                    "+-----------------------------------------------------------------------------+\n");
            idString = input.readLine();
            if (idString.matches("^[a-zA-Z]+$")) {

                askID();
            }
            int id = Integer.parseInt(idString);


            System.out.println(idString);
            System.out.println(id);

            for (Facility fac : Manager.getFacilities()) {

                if (fac.getID() == id) {
                    return fac;
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        askID();
        return null;
    }


    private int passAttempt;

    /**
     * receives the facility that want's to login and asks for a password, for every time the password is wrong,
     * increments the integer passAttempt, and when it reaches 3 sends the user back to the startFacilityAccess.
     * @param facility
     * @return true when the password for the given facility is correct
     */

    private boolean askPw(Facility facility) {
        try {
            output.println("+-----------------------------------------------------------------------------+\n" +
                    "|                            Enter your password:                             |\n" +
                    "+-----------------------------------------------------------------------------+\n");
            String pass = input.readLine();

            if (passAttempt == 3) {
                startFacilityAccess();
                passAttempt = 0;
            }

            if (!facility.getPw().equals(pass)) {
                passAttempt++;
                askPw(facility);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * Shows the user the options that he have and receives the user input of his choice.
     * If the user choose an invalid option, calls back the same method. When the input is a valid option,
     * calls the method relative to the option choosen.
     *
     * @param facility
     */

    private void manageOptions(Facility facility) {

        try {

            output.println("+-----------------------------------------------------------------------------+\n" +
                    "|                            Management Options:                              |\n" +
                    "+-----------------------------------------------------------------------------+\n");
            output.println("- Manage (R)eservations \n" +
                    "- Manage (I)nfo\n" +
                    "- (L)ogOut");
            String manageChoice = input.readLine().toUpperCase();

            switch (manageChoice) {
                case "R":
                    manageReservations(facility);
                    break;
                case "I":
                    manageInfo(facility);
                    break;
                case "L":
                    startFacilityAccess();
                    break;
                default:
                    output.println("Invalid Command!\n\n");
                    manageOptions(facility);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows the user the options that he have and receives the user input of his choice.
     * If the user choose an invalid option, calls back the same method. When the input is a valid option,
     * calls the method relative to the option choosen.
     *
     * @param facility
     */
    private void manageReservations(Facility facility) {

        try {

            output.println("+-----------------------------------------------------------------------------+\n" +
                    "|                     Reservations Management Options:                        |\n" +
                    "+-----------------------------------------------------------------------------+\n");
            output.println("- (N)ew Reservation\n" +
                    "- (C)heck Current Reservations\n" +
                    "- (D)elete Reservation\n" +
                    "- Go (B)ack");

            String manageReservationChoice = input.readLine().toUpperCase();

            switch (manageReservationChoice) {

                case "N":
                    makeNewFacilityReservation(facility);
                    break;
                case "C":
                    checkCurrentReservations(facility);
                    break;
                case "D":
                    deleteReservation(facility);
                    break;
                case "B":
                    manageOptions(facility);
                    break;
                default:
                    output.println("Invalid Command!\n\n");
                    manageReservations(facility);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Given the facility, iterates for the reservations stored an display to the user his own reservations,
     * associated with a number. Receives the input of the user whith the number of the reservation and calls
     * the Manager method to remove that reservation.
     * @param facility
     */

    private void deleteReservation(Facility facility) {

        output.println("+-----------------------------------------------------------------------------+\n" +
                "|                           Deleting Reservation                              |\n" +
                "+-----------------------------------------------------------------------------+\n");

        Reservation reservation;

        output.println("Choose Reservation to delete: ");

        for (int i = 0; i < Manager.getReservations().size(); i++) {

            if (Manager.getReservations().get(i).getFacility().getID() == (facility.getID())) {
                reservation = Manager.getReservations().get(i);

                output.println("(" + (i+1) + ") reservation from: " + reservation.getClient().getName() + " on: " +
                        reservation.getCalendar().getTime());
            }
        }
        try {
            int answer = Integer.parseInt(input.readLine());

            for (int i = 0; i < Manager.getReservations().size(); i++) {

                if (Manager.getReservations().get(i).getFacility().getID() == Manager.getReservations().get(answer-1).getFacility().getID()) {
                    Manager.removeReservation(Manager.getReservations().get(i));
                    output.println("*** Reservation Deleted... ***");
                    manageReservations(facility);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Displays the reservations for the given facility
     * @param facility
     */
    private void checkCurrentReservations(Facility facility) {

        output.println("+-----------------------------------------------------------------------------+\n" +
                "|                      Checking Current Reservations                          |\n" +
                "+-----------------------------------------------------------------------------+\n");

        Reservation reservation;

        for (int i = 0; i < Manager.getReservations().size(); i++) {

            if (Manager.getReservations().get(i).getFacility().getID() == (facility.getID())) {
                reservation = Manager.getReservations().get(i);

                output.println("(" + (i+1) + ") reservation from: " + reservation.getClient().getName() + " on: " +
                        reservation.getCalendar().getTime());
            }
        }
        manageReservations(facility);
    }

    /**
     * Re-directs the user to the client area
     * @param facility
     */
    private void makeNewFacilityReservation(Facility facility) {
        output.println("*** Redirecting to Client Mode ***\n\n");

        clientHandler = new ClientHandler(socket);
        clientHandler.startClientAccess();
    }


    /**
     * Shows the user his managing option relative to his info and recives input relative to that options. If
     * the input is invalid, calls the same method, otherwise, calls the method relative to the input
     * @param facility
     */
    private void manageInfo(Facility facility) {

        try {

            output.println("+-----------------------------------------------------------------------------+\n" +
                    "|                      Facility Management Options:                           |\n" +
                    "+-----------------------------------------------------------------------------+\n");

            output.println("- (C)urrent Information\n" +
                    "- Update Password(PW) \n" +
                    "- Update (I)nfo\n" +
                    "- Update (A)ddress\n" +
                    "- Update (P)hone\n" +
                    "- Go (B)ack");

            String manageInfoChoice = input.readLine().toUpperCase();

            switch (manageInfoChoice) {

                case "C":
                    showInfo(facility);
                    break;
                case "PW":
                    updatePw(facility);
                    break;
                case "I":
                    updateInfo(facility);
                    break;
                case "A":
                    updateAddress(facility);
                    break;
                case "P":
                    updatePhone(facility);
                    break;
                case "B":
                    manageOptions(facility);
                    break;
                default:
                    output.println("Invalid Command!\n\n");
                    manageInfo(facility);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Displays the info relative to this user and calls the previous method
     * @param facility
     */
    private void showInfo(Facility facility) {

        output.println("+-----------------------------------------------------------------------------+\n" +
                "|                         Current Facility Information                        |\n" +
                "+-----------------------------------------------------------------------------+\n" +
                "- Current ID: " + facility.getID() + "\n" +
                "- Current Name: " + facility.getName() + "\n" +
                "- Current Phone: " + facility.getPhone() + "\n" +
                "- Current Address: " + facility.getAddress() + "\n" +
                "- Current Info: " + facility.getInfo() + "\n");

        manageInfo(facility);

    }

    private volatile String phoneString;

    /**
     * Displays the current phone number of this user and recives the input of the new info, calling the setPhone method
     * if the input is valid
     * @param facility
     */
    private void updatePhone(Facility facility) {
        try {
            output.println("+-----------------------------------------------------------------------------+\n" +
                    "|                            Update Facility Phone                            |\n" +
                    "+-----------------------------------------------------------------------------+\n" +
                    "- Current Phone: " + facility.getPhone() + "\n" +
                    "- New Phone: ");

            phoneString = input.readLine();
            if (phoneString.matches("^[a-zA-Z]+$")) {

                updatePhone(facility);
            }
            int phone = Integer.parseInt(phoneString);
            facility.setPhone(phone);

            manageInfo(facility);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Displays the current adress of this user and recives the input of the new info, calling the setAdress method
     * @param facility
     */

    private void updateAddress(Facility facility) {
        try {
            output.println("+-----------------------------------------------------------------------------+\n" +
                    "|                         Update Facility Address                             |\n" +
                    "+-----------------------------------------------------------------------------+\n");

            output.println("- Current Address: " + facility.getAddress() + "\n" +
                    "- New Address: ");

            facility.setAddress(input.readLine());
            manageInfo(facility);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Displays the current info of this user and recives the input of the new info, calling the setInfo method
     * @param facility
     */

    private void updateInfo(Facility facility) {
        try {
            output.println("+-----------------------------------------------------------------------------+\n" +
                    "|                          Update Facility Info                               |\n" +
                    "+-----------------------------------------------------------------------------+\n");

            output.println("- Current Info: " + facility.getInfo() + "\n" +
                    "- New Info: ");

            facility.setInfo(input.readLine());
            manageInfo(facility);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Recives the input of the new password, calling the setPw method
     * @param facility
     */

    private void updatePw(Facility facility) {
        try {
            output.println("+-----------------------------------------------------------------------------+\n" +
                           "|                          Update Login Password                              |\n" +
                    "+-----------------------------------------------------------------------------+\n");

            output.println("- New Password: ");

            facility.setPw(input.readLine());
            manageInfo(facility);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
