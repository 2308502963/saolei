import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class DialogSelfDefine extends JDialog implements ActionListener{
	MineFrame mf;
	JLabel labelRows = new JLabel("������");
	JLabel labelCols = new JLabel("������");
	JLabel labelMines = new JLabel("������");
	JTextField  tfRows = new JTextField(5);
	JTextField  tfCols = new JTextField(5);
	JTextField  tfMines = new JTextField(5);
	JButton btOK = new JButton("ȷ��");
	JButton btCancel = new JButton("ȡ��");
	private int option=0;
	
	public DialogSelfDefine(MineFrame parent){
		super(parent, "�û��Զ���",JDialog.ModalityType.APPLICATION_MODAL);	
		mf = parent;
		this.setLayout(new BorderLayout());
		JPanel jpWest = new JPanel();		
		JPanel jpCenter = new JPanel();	
		JPanel jpSouth = new JPanel();		

		jpWest.setLayout(new GridLayout(3, 1));
		jpCenter.setLayout(new GridLayout(3, 1));		
		jpSouth.setLayout(new FlowLayout());

		jpWest.add(labelRows);
		jpWest.add(labelCols);
		jpWest.add(labelMines);

		jpCenter.add(tfRows);
		jpCenter.add(tfCols);
		jpCenter.add(tfMines);
		
		jpSouth.add(btOK);
		jpSouth.add(btCancel);
		
		tfRows.setText("" + mf.getRows());
		tfCols.setText("" + mf.getCols());
		tfMines.setText("" + mf.getMines());
		
		this.add(new JPanel(),BorderLayout.NORTH); //�Ϸ�����հ�
		this.add(jpWest,BorderLayout.WEST);
		this.add(jpCenter,BorderLayout.CENTER);
		this.add(new JPanel(),BorderLayout.EAST); //�Ҳ�����հ�		
		this.add(jpSouth,BorderLayout.SOUTH);

		btOK.addActionListener(this);
		btCancel.addActionListener(this);
		
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.pack();
		this.setLocationRelativeTo(mf);
		
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(btOK)){
			option = 1;
			dispose();
		}
		else if(e.getSource().equals(btCancel)){
			option = 0;
			dispose();
		}
		
	}
	public int openDialog() {
        this.setVisible(true);
        return option;
    }

}
