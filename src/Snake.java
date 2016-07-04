import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;


public class Snake {
	private int size=0;
	private Node head = null;
	private Node tail = null;
	
	private Node n = new Node(20,30, Dir.L);
	private Yard y;
    public Snake (Yard y) {
		head = n;
		tail = n;
		size = 1;
		this.y = y;
		
	}
    
    public void addToTail() {
		Node node = null;
		switch(tail.dir) {
		case L :
			node = new Node(tail.row, tail.col + 1, tail.dir);
			break;
		case U :
			node = new Node(tail.row + 1, tail.col, tail.dir);
			break;
		case R :
			node = new Node(tail.row, tail.col - 1, tail.dir);
			break;
		case D :
			node = new Node(tail.row - 1, tail.col, tail.dir);
			break;
		}
		tail.next = node;
		node.prev = tail;
		tail = node;
		size ++;
	}
    
    public void addToHead() {
		Node node = null;
		switch(head.dir) {
		case L :
			node = new Node(head.row, head.col - 1, head.dir);
			break;
		case U :
			node = new Node(head.row - 1, head.col, head.dir);
			break;
		case R :
			node = new Node(head.row, head.col + 1, head.dir);
			break;
		case D :
			node = new Node(head.row + 1, head.col, head.dir);
			break;
		}
		node.next = head;
		head.prev = node;
		head = node;
		size ++;
	}
   
   /*�����ߵ�ͼ��*/
    public void draw(Graphics g) {
    	if(size <= 0) return;
    	move();
    	for(Node n = head; n != null; n = n.next){
    		n.draw(g);
    	}
    }
   
    /*���ƶ�*/
    public void move() {
    	addToHead();
    	deleteFromTail();
    	checkdead();/*�����û����*/
    }
    
    
    private void checkdead() {
    	if(head.row < 2 || head.col < 0 || head.row > Yard.ROWS || head.col > Yard.CLOS)  {
			y.stop();
		}
    	/*�����ͷ����������ײ*/
    	for(Node n = head.next; n != null; n = n.next) {
			if(head.row == n.row && head.col == n.col) {
				y.stop();
			}
		}
	}

    /*ɾ���ߵ�β��*/
	public void deleteFromTail() {
    	if(size == 0) return;
    	tail = tail.prev;
    	tail.next = null;
    }
	
 	private class Node {
		int row,col;
		int width = Yard.BLOCK_SIZE;
		int height = Yard.BLOCK_SIZE;
		Dir dir = Dir.L;
		Node next = null;
		Node prev = null;
		
		Node(int row, int col, Dir dir) {
			this.row = row;
			this.col = col;
			this.dir = dir;
		}
		
		void draw (Graphics g) {
			Color c = g.getColor();
			g.setColor(Color.BLACK);
			g.fillRect(Yard.BLOCK_SIZE * col, Yard.BLOCK_SIZE * row, width, height);
			g.setColor(c);
		}			
	}
 	
 	/*��ײ���*/ 
 	public void eat (Egg e) {
 		if(this.getRect().intersects(e.getRect())){
 			e.reAppear();/*����֮�󣬵����µĵط����³���*/
 			this.addToHead();/*�Ե�֮��һ��*/
 			y.setScore(y.getScore() + 5 );
 		}
 	}
 	/*��ͷ��λ��*/
 	private Rectangle getRect() {
 		return new Rectangle (Yard.BLOCK_SIZE * head.col, Yard.BLOCK_SIZE * head.row, head.row, head.col);
 	}
	
 	/*ͨ���������̵ķ��򣬸ı��ߵ��ƶ�����*/
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		switch (key) {
		case KeyEvent.VK_LEFT :
			if(head.dir != Dir.R)/*Ϊ�˷�ֹ�߻����������ߣ������������ߣ�����㰴���Ҽ��������������������*/
			head.dir = Dir.L ;
			break;
		case KeyEvent.VK_UP :
			if(head.dir != Dir.D)
			head.dir = Dir.U;
			break;
		case KeyEvent.VK_RIGHT :
			if(head.dir != Dir.	L)
			head.dir = Dir.R;
			break;
		case KeyEvent.VK_DOWN :
			if(head.dir != Dir.U)
			head.dir = Dir.D;
			break;
		}
	}
}


