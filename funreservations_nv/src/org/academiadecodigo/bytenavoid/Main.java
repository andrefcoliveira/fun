package org.academiadecodigo.bytenavoid;

import org.academiadecodigo.bytenavoid.client.Client;
import org.academiadecodigo.bytenavoid.client.ClientHandler;
import org.academiadecodigo.bytenavoid.facility.Facility;
import org.academiadecodigo.bytenavoid.facility.FacilityHandler;
import org.academiadecodigo.bytenavoid.facility.FacilityType;
import org.academiadecodigo.bytenavoid.reservation.Reservation;
import org.academiadecodigo.bytenavoid.server.Server;
import org.academiadecodigo.bytenavoid.util.Manager;

import java.util.Calendar;

/**
 * Created by codecadet on 01/03/17.
 */
public class Main {

    public static void main(String[] args) {

        Manager.init();

        /*Facility facility = new Facility(1,"1234", FacilityType.SOCCER, "facility1", "facility1", 32434, "facility1");
        Facility facility2 = new Facility(2,"1234", FacilityType.SOCCER, "facility2", "facility2", 32434, "facility2");
        Facility facility3 = new Facility(3,"1234", FacilityType.SWIMMINGPOOL, "facility3", "facility3", 32434, "facility3");
        Facility facility4 = new Facility(4,"1234", FacilityType.SOCCER, "facility4", "facility4", 32434, "facility4");
        Facility facility5 = new Facility(5,"1234", FacilityType.SOCCER, "facility5", "facility5", 32434, "facility5");


        Manager.addFacilityToList(facility);
        Manager.addFacilityToList(facility2);
        Manager.addFacilityToList(facility3);
        Manager.addFacilityToList(facility4);
        Manager.addFacilityToList(facility5);


        Client client = new Client("adriana", "ahdorneles", "1234", "sdada", 21313);
        Client client1 = new Client("Tome", "tlourenzo", "1234", "teste@email", 91919191);
        Manager.addClientToList(client);
        Manager.addClientToList(client1);

        Reservation reservation = new Reservation(client, facility, 3, 3, 3);
        Reservation reservation1 = new Reservation(client, facility, 3, 3, 7);
        Reservation reservation2 = new Reservation(client1, facility, 3, 3, 12);
        Reservation reservation3 = new Reservation(client1, facility, 3, 3, 20);

        System.out.println(reservation.getCalendar().get((Calendar.HOUR_OF_DAY)));

        Manager.addReservationToList(reservation);
        Manager.addReservationToList(reservation1);
        Manager.addReservationToList(reservation2);
        Manager.addReservationToList(reservation3);

        for (Reservation r: Manager.getReservations()) {

            System.out.println(r.getFacility().getName());
            System.out.println(r.getClient().getName());
            System.out.println(r.getCalendar().get(Calendar.HOUR_OF_DAY));
            System.out.println(r.getCalendar().get(Calendar.MONTH));
            System.out.println(r.getCalendar().get(Calendar.DAY_OF_MONTH));

        }*/


        Server server = new Server();
        server.init();

    }
}
