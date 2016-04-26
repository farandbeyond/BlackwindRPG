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
    private int cost;
    public Spell(BattleEntity e, String name, String description,int cost){
        super(e,name, description);
        this.cost=cost;
    }
    public abstract void cast(BattleEntity target);
    public int getCost(){return cost;}
    @Override
    public void execute(BattleEntity target) {
        cast(target);
    }
    
}
