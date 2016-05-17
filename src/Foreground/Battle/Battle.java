/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Foreground.Battle;

import Background.BattleEntity;
import Background.Items.Inventory;
import Background.Party.Party;
import Background.StatID;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Connor
 */
public class Battle extends JPanel{
    private Party enemies, player;
    private Inventory inventory;
    private displayBox[] enemiesDisplay, partyDisplay;
    private menuBox menu;
    private int menuPosition;
    private boolean confirmEvent, cancelEvent;
    private Joystick kb;
    public Battle(Party party, Inventory inv, Party enemyParty,JFrame frame){
        this.setLayout(null);
        this.setSize(612,480);
        this.setLocation(0, 0);
        this.setVisible(true);
        confirmEvent = false;
        cancelEvent = false;
        menuPosition = 0;
        player = party;
        enemies = enemyParty;
        inventory = inv;
        menu = new menuBox(party.getMemberFromParty(0));
        this.add(menu);
        enemiesDisplay = new displayBox[3];
        partyDisplay = new displayBox[4];
        for(int i=0;i<3;i++){
            enemiesDisplay[i]=new displayBox(i*150+30,20,150,150,enemies.getMemberFromParty(i));
            this.add(enemiesDisplay[i]);
        }
        for(int i=0;i<4;i++){
            partyDisplay[i]=new displayBox(i*100,270,100,150,party.getMemberFromParty(i));
            this.add(partyDisplay[i]);
        }
        kb = new Joystick();
        frame.addKeyListener(kb);
        frame.add(this);
    }
    public void loop(){
        while(true){
            repaint();
            confirmMenuPosition(menu.getMaxSelectorPosition());
            menu.updateMenuPosition(menuPosition);
            if(confirmEvent){
                switch(menuPosition){
                    case 0:
                    case 1:menu.switchMenu(menuBox.MAIN, menuBox.SKILLS);break;
                    case 2:menu.switchMenu(menuBox.MAIN, menuBox.ITEMS);break;
                    case 3:System.exit(0);
                }
                resetEvents();
            }
        }
    }
    public void confirmMenuPosition(int maxPosition){
        if(menuPosition<0){
            menuPosition = maxPosition;
        }
        if(menuPosition>maxPosition){
            menuPosition = 0;
        }
    }
    public void resetEvents(){
        confirmEvent = false;
        cancelEvent = false;
    }
    public void paint(Graphics g){
        g.setColor(Color.yellow);
        g.fillRect(0, 0, 612, 480);
        for(int i=0;i<3;i++){
            enemiesDisplay[i].paint(g);
        }
        for(int i=0;i<4;i++){
            partyDisplay[i].paint(g);
        }
        menu.paint(g);
    }
    
