/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Background.Items;

import Background.BattleActions.BattleActionLoader;
import Background.ElementHandler;
import Background.StatID;

/**
 *
 * @author Connor
 */
public class ItemLoader {
    public static final int
            POTION=0,
            ELIXER=1,
            REJUVI=2,
            PHEONIXDOWN=3,
            FIREBOMB=100,
            ICEBOMB=101,
            WINDBOMB=102,
            MUDBOMB=103,
            BRONZESWORD = 200,
            IRONSWORD=201,
            LEATHERARMOR = 300,
            LEATHERGLOVES=301,
            CURESCROLL = 400,
            FIRESCROLL=401,
            WATERSCROLL=402,
            EARTHSCROLL=403,
            WINDSCROLL=404,
            LIGHTSCROLL=405,
            DARKSCROLL=406;
    
    public static Item loadItem(int itemID, int quantity){
        switch(itemID){
            case POTION         :return new HealingItem(itemID, "Potion",       "Heals the user for 20hp",              quantity,99,20,HealingItem.HP,false,10);
            case ELIXER         :return new HealingItem(itemID, "Elixer",       "Restores 20mp to user",                quantity,99,20,HealingItem.MP,false,30);
            case REJUVI         :return new HealingItem(itemID, "Rejuvination", "Restores 15hp and 15mp to user",       quantity,99,15,HealingItem.HPMP,false,100);
            case PHEONIXDOWN    :return new HealingItem(itemID, "Phoenix Down", "Revives user with 5hp",                quantity,99,5,HealingItem.HP,true,500);
            case FIREBOMB       :return new DamageItem(itemID,  "Fire Bomb",    "Deals 10 fire damage",                 quantity,99,10,ElementHandler.FIRE,30);
            case ICEBOMB        :return new DamageItem(itemID,  "Ice Bomb",     "Deals 10 Water damage",                quantity,99,10,ElementHandler.WATER,30);
            case WINDBOMB       :return new DamageItem(itemID,  "Wind Bomb",    "Deals 10 Air damage",                  quantity,99,10,ElementHandler.AIR,30);
            case MUDBOMB        :return new DamageItem(itemID,  "Mud Bomb",     "Deals 10 Earth damage",                quantity,99,10,ElementHandler.EARTH,30);
            case BRONZESWORD    :return new Weapon(itemID,      "Bronze Sword", "A simple sword",                       quantity,10,5,4,StatID.DEX,0,500);
            case IRONSWORD      :return new Weapon(itemID,      "Iron Sword",   "A Basic sword",                        quantity,10,10,8,StatID.STR,1,500);
            case LEATHERARMOR   :return new Armor(itemID,       "Leather Armor","Basic Armor",                          quantity,10,StatID.VIT,2,StatID.MAXHP,10,300);
            case LEATHERGLOVES  :return new Armor(itemID,       "Leather Gloves","Dextrous Armor",                      quantity,10,StatID.DEX,5,StatID.VIT,1,500);
            case CURESCROLL     :return new SkillTeaching(itemID,"Cure Scroll", "Teaches the 'Cure' Skill",             quantity,15,BattleActionLoader.CURE,1500);
            case FIRESCROLL :return new SkillTeaching(itemID,"Kasai Scroll","Teaches the 'Kasai' Skill",                quantity,15,BattleActionLoader.FIRE,1500);
            case WATERSCROLL :return new SkillTeaching(itemID,"Mizu Scroll","Teaches the 'Mizu' Skill",                 quantity,15,BattleActionLoader.WATER,1500);
            case EARTHSCROLL    :return new SkillTeaching(itemID,"Setchi Scroll","Teaches the 'Setchi' Skill",          quantity,15,BattleActionLoader.EARTH,1500);
            case WINDSCROLL     :return new SkillTeaching(itemID,"Kaze Scroll","Teaches the 'Kaze' Skill",              quantity,15,BattleActionLoader.WIND,1500);
            case LIGHTSCROLL     :return new SkillTeaching(itemID,"Hikari Scroll","Teaches the 'Hikari' Skill",         quantity,15,BattleActionLoader.LIGHT,1500);
            case DARKSCROLL     :return new SkillTeaching(itemID,"Yami Scroll","Teaches the 'Yami' Skill",              quantity,15,BattleActionLoader.DARK,1500);
        }
        return null;
    }
}
