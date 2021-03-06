/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Foreground.Menu;

import Background.Items.*;
import Foreground.BlackwindTemp.Tile;
import java.awt.*;
import javax.swing.*;

/**
 *
 * @author Connor
 */
public class InventoryMenu extends JPanel{
    public static final int
            USE=0,
            EXAMINE=1,
            DROP=2;
    private int selectorPosition, selectorMaxPos;
    private boolean selectorVisible;
    private Inventory inv;
    private final int distFromTop = 50, distFromLeft = 30;
    private int currOffset, maxOffset;
    InventoryMenu(Inventory inv){
        this.inv=inv;
        currOffset=0;
        setMaxOffset();
        selectorPosition=0;
        selectorMaxPos = 9;
        selectorVisible=false;
    }
    public void setMaxOffset(){
        maxOffset=0;
        while(true){
            try{
                inv.getItem(maxOffset+10).toString();
                maxOffset++;
            }catch(IndexOutOfBoundsException e){
                System.out.println(String.format("max offset is %d, from an inventory size %d, containing %d items", maxOffset, inv.getInvSize(), inv.getNumberOfItemsInInventory()));
                return;
            }
        }
    }
    public void refreshInventory(){
        inv.updateInventory();
        setMaxOffset();
    }
    public void AddToInventory(Item itemToAdd){
        inv.add(itemToAdd);
    }
    public void dropItem(int position){
        inv.dropItem(position);
    }
    //gets
    public boolean canAdd(Item i){return inv.canAdd(i);}
    public Item getItemAtPosition(){
        return inv.getItem(selectorPosition+currOffset);
    }
    public int getSelectorMaxPosition(){return selectorMaxPos;}
    public int getSelectorPosition(){return selectorPosition;}
    public boolean isSelectorVisible(){return selectorVisible;}
    //selector controllers
    public int updateOffsetSelectorPosition(int newPos){
        //if you scroll down far enough and there are more options to load, scroll the inventory and add to the offset
        try{
            if(newPos!=selectorPosition){
                if(newPos==9){
                    currOffset = maxOffset;
                }
                if(newPos==0){
                    currOffset = 0;
                }
                //-1 is intened to throw null error if not exist

                if(newPos>=7&&inv.getItem(10+currOffset)!=null){
                    currOffset++;
                    //System.out.println(currOffset+"vv"+newPos);
                    return newPos-1;
                }
                if(newPos<=4&&inv.getItem(currOffset-1)!=null){
                    currOffset--;
                    //System.out.println(currOffset+"^^"+newPos);
                    return newPos+1;
                }

                selectorPosition = newPos;
            }
            //System.out.println(currOffset+">>"+newPos);
            return newPos;
        }catch(IndexOutOfBoundsException e){
            //System.out.println("Handling an error");
            
            //System.out.println(currOffset+">>"+newPos);
            selectorPosition = newPos;
            return newPos;
        }
    }
    public void toggleSelectorVisible(){
        if(selectorVisible){
            selectorVisible=false;
            return;
        }
        selectorVisible=true;
    }
    
    public void updateInventory(Inventory i){inv=i;refreshInventory();}
    
    public void paint(Graphics g){
        g.setColor(Color.cyan);
        g.fillRect(0, 0, 400, 480+Tile.tileSize);
        g.setColor(Color.black);
        g.drawRect(0, 0+Tile.tileSize, 400, 480+Tile.tileSize);
        g.setFont(new Font("Courier New", Font.BOLD, 20));
        for(int i=0;i<10;i++){
            try{
                g.drawString(inv.getItem(i+currOffset).getName(), distFromLeft, distFromTop+35*i);
                g.drawString("x"+inv.getItem(i+currOffset).getQuantity(), 350, distFromTop+35*i);
            }catch(IndexOutOfBoundsException e){
                g.drawString("---", distFromLeft, distFromTop+35*i);
                g.drawString("x-", 350, distFromTop+35*i);
            }
        }
        if(selectorVisible){
            g.drawString(">", 0, distFromTop+35*selectorPosition);
        }
    }
}
