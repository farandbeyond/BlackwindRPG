/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Foreground.BlackwindTemp;

import Background.Items.Inventory;
import Background.Party.Party;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Connor
 */
public class Tile{
    //a tile is a 32-bit ground sprite. it has an ID saved, refencing a "tile dictionary" for all of its proper data, including walkable, image, and name
    //global controllers: number of tiles the game allows
    private static int numberOfTiles = 11;
    public static int tileSize = 32;
    //static controllers: tile dictionary + startup initailizers
    private static BufferedImage[] dictTileImages = new BufferedImage[numberOfTiles];
    private static String[] dictTileNames = new String[numberOfTiles];
    private static boolean[] dictTileWalkable = new boolean[numberOfTiles];
    
    //private static int FLOOR=0, WALL=1;
    
    public static void startUp(){
        System.out.println("Tile Startup Commenced");
        try{
            loadTile(0,"Void",false,"void");
            loadTile(1,"Floor",true,"floor2");
            loadTile(2,"Wall",false,"wall2");
            loadTile(3,"Path",true,"testPath");
            loadTile(4,"HouseDoor",true,"YellowHouseDoor");
            loadTile(5,"HouseWall",false,"YellowHouseBlankWall");
            loadTile(6,"HouseWindow",false,"YellowHouseWindow");
            loadTile(7,"HouseRoofL",false,"YellowHouseRoofL");
            loadTile(8,"HouseRoofM",false,"YellowHouseRoofM");
            loadTile(9,"HouseRoofR",false,"YellowHouseRoofR");
            loadTile(10,"HouseFloor",true,"tempHouseFloor");
            System.out.println("Tiles Finished Loading Sucessfully");
        }catch(Exception e){
            System.out.println("Error Ocurred");
            System.out.println(e);
        }
        
    }
    public static void loadTile(int id, String name, boolean walkable, String fileName) throws IOException{
        dictTileNames[id] = name;
        dictTileWalkable[id] = walkable;
        dictTileImages[id] = ImageIO.read(new File(String.format("images/tiles/%s.png",fileName)));
        //System.out.println(String.format("images/tiles/%s.png, sucessfully loaded",fileName));
    }
    public static BufferedImage[] getImagesList(){return dictTileImages;}
    //instance controllers: Constructor, local id, etc
    private int id;
    public Tile(int id){
        this.id = id;
    }
    public void setID(int newID){
        id = newID;
    }
    
    public BufferedImage getImage(){return dictTileImages[id];}
    public String getName(){return dictTileNames[id];}
    public boolean getWalkable(){return dictTileWalkable[id];}
    public int getID(){return id;}
    
    public String getDetails(){return String.format("%d", id);}
    public void activate(Blackwind b,Sprite mc, Map m, Party p, Inventory i){};
    
    public static void main(String[] args){
        Tile.startUp();
    }

}
