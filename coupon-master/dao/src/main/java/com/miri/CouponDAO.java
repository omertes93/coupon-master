package com.miri;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Set;

@Repository
public interface CouponDAO extends JpaRepository<Coupon, Long>{

    default void removeCoupon(Long coupon_id) {
        Coupon coupon = this.getCoupon(coupon_id);
        this.delete(coupon);
    }

    default void updateCoupon(Coupon coupon) {
        this.save(coupon);
    }

    @Query("select c from Coupon c where c.id = ?1")
    Coupon getCoupon(Long id);

    @Query("select c from Coupon c")
    Set<Coupon> getAllCoupons();

    @Query("select c from Coupon c where c.type = :type")
    Set<Coupon> getCouponByType(CouponType type);

    @Transactional
    @Modifying(clearAutomatically = true)
    Set<Coupon> findByendDateBefore(LocalDate currDate);

    default void deleteExpiredCoupons(Set<Coupon> expiredCoupons) {
        this.deleteInBatch(expiredCoupons);
    }

}
