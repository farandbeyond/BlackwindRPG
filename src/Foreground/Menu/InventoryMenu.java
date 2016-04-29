/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Foreground.Menu;

import Background.Items.Inventory;
import java.awt.*;
import javax.swing.*;

/**
 *
 * @author Connor
 */
public class InventoryMenu extends JPanel{
    Inventory inv;
    private final int distFromTop = 50, distFromLeft = 30;
    int currOffset, maxOffset;
    InventoryMenu(Inventory inv){
        this.inv=inv;
        currOffset=0;
        setMaxOffset();
    }
    private void setMaxOffset(){
        maxOffset=0;
        while(true){
            try{
                inv.getItem(maxOffset).toString();
                maxOffset++;
            }catch(IndexOutOfBoundsException e){
                //System.out.println(String.format("max offset is %d, from an inventory size %d, containing %d items", maxOffset, inv.getInvSize(), inv.getNumberOfItemsInInventory()));
                return;
            }
        }
    }
    
    public void paint(Graphics g){
        g.setColor(Color.cyan);
        g.fillRect(0, 0, 400, 480);
        g.setColor(Color.black);
        g.drawRect(0, 0, 400, 480);
        g.setFont(new Font("Serif",Font.BOLD, 18));
        for(int i=0+currOffset;i<10;i++){
            try{
                g.drawString(inv.getItem(i).getName(), distFromLeft, distFromTop+35*i);
                g.drawString("x"+inv.getItem(i).getName(), 370, distFromTop+35*i);
            }catch(IndexOutOfBoundsException e){
                g.drawString("---", distFromLeft, distFromTop+35*i);
                g.drawString("x-", 370, distFromTop+35*i);
            }
        }
    }
}
