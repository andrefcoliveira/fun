package org.academiadecodigo.bytenavoid;

import java.util.Scanner;

/**
 * Created by codecadet on 01/03/17.
 */
public class LogIn {

    private Scanner scanner;


    public void init() {

        scanner = new Scanner(System.in);

    }




    private void logInClient() {
        String answer;

        System.out.println("Please, enter your name: ");
        answer = scanner.nextLine();


        if (clientExists()) {

            while (!passwordCorrect(answer)) {

                System.out.println("Please, enter your password: ");
                answer = scanner.nextLine();
            }
            chooseAction();
        }
    }

    private void chooseAction() {
        String answer = "";
        System.out.println("What do you want to do? \n " +
                "Make a (R)eservation \n" + "(M)anage a reservation \n"
                + "(C)ancell a reservation ");

        switch (answer) {
            case "R":
                makeReservation();
                break;
            case "M":
                manageReservation();
                break;
            case "C":
                cancellReservation();
                break;
            default:
                errorMessage();
        }
    }

    private void makeReservation() {
        String answer = "";
        System.out.println("What kind of facility do you want to book? \n" +
                "(1) Soccer \n" + "(2) Tennis \n" + "(3) Swimming pool \n" +
                "(4) Volley \n" + "(5) Running \n" + "(6) Sports Space " );

        switch (answer) {
            case "1":
                choose(EntityType.SOCCER);
        }

    }

}
