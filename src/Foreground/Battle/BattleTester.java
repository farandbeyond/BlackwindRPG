/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Foreground.Battle;

import Background.BattleActions.BattleActionLoader;
import Background.BattleEntityLoader;
import Background.Items.Equipment;
import Background.Items.Inventory;
import Background.Items.ItemLoader;
import Background.Party.Party;
import Foreground.Menu.PauseMenu;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

/**
 *
 * @author Connor
 */
public class BattleTester {
    public static void main(String[] args){
        JFrame frame = new JFrame();
        Party party = new Party(4);
        Party party2 = new Party(3);
        Inventory inv = new Inventory(15);
        frame.setLayout(null);
        frame.setSize(618, 480);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        party.addPartyMember(BattleEntityLoader.loadEntity(BattleEntityLoader.WILSON));
        party.addPartyMember(BattleEntityLoader.loadEntity(BattleEntityLoader.TESTENTITY));
        //party.damagePartyMember(1, 100);
        
        party2.addPartyMember(BattleEntityLoader.loadEntityWithSkills(BattleEntityLoader.ENEMYONE));
        party2.addPartyMember(BattleEntityLoader.loadEntityWithSkills(BattleEntityLoader.HEALBOT));
        //party.damagePartyMember(0, 25);
        
        party.getMemberFromParty(0).addSkill(BattleActionLoader.loadAction(BattleActionLoader.FIRE));
        party.getMemberFromParty(0).addSkill(BattleActionLoader.loadAction(BattleActionLoader.CURE));
        party.getMemberFromParty(0).addSkill(BattleActionLoader.loadAction(BattleActionLoader.BRAVERY));
        party.getMemberFromParty(0).addSkill(BattleActionLoader.loadAction(BattleActionLoader.SLICE));
        party.getMemberFromParty(1).addSkill(BattleActionLoader.loadAction(BattleActionLoader.WIND));
        party.getMemberFromParty(0).equip((Equipment)ItemLoader.loadItem(ItemLoader.BRONZESWORD, 1), 0);
        party.getMemberFromParty(0).giveExp(1200);
        party.getMemberFromParty(0).checkForLevelUp();
        party.getMemberFromParty(1).checkForLevelUp();
        party.getMemberFromParty(0).equip((Equipment)ItemLoader.loadItem(ItemLoader.LEATHERARMOR, 1), 1);
        inv.add(ItemLoader.loadItem(ItemLoader.BRONZESWORD, 2));
        inv.add(ItemLoader.loadItem(ItemLoader.ELIXER, 10));
        inv.add(ItemLoader.loadItem(ItemLoader.FIREBOMB, 11));
        inv.add(ItemLoader.loadItem(ItemLoader.ICEBOMB, 99));
        inv.add(ItemLoader.loadItem(ItemLoader.LEATHERARMOR, 3));
        inv.add(ItemLoader.loadItem(ItemLoader.LEATHERGLOVES, 3));
        inv.add(ItemLoader.loadItem(ItemLoader.MUDBOMB, 4));
        inv.add(ItemLoader.loadItem(ItemLoader.PHEONIXDOWN, 5));
        inv.add(ItemLoader.loadItem(ItemLoader.POTION, 10));
        inv.add(ItemLoader.loadItem(ItemLoader.REJUVI, 10));
        inv.add(ItemLoader.loadItem(ItemLoader.WINDBOMB, 12));
        inv.add(ItemLoader.loadItem(ItemLoader.IRONSWORD, 1));
        Battle battle = new Battle(party,inv,party2);
        frame.add(battle);
        frame.addKeyListener(battle.getKL());
        battle.loop();
    }
    public static void fromInv(String[] args, Inventory inv, Party p, JFrame frame){
        Party party2 = new Party(3);
        party2.addPartyMember(BattleEntityLoader.loadEntityWithSkills(BattleEntityLoader.ENEMYONE));
        party2.addPartyMember(BattleEntityLoader.loadEntityWithSkills(BattleEntityLoader.HEALBOT));
        Battle battle = new Battle(p,inv,party2);
        frame.add(battle);
        frame.addKeyListener(battle.getKL());
        battle.loop();
    }
}
