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

import Background.BattleActions.BattleAction;
import Background.BattleActions.BattleActionLoader;
import Background.BattleActions.Spell;
import Background.DeBuffs.*;
import Background.Items.*;
import java.util.ArrayList;
import java.util.Random;


public class BattleEntity {
    
    private Stat[] stats;
    private String name;
    private int element,
                level,
                expRequiredToLevel,
                exp;
    private boolean isDead;
    private ArrayList<Effect> effects;
    private ArrayList<BattleAction> skills;
    private Equipment[] equipment;
    
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
            int dex, double dexgrowth, int vit, double vitgrowth, int intel, double intgrowth, int res, double resgrowth, 
            String name, int element, int level, int exp){
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
        xpToLevel();
        isDead = hp==0?true:false;
        effects = new ArrayList<>();
        skills = new ArrayList<>();
        equipment = new Equipment[4];
        for(int i=0;i<4;i++){
            equipment[i]=null;
        }
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
            int dex, double dexgrowth, int vit, double vitgrowth, int intel, double intgrowth, int res, double resgrowth, 
            String name, int element, int level, int exp){
        
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
        xpToLevel();
        isDead=false;
        effects = new ArrayList<>();
        skills = new ArrayList<>();
        equipment = new Equipment[4];
        for(int i=0;i<4;i++){
            equipment[i]=null;
        }
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
            effects = new ArrayList<>();
            skills = new ArrayList<>();
            equipment = new Equipment[4];
            //expRequiredToLevel=0;
        }
    public BattleEntity(String name,String hp, String mp, String str, String dex, String vit, String ine, String res, String element, String levelData, String isDead, String actions, String equipment){
        this.name = name;
        stats = new Stat[9];
        skills = new ArrayList<>();
        effects = new ArrayList<>();
        this.equipment = new Equipment[4];
        stats[StatID.HP]=new Stat(Integer.parseInt(hp.split("-")[0].split("/")[0]),Double.parseDouble(hp.split("-")[1]),Double.parseDouble(hp.split("-")[2]));
        stats[StatID.MAXHP]=new Stat(Integer.parseInt(hp.split("-")[0].split("/")[1]),Double.parseDouble(hp.split("-")[1]),Double.parseDouble(hp.split("-")[2]));
        stats[StatID.MP]=new Stat(Integer.parseInt(mp.split("-")[0].split("/")[0]),Double.parseDouble(mp.split("-")[1]),Double.parseDouble(mp.split("-")[2]));
        stats[StatID.MAXMP]=new Stat(Integer.parseInt(mp.split("-")[0].split("/")[1]),Double.parseDouble(mp.split("-")[1]),Double.parseDouble(mp.split("-")[2]));
        stats[StatID.STR]=new Stat(Integer.parseInt(str.split("-")[0]),Double.parseDouble(str.split("-")[1]),Double.parseDouble(str.split("-")[2]));
        stats[StatID.DEX]=new Stat(Integer.parseInt(dex.split("-")[0]),Double.parseDouble(dex.split("-")[1]),Double.parseDouble(dex.split("-")[2]));
        stats[StatID.VIT]=new Stat(Integer.parseInt(vit.split("-")[0]),Double.parseDouble(vit.split("-")[1]),Double.parseDouble(vit.split("-")[2]));
        stats[StatID.INT]=new Stat(Integer.parseInt(ine.split("-")[0]),Double.parseDouble(ine.split("-")[1]),Double.parseDouble(ine.split("-")[2]));
        stats[StatID.RES]=new Stat(Integer.parseInt(res.split("-")[0]),Double.parseDouble(res.split("-")[1]),Double.parseDouble(res.split("-")[2]));
        this.element = Integer.parseInt(element);
        level = Integer.parseInt(levelData.split("-")[0]);
        exp = Integer.parseInt(levelData.split("-")[1]);
        expRequiredToLevel = Integer.parseInt(levelData.split("-")[2]);
        this.isDead = isDead.equals("true")?true:false;
        for(int i=1;i<actions.split("-").length;i++){
            addSkill(BattleActionLoader.loadAction(Integer.parseInt(actions.split("-")[i])));
        }
        for(int i=0;i<4;i++){
            if(!equipment.split("-")[i+1].equals("null"))
                equip((Equipment)ItemLoader.loadItem(Integer.parseInt(equipment.split("-")[i+1]), 1),i);
        }
    }
    //effect controlling
    public void addEffect(Effect e){
        e.assign(this);
        effects.add(e);
    }
    public void removeEffect(Effect e){
        e.remove(this);
        effects.remove(e);
    }   
    public void removeEffect(int i){
        effects.get(i-1).remove(this);
        effects.remove(i-1);
    }
    public void removeAllEffects(){
        for(Effect e:effects){
            e.setDuration(0);
        }
        updateEffectsList();
    }
    public void tickAllEffects(){
        for(Effect effect:effects){
            effect.onTick(this);
        }
        updateEffectsList();
    }
    public void updateEffectsList(){
        for(int i=effects.size();i>0;i--){
            if(effects.get(i-1).getDuration()==0){
                removeEffect(i);
            }
        }
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
    public String checkForLevelUp(){
        int oldLevel = level;
        while(exp>=expRequiredToLevel){
            level++;
            //System.out.println("Level Up");
            //for(Stat stat:stats){
            //    stat.levelUp();
            //}
            for(int i=0;i<stats.length;i++){
                if(i!=StatID.HP&&i!=StatID.MP){
                    stats[i].levelUp();
                }
            }
            xpToLevel();
        }
        if(oldLevel!=level){
            return "Level up! "+name+" Reached level "+level;
        }else{
            return "";
        }
        
    }
    //equipment controlling
    public void equip(Equipment e, int equipSlot){
        if(e.getClass()==Weapon.class&&equipSlot==0){
            equipment[0]=(Equipment)ItemLoader.loadItem(e.getId(), 1);
            equipment[0].equip(this);
            e.reduceQuantity();
            heal(0);
            return;
        }
        if(e.getClass()==Armor.class&&equipSlot>0&&equipSlot<4){
            equipment[equipSlot]=(Equipment)ItemLoader.loadItem(e.getId(), 1);
            equipment[equipSlot].equip(this);
            e.reduceQuantity();
            heal(0);
            return;
        }
        System.out.println("Did not equip. Error occured");
    }
    public Item unEquip(int equipSlot){
        equipment[equipSlot].unEquip();
        Item unequipped = equipment[equipSlot];
        equipment[equipSlot]=null;
        heal(0);
        return unequipped;
    }
    //hp controlling
    public void healToFull(){
        isDead=false;
        setStat(StatID.HP,getStat(StatID.MAXHP));
        setStat(StatID.MP,getStat(StatID.MAXMP));
    }
    public void damage(int damage){
        reduceStat(StatID.HP,damage);
        if(getStat(StatID.HP)<=0){
            isDead=true;
            setStat(StatID.HP,0);
        }
        if(getStat(StatID.HP)>getStat(StatID.MAXHP)){
            setStat(StatID.HP,getStat(StatID.MAXHP));
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
    public void reduceStat(int StatID, int reduction){
        stats[StatID].reduceStat(reduction);
    }
    public void increaseStat(int StatID, int increase){
        stats[StatID].increaseStat(increase);
    }
    private void setStat(int StatID, int value){
        stats[StatID].setModifiedStat(value);
    }
    //skill controlling
    public void addSkill(BattleAction e){
        skills.add(e);
        e.setCaster(this);
    }
    //prints
    public void printAllEquipment(){
        for(Equipment e:equipment){
            try{
                System.out.println("-"+e.toString());
            }catch(NullPointerException r){
                System.out.println("----");
            }
        }
    }
    public void printAllEffectDurations(){
        if(effects.isEmpty()){
            System.out.println("No Effects");
            return;
        }
        for(int i=0;i<effects.size();i++){
            System.out.println(getEffectDuration(i));
            System.out.println(getEffectName(i));
        }
    }
    public void printAllBaseStats(){
        for(int i=0;i<stats.length;i++){
            System.out.printf("%s: %d", StatID.getStatName(i),getBaseStat(i));
            
        }
        System.out.println();
    }
    public void printAllStats(){
        for(int i=0;i<stats.length;i++){
            System.out.printf("-%s: %d-", StatID.getStatName(i),getStat(i));
        }
        System.out.println();
    }
    public void printHpAndMp(){
        System.out.println(name);
        System.out.printf("HP: %d/%d\n", getStat(StatID.HP),getStat(StatID.MAXHP));
        System.out.printf("MP: %d/%d\n", getStat(StatID.MP),getStat(StatID.MAXMP));
        if(isDead)
            System.out.println("Dead");
        }
    public void printAllEffects(){
        for(Effect effect:effects){
            System.out.print("--"+effect.toString());
        }
    }
    //gets from effects list
    public String getEffectName(int i){return effects.get(i).getName();}
    public int getEffectDuration(int i){return effects.get(i).getDuration();}
    public String getEffectSource(int i){return effects.get(i).getSource();}
    public Buff getBuffFromList(int i){
        if(effects.get(i).getClass()==Effect.effectLoader(Effect.BUFF, StatID.MAXHP, Buff.TENP, name).getClass()){
            return (Buff)effects.get(i);
        }
        return null;
    }
    public Debuff getDebuffFromList(int i){
        if(effects.get(i).getClass()==Effect.effectLoader(Effect.DEBUFF, StatID.MAXHP, Buff.TENP, name).getClass()){
            return (Debuff)effects.get(i);
        }
        return null;
    }
    //gets an effect from the list, and ensures it is a  buff/debuff/effect
    public int getBuffStatIncrease(int i){return getBuffFromList(i).getSavedIncrease();}
    public int getDebuffStatDecrease(int i){return getDebuffFromList(i).getSavedDecrease();}
    //gets from entity
    public boolean canCast(Spell s){
        return s.getCost()<=getStat(StatID.MP);
    }
    public boolean canCast(int i){
        return i<=getStat(StatID.MP);
    }
    public Weapon getWeapon(){
        //System.out.println(equipment[0].getDescription());
        return (Weapon)equipment[0];
    }
    public Equipment getEquipment(int slot){
        if(slot<4&&slot>0){
            //System.out.println("Armor Slot");
            return equipment[slot];
        }if(slot==0){
            //System.out.println("Weapon Slot");
            return getWeapon();
        }
        System.out.println("invalid slot");
        return null;
    }
    public boolean getIsDead(){return isDead;}
    public String getName(){return name;}
    public int getElement(){return element;}
    public int getLevel(){return level;}
    public int getExp(){return exp;}
    public int getTotalExpRequiredToLevel(){return expRequiredToLevel;}
    public int getExpUntilLevel(){return expRequiredToLevel-exp;}
    public BattleAction getSkill(int skillToGet){return skills.get(skillToGet);}
    public int getNumberOfSkills(){return skills.size();}
    //gets from the nested stats. goes hand-in-hand with StatID.java
    public int getBaseStat(int StatID)      {return stats[StatID].getBaseStat();}
    public int getStatModifier(int StatID)  {return stats[StatID].getStatModifier();}
    public int getStat(int StatID)          {return stats[StatID].getModifiedStat();}
    public double getStatGrowth(int StatID) {return stats[StatID].getStatGrowth();}
    public double getGrowthOverflow(int StatID){return stats[StatID].getGrowthOverflow();}
    public String toString(){
        return name;
    }
    
    public String[] saveData(){
        String[] saveData = new String[13];
        //0: name
        //1: hp/maxHP-growth-overflow
        //2: mp/maxMP-growth-overflow
        //3: str-growth-overflow
        //4: dex-growth-overflow
        //5: vit-growth-overflow
        //6: int-growth-overflow
        //7: res-growth-overflow
        //8: element
        //9: level-exp-exp2level
        //10: isDead
        //11: (for BattleAction b:list) id-id2...
        //12: weaponID-armor1ID-armor2ID-armor3ID
        saveData[0] = name;
        saveData[1] = String.format("%d/%d-%f-%f", getStat(StatID.HP),getStat(StatID.MAXHP),getStatGrowth(StatID.MAXHP),getGrowthOverflow(StatID.MAXHP));
        saveData[2] = String.format("%d/%d-%f-%f", getStat(StatID.MP),getStat(StatID.MAXMP),getStatGrowth(StatID.MAXMP),getGrowthOverflow(StatID.MAXMP));
        saveData[3] = String.format("%d-%f-%f", getStat(StatID.STR),getStatGrowth(StatID.STR),getGrowthOverflow(StatID.STR));
        saveData[4] = String.format("%d-%f-%f", getStat(StatID.DEX),getStatGrowth(StatID.DEX),getGrowthOverflow(StatID.DEX));
        saveData[5] = String.format("%d-%f-%f", getStat(StatID.VIT),getStatGrowth(StatID.VIT),getGrowthOverflow(StatID.VIT));
        saveData[6] = String.format("%d-%f-%f", getStat(StatID.INT),getStatGrowth(StatID.INT),getGrowthOverflow(StatID.INT));
        saveData[7] = String.format("%d-%f-%f", getStat(StatID.RES),getStatGrowth(StatID.RES),getGrowthOverflow(StatID.RES));
        saveData[8] = String.format("%d",element);
        saveData[9] = String.format("%d-%d-%d", level,exp,expRequiredToLevel);
        saveData[10] = String.format("%b",isDead);
        saveData[11] = "";
        for(BattleAction b:skills){
            saveData[11]+=String.format("-%d",b.getID());
        }
        saveData[12] = "";
        for(Equipment e:equipment){
            try{
                saveData[12]+="-"+e.getId();
            }catch(NullPointerException err){
                saveData[12]+="-null";
            }
        }
        return saveData;
    }
    
    private class Stat{
        private Random rand = new Random();
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
        /**
         * intended when loading a battle entity from a file
         * @param baseStat
         * @param modifiedStat
         * @param statGrowth
         * @param growthOverflow 
         */
        public Stat(int baseStat, double statGrowth, double growthOverflow) {
            this.baseStat = baseStat;
            this.modifiedStat = baseStat;
            this.statGrowth = statGrowth;
            this.growthOverflow = growthOverflow;
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
            growthOverflow+=rand.nextInt(2);
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
    
    public static void main(String[] args){
        EntityTester.main(args);
    }
}
