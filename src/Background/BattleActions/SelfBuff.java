/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Background.BattleActions;

import Background.BattleEntity;
import Background.DeBuffs.*;
import Background.StatID;

/**
 *
 * @author Connor
 */
public class SelfBuff extends EffectSpell{

    public SelfBuff(int id,BattleEntity caster, String name, String description, int cost, Effect effect) {
        super(id,caster, name, description, cost, effect);
    }
     public SelfBuff(int id,String name, String description, int cost, Effect effect) {
        super(id,null, name, description, cost, effect);
    }
        public String cast(BattleEntity target) {
        getCaster().useMp(getCost());
        getCaster().addEffect(getEffect());
        return String.format("%s bolstered their own %s", getCaster().getName(),getEffect().getType()==Effect.BUFF?StatID.getStatName(((Buff)getEffect()).getStat()):StatID.getStatName(((Debuff)getEffect()).getStat()));
    }
    
}
