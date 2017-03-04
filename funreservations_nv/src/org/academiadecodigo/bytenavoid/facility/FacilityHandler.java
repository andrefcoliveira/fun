package org.academiadecodigo.bytenavoid.facility;


import org.academiadecodigo.bytenavoid.util.Manager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by codecadet on 01/03/17.
 */
public class FacilityHandler {

    private Socket socket;
    private PrintWriter output = null;
    private BufferedReader input = null;

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
     * ask user id, verify if exists into list, if yes ask for pass
     * if no re-ask for user
     */

    public void startFacilityAccess() {

        Facility facility = askID();

        if (askPw(facility)) {

            manageOptions(facility);

        }

    }


    private Facility askID() {

        try {
            output.println("Please, enter your facility ID:");
            int id = Integer.parseInt(input.readLine());

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

    private boolean askPw(Facility facility) {
        try {
            output.println("Enter your password: ");
            String pass = input.readLine();

            if (passAttempt == 3) {
                startFacilityAccess();
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


    private void manageOptions(Facility facility) {

        try {

            output.println("**** Available Options: ****\n\n" +
                    "- Manage (R)eservations \n" +
                    "- Manage (I)nfo\n" +
                    "- (L)ogOut");
            String manageChoice = input.readLine();

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

    private void manageReservations(Facility facility) {

        try{

            output.println("**** Reservations Management Options: ****\n\n" +
                    "- (N)ew Reservation\n" +
                    "- (C)heck Current Reservations\n" +
                    "- (D)elete Reservation\n" +
                    "- Go (B)ack");

            String manageReservationChoice = input.readLine();

            switch (manageReservationChoice){

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

    private void deleteReservation(Facility facility) {

    }

    private void checkCurrentReservations(Facility facility) {

    }

    private void makeNewFacilityReservation(Facility facility) {
    }

    private void manageInfo(Facility facility) {

        try{

            output.println("**** Facility Management Options: ****\n\n" +
                    "- Update (N)ame\n" +
                    "- Update Password(PW) \n" +
                    "- Update (I)nfo\n" +
                    "- Update (A)ddress\n" +
                    "- Update (P)hone\n" +
                    "- Go (B)ack");

            String manageInfoChoice = input.readLine();

            switch (manageInfoChoice){

                case "N":
                    updateName(facility);
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
                    updateAddress(facility);
                    break;
                case "B":
                    manageReservations(facility);
                    break;
                default:
                    output.println("Invalid Command!\n\n");
                    manageInfo(facility);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void updateAddress(Facility facility) {

    }

    private void updateInfo(Facility facility) {

    }

    private void updatePw(Facility facility) {

    }

    private void updateName(Facility facility) {

    }


}
