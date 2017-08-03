package com;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
public class ShowTable extends JFrame
{
	JPanel p1;
	Font f1;
	DefaultTableModel dtm;
	JTable table;
	JScrollPane jsp;
public ShowTable(){
	super("FP Growth");
	p1 = new JPanel();
	p1.setLayout(new BorderLayout());
	dtm = new DefaultTableModel(){
		public boolean isCellEditable(int r,int c){
			return false;
		}
	};
	table = new JTable(dtm);
	table.setFont(f1);
	table.setRowHeight(30);
	table.setFont(new Font("Courier New",Font.BOLD,13));
	table.getTableHeader().setFont(new Font("Courier New",Font.BOLD,15));
	jsp = new JScrollPane(table);
	p1.add(jsp,BorderLayout.CENTER);
	dtm.addColumn("Transaction");
	dtm.addColumn("Frequency");

	getContentPane().add(p1,BorderLayout.CENTER);
}
public void clear(){
	for(int i=dtm.getRowCount()-1;i>=0;i--){
		dtm.removeRow(i);
	}
}

}
