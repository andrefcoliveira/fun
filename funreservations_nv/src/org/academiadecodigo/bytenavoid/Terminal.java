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


