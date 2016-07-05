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
                p.addPartyMember(BattleEntityLoader.loadEntityWithSkills(BattleEntityLoader.ENEMYONE));
        }
        return p;
    }
}
