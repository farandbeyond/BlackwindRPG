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
public class EnemyPartyLoader {
    public static Party loadParty(int partyID){
        Party p = new Party(3);
        switch(partyID){
            case 0:
                p.addPartyMember(BattleEntityLoader.loadEntityWithSkills(BattleEntityLoader.ENEMYONE));break;
            case 1:
                p.addPartyMember(BattleEntityLoader.loadEntityWithSkills(BattleEntityLoader.BAT));
                p.addPartyMember(BattleEntityLoader.loadEntityWithSkills(BattleEntityLoader.BAT));
                p.addPartyMember(BattleEntityLoader.loadEntityWithSkills(BattleEntityLoader.BAT));break;
            case 2:
                p.addPartyMember(BattleEntityLoader.loadEntityWithSkills(BattleEntityLoader.GOBLIN));
                p.addPartyMember(BattleEntityLoader.loadEntityWithSkills(BattleEntityLoader.BAT));
                p.addPartyMember(BattleEntityLoader.loadEntityWithSkills(BattleEntityLoader.HEALBOT));break;
            case 3:
                p.addPartyMember(BattleEntityLoader.loadEntityWithSkills(BattleEntityLoader.GOBLIN));
                p.addPartyMember(BattleEntityLoader.loadEntityWithSkills(BattleEntityLoader.ENEMYONE));
                p.addPartyMember(BattleEntityLoader.loadEntityWithSkills(BattleEntityLoader.GOBLIN));break;
            case 4:
                p.addPartyMember(BattleEntityLoader.loadEntityWithSkills(BattleEntityLoader.BAT));break;
        }
        return p;
    }
}
