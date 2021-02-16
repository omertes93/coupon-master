package com.miri;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * test
 */
@CrossOrigin //(origins = "http://domain2.com", maxAge = 3600)
@RestController                               //For serving REST requests, all responses default format is JSON
@RequestMapping("/rest/api/")  		          //this creates the services main API endpoint
public class AdminController {

    @Autowired
    private CustomerFacade customerServices;

    @Autowired
    private CompanyFacade companyService;

    @Autowired
    private AdminFacade adminService;

    @Autowired
    private CouponTypeConverter couponTypeConverter;


    @GetMapping("/getcustomer/{id}")
    public Customer getCustomer(@PathVariable("id") Long id) {
        return adminService.getCustomer(id);
    }

    @GetMapping("/getallcustomers")
    public Collection<Customer> getCustomer() {
        return adminService.getAllCustomers();
    }

    @PostMapping("/createcompany")
    public GeneralResponse createCompany(@RequestBody Company company) {
        try {
            return new GeneralResponse(adminService.createCompany(company));
        } catch (Exception e) {
            return new GeneralResponse(e);
        }
    }

    @GetMapping("/deletecompany/{id}")
    public void removeCompany(@PathVariable("id") Long id) {
        adminService.removeCompany(adminService.getCompany(id));
    }

    @GetMapping("/deletecustomer/{cust_id}")
    public void removeCustomer(@PathVariable("cust_id") Long cust_id) {
        adminService.removeCustomer(cust_id);
    }

    @PostMapping("/updatecompany")
    public Company updateCompany(@RequestBody Company company) {
        return adminService.updateCompany(company);
    }

    @PostMapping("/updatecustomer")
    public Customer updateCustomer(@RequestBody Customer customer) {
        return adminService.updateCustomer(customer);
    }

    @GetMapping("/getcompany/{id}")
    public Company getCompany(@PathVariable("id") Long id) {
        return adminService.getCompany(id);
    }

    @GetMapping("/getallcompanies")
    public GeneralResponse getAllCompanies() {
        try {
            return new GeneralResponse(adminService.getAllCompanies());
        } catch (Exception e) {
            return new GeneralResponse(e);
        }
    }

    @PostMapping("/createcustomer")
    public Customer createCustomer(@RequestBody Customer customer) {
        return adminService.createCustomer(customer);
    }

}
