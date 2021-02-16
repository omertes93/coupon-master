package com.miri;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "company" ,uniqueConstraints = {@UniqueConstraint(columnNames = {"comp_name"})} )
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "comp_name", updatable = false)
    private String compName;
    private String password;
    private String email;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "company_coupon", joinColumns = @JoinColumn(name = "comp_id"), // this class
            inverseJoinColumns = @JoinColumn(name = "coupon_id") // the other class
    )
    private Set<Coupon> coupons;

    public Company() {
    }

    public Company(Long id, String compName, String password, String email,Set<Coupon> coupons) {
        this.id = id;
        this.compName = compName;
        this.password = password;
        this.email = email;
        this.coupons = coupons;
    }

    public Long getId() {
        return id;
    }

    public String getCompName() {
        return compName;
    }

    public void setCompName(String compName) {
        this.compName = compName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Coupon> getCoupons() {
        return coupons;
    }

    public void setCoupons(Set<Coupon> coupons) {
        this.coupons = coupons;
    }

    @Override
    public String toString() {
        return "Company{" + "id=" + id + ", compName='" + compName + '\'' + ", password='" + password + '\''
                + ", email='" + email + '\'' + ", coupons=" + coupons + '}';
    }

}
