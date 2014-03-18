package com.wanzhu.controller.admin;



import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.wanzhu.base.BaseController;
import com.wanzhu.base.CommonConstant;
import com.wanzhu.base.Page;
import com.wanzhu.base.SystemBuffer;
import com.wanzhu.entity.Code;
import com.wanzhu.entity.Event;
import com.wanzhu.entity.EventLabel;
import com.wanzhu.entity.Lectrue;
import com.wanzhu.entity.Notification;
import com.wanzhu.entity.Summary;
import com.wanzhu.entity.User;
import com.wanzhu.entity.UserEvent;
import com.wanzhu.entity.WorkExperience;
import com.wanzhu.jsonvo.SummaryVo;
import com.wanzhu.service.admin.AdminEventService;
import com.wanzhu.task.MailTask;
import com.wanzhu.task.MailThread;
import com.wanzhu.utils.EscapeHtml;
import com.wanzhu.utils.StringUtils;
import com.wanzhu.utils.TemplateHelper;

@Controller
@RequestMapping("/eventadmin")
public class AdminEventController extends BaseController {

	@Autowired
	private AdminEventService adminEventService;
	
	private static WritableCellFormat wcf_value;// 表格数据样式
	private static WritableCellFormat wcf_value_left;
	private static WritableCellFormat wcf_key;// 表头样式
	private static WritableCellFormat wcf_name_left;// 表名样式
	private static WritableCellFormat wcf_name_right;// 表名样式
	private static WritableCellFormat wcf_name_center;// 表名样式
	private static WritableCellFormat wcf_title;// 页名称样式
	private static WritableCellFormat wcf_percent_float;

	private static final int MAXCOLS = 7;// 表名称样式
	/**
	 * 更新活动
	 * 
	 * @return
	 */
	@RequestMapping(value = "updateEvents")
	public void queryEvents() {
		log.info("查询活动列表");
		
		try {
			
			boolean update= adminEventService.updateEvents();
			System.out.println(update);
			
		} catch (Exception e) {
			// TODO: handle exception
			log.info("查询活动列表异常");
		}
		
	}
	/**
	 * 查询活动列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "queryEvents")
	public String queryEvents(Model model, Integer pageNo, Integer pageSize,String flag, String flag2, String flag3, String beginTime,
			String endTime, String content) {
		log.info("查询活动列表");
		if (this.getSessionAdmin() == null) {
			return "redirect:/admin/index?target=_top";
		}
		try {
			if (null == pageNo) {
				pageNo = 1;
			}
			if (null == pageSize) {
				pageSize = Page.ADMIN_EVENT_PAGE_SIZE;
			}
			Page<Event> page = adminEventService.queryEventsPage(pageNo,pageSize, flag, flag2, flag3, beginTime, endTime, content);
			List<Code> listCode = adminEventService.findCode();
			model.addAttribute("page", page);
			model.addAttribute("beginTime", beginTime);
			model.addAttribute("endTime", endTime);
			model.addAttribute("content", content);
			model.addAttribute("listCode", listCode);
			model.addAttribute("flag", flag);
			model.addAttribute("flag2", flag2);
			model.addAttribute("flag3", flag3);
		} catch (Exception e) {
			// TODO: handle exception
			log.info("查询活动列表异常");
		}
		return "admin/eventList";
	}

	/**
	 * 查询活动详情
	 * 
	 * @return
	 */
	@RequestMapping(value = "queryEventInDeail")
	public String queryEventInDeail(String EventId, Model model) {
		if (this.getSessionAdmin() == null) {
			return "redirect:/admin/index?target=_top";
		}
		Event event = adminEventService.queryEvents(EventId);
		model.addAttribute("event", event);
		return "admin/queryevent";
	}

	@RequestMapping(value = "deleteEvent")
	@ResponseBody
	public int deleteEvent(String eventId) {
		if (this.getSessionAdmin() == null) {
			return 3;
		}
		try {
			adminEventService.deleteEvent(eventId);
			return 0;
		} catch (Exception e) {
			// TODO: handle exception
			return 1;
		}

	}

	@RequestMapping(value = "startSaveEvent")
	public String startSaveEvent(Model model) {
		this.getSession().removeAttribute("summeryid");
		if (this.getSessionAdmin() == null) {
			return "redirect:/admin/index?target=_top";
		}
		model.addAttribute("flag", "save");
		List<com.wanzhu.entity.Label> eventLabel = adminEventService.listEventLabel();
		model.addAttribute("eventLabel", eventLabel);
		return "admin/createEvent";
	}

