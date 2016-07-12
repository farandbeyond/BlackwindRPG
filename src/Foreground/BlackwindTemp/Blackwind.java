/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Foreground.BlackwindTemp;

import Background.BattleActions.BattleActionLoader;
import Background.BattleEntity;
import Background.BattleEntityLoader;
import Background.Items.*;
import Background.Party.*;
import Foreground.Battle.*;
import Foreground.Events.*;
import Foreground.Menu.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Connor
 */
public class Blackwind extends JPanel{
    public static final int STILL=0,DOWN=1, UP=2, RIGHT=3, LEFT=4;
    public static int pixelsMoved = 1;
    public static final int displayWidth=19, displayHeight=15;
    public static final int maxDisplayWidth=20, maxDisplayHeight=15;
    public static final int fps = 1000/100; //approx 100 updates per second
    
    public static int gameState;
    public static final int MAP=0,INVENTORY=1, BATTLE=2,EVENT=3;
    //Joystick kb;
    //Sprite player;
    int mapOffsetX, mapOffsetY, scrollX, scrollY;
    int qedMoves, qedMoveDirection;
    boolean qedMovement;
    int npcMoves, npcMoveDirection;
    boolean npcMovement;
    String npcName;
    String lastEventTriggered;
    Map loadedMap;
    Tile[][] displayArea;
    BufferedImage shownMap;
    Sprite mc;
    Joystick kb;
    TextBox textBox;
    
    PauseMenu menu;
    Battle battle;
    
    Party party;
    Inventory inv;
    Blackwind(Map m, int mapOffsetX, int mapOffsetY){
        this.setSize(640,480);
        this.setLayout(null);
        this.setLocation(0, 0);
        this.setVisible(true);
        this.repaint();
        this.setPreferredSize(new Dimension((displayWidth+2)*Tile.tileSize, (displayHeight+2)*Tile.tileSize));
        kb = new Joystick(this);
        textBox = new TextBox(0,364,640+16,180,this);
        //textBox.start();
        //player = mc;
        gameState = MAP;
        this.mapOffsetX=mapOffsetX;
        this.mapOffsetY=mapOffsetY;
        qedMoves = 0;
        qedMoveDirection = 0;
        qedMovement = false;
        npcMoves = 0;
        npcMoveDirection = 0;
        npcMovement = false;
        npcName = "";
        lastEventTriggered = "";
        scrollX=0;
        scrollY=0;
        shownMap = new BufferedImage((displayWidth+2)*Tile.tileSize, (displayHeight+2)*Tile.tileSize,BufferedImage.TYPE_INT_RGB);
        loadedMap = m;
        mc = new Sprite(0,"Wilson",10+mapOffsetX,8+mapOffsetY,STILL);
        loadDisplayArea(0,0);
        loadMapImage();
        
        party = new Party(4);
        inv = new Inventory(15);
        menu = new PauseMenu(party, inv,this);
        battle = new Battle(party, inv, party,this);
        
        this.add(menu);
        this.add(battle);
    }
    
    public void loadDisplayArea(int offX, int offY){
        displayArea = loadedMap.getMapSegment(offX, offY, displayWidth, displayHeight);
    }
    public void loadMapImage(){
        Graphics bg = shownMap.createGraphics();
        for(int x=0;x<displayWidth+2;x++){
            for(int y=0;y<displayHeight+2;y++){
                bg.drawImage(displayArea[x][y].getImage(), Tile.tileSize*(x), Tile.tileSize*(y), this);
            }
        }
    }
    
