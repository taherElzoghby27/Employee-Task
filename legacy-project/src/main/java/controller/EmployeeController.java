package controller;

import java.io.IOException;
import java.util.List;

import javax.sql.DataSource;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.Employee;
import service.EmployeeService;
import service.impl.ServiceFactory;
@WebServlet("/employee")
public class EmployeeController extends HttpServlet{

	private static final long serialVersionUID = 1L;
	@Resource(name = "employee_db")
	private DataSource dataSource;
	private EmployeeService employeeService;
	

	@Override
	public void init() throws ServletException {
		System.out.println("EmployeeController initialized with DataSource: " + dataSource);
		this.employeeService = ServiceFactory.createEmployeeService(dataSource);
	}


	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Employee employee = mapRequestToEmployee(req);
		List<Employee> employees = this.employeeService.getEmployees(employee);
		writeJsonResponse(resp, employees);
	}

	private Employee mapRequestToEmployee(HttpServletRequest req) {
		Employee employee = new Employee();
		employee.setEmployeeId(parseLong(req.getParameter("employeeId")));
		employee.setEmployeeCode(req.getParameter("employeeCode"));
		employee.setEmployeeName(req.getParameter("employeeName"));
		employee.setBirthCity(req.getParameter("birthCity"));
		employee.setBirthDate(parseDate(req.getParameter("birthDate")));
		employee.setDepartmentName(req.getParameter("department"));
		employee.setJob(req.getParameter("jobTitle"));
		employee.setContractType(req.getParameter("contractType"));
		employee.setDirectManager(req.getParameter("directManager"));
		employee.setStatus(req.getParameter("status"));
		return employee;
	}

	private Long parseLong(String value) {
		if (value == null || value.trim().isEmpty() || "null".equalsIgnoreCase(value)) {
			return null;
		}
		try {
			return Long.parseLong(value);
		} catch (NumberFormatException e) {
			return null;
		}
	}

	private String parseDate(String value) {
		if (value == null || value.trim().isEmpty() || "null".equalsIgnoreCase(value)) {
			return null;
		}
		return value;
	}

	private void writeJsonResponse(HttpServletResponse resp, Object data) throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		resp.getWriter().write(objectMapper.writeValueAsString(data));
	}
}
