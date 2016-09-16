package dao.employee;

import entity.employee.Employee;

public interface EmployeeDAO {
	public boolean checkLogin(String username, String password);
	public boolean register(Employee em);
}