    public void loop(){
        try{
            while(true){
                if(gameState==MAP||gameState==EVENT){
                    checkPlayerForcedMovement();
                    checkNpcForcedMovement();
                    if(mc.isWalking()){
                        mc.animate(mc.getDirection());
                        shiftMap(mc.getDirection());
                    }else{
                        mc.animate(0);
                    }
                    repaint();
                    //Thread.sleep(fps);
                }else if(gameState==INVENTORY){
                    loadMenu();
                }else if(gameState==BATTLE){
                    loadBattle();
                }
                Thread.sleep(fps);
            }
        }catch(InterruptedException e){
            System.out.println("Error Occurred During loop: "+e);
        }
    }
    public void checkPlayerForcedMovement(){
        if(movementQueued()){
            move(qedMoveDirection);
            qedMoves--;
        }
        if(qedMovement&&!movementQueued()&&!mc.isWalking()){
            qedMovement = false;
            textBox.advanceText(this);
        }
    }
    public void checkNpcForcedMovement(){
        //if(npcMovementQueued()){
        //    loadedMap.getSprite(npcName).move(npcMoveDirection);
        //    if(!loadedMap.getSprite(npcName).isWalking()){
        //        System.out.println("One less movement");
        //        npcMoves--;
        //    }
        //}
        //if(npcMovement&&!npcMovementQueued()&&!npcMoving()){
        //    npcMovement = false;
        //    textBox.advanceText(this);
        //}
        
        if(npcMoves==0){
            if(npcMovement){
                npcMovement=false;
                textBox.advanceText(this);
            }
            return;
        }
        if(!loadedMap.getSprite(npcName).isWalking()){
            //System.out.println("Total moves decreasing");
            npcMoves--;
        }
        if(npcMoves!=0){
            System.out.printf("%d/%d\n",mc.getMapX(),mc.getMapY());
            if((loadedMap.getSprite(npcName).isWalking()
                ||loadedMap.tileWalkable(loadedMap.getSprite(npcName).getMapX()-1, loadedMap.getSprite(npcName).getMapY()-1, loadedMap.getSprite(npcName).getDirection())
               )&&!mc.isAt(loadedMap.getSprite(npcName).getMapX(), loadedMap.getSprite(npcName).getMapY(), loadedMap.getSprite(npcName).getDirection()))
                
                loadedMap.getSprite(npcName).move(npcMoveDirection);
            else
                npcMoves--;
            //System.out.println(npcMoves);
        }
    }
    
    public void triggerEvent(){
        for(Sprite s:loadedMap.getSpriteList()){
            try{
                if(s.isAt(mc.getMapX(), mc.getMapY(), mc.getDirection())){
                    //System.out.printf("Trying to trigger %s's Event\n",s.getName());
                    textBox.loadEvent(s.getEvent(),this);
                    gameState = EVENT;
                    //System.out.println("Event in trigger");
                    s.faceAway(mc.getDirection());
                    //try{
                    //    textBox.join();
                    //}catch(InterruptedException e){
                    //    System.out.println("Error Occurred in Thread");
                    //}
                    //System.out.printf("%d/%s\n", s.getMapX(),s.getMapY());
                    System.out.printf("%s",loadedMap.getTile(s.getMapX(), s.getMapY()).getName());
                    lastEventTriggered = s.getName();
                    return;
                }
            }catch(NullPointerException e){
                break;
            }
        }
        System.out.println("Couldnt find an event to trigger");
    }
    public void queueMovement(int direction, int distance){
        qedMoves = distance;
        qedMoveDirection = direction;
        qedMovement = true;
    }
    public void queueNpcMovement(int direction, int distance, String npcName){
        //System.out.printf("Direction:%d Distance:%d Name:%s", direction,distance,npcName);
        this.npcName = npcName;
        npcMoves = distance;
        npcMoveDirection = direction;
        npcMovement = true;
        loadedMap.getSprite(npcName).move(direction);
    }
    public boolean movementQueued(){
        if(!mc.isWalking()&&qedMoves!=0){
            return true;
        }
        return false;
    }
    public boolean npcMoving(){
        if(loadedMap.getSprite(npcName).isWalking())
            return true;
        return false;
    }
    public boolean npcMovementQueued(){
        if(loadedMap.getSprite(npcName).isWalking()&&npcMoves!=0)
            return true;
        return false;
    }
    
