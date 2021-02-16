package com.miri;

public enum CouponType {

    //like public static final ... (constant def)
    RESTAURANTS("rest"),
    ELECTICITY("elec"),
    FOOD("food"),
    HEALTH("hlth"),
    SPORTS("sprt"),
    CAMPING("camp"),
    TRAVELING("trvl"),
    FASHION("fshn");

    private final String code;

    CouponType(String code) {      //constructor is private, like for singleton
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
