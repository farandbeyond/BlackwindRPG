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
        text("----------Test03----------");
        text("Damage Item");
        Item bomb = ItemLoader.loadItem(ItemLoader.FIREBOMB, 5);
        tester.printHpAndMp();
        bomb.use(tester);
        tester.printHpAndMp();
        text("----------Test04----------");
        text("Weapon");
        Item sword = ItemLoader.loadItem(ItemLoader.BRONZESWORD, 1);
        tester.printAllStats();
        tester.equip((Equipment)sword, 0);
        tester.printAllEquipment();
        text("----------Test05----------");
        text("Armor");
        Item armor = ItemLoader.loadItem(ItemLoader.LEATHERARMOR, 1);
        tester.equip((Equipment)armor, 1);
        tester.printAllEquipment();
        tester.printAllStats();
        tester.printHpAndMp();
        text("----------Test05----------");
        text("now unequip");
        tester.unEquip(0);
        tester.heal(20);
        tester.printHpAndMp();
        tester.unEquip(1);
        tester.printAllEquipment();
        tester.printAllStats();
        tester.printHpAndMp();
    }
    public static void text(String text){
        System.out.println(text);
    }
}
