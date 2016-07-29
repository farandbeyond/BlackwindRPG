/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Foreground.BlackwindTemp;

import Foreground.Events.Event;
import Foreground.Events.EventReader;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Connor
 */
public class Sprite {
    //a spritesheet dictionary will be put in up here soon. once connor is less lazy. until then, squares/circles
    public static final int COLUMNS = 10, ROWS = 5;
    
    public static final int numberOfSprites = 4;
    public static String[] spriteNames;
    public static BufferedImage[] spriteSheets;
        
    public static void startUp(){
        System.out.println("Loading Sprites");
        spriteNames = new String[numberOfSprites];
        spriteSheets = new BufferedImage[numberOfSprites];
        addSprite(0,"Test Dummy","testDummy");
        addSprite(1,"Male Villager","villager1");
        addSprite(2,"Female Villager","villager2");
        addSprite(3,"Slutty Villager","villager3");
        System.out.println("Sprites Loaded Successfully");
    }
    public static void addSprite(int id, String name, String fileName){
        try{
            spriteSheets[id]=ImageIO.read(new File(String.format("images/spriteSheets/%s.png",fileName)));
            spriteNames[id]=name;
        }catch(IOException e){
            System.out.printf("Error occured reading file %s\n",fileName);
        }
    }
    
    public static int pixelsMoved = 1;
    //the event is the event linked to the current entity. this will be represented by an ID or String in save data, referencing a file
    Event e;
    //moving direction and facing direction control which sprite the sprite paits itself as. moving when moving, facing when not.
    int facingDirection, movingDirection;
    int aniCycle, cyclePause;
    boolean moving;
    //globalX and globalY are where the sprite paints itself. mapX and mapY are the tile is is linked to. screenX and screenY represent where the sprite is onscreen in relation to loaded map tiles (used only in MC for now)
    int mapX, mapY, screenX, screenY, globalX, globalY;
    //these are usable during events, and for our own clarity
    int id;
    String name;
    String eventFileName;
    
    
    Sprite(int id, String name, int mapX, int mapY, int facingDirection){
        screenX = mapX;
        screenY = mapY;
        this.mapX = mapX;
        this.mapY = mapY;
        this.id = id;
        this.name = name;
        this.facingDirection = facingDirection;
        
        aniCycle = 0;
        moving = false;
        movingDirection = Blackwind.DOWN;
        
        cyclePause = 0;
        setGlobalX();
        setGlobalY();
    }
    Sprite(int id, String name, int mapX, int mapY, int facingDirection,String eventName,String mapName){
        screenX = mapX;
        screenY = mapY;
        this.mapX = mapX;
        this.mapY = mapY;
        this.id = id;
        this.name = name;
        this.facingDirection = facingDirection;
        
        aniCycle = 0;
        moving = false;
        movingDirection = Blackwind.DOWN;
        eventFileName = eventName;
        setEvent(EventReader.loadEvent(eventName,mapName));
        
        cyclePause = 0;
        setGlobalX();
        setGlobalY();
    }
    public void setGlobalX(){globalX = screenX*Tile.tileSize;}
    public void setGlobalY(){globalY = screenY*Tile.tileSize;}
    public void setMapX(){mapX = globalX/32;}
    public void setMapY(){mapY = globalY/32;}
    public void setMapX(int newX){mapX = newX;}
    public void setMapY(int newY){mapY = newY;}
    public void moveMC(int direction){
        this.facingDirection = direction;
        movingDirection = direction;
        switch(direction){
            case Blackwind.UP:   mapY--;break;
            case Blackwind.DOWN: mapY++;break;
            case Blackwind.RIGHT:mapX++;break;
            case Blackwind.LEFT: mapX--;break;
        }
        moving = true;
        aniCycle = 1;
    }
    public void move(int direction){
        //System.out.println("Moving 1");
        if(moving){
            moveTo();
            return;
        }
        //System.out.println("Moving 2");
        this.facingDirection = direction;
        movingDirection = direction;
        switch(direction){
            case Blackwind.UP:   mapY--;break;
            case Blackwind.DOWN: mapY++;break;
            case Blackwind.RIGHT:mapX++;break;
            case Blackwind.LEFT: mapX--;break;
        }
        moving = true;
        aniCycle = 1;
        //System.out.println("Moving 3");
        moveTo();
    }
    public void moveTo(){
        //System.out.println("Moving 4");
        
        switch(movingDirection){
            case Blackwind.UP: globalY--;break;
            case Blackwind.DOWN:globalY++;break;
            case Blackwind.RIGHT:globalX++;break;
            case Blackwind.LEFT:globalX--;break;
        }
        //System.out.printf("%d/%d",globalX,globalY);
        cycleSprite();
        if(globalY%32==0&&globalX%32==0){
            setMapX();
            setMapY();
            System.out.println("Moving stopped: Destination reached");
            moving=false;
        }
            
    }
    
