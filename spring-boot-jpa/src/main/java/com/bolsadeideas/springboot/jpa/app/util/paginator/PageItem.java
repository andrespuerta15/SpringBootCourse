package com.bolsadeideas.springboot.jpa.app.util.paginator;

public class PageItem {

	private int pageNumber;
	private boolean actualPage;

	public PageItem(int pageNumber, boolean actualPage) {
		this.pageNumber = pageNumber;
		this.actualPage = actualPage;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public boolean isActualPage() {
		return actualPage;
	}
}
