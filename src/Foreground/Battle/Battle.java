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
    public Battle(Party party, Inventory inv, Party enemyParty,JFrame frame){
        this.setLayout(null);
        this.setSize(612,480);
        this.setLocation(0, 0);
        this.setVisible(true);
        player = party;
        enemies = enemyParty;
        inventory = inv;
        enemiesDisplay = new displayBox[3];
        partyDisplay = new displayBox[4];
        for(int i=0;i<3;i++){
            enemiesDisplay[i]=new displayBox(i*150+30,20,150,150,enemies.getMemberFromParty(i));
            this.add(enemiesDisplay[i]);
        }
        for(int i=0;i<4;i++){
            partyDisplay[i]=new displayBox(i*100,270,110,150,party.getMemberFromParty(i));
            this.add(partyDisplay[i]);
        }
        frame.add(this);
    }
    public void loop(){
        while(true){
            repaint();
        }
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
    }
    
    private class displayBox extends JPanel{
        int myX, myY, myWidth, myHeight;
        BattleEntity displayed;
        boolean targeted;
        Color currentColor;
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
    public static void main(String[] args){
        BattleTester.main(args);
    }
}
