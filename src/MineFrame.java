import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MineFrame extends JFrame {
	MinePanel minePanel;
	private int rows;
	private int cols;
	private int mines;
	private boolean gamming;
	private boolean stoped;
	private int grade;
	private Record record;

	JMenuBar menuBar;
	JMenu menu;
	JMenuItem[] menuItems;
	String[] menuItemNames = { "初级", "中级", "高级", "自定义", "排行榜", "退出" };
	JTextField minesRemained; // 显示剩余雷数的文本框
	JButton reStart; // 重新开始按钮
	JTextField timeUsed; // 显示游戏使用时间的文本框
	Icon face; // 按钮上的图标
	JPanel upPanel; // 计时区域

	public MineFrame() {
		createMenu();
		createUpPanel();
		initParameter(10, 10, 10);// 长 宽雷数
		initRecord();

		Container c = this.getContentPane();
		c.add(upPanel, BorderLayout.NORTH);
		minePanel = new MinePanel(this, rows, cols, mines);
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BorderLayout());
		centerPanel.add(new JPanel(), BorderLayout.WEST);
		centerPanel.add(minePanel, BorderLayout.CENTER);
		minePanel.add(new JPanel(), BorderLayout.EAST);
		c.add(centerPanel, BorderLayout.CENTER);
		this.pack();
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setVisible(true);

	}

	//读取排行榜
	private void initRecord() {
		grade = Grade.LOWER;
		RecordDao rd = new RecordDao();
		record = rd.readRecord();
		if (record == null) {
			record = new Record();
			record.setLowerName("匿名");
			record.setLowerScore(999);
			record.setMedialName("匿名");
			record.setMedialScore(999);
			record.setHigherName("匿名");
			record.setHigherScore(999);
		}

	}

	/********* 创建计时区 ***********/

	private void createUpPanel() {
		minesRemained = new JTextField("0000");
		minesRemained.setEditable(false);
		timeUsed = new JTextField("0000");
		timeUsed.setEditable(false);
		face = new ImageIcon("image/smile.jpg");
		reStart = new JButton(face);
		reStart.addActionListener(new ButtonMonitor());
		JPanel center = new JPanel();
		JPanel right = new JPanel();
		JPanel left = new JPanel();
		center.add(reStart);
		left.add(minesRemained);
		right.add(timeUsed);
		upPanel = new JPanel(new BorderLayout());
		upPanel.add(left, BorderLayout.WEST);
		upPanel.add(center, BorderLayout.CENTER);
		upPanel.add(right, BorderLayout.EAST);
	}

	/************* 创建菜单注册监听器 ***************/

	private void createMenu() {
		menuBar = new JMenuBar();
		menu = new JMenu("游戏");
		menuItems = new JMenuItem[menuItemNames.length];
		for (int i = 0; i < menuItemNames.length; i++) {

			menuItems[i] = new JMenuItem(menuItemNames[i]);
			menu.add(menuItems[i]);
		}
		menuBar.add(menu);
		this.setJMenuBar(menuBar);
		MenuMonitor mm = new MenuMonitor();
		for (int i = 0; i < menuItems.length; i++) {
			menuItems[i].addActionListener(mm);
		}
	}

	/**
	 * 按钮监听器类
	 */
	class ButtonMonitor implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (minePanel.utt != null)
				minePanel.utt.cancel();
			initParameter(rows, cols, mines);
			minePanel.initMinePanel(rows, cols, mines);
			minePanel.repaint();
			stoped = false;
			gamming = false;

		}
	}

	/**
	 * 菜单监听器
	 */
	class MenuMonitor implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JMenuItem mi = (JMenuItem) e.getSource();
			if (mi.equals(menuItems[5])) {
				System.exit(0);
			}
			/** 难度选择 **/
			else {
				int r1 = rows;
				int c1 = cols;
				if (minePanel.utt != null)
					minePanel.utt.cancel();

				if (mi.equals(menuItems[0])) {// 初级
					grade = Grade.LOWER;
					initParameter(10, 10, 1);
				} else if (mi.equals(menuItems[1])) {// 中级
					grade = Grade.MEDIAL;
					initParameter(20, 20, 1);
				} else if (mi.equals(menuItems[2])) {// 高级
					grade = Grade.HIGHER;
					initParameter(50, 50, 1);
				} else if (mi.equals(menuItems[3])) {// 自定义
					grade = Grade.SELF_DEFINE;
					DialogSelfDefine dlg = new DialogSelfDefine(MineFrame.this);
					if (dlg.openDialog() == 1) {
						grade = Grade.SELF_DEFINE;
						int r = Integer.valueOf(dlg.tfRows.getText());
						int c = Integer.valueOf(dlg.tfCols.getText());
						int m = Integer.valueOf(dlg.tfMines.getText());
						initParameter(r, c, m);
					}
				} else if (mi.equals(menuItems[4])) {
					DialogShowRecord dig = new DialogShowRecord(MineFrame.this);
				}

				minePanel.initMinePanel(rows, cols, mines);
				if (r1 != rows || c1 != cols) {
					pack();
				}
				MineFrame.this.setLocationRelativeTo(null);
			}
		}
	}

	private void initParameter(int rows, int cols, int mines) {
		this.rows = rows;
		this.cols = cols;
		this.mines = mines;
		stoped = false;
		gamming = false;
		setTimeUsed(0);
		setMinesRemained(mines);
	}

	/** 计时器 **/
	public void setTimeUsed(int second) {
		String strSecond;
		if (second > 9999) {
			strSecond = "9999";
		} else if (second / 10 == 0) {
			strSecond = "000" + second;
		} else if (second / 100 == 0) {
			strSecond = "00" + second;
		} else if (second / 1000 == 0) {
			strSecond = "0" + second;
		} else {
			strSecond = "" + second;
		}
		timeUsed.setText(strSecond);
	}

	/*** 计雷器 ******/
	public void setMinesRemained(int mines) {
		String strMines;
		if (mines > 9999) {
			strMines = "9999";
		} else if (mines / 10 == 0) {
			strMines = "000" + mines;
		} else if (mines / 100 == 0) {
			strMines = "00" + mines;
		} else if (mines / 1000 == 0) {
			strMines = "0" + mines;
		} else {
			strMines = "" + mines;
		}
		minesRemained.setText(strMines);
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getCols() {
		return cols;
	}

	public void setCols(int cols) {
		this.cols = cols;
	}

	public int getMines() {
		return mines;
	}

	public void setMines(int mines) {
		this.mines = mines;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public boolean isGamming() {
		return gamming;
	}

	public void setGamming(boolean gamming) {
		this.gamming = gamming;
	}

	public boolean isStoped() {
		return stoped;
	}

	public void setStoped(boolean stoped) {
		this.stoped = stoped;
	}

	public Record getRecord() {
		return record;
	}

	public void setRecord(Record record) {
		this.record = record;
	}
}
