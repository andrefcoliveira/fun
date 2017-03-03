package org.academiadecodigo.bytenavoid.util;
import java.io.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by codecadet on 01/03/17.
 */
public abstract class FileManager {

//getClass().getProtectionDomain().getCodeSource().getLocation().toURI().getPath()

    public static void saveFile(CopyOnWriteArrayList list, FileType type) {

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

    public static CopyOnWriteArrayList loadFile(FileType type) {

        CopyOnWriteArrayList<Object> list = null;
        try {
            ObjectInputStream oIS = new ObjectInputStream(new FileInputStream(type.getFilename()));

            list = new CopyOnWriteArrayList();

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
