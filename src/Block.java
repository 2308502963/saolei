	

	import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JPanel;

public class Block extends JPanel {
	private MinePanel minePanel;
	private int row;  // 在雷区的行号
	private int col;  // 在雷区的列号
	
	public final int WIDTH = 19;   // 方块的宽度
	public final int HEIGHT = 19;  //方块的高度
	private int type;  //0,1,2,3,4,5,6,7,8,（9雷）
	private int state; //0原始状态，1翻开，2标记为雷，3标记为问号 
	
	public  static Toolkit tk;
	public static final Image[] numberImage;  //0~8
	public static final Image[] flagImage ;   //0标记为雷、1标记为问号
	public static final Image[] bombImage;   //0未爆炸、1已爆炸  
	public static final Image backImage;   //未翻开时的背面	
	
	static{ 
		tk = Toolkit.getDefaultToolkit();
		numberImage = new Image[9]; 
		flagImage = new Image[2]; 
		bombImage = new Image[2]; 
		for(int i=0; i<numberImage.length; i++){
			String fileName = "image/"+i+".jpg";
			numberImage[i] = tk.getImage(fileName);
		}
		for(int i=0; i<flagImage.length; i++){
			String fileName = "image/flag"+i+".jpg";
			flagImage[i] = tk.getImage(fileName);
		}
		for(int i=0; i<bombImage.length; i++){
			String fileName = "image/bomb"+i+".jpg";
			bombImage[i] = tk.getImage(fileName);
		}
		backImage = tk.getImage("Image/back.jpg"); 
	}
	
		
	public Block(){
		
	}

	public Block(MinePanel minePanel,int row, int col, int type, int state) {
		super();
		this.minePanel = minePanel;
		this.row = row;
		this.col = col;
		this.type = type;
		this.state = state;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}
	/**
	 * 翻开小方块
	   true：翻开成功   false：翻开失败
	 */
	public boolean open(){
		if(type!=BlockType.NINE){
			state = blockState.OPEN;
			draw(minePanel.getGraphics());
			return true;
		}
		else{
			state = blockState.EXPLODED;
			draw(minePanel.getGraphics());
			return false;
		}
	}

	/**
	 * 显示小方块	 
	 */
	public void draw(Graphics g){
		int x = col*minePanel.GRID_WIDTH;
		int y = row*minePanel.GRID_HEIGHT;
		switch(state){
		case blockState.ORIGINAL:
			g.drawImage(backImage,x,y, WIDTH,HEIGHT,minePanel);
			break;
		case blockState.MINE_FLAG:
			g.drawImage(flagImage[0],x, y, WIDTH,HEIGHT,minePanel);
			break;
		case blockState.QUESTION_FLAG:
			g.drawImage(flagImage[1],x, y, WIDTH,HEIGHT,minePanel);
			break;
		case +blockState.OPEN:
			if(type==BlockType.NINE){
				g.drawImage(bombImage[0],x, y, WIDTH,HEIGHT,minePanel);
			}
			else{
				g.drawImage(numberImage[type],x, y, WIDTH,HEIGHT,minePanel);
			}
			break;  
		case blockState.EXPLODED:
			if(type==BlockType.NINE){
				g.drawImage(bombImage[1],x, y, WIDTH,HEIGHT,minePanel);
			}
			break;
		}
	}
}