	/**
	 * 
	 * @param model
	 * @param EventId
	 * @return
	 * @Date:2013-5-16  
	 * @Author:Guibin Zhang  
	 * @Description:
	 * 修改了后台标签只有系统标签供选择
	 */
	@RequestMapping(value = "startUpEvent")
	public String startUpEvent(Model model, String EventId) {
		this.getSession().removeAttribute("summeryid");
		if (this.getSessionAdmin() == null) {
			return "redirect:/admin/index?target=_top";
		}
		List<com.wanzhu.entity.Label> eventLabel = adminEventService.listEventLabel();
		Event event = adminEventService.queryEvents(EventId);

		int hours = event.getStarttime().getHours();
		int minis = event.getStarttime().getMinutes();

		int hours2 = event.getEndtime().getHours();
		int minis2 = event.getEndtime().getMinutes();

		Set<Lectrue> lectrue = event.getLectrues();
		Set<EventLabel> eventLabels = event.getEventLabels();
		Set<Summary> summary = event.getSummaries();
		model.addAttribute("hours", hours);
		model.addAttribute("minis", minis);
		model.addAttribute("hours2", hours2);
		model.addAttribute("minis2", minis2);
		model.addAttribute("lectrue", lectrue);
		model.addAttribute("eventLabels", eventLabels);
		model.addAttribute("event", event);
		model.addAttribute("eventLabel", eventLabel);
		model.addAttribute("summary", summary);
		return "admin/createEvent";
	}

	@RequestMapping(value = "saveLable")
	@ResponseBody
	public String saveLable(String labels) {
		try {
			String l=replaceBlank(labels);
			if("".equals(l))
				return "3";
			
			labels=EscapeHtml.Html2Text(labels);
			if (adminEventService.listLabelWhere(labels) != 0)
				return "2";
			
			com.wanzhu.entity.Label label = new com.wanzhu.entity.Label();
			label.setLabel(labels);
			label.setMemo(labels);
			label.setCreatetime(new Date());//创建时间
			label.setShoworder(System.currentTimeMillis());//排序
			if(this.getSessionAdmin()!=null) {
				label.setUserid(this.getSessionAdmin().getAdministratorid());//创建人id
			}
			label.setType(0);//系统标签
			label.setRecommendation(0);//不推荐
			adminEventService.saveLabel(label);
			return label.getLabelid();
		} catch (Exception e) {
			// TODO: handle exception
			return "1";
		}

	}

	public static String replaceBlank(String str) {
		        String dest = "";
		        if (str!=null) {
		            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
		            Matcher m = p.matcher(str);
		            dest = m.replaceAll("");
		        }
		        return dest;
	 }

	
	/**
	 * 编辑活动
	 * 
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "updateEvent")
	@ResponseBody
	public int updateEvent(Event event, String starttimes, String endtimes,
			String[] biaoqian, String[] jiabin) throws ParseException {
		event.setTopic(EscapeHtml.Html2Text(event.getTopic()));
		event.setSubtopic(EscapeHtml.Html2Text(event.getSubtopic()));
		event.setPlace(EscapeHtml.Html2Text(event.getPlace()));
		event.setDetail(EscapeHtml.Html2Text(event.getDetail()));
		if (this.getSessionAdmin() == null) {
			return 3;
		}
		if ("".equals(event.getEventid())) {
			event.setEventid(null);
			event.setCreatetime(new Date());
		}
		if(starttimes.indexOf("-")!=-1){
			event.setStarttime(string2Time(starttimes + ":0"));
		}
		if(endtimes.indexOf("-")!=-1){
			event.setEndtime(string2Time(endtimes + ":0"));
		}
		event.setLastmodifytime(new Date());

		boolean bool = adminEventService.saveOrUpdateEvent(event, biaoqian,	jiabin);
		if (bool) {
			String summeryid=(String) this.getSession().getAttribute("summeryid");
 			if(!StringUtils.isEmpty(summeryid)){
			    this.adminEventService.updateSummaryEvent(summeryid, event.getEventid());
				this.getSession().removeAttribute("summeryid");
			}
			return 0;
		} else
			return 1;
	}

	public final static java.sql.Timestamp string2Time(String dateString)
			throws java.text.ParseException {
		DateFormat dateFormat;
		dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);// 设定格式
		dateFormat.setLenient(false);
		java.util.Date timeDate = dateFormat.parse(dateString);
		java.sql.Timestamp dateTime = new java.sql.Timestamp(timeDate.getTime());
		return dateTime;
	}

	/**
	 * 活动会员签到
	 * 
	 * @return
	 */
	@RequestMapping(value = "signIn")
	@ResponseBody
	public int signIn(String UserEventId, String signup, String audit) {
		if (this.getSessionAdmin() == null) {
			return 3;
		}
		if (signup.equals("1")) {
			if (adminEventService.queryAudit(UserEventId)) {
				return 4;
			}
		}
		boolean bool = adminEventService
				.upUserEvent(UserEventId, signup, audit);
		if (bool)
			return 0;
		else
			return 1;
	}

