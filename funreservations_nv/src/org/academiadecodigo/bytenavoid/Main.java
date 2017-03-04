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

      /* Facility facility = new Facility(1,"1234", FacilityType.SOCCER, "Grupo Desportivo Fundao", "Rua do Estadio", 919192829, "Campo de 11");
        Facility facility2 = new Facility(2,"1234", FacilityType.SOCCER, "Sintetico do Fundao", "Rua do Estadio Novo", 18761619, "Campo de 7");
        Facility facility3 = new Facility(3,"1234", FacilityType.SWIMMINGPOOL, "Piscinas Municipais", "Rua das Piscinas", 71263981, "Nao sabe Nadaaaa, Yo");


        Manager.addFacilityToList(facility);
        Manager.addFacilityToList(facility2);
        Manager.addFacilityToList(facility3);


        Client client = new Client("adriana", "ahdorneles", "1234", "sdada", 21313);
        Client client1 = new Client("Tome", "tlourenzo", "1234", "teste@email", 91919191);
        Manager.addClientToList(client);
        Manager.addClientToList(client1);

        Reservation reservation = new Reservation(client, facility, 3, 3, 3);
        Reservation reservation1 = new Reservation(client, facility, 3, 3, 7);
        Reservation reservation2 = new Reservation(client1, facility, 3, 3, 12);

        Manager.addReservationToList(reservation);
        Manager.addReservationToList(reservation1);
        Manager.addReservationToList(reservation2);
*/
        Server server = new Server();
        server.init();

    }
}
