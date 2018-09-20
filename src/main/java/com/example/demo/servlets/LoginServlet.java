package com.example.demo.servlets;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.common.ClientType;
import com.example.demo.entry.CouponSystem;
import com.example.demo.exceptions.WrongLoginInputException;
import com.example.demo.facades.CouponClientFacade;


@Controller
public class LoginServlet {

	@Autowired
	private CouponSystem couponSystem;

	@RequestMapping(value= "/loginservlet", method = RequestMethod.POST) 
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws WrongLoginInputException, InterruptedException
	{
		String name = request.getParameter("nametxt");
		String password = request.getParameter("passwordtxt");
		String clientType = request.getParameter("clienttype");

		try {
			CouponClientFacade facade = couponSystem.login(name, password, ClientType.valueOf(clientType));

				switch (clientType) {
				case "ADMIN":
					request.getSession().setAttribute("adminFacade", facade);	
					response.sendRedirect("http://localhost:8080/admin/index.html");
					break;
					
				case "COMPANY":
					request.getSession().setAttribute("companyFacade", facade);
					response.sendRedirect("http://localhost:8080/company/index.html");
					break;
					
				case "CUSTOMER":
					request.getSession().setAttribute("customerFacade", facade); 
					response.sendRedirect("http://localhost:8080/customer/index.html");
					break;
				
			}
		} catch (IOException e) {
			try {
				response.sendRedirect("http://localhost:8080/login.html?error=wrongDataEntered");
			} catch (IOException e1) {
				e1.printStackTrace();
			}			
		}
	}
}
