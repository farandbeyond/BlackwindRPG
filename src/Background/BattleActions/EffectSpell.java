/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Background.BattleActions;

import Background.BattleEntity;
import Background.DeBuffs.Buff;
import Background.DeBuffs.Debuff;
import Background.DeBuffs.Effect;

/**
 *
 * @author Connor
 */
public class EffectSpell extends Spell{
    private Effect effect;
    private int cost;
    public EffectSpell(BattleEntity caster,int cost, Effect effect){
        super(caster);
        this.effect=effect;
        this.cost=cost;
    }
    public EffectSpell(int cost, Effect effect){
        super(null);
        this.effect=effect;
        this.cost=cost;
    }
    @Override
    public void cast(BattleEntity target) {
        getCaster().useMp(cost);
        if(effect.getType()==Effect.BUFF)
            target.addEffect(Effect.effectLoader(((Buff)effect).getType(), ((Buff)effect).getStat(), ((Buff)effect).getIncreaseLevel(), getCaster().getName()));
        else if(effect.getType()==Effect.DEBUFF)
            target.addEffect(Effect.effectLoader(((Debuff)effect).getType(), ((Debuff)effect).getStat(), ((Debuff)effect).getDecreaseLevel(), getCaster().getName()));
    }
    
}
