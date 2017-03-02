package org.academiadecodigo.bytenavoid.util;

import org.academiadecodigo.bytenavoid.client.Client;
import org.academiadecodigo.bytenavoid.facility.Facility;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by codecadet on 01/03/17.
 */
public class Manager {

    private static CopyOnWriteArrayList<Client> clientList;
    private static CopyOnWriteArrayList<Facility> facilities;




    public static CopyOnWriteArrayList<Client> getClientList() {
        return clientList;
    }

    public static CopyOnWriteArrayList<Facility> getFacilities() {
        return facilities;
    }


    public void init() {

        clientList = new CopyOnWriteArrayList<>();
        facilities = new CopyOnWriteArrayList<>();
    }
}
