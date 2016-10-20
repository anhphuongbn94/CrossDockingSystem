package com.cds.dao.employee;

import com.cds.entity.employee.Employee;

public interface EmployeeDAO {
	public boolean checkLogin(String username, String password);
	public boolean register(Employee em);
}
