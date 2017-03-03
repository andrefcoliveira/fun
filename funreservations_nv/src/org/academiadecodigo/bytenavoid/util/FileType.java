package org.academiadecodigo.bytenavoid.util;


import org.academiadecodigo.bytenavoid.client.Client;
import org.academiadecodigo.bytenavoid.facility.Facility;
import org.academiadecodigo.bytenavoid.reservation.Reservation;

/**
 * Created by codecadet on 02/03/17.
 */
public enum FileType {

    CLIENT("clients.tmp"),
    FACILITY("facilities.tmp"),
    RESERVATION("reservations.tmp");


    String filename;

    FileType(String filename) {
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }

}
