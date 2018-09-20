package com.example.demo.webService;

import java.util.Collection;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.common.ClientType;
import com.example.demo.entities.Company;
import com.example.demo.entities.Customer;
import com.example.demo.entry.CouponSystem;
import com.example.demo.exceptions.CompanyExistException;
import com.example.demo.facades.AdminFacade;

@RestController
@CrossOrigin("*")
public class AdminWS {

	@Autowired
	CouponSystem couponSystem;

	
	private AdminFacade getFacade(HttpServletRequest req)
	{
	// the line below is only for testing
	// AdminFacade adminFacade = (AdminFacade) couponSystem.login(username, password, type); 
	// should be instead
	//	--fake login--
	//	AdminFacade adminFacade = (AdminFacade) couponSystem.login("admin", "1234", ClientType.ADMIN);
	//	return adminFacade;
		
		AdminFacade adminFacade = (AdminFacade) req.getSession().getAttribute("adminFacade");
		return adminFacade;
		
	}
	
	/*
	 *  Admin Facade - Company methods 
	 */
	
	//works
	@RequestMapping(value = "/adminws/company", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public void CreateCompany (HttpServletRequest req, @RequestBody Company comp)
	{
		System.out.println(comp);
		AdminFacade adminFacade = getFacade(req);
		adminFacade.CreateCompany(comp);
	}
	
	//works
	@RequestMapping(value = "/adminws/company/{id}", method = RequestMethod.DELETE)
	public void RemoveCompany (@PathVariable("id")long id, HttpServletRequest req)
	{
		AdminFacade adminFacade = getFacade(req);
		adminFacade.RemoveCompany(adminFacade.GetCompany(id));
	}
	
	//g
	@RequestMapping(value = "/adminws/company/{id}", method = RequestMethod.PUT)
	public void UpdateCompany (@RequestBody Company comp, @PathVariable("id")long id, HttpServletRequest req)
	{
		AdminFacade adminFacade = getFacade(req);
		Company com = adminFacade.GetCompany(id);
		com.setPassword(comp.getPassword());
		com.setEmail(comp.getEmail());
		adminFacade.UpdateCompany(com);
	}
	
	//works
	@RequestMapping(value = "/adminws/company/{id}", method = RequestMethod.GET)
	public Company GetCompany (@PathVariable("id")long id, HttpServletRequest req)
	{
		AdminFacade adminFacade = this.getFacade(req);
		return adminFacade.GetCompany(id);
	}
	
	//works
	@RequestMapping(value = "/adminws/company/name/{name}", method = RequestMethod.GET)
	public Company GetCompanyByCompanyName (@PathVariable("name")String name, HttpServletRequest req)
	{
		AdminFacade adminFacade = getFacade(req);
		return adminFacade.GetCompanyByCompanyName(name);
	}
	
	//works
	@RequestMapping(value = "/adminws/company", method = RequestMethod.GET)
	public Collection <Company> GetAllCompanies(HttpServletRequest req)
	{
		AdminFacade adminFacade = getFacade(req);
		return adminFacade.GetAllCompanies();
	}

	/* 
	 *  Admin Facade - Customer methods 
	 */
	
	//works
	@RequestMapping(value = "/adminws/customer", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public void CreateCustomer(@RequestBody Customer cust, HttpServletRequest req)
	{
		System.out.println(cust);
		AdminFacade adminFacade = getFacade(req);
		adminFacade.CreateCustomer(cust);
	}
	
	//works
	@RequestMapping(value = "/adminws/customer/{id}", method = RequestMethod.DELETE)
	public void RemoveCustomer (@PathVariable("id")long id, HttpServletRequest req)
	{
		AdminFacade adminFacade = getFacade(req);
		adminFacade.RemoveCustomer(adminFacade.GetCustomer(id));
	}
	
	//generates 2 customer if you dont write id
	@RequestMapping(value = "/adminws/customer/{id}", method = RequestMethod.PUT)
	public void UpdateCustomer(@RequestBody Customer cust, @PathVariable("id")long id, HttpServletRequest req)
	{
		AdminFacade adminFacade = getFacade(req);
		adminFacade.UpdateCustomer(cust);
	}
	
	//works
	@RequestMapping(value = "/adminws/customer/{id}", method = RequestMethod.GET)
	public Customer GetCustomer (@PathVariable("id")long id, HttpServletRequest req)
	{
		AdminFacade adminFacade = getFacade(req);
		return adminFacade.GetCustomer(id);
	}
	
	//works
	@RequestMapping(value = "/adminws/customer", method = RequestMethod.GET)
	public Collection <Customer> GetAllCustomers (HttpServletRequest req)
	{
		AdminFacade adminFacade = getFacade(req);
		return adminFacade.GetAllCustomers();
	}
	
	//works
	@RequestMapping(value = "/adminws/customer/name/{name}", method = RequestMethod.GET)
	public Customer GetCustomerByCustomerName (@PathVariable("name")String name, HttpServletRequest req)
	{
		AdminFacade adminFacade = getFacade(req);
		return adminFacade.GetCustomerByCustomerName(name);
	}
}
