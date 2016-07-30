/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Foreground.Events;

import Background.Items.Inventory;
import Background.Party.Party;
import Foreground.BlackwindTemp.Blackwind;

/**
 *
 * @author Connor
 */
public class BattleSegment extends EventSegment{
    int enemyID1, enemyID2, enemyID3;
    BattleSegment(int id1, int id2, int id3){
        enemyID1 = id1;
        enemyID2 = id2;
        enemyID3 = id3;
    }

    @Override
    public String activate(Blackwind b, Inventory i, Party p) {
        Blackwind.gameState = Blackwind.BATTLE;
        b.prepBattle(enemyID1, enemyID2, enemyID3);
        return "adv!!";
    }
    
}
