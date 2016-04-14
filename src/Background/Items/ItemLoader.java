/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Background.Items;

/**
 *
 * @author Connor
 */
public class ItemLoader {
    public static final int
            POTION=0;
    
    public static Item loadItem(int itemID, int quantity){
        switch(itemID){
            case POTION:return new HealingItem(itemID,"Potion","Heals the user for 20hp",quantity,5,20,HealingItem.HP,false,100);
        }
        return null;
    }
}
