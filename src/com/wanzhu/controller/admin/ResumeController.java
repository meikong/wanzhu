package com.wanzhu.controller.admin;

import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wanzhu.base.BaseController;
import com.wanzhu.base.Page;
import com.wanzhu.entity.Label;
import com.wanzhu.entity.admin.Resume;
import com.wanzhu.service.admin.ResumeService;


@Controller
@RequestMapping("/resume")
public class ResumeController extends BaseController {

	@Autowired
	private ResumeService resumeService;
	/**
	 * 查询简历列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "queryResume")
	public String queryEvents(Model model, Integer pageNo, Integer pageSize,String content,String name,String company,String position,String address) {
		
		if (this.getSessionAdmin() == null) {
			return "redirect:/admin/index?target=_top";
		}
		try {
			if (null == pageNo) {
				pageNo = 1;
			}
			if (null == pageSize) {
				pageSize = Page.DEFAULT_PAGE_SIZE;
			}
			Page<Resume> page = resumeService.queryResumePage(pageNo,pageSize, name, company, position, address, content);
			
			model.addAttribute("page", page);
			
		} catch (Exception e) {
			// TODO: handle exception
			log.info("查询简历列表异常");
		}
		return "admin/resumeList";
	}

	/**
	 * 查询简历详情
	 * 
	 * @return
	 */
	@RequestMapping(value = "queryResumeInDeail")
	public String queryResumeDeail(Integer resumeId, Model model) {
		
		Resume resume = resumeService.queryResumeById(resumeId);
		model.addAttribute("resume", resume);
		return "admin/viewResume";
	}


	@RequestMapping(value = "saveResume")
	public String  saveLable(Resume resume) {
		try {
			    String temp_str="";   
			    Date dt = new Date();   
			    //最后的aa表示“上午”或“下午”    HH表示24小时制    如果换成hh表示12小时制   
			    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss aa");   
			    temp_str=sdf.format(dt);   
		        resume.setCreateDate(temp_str);
		        
		        
		        //创建一个list 用来存储读取的内容
		        List list = new ArrayList();
		        Workbook rwb = null;
		        Cell cell = null;
		        
		        //创建输入流
		        InputStream stream = new FileInputStream("C:/Users/xuguangyun/Downloads/样表.xls");
		        
		        //获取Excel文件对象
		        rwb = Workbook.getWorkbook(stream);
		        
		        //获取文件的指定工作表 默认的第一个
		        Sheet sheet = rwb.getSheet(0);  
		       
		        //行数(表头的目录不需要，从1开始)
		        for(int i=1; i<sheet.getRows(); i++){
		         Resume resumes=new Resume();
		         //创建一个数组 用来存储每一列的值
		         String[] str = new String[sheet.getColumns()];
		         resumes.setName(sheet.getCell(0,i).getContents());
		         resumes.setEmail(sheet.getCell(1,i).getContents());
		         resumes.setPhone(sheet.getCell(2,i).getContents());
		         resumes.setCompany(sheet.getCell(3,i).getContents());
		         resumes.setWeibo(sheet.getCell(4,i).getContents());
		         resumeService.saveResume(resumes);
//		         //列数
//		         for(int j=0; j<sheet.getColumns(); j++){
//		         
//		          //获取第i行，第j列的值
//		          cell = sheet.getCell(j,i);    
//		          str[j] = cell.getContents();
//		          System.out.println(str[j]+"    j"+j);
//		          
//		         }
		        
		        }
			
			return "redirect:/resume/queryResume";
			
		} catch (Exception e) {
			// TODO: handle exception
			return "redirect:/resume/queryResume";
		}

	}
	@RequestMapping(value = "startSaveResume")
	public String startSaveEvent(Model model) {
		this.getSession().removeAttribute("summeryid");
		if (this.getSessionAdmin() == null) {
			return "redirect:/admin/index?target=_top";
		}
		model.addAttribute("flag", "save");
		
		return "admin/viewResume";
	}

}
