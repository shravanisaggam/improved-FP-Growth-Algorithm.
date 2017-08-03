package com;
import java.util.ArrayList;
import java.util.HashSet;
public class Transaction{
	ArrayList<String> item_list = new ArrayList<String>(); // ordered
	HashSet<Integer> tid = new HashSet<Integer>();
public double getRelativeSupport(int nbObject) {
	return ((double)tid.size()) / ((double) nbObject);
}
public String getSupport(int obj) {
	return ""+tid.size();
}
//instead of scanning every time from database support count obtain from candidate
//tid (transaction id)
public int getSupportCount(){
	return tid.size();
}
public void addItem(String item){
	item_list.add(item);
}
public ArrayList<String> getItemList(){
	return item_list;
}
public String getItemFrom(int index){
	return item_list.get(index);
}
public void setCount(HashSet<Integer> id) {
	tid = id;
}
public int getListSize(){
	return item_list.size();
}
public HashSet<Integer> getCount() {
	return tid;
}
public String toString(){
	StringBuffer r = new StringBuffer();
	for(String attribute : item_list){
		r.append(attribute.toString());
		//r.append();
	}
	return r.toString();
}
}