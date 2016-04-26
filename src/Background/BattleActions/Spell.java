/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Background.BattleActions;

import Background.BattleEntity;

/**
 *
 * @author Connor
 */
public abstract class Spell extends BattleAction{
    
    public Spell(BattleEntity e){
        super(e);
    }
    public abstract void cast(BattleEntity target);
    
    @Override
    public void execute(BattleEntity target) {
        cast(target);
    }
    
}