	/**
	 * 报名审核 无论是否通过，均产生通知
	 * audit=1
	 * signup null
	 * @return
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "auditSignUp")
	@ResponseBody
	public int auditSignUp(String UserEventId, String signup, String audit) {
		if (this.getSessionAdmin() == null) {
			return 3;
		}
		boolean bool = adminEventService.upUserEvent(UserEventId, signup, audit);
		UserEvent ue=adminEventService.queryAudit2(UserEventId);
		Notification notification = new Notification();
		if (bool) {
			// //////////////
			// 发送通知 //
			// //////////////
			int type=1;
			boolean addflag=false;
			try {
				adminEventService.addNotification(type, ue.getUser().getUserid(), null,new  String[]{ue.getEvent().getEventid(),ue.getEvent().getTopic()});
				addflag=true;
			} catch (Exception e) {
				// TODO: handle exception
				addflag=false;
			}
			if (addflag){
					try {
						Map<String, String> pairs=null;
						StringBuffer sb=null;
						pairs=new HashMap<String, String>();
						 SimpleDateFormat df=new SimpleDateFormat("yyyy年MM月dd日 HH时:mm分");
				    	
				    	 
						pairs.put("name", ue.getUser().getName());
						pairs.put("event", ue.getEvent().getTopic());
						//xu 
						pairs.put("place", ue.getEvent().getPlace());
						pairs.put("eventTime", df.format(ue.getEvent().getStarttime()));
						//xu end
						sb=TemplateHelper.merge("eventpermission.tpl", pairs);
					try
					{
						MailThread mailThread=new MailThread();
						List<String> emailList=new ArrayList<String>();
						emailList.add(ue.getUser().getPersonalemail());
						mailThread.setEmailList(emailList);
						mailThread.setSubject(CommonConstant.audit_mail_subject);
						mailThread.setContent(sb.toString());
						mailThread.start();
						LogFactory.getLog(MailTask.class).info("提交发送审核通知邮件至用户["+ue.getUser().getPersonalemail()+"]的邮箱["+ue.getUser().getPersonalemail()+"]的后台任务。");
					}
					catch(Exception ex)
					{
						LogFactory.getLog(MailTask.class).error("提交发送审核通知邮件至用户["+ue.getUser().getPersonalemail()+"]的邮箱["+ue.getUser().getPersonalemail()+"]的后台任务失败。", ex);
						return 4;
					}
					sb=null;
					pairs=null;
					return 0;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					log.info("邮件发送失败");
					return 1;
				}
			}else
			{
				log.info("发送通知失败");
				return 1;
			} 
		} else {
			System.out.println("");
			adminEventService.addNotification(2, ue.getUser().getUserid(), null,new  String[]{ue.getEvent().getEventid(),ue.getEvent().getTopic()});
			return 1;
		}
	}
 
	/**
	 * 查询报名会员
	 * 怎么大小没变
	 * @param model
	 * @param id
	 * @param pageNo
	 * @param pageSize
	 * @param start
	 * @return
	 */
	@RequestMapping(value = "queryUserEvent")
	public String queryUserEvent(Model model, String id, Integer pageNo,
			Integer pageSize, Integer start) {
		if (this.getSessionAdmin() == null) {
			return "redirect:/admin/index?target=_top";
		}
		try {
			Page<UserEvent> page = adminEventService.queryUserEventPage(id,pageNo, pageSize, start);
			Event event = adminEventService.queryEvents(id);
			model.addAttribute("event", event);
			model.addAttribute("page", page);
			model.addAttribute("start", start);
			return "admin/checkIn";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.info("查询报名会员");
			return "common/error";
		}

	}
	
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws BiffException
	 * @throws WriteException
	 * @Date:2013-4-16  
	 * @Author:xuguangyun  
	 * @Description:导出报名活动人员的列表
	 */
	 @RequestMapping("repairContactMenu.xls")
     protected void export(Model model, String id, HttpServletRequest request, HttpServletResponse response)
        throws IOException, BiffException, WriteException {
		
		 List<User> userList=new ArrayList<User>();
		 
		 try {
			 Page<UserEvent> page = adminEventService.queryUserEventPage(id,1, 500, null);
				
				for(UserEvent userEvent : page.getResult()){
					userList.add(userEvent.getUser());
				}
				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				log.info("查询报名会员");
		
			}

//            response.reset();// 清空输出流
//            //不能用用中文设置 filename，会出错
//            response.setHeader("Content-disposition", "attachment; filename=repairContactMenu.xls");// 设定输出文件头
//            response.setContentType("application/msexcel");// 定义输出类型
           try {  
             //创建只读的Excel工作薄的对象
               String webRootPath = request.getSession().getServletContext().getRealPath("/");
               File file = new File(webRootPath + "eventadmins//repairContactMenu.xls");
             
               genarateExcel( file,userList) ;
           }catch(Exception e){
        	   
           }
        
        }
	//xu end
	// 生成Excel文件
		public void genarateExcel(File file,List<User> userList) throws Exception {
			/******   定义表格格式start    *****/
			WritableFont wf_key = new jxl.write.WritableFont(WritableFont.createFont("微软雅黑"), 10, WritableFont.BOLD);
			WritableFont wf_value = new jxl.write.WritableFont(WritableFont.createFont("微软雅黑"), 10, WritableFont.NO_BOLD);
			wcf_value = new WritableCellFormat(wf_value);
			wcf_value.setAlignment(jxl.format.Alignment.CENTRE);
			wcf_value.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
			wcf_value.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN);
			wcf_value_left = new WritableCellFormat(wf_value);
			wcf_value_left.setAlignment(jxl.format.Alignment.LEFT);
			wcf_value_left.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
			wcf_value_left.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN);
			wcf_value_left.setWrap(true);
			wcf_key = new WritableCellFormat(wf_key);
			wcf_key.setAlignment(jxl.format.Alignment.CENTRE);
			wcf_key.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN);
			wcf_name_left = new WritableCellFormat(wf_key);
			wcf_name_left.setAlignment(Alignment.LEFT);
			wcf_name_left.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
			wcf_name_right = new WritableCellFormat(wf_key);
			wcf_name_right.setAlignment(Alignment.LEFT);
			wcf_name_center = new WritableCellFormat(wf_key);
			wcf_name_center.setAlignment(Alignment.CENTRE);
			jxl.write.NumberFormat wf_percent_float = new jxl.write.NumberFormat("0.00");
			wcf_percent_float = new jxl.write.WritableCellFormat(wf_value,wf_percent_float);
			wcf_percent_float.setAlignment(jxl.format.Alignment.CENTRE);
			wcf_percent_float.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
			wcf_percent_float.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN);
			WritableFont wf_title = new jxl.write.WritableFont(WritableFont.createFont("微软雅黑"), 12, WritableFont.BOLD);
			wcf_title = new WritableCellFormat(wf_title);
			wcf_title.setAlignment(Alignment.LEFT);
			/******   定义表格格式end    *****/
			
			
			try {
				WritableWorkbook wb = Workbook.createWorkbook(file);
				WritableSheet ws = wb.createSheet("数据报表", 0);
				int startRowNum = 0;// 起始行
				int startColNum = 0;// 起始列
				int maxColSize = 6;// 最大列数
				// 设置列宽
				ws.setColumnView(0, 20);
				ws.setColumnView(1, 30);
				ws.setColumnView(2, 20);
				ws.setColumnView(3, 70);
				ws.setColumnView(4, 30);
				ws.setColumnView(5, 50);
				generateCells(ws, startRowNum++, startColNum, 1, MAXCOLS);
//			
				//第5行
				ws.addCell(new Label(startColNum, startRowNum, "姓名", wcf_key));
				ws.mergeCells(startColNum, startRowNum, startColNum, startRowNum);
				startColNum = startColNum + 1;
				ws.addCell(new Label(startColNum, startRowNum, "邮件", wcf_key));
				ws.mergeCells(startColNum, startRowNum, startColNum, startRowNum);
				startColNum = startColNum + 1;
				ws.addCell(new Label(startColNum, startRowNum, "电话", wcf_key));
				ws.mergeCells(startColNum, startRowNum, startColNum, startRowNum);
				startColNum = startColNum + 1;
				ws.addCell(new Label(startColNum, startRowNum, "weibo", wcf_key));
				ws.mergeCells(startColNum, startRowNum, startColNum, startRowNum);
				startColNum = startColNum + 1;
				
				ws.addCell(new Label(startColNum, startRowNum, "职位", wcf_key));
				ws.mergeCells(startColNum, startRowNum, startColNum, startRowNum);
				startColNum = startColNum + 1;
				
				ws.addCell(new Label(startColNum, startRowNum, "公司", wcf_key));
				ws.mergeCells(startColNum, startRowNum, startColNum, startRowNum);
				startColNum = startColNum + 1;
				
				startRowNum++;
				startColNum = 0;
				for(User user : userList){
					//第5行		
					ws.addCell(new Label(startColNum, startRowNum, user.getName(), wcf_key));
					ws.mergeCells(startColNum, startRowNum, startColNum, startRowNum);
					startColNum = startColNum + 1;
					ws.addCell(new Label(startColNum, startRowNum, user.getPersonalemail(), wcf_key));
					ws.mergeCells(startColNum, startRowNum, startColNum, startRowNum);
					startColNum = startColNum + 1;
					ws.addCell(new Label(startColNum, startRowNum, user.getPhone(), wcf_key));
					ws.mergeCells(startColNum, startRowNum, startColNum, startRowNum);
					startColNum = startColNum + 1;
					ws.addCell(new Label(startColNum, startRowNum,user.getWeibo(), wcf_key));
					ws.mergeCells(startColNum, startRowNum, startColNum, startRowNum);
					startColNum = startColNum + 1;
					Iterator it = user.getWorkExperiences().iterator();

					for(;it.hasNext();){
						WorkExperience workExperience = (WorkExperience) it.next();
						if(workExperience.getCurrent()==1){
							ws.addCell(new Label(startColNum, startRowNum,workExperience.getPosition(), wcf_key));
							ws.mergeCells(startColNum, startRowNum, startColNum, startRowNum);
							startColNum = startColNum + 1;
							ws.addCell(new Label(startColNum, startRowNum,workExperience.getCompany().getCompany(), wcf_key));
							ws.mergeCells(startColNum, startRowNum, startColNum, startRowNum);
							startColNum = startColNum + 1;
						}
					}
				
					startRowNum++;
					startColNum = 0;
				}
				
				
				
				generateCells(ws, startRowNum++, startColNum, 1, MAXCOLS);
				generateCells(ws, startRowNum++, startColNum, 1, MAXCOLS);
				ws.addCell(new Label(startColNum, startRowNum, "愉快工作，快乐生活：",wcf_name_right));
				ws.mergeCells(startColNum, startRowNum, startColNum + maxColSize, startRowNum);
				startColNum = 0;
				startRowNum++;
				
				
				wb.write();
				wb.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// 生成空单元格
		public void generateCells(WritableSheet ws, int startRows,
				int startColNums, int rows, int cols) {
			for (int r = 0; r < rows; r++) {
				for (int c = 0; c < cols; c++) {
					try {
						ws.addCell(new Label(startColNums + c, startRows + r, ""));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}

	
	@RequestMapping(value = "queryCheckIn")
	public String queryUserEventCheckIn(Model model, String name, String id,Integer pageNo, Integer pageSize, Integer start) {
		if (this.getSessionAdmin() == null) {
			return "redirect:/admin/index?target=_top";
		}
		try {
			if(null == pageNo) {
				pageNo = 1;
			}
				pageSize = 10;
			
			Page<UserEvent> page = adminEventService.queryUserEventPages(name,id, pageNo, pageSize, start);
			model.addAttribute("page", page);
			model.addAttribute("start", start);
			model.addAttribute("name", name);
			model.addAttribute("eventId",id);
			
			return "admin/checkInUser";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.info("查询报名会员");
			return "common/error";
		}

	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	@ResponseBody
	public Summary upload(@RequestParam MultipartFile[] myfiles,
			HttpServletRequest request, int type, Summary svo, String h,
			String m, String seconds, String id) {
		if (this.getSessionAdmin() == null) {
			return null;
		}
		svo.setRecommendation(0);//设为不推荐
		String filePath = "";
		String fileHttp = "";
		String fileppt = "";
		String filepptPath="";
		if (type == 1) { // PPT
			
			filePath = CommonConstant.upload_file_system_path + File.separator+ CommonConstant.ppt_dir_name;
			fileHttp = CommonConstant.upload_http_path +"/"	+ CommonConstant.ppt_dir_name;
			fileppt = CommonConstant.upload_file_system_path + File.separator+ CommonConstant.snapshot_dir_name;
			filepptPath = CommonConstant.upload_http_path+"/"+ CommonConstant.snapshot_dir_name;
		} else if (type == 0) {// 截图
			filePath = CommonConstant.upload_file_system_path + File.separator	+ CommonConstant.snapshot_dir_name;
			fileHttp = CommonConstant.upload_http_path	+"/"+ CommonConstant.snapshot_dir_name;
		} else if (type == 3) {// 海报
			filePath = CommonConstant.upload_file_system_path + File.separator	+ CommonConstant.poster_dir_name;
			fileHttp = CommonConstant.upload_http_path +"/"	+ CommonConstant.poster_dir_name;
		} else {
			// /文字
		}
		String fileName = null;
		String filepptname=null;
		try {
			for (int i = 0; i < myfiles.length; i++) {
				if (myfiles[i].isEmpty()) {
					System.out.println("文件未上传");
					return null;
				} else {
					// ppt 截图
					if (type == 1) {
						if (i == 1) {
							fileName = SystemBuffer.uuidHexGenerator.generateUUID()	+ "."+ StringUtils.getFileExtension(myfiles[i].getOriginalFilename());
							FileUtils.copyInputStreamToFile(myfiles[i].getInputStream(), new File(fileppt,fileName));
						} else {
							fileName = SystemBuffer.uuidHexGenerator.generateUUID()+ "."+ StringUtils.getFileExtension(myfiles[i].getOriginalFilename());
							filepptname=fileName;
							FileUtils.copyInputStreamToFile(myfiles[i].getInputStream(), new File(filePath,fileName));
						}
					} else {
						fileName = SystemBuffer.uuidHexGenerator.generateUUID()+ "."+ StringUtils.getFileExtension(myfiles[i].getOriginalFilename());
						FileUtils.copyInputStreamToFile(myfiles[i].getInputStream(), new File(filePath,fileName));
					}
				}
			}
			boolean bool=false;
			if(!StringUtils.isEmpty(id)){
				Event event = new Event();
				event.setEventid(id);
				svo.setEvent(event);
				bool=true;
			}
			svo.setDownloadcount(0);
			if(type==1){
				svo.setUrl(fileHttp+ "/" + filepptname);
				svo.setSnapshot(filepptPath + "/" + fileName);
			}else{
				svo.setSnapshot(fileHttp + "/" + fileName);
			}
			if (type == 0) {
				int hours = Integer.valueOf(h);
				int minutes = Integer.valueOf(m);
				int secondss = Integer.valueOf(seconds);
				int allSeconds = hours * 60 * 60 + minutes * 60 + secondss;
				svo.setDuration(allSeconds);
			}
			if(svo.getWords()!=null){
				svo.setWords(EscapeHtml.Html2Text(svo.getWords()));
			}
			this.adminEventService.saveSummary(svo);
			if(!bool){
				this.getSession().setAttribute("summeryid", svo.getSummaryid());
			}
			return svo;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			// 操作失败尝试删除新文件
			File newFile = new File(filePath, fileName);
			if (newFile.exists()) {
				newFile.delete();
			}
			return null;
		}
	}

	@RequestMapping(value="/updataSummary", method=RequestMethod.POST)
	@ResponseBody
	public Summary updataSummary(int type,Summary svo,String h,String m,String seconds,String id) 
	{
		if(this.getSessionAdmin()==null)
		{
			return null;
		}
		try{
			Event event=new Event();
			event.setEventid(id);
			svo.setEvent(event);
			if(type==0){
				int hours = Integer.valueOf(h);
				int minutes = Integer.valueOf(m);
				int secondss = Integer.valueOf(seconds);
				int allSeconds = hours * 60 * 60 + minutes * 60 + secondss;
				svo.setDuration(allSeconds);
			}
			if(svo.getWords()!=null){
				svo.setWords(EscapeHtml.Html2Text(svo.getWords()));
			}
			this.adminEventService.saveSummary(svo);
			return svo;
		}catch (Exception e) 
		{
			log.error(e.getMessage(), e);
			return null;
		}		
	}

	@RequestMapping(value = "/delSummary")
	@ResponseBody
	public int delSummary(String id) {
		if (this.getSessionAdmin() == null) {
			return 3;
		}
		try {
			Summary summary=adminEventService.getSummary(id);
			this.adminEventService.deleteSummary(id);
			String filePath="";
			String fileppt="";
			if (summary.getType() == 1) { // PPT
				fileppt = CommonConstant.upload_file_system_path + File.separator+ CommonConstant.ppt_dir_name;
				filePath = CommonConstant.upload_file_system_path + File.separator+ CommonConstant.snapshot_dir_name;
			} else if (summary.getType() == 0) {// 截图
				filePath = CommonConstant.upload_file_system_path + File.separator	+ CommonConstant.snapshot_dir_name;
			} else if (summary.getType() == 3) {// 海报
				filePath = CommonConstant.upload_file_system_path + File.separator	+ CommonConstant.poster_dir_name;
			} else if (summary.getType() == 2){//文章
				return 0;
			}
			File newFile = new File(filePath, summary.getSnapshot().substring(summary.getSnapshot().lastIndexOf("/")+1));
			if (newFile.exists()) {
				newFile.delete();
			}
			if(summary.getType() == 1){
				File newFiles = new File(fileppt, summary.getUrl().substring(summary.getUrl().lastIndexOf("/")+1));
				if (newFiles.exists()) {
					newFiles.delete();
				}
			}
			return 0;
		} catch (Exception e) {
			e.printStackTrace();
			return 1;
		}

	}
	//xu 添加素材文字标题
	@RequestMapping(value = "/upTextSummary", method=RequestMethod.POST)
	@ResponseBody
	public Summary upTextSummary(String eventId,String text,String summaryTitle, String summaryid){
		//List<Summary> listSummary=adminEventService.listSummaryType(eventId);
		System.out.println("eventId-----> " + eventId + ", summaryid-----> " + summaryid + ", summaryTitle---->" + summaryTitle + ", text----->" + text);
		Summary summary=new Summary();
		Summary result = null;
		summary.setSummaryTitle(summaryTitle);
		summary.setWords(EscapeHtml.Html2Text(text));
		if(summaryid == null || summaryid.trim().length() == 0){//新文章
			Event event=new Event();
			event.setEventid(eventId);
			summary.setEvent(event);
			summary.setType(2);
			summary.setDownloadcount(0);
			summary.setRecommendation(0);// 默认不推荐
			summary.setShoworder(System.currentTimeMillis());
			result = this.adminEventService.saveArticle(summary);
			return result;
		} else {//老文章
			summary.setSummaryid(summaryid);
			this.adminEventService.updateSummary(summary);
		}
		return summary;
	}

	@RequestMapping(value = "/queryImgSummary")
	@ResponseBody
	public SummaryVo querySummary(Model model, String sumid) {
		try {
			if (this.getSessionAdmin() == null) {
				return null;
			}
			Summary summary = adminEventService.getSummary(sumid);
			SummaryVo vo = new SummaryVo();
			vo.setSummaryid(summary.getSummaryid());
			vo.setDownloadcount(summary.getDownloadcount());
			vo.setDuration(summary.getDuration() + "");
			vo.setEventid(summary.getEvent().getEventid());
			vo.setNum(summary.getNum());
			vo.setSnapshot(summary.getSnapshot());
			vo.setType(summary.getType());
			vo.setUrl(summary.getUrl());
			vo.setWords(summary.getWords());
			vo.setVideoSource(summary.getVideoSource());
			return vo;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/*
	public static String toSemiangle(String src) {
        char[] c = src.toCharArray();
        for (int index = 0; index < c.length; index++) {
            if (c[index] == 12288) {// 全角空格
                c[index] = (char) 32;
            } else if (c[index] > 65280 && c[index] < 65375) {// 其他全角字符
                c[index] = (char) (c[index] - 65248);
            }
        }
        return String.valueOf(c);
    }
	*/
}
