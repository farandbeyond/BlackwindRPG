/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Background.Party;

import Background.BattleEntityLoader;

/**
 *
 * @author Connor
 */
public class PartyTester {
    public static void main(String[] args){
        text("----------Test01----------");
        text("creating the party");
        Party party = new Party(4);
        text("----------Test02----------");
        text("Adding entities to the party");
        party.addPartyMember(BattleEntityLoader.loadEntity(BattleEntityLoader.TESTENTITY));
        party.printAllMembersHpAndMp();
        party.addPartyMember(BattleEntityLoader.loadEntity(BattleEntityLoader.WILSON));
        party.printAllMembersHpAndMp();
        text("----------Test03----------");
        text("Swapping members");
        party.swapPartyMembers(0, 1);
        party.printAllMembersHpAndMp();
        text("----------Test04----------");
        text("Damage and Healing");
        party.printMembersHpAndMp(0);
        party.damagePartyMember(0, 5);
        party.printMembersHpAndMp(0);
        party.healPartyMember(0, 10);
        party.printMembersHpAndMp(0);
        text("----------Test05----------");
        text("MP use and regen");
        party.printMembersHpAndMp(0);
        party.usePartyMemberMp(0, 10);
        party.usePartyMemberMp(0, 20);
        party.printMembersHpAndMp(0);
        party.restAllMembers();
        text("----------Test06----------");
        text("Experience");
        party.givePartyMemberExp(0, 500);
        party.givePartyMemberExp(1, 150);
        party.printAllMembersHpAndMp();
    }
    public static void text(String text){
        System.out.println(text);
    }
}
