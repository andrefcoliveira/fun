package org.academiadecodigo.bytenavoid.server;

import org.academiadecodigo.bytenavoid.client.ClientHandler;
import org.academiadecodigo.bytenavoid.facility.FacilityHandler;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by codecadet on 01/03/17.
 */
public class Server {

    private static final int PORT_NUM = 9090;

    public void init() {

        ServerSocket serverSocket = null;
        Socket socket = null;

        try {
            serverSocket = new ServerSocket(PORT_NUM);

        } catch (IOException e) {
            System.out.println("Port not working!");
            e.printStackTrace();

        }

        while (true) {

            waitForClient(serverSocket, socket);
        }
    }

    private void waitForClient(ServerSocket serverSocket, Socket socket) {

        try {
            System.out.println(fundao());
            System.out.println("\nServer ready...");
            socket = serverSocket.accept();

        } catch (IOException e) {
            System.out.println("Could not get Socket!");
            e.printStackTrace();
        }

        InitHandler initHandler = new InitHandler(socket);
        Thread thread = new Thread(initHandler);
        thread.start();

    }

    public String fundao() {
         return
                 "   MMMMMMMMM   MMM    MMMM   MMMM    MMM         MMM     M ZMM+   \n" +
                         "   MMM         MMM    MMMM   MMMMN   MMM         MMM               \n" +
                         "   MMM         MMM    MMMM   MMMMM   MMM    MMMMMMMM    MMMMMMM     MMMMMMM\n" +
                         "   MMMMMMMM    MMM    MMMM   MMM~MM  MMM   MMM   MMM         MMM   MMM   MMM,\n" +
                         "   MMMMMMMM    MMM    MMMM   MMM MMM MMM  MMM?   MMM    NMMMMMMM  MMM?   ?MMM\n" +
                         "   MMM         MMM    MMMI   MMM  MMMMMM  MMM+   MMM   MMM   MMM  MMM+   +MMM\n" +
                         "   MMM         MMMM  MMMM    MMM   MMMMM   MMM   MMM   MMM   MMM   MMM   MMM\n" +
                         "   MMM          MMMMMMMM     MMM    MMMM    MMMMMMMM   DMMMMMMMM    MMMMMMM\n";

    }

    private class InitHandler implements Runnable {
        private Socket socket;

        public InitHandler(Socket socket) {
            this.socket = socket;
        }


        @Override
        public void run() {
            System.out.println("Init Handler");
            PrintWriter output = null;
            BufferedReader input = null;
            try {
                output = new PrintWriter(socket.getOutputStream(), true);
                input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }

            String answer = "";

            output.println(fundao());

            output.println("(C)lient | (F)acility | e(X)it");
            try {
                answer = input.readLine().toUpperCase();

            } catch (IOException e) {
                e.printStackTrace();
            }

            if (!answer.equals("X")) {

                switch (answer) {
                    case "C":
                        ClientHandler clientHandler = new ClientHandler(socket);
                        clientHandler.startClientAccess();
                        break;

                    case "F":
                        FacilityHandler facilityHandler = new FacilityHandler(socket);
                        facilityHandler.startFacilityAccess();
                        break;

                    case "X":
                        return;

                    default:
                        output.println("Please enter a valid option.");
                        run();
                }
            }

            //closes socket after all methods are out of the stack
            try {
                output.println("*** Exiting... ***");
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}




