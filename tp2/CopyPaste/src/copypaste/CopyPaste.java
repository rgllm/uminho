/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copypaste;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Samuel
 */
public class CopyPaste {


    public static void main(String[] args) {
        ArrayList<File> files = new ArrayList<>();
        files.add(new File("C:/Users/Samuel/Desktop/universidade/cc/cc/tp2/BackEndServer/dist/BackEndServer.jar"));
        files.add(new File("C:/Users/Samuel/Desktop/universidade/cc/cc/tp2/ReverseProxy/dist/ReverseProxy.jar"));
        files.add(new File("C:/Users/Samuel/Desktop/universidade/cc/cc/tp2/Client/dist/Client.jar"));

        String path = "C:/Users/Samuel/Desktop/universidade/cc/shared/";
        for(File file : files) {
            try {
                Files.copy(file.toPath(),
                        (new File(path + file.getName())).toPath(),
                        StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException ex) {
                Logger.getLogger(CopyPaste.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
