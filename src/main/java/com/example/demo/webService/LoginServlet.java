package com.example.demo.webService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.common.ClientType;
import com.example.demo.entry.CouponSystem;
import com.example.demo.facades.AdminFacade;
import com.example.demo.facades.CompanyFacade;
import com.example.demo.facades.CustomerFacade;

@Controller
@CrossOrigin("*")
public class LoginServlet {
	
	@Autowired
	private CouponSystem couponSystem;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(HttpServletRequest request, HttpServletResponse response,
			@RequestParam String username, @RequestParam String password, @RequestParam String type)
	{
		ClientType clientType = ClientType.valueOf(type);
		switch (clientType) {
		case ADMIN:
			AdminFacade adminFacade = (AdminFacade) couponSystem.login(username, password, clientType);
			if (adminFacade == null) {
				return "redirect:http://localhost:8080";
			} else {
				request.getSession().setAttribute("adminFacade", adminFacade);
				return "redirect:http://localhost:8080/admin/index.html";
			}
		case COMPANY:
			CompanyFacade companyFacade = (CompanyFacade) couponSystem.login(username, password, clientType);
			if (companyFacade == null) {
				return "redirect:http://localhost:8080";
			} else {
				request.getSession().setAttribute("companyFacade", companyFacade);
				return "redirect:http://localhost:8080/company/index.html";
			}
		case CUSTOMER:
			CustomerFacade customerFacade = (CustomerFacade) couponSystem.login(username, password, clientType);
			if (customerFacade == null) {
				return "redirect:http://localhost:8080";
			} else {
				System.out.println("working");
				request.getSession().setAttribute("customerFacade", customerFacade);
				return "redirect:http://localhost:8080/customer/index.html";
			}
		}
		return null;
	}	
}

