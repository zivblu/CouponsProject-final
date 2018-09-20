package com.example.demo.DBDAO;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.common.CouponType;
import com.example.demo.connection.ConnectionPool;
import com.example.demo.connection.DBConnection;
import com.example.demo.entities.Coupon;
import com.example.demo.entities.Customer;
import com.example.demo.exceptions.CouponNotExistException;
import com.example.demo.exceptions.CustomerExistException;
import com.example.demo.exceptions.CustomerNotExistException;

@Component
public class CustomerDBDAO implements CustomerDAO{

	@Autowired
	CustomerRepo customerRepo;
	@Autowired
	CouponRepo couponRepo;
	
	@Override
	public void createCustomer(Customer cust) throws CustomerExistException {
		DBConnection dbConnection = ConnectionPool.getInstance().getConnection();
		if (customerRepo.existsBycustName(cust.getCustName()))
		throw new CustomerExistException ("Customer Allready Exists");
		else customerRepo.save(cust);
		ConnectionPool.getInstance().returnUserConenction(dbConnection); 

	}

	@Override
	public void removeCustomer(Customer cust) throws CustomerNotExistException {
		DBConnection dbConnection = ConnectionPool.getInstance().getConnection();
		if (customerRepo.existsBycustName(cust.getCustName()))
		{
			customerRepo.delete(cust);
		}
		else throw new CustomerNotExistException ("Customer not Exists");
		ConnectionPool.getInstance().returnUserConenction(dbConnection); 
	}

	@Override
	public void updateCustomer(Customer cust) throws CustomerNotExistException {
		DBConnection dbConnection = ConnectionPool.getInstance().getConnection();
		Customer custfromdb = customerRepo.findCustomerByCustName(cust.getCustName());
		if (custfromdb != null)
		{
			custfromdb.setPassword(cust.getPassword());
			customerRepo.save(cust);
			ConnectionPool.getInstance().returnUserConenction(dbConnection); 
		}
		else throw new CustomerNotExistException ("Customer not Exists");
	}

	@Override
	public Customer getCustomer(long id) {
		DBConnection dbConnection = ConnectionPool.getInstance().getConnection();
		Customer custfromdb = customerRepo.findOne(id);
		if (custfromdb != null)
		{
			return customerRepo.findOne(id);
		}
		ConnectionPool.getInstance().returnUserConenction(dbConnection); 
		return null;
	}

	@Override
	public Collection<Customer> getAllCustomers() {
		DBConnection dbConnection = ConnectionPool.getInstance().getConnection();
		Collection<Customer> listOfAllCustomers = (Collection<Customer>) customerRepo.findAll();
		ConnectionPool.getInstance().returnUserConenction(dbConnection); 
		return listOfAllCustomers;
	}

	@Override
	public Collection<Coupon> getCoupons() {
		DBConnection dbConnection = ConnectionPool.getInstance().getConnection();
		Collection<Coupon> listOfAllCoupons = (Collection<Coupon>) couponRepo.findAll();
		ConnectionPool.getInstance().returnUserConenction(dbConnection); 
		return listOfAllCoupons;
	}

	@Override
	public boolean login(String custName, String password) {
		if (customerRepo.existsByCustNameAndPassword(custName, password))
		{
			System.out.println("login succsesful, welcome " + custName);
			return true;
		}
		else return false;
	}
	
	@Override
	public Customer getCustomerByCustomerName(String custName) {
		DBConnection dbConnection = ConnectionPool.getInstance().getConnection();
		Customer custfromdb = customerRepo.findCustomerByCustName(custName);
		if (custfromdb != null)
		{
			return customerRepo.findCustomerByCustName(custName);
		}
		ConnectionPool.getInstance().returnUserConenction(dbConnection); 
		return null;
	}
	
	@Override
	public void purchaseCoupon(long custId, Coupon coup) throws CouponNotExistException {
		DBConnection dbConnection = ConnectionPool.getInstance().getConnection();
		Collection<Coupon> coupfromdb = customerRepo.findIfCustomerCanBuyCoupon(custId,coup.getEndDate(), coup.getAmount());
		if (coupfromdb != null)
		{
			couponRepo.save(coupfromdb);
			ConnectionPool.getInstance().returnUserConenction(dbConnection); 
		}
		else throw new CouponNotExistException ("Coupon Not Exists");
	}
	
	@Override
	public Collection<Coupon> getAllPurchasedCouponsByType(long custId, CouponType type) {
		DBConnection dbConnection = ConnectionPool.getInstance().getConnection();
		Collection<Coupon> listOfPurchasedCouponsByType = customerRepo.findAllPurchasedCouponsByType(custId, type);
		ConnectionPool.getInstance().returnUserConenction(dbConnection); 
		return listOfPurchasedCouponsByType;
	}
	
	@Override
	public Collection<Coupon> getAllPurchasedCouponsByPrice(long custId, double price) {
		DBConnection dbConnection = ConnectionPool.getInstance().getConnection();
		Collection<Coupon> listOfPurchasedCouponsByPrice = customerRepo.findAllPurchasedCouponsByPrice(custId, price);
		ConnectionPool.getInstance().returnUserConenction(dbConnection); 
		return listOfPurchasedCouponsByPrice;
	}
	
	@Override
	public Coupon findCouponInCustomerDB(long customerLoggedIn, String title) {
		DBConnection dbConnection = ConnectionPool.getInstance().getConnection();
		Coupon custCoupon = customerRepo.findCouponInCustomerDB(customerLoggedIn, title);
		ConnectionPool.getInstance().returnUserConenction(dbConnection); 
		return custCoupon;
	}

}
