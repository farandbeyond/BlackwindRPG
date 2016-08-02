/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Foreground.BlackwindTemp;


import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Connor
 */
public class Map {
    public static String loadedMapName = "unnamed.txt";
    public static void newGame(){
        System.out.println("moving maps to local");
        try{
            File mapsFolder = new File("maps");
            File[] allMaps = mapsFolder.listFiles();
            for(File f:allMaps){
                if(f.isFile()){
                    System.out.printf("Moving %s to local...", f.getName());
                    saveMap(loadMap(f.getName()),"local");
                }else{
                    System.out.println("Folder found: "+f.getName());
                }
            }
            System.out.println("Finished moving files");
        }catch(Exception e){
            System.out.println("Error Occurred moving files");
            System.out.println(e);
        }
    }
    public static Map loadMap(String mapname){
        return loadMap(mapname,"maps");
    }
    public static Map loadMap(String mapname, String folderName){
        loadedMapName= mapname;
        System.out.println(mapname);
        //try{
            try{
                //setup
                String line = "";
                ArrayList<String> contents = new ArrayList<>();
                String filePath = String.format("%s/%s",folderName,mapname);
                InputStream input = new FileInputStream(filePath);
                InputStreamReader inputReader = new InputStreamReader(input);
                BufferedReader fileReader = new BufferedReader(inputReader);
                //tile loading
                while((line=fileReader.readLine()).charAt(0)!='-'){
                        //System.out.println(line);
                        contents.add(line);
                }
                //for(String mapRow:contents)
                    //System.out.println(mapRow);
                String[] testLen = contents.get(0).split(" ");
                //tile writing
                Tile[][] tileIDs = new Tile[contents.size()][testLen.length];
                for(int x=0;x<tileIDs.length;x++){
                    for(int y=0;y<testLen.length;y++){
                        String tileInfo = contents.get(x).split(" ")[y];
                        //System.out.println(x+"/"+y);
                        try{
                            tileIDs[x][y] = new Tile(Integer.parseInt(tileInfo));
                        }catch(NumberFormatException e){
                            //System.out.printf("Non-Basic Tile found at %d/%d\n",x,y);
                            String[] tileDetails = tileInfo.split("/");
                            //warp tile
                            if(tileInfo.split("/")[1].equals("w")){
                                tileIDs[x][y] = new WarpTile(
                                        Integer.parseInt(tileDetails[0]),
                                        Integer.parseInt(tileDetails[3]),
                                        Integer.parseInt(tileDetails[4]),
                                        (tileDetails[2]));
                            }
                            //event tile
                            if(tileInfo.split("/")[1].equals("e")){
                                tileIDs[x][y] = new EventTile(
                                        Integer.parseInt(tileDetails[0]),
                                        (tileDetails[2]),
                                        mapname.split(".txt")[0]);
                            }
                        }
                    }
                }
                Map m = new Map(tileIDs);
                m.setName(mapname.split(".txt")[0]);
                System.out.println(m.getName());
                //sprite loading
                while((line=fileReader.readLine())!=null){
                    String spriteID = line;
                    String spriteName = fileReader.readLine();
                    
                    System.out.println(spriteName.split("=")[0]);
                    String spriteXY = fileReader.readLine();
                    int id = Integer.parseInt(spriteID.split(" ")[0]);
                    String[] xy = spriteXY.split("/");
                    int x = Integer.parseInt(xy[0]);
                    int y = Integer.parseInt(xy[1]);
                    m.addSprite(new Sprite(id,spriteName.split("=")[0],x,y,0,fileReader.readLine().split(" ")[0],mapname));
                }
                
                return m;
            }catch(IOException e){
                System.out.printf("Error Loading map %s\n",mapname);
                System.out.println(e);
                return null;
            }
        //}catch(Exception e){
        //    System.out.printf("Error Loading map %s\n",mapname);
        //    System.out.println(e);
        //    return null;
        //}
    }
    public static void saveMap(Map m){
        saveMap(m,"maps");
    }
    public static void saveMap(Map m, String folderName){
        //try{
            try{
                String filePath = String.format("%s/%s",folderName,loadedMapName);
                FileWriter path = new FileWriter(filePath, false);
                PrintWriter writeline = new PrintWriter(path);
                //System.out.printf("%d/%d",m.getX(),m.getY());
                for(int x=0;x<m.getX();x++){
                    String line = "";
                    for(int y=0;y<m.getY();y++){
                        //System.out.println(x+"/"+y);
                        line+=m.getTile(x, y).getDetails()+" ";
                    }
                    writeline.printf("%s%n",line);
                }
                writeline.printf("-%n");
                for(Sprite s:m.getSpriteList()){
                    writeline.printf("%d --SpriteID%n",s.getID());
                    writeline.printf("%s=--Sprite Name%n", s.getName());
                    writeline.printf("%d/%d/--spriteXY%n", s.getMapX(),s.getMapY());
                    writeline.printf("%s --eventName%n",s.getEventFileName());
                }
                writeline.close();
            }catch(IOException e){
                System.out.println("Error saving file "+loadedMapName);
                System.out.println(e);
            }
        //}catch(Exception e){
        //    
        //}
    }
    //int mapID;
    int mapWidth, mapHeight;
    String mapName;
    //int totalSprites;
    Tile[][] mapTiles;
    ArrayList<Sprite> sprites;
    
    
    //tiles will be saved by map [ x ] [ y ]. meaning, tile [1][0] would be at 32,0; etc.
    public Map(int[][] mapTiles){
        mapWidth = mapTiles.length;
        mapHeight = mapTiles[0].length;
        this.mapTiles = new Tile[mapWidth][mapHeight];
        for(int x=0;x<mapWidth;x++){
            for(int y=0;y<mapHeight;y++){
                //System.out.println(x+"/"+y);
                this.mapTiles[x][y] =  new Tile(mapTiles[x][y]);
            }
        }
        sprites = new ArrayList<>();
    }
    public Map(Tile[][] mapTiles){
        mapWidth = mapTiles.length;
        mapHeight = mapTiles[0].length;
        this.mapTiles = mapTiles;
        sprites = new ArrayList<>();
    }
    public Map(){
        mapWidth = 11;
        mapHeight = 11;
        mapTiles = new Tile[mapWidth][mapHeight];
        for(int x=0;x<mapWidth;x++){
            for(int y=0;y<mapHeight;y++){
                mapTiles[x][y] =  new Tile(0);
            }
        }
        sprites = new ArrayList<>();
    }
    
