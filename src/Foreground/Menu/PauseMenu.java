/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Foreground.Menu;

import Background.Items.*;
import Background.Party.*;
import Background.*;
import Background.BattleActions.HealingSpell;
import Background.BattleActions.Spell;
import Foreground.BlackwindTemp.Blackwind;
import Foreground.BlackwindTemp.Tile;
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
public class PauseMenu extends JPanel {
    private final int menus = 5;
    private final int 
            OPTIONS=0,
            PARTYMENU=1,
            INVENTORYMENU=2,
            STATUSMENU=3,
            SPELLSMENU=4;
    String assistText;
    int menuPosition;
    boolean confirmEvent, cancelEvent;
    OptionsMenu options;
    PartyMenu partyView;
    InventoryMenu inventoryView;
    StatusMenu statusView;
    SpellsMenu spellsView;
    boolean[] visible;
    
    Blackwind engine;
    //Joystick joystick;
    public PauseMenu(Party p, Inventory inv){
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
        spellsView = new SpellsMenu(p.getMemberFromParty(0));
        options = new OptionsMenu();
        //set up this panel
        this.setLayout(null);
        this.setSize(640,480);
        this.setLocation(Tile.tileSize, Tile.tileSize);
        this.setVisible(true);
        //add panels to frame
        this.add(partyView);
        this.add(options);
        this.add(inventoryView);
        this.add(statusView);
        this.add(spellsView);
        //joystick = new Joystick();
    }
    
    public PauseMenu(Party p, Inventory inv, Blackwind b){
        sendEngine(b);
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
        spellsView = new SpellsMenu(p.getMemberFromParty(0));
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
        this.add(spellsView);
        //joystick = new Joystick();
    }
    public void sendEngine(Blackwind b){
        engine = b;
    }
    public void repaint(){
        //System.out.println("Painting");
        try{
            engine.repaint();
        }catch(NullPointerException e){
            System.out.println("engine currently null");
        }
    }
    
