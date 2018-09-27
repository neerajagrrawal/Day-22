package com.capgemini.simpleapp.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.capgemini.simpleapp.entities.Customer;
import com.capgemini.simpleapp.exception.AccountNotFoundException;
import com.capgemini.simpleapp.service.CustomerService;

@Controller
public class CustomerController {

	@Autowired
	private CustomerService service;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String getHomePage() {
		return "iciciHome";
	}

	@RequestMapping(value = "/loginPage", method = RequestMethod.GET)
	public String getLoginPage(Model model) {
		model.addAttribute("customer", new Customer());
		return "login";
	}

	@RequestMapping(value = "/logoutSession", method = RequestMethod.GET)
	public String getLogout(HttpSession session) {
		session.invalidate();
		return "logout";
	}

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public String getAuthentication(@ModelAttribute Customer customer, HttpSession session, Model model) {

			try {
				customer = service.authenticate(customer);
				session.setAttribute("customer", customer);
				return "dashboard";
			}
			catch(Exception e)
			{
				return "wrongCredentials";
			}
			
			
		} 



	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public String getDashboardPage() {
		return "dashboard";
	}

	@RequestMapping(value = "/editProfilePage", method = RequestMethod.GET)
	public String getEditProfilePage(HttpSession session, Model model) {
		model.addAttribute("customer", session.getAttribute("customer"));
		return "/editProfile";
	}

	@RequestMapping(value = "/editProfile", method = RequestMethod.POST)
	public String getEditProfile(@ModelAttribute Customer customer, HttpSession session) {
		Customer newCustomer = (Customer) session.getAttribute("customer");
		customer.setAccount(newCustomer.getAccount());
		customer.setCustomerId(newCustomer.getCustomerId());
		customer.setPassword(newCustomer.getPassword());
		service.updateProfile(customer);
		session.setAttribute("customer", customer);
		return "editProfileSuccess";
	}
	
	
	@RequestMapping(value = "/updatePasswordPage", method = RequestMethod.GET)
	public String getUpdatePasswordPage() {
		return "updatePassword";
	}
	
	@RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
	public String getUpdatePassword(HttpSession session,@RequestParam("newPassword") String newPassword,@RequestParam("oldPassword") String oldPassword) {
		Customer customer=(Customer) session.getAttribute("customer") ;
		if(!service.updatePassword(customer, newPassword, oldPassword))
		 {
			 return "/updatePasswordError" ;
		 }
		 else
		 {
			 customer.setPassword(newPassword);
			 session.setAttribute("customer", customer);
			 return "/updatePasswordSuccess" ;
		 }
	}
	
	

	/*
	 * @RequestMapping(value = "/addEmployeePage", method = RequestMethod.GET)
	 * public String getAddEmployeePage(Model model) {
	 * model.addAttribute("employee", new Employee()); return "addEmployeeForm"; }
	 * 
	 * @RequestMapping(value = "/addEmployee", method = RequestMethod.POST) public
	 * String addNewEmployee(@ModelAttribute Employee employee) {
	 * employeeService.addEmployee(employee); return "redirect:/findAllEmployees";
	 * 
	 * }
	 * 
	 * @RequestMapping(value = "/findAllEmployees", method = RequestMethod.GET)
	 * public String getAllEmployeeDetails(Model model) { List<Employee> employees =
	 * employeeService.findAllEmployees(); model.addAttribute("allEmployees",
	 * employees); return "allEmployees"; }
	 * 
	 * @RequestMapping(value = "/deleteEmployee/{employeeId}", method =
	 * RequestMethod.GET) public String deleteEmployee(@PathVariable int employeeId)
	 * { employeeService.deleteEmployee(employeeId); return
	 * "redirect:/findAllEmployees"; }
	 * 
	 * @RequestMapping(value = "/editEmployeePage/{employeeId}", method =
	 * RequestMethod.GET) public String editEmployeePage(Model model, @PathVariable
	 * int employeeId) { Employee employee =
	 * employeeService.findEmployeeById(employeeId); model.addAttribute("employee",
	 * employee); return "updateEmployeeForm"; }
	 * 
	 * @RequestMapping(value = "/updateEmployee", method = RequestMethod.POST)
	 * public String updateEmployee(@ModelAttribute Employee employee) {
	 * employeeService.updateEmployee(employee); return
	 * "redirect:/findAllEmployees";
	 * 
	 * }
	 */
}
