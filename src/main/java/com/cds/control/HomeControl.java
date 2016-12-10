package com.cds.control;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cds.entity.Pager;
import com.cds.entity.employee.Employee;
import com.cds.field.Constants;
import com.cds.service.employee.EmployeeService;

@Controller
public class HomeControl {
	@Autowired
	private EmployeeService empService;
	
	@RequestMapping(value = {"", "home"}, method = RequestMethod.GET)
	public ModelAndView home(HttpSession session){
		Employee em=(Employee) session.getAttribute("em");
		if(em != null){
			return new ModelAndView("home.def");
		}else{
			return new ModelAndView("login.def");
		}
	}
	@RequestMapping(value = "login", method = RequestMethod.GET)
	public ModelAndView login(){
		return new ModelAndView("login.def");
	}
	@RequestMapping(value = "checkLogin", method = RequestMethod.POST)
	public ModelAndView checkLogin(
			ModelMap mm, HttpSession session, 
			@RequestParam(value = "username") String username,
			@RequestParam(value = "password") String password
	){
		Employee em=empService.checkLogin(username, password);
		if(em != null){
			session.setAttribute("em", em);
			return new ModelAndView("home.def");
		}
		return new ModelAndView("login.def");
	}
	@RequestMapping(value = "empManager", method = RequestMethod.GET)
	public ModelAndView empManager(ModelMap mm){
		String key = Constants.EMPTY;
		int currentPage = Constants.START_PAGE;
		int size = Constants.SIZE_PAGE;
		int total = empService.countAllEmployee(key);
		Pager pager=new Pager();
		pager.initPage(size, total, currentPage);
		mm.put("pager", pager);
		mm.put("pageEmp", empService.getPageEmployee(size, currentPage, key));
		return new ModelAndView("empManager.def");
	}
	@RequestMapping(value = "logout", method = RequestMethod.GET)
	public String logout(HttpSession session){
		session.removeAttribute("em");
		return "redirect:login";
	}
	@RequestMapping(value = "changePassword", method = RequestMethod.POST)
	@ResponseBody
	public String changePassword(HttpSession session,
			@RequestParam(value = "newPass") String newPassword
	){
		Employee em = (Employee) session.getAttribute("em");
		em.setPassword(newPassword);
		empService.changeEmployee(em);
		return "success";
	}
	@RequestMapping(value = "changeProfile", method = RequestMethod.POST)
	@ResponseBody
	public String changeProfile(
			HttpSession session,
			@RequestParam(value = "fullname") String fullname,
			@RequestParam(value = "email") String email,
			@RequestParam(value = "address") String address,
			@RequestParam(value = "phonenumber") String phonenumber,
			@RequestParam(value = "gender") int gender
	){
		Employee em = (Employee) session.getAttribute("em");
		em.setFullname(fullname);
		em.setEmail(email);
		em.setAddress(address);
		em.setPhonenumber(phonenumber);
		em.setGender(gender);
		empService.changeEmployee(em);
		return "success";
	}
	/*@RequestMapping(value = "register", method = RequestMethod.GET)
	public ModelAndView register(){
		return new ModelAndView("register.def");
	}
	@RequestMapping(value = "register", method = RequestMethod.POST)
	public String checkRegister(
			@RequestParam(value = "fullname") String fullname,
			@RequestParam(value = "email") String email,
			@RequestParam(value = "phonenumber") String phonenumber,
			@RequestParam(value = "address") String address,
			@RequestParam(value = "gender") Integer gender,
			@RequestParam(value = "username") String username,
			@RequestParam(value = "password") String password,
			@RequestParam(value = "confirmPassword") String confirmPassword
	){
		int active=1;
		Employee em=new Employee();
		if(emService.register(em)){
			return "redirect:login";
		}
		return "redirect:register";
	}*/
}
