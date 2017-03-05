package org.academiadecodigo.bytenavoid.util;

import org.academiadecodigo.bytenavoid.client.Client;
import org.academiadecodigo.bytenavoid.facility.Facility;
import org.academiadecodigo.bytenavoid.reservation.Reservation;

import java.net.URISyntaxException;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by codecadet on 01/03/17.
 */
public class Manager {

    private static CopyOnWriteArrayList<Client> clientList;
    private static CopyOnWriteArrayList<Facility> facilities;
    private static CopyOnWriteArrayList<Reservation> reservations;

    public static CopyOnWriteArrayList<Client> getClientList() {
        return clientList;
    }

    public static CopyOnWriteArrayList<Facility> getFacilities() {
        return facilities;
    }

    public static CopyOnWriteArrayList<Reservation> getReservations() {
        return reservations;
    }

    public static void init() throws URISyntaxException {

        reservations = new CopyOnWriteArrayList<>();
        clientList = new CopyOnWriteArrayList<>();
        facilities = new CopyOnWriteArrayList<>();

        reservations = (CopyOnWriteArrayList<Reservation>) FileManager.loadFile(FileType.RESERVATION);
        clientList = (CopyOnWriteArrayList<Client>) FileManager.loadFile(FileType.CLIENT);
        facilities = (CopyOnWriteArrayList<Facility>) FileManager.loadFile(FileType.FACILITY);

    }

    public static void saveReservations() {
        FileManager.saveFile(reservations, FileType.RESERVATION);
    }

    public static void saveClients() {
        FileManager.saveFile(clientList, FileType.CLIENT);
        saveReservations();
    }

    public static void saveFacilities() {
        FileManager.saveFile(facilities, FileType.FACILITY);
        saveReservations();
    }


    public static void addClientToList(Client client) {
        clientList.add(client);
        saveClients();
    }

    public static void addFacilityToList(Facility facility) {
        facilities.add(facility);
        saveFacilities();
    }

    public static void addReservationToList(Reservation reservation) {
        reservations.add(reservation);
        saveReservations();
    }

    public static void removeClient(Client client) {
        clientList.remove(client);
        saveClients();
    }

    public static void removeFacility(Facility facility) {
        facilities.remove(facility);
        saveFacilities();
    }

    public static void removeReservation(Reservation reservation) {
        reservations.remove(reservation);
        saveReservations();
    }
}
