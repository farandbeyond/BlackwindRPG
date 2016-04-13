/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Background;



/**
 *
 * @author Connor
 */
public class BattleEntityLoader {
    public static final int
            TESTENTITY=0,
            WILSON=1;
            
    public static BattleEntity loadEntity(int entityID){
        switch(entityID){                          //hp,    mp,   str,   dex,   vit,   int,   res,     name,    element   level/xp
            case TESTENTITY: return new BattleEntity(20,4.8,10,3.6,10,1.0,10,1.1,10,2.0,10,0.5,10,1.0,"Player",ElementHandler.NEUTRAL,1,0);
            case WILSON:     return new BattleEntity(15,2.3,16,5.3,5 ,0.3,8 ,1.1,5 ,0.9,14,1.5,10,1.4,"Wilson",ElementHandler.NEUTRAL,1,0);
        }
        return null;
    }
}
