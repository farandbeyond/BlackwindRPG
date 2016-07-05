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
            FIREBALLSCROLL=401,
            ICEBLASTSCROLL=402,
            QUAKESCROLL=403,
            GUSTSCROLL=404;
    
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
            case FIREBALLSCROLL :return new SkillTeaching(itemID,"Fireball Scroll","Teaches the 'Fireball' Skill",      quantity,15,BattleActionLoader.FIREBALL,1500);
            case ICEBLASTSCROLL :return new SkillTeaching(itemID,"Ice Blast Scroll","Teaches the 'Ice Blast' Skill",    quantity,15,BattleActionLoader.ICEBLAST,1500);
            case QUAKESCROLL    :return new SkillTeaching(itemID,"Quake Scroll","Teaches the 'Quake' Skill",            quantity,15,BattleActionLoader.QUAKE,1500);
            case GUSTSCROLL     :return new SkillTeaching(itemID,"Gust Scroll","Teaches the 'Gust' Skill",              quantity,15,BattleActionLoader.GUST,1500);
        }
        return null;
    }
}
