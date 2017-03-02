package org.academiadecodigo.bytenavoid.util;

import org.academiadecodigo.bytenavoid.client.Client;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by codecadet on 01/03/17.
 */
public class Manager {

    private static CopyOnWriteArrayList<Client> clientList;



    public static CopyOnWriteArrayList<Client> getClientList() {
        return clientList;
    }

    public void init() {

        clientList = new CopyOnWriteArrayList<>();
    }
}
