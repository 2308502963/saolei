
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.color.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.util.Timer;

public class MinePanel extends JPanel{
	public final int GRID_WIDTH = 20; //������
	public final int GRID_HEIGHT = 20;//����߶�
	private MineFrame mf;
	private int cols;  //��������
	private int rows;//��������
	private int mines;//��������
	private int remainedMines;//δ��ǵ�����
	private int openedBlocks;//�Ѿ�����������
	private Block[][]blocks;//��������
	
	
	
	UpdateTimeTask utt;
	
	public MinePanel(MineFrame mf,int rows,int cols,int mines){
		this.mf=mf;
		initMinePanel(rows,cols,mines);
		this.addMouseListener(new MouseMonitor());
		this.setBackground(new Color(210,210,210));
	}
	/*****��ʼ������*********************/
	public void initMinePanel(int rows,int cols,int mines){
		this.cols = cols;
		this.rows = rows;
		this.mines = mines;
		remainedMines = mines;
		openedBlocks = 0;
		createBlocks();
  		layMines();
		repaint();
	}
	
	public void createBlocks(){
		blocks = new Block[rows][cols];
		for(int i = 0;i<rows;i++){
			for(int j = 0;j<cols;j++){
				blocks[i][j] = new Block(this,i,j,BlockType.ZERO,blockState.ORIGINAL);
			}
		}
	}
	/*****�������****/
	private void layMines(){
		int r;
		int c;
		for(int i = 0;i<rows;i++){
			for(int j = 0;j<cols;j++){
				blocks[i][j].setType(BlockType.ZERO);
				blocks[i][j].setState(blockState.ORIGINAL);
			}
		}
		
		//�������
		int m = 0;
		while(m<mines){
			r = (int)(Math.random()*rows);
			c = (int)(Math.random()*cols);
			if(blocks[r][c].getType()!=BlockType.NINE){ 
				blocks[r][c].setType(BlockType.NINE);
				m++;
			}
		}
		
		//����ÿ���㿪������Χ������
		for(int i = 0;i<rows;i++){
			for(int j = 0;j<cols;j++){
				
				if(blocks[i][j].getType()!=BlockType.NINE){
					countMines(i,j);
				}
			}
		}
	}
	 /** ����С������Χ������**/
	private void countMines(int row, int col) {
		int mineNumber = 0;
		for (int i = row - 1; i <= row + 1; i++) {
			if ((i >= 0) && (i < rows)) {
				for (int j = col - 1; j <= col + 1; j++) {
					if ((j >= 0) && (j < cols)){
						if (blocks[i][j].getType() == BlockType.NINE) {
						mineNumber++;
						}
					}
				}
			}
		}

		blocks[row][col].setType(mineNumber);
	}

	public void paint(Graphics g){
		super.paint(g);
		for(int i = 0;i<rows;i++){
			for(int j = 0;j<cols;j++){
				blocks[i][j].draw(g);
			}
		}
	}
	
	public Dimension getPreferredSize(){
		return new Dimension(cols*GRID_WIDTH,rows*GRID_HEIGHT);
	}
	
	/****���������Ϣ************/
	public class MouseMonitor extends MouseAdapter {
		public void mouseClicked(MouseEvent event){
			
			int col=event.getX()/GRID_WIDTH;
			int row=event.getY()/GRID_HEIGHT;
			if(mf.isStoped())
				return;
			if(!mf.isGamming()){			  
			   mf.setGamming(true);
				utt = new UpdateTimeTask(mf);
				Timer timer = new Timer();
				timer.schedule(utt, 1000, 1000);
			}
			if(event.getButton()==MouseEvent.BUTTON1){
				open(row,col);
			}
			else if(event.getButton() == MouseEvent.BUTTON3){ 
				if(blocks[row][col].getState()==blockState.ORIGINAL){
					blocks[row][col].setState(blockState.MINE_FLAG);
					remainedMines--; 
					mf.setMinesRemained(remainedMines);
					blocks[row][col].draw(MinePanel.this.getGraphics());
				}
				else if(blocks[row][col].getState()==blockState.MINE_FLAG){
					blocks[row][col].setState(blockState.QUESTION_FLAG);
					remainedMines++;
					mf.setMinesRemained(remainedMines);
					blocks[row][col].draw(MinePanel.this.getGraphics());
				}
				else if(blocks[row][col].getState()==blockState.QUESTION_FLAG){
					blocks[row][col].setState(blockState.ORIGINAL);
					blocks[row][col].draw(MinePanel.this.getGraphics());
				}				
				//�Ҽ�����
				else if(blocks[row][col].getState()==blockState.OPEN){
				 int flagNumber = 0;
					for(int i=row-1; i<=row+1; i++){
						for(int j=col-1; j<=col+1; j++){
							if((i>=0)&&(i<rows)&&(j>=0)&&(j<cols)){
								if(blocks[i][j].getState() == blockState.MINE_FLAG){
									flagNumber++;
								}
							}
						}
					}
					if(flagNumber == blocks[row][col].getType()){//���Ѿ���ǳ���
						for(int i=row-1; i<=row+1; i++){
							for(int j=col-1; j<=col+1; j++){
								if((i>=0)&&(i<rows)&&(j>=0)&&(j<cols)){
									open(i,j);
								}
							}
						}
					}
				}
			}			
		}
	}				
			
	

