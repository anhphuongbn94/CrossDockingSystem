package com.cds.entity;

import java.util.ArrayList;
import java.util.List;

public class Pager {
	public int totalItem;
	public int sizePage;
	public int numPage;
	public int currentPage;
	public List<Integer> listPage;
	
	public Pager() {
		super();
	}
	public int getTotalItem() {
		return totalItem;
	}
	public void setTotalItem(int totalItem) {
		this.totalItem = totalItem;
	}
	public int getSizePage() {
		return sizePage;
	}
	public void setSizePage(int sizePage) {
		this.sizePage = sizePage;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public void setNumPage(int numPage) {
		this.numPage = numPage;
	}
	public int getNumPage() {
		return numPage;
	}
	public List<Integer> getListPage() {
		return listPage;
	}
	public void initPage(int size, int total, int numPageShow, int currentPage){
		totalItem = total;
		sizePage = size;
		numPage=totalItem/sizePage + 1;
		if(totalItem%sizePage == 0) {
			numPage = totalItem/sizePage;
		}
		if(numPage < numPageShow){
			for(int i=0; i<numPage; i++){
				listPage.add(i+1);
			}
		}else{
			int sPage=1;
			int ePage=numPageShow;
			if(currentPage - 1 + numPageShow > numPage){
				sPage = numPage-numPageShow + 1;
				ePage = numPage;
			}else{
				sPage = currentPage;
				ePage = numPageShow + currentPage - 1;
			}
			for(int i=sPage; i<=ePage; i++){
				listPage.add(i);
			}
		}
	}
	public void initPage(int size, int total, int currentPage){
		int numPageShow = 7;
		totalItem = total;
		sizePage = size;
		numPage=totalItem/sizePage + 1;
		listPage = new ArrayList<Integer>();
		if(totalItem%sizePage == 0) {
			numPage = totalItem/sizePage;
		}
		if(numPage < numPageShow){
			for(int i=1; i<=numPage; i++){
				listPage.add(i);
			}
		}else{
			int sPage=1;
			int ePage=numPageShow;
			if(currentPage - 1 + numPageShow > numPage){
				sPage = numPage-numPageShow + 1;
				ePage = numPage;
			}else{
				sPage = currentPage;
				ePage = numPageShow + currentPage - 1;
			}
			for(int i=sPage; i<=ePage; i++){
				listPage.add(i);
			}
		}
	}
	public static void main(String[] args) {
		Pager p=new Pager();
		p.initPage(15, 130, 4);
		System.out.println(p.getNumPage());
		for(int i=0; i<p.getListPage().size(); i++){
			System.out.print(p.getListPage().get(i) + "-" );
		}
	}
}
