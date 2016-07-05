/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Foreground.Events;

import Background.BattleEntity;
import Background.BattleEntityLoader;
import Background.Items.Inventory;
import Background.Party.Party;

/**
 *
 * @author Connor
 */
public class PartyMemberSegment extends EventSegment{
    BattleEntity e;
    public PartyMemberSegment(int entityID){
        e = BattleEntityLoader.loadEntity(entityID);
    }
    @Override
    public String activate(Inventory i, Party p) {
        if(p.getMaxPartySize()!=p.getCurrentPartySize()){
            p.addPartyMember(e);
            return String.format("%s has joined the party!", e.getName());
        }else{
            return String.format("%s tried to join the party\nbut the party was full...",e.getName());
        }
        
    }
    
}
