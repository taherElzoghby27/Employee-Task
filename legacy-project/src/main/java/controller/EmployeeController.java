package controller;

import java.io.IOException;
import java.util.List;

import javax.sql.DataSource;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Employee;
import service.EmployeeService;
import service.impl.EmployeeServiceImpl;
@WebServlet("/employee")
public class EmployeeController extends HttpServlet{

	private static final long serialVersionUID = 1L;
	@Resource(name = "employee_db")
	private DataSource dataSource;
	private EmployeeService employeeService;
	

	public EmployeeController() {
		this.employeeService = new EmployeeServiceImpl(dataSource);
	}


	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ObjectMapper objectMapper=new ObjectMapper();
		Employee employee=objectMapper.readValue(req.getReader(),Employee.class);
		List<Employee>employees=  this.employeeService.getEmployees(employee);
        resp.getWriter().write(objectMapper.writeValueAsString(employees));
	}
}
