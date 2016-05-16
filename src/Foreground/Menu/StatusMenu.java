/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Foreground.Menu;

import Background.BattleEntity;
import Background.StatID;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 *
 * @author Connor
 */
public class StatusMenu extends JPanel{
    BattleEntity displayed;
    public static final int 
            EQUIP=0,
            UNEQUIP=1,
            DESCRIPTION=2;
    private int selectorPosition, selectorMaxPos;
    private boolean selectorVisible;
    public StatusMenu(BattleEntity e){
        displayed = e;
        selectorMaxPos = 3;
        selectorVisible = false;
        selectorPosition = 0;
    }
    public void setDisplayedEntity(BattleEntity e){
        displayed = e;
    }
    //gets from selector
    public int getSelectorMaxPosition(){return selectorMaxPos;}
    public int getSelectorPosition(){return selectorPosition;}
    public boolean isSelectorVisible(){return selectorVisible;}
    //selector controller
    public void toggleSelectorVisible(){
        if(selectorVisible){
            selectorVisible=false;
            return;
        }
        selectorVisible=true;
    }
    public void updateSelectorPosition(int newPos){
        selectorPosition = newPos;
    }
    
    public void paint(Graphics g){
        g.setColor(Color.magenta);
        g.fillRect(0, 0, 400, 480);
        g.setColor(Color.black);
        g.drawRect(0, 0, 400, 480);
        g.setFont(new Font("Courier New", Font.BOLD, 20));
        g.drawString(displayed.getName(),30,75);
        g.drawString(String.format("Level %d", displayed.getLevel()), 230, 75);
        g.drawString(String.format("%s experience to next level", displayed.getExpUntilLevel()), 30, 100);
        g.drawString(String.format("%d/%d HP", displayed.getStat(StatID.HP),displayed.getStat(StatID.MAXHP)), 30, 130);
        g.drawString(String.format("%d/%d MP", displayed.getStat(StatID.MP),displayed.getStat(StatID.MAXMP)), 230, 130);
        for(int i=StatID.STR;i<=StatID.RES;i++){
            //            4              9                                                        i starts at 4
            g.drawString(String.format("%s:%d | %d", StatID.getStatName(i),displayed.getBaseStat(i),displayed.getStat(i)), 30, 45+30*i);
        }
        try{
            g.drawString(String.format("Weapon: %s",displayed.getEquipment(0).getName()), 30, 325);
        }catch(NullPointerException e){
            g.drawString(String.format("Weapon: ---"), 30, 325);
        }
        for(int i=1;i<4;i++){
            try{
                g.drawString(String.format("Armor:  %s",displayed.getEquipment(i).getName()), 30, 325+30*i);
            }catch(NullPointerException e){
                g.drawString(String.format("Armor:  ---"), 30, 325+30*i);
            }
        }
        
        if(selectorVisible){
            g.drawString(">", 0,325+30*selectorPosition);
        }
    }
    public static void main(String[] args){
        PauseMenuTester.main(args);
    }
}
