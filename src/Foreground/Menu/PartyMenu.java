/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Foreground.Menu;

import Background.BattleEntity;
import Background.Party.Party;
import Background.StatID;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.*;

/**
 *
 * @author Connor
 */
public class PartyMenu extends JPanel{
    private Party party;
    private int selectorPosition;
    private boolean selectorVisible;
    private final int partySize=4;
    private final int distFromTop = 50, distFromLeft = 30;
    
    public PartyMenu(Party p){
        party = p;
        selectorPosition=0;
        selectorVisible=false;
        this.setVisible(true);
    }
    public void updateParty(Party p){
        this.party = p;
    }
    //selector controllers
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
    //gets
    public int getSelectorMaxPosition(){return 4;}
    public int getSelectorPosition(){return selectorPosition;}
    public boolean isSelectorVisible(){return selectorVisible;}
    public BattleEntity getPartyMember(int member){
        return party.getMemberFromParty(member);
    }
    //paint
    public void paint(Graphics g){
        g.setColor(Color.yellow);
        g.fillRect(0, 0, 400, 480);
        g.setColor(Color.black);
        g.drawRect(0, 0, 400, 480);
        g.setFont(new Font("Serif",Font.BOLD, 18));
        for(int i=0;i<partySize;i++){
            try{
                g.drawString(party.getMemberFromParty(i).getName(), distFromLeft, distFromTop+85*i);
                g.drawString(party.getHpMaxHp(i), distFromLeft, distFromTop+30+85*i);
                g.drawString(party.getMpMaxMp(i), distFromLeft+80, distFromTop+30+85*i);
            }catch(NullPointerException e){
                g.drawString("-----", distFromLeft, distFromTop+85*i);
                g.drawString("--/--", distFromLeft, distFromTop+30+85*i);
                g.drawString("--/--", distFromLeft+80, distFromTop+30+85*i);
            }
        }
        if(selectorVisible){
            g.drawString(">", 0, distFromTop+85*selectorPosition);
        }
        
    }
}
