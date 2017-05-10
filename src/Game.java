import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;


public class Game extends JPanel {
	private JLabel[][] grid;
	private int length; 
	private int height;
	private Map map;
	private GameSettings settings;

	public Game(CardLayout views, JPanel mainPanel, Map map, GameSettings settings)  {
		this.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		//this.setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(800,600));
		this.map = map;
		this.settings = settings;
		length = map.getLength();
		height = map.getHeight();
		JPanel gridPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(0,0,0,0);
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.fill = GridBagConstraints.BOTH;
		grid = new JLabel[length][height];

		double scaledLen = 800/length;
		double scaledHgt = 500/height;
		int proportion = (int) Math.min(scaledLen, scaledHgt);

		ImageIcon wall = resizedImage(Map.WALL, proportion);
		ImageIcon empty = resizedImage(Map.EMPTY, proportion);
		ImageIcon player = resizedImage(Map.PLAYER, proportion);
		ImageIcon box = resizedImage(Map.BOX, proportion);
		ImageIcon goal = resizedImage(Map.GOAL, proportion);
		ImageIcon goalBox = resizedImage(Map.GOALBOX, proportion);
		ImageIcon goalPlayer = resizedImage(Map.GOALPLAYER, proportion);



		for (int y=0; y<height; y++){
			gbc.gridy = y;
			for (int x=0; x<length; x++){
				gbc.gridx = x;
				int tileItem = map.getTile(x, y);
				ImageIcon img = null;
				switch (tileItem) {
				case Map.WALL:
					img = wall;
					break;
				case Map.EMPTY:
					img = empty;
					break;
				case Map.PLAYER:
					img = player;
					break;
				case Map.BOX:
					img = box;
					break;
				case Map.GOAL:
					img = goal;
					break;
				case Map.GOALBOX:
					img = goalBox;
					break;
				case Map.GOALPLAYER:
					img = goalPlayer;
					break;
				}
				grid[x][y] = new JLabel("", img, JLabel.CENTER);
				grid[x][y].setPreferredSize(new Dimension(proportion,proportion));
				gridPanel.add(grid[x][y], gbc);
			}
		}

		JPanel btnPanel = new JPanel(new GridLayout(1,3));
		Font gameFont = new Font("Myriad Pro Light", Font.BOLD, 20);
		Border buttonBorder = new LineBorder(Color.BLUE, 2);

		JButton saveBtn = new JButton("Save");
		saveBtn.setFont(gameFont);
		saveBtn.setBorder(buttonBorder);
		saveBtn.setPreferredSize(new Dimension((800-3)/3, 71));

		JButton hintBtn = new JButton("Hint");
		hintBtn.setFont(gameFont);
		hintBtn.setBorder(buttonBorder);
		hintBtn.setPreferredSize(new Dimension((800-3)/3, 71));

		JButton quitBtn = new JButton("Main Menu");
		quitBtn.setFont(gameFont);
		quitBtn.setBorder(buttonBorder);
		quitBtn.setPreferredSize(new Dimension((800-3)/3, 71));


		saveBtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Clicked Save!");
				try {
					SaveLoad.save(Game.this.map);
				} catch (IOException e1) {
					System.out.println("Failed to save");
					e1.printStackTrace();
				}
			}
		});

		hintBtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Clicked Hint!");
				//views.show(mainPanel, "Hint");
			}
		});
		quitBtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Clicked Main Menu!");
				views.show(mainPanel, "Menu");
			}
		});

		btnPanel.add(saveBtn);
		btnPanel.add(hintBtn);
		btnPanel.add(quitBtn);

		this.add(gridPanel);
		this.add(btnPanel);
		
		//add in keyboard controls
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
            @Override
            public boolean dispatchKeyEvent(KeyEvent key) {
            	if (key.getID() == KeyEvent.KEY_PRESSED){
            		System.out.println(key.getKeyCode());
            		if (key.getKeyCode() == KeyEvent.VK_W){
                		Game.this.map.moveUp();
                		System.out.println("music is " + settings.isEnableMusic());
                		playSound("assets/MusicFootsteps.wav");
                	}
            		else if (key.getKeyCode() == KeyEvent.VK_S){
            			Game.this.map.moveDown();
                		playSound("assets/MusicFootsteps.wav");
                	}
            		if (key.getKeyCode() == KeyEvent.VK_A){
            			Game.this.map.moveLeft();
                		playSound("assets/MusicFootsteps.wav");
                	}
            		if (key.getKeyCode() == KeyEvent.VK_D){
            			Game.this.map.moveRight();
                		playSound("assets/MusicFootsteps.wav");
                	}
            		update();
            	}
                return false;
            }
        });
		
		
	}
	
	private void playSound(String filename){
  		try{
			// Open an audio input stream.
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File(filename));
            // Get a sound clip resource.
            Clip clip = AudioSystem.getClip();
            // Open audio clip and load samples from the audio input stream.
            clip.open(audioIn);
            clip.start();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	

	private ImageIcon resizedImage(int item, int size) {

		String imgLoc = null;
		switch (item) {
		case Map.WALL:
			imgLoc = "image/Wall.png";
			break;
		case Map.EMPTY:
			imgLoc = "image/Empty.png";
			break;
		case Map.PLAYER:
			imgLoc = "image/playerUp.png"; // TODO change player direction
			break;
		case Map.BOX:
			imgLoc = "image/Box.png";
			break;
		case Map.GOAL:
			imgLoc = "image/Goal.png";
			break;
		case Map.GOALBOX:
			imgLoc = "image/GoalBox.png";
			break;
		case Map.GOALPLAYER:
			imgLoc = "image/playerUpGoal.png"; // TODO change player direction
			break;
		}

		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(imgLoc));
		} catch (IOException e) {
			return null;
		}

		Image dimg = img.getScaledInstance(size, size, Image.SCALE_SMOOTH);
		return new ImageIcon(dimg);
	}
	
	/**
	 * method for setting the map
	 * @param map
	 */
	public void setMap(Map map){
		this.map = map;
		update();
	}
	
	/**
	 * method for updating the sprites displayed by the grid
	 */
	public void update(){
		System.out.println("updating");
		double scaledLen = 800/length;
		double scaledHgt = 500/height;
		
		int proportion = (int) Math.min(scaledLen, scaledHgt);
		ImageIcon wall = resizedImage(Map.WALL, proportion);
		ImageIcon empty = resizedImage(Map.EMPTY, proportion);
		ImageIcon player = resizedImage(Map.PLAYER, proportion);
		ImageIcon box = resizedImage(Map.BOX, proportion);
		ImageIcon goal = resizedImage(Map.GOAL, proportion);
		ImageIcon goalBox = resizedImage(Map.GOALBOX, proportion);
		ImageIcon goalPlayer = resizedImage(Map.GOALPLAYER, proportion);
		
		for (int y=0; y<height; y++){
			for (int x=0; x<length; x++){
				int tileItem = map.getTile(x, y);
				ImageIcon img = null;
				switch (tileItem) {
				case Map.WALL:
					img = wall;
					break;
				case Map.EMPTY:
					img = empty;
					break;
				case Map.PLAYER:
					img = player;
					break;
				case Map.BOX:
					img = box;
					break;
				case Map.GOAL:
					img = goal;
					break;
				case Map.GOALBOX:
					img = goalBox;
					break;
				case Map.GOALPLAYER:
					img = goalPlayer;
					break;
				}
				grid[x][y].setIcon(img);
				System.out.print(tileItem);
			}
			System.out.println();
		}
	}
	
}
