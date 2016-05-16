/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Foreground.Menu;

import Background.Items.*;
import Background.Party.*;
import Background.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;

/**
 *
 * @author Connor
 */
public class PauseMenu extends JPanel{
    private final int menus = 4;
    private final int 
            OPTIONS=0,
            PARTYMENU=1,
            INVENTORYMENU=2,
            STATUSMENU=3;
    String assistText;
    int menuPosition;
    boolean confirmEvent, cancelEvent;
    OptionsMenu options;
    PartyMenu partyView;
    InventoryMenu inventoryView;
    StatusMenu statusView;
    boolean[] visible;
    Joystick joystick;
    /*
    
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
        statusView = new StatusMenu(p.getMemberFromParty(0));
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
        this.add(statusView);
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
        setAssistText("");
        //System.out.println("Looping");
        if(confirmEvent){
            switch(menuPosition){
                case OptionsMenu.INVENTORY:loadInvFromMainMenu();break;
                case OptionsMenu.STATUS:loadStatusFromMainMenu();break;
                case OptionsMenu.SPELLS:
                case OptionsMenu.EQUIPMENT:
                case OptionsMenu.SWAPMEMBERS:
                case OptionsMenu.SAVE:setAssistText("Not Yet Implemented");
            }
        }
    }
    private void loadInvFromMainMenu(){
        int save = menuPosition;
        menuPosition=0;
        toggleViews(PARTYMENU,INVENTORYMENU);
        options.loadInventoryMenuOptions();
        options.toggleSelectorVisible();
        inventoryView.toggleSelectorVisible();
        while(!cancelEvent){
            inventoryLoop();
        }
        toggleViews(PARTYMENU,INVENTORYMENU);
        options.toggleSelectorVisible();
        inventoryView.toggleSelectorVisible();
        options.loadMainMenuOptions();
        menuPosition = save;
    }
    private void loadStatusFromMainMenu(){
        int save = menuPosition;
        options.toggleSelectorVisible();
        partyView.toggleSelectorVisible();
        resetEvents();
        while(!cancelEvent){
            repaint();
            confirmMenuPosition(partyView.getSelectorMaxPosition()-1);
            partyView.updateSelectorPosition(menuPosition);
            if(confirmEvent){
                int save2 = menuPosition;
                menuPosition = 0;
                try{
                    BattleEntity view = partyView.getPartyMember(save2);
                    view.getName();
                    options.loadStatusMenuOptions();
                    options.toggleSelectorVisible();
                    viewMember(view);
                    options.toggleSelectorVisible();
                    options.loadMainMenuOptions();
                }catch(NullPointerException e){
                    setAssistText("Invalid Party Member");
                    resetEvents();
                }
                menuPosition = save2;
            }
        }
        resetEvents();
        options.toggleSelectorVisible();
        partyView.toggleSelectorVisible();
        menuPosition = save;
    }
    //inventory loop
    private void inventoryLoop(){
        repaint();
        //System.out.println(menuPosition);
        confirmMenuPosition(inventoryView.getSelectorMaxPosition());
        menuPosition = inventoryView.updateOffsetSelectorPosition(menuPosition);
        setAssistText("Select an item");
        if(confirmEvent){
            try{
                inventoryView.getItemAtPosition();
                int save = menuPosition;
                menuPosition=0;
                openItemOptions();
                menuPosition=save;
            }catch(IndexOutOfBoundsException e){
                System.out.println("Invalid Item");
                resetEvents();
            }
        }
    }
    private void openItemOptions(){
        options.toggleSelectorVisible();
        inventoryView.toggleSelectorVisible();
        Item selection = inventoryView.getItemAtPosition();
        setAssistText(selection.getName()+" selected");
        resetEvents();
        while(!cancelEvent){
            resetEvents();
            repaint();
            confirmMenuPosition(options.getSelectorMaxPosition());
            options.updateSelectorPosition(menuPosition);
            options.setSelectorVisible();
            if(confirmEvent){
                int save = menuPosition;
                try{
                    switch(menuPosition){
                        case InventoryMenu.USE:selectUseItemTarget(selection);break;
                        case InventoryMenu.EXAMINE:setAssistText(selection.getDescription());break;
                        case InventoryMenu.EQUIP:selectEquipTarget((Equipment)selection);break;
                        case InventoryMenu.DROP:inventoryView.dropItem(inventoryView.getSelectorPosition());break;
                    }
                }catch(ClassCastException e){
                    System.out.printf("%s cannot do that\n",selection.getName());
                }
                if(selection.getQuantity()==0){
                    inventoryView.setMaxOffset();
                    resetEvents();
                    options.toggleSelectorVisible();
                    inventoryView.toggleSelectorVisible();
                    return;
                }
                menuPosition=save;
            }
        }
        resetEvents();
        options.toggleSelectorVisible();
        inventoryView.toggleSelectorVisible();
    }
    private void selectUseItemTarget(Item i){
        toggleViews(INVENTORYMENU,PARTYMENU);
        if(!partyView.isSelectorVisible()){
            partyView.toggleSelectorVisible();
        }
        setAssistText("Use "+i.getName()+" on who?");
        while(!cancelEvent){
            repaint();
            //System.out.println("in the loop");
            confirmMenuPosition(partyView.getSelectorMaxPosition());
            partyView.updateSelectorPosition(menuPosition);
            if(confirmEvent){
                try{
                    System.out.println(menuPosition);
                    i.use(partyView.getPartyMember(menuPosition));
                    inventoryView.refreshInventory();
                    resetEvents();
                    System.out.println("Used Item");
                }catch(NullPointerException e){
                    System.out.println("Invalid Target");
                    resetEvents();
                }
            }
            if(i.getQuantity()==0){
                break;
            }
        }
        toggleViews(INVENTORYMENU,PARTYMENU);
        
    }
    private void selectEquipTarget(Equipment equipment){
        toggleViews(INVENTORYMENU,PARTYMENU);
        if(!partyView.isSelectorVisible()){
            partyView.toggleSelectorVisible();
        }
        setAssistText("Who will equip "+equipment.getName());
        while(!cancelEvent){
            repaint();
            //System.out.println("in the loop");
            confirmMenuPosition(partyView.getSelectorMaxPosition());
            partyView.updateSelectorPosition(menuPosition);
            if(confirmEvent){
                try{
                    System.out.println(menuPosition);
                    //i.use(partyView.getPartyMember(menuPosition));
                    BattleEntity equipper = partyView.getPartyMember(menuPosition);
                    try{
                        equipper.getWeapon().getId();
                        inventoryView.AddToInventory(equipper.getWeapon());
                        //inventoryView.refreshInventory();
                        equipper.equip((Weapon)equipment, 0);
                    }catch(NullPointerException t){
                        equipper.equip((Weapon)equipment, 0);
                    }catch(ClassCastException e){
                        System.out.println("Is armor");
                        for(int armorSlot=1;armorSlot<=3;armorSlot++){
                            try{
                                System.out.println(equipper.getEquipment(armorSlot).toString());
                            }catch(NullPointerException c){
                                equipper.equip(equipment, armorSlot);
                                break;
                            }
                        }
                        equipper.printAllEquipment();
                        //break;
                    }
                    inventoryView.refreshInventory();
                    resetEvents();
                    System.out.println("Equipped Item");
                }catch(NullPointerException e){
                    System.out.println("Invalid Target");
                    resetEvents();
                }
            }
            if(equipment.getQuantity()==0){
                break;
            }
        }
        toggleViews(INVENTORYMENU,PARTYMENU);
        
    }
    //status loop
    private void viewMember(BattleEntity member){
        toggleViews(PARTYMENU,STATUSMENU);
        statusView.setDisplayedEntity(member);
        while(!cancelEvent&&!confirmEvent){
            confirmMenuPosition(0);
            options.updateSelectorPosition(menuPosition);
            repaint();
        }
        toggleViews(PARTYMENU,STATUSMENU);
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
    public void setAssistText(String assistText){
        this.assistText = assistText;
    }
    //paint
    public void paint(Graphics g){
        
        if(visible[OPTIONS])
            options.paint(g);
        if(visible[PARTYMENU])
            partyView.paint(g);
        if(visible[INVENTORYMENU])
            inventoryView.paint(g);
        if(visible[STATUSMENU])
            statusView.paint(g);
        try{
            g.setFont(new Font("Courier New", Font.BOLD, 20));
            g.setColor(Color.black);
            g.drawString(assistText, 15, 25);
        }catch(NullPointerException e){
        }
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
    public static void main(String[] args){
        PauseMenuTester.main(args);
    }
}
