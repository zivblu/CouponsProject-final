package com.example.demo.webService;

import java.util.Collection;

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
import com.example.demo.facades.CustomerFacade;
import com.example.demo.facades.CompanyFacade;

@RestController
@CrossOrigin("*")
public class CustomerWS {

	@Autowired
	CouponSystem couponSystem;
	
	private CustomerFacade getFacade(HttpServletRequest req)
	{
	// the line below is only for testing
	// --fake login--	
	//	CustomerFacade customerFacade = (CustomerFacade) couponSystem.login("ziv", "ziv1234", ClientType.CUSTOMER);
	//	return customerFacade;
		
		CustomerFacade customerFacade = (CustomerFacade) req.getSession().getAttribute("customerFacade");
		return customerFacade;
	}
	
	//why postman creates 2 same coupons to customer
	@RequestMapping(value = "/customerws/getallcoupons/{id}", method = RequestMethod.PUT)
	public void PurchaseCoupon (HttpServletRequest req, @PathVariable("id") int id)
	{
		CustomerFacade customerFacade = getFacade(req);
		Coupon coup = customerFacade.getCoupon(id);
		customerFacade.PurchaseCoupon(coup);
	}
	
	@RequestMapping(value = "/customerws/getallcoupons/{id}", method = RequestMethod.GET)
	public Coupon getCoupon (HttpServletRequest req, @PathVariable("id")long id)
	{
		CustomerFacade customerFacade = getFacade(req);	
		return customerFacade.getCoupon(id);
	}
	
	@RequestMapping(value = "/customerws/getallcoupons", method = RequestMethod.GET)
	public Collection <Coupon> getAllCoupons(HttpServletRequest req)
	{
		CustomerFacade customerFacade = getFacade(req);	
		return customerFacade.getAllCoupons();
	}
	
	//works
	@RequestMapping(value = "/customerws/getpurchasedcoupons", method = RequestMethod.GET)
	public Collection<Coupon> getAllPurchasedCoupons (HttpServletRequest req)
	{
		CustomerFacade customerFacade = getFacade(req);
		return customerFacade.getAllPurchasedCoupons();
	}
	
	//works
	@RequestMapping(value = "/customerws/coupon/type/{type}", method = RequestMethod.GET)
	public Collection<Coupon> getAllPurchasedCouponsByType (HttpServletRequest req, @PathVariable("type")CouponType type)
	{
		CustomerFacade customerFacade = getFacade(req);
		return customerFacade.getAllPurchasedCouponsByType(type);
	}
	
	//works
	@RequestMapping(value = "/customerws/coupon/price/{price}", method = RequestMethod.GET)
	public Collection<Coupon> getAllPurchasedCouponsByPrice (HttpServletRequest req, @PathVariable("price")double price)
	{
		CustomerFacade customerFacade = getFacade(req);
		return customerFacade.getAllPurchasedCouponsByPrice(price);
	}
	
	//works, but brings back one coupon when 2 are created under the same title
	@RequestMapping(value = "/customerws/coupon/title/{title}", method = RequestMethod.GET)
	public Coupon getCouponFromCustomerDB (HttpServletRequest req, @PathVariable("title")String title)
	{
		CustomerFacade customerFacade = getFacade(req);
		return customerFacade.getCouponFromCustomerDB(title);
	}
	
}
