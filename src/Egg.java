import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;


public class Egg {
	
	int row,col;
	int  width = Yard.BLOCK_SIZE;
	int  height = Yard.BLOCK_SIZE;
	private static Random r = new Random();
	private Color color = Color.GREEN;
	
	public Egg(int row, int col) {
		this.row = row;
		this.col = col;
	} 
	
	/*蛇吃掉之后，重新出现egg*/
	public void reAppear() {
		this.row = r.nextInt(Yard.ROWS-2)+2;
		this.col = r.nextInt(Yard.CLOS);
	}
	
	
	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	/*egg随机出现*/
	public Egg() {
		this(r.nextInt(Yard.ROWS-2)+2,r.nextInt(Yard.CLOS));
	}
	
	public Rectangle getRect() {
 		return new Rectangle (Yard.BLOCK_SIZE * col, Yard.BLOCK_SIZE * row, row, col);
 	}
	
	public void draw(Graphics g) {
		Color c = g.getColor();
		g.setColor(color);
		g.fillOval(Yard.BLOCK_SIZE *col, Yard.BLOCK_SIZE *row, width, height);
		g.setColor(c);
		
		if(color == Color.GREEN) {
			color = color.RED;
		}else {
			color = Color.GREEN;
		}
	}
}
