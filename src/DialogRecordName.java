import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.jar.JarFile;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class DialogRecordName extends JDialog implements ActionListener {
	private JLabel msg;
	JTextField name;
	private JButton OK;
	private JButton Cancel;
	private int option = 0;

	public DialogRecordName(MineFrame parent, int second, int grade) {
		super(parent, "", JDialog.ModalityType.APPLICATION_MODAL);
		switch (grade) {
		case Grade.LOWER:
			setTitle("初级记录");
			break;
		case Grade.MEDIAL:
			setTitle("中级记录");
			break;
		case Grade.HIGHER:
			setTitle("高级记录");
			break;
		}
		msg = new JLabel("你的成勣是:" + second + "秒,輸入你的名字");
		name = new JTextField(20);
		OK = new JButton("確定");
		OK.addActionListener(this);
		Cancel = new JButton("取消");
		Cancel.addActionListener(this);
		

		this.setLayout(new GridLayout(3, 1));

		this.add(msg);
		JPanel downPanel = new JPanel();
		JPanel jpSouth=new JPanel();
		downPanel.add(name);
		jpSouth.add(OK);
		jpSouth.add(Cancel);
		this.add(downPanel);
		this.add(jpSouth);
		this.pack();
		this.setLocationRelativeTo(parent);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(OK)) {
			option = 1;
			dispose();
		} else if (e.getSource().equals(Cancel)) {
			option = 0;
			dispose();
		}
	}

	public int openDialog() {
		this.setVisible(true);
		return option;
	}

}

