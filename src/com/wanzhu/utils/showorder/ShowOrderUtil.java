 /**  
 *@Description:     
 */ 
package com.wanzhu.utils.showorder;  

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
  
public class ShowOrderUtil {
	/**
	 * 原始顺序  0 100 200 300 400 500 600 700 800 900
	 * 新顺序       100 200 0 400 500 600 900 700 800 300
	 */
	//static int top = 0;
	//static int bottom = 0;
	
	
	//private 
	
	/**
	 * 数组字符串转List
	 * @param strJsonArray
	 * @return
	 * @Date:2013-4-18  
	 * @Author:Guibin Zhang  
	 * @Description:
	 */
	public static List<Order> formatToList(String strJsonArray){
		JSONArray jsonArray = JSONArray.fromObject(strJsonArray);
		List<Order> result = JSONArray.toList(jsonArray, Order.class);
		return result;
	}
	
	public static List<Order> formatToList(Object jsonArrayObject){
		JSONArray jsonArray = JSONArray.fromObject(jsonArrayObject);
		List<Order> result = JSONArray.toList(jsonArray, Order.class);
		return result;
	}
	
	/**
	 * 以二维数组的方式转化原list
	 * @param oldList 
	 * @return
	 * @Date:2013-4-18  
	 * @Author:Guibin Zhang  
	 * @Description:
	 */
	private static List<List<Order>> getOrderedSubLists(List<Order> oldList){
		List<List<Order>> result = new ArrayList<List<Order>>();
		int start = 0;
		int end = 1;
		for (int i = 0; i < oldList.size()-1; i++) {
			if(oldList.get(i).compareTo(oldList.get(i+1)) <= 0){//如果升序
				end ++;
			}else{
				List<Order> sub = oldList.subList(start, end);
				result.add(new ArrayList<Order>(sub));
				start = end;
				end ++;
			}
		}
		return result;
	}
	
	/**
	 * 得到需要更改showorder的记录
	 * @param orderedLists
	 * @return
	 * @Date:2013-4-18  
	 * @Author:Guibin Zhang  
	 * @Description:
	 */
	public List<Order> getNeedUpdates(List<Order> oldList){
		List<Order> needs = new ArrayList<Order>();
		List<List<Order>> orderedLists = getOrderedSubLists(oldList);
		int maxSubListIndex = 0;//最大升序子串
		double max = Double.MIN_VALUE;
		double min = Double.MAX_VALUE;	
		for (int i = 0; i < orderedLists.size(); i++) {
			if(maxSubListIndex < orderedLists.get(i).size()){
				maxSubListIndex = i;
			}
		}
		for (int i = 0; i < oldList.size()-1; i++) {
			if(oldList.get(i).order < oldList.get(i+1).order){
				max = oldList.get(i+1).order;
			}
			if(oldList.get(i).order > oldList.get(i+1).order){
				min = oldList.get(i+1).order;
			}
		}
		if(min == orderedLists.get(maxSubListIndex).get(0).order){//最长串的最小值
			
		}
		if(max == orderedLists.get(maxSubListIndex).get(orderedLists.get(maxSubListIndex).size()-1).order){//最长串的最大值
		
		}
		return null;
	}
	
	/**
	 * 
	 * @param max
	 * @return
	 * @Date:2013-4-18  
	 * @Author:Guibin Zhang  
	 * @Description:
	 */
	private List<Order> needUpdate(List<Order> oldList, int max){
		int top = 0;
		int bottom = 0;
		for (int i = 0; i < oldList.size(); i++) {
			
		}
		//找到左边需要更改排序的串
//		if(max != 0){
//			for (int i = max - 1; i < max; i++) {//左边
//				if(orderedLists.get(i).size() == 1){//就一个元素
//					needs.add(orderedLists.get(i).get(0));//加到要更改的数组
//					continue;
//				}
//				int loop = orderedLists.get(i).size();
//				for (int j = 0; j < loop; j++) {
//					
//				}
//				
//			}
//		}
//		if(max != orderedLists.size()-1){
//			for (int i = max + 1; i < orderedLists.size(); i++) {//右边
//				orderedLists
//			}
//		}
		return null;
	}

	private static void print(List<List<Order>> lists){
		for (List<Order> arrayList : lists) {
			System.out.println(arrayList);
		}
	}
	
	public static void main(String[] args) {
//		String strJsonArray = "[{id:'uuid1100', order:1100}, {id:'uuid100', order:100}, {id:'uuid200', order:200}, {id:'uuid1300', order:1300}, {id:'uuid1200', order:1200}, {id:'uuid000', order:0}, {id:'uuid400', order:400}, {id:'uuid500', order:500}, {id:'uuid600', order:600}, {id:'uuid1000', order:1000}, {id:'uuid900', order:900}, {id:'uuid700', order:700}, {id:'uuid5800', order:800}, {id:'uuid300', order:300}]";
//		List<Order> oldList = formatToList(strJsonArray);
//		List<List<Order>> lists = getOrderedSubLists(oldList);
//		print(lists);
		
		System.out.println(System.currentTimeMillis());
		
	}
}
