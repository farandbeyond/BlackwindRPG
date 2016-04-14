/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Background.Items;

import Background.BattleEntity;
import Background.BattleEntityLoader;

/**
 *
 * @author Connor
 */
public class ItemTester {
    public static void main(String[] args){
        text("----------Test01----------");
        text("creating a healing Item, and an entity to heal");
        Item potion = ItemLoader.loadItem(ItemLoader.POTION, 5);
        BattleEntity tester = BattleEntityLoader.loadEntity(BattleEntityLoader.TESTENTITY);
        text("----------Test02----------");
        text("Use the item on the entity.");
        tester.damage(10);
        text(potion.getQuantity()+"");
        tester.printHpAndMp();
        potion.use(tester);
        text(potion.getQuantity()+"");
        tester.printHpAndMp();
        
        
    }
    public static void text(String text){
        System.out.println(text);
    }
}
