/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Foreground.BlackwindTemp;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 *
 * @author Connor
 */
public class EventFlags {
    static int numberOfFlags = 1;
    private static boolean[] flags;
    public static void startUp(){
        flags = new boolean[numberOfFlags];
        for(int i=0;i<numberOfFlags;i++){
            flags[i]=false;
        } 
    }
    public static void saveGame(){
        try{
            FileWriter partyWrite = new FileWriter("save/flags.txt", false);
            PrintWriter writeline = new PrintWriter(partyWrite);
            for(boolean b:flags){
                writeline.printf("%b%n",b);
            }
            writeline.close();
        }catch(IOException e){
            
        }
    }
    public static void loadGame(){
        try{
            String line = "";
            ArrayList<String> contents = new ArrayList<>();
            String filePath = "save/flags.txt";
            InputStream input = new FileInputStream(filePath);
            InputStreamReader inputReader = new InputStreamReader(input);
            BufferedReader fileReader = new BufferedReader(inputReader);
            try{
                while((line = fileReader.readLine())!=null){
                   contents.add(line);
                }
            for(int i=0;i<numberOfFlags;i++){
                if(contents.get(i).equals("true"))
                    flags[i]=true;
                else
                    flags[i]=false;
            }
            }catch(IOException o){

            }
        }catch(FileNotFoundException e){
            
        }
    }
    
    public static void triggerFlag(int flagNumber){
        flags[flagNumber] = true;
    }
    public static boolean checkFlag(int flagNumber){
        return flags[flagNumber];
    }
}
