/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Background.Party;

import Background.BattleEntity;
import Background.StatID;

/**
 *
 * @author Connor
 */
public class Party {
    private BattleEntity[] party;
    private int maxPartySize;
    public Party(int partySize){
        party = new BattleEntity[partySize];
        this.maxPartySize=partySize;
    }
    public Party(BattleEntity[] party){
        this.party=party;
        maxPartySize=party.length;
    }
    
    //party Ajustments
    public void addPartyMember(BattleEntity member){
        for(int i=0;i<maxPartySize;i++){
            if(party[i]==null){
                party[i]=member;
                System.out.printf("%s was added to party slot %d\n",member.getName(),i);
                return;
            }
        }
        System.out.println("Cannot add member to party");
    }
    public void removePartyMember(int member){
        if(party[member]!=null){
            System.out.println(party[member].getName()+" was removed from the party");
            party[member]=null;
            return;
        }
        System.out.println("Member does not exist");
    }
    public void swapPartyMembers(int memberToSwap, int memberToSwapWith){
        BattleEntity tempStorage;
        if(party[memberToSwap]!=null && party[memberToSwapWith]!=null){
             tempStorage = getMemberFromParty(memberToSwap);
             setPartyMember(getMemberFromParty(memberToSwapWith),memberToSwap);
             setPartyMember(tempStorage,memberToSwapWith);
             return;
        }
        System.out.println("One of the members does not exist");
    }
    //voids concerning BattleEntities
    public void damagePartyMember(int member, int damage){
        getMemberFromParty(member).damage(damage);
    }
    public void healPartyMember(int member, int heal){
        getMemberFromParty(member).heal(heal);
    }
    public void givePartyMemberExp(int member, int expGained){
        getMemberFromParty(member).giveExp(expGained);
        //getMemberFromParty(member).checkForLevelUp();
    }
    public void restAllMembers(){
        try{
            for(BattleEntity entity:party)
                entity.healToFull();
        }catch(NullPointerException e){
            // do nothing
        }
    }
    public void usePartyMemberMp(int member,int mpUsed){
        getMemberFromParty(member).useMp(mpUsed);
    }
    public void raisePartyMember(int member, int healValue){
        getMemberFromParty(member).raise(healValue);
    }
    //prints
    public void printAllMembersHpAndMp(){
        
        for(int i=0;i<maxPartySize;i++){
            try{
                System.out.println(party[i].getName());
                party[i].printHpAndMp();
            }catch(NullPointerException e){
                System.out.println("---");
                System.out.println("HP: --/--");
                System.out.println("MP: --/--");
            }
        }
    }
    public void printAllMembersStats(){
        for(int i=0;i<maxPartySize;i++){
            party[i].printAllStats();
        }
    }
    public void printMembersStats(int member){
        party[member].printAllStats();
    }
    public void printMembersHpAndMp(int member){
        party[member].printHpAndMp();
    }
    //gets
    public BattleEntity getMemberFromParty(int member){
        return party[member];
    }
    public int getPartyMembersStat(int member, int StatID){
        return getMemberFromParty(member).getStat(StatID);
    }
    public String getHpMaxHp(int member){
        return String.format("%d/%d", getMemberFromParty(member).getStat(StatID.HP),getMemberFromParty(member).getStat(StatID.MAXHP));
    }
    public String getMpMaxMp(int member){
        return String.format("%d/%d", getMemberFromParty(member).getStat(StatID.MP),getMemberFromParty(member).getStat(StatID.MAXMP));
    }
    public int getMaxPartySize(){return maxPartySize;}
    public int getCurrentPartySize(){
        int i=0;
        for(BattleEntity member:party){
            try{
                member.getName();
                i++;
            }catch(NullPointerException e){
                return i;
            }
        }
        return i;
    }
    //sets
    private void setPartyMember(BattleEntity entity, int position){
        party[position]=entity;
    }
}
