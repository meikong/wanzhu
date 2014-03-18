package com.wanzhu.controller.admin;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wanzhu.base.BaseController;
import com.wanzhu.base.Page;
import com.wanzhu.entity.admin.Administrator;
import com.wanzhu.service.admin.AdministratorService;

@Controller
@RequestMapping("/lagouAdmin")
public class AdministratorController extends BaseController {

	@Autowired
	private AdministratorService administratorService;

	@RequestMapping(value = "listadmin")
	public String listAdministrator(Model model, Integer role, Integer pageNo,
			Integer pageSize) {
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
			Page<Administrator> page = administratorService.listAdministrator(pageNo, pageSize, role);
			model.addAttribute("page", page);
			model.addAttribute("role", role);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error(e);
		}
		return "admin/adminList";
	}

	@RequestMapping(value = "startUpAdmin")
	public String startUpAdmin() {
		if (this.getSessionAdmin() == null) {
			return "redirect:/admin/index?target=_top";
		}
		return "admin/editPassword";
	}

	@RequestMapping(value = "upPasswordAdmin")
	@ResponseBody
	public int upPasswordAdmin(Model model, String password, String ypassword,
			String qpassword) {
		int res = 3;
		try {
			if (this.getSessionAdmin() == null) {
				return 3;
			}
			Administrator administa = (Administrator) this.getSessionAdmin();
			res = administratorService.upPassword(administa.getAdministratorid(), password, ypassword,qpassword);
			model.addAttribute(ERROR_CODE_KEY, "操作成功");
		} catch (Exception e) {
			// TODO: handle exception
			model.addAttribute(ERROR_CODE_KEY, "操作失败");
		}
		return res;
	}

	@RequestMapping(value = "saveAdministrator")
	@ResponseBody
	public int saveAdministrator(Model model, Administrator administrator) {
		if (this.getSessionAdmin() == null) {
			return 3;
		}
		administrator.setAdministrator(replaceBlank(administrator.getAdministrator()));
		administrator.setAdministrator(administrator.getAdministrator().toLowerCase());
		Administrator administa = (Administrator) this.getSessionAdmin();
		if (!administa.getRole().equals(administrator.getRole())) {
			if (administa.getRole() == 1) {
				return 4;
			}
		} else {

			if (!administa.getAdministrator().equals(
					administrator.getAdministrator())) {
				if (administa.getRole() == 1) {
					return 4;
				}
			}
		}

		int res = administratorService.saveOrUpdateAdministrator(administrator,administa.getAdministratorid());
		return res;
	}

	public static String replaceBlank(String str) {
		String dest = "";
		if (str != null) {
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}



	@RequestMapping(value = "delAdministrator")
	@ResponseBody
	public int delAdministrator(String id) {
		if (this.getSessionAdmin() == null) {
			return 3;
		}
		Administrator administa = (Administrator) this.getSessionAdmin();
		if (administa.getRole() == 1) {
			return 4;
		}
		try {
			String[] administratorId = new String[] { id };
			administratorService.deleteAdministrator(administratorId);
			return 0;
		} catch (Exception e) {
			// TODO: handle exception
			return 1;
		}
	}

	@RequestMapping(value = "queryAdministrator")
	@ResponseBody
	public Administrator queryAdministrator(Model model, String administratorid) {
		if (this.getSessionAdmin() == null) {
			return null;
		}
		Administrator administa = administratorService
				.queryAdministrator(administratorid);
		return administa;
	}
}
