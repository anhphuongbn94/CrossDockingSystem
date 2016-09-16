package control;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import dao.employee.EmployeeDAO;
import entity.employee.Employee;

@Controller
public class HomeControl {
	@Autowired
	private EmployeeDAO empDAO;
	
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
	public String checkLogin(
			@RequestParam(value = "username") String username,
			@RequestParam(value = "password") String password,
			HttpSession session
	){
		if(empDAO.checkLogin(username, password)){
			Employee em=new Employee(); em.setUsername(username); em.setPassword(password);
			session.setAttribute("em", em);
			return "redirect:";
		}else{
			return "redirect:login";
		}
	}
	@RequestMapping(value = "register", method = RequestMethod.GET)
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
		Employee em=new Employee(fullname, gender, email, phonenumber, address, username, password, active);
		if(empDAO.register(em)){
			return "redirect:login";
		}
		return "redirect:register";
	}
}