    public Tile[][] getMapSegment(int sx, int sy, int width, int height){
        Tile[][] returnTiles = new Tile[width+2][height+2];
        for(int x=0;       x<width+2;     x++){
            for(int y=0;   y<height+2;    y++){
                //try to grab selection
                try{
                    returnTiles[x][y] = mapTiles[x+sx-1][y+sy-1];
                }catch(IndexOutOfBoundsException e){
                    //if you go off the map, grab a void tile
                    returnTiles[x][y] = new Tile(0);
                }
            }
        }
        return returnTiles;
    }
    
    public Tile getTile(int x, int y){return mapTiles[x][y];}
    public Sprite getSprite(String spriteName){
        for(Sprite s: sprites){
            if(s.getName().equals(spriteName)){
                return s;
            }
        }
        //System.out.printf("Didn't find Sprite %s\n",spriteName);
        return null;
    }
    public boolean tileWalkable(int x, int y, int direction){
        try{
            switch(direction){
                case Blackwind.UP:y--;break;
                case Blackwind.DOWN:y++;break;
                case Blackwind.RIGHT:x++;break;
                case Blackwind.LEFT:x--;break;
            }
            return mapTiles[x][y].getWalkable();
        }catch(IndexOutOfBoundsException e){
            return false;
        }
    }
    public int getWidth(){return mapWidth;}
    public int getHeight(){return mapHeight;}
    
    public void addSprite(Sprite s){
        sprites.add(s);
        
    }
    
    public void changeTile(int x, int y, int tileID){
        mapTiles[x][y].setID(tileID);
    }
    public void setWidth(int w){
        int tilesDeleted=0;
        Tile[][] tempHold = mapTiles;
        mapTiles = new Tile[w][tempHold[0].length];
        for(int x=0;x<mapTiles.length;x++){
            for(int y=0;y<mapTiles[0].length;y++){
                try{
                    mapTiles[x][y] = tempHold[x][y];
                }catch(IndexOutOfBoundsException e){
                    mapTiles[x][y] = new Tile(0);
                    tilesDeleted++;
                }
            }
        }
        mapWidth = w;
        System.out.printf("Width set to %d. %d tiles added/deleted by this\n", w,tilesDeleted);
    }
    public void setHeight(int h){
        int tilesDeleted=0;
        Tile[][] tempHold = mapTiles;
        mapTiles = new Tile[tempHold.length][h];
        for(int x=0;x<mapTiles.length;x++){
            for(int y=0;y<mapTiles[0].length;y++){
                try{
                    mapTiles[x][y] = tempHold[x][y];
                }catch(IndexOutOfBoundsException e){
                    mapTiles[x][y] = new Tile(0);
                    tilesDeleted++;
                }
            }
        }
        mapHeight = h;
        System.out.printf("Height set to %d. %d tiles added/deleted by this\n", h,tilesDeleted);
    }
    public void setTile(int x, int y, Tile t){
        mapTiles[x][y] = t;
    }
    public void setName(String name){this.mapName = name;}
    public String getName(){return mapName;}
    public int getX(){return getWidth();}
    public int getY(){return getHeight();}
    public ArrayList<Sprite> getSpriteList(){return sprites;}
    
    public static void main(String[] args){
        Tile.startUp();
        //Map m = new Map();
        newGame();
    }
}
