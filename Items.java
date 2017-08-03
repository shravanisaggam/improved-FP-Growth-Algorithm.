package com;
import java.util.Comparator;
public class Items implements Comparator<Items>{
	String items[];
	int length;
public void setItem(String items[]){
	this.items = items;
}
public String[] getItem(){
	return items;
}
public void setLength(int length){
	this.length = length;
}
public int getLength(){
	return length;
}
public int compare(Items p1,Items p2){
	double s1 = p1.getLength();
    double s2 = p2.getLength();
	if (s1 == s2)
		return 0;
    else if (s1 > s2)
    	return 1;
    else
		return -1;
}
}