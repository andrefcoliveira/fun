package org.academiadecodigo.bytenavoid.util;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by codecadet on 01/03/17.
 */
public abstract class FileManager {

//getClass().getProtectionDomain().getCodeSource().getLocation().toURI().getPath()


    public synchronized static void saveFile(CopyOnWriteArrayList list, FileType type) {

        try {

            File file = new File(type.getFilename());
            file.delete();


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

    public synchronized static CopyOnWriteArrayList loadFile(FileType type) {

        CopyOnWriteArrayList<Object> list = null;
        try {
            list = new CopyOnWriteArrayList<>();
            Object obj;

            ObjectInputStream oIS = new ObjectInputStream(new FileInputStream(type.getFilename()));

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
