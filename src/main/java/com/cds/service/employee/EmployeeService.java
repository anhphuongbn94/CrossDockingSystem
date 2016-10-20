package com.cds.service.employee;

import com.cds.entity.employee.Employee;

public interface EmployeeService {
	public boolean checkLogin(String username, String password);
	public boolean register(Employee em);
}
