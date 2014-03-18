 /**  
 *@Description:     
 */ 
package com.wanzhu.utils;  

import java.util.ArrayList;
  
public class RecommendResult<T> {
	public boolean hasNext;
	public ArrayList<T> result;
	
	public RecommendResult(boolean hasNext, ArrayList<T> result) {
		super();
		this.hasNext = hasNext;
		this.result = result;
	}

	public RecommendResult() {
		super();  
		// TODO Auto-generated constructor stub
	}

	public boolean isHasNext() {
		return hasNext;
	}

	public void setHasNext(boolean hasNext) {
		this.hasNext = hasNext;
	}

	public ArrayList<T> getResult() {
		return result;
	}

	public void setResult(ArrayList<T> result) {
		this.result = result;
	}
	
	
}
