/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Foreground.Events;

import Background.Items.Inventory;
import Background.Party.Party;
import Foreground.BlackwindTemp.Blackwind;
import Foreground.BlackwindTemp.Sprite;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Connor
 */
public class PlayerMovingSegment extends EventSegment{
    int moveX, moveY;
    PlayerMovingSegment(int moveX, int moveY){
        this.moveX = moveX;
        this.moveY = moveY;
    }
    @Override
    public String activate(Blackwind b, Inventory inv, Party p){
        int direction;
        if(moveX>0){
            direction = Blackwind.RIGHT;
        }else if(moveX<0){
            direction = Blackwind.LEFT;
        }else if(moveY>0){
            direction = Blackwind.DOWN;
        }else if(moveY<0){
            direction = Blackwind.UP;
        }else{
            direction = Blackwind.STILL;
        }
        b.queueMovement(direction, Integer.signum(moveX+moveY)*(moveX+moveY));
        /*for(int i=0;i<Integer.signum(moveX+moveY)*(moveX+moveY);i++){
            try{
                System.out.printf("Moving player %d",direction);
                b.move(direction);
                while(b.getMC().isWalking()){
                    b.getMC().animate(b.getMC().getDirection());
                    b.shiftMap(b.getMC().getDirection());
                    b.repaint();
                    Thread.sleep(b.fps);
                }
            }catch(InterruptedException e){
                System.out.println("Thread Error Occurred");
            }
        }*/
        return "adv!!";
    }
    
}
