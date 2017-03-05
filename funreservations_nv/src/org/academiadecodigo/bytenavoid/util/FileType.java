package org.academiadecodigo.bytenavoid.util;


import org.academiadecodigo.bytenavoid.Main;

import java.net.URISyntaxException;

/**
 * Created by codecadet on 02/03/17.
 */
public enum FileType {

    CLIENT("clients.tmp"),
    FACILITY("facilities.tmp"),
    RESERVATION("reservations.tmp");


    String filename;

    FileType(String filename) throws URISyntaxException {
        this.filename = filename;
    }

    public String getFilename() throws  URISyntaxException{

        //TODO prepare path to deploy on jar Builder
        //TODO handle exceptions on URI

        String path = Main.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
        return path + filename;
    }

}
