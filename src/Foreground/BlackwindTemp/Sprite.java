/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Foreground.BlackwindTemp;

import Foreground.Events.Event;
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
    
    public static final int numberOfSprites = 1;
    public static String[] spriteNames;
    public static BufferedImage[] spriteSheets;
        
    public static void startUp(){
        spriteNames = new String[numberOfSprites];
        spriteSheets = new BufferedImage[numberOfSprites];
        addSprite(0,"Test Dummy","testDummy");
        
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
    int aniCycle;
    boolean moving;
    //globalX and globalY are where the sprite paints itself. mapX and mapY are the tile is is linked to. screenX and screenY represent where the sprite is onscreen
    int mapX, mapY, screenX, screenY, globalX, globalY;
    //these are usable during events, and for our own clarity
    int id;
    String name;
    
    
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
        
        setGlobalX();
        setGlobalY();
    }
    public void setGlobalX(){globalX = screenX*Tile.tileSize;}
    public void setGlobalY(){globalY = screenY*Tile.tileSize;}
    
    /*public void move(int direction){
        switch(movingDirection==Blackwind.STILL?direction:movingDirection){
            case Blackwind.DOWN:
                globalY+=pixelsMoved;
                
                break;
            case Blackwind.UP:
                globalY-=pixelsMoved;
                
                break;
            case Blackwind.RIGHT:
                
                globalX+=pixelsMoved;
                break;
            case Blackwind.LEFT:
                
                globalX-=pixelsMoved;
                break;
        }
        if(!moving){
            facingDirection = direction;
            movingDirection = direction;
            moving = true;
            switch(direction){
                case Blackwind.DOWN:mapY++;break;
                case Blackwind.UP:mapY--;break;
                case Blackwind.LEFT:mapX--;break;
                case Blackwind.RIGHT:mapX++;break;
            }
        }
        if(globalX%Tile.tileSize==0&&globalY%Tile.tileSize==0){
            moving=false;
            movingDirection = Blackwind.STILL;
        }
    }*/
    public void move(int direction){
        this.facingDirection = direction;
        movingDirection = direction;
        switch(direction){
            case Blackwind.UP:   mapY--;break;
            case Blackwind.DOWN: mapY++;break;
            case Blackwind.RIGHT:mapX++;break;
            case Blackwind.LEFT: mapX--;break;
        }
        moving = true;
        //moveTo();
    }
    
    
    public void toggleIsWalking(){
        if(moving){
            moving=false;
            return;
        }
        moving = true;
    }
    public void setNewMapLocation(int newX, int newY){
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
    
    public BufferedImage animate(int sheetRow){
        //this is a temp thing. it will be updated soon
        return getSprite();
    }
    
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
    
    public void setEvent(Event e){
        this.e = e;
    }
    
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
        return spriteSheets[id].getSubimage(1+(1+Tile.tileSize)*aniCycle, 1+(1+Tile.tileSize)*facingDirection, Tile.tileSize, Tile.tileSize);
        //for testing, you are represented by a void tile
        //return Tile.getImagesList()[0];
    }
    
}
