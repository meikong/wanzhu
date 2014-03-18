package com.wanzhu.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.wanzhu.controller.admin.AdminHomePageSettingController;

/**
 * 分页对象. 包含当前页数据及分页信息如总记录数.
 *
 */
public class Page<T> implements Serializable {

	private static final long serialVersionUID = -6238953545160113978L;

	public static final int DEFAULT_PAGE_SIZE = 15; 
	public static final int ADMIN_COMMON_PAGE_SIZE = 15; //一般条数，后台管理除活动管理外应用这个
	public static final int ADMIN_EVENT_PAGE_SIZE = 10;  //后台管理活动列表，每页活动个数
	public static final int ADMIN_MAX_PAGE_SIZE = 20;
	
	public static final int ADMIN_RECOMMEND_EVENT_SIZE = 5; // 轮播活动数
	public static final int ADMIN_EVENT_SIZE = 10; // 轮播活动数 + 非推荐
	public static final int ADMIN_RECOMMEND_VIDEO_SIZE = 6; // 推荐视频数
	public static final int ADMIN_RECOMMEND_WORDS_SIZE = 5; // 推荐文章数

	private int pageSize = DEFAULT_PAGE_SIZE; // 每页的记录数
	
	private long start; // 当前页第一条数据在List中的位置,从0开始

	private List<T> result; // 当前页中存放的记录,类型一般为List

	private long totalCount; // 总记录数
	
	private int pageNo=1;//要跳转到的页码
	
	public void setResult(List<T> result) {
		this.result = result;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 * 构造方法，只构造空页.
	 */
	public Page() {
		this(0, 0, DEFAULT_PAGE_SIZE, new ArrayList<T>());
	}

	/**
	 * 默认构造方法.
	 *
	 * @param start	 本页数据在数据库中的起始位置
	 * @param totalSize 数据库中总记录条数
	 * @param pageSize  本页容量
	 * @param data	  本页包含的数据
	 */
	public Page(long start, long totalSize, int pageSize, List<T> result) {
		this.pageSize = pageSize;
		this.start = start;
		this.totalCount = totalSize;
		this.result = result;
	}

	/**
	 * 取总记录数.
	 */
	public long getTotalCount() {
		return this.totalCount;
	}

	/**
	 * 取总页数.
	 */
	public long getTotalPageCount() {
		if (totalCount % pageSize == 0)
			return totalCount / pageSize;
		else
			return totalCount / pageSize + 1;
	}
	
	/**
	 * 
	 * @return
	 * @Date:2013-4-16  
	 * @Author:Guibin Zhang  
	 * @Description:
	 */
	public long getTotalPageCountForRecommend() { 
		if(pageNo == 1){
			long num = Page.ADMIN_EVENT_SIZE - AdminHomePageSettingController.RecommendationCount;//第一页的数量
			long totalCountOfOtherPage = this.totalCount - num;
			if (totalCountOfOtherPage % pageSize == 0){
				return totalCountOfOtherPage / pageSize + 1;
			}else{
				return totalCountOfOtherPage / pageSize + 2;
			}
		}else{
			return getTotalPageCount();  
		}
	}

	/**
	 * 取每页数据容量.
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * 取当前页中的记录.
	 */
	public List<T> getResult() {
		return result;
	}

	/**
	 * 取该页当前页码,页码从1开始.
	 */
	public long getCurrentPageNo() {
		return start / pageSize + 1;
	}

	/**
	 * 该页是否有下一页.
	 */
	public boolean isHasNextPage() {
		return this.getCurrentPageNo() < this.getTotalPageCount();
	}

	/**
	 * 该页是否有上一页.
	 */
	public boolean isHasPreviousPage() {
		return this.getCurrentPageNo() > 1;
	}

	/**
	 * 获取任一页第一条数据在数据集的位置，每页条数使用默认值.
	 *
	 * @see #getStartOfPage(int,int)
	 */
	protected static int getStartOfPage(int pageNo) {
		return getStartOfPage(pageNo, DEFAULT_PAGE_SIZE);
	}

	/**
	 * 获取任一页第一条数据在数据集的位置.
	 *
	 * @param pageNo   从1开始的页号
	 * @param pageSize 每页记录条数
	 * @return 该页第一条数据
	 */
	public static int getStartOfPage(int pageNo, int pageSize) {
		return (pageNo - 1) * pageSize;
	}

	public long getStart() {
		return start;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public void setStart(long start) {
		this.start = start;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
		this.setStart(this.getStartOfPage(this.pageNo, this.pageSize));
	}

}