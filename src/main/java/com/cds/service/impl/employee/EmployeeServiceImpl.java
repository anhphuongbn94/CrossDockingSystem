package com.cds.service.impl.employee;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cds.dao.employee.EmployeeDAO;
import com.cds.entity.employee.Employee;
import com.cds.service.employee.EmployeeService;

@Component
public class EmployeeServiceImpl implements EmployeeService{
	@Autowired
	private EmployeeDAO emDAO;
	
	public Employee checkLogin(String username, String password) {
		return emDAO.checkLogin(username, password);
	}

	public boolean register(Employee em) {
		return emDAO.register(em);
	}

	public List<Employee> getPageEmployee(int sizePage, int currentPage, String key) {
		return emDAO.getPageEmployee(sizePage, currentPage, key);
	}

	public int countAllEmployee(String key) {
		return emDAO.countAllEmployee(key);
	}

	public void changeEmployee(Employee em) {
		emDAO.changeEmployee(em);
	}

}
