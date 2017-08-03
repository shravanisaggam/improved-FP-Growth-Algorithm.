package com;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
public class TransactionSet {
	ArrayList<ArrayList<Transaction>> candidates = new ArrayList<ArrayList<Transaction>>();  // itemset classé par taille
	int count = 0;
public void clear(){
	candidates.clear();
}
public TransactionSet(){
	candidates.add(new ArrayList<Transaction>()); 
}
//call to show all frequent candidate set
public void showFrequentCandidates(int obj,DefaultTableModel dtm){
	int list = 0;
	for(ArrayList<Transaction> candidate : candidates){
		if(list > 0){
			String row[] = {"Candidate List : "+list};
			dtm.addRow(row);
		}
		for(Transaction item : candidate){
			String row1[]={item.toString(),item.getSupport(obj)};
			dtm.addRow(row1);
		}
		list = list + 1;
	}
}
//create candidate new list if size can fit into memory
//if not swith to apriori by scanning get function
public void addTransaction(Transaction tr,int size){
	//check if it can fit in memory
	while(candidates.size() <= size){
		candidates.add(new ArrayList<Transaction>());
	}
	candidates.get(size).add(tr);
	count = count + 1;
}
//returns candidates list 
public ArrayList<ArrayList<Transaction>> getCandidates() {
	return candidates;
}
//return transaction count
public int getTransactionCount() {
	return count;
}
}