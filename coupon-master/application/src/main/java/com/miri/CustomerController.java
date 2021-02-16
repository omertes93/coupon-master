package com.miri;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@CrossOrigin //(origins = "http://domain2.com", maxAge = 3600)
@RestController                               //For serving REST requests, all responses default format is JSON
@RequestMapping("/rest/api/")  		          //this creates the services main API endpoint
public class CustomerController {

    @Autowired
    private CustomerFacade customerServices;


    @PostMapping("/purchasecoupon/{cust_id}/{coupon_id}")
    public GeneralResponse purchaseCoupon(
            @PathVariable("cust_id") Long custId,
            @PathVariable("coupon_id") Long couponId) {
        try {
            return customerServices.purchaseCoupon(custId, couponId);
        } catch (Exception e) {
            return new GeneralResponse(e);
        }

    }

    @GetMapping("/getallpurchasedcouponsbytype/{customerId}/{typeId}")
    public Collection<Coupon> getAllPurchasedCouponsByType(
            @PathVariable("customerId") Long customerId,
            @PathVariable("typeId") CouponType type) {
        return customerServices.getAllPurchasedCouponsByType(customerId,type);
    }

    @GetMapping("/getallpurchasedcouponsbyprice/{customerId}/{price}")
    public Collection<Coupon> getAllPurchasedCouponsByPrice(
            @PathVariable("customerId") Long customerId,
            @PathVariable("price") Double price) {
        return customerServices.getAllPurchasedCouponsByPrice(customerId,price);
    }

}
