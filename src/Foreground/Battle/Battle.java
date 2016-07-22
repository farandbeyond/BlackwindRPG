/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Foreground.Battle;

import Background.BattleActions.BattleAction;
import Background.BattleActions.BattleActionLoader;
import Background.BattleEntity;
import Background.Items.Inventory;
import Background.Items.Item;
import Background.Party.Party;
import Background.StatID;
import Foreground.BlackwindTemp.Blackwind;
import Foreground.BlackwindTemp.Tile;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Connor
 */
public class Battle extends JPanel{
    private Party enemies, playerParty;
    private Inventory inventory;
    private displayBox[] enemiesDisplay, partyDisplay;
    private menuBox menu;
    private int menuPosition, enemyTargeted;
    private boolean confirmEvent, cancelEvent;
    private String assistText;
    private Joystick kb;
    private ArrayList<BattleAction> actionsLoaded;
    private ArrayList<BattleEntity> targetsLoaded;
    private boolean battleOver,turnOver,flee;
    private Random rand;
    
    Blackwind engine;
    public Battle(Party party, Inventory inv, Party enemyParty){
        //panel setup
        this.setLayout(null);
        this.setSize(640,480);
        this.setLocation(32, 32);
        this.setVisible(true);
        //instance variable setup
        rand = new Random();
        actionsLoaded = new ArrayList<>();
        targetsLoaded = new ArrayList<>();
        assistText = "";
        confirmEvent = false;
        cancelEvent = false;
        turnOver = false;
        flee = false;
        menuPosition = 0;
        enemyTargeted = 0;
        enemiesDisplay = new displayBox[3];
        partyDisplay = new displayBox[4];
        playerParty = party;
        enemies = enemyParty;
        inventory = inv;
        //panel setup
        menu = new menuBox(party.getMemberFromParty(0));
        this.add(menu);
        for(int i=0;i<3;i++){
            enemiesDisplay[i]=new displayBox(i*150+30,20,150,150,enemies.getMemberFromParty(i));
            this.add(enemiesDisplay[i]);
        }
        for(int i=0;i<4;i++){
            partyDisplay[i]=new displayBox(i*100,270,100,150,party.getMemberFromParty(i));
            this.add(partyDisplay[i]);
        }
        kb = new Joystick();
        battleOver = false;
        //frame.add(this);
    }
    
    public Battle(Party party, Inventory inv, Party enemyParty, Blackwind b){
        sendEngine(b);
        //panel setup
        this.setLayout(null);
        this.setSize(612,480);
        this.setLocation(0, 0);
        this.setVisible(true);
        //instance variable setup
        rand = new Random();
        actionsLoaded = new ArrayList<>();
        targetsLoaded = new ArrayList<>();
        assistText = "";
        confirmEvent = false;
        cancelEvent = false;
        turnOver = false;
        flee = false;
        menuPosition = 0;
        enemyTargeted = 0;
        enemiesDisplay = new displayBox[3];
        partyDisplay = new displayBox[4];
        playerParty = party;
        enemies = enemyParty;
        inventory = inv;
        //panel setup
        menu = new menuBox(party.getMemberFromParty(0));
        this.add(menu);
        for(int i=0;i<3;i++){
            enemiesDisplay[i]=new displayBox(i*150+30,20,150,150,enemies.getMemberFromParty(i));
            this.add(enemiesDisplay[i]);
        }
        for(int i=0;i<4;i++){
            partyDisplay[i]=new displayBox(i*100,270,100,150,party.getMemberFromParty(i));
            this.add(partyDisplay[i]);
        }
        kb = new Joystick();
        battleOver = false;
        //frame.add(this);
    }
    public void sendEngine(Blackwind b){
        engine = b;
    }
    public void repaint(){
        try{
            engine.repaint();
        }catch(NullPointerException e){
            System.out.println("engine currently null");
        }
    }
    //battle loop
    public void loop(){
        try{
            while(!battleOver){
                resetEvents();
                prepareActions();
                getEnemyActions();
                sortByDex();
                executeAllActions();
                checkForBattleOver();
                tickBuffs();
            }
            endBattle();
        }catch(InterruptedException e){
            System.out.println("Error Occured");
        }
        
    }
    
