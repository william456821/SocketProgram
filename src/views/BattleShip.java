package views;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Line2D;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import player_test.playerController;

public class BattleShip extends JFrame implements ActionListener, KeyListener, MouseListener, MouseMotionListener {
	playerController cpu;
	
	public boolean yourTurn = false; //true:player1, false:player2
	
	public JFrame mainWindow = new JFrame("遊戲視窗");
	static JPanel playerPanel[] = new JPanel[2];

	static JFrame shipWindow[] = new JFrame[2];
	static JPanel shipPanel[] = new JPanel[2];
	static JButton battleship[] = new JButton[4];
	String shipName[] = {"航空母艦 ","大船","中船","小船"};
	static int playerMap[][] = new int[10][10];
	static boolean check2Map[][] = new boolean[10][10];
	int shipCount[] = {1, 1, 2, 1};//
	int shipSize[] = {5, 4, 3, 2}; //
	int nowSize = 0;			   //
	int nowShip;				   //
	boolean changePlayer[] = {true, true, true, true};
	static boolean shipOrientation = true; //true:left,right false:up,down
	public boolean gameStart = false;
	
	BattleShip(int order) {
		initial();
		createMap();
		shipWindow(0);
		cpu = new playerController(this);   //���隞�
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BattleShip window = new BattleShip(1);
					window.mainWindow.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public void initial() {

		for (int i=0;i<10;i++) {
			for (int j=0;j<10;j++) {
				if (!gameStart) {
					playerMap[i][j] = 0;
				}
				check2Map[i][j] = true;
			}
		}		
	}
	
	public void changePlayer() {
		
		shipWindow[0].setVisible(false);
		
		shipCount[0] = 1;
		shipCount[1] = 1;
		shipCount[2] = 2;
		shipCount[3] = 1;
		nowSize = 0;
		
		for (int i=0;i<4;i++) 
			changePlayer[i] = true;
		
		shipWindow(1);
	}
	
	public void createMap() {
		
		mainWindow.setSize(600, 940);
		mainWindow.setLocation(60, 50);
		mainWindow.addKeyListener(this);
		mainWindow.addMouseListener(this);
		mainWindow.addMouseMotionListener(this);
		
		for (int i=0;i<2;i++)
		{
			playerPanel[i] = new JPanel(){
				protected void paintComponent(Graphics g) {
					super.paintComponents(g);
					
					for (int i = 0; i <= 18; i++) {
						int left = 40 + i * 45;
						g.drawLine(0, left , 600, left );
					}
					
					g.setColor(Color.BLACK);
					for (int i = 0; i <= 10; i++) {
						int top = 51 + i * 60;
						g.drawLine(top, 0, top , 940);
					}
					Graphics2D g2 = (Graphics2D) g;
			        int fontSize = 40;
			        Font f = new Font("Comic Sans MS", Font.BOLD, fontSize);
			        g2.setFont(f);
					g2.setColor(Color.RED);				
	                g2.setStroke(new BasicStroke(10));
	                g2.draw(new Line2D.Float(0, 490, 600, 490));
				}
			};
		}
		
		playerPanel[0].setSize(600, 300);
		playerPanel[0].setLocation(0, 0);
		playerPanel[0].setBackground(Color.gray);
		
		playerPanel[1].setSize(600, 300);
		playerPanel[1].setLocation(0, 300);
		playerPanel[1].setBackground(Color.blue);
		
		for (int i=0;i<2;i++) 
			mainWindow.add(playerPanel[i]);
		
		mainWindow.setVisible(true);
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
	
	
	//嚙踝�蕭謍蕭嚙踝嚙踐嚙踐▽嚙踝蕭
	public void shipWindow(int player) {
		
		shipWindow[player] = new JFrame("造船室");
		
		battleship[0] = new JButton(shipName[0] + "x1");
		battleship[1] = new JButton(shipName[1] + "x1");
		battleship[2] = new JButton(shipName[2] + "x2");
		battleship[3] = new JButton(shipName[3] + "x1");	

		shipPanel[player] = new JPanel(new GridLayout(1,4));
		
		for (int i=0;i<4;i++) {
			battleship[i].addActionListener(this);
			shipPanel[player].add(battleship[i]);
		}
		
		shipWindow[player].setSize(600,140);
		shipWindow[player].setLocation(mainWindow.location().x+600, mainWindow.location().y);
		shipWindow[player].add(shipPanel[player]);
		shipWindow[player].addKeyListener(this);
		shipWindow[player].setVisible(true);
		
	}
	
	//嚙踐����蕭謍�
	public void settleShip(int x, int y) {
				
		System.out.println("Clicked point: (" + x + "," + y + ")");
		
			
			if (shipOrientation) {
				if (y+nowSize <= 10) {
					if (check(x,y) && nowSize != 0) {
						for (int i=y;i<y+nowSize;i++) {
							playerMap[x][i]++;
							check2Map[x][i] = false;
						}
						judgeShip(nowShip);
					}
				}
			} else {
				if (x+nowSize <= 10) {
					if (check(x,y) && nowSize != 0) {
						for (int i=x;i<x+nowSize;i++) {
							playerMap[i][y]++;
							check2Map[i][y] = false;
						}
						judgeShip(nowShip);
					}
				}
			}
		if (changePlayer[0] == false && changePlayer[1] == false && changePlayer[2] == false && changePlayer[3] == false ) {
			System.out.print("Change");
			cpu.readyForStart(playerMap);
		}
		
	}
	//嚙踐����蕭謍蕭嚙踐縈嚙踝��蕭謘��僱嚙踝蕭���豯伐蕭��蕭嚙�
	public boolean check(int x, int y) {
		
		boolean result = true;
			
		if (shipOrientation) {
			for (int i=y;i<y+nowSize;i++) {
				if (check2Map[x][i] == false) {
					result = false;
				}
			}
		} else {
			for (int i=x;i<x+nowSize;i++) {
				if (check2Map[i][y] == false) {
					result = false;
				}
			}
		}
		return result;
	}
	//嚙踐�蕭嚙踝��蕭�嚙踐��
	public void Strike(int x, int y) {
		
		if (yourTurn == true) {
			cpu.attack(x,y);
		} else {
			JOptionPane.showMessageDialog(mainWindow, "現在不是你攻擊唷");
		}
		
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		//嚙踝�蕭謘頛魂�蕭�嚙踝蕭謢對��		
		if (e.getSource() == battleship[0]) {
			nowShip = 0;
			nowSize = shipSize[nowShip];
		} else if (e.getSource() == battleship[1]) {
			nowShip = 1;
			nowSize = shipSize[nowShip];
		} else if (e.getSource() == battleship[2]) {
			nowShip = 2;
			nowSize = shipSize[nowShip];
		} else if (e.getSource() == battleship[3]) {
			nowShip = 3;
			nowSize = shipSize[nowShip];
		}
				
	}
	
	//嚙踐�蕭��蕭��蕭謍�嚙踝蕭
	public void judgeShip(int index) {
		nowShip = 0;		//嚙踝�蕭謍�嚙踐���0
		nowSize = 0;		//嚙踝�蕭謍�嚙踐憌�0
		shipCount[index]--;	//嚙踝�蕭謍蕭�嚙踐�蕭�嚙踝蕭嚙�
		battleship[index].setText(shipName[index] + "x" + shipCount[index]);
		if (shipCount[index]==0) {
			battleship[index].setEnabled(false);
			changePlayer[index] = false;
		}
		
	}
	
	//////////////////////////keyListener/////////////////////////
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		int key = e.getKeyCode();
		
		if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT) {
			shipOrientation = !shipOrientation; //���蕭謜�蕭謍蕭謘蕭嚙踝蕭
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	//////////////////////////keyListener/////////////////////////

	
	/////////////////////////mouseListener////////////////////////
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		int x = (e.getX()) / 60;
		int y = (e.getY() - 33) / 45;
		System.out.println(e.getX() + "," + e.getY());
		if (y >= 10) {
			y = y - 10;
		}
		
		if (gameStart == false) {
			settleShip(y, x);
		} else {
			Strike(y,x);
		}
		
		System.out.println("//////////////////////");
		
		for (int i=0;i<10;i++) {
			for (int j=0;j<10;j++) {
				System.out.print(playerMap[i][j] + " ");
			}
			System.out.print("   |   ");
			for (int j=0;j<10;j++) {
				System.out.print(playerMap[i][j] + " ");
			}
			System.out.println();
		}
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
	
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	/////////////////////////mouseListener////////////////////////

	
	////////////////////////mouseMotion//////////////////////////
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
//		System.out.println(e.getPoint());
		int x = (e.getX()) / 60;
		int y = (e.getY() - 33) / 45;
		System.out.println(e.getX() + "," + e.getY());
		System.out.println(x +"," + y);
	}
	////////////////////////mouseMotion//////////////////////////

}