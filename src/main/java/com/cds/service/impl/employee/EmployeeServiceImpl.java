package com.cds.service.impl.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cds.dao.employee.EmployeeDAO;
import com.cds.entity.employee.Employee;
import com.cds.service.employee.EmployeeService;

@Component
public class EmployeeServiceImpl implements EmployeeService{
	@Autowired
	private EmployeeDAO emDAO;
	
	public boolean checkLogin(String username, String password) {
		return emDAO.checkLogin(username, password);
	}

	public boolean register(Employee em) {
		return emDAO.register(em);
	}

}
