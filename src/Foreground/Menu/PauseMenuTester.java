/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Foreground.Menu;

import Background.BattleActions.BattleActionLoader;
import Background.BattleEntityLoader;
import Background.Items.Equipment;
import Background.Items.Inventory;
import Background.Items.ItemLoader;
import Background.Party.Party;
import javax.swing.*;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

/**
 *
 * @author Connor
 */
public class PauseMenuTester {
    
    public static void main(String[] args){
        JFrame frame = new JFrame();
        Party party = new Party(4);
        Inventory inv = new Inventory(15);
        frame.setLayout(null);
        frame.setSize(618, 480);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        party.addPartyMember(BattleEntityLoader.loadEntity(BattleEntityLoader.WILSON));
        party.addPartyMember(BattleEntityLoader.loadEntity(BattleEntityLoader.TESTENTITY));
        party.getMemberFromParty(0).checkForLevelUp();

        party.getMemberFromParty(0).addSkill(BattleActionLoader.loadAction(BattleActionLoader.FIREBALL));
        party.getMemberFromParty(0).addSkill(BattleActionLoader.loadAction(BattleActionLoader.CURE));
        party.getMemberFromParty(0).addSkill(BattleActionLoader.loadAction(BattleActionLoader.BRAVERY));
        party.getMemberFromParty(0).addSkill(BattleActionLoader.loadAction(BattleActionLoader.SLICE));
        party.getMemberFromParty(1).addSkill(BattleActionLoader.loadAction(BattleActionLoader.GUST));

        party.getMemberFromParty(1).checkForLevelUp();
        party.getMemberFromParty(0).equip((Equipment)ItemLoader.loadItem(ItemLoader.LEATHERARMOR, 1), 1);
        inv.add(ItemLoader.loadItem(ItemLoader.BRONZESWORD, 1));
        inv.add(ItemLoader.loadItem(ItemLoader.ELIXER, 1));
        inv.add(ItemLoader.loadItem(ItemLoader.FIREBOMB, 11));
        inv.add(ItemLoader.loadItem(ItemLoader.ICEBOMB, 99));
        inv.add(ItemLoader.loadItem(ItemLoader.LEATHERARMOR, 1));
        inv.add(ItemLoader.loadItem(ItemLoader.MUDBOMB, 1));
        inv.add(ItemLoader.loadItem(ItemLoader.PHEONIXDOWN, 1));
        inv.add(ItemLoader.loadItem(ItemLoader.POTION, 1));
        inv.add(ItemLoader.loadItem(ItemLoader.REJUVI, 1));
        inv.add(ItemLoader.loadItem(ItemLoader.WINDBOMB, 1));
        inv.add(ItemLoader.loadItem(ItemLoader.IRONSWORD, 1));
        PauseMenu menu = new PauseMenu(frame,party,inv);
        menu.run(party, inv);
    }
}
