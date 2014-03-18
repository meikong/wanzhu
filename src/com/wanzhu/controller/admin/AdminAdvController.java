package com.wanzhu.controller.admin;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.wanzhu.base.BaseController;
import com.wanzhu.base.CommonConstant;
import com.wanzhu.base.SystemBuffer;
import com.wanzhu.entity.Adv;
import com.wanzhu.entity.Result;
import com.wanzhu.service.admin.AdminAdvService;
import com.wanzhu.utils.StringUtils;

@Controller
@RequestMapping("/adminAdv")
public class AdminAdvController extends BaseController
{
    @Autowired
    private AdminAdvService adminAdvService;
    
    @RequestMapping("/query")
    public String query(Model model)
    {
        model.addAttribute("vos", adminAdvService.query());
        model.addAttribute("advTypes", adminAdvService.getAllTypes());
        return "admin/advQuery";
    }
    
    @RequestMapping("/modify")
    public String modify(Model model,Adv adv,@RequestParam(value="file",required=false) MultipartFile[] file,String parkings)
    {
        try
        {
            Adv vo = adminAdvService.queryById(adv.getAdvid());
            vo = null == vo?new Adv():vo;
            vo.setMemo(adv.getMemo());
            vo.setLink(adv.getLink());
            vo.setValid(adv.getValid());
            vo.setParking(adv.getParking());
            if(null != file && file.length>0 &&  !file[0].isEmpty())
            {
                String filePath=CommonConstant.upload_file_system_path+"/"+CommonConstant.adv_dir_name+"/";
                String fileName = SystemBuffer.uuidHexGenerator.generateUUID()+"."+StringUtils.getFileExtension(file[0].getOriginalFilename());
                File f = new File(filePath,fileName);
                FileUtils.copyInputStreamToFile(file[0].getInputStream(), f); 
                String newPath = CommonConstant.upload_http_path+"/"+CommonConstant.adv_dir_name+"/"+fileName;
                vo.setUrl(newPath);
            }
            adminAdvService.saveOrUpdate(vo,parkings);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return "redirect:/adminAdv/query";
    }
   
    @RequestMapping("del")
    public String del(Model model,Adv adv)
    {
        try
        {
            if(null != adv && adv.getAdvid().length()>0)
                adminAdvService.del(adv);
        }
        catch(Exception e)
        {
            log.error(e.getMessage());
        }
        return "redirect:/adminAdv/query";
    }
    
    @RequestMapping("/detail")
    public String detail(Model model,Adv adv)
    {
        Result result = new Result();
        try
        {
            result.setContent(adminAdvService.queryById(adv.getAdvid()));
            result.setSuccess(true);
        }
        catch(Exception e)
        {
           log.error(e.getMessage());
           result.setSuccess(false);
        }
        model.addAttribute("result", result);
        return "jsonview";
    }
    
    @RequestMapping("/valid")
    public String valid(Model model ,Adv adv)
    {
        try
        {
            adminAdvService.valid(adv);
        }
        catch(Exception e)
        {
            log.error(e.getMessage());
        }
        return "redirect:/adminAdv/query";
    }
}