    public void run(Party p, Inventory inv){
        partyView.updateParty(p);
        inventoryView.updateInventory(inv);
        visible[OPTIONS]=true;
        visible[PARTYMENU]=true;
        System.out.println("Got here");
        resetEvents();
        while(!cancelEvent){
            resetEvents();
            menuLoop();
        }
        //System.exit(0);
        //Blackwind.gameState = Blackwind.MAP;
        exitMenu();
    }
    //main menu loop
    public void menuLoop(){
        confirmMenuPosition(options.getSelectorMaxPosition());
        options.updateSelectorPosition(menuPosition);
        options.setSelectorVisible();
        setAssistText("");
        repaint();
        //System.out.println("Looping");
        if(confirmEvent){
            System.out.println("Confirmed");
            switch(menuPosition){
                case OptionsMenu.INVENTORY:loadInvFromMainMenu();break;
                case OptionsMenu.STATUS:loadStatusFromMainMenu();break;
                case OptionsMenu.SPELLS:loadSpellsFromMainMenu();break;
                case OptionsMenu.EQUIPMENT:loadEquipmentFromMainMenu();break;
                case OptionsMenu.SWAPMEMBERS:loadSwapFromMainMenu();break;
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
        setAssistText("Select an item");
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
        menuPosition = 0;
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
    private void loadSpellsFromMainMenu(){
        int save = menuPosition;
        menuPosition = 0;
        options.toggleSelectorVisible();
        partyView.toggleSelectorVisible();
        resetEvents();
        while(!cancelEvent){
            setAssistText("Choose a party member");
            repaint();
            confirmMenuPosition(partyView.getSelectorMaxPosition()-1);
            partyView.updateSelectorPosition(menuPosition);
            if(confirmEvent){
                int save2 = menuPosition;
                menuPosition = 0;
                try{
                    BattleEntity view = partyView.getPartyMember(save2);
                    view.getName();
                    options.loadSpellsMenuOptions();
                    options.toggleSelectorVisible();
                    spellsLoop(view);
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
    private void loadEquipmentFromMainMenu(){
        int save = menuPosition;
        menuPosition = 0;
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
                    options.loadEquipmentMenuOptions();
                    options.toggleSelectorVisible();
                    adjustEquipment(view);
                    options.toggleSelectorVisible();
                    options.loadMainMenuOptions();
                }catch(NullPointerException e){
                    System.out.println(e);
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
    private void loadSwapFromMainMenu(){
        options.toggleSelectorVisible();
        partyView.toggleSelectorVisible();
        resetEvents();
        swapMembers();
        options.toggleSelectorVisible();
        partyView.toggleSelectorVisible();
    }
    //inventory loop
    private void inventoryLoop(){
        repaint();
        //System.out.println(menuPosition);
        confirmMenuPosition(inventoryView.getSelectorMaxPosition());
        menuPosition = inventoryView.updateOffsetSelectorPosition(menuPosition);
        
        if(confirmEvent){
            try{
                inventoryView.getItemAtPosition();
                int save = menuPosition;
                menuPosition=0;
                openItemOptions();
                menuPosition=save;
            }catch(IndexOutOfBoundsException e){
                setAssistText("Invalid Item");
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
                        //case InventoryMenu.EQUIP:selectEquipTarget((Equipment)selection);break;
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
        partyView.toggleSelectorVisible();
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
    //spells loop
    private void spellsLoop(BattleEntity e){
        spellsView.setDisplayedEntity(e);
        toggleViews(SPELLSMENU,PARTYMENU);
        spellsView.toggleSelectorVisible();
        options.toggleSelectorVisible();
        resetEvents();
        while(!cancelEvent){
            repaint();
            confirmMenuPosition(spellsView.getSelectorMaxPosition());
            menuPosition = spellsView.updateOffsetSelectorPosition(menuPosition);
            setAssistText("Select a spell");
            if(confirmEvent){
                resetEvents();
                int save = menuPosition;
                //spellsView.toggleSelectorVisible();
                //options.toggleSelectorVisible();
                spellOptions();
                spellsView.toggleSelectorVisible();
                //options.toggleSelectorVisible();
                menuPosition = save;
            }
        }
        toggleViews(SPELLSMENU,PARTYMENU);
        spellsView.toggleSelectorVisible();
        if(!partyView.isSelectorVisible())
            partyView.toggleSelectorVisible();
    }
    private void spellOptions(){
        resetEvents();
        spellsView.toggleSelectorVisible();
        if(!options.isSelectorVisible())
            options.toggleSelectorVisible();
        while(!cancelEvent){
            repaint();
            confirmMenuPosition(options.getSelectorMaxPosition());
            options.updateSelectorPosition(menuPosition);
            if(confirmEvent){
                resetEvents();
                int save = menuPosition;
                switch(menuPosition){
                    case SpellsMenu.DESCRIPTION :setAssistText(spellsView.getSkillAtPosition().getDescription());break;
                    case SpellsMenu.ELEMENT     :setAssistText(String.format("Element: %s", ElementHandler.getElementName(spellsView.getSkillAtPosition().getElement())));break;
                    case SpellsMenu.CAST        :
                        try{
                            
                            if(((Spell)spellsView.getSkillAtPosition()).getCaster().canCast((Spell)spellsView.getSkillAtPosition()))
                                try{
                                    ((HealingSpell)spellsView.getSkillAtPosition()).getBaseHeal();
                                    castSpellOn((Spell)spellsView.getSkillAtPosition());
                                    //catch for non healing spells
                                }catch(ClassCastException r){
                                    setAssistText("That spell is dangerous!");
                                }
                            //catch for spells with too high a mana cost
                            else
                                setAssistText("Not Enough Mana");
                            //catch for non spells
                        }catch(ClassCastException e){
                            setAssistText("This ability cannot be cast");
                        }
                    
                }   
                menuPosition = save;
            }
        }
        options.toggleSelectorVisible();
        resetEvents();
    }
    private void castSpellOn(Spell s){
        toggleViews(SPELLSMENU,PARTYMENU);
        spellsView.toggleSelectorVisible();
        //partyView.toggleSelectorVisible();
        resetEvents();
        while(!cancelEvent){
            resetEvents();
            repaint();
            confirmMenuPosition(partyView.getSelectorMaxPosition());
            partyView.updateSelectorPosition(menuPosition);
            if(confirmEvent){
                s.cast(partyView.getPartyMember(menuPosition));
            }
        }
        toggleViews(SPELLSMENU,PARTYMENU);
        spellsView.toggleSelectorVisible();
        partyView.toggleSelectorVisible();
    }
    //equipment loop
    private void adjustEquipment(BattleEntity target){
        resetEvents();
        toggleViews(PARTYMENU,STATUSMENU);
        statusView.setDisplayedEntity(target);
        statusView.toggleSelectorVisible();
        options.toggleSelectorVisible();
        while(!cancelEvent){
            repaint();
            confirmMenuPosition(statusView.getSelectorMaxPosition());
            statusView.updateSelectorPosition(menuPosition);
            if(confirmEvent){
                int save = menuPosition;
                options.toggleSelectorVisible();
                //statusView.toggleSelectorVisible();
                equipmentOptions(target,save);
                options.toggleSelectorVisible();
                //statusView.toggleSelectorVisible();
                menuPosition = save;
                resetEvents();
            }
        }
        resetEvents();
        toggleViews(PARTYMENU,STATUSMENU);
        statusView.toggleSelectorVisible();
        options.toggleSelectorVisible();
    }
    private void equipmentOptions(BattleEntity target, int slot){
        resetEvents();
        while(!cancelEvent){
            repaint();
            confirmMenuPosition(options.getSelectorMaxPosition());
            options.updateSelectorPosition(menuPosition);
            if(confirmEvent){
                resetEvents();
                int save = menuPosition;
                switch(menuPosition){
                    case StatusMenu.DESCRIPTION :
                        if(target.getEquipment(save)!=null)
                            setAssistText(target.getEquipment(save).getDescription());
                        else
                            setAssistText("Nothing Equipped");
                        break;
                    case StatusMenu.UNEQUIP     :
                        try{
                            if(inventoryView.canAdd(target.getEquipment(slot))){
                                inventoryView.AddToInventory(target.unEquip(slot));
                                inventoryView.refreshInventory();
                                break;
                            }else{
                                setAssistText("Cannot unequip: Inventory Full");
                                break;
                            }
                        }catch(NullPointerException e){
                            setAssistText("Nothing Equipped");
                            break;
                        }
                    case StatusMenu.EQUIP       :
                        selectItemToEquip(target,slot);
                        inventoryView.refreshInventory();
                        break;
                }
                menuPosition = save;
            }
        }
    }
    private void selectItemToEquip(BattleEntity target, int slot){
        menuPosition = 0;
        toggleViews(INVENTORYMENU,STATUSMENU);
        statusView.toggleSelectorVisible();
        inventoryView.toggleSelectorVisible();
        resetEvents();
        while(!cancelEvent){
            confirmMenuPosition(inventoryView.getSelectorMaxPosition());
            inventoryView.updateOffsetSelectorPosition(menuPosition);
            repaint();
            if(confirmEvent){
                resetEvents();
                try{
                    switch(slot){
                        case 0:
                            if(target.getEquipment(0)!=null)
                                inventoryView.AddToInventory(target.unEquip(slot));
                            target.equip((Weapon)inventoryView.getItemAtPosition(), slot);
                            break;
                        case 1:
                        case 2:
                        case 3: target.equip((Armor)inventoryView.getItemAtPosition(), slot);break;
                    }
                    break;
                }catch(ClassCastException e){
                    setAssistText("Cannot be equipped");
                }
            }
        }
        toggleViews(INVENTORYMENU,STATUSMENU);
        statusView.toggleSelectorVisible();
        inventoryView.toggleSelectorVisible();
        resetEvents();
    }
    //swap loop
    private void swapMembers(){
        while(!cancelEvent){
            setAssistText("Who will swap?");
            confirmMenuPosition(partyView.getCurrentPartySize()-1);
            partyView.updateSelectorPosition(menuPosition);
            resetEvents();
            repaint();
            if(confirmEvent&&partyView.getPartyMember(menuPosition)!=null){
                int m1 = menuPosition;
                menuPosition = 0;
                resetEvents();
                while(!cancelEvent){
                    setAssistText("Who will be swapped with?");
                    confirmMenuPosition(partyView.getCurrentPartySize()-1);
                    partyView.updateSelectorPosition(menuPosition);
                    repaint();
                    if(confirmEvent&&partyView.getPartyMember(menuPosition)!=null){
                        int m2 = menuPosition;
                        partyView.swapMembers(m1, m2);
                        resetEvents();
                        break;
                    }
                }
            }
        }
        resetEvents();
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
        if(visible[SPELLSMENU])
            spellsView.paint(g);
        try{
            g.setFont(new Font("Courier New", Font.BOLD, 20));
            g.setColor(Color.black);
            g.drawString(assistText, 15, 25);
        }catch(NullPointerException e){
        }
    }
    //key events
    /*public class Joystick implements KeyListener{

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
        
    }*/
    public void confirmEvent(){confirmEvent=true;}
    public void cancelEvent(){cancelEvent=true;}
    public void upEvent(){menuPosition--;}
    public void downEvent(){menuPosition++;}
    public void leftEvent(){}
    public void rightEvent(){}
    public void menuEvent(){exitMenu();}
    public void exitMenu(){
        for(boolean b:visible){
            b=false;
        }
        Blackwind.gameState = Blackwind.MAP;
        resetEvents();
    }
    //public Joystick getKL(){return joystick;}
    public static void main(String[] args){
        //PauseMenuTester.main(args);
        Blackwind.main(args);
    }
}
