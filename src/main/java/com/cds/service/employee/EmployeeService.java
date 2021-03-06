package com.cds.service.employee;

import java.util.List;

import com.cds.entity.employee.Employee;

public interface EmployeeService {
	public Employee checkLogin(String username, String password);
	public List<Employee> getPageEmployee(int sizePage, int currentPage, String key);
	public int countAllEmployee(String key);
	public boolean register(Employee em);
	public void changeEmployee(Employee em);
}
