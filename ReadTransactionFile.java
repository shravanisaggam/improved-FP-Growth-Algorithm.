package com;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashSet;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
public class ReadTransactionFile {
	HashSet<String> attributes = new HashSet<String>();
	ArrayList<Transaction> transaction = new ArrayList<Transaction>();
public void readTransaction(String file_path){
	try{
		BufferedReader br = new BufferedReader(new FileReader(file_path));
		String line = null;
		while((line = br.readLine())!=null){
			String items[] = line.split(",");
			Transaction obj = new Transaction();
			for(String str : items){
				obj.addItem(str.trim());
				attributes.add(str.trim());
			}
			transaction.add(obj);
		}
		br.close();
	}catch(Exception e){
		e.printStackTrace();
	}
}
public int size(){
	return transaction.size();
}
public ArrayList<Transaction> getTransaction() {
	return transaction;
}
public HashSet<String> getAttributes() {
	return attributes;
}
}