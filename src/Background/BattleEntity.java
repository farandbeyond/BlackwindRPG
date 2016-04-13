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
    private int element,
                level,
                expRequiredToLevel,
                exp;
    private boolean isDead;
    
    /**
     * assumes the entity will be at less than full health. used for recreation
     * @param hp
     * @param maxhp
     * @param hpgrowth
     * @param mp
     * @param maxmp
     * @param mpgrowth
     * @param str
     * @param strgrowth
     * @param dex
     * @param dexgrowth
     * @param vit
     * @param vitgrowth
     * @param intel
     * @param intgrowth
     * @param res
     * @param resgrowth
     * @param name 
     */
    BattleEntity(int hp,int maxhp,double hpgrowth, int mp, int maxmp, double mpgrowth, int str, double strgrowth, 
            int dex, double dexgrowth, int vit, double vitgrowth, int intel, double intgrowth, int res, double resgrowth, String name, int element, int level, int exp){
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
        this.element=element;
        this.exp=exp;
        this.level=level;
        expRequiredToLevel=0;
        isDead = hp==0?true:false;
    }
    /**
     * assumes the entity will be at full health. used for creation and recreation within guild.
     * @param maxhp
     * @param hpgrowth
     * @param maxmp
     * @param mpgrowth
     * @param str
     * @param strgrowth
     * @param dex
     * @param dexgrowth
     * @param vit
     * @param vitgrowth
     * @param intel
     * @param intgrowth
     * @param res
     * @param resgrowth
     * @param name 
     */
    BattleEntity(int maxhp,double hpgrowth, int maxmp, double mpgrowth, int str, double strgrowth, 
            int dex, double dexgrowth, int vit, double vitgrowth, int intel, double intgrowth, int res, double resgrowth, String name, int element, int level, int exp){
        
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
        this.element=element;
        this.exp=exp;
        this.level=level;
        expRequiredToLevel=0;
        isDead=false;
    }
    /**
         * SHOULD ONLY BE CALLED FOR ENEMIES. stats are not meant to grow after being set
         * @param maxhp
         * @param maxmp
         * @param str
         * @param dex
         * @param vit
         * @param intel
         * @param res
         * @param name 
         */
    BattleEntity(int maxhp,int maxmp, int str, int dex, int vit, int intel, int res, String name, int element, int level,int exp){
            
            stats = new Stat[9];
            stats[StatID.HP]=new Stat(maxhp);
            stats[StatID.MAXHP]=new Stat(maxhp);
            stats[StatID.MP]=new Stat(maxmp);
            stats[StatID.MAXMP]=new Stat(maxmp);
            stats[StatID.STR]=new Stat(str);
            stats[StatID.DEX]=new Stat(dex);
            stats[StatID.VIT]=new Stat(vit);
            stats[StatID.INT]=new Stat(intel);
            stats[StatID.RES]=new Stat(res);
            this.name=name;  
            this.element=element;
            this.exp=exp;
            this.level=level;
            isDead=false;
            //expRequiredToLevel=0;
        }
    //voids
    public void healToFull(){
        isDead=false;
        setStat(StatID.HP,getStat(StatID.MAXHP));
        setStat(StatID.MP,getStat(StatID.MAXMP));
    }
    //level controlling
    public void xpToLevel(){
        //doing this for now. subject to change later
        //triangular example
        expRequiredToLevel=0;
        for(int i=1;i<level+1;i++){
            for(int w=1;w<i+1;w++){
                expRequiredToLevel+=100*w;
            }
        }
    }
    public void giveExp(int expGained){
        exp+=expGained;
        checkForLevelUp();
    }
    public void checkForLevelUp(){
        while(exp>=expRequiredToLevel){
            level++;
            System.out.println("Level Up");
            for(Stat stat:stats){
                stat.levelUp();
            }
            xpToLevel();
        }
    }
    //hp controlling
    public void damage(int damage){
        reduceStat(StatID.HP,damage);
        if(getStat(StatID.HP)<=0){
            isDead=true;
            setStat(StatID.HP,0);
        }
    }
    public void heal(int heal){
        if(!isDead){
            increaseStat(StatID.HP,heal);
            if(getStat(StatID.HP)>getStat(StatID.MAXHP)){
                setStat(StatID.HP,getStat(StatID.MAXHP));
            }
        }
        else
            System.out.println("Heal Failed. Target is Dead");
    }
    public void raise(int heal){
        if(isDead){
            isDead=false;
            heal(heal);
        }else{
            System.out.println("Target is not Dead");
        }
            
    }
    //mp controlling
    public void regainMp(int mpHeal){
        if(!isDead){
            increaseStat(StatID.MP,mpHeal);
            if(getStat(StatID.MP)>getStat(StatID.MAXMP))
                setStat(StatID.MP,getStat(StatID.MAXMP));
            return;
        }
        System.out.println("Target is dead and cannot regain mana");
    }
    public void useMp(int mpUsed){
        if(mpUsed>getStat(StatID.MP)){
            System.out.println("Not enough mana");
            return;
        }
        reduceStat(StatID.MP,mpUsed);
    }
    //stat ajustments
    private void reduceStat(int StatID, int reduction){
        stats[StatID].reduceStat(reduction);
    }
    private void increaseStat(int StatID, int increase){
        stats[StatID].increaseStat(increase);
    }
    private void setStat(int StatID, int value){
        stats[StatID].setModifiedStat(value);
    }
    //prints
    public void printAllStats(){
        for(int i=0;i<stats.length;i++){
            System.out.printf("%s: %d", StatID.getStatName(i),getStat(i));
            
        }
        System.out.println();
    }
    public void printHpAndMp(){
        System.out.printf("HP: %d/%d\n", getStat(StatID.HP),getStat(StatID.MAXHP));
        System.out.printf("MP: %d/%d\n", getStat(StatID.MP),getStat(StatID.MAXMP));
        if(isDead)
            System.out.println("Dead");
        }
    //gets from entity
    public boolean getIsDead(){return isDead;}
    public String getName(){return name;}
    public int getElement(){return element;}
    public int getLevel(){return level;}
    public int getExp(){return exp;}
    public int getExpRequiredToLevel(){return expRequiredToLevel;}
    public int getExpUntilLevel(){return exp-expRequiredToLevel;}
    //gets from the nested stats. goes hand-in-hand with StatID.java
    public int getBaseStat(int StatID)      {return stats[StatID].getBaseStat();}
    public int getStatModifier(int StatID)  {return stats[StatID].getStatModifier();}
    public int getStat(int StatID)          {return stats[StatID].getModifiedStat();}
    public double getStatGrowth(int StatID) {return stats[StatID].getStatGrowth();}
    public double getGrowthOverflow(int StatID){return stats[StatID].getGrowthOverflow();}
    
    
    
    private class Stat{
        int baseStat;
        int modifiedStat;
        double statGrowth;
        double growthOverflow;
        /**
         * meant for when the entity will level up. usually party entities.
         * @param stat
         * @param growth 
         */
        Stat(int stat, double growth){
            baseStat=stat;
            statGrowth=growth;
            modifiedStat=baseStat;
            growthOverflow=0;
        }
        /**
         * meant for when the entity will never level up. usually enemy entities
         * @param stat 
         */
        Stat(int stat){
            baseStat=stat;
            statGrowth=0;
            modifiedStat=baseStat;
            growthOverflow=0;
        }
        //sets
        public void setBaseStat(int stat){
            baseStat=stat;
        }
        public void setModifiedStat(int stat){
            modifiedStat=stat;
        }
        //ajustments
        public void levelUp(){
            growthOverflow+=statGrowth;
            while(growthOverflow>1){
                baseStat++;
                modifiedStat++;
                growthOverflow--;
            }
        }
        public void increaseStat(int increase){
            modifiedStat+=increase;
        }
        public void reduceStat(int decrease){
            modifiedStat-=decrease;
        }
        private void reduceBaseStat(int decrease){
            baseStat-=decrease;
        }
        private void increaseBaseStat(int increase){
            baseStat+=increase;
        }
        //gets
        public int getBaseStat(){return baseStat;}
        public int getStatModifier(){return modifiedStat-baseStat;}
        public int getModifiedStat(){return modifiedStat;}
        public double getStatGrowth(){return statGrowth;}
        public double getGrowthOverflow(){return growthOverflow;}
    }
}
