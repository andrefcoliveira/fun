package org.academiadecodigo.bytenavoid;

import org.academiadecodigo.bytenavoid.server.Server;
import org.academiadecodigo.bytenavoid.util.FileManager;
import org.academiadecodigo.bytenavoid.util.FileType;
import org.academiadecodigo.bytenavoid.util.Manager;

import java.net.URISyntaxException;
import java.util.Scanner;

/**
 * Created bys codecadet on 01/03/17.
 */
public class Main {

    public static void main(String[] args) throws URISyntaxException {
/*

        FileManager.createFile(FileType.CLIENT);
        FileManager.createFile(FileType.RESERVATION);
        FileManager.createFile(FileType.FACILITY);
*/


        Manager.init();

        Scanner scanner = new Scanner(System.in);
        System.out.println("(S)erver | (U)ser?");
        String serverOrClient = scanner.nextLine().toUpperCase();

        switch (serverOrClient) {

            case "U":
                Terminal terminal = new Terminal();
                terminal.start();
                break;
            case "S":
                Server server = new Server();
                server.init();
                break;
            default:
                main(args);

        }

    }
}
