package org.academiadecodigo.bytenavoid;

import org.academiadecodigo.bytenavoid.server.Server;
import org.academiadecodigo.bytenavoid.util.Manager;

/**
 * Created by codecadet on 01/03/17.
 */
public class Main {

    public static void main(String[] args) {

        Manager manager = new Manager();
        manager.init();

        Server server = new Server();
        server.init();

    }
}
