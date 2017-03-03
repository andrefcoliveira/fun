package org.academiadecodigo.bytenavoid;

import org.academiadecodigo.bytenavoid.client.Client;
import org.academiadecodigo.bytenavoid.facility.Facility;
import org.academiadecodigo.bytenavoid.facility.FacilityType;
import org.academiadecodigo.bytenavoid.reservation.Reservation;
import org.academiadecodigo.bytenavoid.util.FileManager;
import org.academiadecodigo.bytenavoid.util.FileType;
import org.academiadecodigo.bytenavoid.util.Manager;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by codecadet on 02/03/17.
 */
public class Test {

    // TODO: 03/03/17 Facility --  

    public static void main(String[] args) {

        Manager.init();

        /*Client client = new Client("adriana", "ahdorneles", "1234", "sdada", 21313);
        Manager.addClientToList(client);

        Facility facility = new Facility(2313, FacilityType.SOCCER, "facility1", "facility1", 32434, "facility1");
        Facility facility2 = new Facility(3424, FacilityType.SOCCER, "facility2", "facility2", 32434, "facility2");
        Facility facility3 = new Facility(25, FacilityType.SWIMMINGPOOL, "facility3", "facility3", 32434, "facility3");
        Facility facility4 = new Facility(646, FacilityType.SOCCER, "facility4", "facility4", 32434, "facility4");
        Facility facility5 = new Facility(232, FacilityType.SOCCER, "facility5", "facility5", 32434, "facility5");

        Manager.addFacilityToList(facility);
        Manager.addFacilityToList(facility2);
        Manager.addFacilityToList(facility3);
        Manager.addFacilityToList(facility4);
        Manager.addFacilityToList(facility5);



        Reservation reservation = new Reservation(client, facility, 3, 3, 3);
        Reservation reservation1 = new Reservation(client, facility, 3, 3, 7);
        Reservation reservation2 = new Reservation(client, facility, 3, 3, 12);
        Reservation reservation3 = new Reservation(client, facility, 3, 3, 20);

        System.out.println(reservation.getCalendar().get((Calendar.HOUR_OF_DAY)));

        Manager.addReservationToList(reservation);
        Manager.addReservationToList(reservation1);
        Manager.addReservationToList(reservation2);
        Manager.addReservationToList(reservation3);*/
  /*       Reservation reservation = new Reservation(new Client("armando", "Armando", "ARMANDO", "armando@armando", 919191919),
                new Facility(1, FacilityType.RUNNING, "run bitch", "rua do run", 1234, "poo caralho!!"), 2, 3, 12);


        Reservation reservation3 = new Reservation(new Client("armandosetrh", "Armando", "ARMANDO", "armando@armando", 919191919),
                new Facility(1, FacilityType.RUNNING, "run bitch", "rua do run", 1234, "poo caralho!!"), 2, 3, 12);


        Reservation reservation2 = new Reservation(new Client("armandokugkavksdghfkshf", "Armando", "ARMANDO", "armando@armando", 919191919),
                new Facility(1, FacilityType.RUNNING, "run bitch", "rua do run", 1234, "poo caralho!!"), 2, 3, 12);


        list.add(reservation);
        list.add(reservation3);
        list.add(reservation2);

        FileManager.saveFile(list, FileType.RESERVATION);*/



        /*reservation = new Reservation(new Client("armandojyc", "Armando", "ARMANDO", "armando@armando", 919191919),
          new Facility(1, FacilityType.RUNNING, "run bitch", "rua do run", 1234, "poo caralho!!"), 2, 3, 12);

        Manager.addReservationToList(reservation);
        Manager.addReservationToList(new Reservation(new Client("armandordh<aetg", "Armando", "ARMANDO", "armando@armando", 919191919),
                new Facility(1, FacilityType.RUNNING, "run bitch", "rua do run", 1234, "poo caralho!!"), 2, 3, 12));*/

        CopyOnWriteArrayList<Object> list = FileManager.loadFile(FileType.RESERVATION);

        for (Object obj: list) {

            System.out.println(((Reservation)obj).getClient().getName());
            System.out.println();
        }



        /*for (Reservation c: Manager.getReservations()) {

            if (c.getClient().getName().equals("armandojyc")) {
                Manager.removeReservation(c);
                continue;
            }
            System.out.println(c.getClient().getName());

        }*/

    }
}
