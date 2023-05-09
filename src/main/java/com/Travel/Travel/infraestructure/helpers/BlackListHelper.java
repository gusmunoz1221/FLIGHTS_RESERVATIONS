package com.Travel.Travel.infraestructure.helpers;

import com.Travel.Travel.util.exceptions.ForBiddenCustomerException;
import org.springframework.stereotype.Component;

@Component
public class BlackListHelper {
    public void isBlackListCustomer(String idCustomer){
        if(idCustomer.equals("GOTW771012HMRGR087")){
            throw new ForBiddenCustomerException();
        }
    }
}
