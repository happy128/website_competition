package com.bjpowernode.crm.workbench.dao;

import com.bjpowernode.crm.workbench.domain.Customer;
import com.bjpowernode.crm.workbench.domain.UserManage;

import java.util.List;
import java.util.Map;

public interface CustomerDao {

    List<Customer> getActivityListByCondition(Map<String, Object> map);

    int getTotalByCondition(Map<String, Object> map);

    Customer getCustomerByName(String company);

    int save(Customer cus);

    List<String> getCustomerName(String name);

    List<Customer> getCustomerList();

    int delete(String[] ids);
}
