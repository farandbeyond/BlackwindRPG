/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Foreground.Menu;

import Background.BattleEntity;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 *
 * @author Connor
 */
public class SpellsMenu extends JPanel{
    private int selectorPosition, selectorMaxPos;
    private boolean selectorVisible;
    private BattleEntity viewed;
    private final int distFromTop = 50, distFromLeft = 30;
    private int currOffset, maxOffset;
    
    public void paint(Graphics g){
        g.setColor(Color.green);
        g.fillRect(0, 0, 400, 480);
        g.setColor(Color.black);
        g.drawRect(0, 0, 400, 480);
        g.setFont(new Font("Courier New", Font.BOLD, 20));
        for(int i=0;i<10;i++){
            try{
                g.drawString(viewed.getSkill(i+currOffset).getName(),distFromLeft,distFromTop+35*i);
                g.drawString(viewed.getSkill(i+currOffset), i, i);
                //g.drawString(inv.getItem(i+currOffset).getName(), distFromLeft, distFromTop+35*i);
                //g.drawString("x"+inv.getItem(i+currOffset).getQuantity(), 370, distFromTop+35*i);
            }catch(IndexOutOfBoundsException e){
                //g.drawString("---", distFromLeft, distFromTop+35*i);
                //g.drawString("x-", 370, distFromTop+35*i);
            }
        }
        if(selectorVisible){
            g.drawString(">", 0, distFromTop+35*selectorPosition);
        }
    }
}
