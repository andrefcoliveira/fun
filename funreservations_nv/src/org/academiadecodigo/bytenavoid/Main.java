package org.academiadecodigo.bytenavoid;

import org.academiadecodigo.bytenavoid.client.Client;
import org.academiadecodigo.bytenavoid.client.ClientHandler;
import org.academiadecodigo.bytenavoid.facility.Facility;
import org.academiadecodigo.bytenavoid.facility.FacilityHandler;
import org.academiadecodigo.bytenavoid.facility.FacilityType;
import org.academiadecodigo.bytenavoid.server.Server;
import org.academiadecodigo.bytenavoid.util.Manager;

/**
 * Created by codecadet on 01/03/17.
 */
public class Main {

    public static void main(String[] args) {

        Facility facility = new Facility(2313, FacilityType.SOCCER, "facility1", "facility1", 32434, "facility1");
        Facility facility2 = new Facility(2313, FacilityType.SOCCER, "facility2", "facility2", 32434, "facility2");
        Facility facility3 = new Facility(2313, FacilityType.SWIMMINGPOOL, "facility3", "facility3", 32434, "facility3");
        Facility facility4 = new Facility(2313, FacilityType.SOCCER, "facility4", "facility4", 32434, "facility4");
        Facility facility5 = new Facility(2313, FacilityType.SOCCER, "facility5", "facility5", 32434, "facility5");
        Manager manager = new Manager();
        manager.init();

        Manager.getFacilities().add(facility);
        Manager.getFacilities().add(facility2);
        Manager.getFacilities().add(facility3);
        Manager.getFacilities().add(facility4);
        Manager.getFacilities().add(facility5);


        Client client = new Client("adriana", "ahdorneles", "1234", "sdada", 21313);
        Manager.getClientList().add(client);


        Server server = new Server();
        server.init();

    }
}
