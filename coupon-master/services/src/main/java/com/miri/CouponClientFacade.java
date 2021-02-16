package com.miri;

import org.springframework.stereotype.Service;

@Service
public interface CouponClientFacade {

    CouponClientFacade login(String name, String password, String clientType);

}
