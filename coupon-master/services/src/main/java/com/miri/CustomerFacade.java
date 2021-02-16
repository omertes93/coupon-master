package com.miri;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomerFacade implements CouponClientFacade {

    @Autowired
    CustomerDAO customerDAO;

    @Autowired
    CouponDAO couponDAO;

    public CustomerFacade() {
    }

    GeneralResponse purchaseCoupon(Long custId, Long couponId) {
        Customer customer = customerDAO.getCustomer(custId);
        Coupon coupon = couponDAO.getCoupon(couponId);
        if (coupon.getAmount() == 0) {
            return new GeneralResponse("No coupons available!");
        }
        else if (LocalDate.now().isAfter(coupon.getEndDate())) {
            return new GeneralResponse("Coupon expired in " + coupon.getEndDate());
        }
        else if (customer.getCoupons().contains(coupon)) {
            return new GeneralResponse("Coupon already purchased!");
        }
        else {
            customerDAO.addCoupon(customer, coupon);  //adding the coupon to customer & saving it
            coupon.setAmount(coupon.getAmount() - 1);
            couponDAO.updateCoupon(coupon);
            return new GeneralResponse("Coupon purchased successfully!");
        }
    }

    GeneralResponse getAllPurchasedCoupons(Long cust_id) {
        return new GeneralResponse(customerDAO.getAllPurchasedCoupons(cust_id));
    }

    Collection<Coupon> getAllPurchasedCouponsByType(Long cust_id, CouponType type) throws RuntimeException {
        Set<Coupon> customerCoupons = customerDAO.getAllPurchasedCoupons(cust_id);
        return customerCoupons.stream().filter(coupon -> coupon.getType().equals(type))
                .collect(Collectors.toCollection(LinkedList::new));
    }

    Collection<Coupon> getAllPurchasedCouponsByPrice(Long cust_id, Double price) throws RuntimeException {
        Set<Coupon> customerCoupons = customerDAO.getAllPurchasedCoupons(cust_id);
        return customerCoupons.stream().filter(coupon -> coupon.getPrice() <= price)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    public CouponClientFacade login(String name, String password, String clientType) {
        return null;
    }

}
