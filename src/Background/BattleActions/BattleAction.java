
<<<<<<< HEAD
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
    private String name,description;
    public abstract void execute(BattleEntity target);
    public BattleAction(BattleEntity caster,String name, String description){
        this.name=name;
        this.description=description;
        this.caster=caster;
    }
    //gets
    public BattleEntity getCaster(){return caster;}
    public int getCasterStat(int statID){return caster.getStat(statID);}
    public String getName(){return name;}
    public String getDescription(){return description;}
    public BattleAction(BattleEntity caster){
        this.caster = caster;
    }
}
=======
>>>>>>> origin/master