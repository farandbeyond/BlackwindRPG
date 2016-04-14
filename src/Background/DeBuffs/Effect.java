/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Background.DeBuffs;

import Background.BattleEntity;
import Background.StatID;

/**
 *
 * @author Connor
 */
public abstract class Effect {
    String name, source;
    int duration;
    public abstract void assign(BattleEntity e);
    public abstract void remove(BattleEntity e);
    public abstract void onTick(BattleEntity e);
    
    public Effect(String name, String source, int duration){
        this.name=name;
        this.source=source;
        this.duration=duration;
    }
    public String getName(){return name;}
    public String getSource(){return source;}
    public int getDuration(){return duration;}
    
    //effect loader information
    public static final int
            BUFF=1,
            DEBUFF=2,
            EFFECT=3;
    public static final int
            basicDuration = 5;
    public static Effect effectLoader(int type, int stat, int value, String source){
        switch(type){
            case BUFF:
                switch(stat){
                    case StatID.MAXHP:
                        switch(value){
                            case Buff.TENP:         return new Buff("10% HP",source,basicDuration,stat,0.1);
                            case Buff.TWENTYFIVEP:  return new Buff("25% HP",source,basicDuration,stat,0.25);
                            case Buff.FIFTYP:       return new Buff("50% HP",source,basicDuration,stat,0.5);
                        }
                    case StatID.MAXMP:
                        switch(value){
                            case Buff.TENP:         return new Buff("10% MP",source,basicDuration,stat,0.1);
                            case Buff.TWENTYFIVEP:  return new Buff("25% MP",source,basicDuration,stat,0.25);
                            case Buff.FIFTYP:       return new Buff("50% MP",source,basicDuration,stat,0.5);
                        }
                    case StatID.STR:
                        switch(value){
                            case Buff.TENP:         return new Buff("10% STR",source,basicDuration,stat,0.1);
                            case Buff.TWENTYFIVEP:  return new Buff("25% STR",source,basicDuration,stat,0.25);
                            case Buff.FIFTYP:       return new Buff("50% STR",source,basicDuration,stat,0.5);
                        }
                    case StatID.DEX:
                        switch(value){
                            case Buff.TENP:         return new Buff("10% DEX",source,basicDuration,stat,0.1);
                            case Buff.TWENTYFIVEP:  return new Buff("25% DEX",source,basicDuration,stat,0.25);
                            case Buff.FIFTYP:       return new Buff("50% DEX",source,basicDuration,stat,0.5);
                        }
                    case StatID.VIT:
                        switch(value){
                            case Buff.TENP:         return new Buff("10% VIT",source,basicDuration,stat,0.1);
                            case Buff.TWENTYFIVEP:  return new Buff("25% VIT",source,basicDuration,stat,0.25);
                            case Buff.FIFTYP:       return new Buff("50% VIT",source,basicDuration,stat,0.5);
                        }
                    case StatID.INT:
                        switch(value){
                            case Buff.TENP:         return new Buff("10% INT",source,basicDuration,stat,0.1);
                            case Buff.TWENTYFIVEP:  return new Buff("25% INT",source,basicDuration,stat,0.25);
                            case Buff.FIFTYP:       return new Buff("50% INT",source,basicDuration,stat,0.5);
                        }
                    case StatID.RES:
                        switch(value){
                            case Buff.TENP:         return new Buff("10% RES",source,basicDuration,stat,0.1);
                            case Buff.TWENTYFIVEP:  return new Buff("25% RES",source,basicDuration,stat,0.25);
                            case Buff.FIFTYP:       return new Buff("50% RES",source,basicDuration,stat,0.5);
                        }
                }
            case DEBUFF:
                switch(stat){
                    case StatID.MAXHP:
                        switch(value){
                            case Buff.TENP:         return new Debuff("10% HP",source,basicDuration,stat,0.1);
                            case Buff.TWENTYFIVEP:  return new Debuff("25% HP",source,basicDuration,stat,0.25);
                            case Buff.FIFTYP:       return new Debuff("50% HP",source,basicDuration,stat,0.5);
                        }
                    case StatID.MAXMP:
                        switch(value){
                            case Buff.TENP:         return new Debuff("10% MP",source,basicDuration,stat,0.1);
                            case Buff.TWENTYFIVEP:  return new Debuff("25% MP",source,basicDuration,stat,0.25);
                            case Buff.FIFTYP:       return new Debuff("50% MP",source,basicDuration,stat,0.5);
                        }
                    case StatID.STR:
                        switch(value){
                            case Buff.TENP:         return new Debuff("10% STR",source,basicDuration,stat,0.1);
                            case Buff.TWENTYFIVEP:  return new Debuff("25% STR",source,basicDuration,stat,0.25);
                            case Buff.FIFTYP:       return new Debuff("50% STR",source,basicDuration,stat,0.5);
                        }
                    case StatID.DEX:
                        switch(value){
                            case Buff.TENP:         return new Debuff("10% DEX",source,basicDuration,stat,0.1);
                            case Buff.TWENTYFIVEP:  return new Debuff("25% DEX",source,basicDuration,stat,0.25);
                            case Buff.FIFTYP:       return new Debuff("50% DEX",source,basicDuration,stat,0.5);
                        }
                    case StatID.VIT:
                        switch(value){
                            case Buff.TENP:         return new Debuff("10% VIT",source,basicDuration,stat,0.1);
                            case Buff.TWENTYFIVEP:  return new Debuff("25% VIT",source,basicDuration,stat,0.25);
                            case Buff.FIFTYP:       return new Debuff("50% VIT",source,basicDuration,stat,0.5);
                        }
                    case StatID.INT:
                        switch(value){
                            case Buff.TENP:         return new Debuff("10% INT",source,basicDuration,stat,0.1);
                            case Buff.TWENTYFIVEP:  return new Debuff("25% INT",source,basicDuration,stat,0.25);
                            case Buff.FIFTYP:       return new Debuff("50% INT",source,basicDuration,stat,0.5);
                        }
                    case StatID.RES:
                        switch(value){
                            case Buff.TENP:         return new Debuff("10% RES",source,basicDuration,stat,0.1);
                            case Buff.TWENTYFIVEP:  return new Debuff("25% RES",source,basicDuration,stat,0.25);
                            case Buff.FIFTYP:       return new Debuff("50% RES",source,basicDuration,stat,0.5);
                        }
                }
            case EFFECT:
                System.out.println("Effects NYI");
        }
        
        
        System.out.println("Invalid Effect");
        return null;
    }
}