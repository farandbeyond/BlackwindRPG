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
    public static Party loadParty(int mapID,int partyID){
        Party p = new Party(3);
        switch(mapID){
            case 0:
            switch(partyID){
                case 0:
                case 1:
                case 2:
                case 3:
                case 4:
                    p.addPartyMember(BattleEntityLoader.loadEntityWithSkills(BattleEntityLoader.BAT));break;
            }
            break;
        }
    return p;
    }
}