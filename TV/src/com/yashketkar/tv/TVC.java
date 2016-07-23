package com.yashketkar.tv;

public class TVC {

public	String[] keys;
public	String[] values;
public String name;
public String path;
public int imageid;

	TVC() {
	}

	TVC(String name, String path, int imageid) {
		this.name = name;
		this.path = path;
		this.imageid = imageid;
	}

	TVC(String name, String path, int imageid, String[] keys, String[] values) {
		this.name = name;
		this.path = path;
		this.imageid = imageid;
		this.keys = keys;
		this.values = values;
	}
}