    public void shiftMap(int direction){
        switch(direction){
            case UP:scrollY-=pixelsMoved;break;
            case DOWN:scrollY+=pixelsMoved;break;
            case LEFT:scrollX-=pixelsMoved;break;
            case RIGHT:scrollX+=pixelsMoved;break;
        }
        switch(direction){
            case UP:
                if(scrollY<=-32){
                    scrollY=0;
                    mapOffsetY--;
                    //mc.setNewMapLocation(mc.getMapX(), mc.getMapY()+1);
                }
            case DOWN:
                if(scrollY>=32){
                    scrollY=0;
                    mapOffsetY++;
                    //mc.setNewMapLocation(mc.getMapX(), mc.getMapY()-1);
                }
            case LEFT: 
                if(scrollX<=-32){
                    scrollX=0;
                    mapOffsetX--;
                    //mc.setNewMapLocation(mc.getMapX()+1, mc.getMapY());
                }
            case RIGHT:
                if(scrollX>=32){
                    scrollX=0;
                    mapOffsetX++;
                    //mc.setNewMapLocation(mc.getMapX()-1, mc.getMapY());
                }
        }
        if(scrollY==0&&scrollX==0){
            loadDisplayArea(mapOffsetX,mapOffsetY);
            loadMapImage();
            mc.toggleIsWalking();
        }
    }
    public void repaint(){
        super.repaint();
    }
    public void paintComponents(Graphics g){
        this.paint(g);
    }
    public void paint(Graphics g){
        //System.out.println("Blackwind Paint Function");
        switch(gameState){
            case EVENT:
            case MAP:paintMap(g);break;
            case INVENTORY:menu.paint(g);break;
            case BATTLE:battle.paint(g);break;
        }
        if(gameState==EVENT){
            textBox.paint(g);
        }
        
    }
    public void paintMap(Graphics g){
        g.setColor(Color.MAGENTA);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        g.drawImage(shownMap.getSubimage(32+scrollX,32+scrollY,(32*displayWidth),(32*displayHeight)), 32, 32, this);
        g.drawImage(mc.getSprite(), mc.getGlobalX(), mc.getGlobalY(), this);
        for(Sprite s:loadedMap.getSpriteList()){
            if(s.isDisplayed(mapOffsetX, mapOffsetY, displayWidth+1, displayHeight+1)){
                //g.drawImage(s.getSprite(), (s.getScreenX()-mapOffsetX)*Tile.tileSize-scrollX, (s.getScreenY()-mapOffsetY)*Tile.tileSize-scrollY, this);
                g.drawImage(s.getSprite(), (s.getGlobalX()-mapOffsetX*Tile.tileSize)-scrollX, (s.getGlobalY()-mapOffsetY*Tile.tileSize)-scrollY, this);
                //System.out.printf("Sprite %s is displayed at %dx and %dy\nthis is with a map offset of %dx and %dy\n\n",s.getName(),s.getMapX(),s.getMapY(),mapOffsetX,mapOffsetY);
            }
        }
    }
    
    public boolean nearBorder(int direction){
        switch(direction){
            case UP:    if(mc.getScreenY()<=3)return true;else break;
            case DOWN:  if(mc.getScreenY()>=6)return true;else break;
            case LEFT: if(mc.getScreenX()<=3)return true;else break;
            case RIGHT:  if(mc.getScreenX()>=6)return true;else break;
        }
        return false;
    }
    public boolean mapContinues(int direction){
        switch(direction){
            case UP:
            case DOWN:
                for(int i=0;i<displayWidth;i++){
                    if(displayArea[i][direction==UP?0:displayHeight+1].getID()!=0)
                        return true;
                }
            return false;
            case RIGHT:
            case LEFT:
                for(int i=0;i<displayHeight;i++){
                    if(displayArea[direction==LEFT?0:displayWidth+1][i].getID()!=0)
                        return true;
                }
        }
        return false;
    }
    public void move(int direction){
        //repaint();
        //System.out.println(mc.isWalking()?"Walking":"Not Walking" + mc.isWalking());
        if(mc.isWalking()){
            //System.out.println("MC is walking");
            return;
        }
        
        //System.out.println("Moving");
        boolean tileIsWalkable = false;
        mc.setFacingDirection(direction);
        switch(direction){
            case UP:
                tileIsWalkable = displayArea[mc.getScreenX()][mc.getScreenY()-1].getWalkable();
                break;
            case DOWN:
                tileIsWalkable = displayArea[mc.getScreenX()][mc.getScreenY()+1].getWalkable();
                break;
            case RIGHT:
                tileIsWalkable = displayArea[mc.getScreenX()+1][mc.getScreenY()].getWalkable();
                break;
            case LEFT:
                tileIsWalkable = displayArea[mc.getScreenX()-1][mc.getScreenY()].getWalkable();
                break;
        }
        for(Sprite s:loadedMap.getSpriteList()){
            //System.out.println(mc.getMapX()+"/"+mc.getMapY());
            if(s.isAt(mc.getMapX(), mc.getMapY(), direction))
                tileIsWalkable = false;
        }
        if(!tileIsWalkable)
            return;
        mc.moveMC(direction);
    }
    public Joystick getKL(){return kb;}
    
    public void loadNewEvent(String eventName){
        loadedMap.getSprite(lastEventTriggered).setEventFileName(eventName);
        loadedMap.getSprite(lastEventTriggered).setEvent(EventReader.loadEvent(eventName));
    }

