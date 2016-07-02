/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Foreground.BlackwindTemp;

import Background.Items.*;
import Background.Party.*;
import Foreground.Battle.*;
import Foreground.Menu.*;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Connor
 */
public class Blackwind extends JPanel{
    public static final int STILL=0,DOWN=1, UP=2, RIGHT=3, LEFT=4;
    public static int pixelsMoved = 4;
    public static final int displayWidth=19, displayHeight=15;
    public static final int maxDisplayWidth=20, maxDisplayHeight=15;
    public static final int fps = 1000/60; //approx 30 updates per second
    //Joystick kb;
    //Sprite player;
    int mapOffsetX, mapOffsetY, scrollX, scrollY;
    Map loadedMap;
    Tile[][] displayArea;
    BufferedImage shownMap;
    Sprite mc;
    Joystick kb;
    Blackwind(Map m){
        this.setSize(640, 480);
        this.setLayout(null);
        this.setLocation(0, 0);
        this.setVisible(true);
        this.repaint();
        this.setPreferredSize(new Dimension(640,480));
        kb = new Joystick();
        //player = mc;
        mapOffsetX=0;
        mapOffsetY=0;
        scrollX=0;
        scrollY=0;
        shownMap = new BufferedImage((displayWidth+2)*Tile.tileSize, (displayHeight+2)*Tile.tileSize,BufferedImage.TYPE_INT_RGB);
        loadedMap = m;
        mc = new Sprite(0,"Wilson",3,3,STILL);
        loadDisplayArea(0,0);
        loadMapImage();
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
                updateSprites();
                repaint();
                Thread.sleep(fps);
            }
        }catch(InterruptedException e){
            System.out.println("Error Occurred During loop: "+e);
        }
    }
    public void updateSprites(){
        mc.move(mc.movingDirection);
    }
    
    
    public void paintComponents(Graphics g){
        this.paint(g);
    }
    public void paint(Graphics g){
        //g.setColor(Color.red);
        //g.fillRect(0, 0, 1000, 1000);
        //g.drawImage(shownMap, 32+scrollX, 32+scrollY, this);'
        //System.out.printf("%d/%d\n",scrollX,scrollY);
        g.drawImage(shownMap.getSubimage(32+scrollX,32+scrollY,(Tile.tileSize*displayWidth),(Tile.tileSize*displayHeight)), 32*((maxDisplayWidth-displayWidth)/2), 32*((maxDisplayHeight-displayHeight)/2), this);
        g.drawImage(mc.getImage(), mc.getGlobalX(), mc.getGlobalY(), this);
    }
    
    public void move(int direction){
        
    }
    
    public class Joystick implements KeyListener{
        @Override
        public void keyTyped(KeyEvent ke) {
            }
        @Override
        public void keyPressed(KeyEvent ke) {
            switch(ke.getExtendedKeyCode()){
                case KeyEvent.VK_O:
                case KeyEvent.VK_P:break;
                case KeyEvent.VK_W:upEvent();break;
                case KeyEvent.VK_A:leftEvent();break;
                case KeyEvent.VK_S:downEvent();break;
                case KeyEvent.VK_D:rightEvent();break;
                case KeyEvent.VK_ENTER:     break;               
            }
        }
        @Override
        public void keyReleased(KeyEvent ke) {
        }
    }
    public void confirmEvent(){}
    public void cancelEvent(){}
    public void upEvent(){
        if(loadedMap.tileWalkable(mc.getMapX(), mc.getMapY(), UP))
            mc.move(UP);
    }
    public void downEvent(){
        if(loadedMap.tileWalkable(mc.getMapX(), mc.getMapY(), DOWN))
            mc.move(DOWN);
    }
    public void leftEvent(){
        if(loadedMap.tileWalkable(mc.getMapX(), mc.getMapY(), LEFT))
            mc.move(LEFT);
    }
    public void rightEvent(){
        if(loadedMap.tileWalkable(mc.getMapX(), mc.getMapY(), RIGHT))
            mc.move(RIGHT);
    }
    public void menuEvent(){System.exit(0);}
    public Joystick getKL(){return kb;}
    
    public static void main(String[] args){
        JFrame frame = new JFrame("Blackwind Dev",null);
        frame.setVisible(true);
        frame.setResizable(true);
        frame.setDefaultCloseOperation(3); //exit on close
        frame.setSize(640, 480);
        // ^^ using this size means the display area can be as large as 20*15
        Tile.startUp();
        //Sprite.initialize();
        //Sprite wilson = new Sprite(0,5,5);
        //Game g = new Game(Map.loadMap("bigTest.txt"),wilson);
        Blackwind g = new Blackwind(Map.loadMap("maxSize2.txt"));
        frame.add(g);
        frame.pack();
        frame.addKeyListener(g.getKL());
        g.loop();
    }
}
