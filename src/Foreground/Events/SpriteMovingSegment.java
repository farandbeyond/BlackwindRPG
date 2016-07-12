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

/**
 *
 * @author Connor
 */
public class SpriteMovingSegment extends EventSegment{
    int moveX, moveY;
    String spriteName;
    public SpriteMovingSegment(String spriteName, int moveX, int moveY) {
        this.moveX = moveX;
        this.moveY = moveY;
        this.spriteName = spriteName;
    }
    @Override
    public String activate(Blackwind b, Inventory i, Party p) {
        Sprite movingSprite = b.getMap().getSprite(spriteName);
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
        if(movingSprite!=null){
            b.queueNpcMovement(direction,  Integer.signum(moveX+moveY)*(moveX+moveY), spriteName);
            return "adv!!";
        }else{
            return String.format("Error Occurred moving Sprite %s",spriteName);
        }
    }
    
}
