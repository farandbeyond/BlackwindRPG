/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Foreground.BlackwindTemp;

import Background.Items.Inventory;
import Background.Party.Party;

/**
 *
 * @author Connor
 */
public class WarpTile extends Tile{
    int newPlayerX, newPlayerY;
    String newMapName;

    public WarpTile(int id,int newPlayerX, int newPlayerY, String newMapName) {
        super(id);
        this.newPlayerX = newPlayerX;
        this.newPlayerY = newPlayerY;
        this.newMapName = newMapName;
    }

    @Override
    public void activate(Blackwind b,Sprite mc, Map m, Party p, Inventory i) {
        //m = Map.loadMap(newMapName);
        b.loadMap(newMapName);
        b.setNewMapOffset(newPlayerX, newPlayerY);
        mc.setNewMapLocation(newPlayerX, newPlayerY);
        System.out.printf("Warped player to %d/%d on map %s\n",newPlayerX,newPlayerY,newMapName);
        //mc.setGlobalX();
        //mc.setGlobalY();
        //mc.setNewScreenLocation(newPlayerX, newPlayerY);
    }
    @Override
    public String getDetails(){return String.format("%d/w/%s/%d/%d", getID(),newMapName,newPlayerX,newPlayerY);}
    
    public String getNewMapName(){return newMapName;}
    public int getWarpX(){return newPlayerX;}
    public int getWarpY(){return newPlayerY;}
    
    public void setNewMapName(String newMapName){this.newMapName = newMapName;}
    public void setWarpX(int newX){newPlayerX = newX;}
    public void setWarpY(int newY){newPlayerY = newY;}
}
