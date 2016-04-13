/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Connor
 */
package Background;


public class BattleEntity {
    
    private Stat[] stats;
    private String name;
    
    
    BattleEntity(int hp,int maxhp,double hpgrowth, int mp, int maxmp, double mpgrowth, int str, double strgrowth, 
            int dex, double dexgrowth, int vit, double vitgrowth, int intel, double intgrowth, int res, double resgrowth, String name){
        stats = new Stat[9];
        stats[StatID.HP]=new Stat(hp,hpgrowth);
        stats[StatID.MAXHP]=new Stat(maxhp,hpgrowth);
        stats[StatID.MP]=new Stat(mp,mpgrowth);
        stats[StatID.MAXMP]=new Stat(maxmp,mpgrowth);
        stats[StatID.STR]=new Stat(str,strgrowth);
        stats[StatID.DEX]=new Stat(dex,dexgrowth);
        stats[StatID.VIT]=new Stat(vit,vitgrowth);
        stats[StatID.INT]=new Stat(intel,intgrowth);
        stats[StatID.RES]=new Stat(res,resgrowth);
        this.name=name;
    }
        BattleEntity(int maxhp,double hpgrowth, int maxmp, double mpgrowth, int str, double strgrowth, 
            int dex, double dexgrowth, int vit, double vitgrowth, int intel, double intgrowth, int res, double resgrowth, String name){
        stats = new Stat[9];
        stats[StatID.HP]=new Stat(maxhp,hpgrowth);
        stats[StatID.MAXHP]=new Stat(maxhp,hpgrowth);
        stats[StatID.MP]=new Stat(maxmp,mpgrowth);
        stats[StatID.MAXMP]=new Stat(maxmp,mpgrowth);
        stats[StatID.STR]=new Stat(str,strgrowth);
        stats[StatID.DEX]=new Stat(dex,dexgrowth);
        stats[StatID.VIT]=new Stat(vit,vitgrowth);
        stats[StatID.INT]=new Stat(intel,intgrowth);
        stats[StatID.RES]=new Stat(res,resgrowth);
        this.name=name;
    }
    
    
    
    private class Stat{
        int baseStat;
        int modifiedStat;
        double statGrowth;
        double growthOverflow;
        Stat(int stat, double growth){
            baseStat=stat;
            statGrowth=growth;
            modifiedStat=0;
            growthOverflow=0;
        }
        public void levelUp(){
            growthOverflow+=statGrowth;
            while(growthOverflow>1){
                baseStat++;
                growthOverflow--;
            }
        }
        public void increaseStatModifier(int increase){
            modifiedStat+=increase;
        }
        public void decreaseStatModifier(int decrease){
            modifiedStat+=decrease;
        }
        public int getBaseStat(){return baseStat;}
        public int getStatModifier(){return modifiedStat;}
        public int getModifiedStat(){return modifiedStat+baseStat;}
        public double getStatGrowth(){return statGrowth;}
        public double getGrowthOverflow(){return growthOverflow;}
    }
}
