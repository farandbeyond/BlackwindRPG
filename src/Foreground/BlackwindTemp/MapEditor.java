/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Foreground.BlackwindTemp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 *
 * @author Connor
 */
public class MapEditor extends JPanel implements ActionListener, MouseListener{
    	private static MapEditor editor;
	private static JFrame window;
	//MENU BAR
	private static JMenuBar menuBar;
	private static JMenu file;
	private static JMenuItem file_newmap;
	private static JMenuItem file_savemap;
	private static JMenuItem file_loadmap;
	private static JMenuItem file_closemap;
	private static JMenu edit;
	private static JMenuItem edit_entity;
	private static JMenuItem edit_tile;
	//Map and Map Editor
	private static boolean mapLoaded = false;
	private static boolean mapChanged = false;
	private static Map loadedMap;
	private static JComboBox<String> editType;
        //Sprite editor
        private static boolean spriteSelected = false;
        private static boolean spriteAltered = false;
        private static Sprite selectedSprite;
        //Warp Tile Editor
        private static Tile selectedTile;
        private static boolean tileSelected = false;
        private static int selectedX=0, selectedY=0;
        
        //sprite editor buttons/text areas
        private static JButton newSprite;
        private static JComboBox<ImageIcon> spriteID;
        private static JTextArea spriteName;
        private static JComboBox<Integer> spriteMapX;
        private static JComboBox<Integer> spriteMapY;
        private static JTextArea eventFileName;
        //Warp Tile Editor buttons/text areas
        private static JButton addWarp;
        private static JButton removeWarp;
        private static JButton saveTile;
        private static JTextArea mapToLoad;
        private static JComboBox<Integer> destinationX;
        private static JComboBox<Integer> destinationY;
        
        
	//Edit types for the Editor type drop down menu
	public static final int EDIT_TILE = 0;
	public static final int EDIT_SPRITES = 1;
        public static final int EDIT_WARPS = 2;
	//Edit Selectors
	private static JComboBox<ImageIcon> tileSelecterLeft;
	private static JComboBox<ImageIcon> tileSelecterRight;
        //map Size Altering
        private static JComboBox<Integer> mapWidth;
        private static JComboBox<Integer> mapHeight;
        
        
        private static int menuItemLeft = 1300, menuItemRight = menuItemLeft+100;
        public MapEditor(){
		setPreferredSize(new Dimension(40*32+200,25*32));
		setLayout(null);
		//Setup the MapEditor Combo Box's
		editType = new JComboBox<String>();
		editType.addItem("Tile Edit Mode");
                editType.addItem("Sprite Edit Mode");
                editType.addItem("Warp Placement Mode");
		editType.setBounds(menuItemLeft,10,160,30);
		add(editType);
		//map eiditing details
		tileSelecterLeft = new JComboBox<ImageIcon>();
		tileSelecterRight = new JComboBox<ImageIcon>();
                mapWidth = new JComboBox<Integer>();
                mapHeight = new JComboBox<Integer>();
		tileSelecterLeft.setBounds(menuItemLeft,50,60,30);
		tileSelecterRight.setBounds(menuItemRight,50,60,30);
                mapWidth.setBounds(menuItemLeft,100,60,30);
                mapHeight.setBounds(menuItemRight,100,60,30);
		for(BufferedImage b : Tile.getImagesList()){
			tileSelecterLeft.addItem(new ImageIcon(b));
			tileSelecterRight.addItem(new ImageIcon(b));
		}
                for(int i=3;i<41;i++)
                    mapWidth.addItem(i);
                for(int i=3;i<26;i++)
                    mapHeight.addItem(i);
                //sprite editing details
                spriteID = new JComboBox<ImageIcon>();
                spriteName = new JTextArea();
                eventFileName = new JTextArea();
                spriteMapX = new JComboBox<Integer>();
                spriteMapY = new JComboBox<Integer>();
                newSprite = new JButton();
                
                spriteID.setBounds(menuItemLeft, 350, 60, 45);
                newSprite.setBounds(menuItemLeft, 300, 140, 45);
                spriteName.setBounds(menuItemLeft, 400, 140, 30);
                eventFileName.setBounds(menuItemLeft, 440, 140, 30);
                spriteMapX.setBounds(menuItemLeft,480,60,30);
                spriteMapY.setBounds(menuItemRight,480,60,30);
                
                newSprite.setText("New Sprite");
                
                
                for(BufferedImage b:Sprite.getSpritesheetList()){
                    spriteID.addItem(new ImageIcon(b.getSubimage(1, 1, 32, 32)));
                }
                for(int i=0;i<=40;i++){
                    if(i<=25)
                        spriteMapY.addItem(i);
                    spriteMapX.addItem(i);
                }
                //Warp Tile editing details
                addWarp = new JButton();
                removeWarp = new JButton();
                saveTile = new JButton();
                mapToLoad = new JTextArea();
                destinationX = new JComboBox<>();
                destinationY = new JComboBox<>();
                
                addWarp.setBounds(menuItemLeft,350,150,45);
                removeWarp.setBounds(menuItemLeft,350,150,45);
                removeWarp.setVisible(false);
                saveTile.setBounds(menuItemLeft,400,150,45);
                mapToLoad.setBounds(menuItemLeft, 450, 140, 30);
                destinationX.setBounds(menuItemLeft,490,60,30);
                destinationY.setBounds(menuItemRight,490,60,30);
                
                addWarp.setText("Create New WarpTile");
                removeWarp.setText("Remove WarpTile");
                saveTile.setText("Save Warp Data");
                for(int i=2;i<=40;i++){
                    if(i<=25)
                        destinationY.addItem(i);
                    destinationX.addItem(i);
                }
                //all the add's
		add(tileSelecterLeft);
		add(tileSelecterRight);
                add(mapWidth);
                add(mapHeight);
                
                add(spriteID);
                add(spriteName);
                add(eventFileName);
                add(spriteMapX);
                add(spriteMapY);
                add(newSprite);
                
                add(addWarp);
                add(removeWarp);
                add(saveTile);
                add(mapToLoad);
                add(destinationX);
                add(destinationY);
	}
        public void paintComponent(Graphics g){
            //System.out.println("painting");
		super.paintComponent(g);
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0, 0, 19*32, 19*32);
		if(!mapLoaded){
			return;
		}
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0, 0, loadedMap.getY()*32, loadedMap.getX()*32);
		//Draw the tiles
		for(int col = 0; col < loadedMap.getX(); col++){
			for(int row = 0; row < loadedMap.getY(); row++){
				Tile thisTile = loadedMap.getTile(col,row);
                                try{
                                    g.drawImage(thisTile.getImage(), col*32, row*32, 32, 32, null);
                                }catch(NullPointerException e){
                                    g.drawImage(new Tile(0).getImage(), col*32, row*32, 32, 32, null);
                                }
			}
		}
		//Draw the lines
		for(int row = 0; row < loadedMap.getY()+1; row++)
			g.drawLine(0, row*32, loadedMap.getX()*32, row*32);
		for(int col = 0; col < loadedMap.getX()+1; col++)
			g.drawLine(col*32, 0, col*32, loadedMap.getY()*32);
                
                if(editType.getSelectedIndex()==EDIT_SPRITES){
                    for(Sprite s:loadedMap.getSpriteList())
                        g.drawImage(s.getSprite(), (s.getMapX()-1)*32, (s.getMapY()-1)*32, 32, 32, null);
                }
                if(editType.getSelectedIndex()==EDIT_WARPS){
                    if(selectedTile==null||selectedTile.getClass()== new Tile(0).getClass()){
                        addWarp.setVisible(true);
                        removeWarp.setVisible(false);
                    }else{
                        removeWarp.setVisible(true);
                        addWarp.setVisible(false);
                    }
                    g.setColor(Color.orange);
                    g.drawRect(selectedX*32+1, selectedY*32+1,30,30);
                    for(int col = 0; col < loadedMap.getX(); col++){
			for(int row = 0; row < loadedMap.getY(); row++){
				Tile thisTile = loadedMap.getTile(col,row);
                                try{
                                    ((WarpTile)thisTile).getID();
                                    g.setColor(Color.red);
                                    g.drawRect(col*32+1, row*32+1, 30, 30);
                                    g.drawRect(col*32+2, row*32+2, 28, 28);
                                    g.setColor(Color.gray);
                                }catch(ClassCastException e){
                                    
                                }
			}
		}
                }
	}
        
        public static void setupMenu(){
		menuBar = new JMenuBar();
		file = new JMenu("File [TODO]");
		file_newmap = new JMenuItem("Create New Map [TODO]");
		file_savemap = new JMenuItem("Save Map");
		file_loadmap = new JMenuItem("Load Map");
		file_closemap = new JMenuItem("Close Map");
		file.add(file_newmap);
		file.addSeparator();
		file.add(file_savemap);
		file.add(file_loadmap);
		file.addSeparator();
		file.add(file_closemap);
		menuBar.add(file);
		edit = new JMenu("Edit");
		edit_entity = new JMenuItem("Open Entity Editor");
		edit_tile = new JMenuItem("Open Tile Editor [TODO]");
		edit.add(edit_tile);
		edit.add(edit_entity);
		file.add(edit);
		menuBar.add(edit);
		file.addActionListener(editor);
		file_newmap.addActionListener(editor);
		file_savemap.addActionListener(editor);
		file_loadmap.addActionListener(editor);
		file_closemap.addActionListener(editor);
		edit.addActionListener(editor);
		edit_entity.addActionListener(editor);
		edit_tile.addActionListener(editor);
	}
        public static void generateError(String message, Exception error){
		if(error == null){
			JOptionPane.showMessageDialog(window, message);
			return;
		}
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		error.printStackTrace(pw);
		String errorMsg = sw.toString().substring(0, sw.toString().indexOf("at javax"));
		JOptionPane.showMessageDialog(window, String.format("%s%nError Below:%n%n%s", message, errorMsg));
	}
        public static void closeMap(){
		if(mapChanged){
			switch(JOptionPane.showConfirmDialog(null, "There are unsaved changes to the map. Would you like to save them?")){
				case JOptionPane.OK_OPTION:{
					loadedMap.saveMap(loadedMap);
					mapChanged = false;
				}break;
				case JOptionPane.CANCEL_OPTION:{
					return;
				}
			}
		}
		mapLoaded = false;
		loadedMap = null;
	}
        public static void loadSpriteData(){
            spriteMapX.setSelectedIndex(selectedSprite.getMapX()+1);
            spriteMapY.setSelectedIndex(selectedSprite.getMapY()+1);
            spriteName.setText(selectedSprite.getName());
            eventFileName.setText(selectedSprite.getEventFileName());
            spriteID.setSelectedIndex(selectedSprite.getID());
            System.out.printf("Sprite %s loaded\n",selectedSprite.getName());
        }
        
        public void actionPerformed(ActionEvent ae){
            //System.out.println("Action");
		if(ae.getSource() == file_loadmap){
			JFileChooser fc = new JFileChooser();
			fc.setCurrentDirectory(new File(System.getProperty("user.dir")+"/maps/"));
			int returnVal = fc.showOpenDialog(this);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				String fileName = file.getName();
				loadedMap = Map.loadMap(fileName);
				mapLoaded = true;
                                mapHeight.setSelectedIndex(loadedMap.getY()-3);
                                mapWidth.setSelectedIndex(loadedMap.getX()-3);
			}
		}
		else if(ae.getSource() == file_savemap){
			if(mapLoaded){
                            System.out.println("Saving map");
				loadedMap.saveMap(loadedMap);
				mapChanged = false;
			}
		}
                else if(ae.getSource() == file_newmap){
                    mapLoaded = true;
                    loadedMap = new Map();
                    mapHeight.setSelectedIndex(10-3);
                    mapWidth.setSelectedIndex(10-3);
                }
		else if(ae.getSource() == file_closemap){
			if(mapLoaded){
				closeMap();
			}
		}
                else if(ae.getSource() == newSprite){
                    System.out.println("new sprite");
                    Sprite s = new Sprite(0,"name",1,1,0,"eventName",loadedMap.getName());
                    loadedMap.addSprite(s);
                    selectedSprite = s;
                    loadSpriteData();
                }
                else if(ae.getSource() == addWarp&&selectedTile!=null){
                    selectedTile = new WarpTile(selectedTile.getID(),0,0,"");
                    destinationX.setSelectedIndex(0);
                    destinationY.setSelectedIndex(0);
                    mapToLoad.setText("mapFile.txt");
                    loadedMap.setTile(selectedX, selectedY, selectedTile);
                }
                else if(ae.getSource() == removeWarp&&selectedTile!=null){
                    selectedTile = new Tile(selectedTile.getID());
                    destinationX.setSelectedIndex(0);
                    destinationY.setSelectedIndex(0);
                    mapToLoad.setText("");
                    loadedMap.setTile(selectedX, selectedY, selectedTile);
                }
                else if(ae.getSource() == saveTile&&selectedTile!=null){
                    try{
                        if(((WarpTile)selectedTile).getWarpX()!=destinationX.getSelectedIndex()+2){
                            ((WarpTile)selectedTile).setWarpX(destinationX.getSelectedIndex()+2);
                        }
                        if(((WarpTile)selectedTile).getWarpY()!=destinationY.getSelectedIndex()+2){
                            ((WarpTile)selectedTile).setWarpY(destinationY.getSelectedIndex()+2);
                        }
                        if(!((WarpTile)selectedTile).getNewMapName().equals(mapToLoad.getText())){
                            ((WarpTile)selectedTile).setNewMapName(mapToLoad.getText());
                        }
                    }catch(ClassCastException e){
                        System.out.println("Non-Warp Tile saved");
                        
                    }
                    loadedMap.setTile(selectedX, selectedY, selectedTile);
                    tileSelected = false;
                }
		repaint();
	}
        
	//MouseListener
	public void mouseClicked(MouseEvent me) {mousePressed(me);}
	public void mouseEntered(MouseEvent me) {}
	public void mouseExited(MouseEvent me) {}
	public void mousePressed(MouseEvent me) {
                //repaint();
		//Get the x and y of the square we clicked on
		int squareX = me.getX()/32;
		int squareY = me.getY()/32;
		//Only do this if we are inside our map
                repaint();
		if(squareX >= mapWidth.getSelectedIndex()+3 || squareY >= mapHeight.getSelectedIndex()+3 || !mapLoaded)
			return;
                if(loadedMap.getX()!=mapWidth.getSelectedIndex()+3){
                    loadedMap.setWidth(mapWidth.getSelectedIndex()+3);
                    System.out.println(loadedMap.mapWidth);
                }
                if(loadedMap.getY()!=mapHeight.getSelectedIndex()+3){
                    loadedMap.setHeight(mapHeight.getSelectedIndex()+3);
                    System.out.println(loadedMap.mapHeight);
                }
                //load menu stuff essential to current mode
                //Tile editing
                tileSelecterLeft.setVisible(false);
                tileSelecterRight.setVisible(false);
                mapWidth.setVisible(false);
                mapHeight.setVisible(false);
                //sprite editing
                newSprite.setVisible(false);
                spriteID.setVisible(false);
                spriteName.setVisible(false);
                spriteMapX.setVisible(false);
                spriteMapY.setVisible(false);
                eventFileName.setVisible(false);
                saveTile.setVisible(false);
                //warp editing
                addWarp.setVisible(false);
                removeWarp.setVisible(false);
                mapToLoad.setVisible(false);
                destinationX.setVisible(false);
                destinationY.setVisible(false);
                switch(editType.getSelectedIndex()){
                    case EDIT_TILE:
                        selectedTile=null;
                        tileSelecterLeft.setVisible(true);
                        tileSelecterRight.setVisible(true);
                        mapWidth.setVisible(true);
                        mapHeight.setVisible(true);
                        break;
                    case EDIT_SPRITES:
                        selectedTile=null;
                        newSprite.setVisible(true);
                        spriteID.setVisible(true);
                        spriteName.setVisible(true);
                        spriteMapX.setVisible(true);
                        spriteMapY.setVisible(true);
                        eventFileName.setVisible(true);
                        break;
                    case EDIT_WARPS:
                        saveTile.setVisible(true);
                        addWarp.setVisible(true);
                        removeWarp.setVisible(true);
                        mapToLoad.setVisible(true);
                        destinationX.setVisible(true);
                        destinationY.setVisible(true);
                        break;
                }
		//Switch depending on what mode we are set on
		switch(editType.getSelectedIndex()){
			//If we are on edit tile mode
			case EDIT_TILE:{
				if(me.getButton() == MouseEvent.BUTTON1)
					loadedMap.changeTile(squareX, squareY, tileSelecterLeft.getSelectedIndex());
				else if(me.getButton() == MouseEvent.BUTTON3)
					loadedMap.changeTile(squareX, squareY, tileSelecterRight.getSelectedIndex());
				mapChanged = true;
			}break;
                        case EDIT_SPRITES:{
                            if(me.getButton() == MouseEvent.BUTTON1){
                                if(spriteSelected){
                                    loadedMap.getSpriteList().remove(selectedSprite);
                                    if(selectedSprite.getMapX()!=spriteMapX.getSelectedIndex()-1||selectedSprite.getMapY()!=spriteMapY.getSelectedIndex()-1){
                                        selectedSprite.setNewMapLocation(spriteMapX.getSelectedIndex()-1, spriteMapY.getSelectedIndex()-1);
                                    }
                                    if(!selectedSprite.getName().equals(spriteName.getText())){
                                        selectedSprite.setName(spriteName.getText());
                                    }
                                    if(!selectedSprite.getEventFileName().equals(eventFileName.getText())){
                                        selectedSprite.setEventFileName(eventFileName.getText());
                                    }
                                    if(selectedSprite.getID()!=spriteID.getSelectedIndex()){
                                        selectedSprite.setID(spriteID.getSelectedIndex());
                                    }
                                    loadedMap.getSpriteList().add(selectedSprite);
                                    loadSpriteData();
                                    mapChanged = true;
                                }
                                for(Sprite s:loadedMap.getSpriteList()){
                                    if(s.getMapX()-1==squareX&&s.getMapY()-1==squareY){
                                        selectedSprite = s;
                                        spriteSelected= true;
                                        loadSpriteData();
                                    }
                                }
                            }
                            
                        }break;
                        case EDIT_WARPS:
                            if(me.getButton()==MouseEvent.BUTTON1){
                                if(selectedTile!=null){
                                    if(selectedTile.getClass()==WarpTile.class){
                                        destinationX.setSelectedIndex(((WarpTile)selectedTile).getWarpX()+2);
                                        destinationY.setSelectedIndex(((WarpTile)selectedTile).getWarpY()+2);
                                        mapToLoad.setText(((WarpTile)selectedTile).getNewMapName());
                                    }
                                }
                                selectedX = squareX;
                                selectedY = squareY;
                                //System.out.println(selectedX+"/"+squareX);
                                selectedTile = loadedMap.getTile(squareX, squareY);
                                tileSelected = true;
                            }
                            break;
		}
		//Repaint every click
		
	}
	public void mouseReleased(MouseEvent me) {}
        
        public static void main(String[]args){
		System.out.println("Starting Up - Editor");
		System.out.println("======================");
		System.out.println("Loading Tiles...");
		Tile.startUp();
                System.out.println("Loading Sprites...");
                Sprite.startUp();
		System.out.println("Starting Editor...");
		editor = new MapEditor();
		setupMenu();
                newSprite.addActionListener(editor);
                addWarp.addActionListener(editor);
                removeWarp.addActionListener(editor);
                saveTile.addActionListener(editor);
                        
		window = new JFrame("Editor");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setLayout(new BorderLayout());
		window.add(menuBar, BorderLayout.NORTH);
		window.add(editor, BorderLayout.CENTER);
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		editor.addMouseListener(editor);
		window.addWindowListener(new WindowListener(){
			public void windowClosing(WindowEvent e){
				if(mapChanged){
					switch(JOptionPane.showConfirmDialog(editor, "Would you like to save the map before closing?")){
						case JOptionPane.OK_OPTION:{
							loadedMap.saveMap(loadedMap);
							System.out.println("Saving Map");
						}
						case JOptionPane.NO_OPTION:{
							System.exit(0);
						}break;
					}
				}
				else{
					window.dispose();
				}	
			}
			public void windowActivated(WindowEvent e) {}
			public void windowClosed(WindowEvent e) {}
			public void windowDeactivated(WindowEvent e) {}
			public void windowDeiconified(WindowEvent e) {}
			public void windowIconified(WindowEvent e) {}
			public void windowOpened(WindowEvent e) {}
		});
	}
}

