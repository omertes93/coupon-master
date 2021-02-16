package com.miri;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.LinkedList;
import java.util.stream.Collectors;

@Service
public class CompanyFacade implements CouponClientFacade {

    @Autowired
    CompanyDAO companyDAO;

    @Autowired
    CustomerDAO customerDAO;

    @Autowired
    CouponDAO couponDAO;

    public CompanyFacade() {
    }

    Coupon getCoupon(Long coupon_id) {
        return couponDAO.getCoupon(coupon_id);
    }

    Collection<Coupon> getAllCoupons() {
        return couponDAO.getAllCoupons();
    }

    Collection<Coupon> getCouponsByType(Long comp_id, CouponType type) throws RuntimeException {
        Collection<Coupon> companyCoupons = companyDAO.getCoupons(comp_id);
            return companyCoupons.stream()
                    .filter(coupon -> coupon.getType().equals(type))
                    .collect(Collectors.toCollection(LinkedList::new));
    }

    Collection<Coupon> getAllCouponsByCompany(Long comp_id) {
        return companyDAO.getCoupons(comp_id);
    }

    Collection<Coupon> getCouponsByPriceLimit(Long comp_id, Double price) throws RuntimeException {
        Collection<Coupon> companyCoupons = companyDAO.getCoupons(comp_id);
        return companyCoupons.stream().filter(coupon -> coupon.getPrice() <= price)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    Collection<Coupon> getCouponsByDateLimit(Long comp_id, LocalDate endDate) throws RuntimeException {
        Collection<Coupon> companyCoupons = companyDAO.getCoupons(comp_id);
        return companyCoupons.stream().filter(coupon -> coupon.getEndDate().isBefore(endDate))
                .collect(Collectors.toCollection(LinkedList::new));
    }

    void createCoupon(Coupon coupon, Long comp_id) throws ConstraintViolationException {
        companyDAO.createCoupon(comp_id, coupon);
    }

    void removeCoupon(Long comp_id, Long coupon_id) {
        companyDAO.removeCoupon(comp_id, coupon_id);
        customerDAO.removeCoupon(coupon_id);
        couponDAO.removeCoupon(coupon_id);
    }

    void updateCoupon(Coupon coupon) {
        couponDAO.updateCoupon(coupon);
    }

    public CouponClientFacade login(String name, String password, String clientType) {
        return null;
    }

}

