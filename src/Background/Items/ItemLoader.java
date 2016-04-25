/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Background.Items;

import Background.ElementHandler;
import Background.StatID;

/**
 *
 * @author Connor
 */
public class ItemLoader {
    public static final int
            POTION=0,
            FIREBOMB=100,
            BRONZESWORD = 200,
            LEATHERARMOR = 300;
    
    public static Item loadItem(int itemID, int quantity){
        switch(itemID){
            case POTION:return new HealingItem(itemID,"Potion","Heals the user for 20hp",quantity,99,20,HealingItem.HP,false,10);
            case FIREBOMB:return new DamageItem(itemID,"Fire Bomb","Deals 10 fire damage",quantity,99,10,ElementHandler.FIRE,30);
            case BRONZESWORD:return new Weapon(itemID,"Bronze Sword","A simple sword",quantity,10,5,4,StatID.DEX,0,500);
            case LEATHERARMOR:return new Armor(itemID,"Leather Armor","Basic Armor",quantity,10,StatID.VIT,2,StatID.MAXHP,10,300);
        }
        return null;
    }
}
