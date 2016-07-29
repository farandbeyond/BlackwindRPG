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
    public Spell(int id,BattleEntity e, String name, String description,int cost){
        super(id,e,name, description);
        this.cost=cost;
    }
    public abstract String cast(BattleEntity target);
    public abstract boolean targetsAllies();
    @Override
    public String execute(BattleEntity target) {
        return cast(target);
    }
    //gets
    public int getCost(){return cost;}
}