	public void open(int row, int col){
		if(blocks[row][col].getState()==blockState.ORIGINAL){
			if(blocks[row][col].open()){
				openedBlocks++;
				if(openedBlocks == rows * cols -  mines){
					wins();  //ɨ�׳ɹ�
				}
				if(blocks[row][col].getType()==BlockType.ZERO){
					search(row, col);  // ����ɨ��
				}						

			}else{
				lose(row,col); //ɨ��ʧ��
			}
		}
	}
	//ʧ��
	private void lose(int row,int col) {
		int i,j;  
		utt.cancel();
		for(i=0; i<rows; i++){
			for(j=0; j<cols; j++){
				if( (blocks[i][j].getType()==BlockType.NINE) && (blocks[i][j].getState()!=blockState.MINE_FLAG)){
					blocks[i][j].setState(blockState.OPEN);
					blocks[i][j].draw(MinePanel.this.getGraphics());
				}
			}
		}
		blocks[row][col].setState(blockState.EXPLODED);
		blocks[row][col].draw(MinePanel.this.getGraphics());

		mf.setGamming(false);
		mf.setStoped(true);
		JOptionPane.showMessageDialog(this, "ʧ�ܣ�");
		
	}
	//��������
	private void search(int row, int col){
		int i,j;
		for(i=row-1; i<=row+1; i++){ 
			if( (i<0) || (i>=rows) ){ 
				continue;
			}
			for(j=col-1; j<=col+1; j++){
				if( (j<0)||(j>=cols) ){ 
					continue;
				}
				if(blocks[i][j].getState()==blockState.ORIGINAL ){
				
					blocks[i][j].open();  
					openedBlocks++;
					if(openedBlocks == rows * cols -  mines){
						wins();
					}
					if(blocks[i][j].getType()==BlockType.ZERO){
						search(i,j);  
					}
				}
			}
		}
	}
	
	//�ɹ�
	public void wins() {
		int second = utt.getSecond();
		utt.cancel();
		mf.setGamming(false);
		mf.setStoped(true);		
		JOptionPane.showMessageDialog(this, "������");
		/**
		 * ���а񣬱����¼�¼
		 */
		int grade = mf.getGrade();
		Record record = mf.getRecord();
		boolean newRecord = false;
		switch(grade){
		case Grade.LOWER:
			if(second < record.getLowerScore()){
				newRecord = true;
			}
			break;
		case Grade.MEDIAL:
			if(second < record.getMedialScore()){
				newRecord = true;
			}
			break;
		case Grade.HIGHER:
			if(second < record.getHigherScore()){
				newRecord = true;
			}
			break;
		}
		if(newRecord){
			String name = null;
			DialogRecordName dlg = new DialogRecordName(mf,second, grade);
			int option = dlg.openDialog();
			if(option==1){
				name = dlg.name.getText();
				switch(grade){
				case Grade.LOWER:
					record.setLowerName(name);
					record.setLowerScore(second);
					break;
				case Grade.MEDIAL:
					record.setMedialName(name);
					record.setMedialScore(second);
					break;
				case Grade.HIGHER:
					record.setHigherName(name);
					record.setHigherScore(second);
					break;
				}
				RecordDao rd = new RecordDao();
				rd.writeRecord(record);
			}			
		}
	}
		
		
	}	
