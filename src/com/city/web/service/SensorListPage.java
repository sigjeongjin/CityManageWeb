package com.city.web.service;

import java.util.List;

import com.city.model.LocationManagement;

public class SensorListPage {

	private int total;
	private int currentPage;
	private List<LocationManagement> content;
	private int totalPages;
	private int startPage;
	private int endPage;

	public SensorListPage(int total, int currentPage, int size, List<LocationManagement> content) {
		this.total = total;
		this.currentPage = currentPage;
		this.content = content;
		if (total == 0) {
			totalPages = 0;
			startPage = 0;
			endPage = 0;
		} else {
			totalPages = total / size;
			if (total % size > 0) {
				totalPages++;
			}
			int modVal = currentPage % 5;
			startPage = currentPage / 5 * 5 + 1;
			if (modVal == 0) startPage -= 5;
			
			endPage = startPage + 4;
			if (endPage > totalPages) endPage = totalPages;
		}
	}

	public int getTotal() {
		return total;
	}

	public boolean hasNoSensors() {
		return total == 0;
	}
	
	public boolean hasSensors() {
		return total > 0;
	}
	
	public int getCurrentPage() {
		return currentPage;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public List<LocationManagement> getContent() {
		return content;
	}

	public int getStartPage() {
		return startPage;
	}
	
	public int getEndPage() {
		return endPage;
	}
}
