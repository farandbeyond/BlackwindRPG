/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Foreground.Shop;

import Background.Items.Inventory;
import Background.Items.Item;
import Background.Items.ItemLoader;
import Foreground.BlackwindTemp.Blackwind;
import Foreground.BlackwindTemp.Tile;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Connor
 */
public class Shop extends JPanel{
    //ArrayList<Item> sellingItems;
    
    private boolean confirmEvent, cancelEvent;
    private int selectorPosition, selectorMaxPos;
    private boolean selectorVisible;
    private boolean gettingQuantity, inventoryVisible;
    private int itemNumber;
    private Item selectedItem;
    private Inventory shopContents;
    private final int distFromTop = 50, distFromLeft = 30;
    private int gold;
    
    Blackwind engine;
    shopDisplay display;
    public Shop(ArrayList<Integer> inventory, Blackwind b){
        engine = b;
        shopContents = new Inventory(99);
        for(int i:inventory){
            shopContents.add(ItemLoader.loadItem(i, 1));
        }
        display = new shopDisplay(shopContents);
        confirmEvent = false;
        cancelEvent = false;
        inventoryVisible = false;
        gettingQuantity = false;
        itemNumber = 0;
        selectorPosition = 0;
        selectorMaxPos = 2;
        selectorVisible = true;
        gold = 0;
        this.setLayout(null);
        this.setSize(640,480);
        this.setLocation(Tile.tileSize, Tile.tileSize);
        this.setVisible(true);
        this.setPreferredSize(new Dimension(641,481));
    }    
    
    public void resetEvents(){
        cancelEvent = false;
        confirmEvent = false;
    }
    public void confirmMenuPosition(){
        if(selectorPosition<0)
            selectorPosition = selectorMaxPos;
        if(selectorPosition>selectorMaxPos)
            selectorPosition = 0;
    }
    public void confirmItemNumber(){
        if(Blackwind.getInventory().contains(selectedItem.getId())){
            Item item = Blackwind.getInventory().getItem(Blackwind.getInventory().getLocationOf(selectedItem.getId()));
            if(itemNumber>selectedItem.getMaxQuantity()-item.getQuantity())
                itemNumber = selectedItem.getMaxQuantity()-item.getQuantity();
            if(selectedItem.getShopValue()*itemNumber>gold){
                itemNumber--;
                confirmItemNumber();
            }else if(itemNumber<0){
                itemNumber = 0;
            }
        }else{
            if(itemNumber>selectedItem.getMaxQuantity())
                itemNumber = selectedItem.getMaxQuantity();
            if(selectedItem.getShopValue()*itemNumber>gold){
                itemNumber--;
                confirmItemNumber();
            }else if(itemNumber<0){
                itemNumber = 0;
            }
        }
    }
    public void loop(int gold){
        this.gold = gold;
        while(!cancelEvent){
            //System.out.println("Looping");
            resetEvents();
            repaint();
            confirmMenuPosition();
            if(confirmEvent){
                switch(selectorPosition){
                    case 0:buyMenu();break;
                    case 1:break;
                    case 2:cancelEvent = true;break;
                }
            }
        }
        System.out.println("exit");
    }
    public void repaint(){
        try{
            //System.out.println("Shop is painting");
            engine.repaint();
        }catch(NullPointerException e){
            //System.out.println("Shop engine null");
        }
    }
    //buy loop
    public void buyMenu(){
        inventoryVisible = true;
        resetEvents();
        while(!cancelEvent){
            resetEvents();
            repaint();
            display.confirmOffsetMenuPosition();
            if(confirmEvent){
                selectedItem  = display.getSelectedItem();
                buyItem();
            }
        }
        resetEvents();
        inventoryVisible = false;
    }
    public void buyItem(){
        gettingQuantity = true;
        while(!cancelEvent){
            resetEvents();
            repaint();
            confirmItemNumber();
            if(confirmEvent){
                Blackwind.getInventory().add(ItemLoader.loadItem(selectedItem.getId(), itemNumber));
                gold-= itemNumber*selectedItem.getShopValue();
                cancelEvent = true;
            }
            
        }
        itemNumber = 0;
        gettingQuantity = false;
        resetEvents();
    }
    
    public void confirmEvent(){confirmEvent = true;}
    public void cancelEvent(){cancelEvent = true;}
    public void upEvent(){
        if(gettingQuantity)
            itemNumber++;
        else if(inventoryVisible)
            display.adjustMenuPosition(-1);
        else
            selectorPosition--;
    }
    public void downEvent(){
        if(gettingQuantity)
            itemNumber--;
        else if(inventoryVisible)
            display.adjustMenuPosition(+1);
        else
            selectorPosition++;}
    public void leftEvent(){
        if(gettingQuantity)
            itemNumber-=10;
        }
    public void rightEvent(){
        if(gettingQuantity)
            itemNumber+=10;
    }
    
