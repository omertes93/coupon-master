# coupon
4 maven modules:
- Entities
- Dao
- Application (includes controllers, test app)
- Services (Facades)

# DB dump
Dump20180819.sql file, located in the coupon root dir

# Application test class
- Execute TestCoupon.java from IDE.
Unfortunately, not all implemented endpoints were included)

# Application API 
Base endpoint: "/rest/api/"  <br>
Admin
 - @GetMapping("/getcustomer/{id}")
 - @GetMapping("/getallcustomers")
 - @PostMapping("/createcompany") - requires JSON payload with: <br>
  {
      "compName": "[unique name]",
      "email": "[email]",
      "password": "[password]"
   }
  - @GetMapping("/deletecompany/{id}")
  - @GetMapping("/deletecustomer/{cust_id}")
  - @PostMapping("/updatecompany") - requires JSON payload with: <br>
  {
  "id": [company id(readonly)],
  "compName": "[company name (won't get updated)]",
  "password": "[password]",
  "email": "[email]"
  }
  - @PostMapping("/updatecustomer") - requires JSON payload with: <br>
  {
    "id": [customer id (readonly)],
    "custName": "[customer name (won't get updated)]",
    "password":"[password]"
  }
  - @GetMapping("/getcompany/{id}")
  - @GetMapping("/getallcompanies")
  
Company
- @GetMapping("/getallcoupons")
- @GetMapping("/getallcompanycoupons/{comp_id}")
- @GetMapping("/getcouponsbytype/{comp_id}/{type}")
- @GetMapping("/getcouponsbyprice/{comp_id}/{price}")
- @GetMapping("/getcouponsbydatelimit/{comp_id}/{enddate}")
- @PostMapping("/createcoupon/{comp_id}") - requires JSON payload with: <br>
{
  "title": "[unique title]",
  "amount": [amount],
  "startDate":"[date yyyy-mm-dd]",
  "endDate":"[date yyyy-mm-dd]",
  "image":null,
  "message":"[message]",
  "price": [price],
  "type": [0-7]
}
- @GetMapping("/deletecoupon/{comp_id}/{coupon_id}")

Customer
- @PostMapping("/purchasecoupon/{cust_id}/{coupon_id}")
- @GetMapping("/getallpurchasedcouponsbytype/{customerId}/{typeId}")
- @GetMapping("/getallpurchasedcouponsbyprice/{customerId}/{price}")