    //prepareActions
    public void prepareActions() throws InterruptedException{
        int actingMember = 0;
        while(actingMember<playerParty.getCurrentPartySize()){
            //System.out.println("--");
            //System.out.println(actingMember);
            //for(BattleAction action:actionsLoaded){
            //    System.out.print(action.toString());
            //    System.out.println(actionsLoaded.indexOf(action));
            //}
            //System.out.println("--");
            if(playerParty.getMemberFromParty(actingMember).getIsDead()){
                if(cancelEvent){
                    actingMember--;
                    actionsLoaded.remove(actingMember);
                    targetsLoaded.remove(actingMember);
                }else{
                    actionsLoaded.add(BattleActionLoader.noAction(playerParty.getMemberFromParty(actingMember)));
                    targetsLoaded.add(playerParty.getMemberFromParty(actingMember));
                    actingMember++;
                }
                System.out.println(actingMember);
            }else{
                resetEvents();
                Thread.sleep(10);
                setAssistText(String.format("What will %s do?",playerParty.getMemberFromParty(actingMember).getName()));
                turnOver = false;
                if(!partyDisplay[actingMember].isActing())
                    partyDisplay[actingMember].toggleActing();
                partyDisplay[actingMember].updateColor();
                repaint();
                confirmActingMember(actingMember);
                confirmMenuPosition(menu.getMaxSelectorPosition());
                menu.updateSelectorPosition(menuPosition);
                if(confirmEvent){
                    menu.setCurrent(playerParty.getMemberFromParty(actingMember));
                    resetEvents();
                    int save = menuPosition;
                    menuPosition = 0;
                    switch(save){
                        case 0:selectAttackTarget(actingMember);break;
                        case 1:selectSkill(actingMember);menu.loadMainMenuOptions();break;
                        case 2:selectItem(actingMember);menu.loadMainMenuOptions();break;
                        case 3:flee();break;
                    }
                    if(!cancelEvent)
                        actingMember++;
                    menuPosition = save;
                    resetEvents();
                }
                
                if(cancelEvent){
                    partyDisplay[actingMember].toggleActing();
                    if(actingMember!=0){
                        actingMember--;
                        //if(!playerParty.getMemberFromParty(actingMember).getIsDead()){
                            actionsLoaded.remove(actingMember);
                            targetsLoaded.remove(actingMember);
                        //}
                    }
                    partyDisplay[actingMember].toggleActing();
                }
            }
        }
    }
    public void selectAttackTarget(int memberActing){
        resetEvents();
        while(!cancelEvent&&!turnOver){
            setAssistText(String.format("Who will %s attack?",playerParty.getMemberFromParty(memberActing).getName()));
            repaint();
            confirmEnemyTarget(enemies.getCurrentPartySize()-1);
            confirmTargetedEnemy();
            if(confirmEvent){
                //System.out.println("Here");
                actionsLoaded.add(BattleActionLoader.loadAttack(playerParty.getMemberFromParty(memberActing)));
                targetsLoaded.add(enemies.getMemberFromParty(enemyTargeted));
                turnOver = true;
                enemiesDisplay[enemyTargeted].toggleTargeted();
                return;
            }
            if(cancelEvent){
                //resetEvents();
                enemiesDisplay[enemyTargeted].toggleTargeted();
                return;
            }
        }
    }
    public void confirmTargetedEnemy(){
        for(int i=0;i<enemies.getCurrentPartySize();i++){
            if(enemiesDisplay[i].isTargeted()&&i!=enemyTargeted){
                enemiesDisplay[i].toggleTargeted();
                enemiesDisplay[i].updateColor();
            }
            if(!enemiesDisplay[i].isTargeted()&&i==enemyTargeted){
                enemiesDisplay[i].toggleTargeted();
                enemiesDisplay[i].updateColor();
            }
        }
    }
    public void confirmTargetedAlly(){
        for(int i=0;i<playerParty.getCurrentPartySize();i++){
            if(partyDisplay[i].isTargeted()&&i!=enemyTargeted){
                partyDisplay[i].toggleTargeted();
                partyDisplay[i].updateColor();
            }
            if(!partyDisplay[i].isTargeted()&&i==enemyTargeted){
                partyDisplay[i].toggleTargeted();
                partyDisplay[i].updateColor();
            }
        }
    }
    public void confirmActingMember(int acting){
        for(int i=0;i<playerParty.getCurrentPartySize();i++){
            if(partyDisplay[i].isActing()&&i!=acting){
                partyDisplay[i].toggleActing();
                partyDisplay[i].updateColor();
            }
            if(!partyDisplay[i].isActing()&&i==acting){
                partyDisplay[i].toggleActing();
                partyDisplay[i].updateColor();
            }
        }
    }
    public void selectSkill(int memberActing){
        resetEvents();
        menu.switchMenu(menuBox.MAIN, menuBox.SKILLS);
        menu.loadSkills();
        while(!cancelEvent&&!turnOver){
            setAssistText(String.format("What will %s do?",playerParty.getMemberFromParty(memberActing).getName()));
            repaint();
            confirmMenuPosition(menu.getMaxSelectorPosition());
            menuPosition = menu.updateSkillsOffsetSelectorPosition(menuPosition);
            if(confirmEvent){
                BattleAction selectedAction = menu.getSkillAtPosition();
                if(selectedAction.targetsAllies()){
                    selectAllyTarget(selectedAction); 
                }else{
                    selectEnemyTarget(selectedAction);
                }
            }
        }
    }
    public void selectItem(int memberActing){
        resetEvents();
        menu.switchMenu(menuBox.MAIN, menuBox.ITEMS);
        while(!cancelEvent&&!turnOver){
            setAssistText(String.format("What will %s use?",playerParty.getMemberFromParty(memberActing).getName()));
            menu.loadItems();
            repaint();
            confirmMenuPosition(menu.getMaxSelectorPosition());
            menuPosition = menu.updateInvOffsetSelectorPosition(menuPosition);
            if(confirmEvent){
                Item selectedItem = menu.getItemAtPosition();
                if(selectedItem.getId()>=0&&selectedItem.getId()<=99){
                    selectAllyTarget(memberActing,selectedItem);
                }else{
                    selectEnemyTarget(memberActing,selectedItem);
                }
            }
        }
    }
    public void selectAllyTarget(BattleAction selectedAction){
        resetEvents();
        while(!cancelEvent&&!turnOver){
            setAssistText(String.format("Who will be targeted by %s's %s",selectedAction.getCaster().getName(),selectedAction.getName()));
            repaint();
            confirmEnemyTarget(playerParty.getCurrentPartySize()-1);
            confirmTargetedAlly();
            if(confirmEvent){
                //System.out.println("Here");
                actionsLoaded.add(selectedAction);
                targetsLoaded.add(playerParty.getMemberFromParty(enemyTargeted));
                turnOver = true;
                partyDisplay[enemyTargeted].toggleTargeted();
                return;
            }
            if(cancelEvent){
                //resetEvents();
                partyDisplay[enemyTargeted].toggleTargeted();
                return;
            }
        }
    }
    public void selectAllyTarget(int memberActing, Item selectedItem){
        resetEvents();
        while(!cancelEvent&&!turnOver){
            setAssistText(String.format("Who will be targeted by %s's %s",playerParty.getMemberFromParty(memberActing).getName(),selectedItem.getName()));
            repaint();
            confirmEnemyTarget(playerParty.getCurrentPartySize()-1);
            confirmTargetedAlly();
            if(confirmEvent){
                //System.out.println("Here");
                actionsLoaded.add(BattleActionLoader.loadItemAction(playerParty.getMemberFromParty(memberActing), selectedItem));
                targetsLoaded.add(playerParty.getMemberFromParty(enemyTargeted));
                turnOver = true;
                partyDisplay[enemyTargeted].toggleTargeted();
                return;
            }
            if(cancelEvent){
                //resetEvents();
                partyDisplay[enemyTargeted].toggleTargeted();
                return;
            }
        }
    }
    public void selectEnemyTarget(BattleAction selectedAction){
        resetEvents();
        while(!cancelEvent&&!turnOver){
            setAssistText(String.format("Who will be targeted by %s's %s",selectedAction.getCaster().getName(),selectedAction.getName()));
            repaint();
            confirmEnemyTarget(enemies.getCurrentPartySize()-1);
            confirmTargetedEnemy();
            if(confirmEvent){
                //System.out.println("Here");
                actionsLoaded.add(selectedAction);
                targetsLoaded.add(enemies.getMemberFromParty(enemyTargeted));
                turnOver = true;
                enemiesDisplay[enemyTargeted].toggleTargeted();
                return;
            }
            if(cancelEvent){
                //resetEvents();
                enemiesDisplay[enemyTargeted].toggleTargeted();
                return;
            }
        }
    }
    public void selectEnemyTarget(int memberActing, Item selectedItem){
        resetEvents();
        while(!cancelEvent&&!turnOver){
            setAssistText(String.format("Who will be targeted by %s's %s",playerParty.getMemberFromParty(memberActing).getName(),selectedItem.getName()));
            repaint();
            confirmEnemyTarget(enemies.getCurrentPartySize()-1);
            confirmTargetedEnemy();
            if(confirmEvent){
                //System.out.println("Here");
                actionsLoaded.add(BattleActionLoader.loadItemAction(playerParty.getMemberFromParty(memberActing), selectedItem));
                targetsLoaded.add(enemies.getMemberFromParty(enemyTargeted));
                turnOver = true;
                enemiesDisplay[enemyTargeted].toggleTargeted();
                return;
            }
            if(cancelEvent){
                //resetEvents();
                enemiesDisplay[enemyTargeted].toggleTargeted();
                return;
            }
        }
    }
    //getEnemyAcions
    public void getEnemyActions(){
        for(int i=0;i<enemies.getCurrentPartySize();i++){
            if(enemies.getMemberFromParty(i).getIsDead()){
                //do nothing: Dead enemies do not act
            }else{
                boolean actionLoaded;
                do{
                    actionLoaded = false;
                    BattleAction aTL =  enemies.getMemberFromParty(i).getSkill(rand.nextInt(enemies.getMemberFromParty(i).getNumberOfSkills()));
                    if(aTL.targetsAllies()){
                        BattleEntity target = enemies.getMemberFromParty(rand.nextInt(enemies.getCurrentPartySize()));
                        if(!target.getIsDead()){
                            targetsLoaded.add(target);
                            actionsLoaded.add(aTL);
                            actionLoaded = true;

                        }
                    }else{
                        BattleEntity target = playerParty.getMemberFromParty(rand.nextInt(playerParty.getCurrentPartySize()));
                        if(!target.getIsDead()){
                            targetsLoaded.add(playerParty.getMemberFromParty(rand.nextInt(playerParty.getCurrentPartySize())));
                            actionsLoaded.add(aTL);
                            actionLoaded = true;
                        }
                    }
                }while(!actionLoaded);
            }
        }
    }
    //sort by dexterity
    public void sortByDex(){
        for(int i=0;i<actionsLoaded.size();i++){
            BattleAction fastest = null;
            int position = 0;
            for(int p=actionsLoaded.size()-1-i;p>=0;p--){
                if(p==actionsLoaded.size()-1-i){
                    position = p;
                    fastest = actionsLoaded.get(position);
                    
                }
                if(actionsLoaded.get(i).getCaster().getStat(StatID.DEX)>fastest.getCasterStat(StatID.DEX)){
                    position =p;
                    fastest = actionsLoaded.get(position);
                    
                }              
            }
            BattleEntity target = targetsLoaded.get(position);
            actionsLoaded.remove(position);
            targetsLoaded.remove(position);
            actionsLoaded.add(fastest);
            targetsLoaded.add(target);
        }
        //for(BattleAction e:actionsLoaded){
        //    System.out.printf("%s has the %s action prepared\n", e.getCaster(),e.getName());
        //}
        //System.out.println("");
    }
    //executeAllActions
    public void executeAllActions() throws InterruptedException{
        for(int i=0;i<actionsLoaded.size();i++){
            if(actionsLoaded.get(i).getCaster().getIsDead())
                setAssistText(String.format("%s is dead and cannot act\n",actionsLoaded.get(i).getCaster().getName()));
            else if(actionsLoaded.get(i).getCaster().canCast(actionsLoaded.get(i).getCost()))
                setAssistText(actionsLoaded.get(i).execute(targetsLoaded.get(i)));
            else
                setAssistText(String.format("%s tried to act, but couldnt cast %s\n",actionsLoaded.get(i).getCaster().getName(),actionsLoaded.get(i).getName()));
            repaint();
            Thread.sleep(1000);
        }
        for(int i=actionsLoaded.size()-1;i>=0;i--){
            actionsLoaded.remove(i);
            targetsLoaded.remove(i);
        }
        for(BattleAction b:actionsLoaded){
            System.out.println(b.toString());
        }
        repaint();
    }
    //check for battle over
    public void checkForBattleOver(){
        boolean enemiesDead = true;
        boolean partyDead = true;
        for(int i=0;i<enemies.getCurrentPartySize();i++){
            if(!enemies.getMemberFromParty(i).getIsDead()){
                enemiesDead = false;
            }
        }
        for(int i=0;i<playerParty.getCurrentPartySize();i++){
            if(!playerParty.getMemberFromParty(i).getIsDead()){
                partyDead = false;
            }
        }
        if(partyDead || enemiesDead){
            battleOver = true;
        }
    }
    //buffs
    public void tickBuffs(){
        if(battleOver){
            for(int i=0;i<playerParty.getCurrentPartySize();i++){
                playerParty.getMemberFromParty(i).removeAllEffects();
            }
            for(int i=0;i<enemies.getCurrentPartySize();i++){
                enemies.getMemberFromParty(i).removeAllEffects();
            }
        }else{
            for(int i=0;i<playerParty.getCurrentPartySize();i++){
                playerParty.getMemberFromParty(i).tickAllEffects();
                //player.getMemberFromParty(i).printAllEffects();
                //player.printMembersStats(i);
            }
            for(int i=0;i<enemies.getCurrentPartySize();i++){
                enemies.getMemberFromParty(i).tickAllEffects();
                //enemies.getMemberFromParty(i).printAllEffects();
            }
        }
    }
    //menu controlling
    public void confirmMenuPosition(int maxPosition){
        if(menuPosition<0){
            menuPosition = maxPosition;
        }
        if(menuPosition>maxPosition){
            menuPosition = 0;
        }
    }
    public void confirmEnemyTarget(int maxPosition){
        if(enemyTargeted<0){
            enemyTargeted = maxPosition;
        }
        if(enemyTargeted>maxPosition){
            enemyTargeted = 0;
        }
    }
    public void resetEvents(){
        confirmEvent = false;
        cancelEvent = false;
    }
    //assisttext ajusting
    public void setAssistText(String text){
        assistText = text;
    }
    //end battle
    public void flee(){
        flee = true;
        battleOver = true;
    }
    public void endBattle() throws InterruptedException{
        if(!flee){
            int xpGained = 0;
            for(int i=0;i<enemies.getCurrentPartySize();i++){
                xpGained+=enemies.getMemberFromParty(i).getExp();
            }
            for(int i=0;i<playerParty.getCurrentPartySize();i++){
                if(!playerParty.getMemberFromParty(i).getIsDead()){
                    playerParty.getMemberFromParty(i).giveExp(xpGained);
                    setAssistText(String.format("%s gained %d experience", playerParty.getMemberFromParty(i).getName(),xpGained));
                    repaint();
                    Thread.sleep(500);
                    String levelUp = playerParty.getMemberFromParty(i).checkForLevelUp();
                    if(!levelUp.equals("")){
                        setAssistText(levelUp);
                        repaint();
                        Thread.sleep(1500);
                    }
                }
            }
        }else{
            setAssistText("Fled battle");
            Thread.sleep(1500);
        }
        Blackwind.gameState = Blackwind.MAP;
    }
    //paint
    public void paint(Graphics g){
        g.setColor(Color.yellow);
        g.fillRect(0+Tile.tileSize, 0+Tile.tileSize, 612, 480);
        g.setFont(new Font("Courier New", Font.BOLD, 15));
        for(int i=0;i<3;i++){
            enemiesDisplay[i].paint(g);
        }
        for(int i=0;i<4;i++){
            partyDisplay[i].paint(g);
        }
        menu.paint(g);
        g.setFont(new Font("Courier New", Font.BOLD, 15));
        if(assistText.length()<40){
            g.drawString(assistText, 10+Tile.tileSize, 190+Tile.tileSize);
        }else{
            int split = 0;
            for(int i=19;i<41;i++){
                if(assistText.charAt(i)==' '){
                    split = i;
                    break;
                }
                    
            }
            g.drawString(assistText.substring(0, split), 10+Tile.tileSize, 190+Tile.tileSize);
            g.drawString(assistText.substring(split+1), 10+Tile.tileSize, 220+Tile.tileSize);
        }
    }
    //subclass jpanels
    private class displayBox extends JPanel{
        private int myX, myY, myWidth, myHeight;
        private BattleEntity displayed;
        private boolean targeted, acting;
        private Color currentColor;
        displayBox(int x, int y, int width, int height, BattleEntity displayTarget){
            myX = x;
            myY = y;
            myWidth = width;
            myHeight = height;
            acting = false;
            targeted = false;
            this.setSize(x,y);
            this.setLocation(width, height);
            this.setVisible(true);
            displayed = displayTarget;
            updateColor();
            
        }
        public void updateColor(){
            try{
                if(displayed.getIsDead()){
                    currentColor = Color.red;
                }else if(acting){
                    currentColor = Color.green;
                }else{
                    currentColor = Color.cyan;
                }
                if(targeted){
                    currentColor = Color.orange;
                    return;
                }
            }catch(NullPointerException e){
                currentColor = Color.gray;
            }
        }
        public void toggleTargeted(){
            if(targeted){
                targeted = false;
                return;
            }
            targeted = true;
        }
        public void updateDisplay(BattleEntity e){displayed = e;}
        public void toggleActing(){
            if(acting){
                acting = false;
                return;
            }
            acting = true;
        }
        public boolean isTargeted(){return targeted;}
        public boolean isActing(){return acting;}
        @Override
        public int getX(){return myX;}
        @Override
        public int getY(){return myY;}
        @Override
        public int getWidth(){return myWidth;}
        @Override
        public int getHeight(){return myHeight;}
        public BattleEntity getDisplayed(){return displayed;}
        @Override
        public void paint(Graphics g){
            updateColor();
            try{
                g.setColor(currentColor);
                g.fillRect(myX+Tile.tileSize, myY+Tile.tileSize, myWidth, myHeight);
            }catch(NullPointerException e){
                g.setColor(Color.gray);
                g.fillRect(myX+Tile.tileSize, myY+Tile.tileSize, myWidth, myHeight);
            }
            g.setColor(Color.black);
            g.drawRect(myX+Tile.tileSize, myY+Tile.tileSize, myWidth, myHeight);
            g.setFont(new Font("Courier New", Font.BOLD, 15));
            try{
               g.drawString(displayed.getName(),myX+10+Tile.tileSize ,myY+40+Tile.tileSize);
               g.drawString(String.format("%d/%d hp", displayed.getStat(StatID.HP),displayed.getStat(StatID.MAXHP)),myX+10+Tile.tileSize ,myY+70+Tile.tileSize);
               g.drawString(String.format("%d/%d mp", displayed.getStat(StatID.MP),displayed.getStat(StatID.MAXMP)),myX+10+Tile.tileSize ,myY+100+Tile.tileSize);
            }catch(NullPointerException e){
               g.drawString("----",     myX+10+Tile.tileSize ,myY+40+Tile.tileSize);
               g.drawString("--/-- hp", myX+10+Tile.tileSize ,myY+70+Tile.tileSize);
               g.drawString("--/-- hp", myX+10+Tile.tileSize ,myY+100+Tile.tileSize);
            }
        }
    }
    private class menuBox extends JPanel{
        public static final int MAIN=0, SKILLS=1,ITEMS=2;
        private boolean[] menuLoaded;
        private int myX, myY, myWidth, myHeight;
        private int invOffset,invMaxOffset, skillsOffset,skillsMaxOffset;
        private int maxMenuPosition;
        private int selectorPosition;
        private BattleEntity current;
        private String[] menuOptions;
        menuBox(BattleEntity current){
            selectorPosition = 0;
            invOffset = 0;
            skillsOffset = 0;
            menuLoaded = new boolean[3];
            menuLoaded[MAIN]=true;
            this.current = current;
            menuOptions = new String[8];
            loadMenus();
            setSkillsMaxOffset();
            setInvMaxOffset();
            this.setSize(myWidth,myHeight);
            this.setLocation(myX, myY);
            this.setVisible(true);
        }
        public void loadMenus(){
            myX = 400;
            myWidth = 201;
            if(menuLoaded[MAIN]){
                myY = 180;
                myHeight = 260;
                loadMainMenuOptions();
            }else if(menuLoaded[SKILLS]){
                myY = 180;
                myHeight = 260;
                loadSkills();
            }
            else if(menuLoaded[ITEMS]){
                myY = 180;
                myHeight = 260;
                loadItems();
            }
        }
        //menu option sets
        public void loadMainMenuOptions(){
            menuOptions[0]="Attack";
            menuOptions[1]="Skills";
            menuOptions[2]="Items";
            menuOptions[3]="Flee";
            menuOptions[4]="";
            menuOptions[5]="";
            menuOptions[6]="";
            menuOptions[7]="";
            maxMenuPosition = 3;
        }
        public void loadItems(){
            for(int i=0;i<8;i++){
                try{
                    menuOptions[i] = "x"+inventory.getItem(i+invOffset).getQuantity()+" "+inventory.getItem(i+invOffset).getName();
                }catch(IndexOutOfBoundsException e){
                    menuOptions[i]="x-- ----";
                }
            }
            maxMenuPosition = 7;
        }
        public void loadSkills(){
            for(int i=0;i<8;i++){
                try{
                    menuOptions[i] = current.getSkill(i+skillsOffset).getCost()+"mp "+current.getSkill(i+skillsOffset).getName();
                }catch(IndexOutOfBoundsException e){
                    menuOptions[i] = "--mp -----";
                }
            }
            maxMenuPosition = 7;
        }
        public void loadMainMenu(){
            menuLoaded[MAIN]=true;
            menuLoaded[SKILLS]=false;
            menuLoaded[ITEMS]=false;
            loadMenus();
        }
        //menu navigating
        public void updateSelectorPosition(int newPos){selectorPosition = newPos;}
        //offsetNavigation
        public void setInvMaxOffset(){
            invMaxOffset=0;
            while(true){
                try{
                    inventory.getItem(invMaxOffset+8).toString();
                    invMaxOffset++;
                }catch(IndexOutOfBoundsException e){
                    //System.out.println(String.format("max offset is %d, from an inventory size %d, containing %d items", invMaxOffset, inventory.getInvSize(), inventory.getNumberOfItemsInInventory()));
                    return;
                }
            }
        }    
        public void setSkillsMaxOffset(){
            skillsMaxOffset=0;
            while(true){
                try{
                    current.getSkill(skillsMaxOffset+8).toString();
                    skillsMaxOffset++;
                }catch(IndexOutOfBoundsException|NullPointerException e){
                    //System.out.println(String.format("Max offset is %d, looking at the entity %s with %d skills",skillsMaxOffset,current.getName(),current.getNumberOfSkills()));
                    return;
                }
            }
        }
        public int updateInvOffsetSelectorPosition(int newPos){
        //if you scroll down far enough and there are more options to load, scroll the inventory and add to the offset
        try{
            if(newPos!=selectorPosition){
                if(newPos==7){
                    invOffset = invMaxOffset;
                }
                if(newPos==0){
                    invOffset = 0;
                }
                //-1 is intened to throw null error if not exist
                if(newPos>=5&&inventory.getItem(10+invOffset)!=null){
                    invOffset++;
                    //System.out.println(currOffset+"vv"+newPos);
                    return newPos-1;
                }
                if(newPos<=2&&inventory.getItem(invOffset-1)!=null){
                    invOffset--;
                    //System.out.println(currOffset+"^^"+newPos);
                    return newPos+1;
                }

                selectorPosition = newPos;
            }
            //System.out.println(currOffset+">>"+newPos);
            return newPos;
        }catch(IndexOutOfBoundsException e){
            //System.out.println("Handling an error");

            //System.out.println(currOffset+">>"+newPos);
            selectorPosition = newPos;
            return newPos;
        }
    }
        public int updateSkillsOffsetSelectorPosition(int newPos){
        //if you scroll down far enough and there are more options to load, scroll the inventory and add to the offset
        try{
            if(newPos!=selectorPosition){
                if(newPos==7){
                    skillsOffset = skillsMaxOffset;
                }
                if(newPos==0){
                    skillsOffset = 0;
                }
                //-1 is intened to throw null error if not exist
                if(newPos>=5&&current.getSkill(10+skillsOffset)!=null){
                    skillsOffset++;
                    //System.out.println(currOffset+"vv"+newPos);
                    return newPos-1;
                }
                if(newPos<=2&&current.getSkill(skillsOffset-1)!=null){
                    skillsOffset--;
                    //System.out.println(currOffset+"^^"+newPos);
                    return newPos+1;
                }

                selectorPosition = newPos;
            }
            //System.out.println(currOffset+">>"+newPos);
            return newPos;
        }catch(IndexOutOfBoundsException e){
            //System.out.println("Handling an error");

            //System.out.println(currOffset+">>"+newPos);
            selectorPosition = newPos;
            return newPos;
        }
    }
        public Item getItemAtPosition(){return inventory.getItem(menuPosition+invOffset);}
        public BattleAction getSkillAtPosition(){return current.getSkill(menuPosition+skillsOffset);}
        //sets
        public void setCurrent(BattleEntity current){this.current=current;setSkillsMaxOffset();}
        public void switchMenu(int currentMenu, int newMenu){
            menuLoaded[currentMenu]=false;
            menuLoaded[newMenu]=true;
            loadMenus();
        }
        //gets
        public int getMaxSelectorPosition(){return maxMenuPosition;}
        public int getX(){return myX;}
        public int getY(){return myY;}
        public int getWidth(){return myWidth;}
        public int getHeight(){return myHeight;}
        public int getInvOffset(){return invOffset;}
        public int getSkillsOffset(){return skillsOffset;}
        public int getInvMaxOffset(){return invMaxOffset;}
        public int getSkillsMaxOffset(){return skillsMaxOffset;}
        public int getSelectorPosition(){return selectorPosition;}
        //paint
        public void paint(Graphics g){
            g.setColor(Color.GREEN);
            g.fillRect(myX+Tile.tileSize, myY+Tile.tileSize, myWidth, myHeight);
            g.setColor(Color.black);
            g.drawRect(myX+Tile.tileSize, myY+Tile.tileSize, myWidth, myHeight);
            g.setFont(new Font("Courier New", Font.BOLD, 16));
            for(int i=0;i<8;i++){
                g.drawString(menuOptions[i], myX+20+Tile.tileSize, myY+Tile.tileSize+20+30*i);
            }
            g.drawString(">", myX+Tile.tileSize, myY+Tile.tileSize+20+30*selectorPosition);
        }
        

    }
    //key event controlling
    public class Joystick implements KeyListener{
        @Override
        public void keyTyped(KeyEvent ke) {
        }
        @Override
        public void keyPressed(KeyEvent ke) {
            switch(ke.getExtendedKeyCode()){
                case KeyEvent.VK_O:confirmEvent();break;
                case KeyEvent.VK_P:cancelEvent();break;
                case KeyEvent.VK_W:upEvent();break;
                case KeyEvent.VK_A:leftEvent();break;
                case KeyEvent.VK_S:downEvent();break;
                case KeyEvent.VK_D:rightEvent();break;
                case KeyEvent.VK_ENTER:menuEvent();break;
            }
        }
        @Override
        public void keyReleased(KeyEvent ke) {
        }
    }
    public void confirmEvent(){confirmEvent=true;}
    public void cancelEvent(){cancelEvent=true;}
    public void upEvent(){menuPosition--;}
    public void downEvent(){menuPosition++;}
    public void leftEvent(){enemyTargeted--;}
    public void rightEvent(){enemyTargeted++;}
    public void menuEvent(){}
    public Joystick getKL(){return kb;}
    //public void menuEvent(){System.exit(0);}
    public static void main(String[] args){
        Blackwind.main(args);
    }
}
