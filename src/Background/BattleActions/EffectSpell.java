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
import Background.ElementHandler;
import Background.StatID;

/**
 *
 * @author Connor
 */
public class EffectSpell extends Spell{
    private Effect effect;
    
    public EffectSpell(BattleEntity caster,String name, String description,int cost, Effect effect){
        super(caster,name,description,cost);
        this.effect=effect;
    }
    public EffectSpell(String name, String description,int cost, Effect effect){
        super(null,name,description,cost);
        this.effect=effect;
    }
    @Override
    public String cast(BattleEntity target) {
        getCaster().useMp(getCost());
        if(effect.getType()==Effect.BUFF){
            target.addEffect(Effect.effectLoader(((Buff)effect).getType(), ((Buff)effect).getStat(), ((Buff)effect).getIncreaseLevel(), getCaster().getName()));
            return String.format("%s bolstered %s's %s",getCaster().getName(),target.getName(),StatID.getStatName(((Buff)effect).getStat()));
        }else if(effect.getType()==Effect.DEBUFF){
            target.addEffect(Effect.effectLoader(((Debuff)effect).getType(), ((Debuff)effect).getStat(), ((Debuff)effect).getDecreaseLevel(), getCaster().getName()));
            return String.format("%s reduced %s's %s",getCaster().getName(),target.getName(),StatID.getStatName(((Debuff)effect).getStat()));
        }
        return "something went wrong";
    }
    //gets
    public Effect getEffect(){return effect;}
    public int getElement(){return ElementHandler.NEUTRAL;}
}
