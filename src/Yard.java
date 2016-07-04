import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;



public class Yard extends Frame {
	
	PaintThread paintThread = new PaintThread();
	private boolean gameover = false;
	
	public static final int  ROWS  = 30;
	public static final int  CLOS = 30;
	public static final int  BLOCK_SIZE = 15;
	private int score = 0;
	
	Snake s = new Snake(this);
	Egg e = new Egg();
	
	Image offScreenImage = null;
	 
	public void lanuch() {
		this.setLocation(200,200);
		this.setSize(CLOS * BLOCK_SIZE, ROWS * BLOCK_SIZE);
		 
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		this.setVisible(true); 
		this.addKeyListener(new KeyMonitor());/*增加键盘监听*/
		new Thread(paintThread).start();
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Yard().lanuch();
	}
	
	public void stop() {
		gameover = true;
	} 
	
	/*绘制图形*/
	public void paint(Graphics g) {
		
		Color c = g.getColor();
		g.setColor(Color.GRAY);
		g.fillRect(0, 0, CLOS * BLOCK_SIZE, ROWS * BLOCK_SIZE);
		g.setColor(Color.DARK_GRAY);
		//画出横线
		for(int i=1; i<ROWS; i++) {
			g.drawLine(0,BLOCK_SIZE * i, CLOS * BLOCK_SIZE, BLOCK_SIZE * i);
		}
		//画出竖线
		for(int i=1; i<CLOS; i++) {
			g.drawLine(BLOCK_SIZE * i, 0, BLOCK_SIZE * i, ROWS * BLOCK_SIZE);
		}
		g.setColor(Color.YELLOW);
		g.drawString("score: " + score, 10, 60);
		
		if(gameover) {
			g.setFont(new Font("华文彩云" ,Font.BOLD, 50));
			g.drawString("游戏结束", 120, 180);
			paintThread.pause();
		}
		
		g.setColor(c);	
		 
		s.eat(e);
		e.draw(g);
		s.draw(g);
		
		
	}

	public void update(Graphics g) {
		if(offScreenImage == null) {
			offScreenImage = this.createImage(CLOS * BLOCK_SIZE, ROWS * BLOCK_SIZE);
		}
		Graphics goff = offScreenImage.getGraphics();
		paint(goff);
		g.drawImage(offScreenImage, 0, 0,null);
	}
	
	/*定义线程*/
	private class PaintThread implements Runnable {
		private boolean running = true;
		private boolean pause = false;
		public void run() {
			while (running) {
				if(pause) continue; 
				repaint(); /*重绘此组件。*/
				try{
					Thread.sleep(100);
				}catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
		public void pause() {
			this.pause = true;
		}
		
		public void reStart() {
			this.pause = false;
			s = new Snake(Yard.this);
			gameover = false;
		
		}
		public void gameover() {
			running = false;
		}
	}
	
	
    /*键盘监听*/
	private class KeyMonitor extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			int key = e.getKeyCode();
			if(key == KeyEvent.VK_F2) {
				paintThread.reStart();
			}
			s.keyPressed(e);
		}

	}
	
	/**
	 * 拿到所得的分数
	 * @return 分数
	 */
	
	public int getScore() {
		return score;
	}
	
	/**
	 * 设置所得的分数
	 * @param score 分数
	 */
	
	public void setScore(int score) {
		this.score = score;
	}
	
}
