package com;
import java.util.Hashtable;
import java.util.ArrayList;
public class Child{
	Hashtable<String,Integer> child =  new Hashtable<String,Integer>();
	int parent_count;
	String parent;
public void setParent(String parent){
	this.parent = parent;
}
public String getParent(){
	return parent;
}
public void addChild(String item){
	child.put(item,(child.containsKey(item) ? 1 + child.get(item) : 1));
}
public Hashtable<String,Integer> getChild(){
	return child;
}
public void setParentCount(int parent_count){
	this.parent_count = parent_count;
}
public int getParentCount(){
	return parent_count;
}
}