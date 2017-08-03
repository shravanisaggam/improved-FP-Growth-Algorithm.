package com;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collections;
import java.util.Comparator;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;
import java.util.Map;
import java.util.HashSet;
import javax.swing.table.DefaultTableModel;
public class NPainting {
	ArrayList<String> output = new ArrayList<String>();
	ArrayList<Items> transaction = new ArrayList<Items>();
	ArrayList<String[]> permute = new ArrayList<String[]>();
	HashMap<String,HashMap<String,Integer>> map = new HashMap<String,HashMap<String,Integer>>();
	HashMap<String,Integer> frequent = new HashMap<String,Integer>();
	HashSet<String> peak = new HashSet<String>();
	int sp,support;
public boolean compareItems(String[] arr1) {
	boolean flag = false;
    HashSet<String> set1 = new HashSet<String>(Arrays.asList(arr1));
	for(Map.Entry<String,Integer> temp : frequent.entrySet()){
		String key = temp.getKey();
		HashSet<String> set2 = new HashSet<String>(Arrays.asList(key.split("\\s+")));
		if(set1.equals(set2)){
			flag = true;
			break;
		}
	}
	return flag;
}
public void readDataset(String file,DefaultTableModel dtm,int sup){
	try{
		int i = 1;
		support = sup;
		transaction.clear();
		permute.clear();
		map.clear();
		frequent.clear();
		output.clear();
		peak.clear();
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line = null;
		while((line = br.readLine()) != null){
			line = line.trim();
			if(line.length() > 0){
				Object row[] = {i,line};
				dtm.addRow(row);
				i = i + 1;
				String items[] = line.split(",");
				Items tr = new Items();
				tr.setItem(items);
				tr.setLength(items.length);
				transaction.add(tr);
				for(String str : items)
					peak.add(str);
			}
		}
		br.close();
	}catch(Exception e){
		e.printStackTrace();
	}
}
public void permuteSet(){
	String peak_items[] = peak.toArray(new String[peak.size()]);
	for(int i=0;i<transaction.size();i++){
		Items tr = transaction.get(i);
		String items[] = tr.getItem();
		for(int j=0;j<items.length;j++){
			String first = items[j];
			for(int k=0;k<items.length;k++){
				if(j != k){
					String second = items[k];
					String arr[] = {first,second};
					permute.add(arr);
				}
			}
		}
	}
	for(int i=0;i<peak_items.length;i++){
		String peak = peak_items[i];
		HashMap<String,Integer> list = map(peak);
		map.put(peak,list);
	}
	for(Map.Entry<String,HashMap<String,Integer>> me : map.entrySet()){
		String key = me.getKey();
		HashMap<String,Integer> value = me.getValue();
		Set set = value.entrySet();
		Iterator itr = set.iterator();
		while(itr.hasNext()){
			Map.Entry<String,Integer> temp = (Map.Entry<String,Integer>)itr.next();
			if(temp.getValue() < support)
				itr.remove();
		}
	}
	for(Map.Entry<String,HashMap<String,Integer>> me : map.entrySet()){
		String key = me.getKey();
		HashMap<String,Integer> value = me.getValue();
		for(Map.Entry<String,Integer> temp : value.entrySet()){
			String arr = key+" "+temp.getKey();
			boolean flag = compareItems(arr.split("\\s+"));
			if(!flag){
				frequent.put(key+" "+temp.getKey(),temp.getValue());
				output.add(key+" "+temp.getKey()+","+temp.getValue());
			}
		}
	}
}
public String getPattern(String item[],String val){
	String data = "none";
	int count = 0;
	sp = 0;
	for(Map.Entry<String,HashMap<String,Integer>> me : map.entrySet()){
		String key = me.getKey();
		HashMap<String,Integer> value = me.getValue();
		ArrayList<Integer> scount = new ArrayList<Integer>();
		for(int j=0;j<item.length;j++){
			scount.add(value.get(item[j]));
			if(value.containsKey(item[j])){
				count = count + 1;
			}
		}
		if(count == item.length){
			val = key+" "+val;
			if(!frequent.containsKey(val)){
				Collections.sort(scount);
				sp = scount.get(0);
				data = key;
				break;
			}
		}
		count = 0;
	}
	return data;
}	

public void frequent(){
	int freq = 2;
	boolean run = true;
	while(run){
		boolean stop = false;
		HashMap<String,Integer> fr = new HashMap<String,Integer>(frequent);
		for(Map.Entry<String,Integer> temp : fr.entrySet()){
			String arr[] = temp.getKey().split("\\s+");
			if(arr.length == freq){
				String data = getPattern(arr,temp.getKey());
				if(!data.equals("none")){
					String ar = temp.getKey()+" "+data;
					boolean flag = compareItems(ar.split("\\s+"));
					if(!flag){
						ar = sort(ar.split("\\s+"));
						frequent.put(ar,sp);
						output.add(ar+","+sp);
						stop = true;
					}
				}
			}
		}
		freq = freq + 1;
		if(!stop){
			run = false;
			break;
		}
	}
}
public void print(){
	AssociationRule ar = new AssociationRule();
	ar.setVisible(true);
	ar.setSize(400,400);
	for(Map.Entry<String,HashMap<String,Integer>> me : map.entrySet()){
		String key = me.getKey();
		HashMap<String,Integer> value = me.getValue();
		StringBuilder sb = new StringBuilder();
		sb.append(key+"(");
		for(Map.Entry<String,Integer> temp : value.entrySet()){
			sb.append(temp.getKey()+":"+temp.getValue()+" ");
		}
		sb.append(")");
		Object row[] = {sb.toString().trim()};
		ar.dtm.addRow(row);
	}
	FrequentItems fi = new FrequentItems();
	fi.setVisible(true);
	fi.setSize(400,500);
	for(String str : output){
		String row[] = str.split(",");
		fi.dtm.addRow(row);
	}
}
public String remove(String arr1[],String peak){
	StringBuilder sb = new StringBuilder();
	for(int i=0;i<arr1.length;i++){
		if(!arr1[i].equals(peak))
			sb.append(arr1[i]);
	}
	return sb.toString();
}
public HashMap<String,Integer> map(String peak){
	HashMap<String,Integer> list = new HashMap<String,Integer>();
	String arr[] = peak.split(",");
	for(int i=0;i<permute.size();i++){
		String item_set[] = permute.get(i);
		if(item_set[0].equals(arr[0])){
			String str = remove(item_set,peak);
			list.put(str,(list.containsKey(str) ? 1 + list.get(str) : 1));
		}
	}
	return list;
}
public String sort(String arr[]){
	Arrays.sort(arr);
	StringBuilder sb = new StringBuilder();
	for(String str : arr){
		sb.append(str+" ");
	}
	return sb.toString().trim();
}
}