    private class displayBox extends JPanel{
        private int myX, myY, myWidth, myHeight;
        private BattleEntity displayed;
        private boolean targeted;
        private Color currentColor;
        displayBox(int x, int y, int width, int height, BattleEntity displayTarget){
            myX = x;
            myY = y;
            myWidth = width;
            myHeight = height;
            this.setSize(x,y);
            this.setLocation(width, height);
            this.setVisible(true);
            displayed = displayTarget;
            updateColor();
            targeted = false;
        }
        public void updateColor(){
            try{
                if(displayed.getIsDead()){
                    currentColor = Color.red;
                }else{
                    currentColor = Color.cyan;
                }
                if(targeted){
                    currentColor = Color.orange;
                    return;
                }
            }catch(NullPointerException e){
                currentColor = Color.gray;
            }
        }
        public void updateDisplay(BattleEntity e){displayed = e;}
        @Override
        public int getX(){return myX;}
        @Override
        public int getY(){return myY;}
        @Override
        public int getWidth(){return myWidth;}
        @Override
        public int getHeight(){return myHeight;}
        public BattleEntity getDisplayed(){return displayed;}
        @Override
        public void paint(Graphics g){
            try{
                g.setColor(currentColor);
                g.fillRect(myX, myY, myWidth, myHeight);
            }catch(NullPointerException e){
                g.setColor(Color.gray);
                g.fillRect(myX, myY, myWidth, myHeight);
            }
            g.setColor(Color.black);
            g.drawRect(myX, myY, myWidth, myHeight);
            g.setFont(new Font("Courier New", Font.BOLD, 15));
            try{
               g.drawString(displayed.getName(),myX+10 ,myY+40);
               g.drawString(String.format("%d/%d hp", displayed.getStat(StatID.HP),displayed.getStat(StatID.MAXHP)),myX+10 ,myY+70);
               g.drawString(String.format("%d/%d hp", displayed.getStat(StatID.MP),displayed.getStat(StatID.MAXMP)),myX+10 ,myY+100);
            }catch(NullPointerException e){
               g.drawString("----",myX+10 ,myY+40);
               g.drawString("--/-- hp",myX+10 ,myY+70);
               g.drawString("--/-- hp",myX+10 ,myY+100);
            }
        }
    }
    private class menuBox extends JPanel{
        public static final int MAIN=0, SKILLS=1,ITEMS=2;
        private boolean[] menuLoaded;
        private int myX, myY, myWidth, myHeight;
        private int invOffset,invMaxOffset, skillsOffset,skillsMaxOffset;
        private int maxMenuPosition;
        private int selectorPosition;
        private BattleEntity current;
        private String[] menuOptions;
        menuBox(BattleEntity current){
            selectorPosition = 0;
            invOffset = 0;
            skillsOffset = 0;
            menuLoaded = new boolean[3];
            menuLoaded[MAIN]=true;
            this.current = current;
            menuOptions = new String[8];
            loadMenus();
            this.setSize(myWidth,myHeight);
            this.setLocation(myX, myY);
            this.setVisible(true);
        }
        public void loadMenus(){
            myX = 400;
            myWidth = 201;
            if(menuLoaded[MAIN]){
                myY = 300;
                myHeight = 140;
                loadMainMenuOptions();
            }else if(menuLoaded[SKILLS]){
                myY = 180;
                myHeight = 260;
                loadSkillsOptions();
            }
            else if(menuLoaded[ITEMS]){
                myY = 180;
                myHeight = 260;
                loadItemOptions();
            }
        }
        //menu option sets
        public void loadMainMenuOptions(){
            menuOptions[0]="Attack";
            menuOptions[1]="Skills";
            menuOptions[2]="Items";
            menuOptions[3]="Flee";
            menuOptions[4]="";
            menuOptions[5]="";
            menuOptions[6]="";
            menuOptions[7]="";
            maxMenuPosition = 3;
        }
        public void loadItemOptions(){
            for(int i=0;i<8;i++){
                try{
                    menuOptions[i] = "x"+inventory.getItem(i+invOffset).getQuantity()+" "+inventory.getItem(i+invOffset).getName();
                }catch(IndexOutOfBoundsException e){
                    menuOptions[i]="x-- ----";
                }
            }
            maxMenuPosition = 7;
        }
        public void loadSkillsOptions(){
            for(int i=0;i<8;i++){
                try{
                    menuOptions[i] = current.getSkill(i+skillsOffset).getCost()+"mp "+current.getSkill(i+skillsOffset).getName();
                }catch(IndexOutOfBoundsException e){
                    menuOptions[i] = "--mp -----";
                }
            }
            maxMenuPosition = 7;
        }
        //menu navigating
        public void updateMenuPosition(int newPos){selectorPosition = newPos;}
        //sets
        public void setCurrent(BattleEntity current){this.current=current;}
        public void switchMenu(int currentMenu, int newMenu){
            menuLoaded[currentMenu]=false;
            menuLoaded[newMenu]=true;
            loadMenus();
        }
        //gets
        public int getMaxSelectorPosition(){return maxMenuPosition;}
        public int getX(){return myX;}
        public int getY(){return myY;}
        public int getWidth(){return myWidth;}
        public int getHeight(){return myHeight;}
        public int getInvOffset(){return invOffset;}
        public int getSkillsOffset(){return skillsOffset;}
        public int getInvMaxOffset(){return invMaxOffset;}
        public int getSkillsMaxOffset(){return skillsMaxOffset;}
        public int getSelectorPosition(){return selectorPosition;}
        //offset handling
        public void setInvMaxOffset(){
        invMaxOffset=0;
        while(true){
            try{
                inventory.getItem(invMaxOffset+10).toString();
                invMaxOffset++;
            }catch(IndexOutOfBoundsException e){
                System.out.println(String.format("max offset is %d, from an inventory size %d, containing %d items", invMaxOffset, inventory.getInvSize(), inventory.getNumberOfItemsInInventory()));
                return;
            }
        }
    }
        public void setSkillsMaxOffset(){
            skillsMaxOffset=0;
            while(true){
                try{
                    current.getSkill(skillsMaxOffset+10).toString();
                    skillsMaxOffset++;
                }catch(IndexOutOfBoundsException e){
                    System.out.println(String.format("Max offset is %d, looking at the entity %s with %d skills",skillsMaxOffset,current.getName(),current.getNumberOfSkills()));
                    return;
                }
            }
        }
        //paint
        public void paint(Graphics g){
            g.setColor(Color.GREEN);
            g.fillRect(myX, myY, myWidth, myHeight);
            g.setColor(Color.black);
            g.drawRect(myX, myY, myWidth, myHeight);
            g.setFont(new Font("Courier New", Font.BOLD, 16));
            for(int i=0;i<8;i++){
                g.drawString(menuOptions[i], myX+20, myY+20+30*i);
            }
            g.drawString(">", myX, myY+20+30*selectorPosition);
        }
        

    }
    //key event controlling
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
                //case KeyEvent.VK_ENTER:menuEvent();break;
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
    //public void menuEvent(){System.exit(0);}
    public static void main(String[] args){
        BattleTester.main(args);
    }
}
