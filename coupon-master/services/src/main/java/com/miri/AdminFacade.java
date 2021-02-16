package com.miri;

import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;

@Service
public class AdminFacade implements CouponClientFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(CouponSystem.class);
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

    @Autowired
    CompanyDAO companyDAO;

    @Autowired
    CustomerDAO customerDAO;

    @Autowired
    CouponDAO couponDAO;

    public AdminFacade() {
    }

    Company createCompany(Company company) throws ConstraintViolationException {
        return companyDAO.createCompany(company);
    }

    Company updateCompany(Company company) {
        return companyDAO.updateCompany(company);
    }

    void removeCompany(Company company) {
        Set<Coupon> companyCoupons = company.getCoupons();
        Collection<Customer> allCustomers = customerDAO.getAllCustomers();
        for (Coupon coupon:companyCoupons) {                      //for each company coupon
            for (Customer customer:allCustomers){                 //for each customer

                Set<Coupon> currCustomerCoupons=customer.getCoupons();  //get purchased coupons
                for (Coupon currCoupon:currCustomerCoupons){      //for each purchased coupon
                    if (Objects.equals(currCoupon.getId(), coupon.getId())){  //if coupon exists
                        currCustomerCoupons.remove(currCoupon);   //remove from customer coupons
                    }
                }
                customerDAO.save(customer);   //save the customer
            }
        }
        companyDAO.delete(company);  //will delete from company, coupon & company-coupon
    }

    Company getCompany(Long id) {
        return companyDAO.getCompany(id);
    }

    Set<Company> getAllCompanies() {
        return companyDAO.getAllCompanies();
    }

    Customer getCustomer(Long cust_id) {
        return customerDAO.getCustomer(cust_id);
    }

    Set<Customer> getAllCustomers() {
        return customerDAO.getAllCustomers();
    }

    Customer createCustomer(Customer customer) {
        return customerDAO.createCustomer(customer);
    }

    Customer updateCustomer(Customer customer) {
        return customerDAO.updateCustomer(customer);
    }

    void removeCustomer(Long cust_id) {
        customerDAO.removeCustomer(cust_id);
    }

    Set<Coupon> findByEndDateBefore(LocalDate currDate) {
        return couponDAO.findByendDateBefore(currDate);
    }

    void deleteExpiredCoupons(Set<Coupon> expiredCoupons) {
        couponDAO.deleteExpiredCoupons(expiredCoupons);
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void dailyCouponExpirationTask() {
        LOGGER.info("Cron Task :: Execution Time - {}", DATE_TIME_FORMATTER.format(LocalDateTime.now()));
        LocalDate today = LocalDate.now();
//TODO: put following in method to avoid code duplication
        Set<Coupon> expiredCoupons = couponDAO.findByendDateBefore(today);
        Set<Customer> allCustomers = customerDAO.getAllCustomers();
        for (Coupon coupon : expiredCoupons) {
            for (Customer customer : allCustomers) {
                Set<Coupon> currCustomerCoupons = customer.getCoupons();
                for (Coupon currCoupon : currCustomerCoupons) {
                    if (Objects.equals(currCoupon.getId(), coupon.getId())) {
                        currCustomerCoupons.remove(currCoupon);
                    }
                }
                customerDAO.save(customer);
            }
        }
        couponDAO.deleteExpiredCoupons(expiredCoupons);
    }

    public CouponClientFacade login(String name, String password, String clientType) {
        return null;
    }

}
