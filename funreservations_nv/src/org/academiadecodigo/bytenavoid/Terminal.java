package org.academiadecodigo.bytenavoid;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by codecadet on 04/03/17.
 */
public class Terminal {

    private static String host = getHost();
    private static int port = 9090;
    private static Socket clientSocket = null;
    private static PrintWriter out = null;
    private static BufferedReader in = null;
    private static String line;


    public void start() {

        try {
            System.out.println(
                    "   MMMMMMMMM   MMM    MMMM   MMMM    MMM         MMM     M ZMM+   \n" +
                            "   MMM         MMM    MMMM   MMMMN   MMM         MMM               \n" +
                            "   MMM         MMM    MMMM   MMMMM   MMM    MMMMMMMM    MMMMMMM     MMMMMMM\n" +
                            "   MMMMMMMM    MMM    MMMM   MMM~MM  MMM   MMM   MMM         MMM   MMM   MMM,\n" +
                            "   MMMMMMMM    MMM    MMMM   MMM MMM MMM  MMM?   MMM    NMMMMMMM  MMM?   ?MMM\n" +
                            "   MMM         MMM    MMMI   MMM  MMMMMM  MMM+   MMM   MMM   MMM  MMM+   +MMM\n" +
                            "   MMM         MMMM  MMMM    MMM   MMMMM   MMM   MMM   MMM   MMM   MMM   MMM\n" +
                            "   MMM          MMMMMMMM     MMM    MMMM    MMMMMMMM   DMMMMMMMM    MMMMMMM");

            System.out.println("+-----------------------------------------------------------------------------+\n" +
                               "|                      Welcome to FUNdão Reservations:                        |\n" +
                    "+-----------------------------------------------------------------------------+\n");

            clientSocket = new Socket(host, port);
            out = new PrintWriter(clientSocket.getOutputStream(), true);

            Thread thread = new Thread(new MessageReceiver());
            thread.start();

            while (!clientSocket.isClosed()) {
                line = getMessage();
                out.println(line);
                out.flush();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getMessage() {

        Scanner reader = new Scanner(System.in);
        return reader.nextLine();
    }

    public static String getHost() {

        System.out.println("Please insert Server IP address: ");
        Scanner reader = new Scanner(System.in);
        return reader.nextLine();
    }


    public static class MessageReceiver implements Runnable {

        public MessageReceiver() {
            try {
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            try {
                while (!clientSocket.isClosed()) {

                    String message = in.readLine();
                    if (message == null) {
                        break;
                    }

                    System.out.println(message);
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {

                    if (clientSocket != null) {
                        clientSocket.close();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}


