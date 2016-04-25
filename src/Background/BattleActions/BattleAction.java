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
public abstract class BattleAction {
    private BattleEntity caster;
    public BattleAction(BattleEntity caster){
        this.caster = caster;
    }
}
