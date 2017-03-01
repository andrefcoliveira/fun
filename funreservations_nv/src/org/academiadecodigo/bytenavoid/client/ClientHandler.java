package org.academiadecodigo.bytenavoid.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by codecadet on 01/03/17.
 */
public class ClientHandler {

    private Socket socket;
    private PrintWriter output = null;
    private BufferedReader input = null;

    public ClientHandler(Socket socket) {
        this.socket = socket;
        try {
            output = new PrintWriter(socket.getOutputStream(), true);
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void startClientAccess() {

        String answer;

        System.out.println("(L)og In or (S)ign Up? ");
        answer = scanner.nextLine();

        switch (answer) {
            case "L":
                logInClient();
                break;

            case "S":
                signUpClient();
                break;

            default:
                errorMessage();


        }
    }


}
