package org.academiadecodigo.bytenavoid.util;

import org.academiadecodigo.bytenavoid.client.Client;
import org.academiadecodigo.bytenavoid.facility.Facility;
import org.academiadecodigo.bytenavoid.facility.FacilityType;
import org.academiadecodigo.bytenavoid.reservation.Reservation;

import java.io.*;
import java.util.LinkedList;

/**
 * Created by codecadet on 01/03/17.
 */
public abstract class FileManager {

//getClass().getProtectionDomain().getCodeSource().getLocation().toURI().getPath()

    public static void saveFile(LinkedList list, FileType type) {

        try {

            ObjectOutputStream oOS = new ObjectOutputStream(new FileOutputStream(type.getFilename()));

            for (Object obj:list) {

                oOS.writeObject(obj);

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static LinkedList<Object> loadFile(FileType type) {

        LinkedList<Object> list = null;
        try {
            ObjectInputStream oIS = new ObjectInputStream(new FileInputStream(type.getFilename()));

            list = new LinkedList();

            Object obj;

            while (!(obj = oIS.readObject()).equals(-1)) {
                list.add(obj);
            }

            return list;

        } catch (EOFException e) {
          return list;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        return null;
    }

}
