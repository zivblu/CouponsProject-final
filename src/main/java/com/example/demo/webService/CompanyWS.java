package com.example.demo.webService;

import java.util.Collection;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;


import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.common.ClientType;
import com.example.demo.common.CouponType;
import com.example.demo.entities.Coupon;
import com.example.demo.entry.CouponSystem;
import com.example.demo.facades.AdminFacade;
import com.example.demo.facades.CompanyFacade;

@RestController
@CrossOrigin("*")
public class CompanyWS {
	
	@Autowired
	CouponSystem couponSystem;

	private CompanyFacade getFacade(HttpServletRequest req)
	{
	// the line below is only for testing
	// AdminFacade adminFacade = (AdminFacade) couponSystem.login(username, password, type); 
	// should be instead
	// --fake login--	
	//	CompanyFacade companyFacade = (CompanyFacade) couponSystem.login("BBB", "BBB1234", ClientType.COMPANY);
	//	return companyFacade;
		
		CompanyFacade companyFacade = (CompanyFacade) req.getSession().getAttribute("companyFacade");
		return companyFacade;
	}
	
	//works
	@RequestMapping(value = "/companyws/coupon", method = RequestMethod.POST, 
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public void CreateCoupon (HttpServletRequest req, @RequestBody Coupon coup)
	{
		System.out.println(coup);
		CompanyFacade companyFacade = getFacade(req);
		companyFacade.CreateCoupon(coup);
	}
	
	//works
	@RequestMapping(value = "/companyws/coupon/{id}", method = RequestMethod.DELETE)
	public void RemoveCoupon (HttpServletRequest req, @PathVariable("id")long id)
	{
		CompanyFacade companyFacade = getFacade(req);	
		companyFacade.RemoveCoupon(companyFacade.GetCoupon(id));
	}
	
	//works
	@RequestMapping(value = "/companyws/coupon/{id}", method = RequestMethod.PUT)
	public void UpdateCoupon (HttpServletRequest req, @RequestBody Coupon coup, @PathVariable("id")long id)
	{
		CompanyFacade companyFacade = getFacade(req);	
		companyFacade.UpdateCoupon(coup);
	}
	
	//works
	@RequestMapping(value = "/companyws/coupon/{id}", method = RequestMethod.GET)
	public Coupon GetCoupon (HttpServletRequest req, @PathVariable("id")long id)
	{
		CompanyFacade companyFacade = getFacade(req);	
		return companyFacade.GetCoupon(id);
	}
	
	//works
	@RequestMapping(value = "/companyws/coupon", method = RequestMethod.GET)
	public Collection <Coupon> getAllCoupons(HttpServletRequest req)
	{
		CompanyFacade companyFacade = getFacade(req);	
		return companyFacade.getAllCoupons();
	}
	
	//works
	@RequestMapping(value = "/companyws/coupon/type/{type}", method = RequestMethod.GET)
	public Collection <Coupon> getCouponByType (HttpServletRequest req, @PathVariable("type")CouponType type)
	{ 
		CompanyFacade companyFacade = getFacade(req);	
		return companyFacade.getCouponByType(type);
	}
	
	//works, show price between 0 to the amount inserted
	@RequestMapping(value = "/companyws/coupon/price/{price}", method = RequestMethod.GET)
	public Collection <Coupon> getCouponByPrice (HttpServletRequest req, @PathVariable("price")double price)
	{
		CompanyFacade companyFacade = getFacade(req);	
		return companyFacade.getCouponByPrice(price);
	}
	
	//need to fix date in postman
	@RequestMapping(value = "/companyws/coupon/date/{stringenddate}", method = RequestMethod.GET)
	public Collection <Coupon> getCouponByDate (HttpServletRequest req, @PathVariable("stringenddate")String stringenddate) throws ParseException
	{
		CompanyFacade companyFacade = getFacade(req);	
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date endDate  = dateFormat.parse(stringenddate);
		return companyFacade.getCouponByDate(endDate);
	}
	
	//works, if coupon updated and creates another coupon in the same name, get method dosent work
	@RequestMapping(value = "/companyws/coupon/title/{title}", method = RequestMethod.GET)
	public Coupon getCouponByTitle (HttpServletRequest req, @PathVariable("title")String title)
	{
		CompanyFacade companyFacade = getFacade(req);	
		return companyFacade.getCouponByTitle(title);
	}
	
}
