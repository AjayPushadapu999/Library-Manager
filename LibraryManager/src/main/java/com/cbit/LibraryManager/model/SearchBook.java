package com.cbit.LibraryManager.model;

public class SearchBook {
	private int bid;

	public int getBid() {
		return bid;
	}

	public void setBid(int bid) {
		this.bid = bid;
	}

	public SearchBook(int bid) {
		super();
		this.bid = bid;
	}

	@Override
	public String toString() {
		return "SearchBook [bid=" + bid + "]";
	}

	public SearchBook() {
		super();
		// TODO Auto-generated constructor stub
	}
}
