package ie.gmit.sw.ai;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.imageio.*;
import javax.swing.*;
public class GameView extends JPanel implements ActionListener{
	private static final long serialVersionUID = 1L;
	public static final int DEFAULT_VIEW_SIZE = 800;	
	private static final int IMAGE_COUNT = 20;
	private int cellspan = 5;	
	private int cellpadding = 2;
	private char[][] maze;
	private BufferedImage[] images;
	private int enemy_state = 5;
	private int player_state = 7;
	private int attack_state = 8;
	private Timer timer;
	private int currentRow;
	private int currentCol;
	private boolean zoomOut = false;
	private int imageIndex = -1;

	public GameView(char[][] maze) throws Exception{
		init();
		this.maze = maze;
		setBackground(Color.LIGHT_GRAY);
		setDoubleBuffered(true);
		timer = new Timer(300, this);
		timer.start();
	}

	public void setCurrentRow(int row) {
		if (row < cellpadding){
			currentRow = cellpadding;
		}else if (row > (maze.length - 1) - cellpadding){
			currentRow = (maze.length - 1) - cellpadding;
		}else{
			currentRow = row;
		}
	}

	public void setCurrentCol(int col) {
		if (col < cellpadding){
			currentCol = cellpadding;
		}else if (col > (maze[currentRow].length - 1) - cellpadding){
			currentCol = (maze[currentRow].length - 1) - cellpadding;
		}else{
			currentCol = col;
		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;

		cellspan = zoomOut ? maze.length : 5;         
		final int size = DEFAULT_VIEW_SIZE/cellspan;
		g2.drawRect(0, 0, GameView.DEFAULT_VIEW_SIZE, GameView.DEFAULT_VIEW_SIZE);

		for(int row = 0; row < cellspan; row++) {
			for (int col = 0; col < cellspan; col++){  
				int x1 = col * size;
				int y1 = row * size;

				char ch = 'X';

				if (zoomOut){
					ch = maze[row][col];
					if (row == currentRow && col == currentCol){
						g2.setColor(Color.YELLOW);
						g2.fillRect(x1, y1, size, size);
						continue;
					}
				}else{
					ch = maze[currentRow - cellpadding + row][currentCol - cellpadding + col];
				}


				if (ch == 'X'){        			
					imageIndex = 0;;
				}else if (ch == 'W'){
					imageIndex = 1;;
				}else if (ch == '?'){
					imageIndex = 2;;
				}else if (ch == 'B'){
					imageIndex = 3;;
				}else if (ch == 'H'){
					imageIndex = 4;;
				}else if (ch == 'E'){
					imageIndex = enemy_state;;
				}else if (ch == 'P'){
					imageIndex = player_state;;
				}else if (ch == 'F'){
					imageIndex = attack_state;;
				}else{
					imageIndex = -1;
				}

				if (imageIndex >= 0){
					g2.drawImage(images[imageIndex], x1, y1, null);
				}else{
					g2.setColor(Color.LIGHT_GRAY);
					g2.fillRect(x1, y1, size, size);
				}      		
			}
		}
	}

	public void toggleZoom(){
		zoomOut = !zoomOut;		
	}
	static GameRunner runner;
	public static void main(String[] args) {
		runner=new GameRunner();
	}
	public static int times=0;
	public void actionPerformed(ActionEvent e) {	
		if (enemy_state < 0 || enemy_state == 5){
			enemy_state = 6;
		}else{
			enemy_state = 5;
		}

		if(runner.flag){

			if (attack_state < 0 || attack_state == 8){
				attack_state = 9;
			}else if (attack_state < 0 || attack_state == 9){
				attack_state = 10;
			}else if (attack_state < 0 || attack_state == 10){
				attack_state = 11;
			}else if (attack_state < 0 || attack_state == 11){
				attack_state = 12;
			}else if (attack_state < 0 || attack_state == 12){
				attack_state = 13;
			}else if (attack_state < 0 || attack_state == 13){
				attack_state = 14;
			}else if (attack_state < 0 || attack_state == 14){
				attack_state = 15;
				times++;
			}else{
				attack_state = 8;
			}
			System.out.println(times);
			if(times>=4){
				attack_state=7;
			}

		}
		this.repaint();
	}

	private void init() throws Exception{
		images = new BufferedImage[IMAGE_COUNT];
		images[0] = ImageIO.read(new java.io.File("resources/Wall.png"));
		images[1] = ImageIO.read(new java.io.File("resources/sword.png"));		
		images[2] = ImageIO.read(new java.io.File("resources/help.png"));
		images[3] = ImageIO.read(new java.io.File("resources/bomb.png"));
		images[4] = ImageIO.read(new java.io.File("resources/h_bomb.png"));
		images[5] = ImageIO.read(new java.io.File("resources/spider_down.png"));
		images[6] = ImageIO.read(new java.io.File("resources/spider_up.png"));
		images[7] = ImageIO.read(new java.io.File("resources/boy.png"));
		images[8] = ImageIO.read(new java.io.File("resources/boy1.png"));
		images[9] = ImageIO.read(new java.io.File("resources/boy2.png"));
		images[10] = ImageIO.read(new java.io.File("resources/boy3.png"));
		images[11] = ImageIO.read(new java.io.File("resources/boy4.png"));
		images[12] = ImageIO.read(new java.io.File("resources/boy5.png"));
		images[13] = ImageIO.read(new java.io.File("resources/boy6.png"));
		images[14] = ImageIO.read(new java.io.File("resources/boy7.png"));
		images[15] = ImageIO.read(new java.io.File("resources/boy8.png"));
	}
}