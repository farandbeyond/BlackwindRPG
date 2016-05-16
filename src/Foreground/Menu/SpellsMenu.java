/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Foreground.Menu;

import Background.BattleActions.BattleAction;
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
    public static final int
            CAST=0,
            DESCRIPTION=1,
            ELEMENT=2;
    private int selectorPosition, selectorMaxPos;
    private boolean selectorVisible;
    private BattleEntity viewed;
    private final int distFromTop = 70, distFromLeft = 30;
    private int currOffset, maxOffset;
    public SpellsMenu(BattleEntity viewed){
        this.viewed=viewed;
        currOffset = 0;
        setMaxOffset();
        selectorMaxPos = 9;
        selectorPosition = 0;
    }
    //sets
    public void setMaxOffset(){
        maxOffset=0;
        while(true){
            try{
                viewed.getSkill(maxOffset+10).toString();
                maxOffset++;
            }catch(IndexOutOfBoundsException e){
                System.out.println(String.format("Max offset is %d, looking at the entity %s with %d skills",maxOffset,viewed.getName(),viewed.getNumberOfSkills()));
                return;
            }
        }
    }
    public void setDisplayedEntity(BattleEntity e){
        viewed = e;
    }
    //gets
    public BattleAction getSkillAtPosition(){return viewed.getSkill(selectorPosition);}
    public int getSelectorMaxPosition(){return selectorMaxPos;}
    public int getSelectorPosition(){return selectorPosition;}
    public boolean isSelectorVisible(){return selectorVisible;}
    //selector controlling
    public int updateOffsetSelectorPosition(int newPos){
        //if you scroll down far enough and there are more options to load, scroll the inventory and add to the offset
        try{
            if(newPos!=selectorPosition){
                if(newPos==9){
                    currOffset = maxOffset;
                }
                if(newPos==0){
                    currOffset = 0;
                }
                //-1 is intened to throw null error if not exist
                if(newPos>=7&&viewed.getSkill(10+currOffset)!=null){
                    currOffset++;
                    //System.out.println(currOffset+"vv"+newPos);
                    return newPos-1;
                }
                if(newPos<=4&&viewed.getSkill(currOffset-1)!=null){
                    currOffset--;
                    //System.out.println(currOffset+"^^"+newPos);
                    return newPos+1;
                }

                selectorPosition = newPos;
            }
            //System.out.println(currOffset+">>"+newPos);
            return newPos;
        }catch(IndexOutOfBoundsException e){
            //System.out.println("Handling an error");
            
            //System.out.println(currOffset+">>"+newPos);
            selectorPosition = newPos;
            return newPos;
        }
    }
    public void toggleSelectorVisible(){
        if(selectorVisible){
            selectorVisible=false;
            return;
        }
        selectorVisible=true;
    }
    //paint
    public void paint(Graphics g){
        g.setColor(Color.green);
        g.fillRect(0, 0, 400, 480);
        g.setColor(Color.black);
        g.drawRect(0, 0, 400, 480);
        g.setFont(new Font("Courier New", Font.BOLD, 20));
        for(int i=0;i<10;i++){
            try{
                g.drawString(viewed.getSkill(i+currOffset).getName(),distFromLeft,distFromTop+35*i);
                g.drawString("mp: "+viewed.getSkill(i+currOffset).getCost(), 310, distFromTop+35*i);
            }catch(IndexOutOfBoundsException e){
                g.drawString("---", distFromLeft, distFromTop+35*i);
                g.drawString("mp: --", 310, distFromTop+35*i);
            }
        }
        if(selectorVisible){
            g.drawString(">", 0, distFromTop+35*selectorPosition);
        }
    }
}
