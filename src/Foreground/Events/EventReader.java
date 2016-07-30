/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Foreground.Events;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Connor
 */
public class EventReader {
    
    
    public static Event loadEvent(String fileName, String mapNameRough){
        try {
            String mapName = mapNameRough.split(".txt")[0];
            //System.out.println(String.format("Events/%s/%s.txt",mapName,fileName));
            String filePath = String.format("Events/%s/%s.txt",mapName,fileName);
            InputStream input;

            input = new FileInputStream(filePath);

            InputStreamReader inputReader = new InputStreamReader(input);
            BufferedReader fileReader = new BufferedReader(inputReader);
            String line;
            
            Event eve = new Event(true,"");
            try{
                //if file starts with the word repeatable:
                if(fileReader.readLine().equals("repeatable")){
                    eve = new Event(true,"");
                }else{
                    eve = new Event(false,"");
                }
                //while the file is not over
                while((line=fileReader.readLine())!=null){
                    //System.out.println(line);
                    if(line.charAt(0)!=';'){
                        //if the segment is a text event
                        if(line.equals("-text")){
                            String[] text = {" "," "," "," "};
                            for(int i=0;i<4;i++){
                                //read the next line
                                text[i]=fileReader.readLine();
                                //if it starts with a '-' character (eg. new event segment)
                                if(text[i].charAt(0)=='-'){
                                    //make the line blank
                                    text[i]=" ";
                                    i=4;
                                }
                            }
                            //send the up to four line into the segment and event
                            eve.addSegment(new TextSegment(text[0],text[1],text[2],text[3]));
                        }
                        //if the segment is an item event
                        else if(line.equals("-item")){
                            eve.addSegment(loadItemSegment(fileReader.readLine()));
                        }
                        else if(line.equals("-pmem")){
                            eve.addSegment(loadPartyMemberSegment(fileReader.readLine()));
                        }
                        else if(line.equals("-movemc")){
                            eve.addSegment(loadMCMovementSegment(fileReader.readLine()));
                        }
                        else if(line.equals("-movenpc")){
                            eve.addSegment(loadSpriteMovementSegment(fileReader.readLine(),fileReader.readLine()));
                        }
                        else if(line.equals("-post")){
                            eve.addSegment(new PostEventSegment(fileReader.readLine()));
                        }
                        else if(line.equals("-rest")){
                            eve.addSegment(new RestSegment());
                        }
                        else if(line.equals("-battle")){
                            String Line = fileReader.readLine();
                            eve.addSegment(new BattleSegment(Integer.parseInt(Line.split("/")[0]),Integer.parseInt(Line.split("/")[1]),Integer.parseInt(Line.split("/")[2])));
                        }
                        //if the file is not labelled properly
                        else{
                            eve.addSegment(new TextSegment("Error Loading","","",""));
                        }
                    }
                }
            }catch(IOException e){
                System.out.printf("Error Occured reading %s\n",fileName);
            }catch(NullPointerException e){
                
            }
            eve.setName(fileName.split(".txt")[0]);
            return eve;
        }catch (FileNotFoundException ex) {
            System.out.printf("File %s not found\n",fileName);
            return null;
        }
    }
    public static ItemSegment loadItemSegment(String itemInfo){
        String[] itemInfoArray = itemInfo.split("x");
        return new ItemSegment(Integer.parseInt(itemInfoArray[0]),Integer.parseInt(itemInfoArray[1]));
    }
    public static PartyMemberSegment loadPartyMemberSegment(String memberID){
        return new PartyMemberSegment(Integer.parseInt(memberID));
    }
    public static PlayerMovingSegment loadMCMovementSegment(String movementVector){
        String[] xy = movementVector.split("/");
        return new PlayerMovingSegment(Integer.parseInt(xy[0]),Integer.parseInt(xy[1]));
    }
    public static SpriteMovingSegment loadSpriteMovementSegment(String spriteName, String moveVector){
        String[] xy = moveVector.split("/");
        return new SpriteMovingSegment(spriteName,Integer.parseInt(xy[0]),Integer.parseInt(xy[1]));
    }
}
