package org.academiadecodigo.bytenavoid;

import org.academiadecodigo.bytenavoid.client.Client;
import org.academiadecodigo.bytenavoid.facility.Facility;
import org.academiadecodigo.bytenavoid.facility.FacilityType;
import org.academiadecodigo.bytenavoid.reservation.Reservation;
import org.academiadecodigo.bytenavoid.util.FileManager;
import org.academiadecodigo.bytenavoid.util.FileType;
import org.academiadecodigo.bytenavoid.util.Manager;

import java.util.LinkedList;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by codecadet on 02/03/17.
 */
public class Test {

    // TODO: 03/03/17 Facility --  

    public static void main(String[] args) {



       CopyOnWriteArrayList<Object> list = new CopyOnWriteArrayList<>();
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
  Manager.init();

  Reservation reservation = new Reservation(new Client("armandojyc", "Armando", "ARMANDO", "armando@armando", 919191919),
          new Facility(1, FacilityType.RUNNING, "run bitch", "rua do run", 1234, "poo caralho!!"), 2, 3, 12);
        Manager.addReservationToList(reservation);
        Manager.addReservationToList(new Reservation(new Client("armandordh<aetg", "Armando", "ARMANDO", "armando@armando", 919191919),
                new Facility(1, FacilityType.RUNNING, "run bitch", "rua do run", 1234, "poo caralho!!"), 2, 3, 12));

       /*// list = FileManager.loadFile(FileType.RESERVATION);

        for (Object obj: list) {

            System.out.println(((Reservation)obj).getClient().getName());
        }*/



        for (Reservation c: Manager.getReservations()) {

            if (c.getClient().getName().equals("armandojyc")) {
                Manager.removeReservation(c);
                continue;
            }
            System.out.println(c.getClient().getName());

        }

    }
}
