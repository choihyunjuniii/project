package Test;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import DB.Sql;
import javax.swing.JScrollBar;

public class cal_gui {

	private JFrame frame;
	private JTable table;
	public static String id;
	
	int y, m;
	public static String[][] rs;
	String entryDay;
	String finalDay;
	private JTextField txtFat;
	private JTextField txtWeight;
	private JTextField txtHeight;
	private JTextField txtMuscle;
	private JTextField textField;
	private JTextField textID;

	/**
	 * Launch the application.
	 */

//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					cal_gui window = new cal_gui();
//					window.frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the application.
	 */
	public cal_gui(String id) {
		initialize();
		frame.setVisible(true);
		this.id = id;
		System.out.println(this.id);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		Sql sql = new Sql("good", "1234");

		frame = new JFrame();
		frame.setBounds(100, 100, 654, 568);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		table = new JTable();
		table.setRowSelectionAllowed(false);
		table.setBounds(27, 69, 340, 240);
		table.setRowHeight(40);
		

		JTextArea textHeight = new JTextArea();
		textHeight.setBounds(467, 92, 120, 24);
		frame.getContentPane().add(textHeight);
		
		JTextArea textWeight = new JTextArea();
		textWeight.setBounds(467, 140, 120, 24);
		frame.getContentPane().add(textWeight);
		
		JTextArea textMuscle = new JTextArea();
		textMuscle.setBounds(467, 247, 120, 24);
		frame.getContentPane().add(textMuscle);
		
		JTextArea textFat = new JTextArea();
		textFat.setBounds(467, 191, 120, 24);
		frame.getContentPane().add(textFat);
		

		JTextArea text_int = new JTextArea();
		text_int.setBounds(27, 323, 340, 119);
		frame.getContentPane().add(text_int);
		

		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				text_int.setText("");

				int row = table.getSelectedRow();
				int col = table.getSelectedColumn();
				Object obj = table.getValueAt(row, col);

				int nextDay = Integer.parseInt(String.valueOf(obj)) + 1;
				entryDay = String.format("%s/%s/%s", y, m + 1, String.valueOf(obj));
				finalDay = String.format("%s/%s/%s", y, m + 1, String.valueOf(nextDay));

				String query = String.format("select * from CALENDAR where record_date between '%s' and '%s' and id='%s'", entryDay,finalDay,id); 	// 해당날짜의 데이터를 가져온다.
				System.out.println(query);

				rs = sql.executeQuery(query, 1, 7);	// rs는 아이디, 날짜, 내용
				if (rs.length > 0) {
					text_int.setText(rs[0][2]);// 날짜에 데이터가 있으면 나타냄
					textHeight.setText(rs[0][3]);
					textWeight.setText(rs[0][4]);
					textFat.setText(rs[0][5]);
					textMuscle.setText(rs[0][6]);
				}
			}
		});

		frame.getContentPane().setLayout(null);
		table.setModel(new DefaultTableModel(
				new Object[][] { { null, null, null, null, null, null, null },
						{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
						{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
						{ null, null, null, null, null, null, null }, },
				new String[] { "New column", "New column", "New column", "New column", "New column", "New column",
						"New column" }) {
			Class[] columnTypes = new Class[] { String.class, String.class, String.class, String.class, String.class,
					String.class, String.class };

			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		frame.getContentPane().add(table);
		
		JLabel lblNewLabel = new JLabel("키");
		lblNewLabel.setBounds(398, 97, 57, 15);
		frame.getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("몸무게");
		lblNewLabel_1.setBounds(398, 145, 57, 15);
		frame.getContentPane().add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("체지방");
		lblNewLabel_2.setBounds(398, 196, 57, 15);
		frame.getContentPane().add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("근육량");
		lblNewLabel_3.setBounds(398, 252, 57, 15);
		frame.getContentPane().add(lblNewLabel_3);

		JButton btn_save = new JButton("\uC800\uC7A5");
		btn_save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				
				System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
				if (rs.length == 0) {// 날짜에 해당 데이터가 없어
					System.out.println("0");
					sql.query = String.format("select * from calendar where record_date between '%s' and '%s' and id = '%s'", entryDay,finalDay,id);
					if (sql.executeUpdate() == 0) {			
						System.out.println("1");
						
						// 데이터가 없어

//						query = String.format("Insert INTO calendar_ID(now_day,now_write) values('%s','%s')", entryDay,
//								text_int.getText());

						sql.query = String.format(						// 이러한 것들을 입력해
								//"Insert INTO calendar(id,content,record_date) values('%s','%s','%s')", id,text_int.getText(), entryDay);
								"Insert INTO calendar(id,content,record_date,height,weight,fat,muscle) values('%s','%s','%s',%s,%s,%s,%s)", id,text_int.getText(), entryDay, txtHeight.getText(),
								txtWeight.getText(), txtFat.getText(),txtMuscle.getText());
						System.out.println(sql.query);
						sql.executeUpdate();
						
					}
				} else {	//  현재 있는 데이터가 입력데이터값을 업데이트
					sql.query = String.format("update calendar set content ='%s' where content ='%s'",text_int.getText(), rs[1]);
					System.out.println(sql.query);
					sql.executeUpdate();
				}
		
			}
		});


		btn_save.setBounds(290, 496, 77, 23);
		frame.getContentPane().add(btn_save);
		
		// 날짜 값 가져오기
		Calendar cal = Calendar.getInstance();
		y = 2021;
		m = cal.get(Calendar.MONTH);

		cal.set(y, m, 1);
		int w = cal.get(Calendar.DAY_OF_WEEK);
		int end = cal.getActualMaximum(Calendar.DATE);

		System.out.println("\t" + y + "��" + (m + 1) + "��");

		int row = 0;
		int col = 0;

		for (col = 0; col < w - 1; col++) {
			table.setValueAt(" ", 0, col);
		}

		for (int i = 1; i <= end; i++) {

			table.setValueAt(i, row, col++);

			if (++w % 7 == 1) {
				row++;
				col = 0;
			}

		}

	}
}
