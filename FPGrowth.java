package com;
import java.util.HashMap;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Comparator;
import java.util.Collections;
import javax.swing.table.DefaultTableModel;
public class FPGrowth {
	TransactionSet tsets = new TransactionSet();
	ReadTransactionFile reader;
	int k;
	// map items with count value
	HashMap<String,HashSet<Integer>> items_mapping = new HashMap<String,HashSet<Integer>>();
	int support_value;
	int max_item_size = Integer.MAX_VALUE;
public FPGrowth(ReadTransactionFile reader) {
	this.reader = reader;
}
public TransactionSet frequentPatterns(int support_value) {
	tsets.clear();
	this.support_value = support_value;
	items_mapping = new HashMap<String,HashSet<Integer>>();
	for(int i=0;i<reader.getTransaction().size();i++){
		Transaction transaction = reader.getTransaction().get(i);
		for(int j=0;j<transaction.getListSize();j++){
			HashSet<Integer> id = items_mapping.get(transaction.getItemFrom(j));
			if(id == null){
				id = new HashSet<Integer>();
				items_mapping.put(transaction.getItemFrom(j),id);
			}
			id.add(i);
		}
	}
	// scan database only single time to calculate the support of each candidate.
	k=1;
	ArrayList<Transaction> candidates = new ArrayList<Transaction>();
	Iterator<Map.Entry<String,HashSet<Integer>>> iterator = items_mapping.entrySet().iterator();
	while (iterator.hasNext()) {
		Map.Entry<String,HashSet<Integer>> entry = (Map.Entry<String,HashSet<Integer>>) iterator.next();
		if(entry.getValue().size() >= support_value){ 
			String item = entry.getKey();
			Transaction itemset = new Transaction();
			itemset.addItem(item);
			itemset.setCount(items_mapping.get(item));
			candidates.add(itemset);
			tsets.addTransaction(itemset,1);
		}else{
			iterator.remove();  // if the item is not frequent we don't need to keep it into memory.
		}
	}
	Collections.sort(candidates, new Comparator<Transaction>(){
		public int compare(Transaction o1, Transaction o2) {
			return o1.getItemFrom(0).compareTo(o2.getItemFrom(0));
		}
	});
	k = 2;
	while(!candidates.isEmpty()  && k <= max_item_size) {
		candidates = fptree(candidates); // We keep only the last level... 
		k++;
	}
	return tsets; // Return all frequent itemsets found!
}
public ArrayList<Transaction> generateCandidateSize1() {
	ArrayList<Transaction> candidates = new ArrayList<Transaction>(); 
	for (String item : reader.getAttributes()) {
		Transaction itemset = new Transaction();
		itemset.addItem(item);
		candidates.add(itemset);
	}
	return candidates;
}
public ArrayList<Transaction> fptree(ArrayList<Transaction> levelK_1) {
	ArrayList<Transaction> candidates = new ArrayList<Transaction>();
	loop1:for(int i=0; i< levelK_1.size(); i++){
			Transaction itemset1 = levelK_1.get(i);
			loop2:for(int j=i+1; j< levelK_1.size(); j++){
				Transaction itemset2 = levelK_1.get(j);
				for(int k=0;k<itemset1.getListSize();k++){
					if(k == itemset1.getListSize()-1){ 
						if(itemset1.getItemList().get(k).compareTo(itemset2.getItemFrom(k)) >= 0){  
							continue loop1;
						}
					}
					else if(itemset1.getItemList().get(k).compareTo(itemset2.getItemFrom(k)) < 0){ 
						continue loop2; // we continue searching
					}
					else if(itemset1.getItemList().get(k).compareTo(itemset2.getItemFrom(k)) > 0){ 
						continue loop1;  // we stop searching:  because of lexical order
					}
				}
				String missing = itemset2.getItemFrom(itemset2.getListSize()-1);
				HashSet<Integer> list = new HashSet<Integer>();
				for(Integer val1 : itemset1.getCount()){
					if(itemset2.getCount().contains(val1)){
						list.add(val1);
					}
				}
				if(list.size() >= support_value){
					Transaction candidate = new Transaction();
					for(int k=0;k<itemset1.getListSize();k++){
						candidate.addItem(itemset1.getItemFrom(k));
					}
					candidate.addItem(missing);
					candidate.setCount(list);
					candidates.add(candidate);
					tsets.addTransaction(candidate, k);
				}
			}
	}
	return candidates;
}
public void setMaxItemsetSize(int max_item_size) {
	this.max_item_size = max_item_size;
}
public TransactionSet getItemsets() {
	return tsets;
}



}