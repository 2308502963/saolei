import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class DialogShowRecord extends JDialog implements ActionListener{
MineFrame mf;
	
	JLabel title = new JLabel("");
	
	JLabel gradeLower = new JLabel("初级：");
	JLabel gradeMedial = new JLabel("中级：");
	JLabel gradeHigher = new JLabel("高级：");
	
	JLabel nameLower = new JLabel("");
	JLabel nameMedial = new JLabel("");
	JLabel nameHigher = new JLabel("");
	
	JLabel scoreLower = new JLabel("");
	JLabel scoreMedial = new JLabel("");
	JLabel scoreHigher = new JLabel("");

	JButton btOK = new JButton("确定");
	
	
	public DialogShowRecord(MineFrame parent){
		super(parent, "扫雷排行榜",JDialog.ModalityType.APPLICATION_MODAL);	
		this.mf = parent;
		this.setLayout(new BorderLayout());
		JPanel jpCentert = new JPanel();		
		JPanel jpSouth = new JPanel();	
		JPanel jpNorth = new JPanel();	
		
		jpNorth.add(title);
		
		jpCentert.setLayout(new GridLayout(3,3));
		jpCentert.add(gradeLower);  
		jpCentert.add(nameLower);
		jpCentert.add(scoreLower);
		jpCentert.add(gradeMedial);
		jpCentert.add(nameMedial);
		jpCentert.add(scoreMedial);
		jpCentert.add(gradeHigher);
		jpCentert.add(nameHigher);
		jpCentert.add(scoreHigher);
		
		jpSouth.add(btOK);
		
		this.add(title,BorderLayout.NORTH);
		this.add(jpCentert,BorderLayout.CENTER);
		this.add(btOK,BorderLayout.SOUTH);
		btOK.addActionListener(this);
		Record record = mf.getRecord();
		if(record!=null){
			title.setText("最好记录为：");
			nameLower.setText(record.getLowerName());
			nameMedial.setText(record.getMedialName());
			nameHigher.setText(record.getHigherName());
			
			scoreLower.setText("" + record.getLowerScore());
			scoreMedial.setText("" + record.getMedialScore());
			scoreHigher.setText("" + record.getHigherScore());
		}
		else{
			title.setText("没有扫雷记录");
		}
		this.setSize(300, 150);
		this.setLocationRelativeTo(parent);
		this.setVisible(true);
	
	}

	public void actionPerformed(ActionEvent arg0) {
		dispose();

	}

}

