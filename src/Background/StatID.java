/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Background;

/**
 *
 * @author Connor
 */
public class StatID {
    
    public static final int
            HP=0,
            MAXHP=1,
            MP=2,
            MAXMP=3,
            STR=4,
            DEX=5,
            VIT=6,
            INT=7,
            RES=8;
    public static String getStatName(int statID){
        switch(statID){
            case HP:return "Hp";
            case MAXHP:return "Max Hp";
            case MP:return "Mp";
            case MAXMP:return "Max Mp";
            case STR:return "Str";
            case DEX:return "Dex";
            case VIT:return "Vit";
            case INT:return "Int";
            case RES:return "Res";
        }
        return "";
    }
}
