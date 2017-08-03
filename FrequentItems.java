package com;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import java.awt.Font;
public class FrequentItems extends JFrame{
	JTable tab;
	DefaultTableModel dtm;
	JScrollPane jsp;
	int length = 0;
	String hop;
public FrequentItems(){
	super("View Frequent Items");
	
	dtm = new DefaultTableModel(){
		public boolean isCellEditable(int row_no,int column_no){
			return false;
		}
	};
	tab = new JTable(dtm);
	tab.getTableHeader().setFont(new Font("Courier New",Font.BOLD,14));
	tab.setFont(new Font("Courier New",Font.BOLD,13));
	tab.setRowHeight(30);
	jsp = new JScrollPane(tab);
	dtm.addColumn("Frequent Items");
	dtm.addColumn("Support Count");
	getContentPane().add(jsp);
	
}
}