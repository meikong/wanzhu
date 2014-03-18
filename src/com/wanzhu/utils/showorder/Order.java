 /**  
 *@Description:     
 */ 
package com.wanzhu.utils.showorder;  

import java.util.Comparator;
  
public class Order implements Comparator<Order>, Comparable<Order>{
	public String id;
	public long order;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public long getOrder() {
		return order;
	}
	public void setOrder(long order) {
		this.order = order;
	}
	@Override
	public int compare(Order o1, Order o2) {
		if(o1.order > o2.order)
			return 1;
		if(o1.order < o2.order)
			return -1;
		else 
			return o1.id.compareTo(o2.id);
	}
	@Override
	public int compareTo(Order o) {
		if(this.order > o.order)
			return 1;
		if(this.order < o.order)
			return -1;
		else 
			return this.id.compareTo(o.id);
	}
	@Override
	public String toString() {
		return "Order [id=" + id + ", order=" + order + "]";
	}
	
	
	
	
}
