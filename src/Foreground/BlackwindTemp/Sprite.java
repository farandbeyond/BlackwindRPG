/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Foreground.BlackwindTemp;

import Foreground.Events.Event;
import java.awt.image.BufferedImage;

/**
 *
 * @author Connor
 */
public class Sprite {
    //a spritesheet dictionary will be put in up here soon. once connor is less lazy. until then, squares/circles
    
    
    public static int pixelsMoved = 1;
    //the event is the event linked to the current entity. this will be represented by an ID or String in save data, referencing a file
    Event e;
    //moving direction and facing direction control which sprite the sprite paits itself as. moving when moving, facing when not.
    int facingDirection, movingDirection;
    boolean moving;
    //globalX and globalY are where the sprite paints itself. mapX and mapY are the tile is is linked to.
    int mapX, mapY, globalX, globalY;
    //these are usable during events, and for our own clarity
    int id;
    String name;
    
    
    Sprite(int id, String name, int mapX, int mapY, int facingDirection){
        this.mapX = mapX;
        this.mapY = mapY;
        this.id = id;
        this.name = name;
        this.facingDirection = facingDirection;
        
        moving = false;
        movingDirection = Blackwind.DOWN;
        
        setGlobalX();
        setGlobalY();
    }
    public void setGlobalX(){globalX = mapX*Tile.tileSize;}
    public void setGlobalY(){globalY = mapY*Tile.tileSize;}
    
    public void move(int direction){
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
    }
    
    public int getMapX(){return mapX;}
    public int getMapY(){return mapY;}
    public int getGlobalX(){return globalX;}
    public int getGlobalY(){return globalY;}
    
    public BufferedImage getImage(){
        //for testing, you are represented by a void tile
        return Tile.getImagesList()[0];
    }
    
}
