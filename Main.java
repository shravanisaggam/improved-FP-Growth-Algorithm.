package com;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.UIManager;
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingUtilities;
import javax.swing.JOptionPane;
import java.awt.Cursor;
import java.util.ArrayList;
import java.io.File;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JFileChooser;
import org.jfree.ui.RefineryUtilities;
public class Main extends JFrame{
	JPanel p1,p2,p3,p4,p5;
	JLabel l1;
	static JLabel l2;
	Font f1,f2;
	JButton b1,b2,b3,b4,b5,b6;
	JScrollPane jsp;
	DefaultTableModel dtm;
	JTable table;
	File file;
	JFileChooser chooser;
	Painting p = new Painting();
	NPainting np = new NPainting();
	long ptime,ntime,fptime;
	int support;
public Main(){
	super("Association Rules Mining");
	p1 = new JPanel();
	p1.setLayout(new BorderLayout());

	p2 = new JPanel();
	p2.setBackground(Color.black);
	l1 = new JLabel("<HTML><BODY><CENTER>Research of Improved FP-Growth Algorithm in Association Rules Mining</center></body></html>".toUpperCase());
	l1.setForeground(Color.white);
	l1.setFont(new Font("Courier New",Font.BOLD,18));
	p2.add(l1,BorderLayout.CENTER);

	p3 = new JPanel();
	p3.setPreferredSize(new Dimension(300,120));

	p4 = new JPanel();
	p4.setLayout(new GridLayout(2,4,25,10));
	p3.add(p4);

	f2 = new Font("Courier New",Font.BOLD,14);

	chooser = new JFileChooser(new File("dataset"));
	
	b1 = new JButton("Upload");
	b1.setFont(f2);
	p4.add(b1);
	b1.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			int option = chooser.showOpenDialog(Main.this);
			if(option == chooser.APPROVE_OPTION){
				file = chooser.getSelectedFile();
				clearTable();
				String input = JOptionPane.showInputDialog(Main.this,"Enter support count");
				try{
					support = Integer.parseInt(input.trim());
				}catch(NumberFormatException nfe){
					JOptionPane.showMessageDialog(Main.this,"Support count must be numeric only\nDefault support count 2 will be used");
					support = 2;
				}
				p.readDataset(file.getPath(),dtm,support);
				JOptionPane.showMessageDialog(Main.this,"Dataset Reading process completed"); 
			}
		}
	});

	b2 = new JButton("Painting Growth");
	b2.setFont(f2);
	p4.add(b2);
	b2.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			long start = System.currentTimeMillis();
			p.permuteSet();
			p.frequent();
			long end = System.currentTimeMillis();
			ptime = end-start;
			p.print();
		}
	});

	b3 = new JButton("Not Painting Growth");
	b3.setFont(f2);
	p4.add(b3);
	b3.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			long start = System.currentTimeMillis();
			clearTable();
			np.readDataset(file.getPath(),dtm,support);
			np.permuteSet();
			np.frequent();
			np.print();
			long end = System.currentTimeMillis();
			ntime = end-start;
		}
	});

	
	b6 = new JButton("FP Growth");
	b6.setFont(f2);
	p4.add(b6);
	b6.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			long start = System.currentTimeMillis();
			ShowTable st = new ShowTable();
			ReadTransactionFile dataset_reader = new ReadTransactionFile();
			dataset_reader.readTransaction(file.getPath());
			FPGrowth fpg = new FPGrowth(dataset_reader);
			TransactionSet sets = fpg.frequentPatterns(support);
			sets.showFrequentCandidates(dataset_reader.size(),st.dtm);
			st.setVisible(true);
			st.setSize(600,400);
			long end = System.currentTimeMillis();
			fptime = end-start;
		}
	});

	

	b4 = new JButton("Comparision Chart");
	b4.setFont(f2);
	p4.add(b4);
	b4.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			Chart chart1 = new Chart("Execution Time Chart",ptime,ntime,fptime,dtm.getRowCount());
			chart1.pack();
			RefineryUtilities.centerFrameOnScreen(chart1);
			chart1.setVisible(true);
		}
	});

	b5 = new JButton("Exit");
	b5.setFont(f2);
	p4.add(b5);
	b5.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			System.exit(0);
		}
	});

	l2 = new JLabel("");
	l2.setFont(f2);
	p4.add(l2);
	l2.setText("<html><body<center>Not Painting Execution Time : <br/>Painting Execution Time : <br/>FP Growth Execution Time : ");
	

	p5 = new JPanel();
	p5.setLayout(new BorderLayout());
	dtm = new DefaultTableModel(){
		public boolean isCellEditable(int r,int c){
			return false;
		}
	};
	table = new JTable(dtm);
	table.setRowHeight(30);
	//table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	table.setFont(f2);
	table.getTableHeader().setFont(f2);

	jsp = new JScrollPane(table);
	dtm.addColumn("Serial No");
	dtm.addColumn("Transaction");
	
	p1.add(p2,BorderLayout.NORTH);
	p1.add(p3,BorderLayout.CENTER);
	getContentPane().add(p1,BorderLayout.NORTH);
	getContentPane().add(jsp,BorderLayout.CENTER);
}
public static void main(String a[])throws Exception{
	UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	Main lp = new Main();
	lp.setVisible(true);
	lp.setExtendedState(JFrame.MAXIMIZED_BOTH);
}
public void clearTable(){
	for(int i=table.getRowCount()-1;i>=0;i--){
		dtm.removeRow(i);
	}
}
}