    public void paint(Graphics g){
        g.setFont(new Font("Courier New", Font.BOLD, 20));
        
        //cyan background
        g.setColor(new Color(0,255,255));
        g.fillRect(0, 0, 640, 480);
        g.setColor(Color.black);
        g.drawRect(0, 0, 640, 480);
        //box containing gold
        g.setColor(new Color(150,255,0));
        g.fillRect(0, 0, 200, 75);
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, 200, 75);
        g.drawString(gold+"g",50,30);
        //box containing buy/sell/exit options
        g.setColor(new Color(200,255,100));
        g.fillRect(0, 250, 200, 200);
        g.setColor(Color.black);
        g.drawRect(0, 250, 200, 200);
        //menu options and selector
        g.drawString("Buy", 50, 300);
        g.drawString("Sell", 50, 340);
        g.drawString("Exit", 50, 380);
        g.drawString(">", 0, selectorPosition*40+300);
        
        if(inventoryVisible)
            display.paint(g);
        if(gettingQuantity){
            //quantity box
            g.setColor(new Color(100,100,255));
            g.fillRect(0, 100, 200, 150);
            g.setColor(Color.black);
            g.drawRect(0,100,200,150);
            //quantity box info
            g.drawString(selectedItem.getName(), 10, 120);
            g.drawString(selectedItem.getShopValue()+"g", 10, 150);
            g.drawString(String.format("%dx%dg = %dg",itemNumber, selectedItem.getShopValue(),itemNumber*selectedItem.getShopValue()), 10, 180);
        }
        
    }
            public class shopDisplay{
                Inventory displayedInv;
                private int currOffset, maxOffset;
                private int selectorPosition, selectorMaxPos;
                private boolean selectorVisible;
                public shopDisplay(Inventory i){
                    displayedInv = i;
                    currOffset = 0;
                    selectorPosition = 0;
                    selectorMaxPos = displayedInv.getNumberOfItemsInInventory()<9?displayedInv.getNumberOfItemsInInventory()-1:9;
                    selectorVisible = true;
                    setMaxOffset();
                }
                private void setMaxOffset(){
                    maxOffset = 0;
                    try{
                        int i = selectorMaxPos+1;
                        while(true){
                            displayedInv.getItem(i);
                            i++;
                            maxOffset++;
                        }
                    }catch(IndexOutOfBoundsException e){
                        System.out.printf("Max offset is %d, from an inventory size %d, containing %d items\n",maxOffset,displayedInv.getInvSize(),displayedInv.getNumberOfItemsInInventory());
                    }
                }
                public void setDisplayedInv(Inventory i){
                    displayedInv = i;
                    setMaxOffset();
                }
                public void adjustMenuPosition(int adjust){
                    selectorPosition+=adjust;
                }
                public void confirmOffsetMenuPosition(){
                    if(currOffset<maxOffset && selectorPosition==7){
                        currOffset++;
                        selectorPosition--;
                    }else if(currOffset>0 && selectorPosition==4){
                        currOffset--;
                        selectorPosition++;
                    }else if(selectorPosition<0){
                        currOffset = maxOffset;
                        selectorPosition = selectorMaxPos;
                    }else if(selectorPosition>selectorMaxPos){
                        currOffset = 0;
                        selectorPosition = 0;
                    }
                    //if(selectorPosition<0)
                    //   selectorPosition = selectorMaxPos;
                    //if(selectorPosition>selectorMaxPos)
                    //    selectorPosition = 0;
                    
                }
                
                public Inventory getLoadedInv(){return displayedInv;}
                public Item getSelectedItem(){return displayedInv.getItem(selectorPosition+currOffset);}
                public void paint(Graphics g){
                    //green item holding area
                    g.setColor(new Color(0,255,0)); // int red, int green, int blue
                    g.fillRect(200, 0, 440, 480);
                    g.setColor(Color.black);
                    g.drawRect(200, 0, 440, 480);

                    
                    for(int i=0;i<10;i++){
                        try{
                            g.drawString(shopContents.getItem(i+currOffset).getName(), 230, i*40+distFromTop);
                            g.drawString(shopContents.getItem(i+currOffset).getShopValue()+"g", 550, i*40+distFromTop);
                        }catch(IndexOutOfBoundsException e){
                            g.drawString("---", 230, i*40+distFromTop);
                            g.drawString("--g", 550, i*40+distFromTop);
                        }
                    }
                    if(selectorVisible){
                        g.drawString(">", 210, selectorPosition*40+distFromTop);
                    }
                }
            }
            
    public static void main(String[] args) {
        Blackwind.main(args);
    }
    
}
