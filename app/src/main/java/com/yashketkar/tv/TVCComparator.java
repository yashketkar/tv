package com.yashketkar.tv;

import java.util.Comparator;

public class TVCComparator implements Comparator<TVC> {
	public int compare(TVC c1, TVC c2) {
		return c1.name.compareTo(c2.name);
	}
}
