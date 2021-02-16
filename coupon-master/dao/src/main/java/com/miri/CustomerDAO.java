package com.miri;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface CustomerDAO extends JpaRepository<Customer, Long> {

    @Query("select c from Customer c where c.id = ?1")
    Customer getCustomer(Long id);

    @Query("select c from Customer c")
    Set<Customer> getAllCustomers();


    default Customer createCustomer(Customer customer) {
        return this.save(customer);
    }

    default void removeCustomer(Long cust_id) {
        this.deleteById(cust_id);
    }

    default Customer updateCustomer(Customer customer) {
        return this.save(customer);
    }

    default void removeCoupon(Long coupon_id) {
        Set<Customer> allCustomers = this.getAllCustomers();
        for (Customer customer:allCustomers) {
            Set<Coupon> customerCoupons = customer.getCoupons();
            for (Coupon coupon:customerCoupons) {
                if (coupon.getId().equals(coupon_id)) {
                    customerCoupons.remove(coupon);
                    break;
                }
            }
            customer.setCoupons(customerCoupons);
            this.save(customer);
        }
    }

    @Query("select c.coupons from Customer c WHERE c.id = ?1")
    Set<Coupon> getAllPurchasedCoupons(Long cust_id);

    default void addCoupon(Customer customer, Coupon coupon) {
        Set<Coupon> customerCoupons = customer.getCoupons();
        customerCoupons.add(coupon);
        this.save(customer);
    }

//    boolean login(String custName, String password);
}