    public Map getMap(){return loadedMap;}
    public Battle getBattle(){return battle;}
    public PauseMenu getMenu(){return menu;}
    public TextBox getTextBox(){return textBox;}
    public Sprite getMC(){return mc;}
    
    public void confirmEvent(){}
    public void cancelEvent(){}
    public void loadMenu(){
        //System.out.println("Opening menu");
        menu.run(party, inv);
        //System.out.println("Closing menu");
    }
    public void loadBattle(){
        //System.out.println("Opening menu");
        Random rand = new Random();
        battle = new Battle(party, inv,EnemyPartyLoader.loadParty(rand.nextInt(5)),this);
        battle.loop();
        //System.out.println("Closing menu");
    }
    //public void menuEvent(){System.exit(0);}
    public class TextBox{
        int x,y,width,height;
        Event currentEvent;
        String eventLine;
        String[] display;
        int increment;
        TextBox(int x, int y, int width, int height,Blackwind b){
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            increment = 0;
            display = new String[4];
        }
        public void loadEvent(Event e,Blackwind b){
            if(!e.triggered()||e.reTriggerable()){
                currentEvent = e;
                eventLine = currentEvent.nextSegment(b,inv, party);
                display = eventLine.split("\n");
            }else{
                eventLine = "cant be triggered again";
                display = eventLine.split("\n");
                currentEvent = new Event(false);
            }
        }
        public void advanceText(Blackwind b){
            try{
                eventLine = currentEvent.nextSegment(b,inv, party);
                if(eventLine.equals("adv!!")&&!mc.isWalking()&&qedMoves==0&&!npcMoving()&&npcMoves==0)
                    advanceText(b);
                else
                    if(eventLine.equals("adv!!"))
                        display = "\n\n\n".split("\n");
                    else
                        display = eventLine.split("\n");
            }catch(IndexOutOfBoundsException e){
                //System.out.println("out of bounds");
                display = null;
                currentEvent.reset();
            }
        }
        public void paint(Graphics g){
            g.setColor(Color.cyan);
            g.fillRect(x, y, width, height);
            g.setColor(Color.black);
            g.setFont(new Font("Courier New", Font.BOLD, 17));
            g.drawRect(x, y, width, height);
            try{
                for(int i=0;i<display.length;i++){
                    g.drawString(display[i], x+30, y+30+30*i);
                    //System.out.println(i);
                }
            }catch(NullPointerException e){
                //System.out.println("Null");
                g.drawString("", x+30, y+30);
                gameState = MAP;
            }
        }
    }
    
    
    public static void main(String[] args){
        JFrame frame = new JFrame("Blackwind Dev",null);
        frame.setVisible(true);
        frame.setResizable(true);
        frame.setDefaultCloseOperation(3); //exit on close
        frame.setSize(640, 480);
        // ^^ using this size means the display area can be as large as 20*15
        Tile.startUp();
        Sprite.startUp();
        //Sprite.initialize();
        //Sprite wilson = new Sprite(0,5,5);
        //Game g = new Game(Map.loadMap("bigTest.txt"),wilson);
        Map m = Map.loadMap("maxSize2.txt");
        Blackwind g = new Blackwind(m,0,0);
        
        frame.add(g);
        g.inv.add(ItemLoader.loadItem(0, 10));
        BattleEntity b = BattleEntityLoader.loadEntity(0);
        b.equip((Equipment)ItemLoader.loadItem(ItemLoader.BRONZESWORD, 1), 0);
        b.addSkill(BattleActionLoader.loadAction(BattleActionLoader.FIREBALL));
        b.addSkill(BattleActionLoader.loadAction(BattleActionLoader.BRAVERY));
        g.party.addPartyMember(b);
        
        //g.getMenu().setPreferredSize(new Dimension((displayWidth+2)*Tile.tileSize, (displayHeight+2)*Tile.tileSize));
        //g.getBattle().setPreferredSize(new Dimension((displayWidth+2)*Tile.tileSize, (displayHeight+2)*Tile.tileSize));
        //g.getBattle().sendEngine(g);
        //g.getMenu().sendEngine(g);
        
        //frame.add(g.getBattle());
        //frame.add(g.getMenu());
        
        frame.pack();
        frame.addKeyListener(g.getKL());
        //g.getKL().start();
        g.loop();
        //Blackwind.gameState = INVENTORY;
        //g.getMenu().run(g.party, g.inv);
    }
}
