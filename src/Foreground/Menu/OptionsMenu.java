/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Foreground.Menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.*;

/**
 *
 * @author Connor
 */
public class OptionsMenu extends JPanel{
    public static final int
            INVENTORY=0,
            STATUS=1,
            SPELLS=2,
            EQUIPMENT=3,
            SWAPMEMBERS=4,
            SAVE=5;
    public static final int
            maxOptions=6,
            distFromLeft = 40,
            distFromTop = 50;
    private int selectorPosition, selectorMaxPos;
    private boolean selectorVisible;
    public int currentOptions;
    private String[] optionsDisplayed;
    
    
    public OptionsMenu(){
        optionsDisplayed = new String[6];
        loadMainMenuOptions();
        selectorPosition=0;
        selectorVisible = false;
        this.setVisible(true);
    }
    //selector controlling
    public void updateSelectorPosition(int newPos){
        selectorPosition = newPos;
    }
    public void toggleSelectorVisible(){
        if(selectorVisible){
            selectorVisible=false;
            return;
        }
        selectorVisible=true;
    }
    public void setSelectorVisible(){
        selectorVisible = true;
    }
    //gets
    public int getSelectorMaxPosition(){return selectorMaxPos;}
    public int getSelectorPosition(){return selectorPosition;}
    public boolean isSelectorVisible(){return selectorVisible;}
    //controls the options displayed
    public void loadMainMenuOptions(){
        optionsDisplayed[0]="Inventory";
        optionsDisplayed[1]="Status";
        optionsDisplayed[2]="Spells";
        optionsDisplayed[3]="Equipment";
        optionsDisplayed[4]="Swap Members";
        optionsDisplayed[5]="Save";
        selectorMaxPos=5;
    }
    public void loadInventoryMenuOptions(){
        optionsDisplayed[0]="Use";
        optionsDisplayed[1]="Equip";
        optionsDisplayed[2]="Examine";
        optionsDisplayed[3]="Drop";
        optionsDisplayed[4]="";
        optionsDisplayed[5]="";
        selectorMaxPos=3;
    }
    public void loadStatusMenuOptions(){
        optionsDisplayed[0]="Exit";
        optionsDisplayed[1]="";
        optionsDisplayed[2]="";
        optionsDisplayed[3]="";
        optionsDisplayed[4]="";
        optionsDisplayed[5]="";
        selectorMaxPos=0;
    }
    public void loadSpellsMenuOptions(){
        optionsDisplayed[0]="Cast";
        optionsDisplayed[1]="Description";
        optionsDisplayed[2]="Element";
        optionsDisplayed[3]="";
        optionsDisplayed[4]="";
        optionsDisplayed[5]="";
        selectorMaxPos=2;
    }
    public void loadEquipmentMenuOptions(){
        optionsDisplayed[0]="Equip";
        optionsDisplayed[1]="UnEquip";
        optionsDisplayed[2]="Description";
        optionsDisplayed[3]="";
        optionsDisplayed[4]="";
        optionsDisplayed[5]="";
        selectorMaxPos=2;
    }
    public void paint(Graphics g){
        g.setColor(Color.red);
        g.fillRect(400, 0, 618, 480);
        g.setColor(Color.black);
        g.drawRect(400, 0, 618, 410);
        g.setFont(new Font("Serif",Font.BOLD, 18));
        for(int i=0;i<maxOptions;i++){
            g.drawString(optionsDisplayed[i], distFromLeft+400, distFromTop+40*i);
        }
        if(selectorVisible){
            g.setFont(new Font("Serif",Font.BOLD, 25));
            g.drawString(">", 400, distFromTop+40*selectorPosition);
        }
        
    }
}
