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

        output.println("** Deleting Reservation **\n\n");

        Reservation reservation;

        output.println("Choose Reservation to delete: ");

        for (int i = 0; i < Manager.getReservations().size(); i++) {

            if (Manager.getReservations().get(i).getFacility().getID() == (facility.getID())) {
                reservation = Manager.getReservations().get(i);

                output.println("(" + i + ") reservation from: " + reservation.getClient().getName() + " on: " +
                        reservation.getCalendar().getTime());
            }
        }
        try {
           int answer = Integer.parseInt(input.readLine());

            for (int i = 0; i < Manager.getReservations().size(); i++) {

                if (Manager.getReservations().get(i).getFacility().getID() == Manager.getReservations().get(answer).getFacility().getID()) {
                    Manager.removeReservation(Manager.getReservations().get(i));
                    output.println("Reservation Deleted...");
                    manageOptions(facility);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void checkCurrentReservations(Facility facility) {

        output.println("** Checking Current Reservations **\n\n");

        Reservation reservation;

        for (int i = 0; i < Manager.getReservations().size(); i++) {

            if (Manager.getReservations().get(i).getFacility().getID() == (facility.getID())) {
                reservation = Manager.getReservations().get(i);

                output.println("(" + i + ") reservation from: " + reservation.getClient().getName() + " on: " +
                        reservation.getCalendar().getTime());
            }
        }
        manageOptions(facility);
    }

    private void makeNewFacilityReservation(Facility facility) {
        output.println("** Redirecting to Client Mode **\n\n");
        clientHandler.startClientAccess();
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
                    updatePhone(facility);
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

    private void updatePhone(Facility facility) {
        try{
            output.println("** Update Facility Phone **\n" +
                    "- Current Phone: " + facility.getAddress()+"\n" +
                    "- New Address: ");

            facility.setAddress(input.readLine());
            manageInfo(facility);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateAddress(Facility facility) {
        try{
            output.println("** Update Facility Address **\n" +
                    "- Current Address: " + facility.getAddress()+"\n" +
                    "- New Address: ");

            facility.setAddress(input.readLine());
            manageInfo(facility);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateInfo(Facility facility) {
        try{
            output.println("** Update Facility Info **\n" +
                    "- Current Info: " + facility.getAddress()+"\n" +
                    "- New Info: ");

            facility.setInfo(input.readLine());
            manageInfo(facility);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updatePw(Facility facility) {
        try{
            output.println("** Update Login Password **\n" +
                    "- New Password: ");

            facility.setPw(input.readLine());
            manageInfo(facility);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateName(Facility facility) {
        try{
            output.println("** Update Facility Name **\n" +
                    "- Current Name: " + facility.getAddress()+"\n" +
                    "- New Name: ");

            facility.setName(input.readLine());
            manageInfo(facility);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
