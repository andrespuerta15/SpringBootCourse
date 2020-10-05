package com.bolsadeideas.springboot.jpa.app.util.paginator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;

public class PageRender<T> {

	private String url;
	private Page<T> page;
	private int totalPages;
	private int elementsPerPage;
	private int actualPage;
	private List<PageItem> lPages;

	public PageRender(String url, Page<T> page) {
		this.url = url;
		this.page = page;
		this.elementsPerPage = page.getSize();
		this.totalPages = page.getTotalPages();
		this.actualPage = page.getNumber() + 1;
		construirPaginador();
	}

	private void construirPaginador() {
		int initialPage;
		int finalPage;
		lPages = new ArrayList<PageItem>();

		if (totalPages <= elementsPerPage) {
			initialPage = 1;
			finalPage = totalPages;
		} else {
			if (actualPage <= (elementsPerPage / 2)) {
				initialPage = 1;
				finalPage = elementsPerPage;
			} else if (actualPage >= (totalPages - (elementsPerPage / 2))) {
				initialPage = (totalPages - elementsPerPage) + 1;
				finalPage = elementsPerPage;
			} else {
				initialPage = actualPage - (elementsPerPage / 2);
				finalPage = elementsPerPage;
			}
		}

		for (int i = 0; i < finalPage; i++) {
			lPages.add(new PageItem(initialPage + i, actualPage == (initialPage + i)));
		}
	}

	public String getUrl() {
		return url;
	}

	public Page<T> getPage() {
		return page;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public List<PageItem> getlPages() {
		return lPages;
	}

	public int getActualPage() {
		return this.actualPage;
	}

	public boolean isFirst() {
		return page.isFirst();
	}

	public boolean isLast() {
		return page.isLast();
	}

	public boolean isHasNext() {
		return page.hasNext();
	}

	public boolean isHasPrevious() {
		return page.hasPrevious();
	}

}
