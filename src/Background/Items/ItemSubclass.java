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
public class ItemSubclass {
    public static HealingItem getHealingItem(Item i){
        if(i.getClass()==ItemLoader.loadItem(ItemLoader.POTION, 1).getClass()){
            return (HealingItem)i;
        }
        return null;
    }
}