    public void toggleIsWalking(){
        if(moving){
            moving=false;
            return;
        }
        moving = true;
    }
    public void setNewMapLocation(int newX, int newY){
        mapX = newX;
        mapY = newY;
    }
    public void setNewScreenLocation(int newX, int newY){
        screenX = newX;
        screenY = newY;
        setGlobalX();
        setGlobalY();
    }
    public void adjustGlobalXY(int newX, int newY){
        globalX+=newX;
        globalY+=newY;
    }
    public void setFacingDirection(int direction){
        facingDirection = direction;
    }
    public void faceAway(int direction){
        if(direction%2==0)
            direction--;
        else
            direction++;
        facingDirection = direction;
    }
    
    public void cycleSprite(){
        cyclePause++;
        if(cyclePause == 8){
            cyclePause = 0;
            aniCycle++;
        }
        if(aniCycle >= 4 && cyclePause == 7){
            cyclePause = 0;
            aniCycle = 0;
        }
    }
    public BufferedImage animate(int sheetRow){
        //this is a temp thing. it will be updated soon
        if(aniCycle!=0)
            cycleSprite();
        return getSprite();
    }
    
    public int getID(){return id;}
    
    public int getMapX(){return mapX;}
    public int getMapY(){return mapY;}
    public int getScreenX(){return screenX;}
    public int getScreenY(){return screenY;}
    public int getGlobalX(){return globalX;}
    public int getGlobalY(){return globalY;}
    public boolean isWalking(){return moving;}
    public int getDirection(){
        if(moving)
            return movingDirection;
        return facingDirection;
    }
    public String getName(){return name;}
    
    public Event getEvent(){return e;}
    public String getEventFileName(){return eventFileName;}
    
    public void setEvent(Event e){
        this.e = e;
    }
    public void setEventFileName(String e){eventFileName = e;}
    public void setName(String n){name = n;}
    public void setID(int id){this.id = id;}
    
    public boolean isDisplayed(int offX, int offY, int disW, int disH){
        if(mapX>offX&&mapX<disW+offX){
            if(mapY>offY&&mapY<disH+offY){
                return true;
            }
        }
        return false;
    }
    public boolean isAt(int x, int y, int direction){
        switch(direction){
            case Blackwind.UP:y--;break;
            case Blackwind.DOWN:y++;break;
            case Blackwind.LEFT:x--;break;
            case Blackwind.RIGHT:x++;break;
        }
        if(mapX==x&&mapY==y)
            return true;
        return false;
    }
    
    public BufferedImage getSprite(){
        return spriteSheets[id].getSubimage(
                1+(1+Tile.tileSize)*aniCycle, 
                1+(1+Tile.tileSize)*facingDirection, 
                Tile.tileSize, 
                Tile.tileSize);
        //for testing, you are represented by a void tile
        //return Tile.getImagesList()[0];
    }
    public static BufferedImage[] getSpritesheetList(){return spriteSheets;}
    
    public static void main(String[] args){
        Sprite.startUp();
    }
}
