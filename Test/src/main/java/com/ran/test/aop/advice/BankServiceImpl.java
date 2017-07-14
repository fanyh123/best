package com.ran.test.aop.advice;

import org.springframework.stereotype.Service;

@Service
public class BankServiceImpl implements BankService {

	@Override
	public boolean transfer(String form, String to, double account) {
		// TODO Auto-generated method stub
		if(account<100) {
            throw new IllegalArgumentException("最低转账金额不能低于100元");
        }
        System.out.println(form+"向"+to+"交行账户转账"+account+"元");
        return false;
	}

}
