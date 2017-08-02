package com.ran.test.aop.advice.service;


public class BankServiceImpl implements BankService {

	@Override
	public boolean transfer(String form, String to, double account) {
		// TODO Auto-generated method stub
		System.out.println("transfer begin......");
		if(account<100) {
            throw new IllegalArgumentException("最低转账金额不能低于100元");
        }
        System.out.println(form+"向"+to+"交行账户转账"+account+"元");
        return false;
	}

}
