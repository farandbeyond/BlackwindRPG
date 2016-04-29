/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Foreground.Menu;

import Background.Items.*;
import Background.Party.*;
import Background.*;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;

/**
 *
 * @author Connor
 */
public class PauseMenu extends JPanel{
    private final int menus = 3;
    private final int 
            OPTIONS=0,
            PARTYMENU=1,
            INVENTORYMENU=2;
    int menuPosition;
    boolean confirmEvent, cancelEvent;
    OptionsMenu options;
    PartyMenu partyView;
    InventoryMenu inventoryView;
    boolean[] visible;
    Joystick joystick;
    /*
    private static final int
            USE=0,
            EQUIP=1,
            EXAMINE=2,
            DROP=3;
    private static final int
            EXIT=0;
    private static final int
            CAST=0,
            DESCRIPTION=1,
            ELEMENT=2;
    private static final int
            EQUIP=0,
            UNEQUIP=1,
            DESCRIPTION=2;
            */
    
    PauseMenu(Party p, Inventory inv){
        //local variables
        menuPosition=0;
        confirmEvent=false;
        cancelEvent=false;
        //menu visibility
        visible = new boolean[menus];
        for(int i=0;i<menus;i++){
            visible[i]=false;
        }
        //menu panels
        
    }
    PauseMenu(JFrame frame,Party p, Inventory inv){
        //local variables
        menuPosition=0;
        confirmEvent=false;
        cancelEvent=false;
        //menu visibility
        visible = new boolean[menus];
        for(int i=0;i<menus;i++){
            visible[i]=false;
        }
        //menu panels
        partyView = new PartyMenu(p);
        inventoryView = new InventoryMenu(inv);
        options = new OptionsMenu();
        //set up this panel
        this.setLayout(null);
        this.setSize(612,480);
        this.setLocation(0, 0);
        this.setVisible(true);
        //add panels to frame
        this.add(partyView);
        this.add(options);
        this.add(inventoryView);
        joystick = new Joystick();
        frame.addKeyListener(joystick);
        frame.add(this);
    }
    
    public void run(Party p, Inventory inv){
        visible[OPTIONS]=true;
        visible[PARTYMENU]=true;
        //System.out.println("Got here");
        while(!cancelEvent){
            resetEvents();
            menuLoop();
        }
        System.exit(0);
    }
    //main menu loop
    public void menuLoop(){
        repaint();
        confirmMenuPosition(options.getSelectorMaxPosition());
        options.updateSelectorPosition(menuPosition);
        options.setSelectorVisible();
        
        //System.out.println("Looping");
        if(confirmEvent){
            switch(menuPosition){
                case OptionsMenu.INVENTORY:LoadInvFromMainMenu();break;
                case OptionsMenu.STATUS:
                case OptionsMenu.SPELLS:
                case OptionsMenu.EQUIPMENT:
                case OptionsMenu.SWAPMEMBERS:
                case OptionsMenu.SAVE:System.out.println("Not Yet Implemented");
            }
        }
    }
    //inventory loop
    private void LoadInvFromMainMenu(){
        int save = menuPosition;
        menuPosition=0;
        toggleViews(PARTYMENU,INVENTORYMENU);
        options.loadInventoryMenuOptions();
        while(!cancelEvent){
            inventoryLoop();
        }
        toggleViews(PARTYMENU,INVENTORYMENU);
        options.loadMainMenuOptions();
        menuPosition = save;
    }
    private void toggleViews(int view1, int view2){
        resetEvents();
        if(visible[view1]){
            visible[view1]=false;
        }else{
            visible[view1]=true;
        }
        if(visible[view2]){
            visible[view2]=false;
        }else{
            visible[view2]=true;
        }
    }
    private void inventoryLoop(){
        repaint();
        confirmMenuPosition(options.getSelectorMaxPosition());
        options.updateSelectorPosition(menuPosition);
        options.setSelectorVisible();
    }
    //menuPositionControlling
    private void confirmMenuPosition(int maxPos){
        if(menuPosition<0){
            menuPosition=maxPos;
        }
        if(menuPosition>maxPos){
            menuPosition=0;
        }
    }
    //key event controlling
    private void resetEvents(){
        confirmEvent=false;
        cancelEvent=false;
    }
    //paint
     public void paint(Graphics g){
        if(visible[OPTIONS])
            options.paint(g);
        if(visible[PARTYMENU])
            partyView.paint(g);
        if(visible[INVENTORYMENU])
            inventoryView.paint(g);
    }
    //key events
    public class Joystick implements KeyListener{

        @Override
        public void keyTyped(KeyEvent ke) {
            
        }

        @Override
        public void keyPressed(KeyEvent ke) {
            switch(ke.getExtendedKeyCode()){
                case KeyEvent.VK_O:confirmEvent();break;
                case KeyEvent.VK_P:cancelEvent();break;
                case KeyEvent.VK_W:upEvent();break;
                case KeyEvent.VK_A:leftEvent();break;
                case KeyEvent.VK_S:downEvent();break;
                case KeyEvent.VK_D:rightEvent();break;
                case KeyEvent.VK_ENTER:menuEvent();break;
            }
        }

        @Override
        public void keyReleased(KeyEvent ke) {
            
        }
        
    }
    public void confirmEvent(){confirmEvent=true;}
    public void cancelEvent(){cancelEvent=true;}
    public void upEvent(){menuPosition--;}
    public void downEvent(){menuPosition++;}
    public void leftEvent(){}
    public void rightEvent(){}
    public void menuEvent(){System.exit(0);}
}
