/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Foreground.BlackwindTemp;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author Connor
 */
public class Joystick implements KeyListener{
    Blackwind game;
    public Joystick(Blackwind b){
        game = b;
    }
    @Override
    public void keyTyped(KeyEvent ke) {
    }
    @Override
    public void keyPressed(KeyEvent ke) {
        switch(Blackwind.gameState){
            case Blackwind.SHOP:
                switch(ke.getExtendedKeyCode()){
                    case KeyEvent.VK_O:game.getShop().confirmEvent();break;
                    case KeyEvent.VK_P:game.getShop().cancelEvent();break;
                    case KeyEvent.VK_W:game.getShop().upEvent();break;
                    case KeyEvent.VK_A:game.getShop().leftEvent();break;
                    case KeyEvent.VK_S:game.getShop().downEvent();break;
                    case KeyEvent.VK_D:game.getShop().rightEvent();break;
                }break;
            case Blackwind.MAP:
                //System.out.println("Map Command");
                switch(ke.getExtendedKeyCode()){
                    case KeyEvent.VK_W:game.move(Blackwind.UP);break;
                    case KeyEvent.VK_S:game.move(Blackwind.DOWN);break;
                    case KeyEvent.VK_A:game.move(Blackwind.LEFT);break;
                    case KeyEvent.VK_D:game.move(Blackwind.RIGHT);break;
                    case KeyEvent.VK_ENTER:Blackwind.gameState = Blackwind.INVENTORY;break;
                    case KeyEvent.VK_O:game.triggerEvent();break;
                    //case KeyEvent.VK_B:Blackwind.gameState = Blackwind.BATTLE;break;
                }break;
            case Blackwind.BATTLE:
                switch(ke.getExtendedKeyCode()){
                case KeyEvent.VK_O:game.getBattle().confirmEvent();break;
                case KeyEvent.VK_P:game.getBattle().cancelEvent();break;
                case KeyEvent.VK_W:game.getBattle().upEvent();break;
                case KeyEvent.VK_A:game.getBattle().leftEvent();break;
                case KeyEvent.VK_S:game.getBattle().downEvent();break;
                case KeyEvent.VK_D:game.getBattle().rightEvent();break;
                case KeyEvent.VK_ENTER:game.getBattle().menuEvent();break;
            }break;
            case Blackwind.EVENT:
                switch(ke.getExtendedKeyCode()){
                    case KeyEvent.VK_O:if(!game.getMC().isWalking())game.getTextBox().advanceText(game);break;
                }break;
            case Blackwind.INVENTORY:
                //System.out.println("Menu Command");
                switch(ke.getExtendedKeyCode()){
                    case KeyEvent.VK_O:game.getMenu().confirmEvent();break;
                    case KeyEvent.VK_P:game.getMenu().cancelEvent();break;
                    case KeyEvent.VK_W:game.getMenu().upEvent();break;
                    case KeyEvent.VK_A:game.getMenu().leftEvent();break;
                    case KeyEvent.VK_S:game.getMenu().downEvent();break;
                    case KeyEvent.VK_D:game.getMenu().rightEvent();break;
                    //case KeyEvent.VK_ENTER:game.getMenu().menuEvent();break;
                }break;
        }
    }
    @Override
    public void keyReleased(KeyEvent ke) {
    }
    public static void main(String[] args){
        Blackwind.main(args);
    }
}
