package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.vo.PaginationVO;
import com.bjpowernode.crm.workbench.domain.Customer;


import java.util.List;
import java.util.Map;

public interface CustomerService {

    boolean delete(String[] ids);

    boolean save(Customer a);
    List<String> getCustomerName(String name);
    PaginationVO<Customer> pageList(Map<String, Object> map);
    List<Customer> getCustomerList();
}
