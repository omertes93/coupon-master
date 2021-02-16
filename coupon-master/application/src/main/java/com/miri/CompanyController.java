package com.miri;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collection;

@CrossOrigin //(origins = "http://domain2.com", maxAge = 3600)
@RestController                               //For serving REST requests, all responses default format is JSON
@RequestMapping("/rest/api/")  		          //this creates the services main API endpoint
public class CompanyController {

    @Autowired
    private CompanyFacade companyService;

    @Autowired
    private CouponTypeConverter couponTypeConverter;


    @GetMapping("/getallcoupons")
    public Collection<Coupon> getAllCouponse() {
        return companyService.getAllCoupons();
    }

    @GetMapping("/getallcompanycoupons/{comp_id}")
    public Collection<Coupon> getAllCouponsByCompany(@PathVariable("comp_id") Long comp_id) {
        return companyService.getAllCouponsByCompany(comp_id);
    }

    @GetMapping("/getcouponsbytype/{comp_id}/{type}")
    public GeneralResponse getCouponsByType(@PathVariable("comp_id") Long comp_id, @PathVariable("type") String type) {
        CouponType couponType=couponTypeConverter.convertToEntityAttribute(type);
        try {
            return new GeneralResponse(companyService.getCouponsByType(comp_id, couponType));
        } catch (Exception e) {
            return new GeneralResponse(e);
        }
    }

    @GetMapping("/getcouponsbyprice/{comp_id}/{price}")
    public GeneralResponse getCouponsByPriceLimit(
            @PathVariable("comp_id") Long comp_id,
            @PathVariable("price") Double price) {
        try {
            return new GeneralResponse(companyService.getCouponsByPriceLimit(comp_id, price));
        } catch (Exception e) {
            return new GeneralResponse(e);
        }
    }

    @GetMapping("/getcouponsbydatelimit/{comp_id}/{enddate}")
    public GeneralResponse getCouponsByType(
            @PathVariable("comp_id") Long comp_id,
            @PathVariable("enddate") LocalDate end_date) {
        try {
            return new GeneralResponse(companyService.getCouponsByDateLimit(comp_id, end_date));
        } catch (Exception e) {
            return new GeneralResponse(e);
        }
    }

    @PostMapping("/createcoupon/{comp_id}")
    public GeneralResponse createCoupon(@RequestBody Coupon coupon, @PathVariable("comp_id") Long comp_id) {
        try {
            companyService.createCoupon(coupon, comp_id);
            return new GeneralResponse("Coupon added successfully!");
        } catch (Exception e) {
            return new GeneralResponse(e);
        }

    }

    @GetMapping("/deletecoupon/{comp_id}/{coupon_id}")
    public void deleteCoupon(@PathVariable("comp_id") Long comp_id, @PathVariable("coupon_id") Long coupon_id) {
        companyService.removeCoupon(comp_id, coupon_id);
    }

}

