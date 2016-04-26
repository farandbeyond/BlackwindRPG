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
    private final int menus = 2;
    private final int 
            OPTIONS=0,
            PARTYMENU=1;
    int menuPosition;
    boolean confirmEvent, cancelEvent;
    OptionsMenu options;
    PartyMenu partyView;
    boolean[] visible;
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
        options = new OptionsMenu();
        
        
        frame.add(this);
    }
    
    public void run(){
        
    }
    public void paint(Graphics g){
        if(visible[OPTIONS])
            options.paint(g);
        if(visible[PARTYMENU])
            partyView.paint(g);
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
            }
        }

        @Override
        public void keyReleased(KeyEvent ke) {
            
        }
        
    }
    public void confirmEvent(){
        confirmEvent=true;
        System.out.println("confirmed");
    }
    public void cancelEvent(){
        cancelEvent=true;
    }
    public void upEvent(){
        menuPosition--;
    }
    public void downEvent(){
        menuPosition++;
    }
    public void leftEvent(){
        
    }
    public void rightEvent(){
        
    }
}
