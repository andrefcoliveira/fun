package org.academiadecodigo.bytenavoid.util;
import java.io.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by codecadet on 01/03/17.
 */
public abstract class FileManager {

//getClass().getProtectionDomain().getCodeSource().getLocation().toURI().getPath()


    /**
     * Recieves a CopyOnWriteArrayList of a file type and store every object in a .tmp
     * file, in order to save the intel, but first, delete the previous existing file
     * @param list
     * @param type
     */
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

    /**
     * Given the FileType, access its specified directory and takes the present objects
     * in the file to a CopyOnWriteArrayList
     * @param type
     * @return the CopyOnWriteArrayList of the specified type of object
     */
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
