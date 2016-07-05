/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Foreground.Events;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Connor
 */
public class EventReader {
    
    
    public static Event loadEvent(String fileName){
        try {
            //Event e = new Event();
            String filePath = String.format("Events/testevent.txt");
            InputStream input;

            input = new FileInputStream(filePath);

            InputStreamReader inputReader = new InputStreamReader(input);
            BufferedReader fileReader = new BufferedReader(inputReader);
            String line;


            return null;
        }catch (FileNotFoundException ex) {
            System.out.printf("File %s not found\n",fileName);
            return null;
        }
    }
}
