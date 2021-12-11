package com.bjpowernode.crm.workbench.service.impl;


import com.bjpowernode.crm.utils.SqlSessionUtil;


import com.bjpowernode.crm.vo.PaginationVO;
import com.bjpowernode.crm.workbench.dao.CustomerDao;
import com.bjpowernode.crm.workbench.domain.Customer;
import com.bjpowernode.crm.workbench.service.CustomerService;

import java.util.List;
import java.util.Map;

public class CustomerServiceImpl implements CustomerService {

    private CustomerDao customerDao = SqlSessionUtil.getSqlSession().getMapper(CustomerDao.class);

    public boolean delete(String[] ids) {
        boolean flag = true;


        //删除市场活动
        int count3 = customerDao.delete(ids);
        if(count3!=ids.length){

            flag = false;

        }

        return flag;

    }

    public boolean save(Customer a) {
        boolean flag = true;

        int count = customerDao.save(a);
        if(count!=1){

            flag = false;

        }

        return flag;

    }

    public List<String> getCustomerName(String name) {

        List<String> sList = customerDao.getCustomerName(name);

        return sList;
    }


    public List<Customer> getCustomerList(){

        List<Customer> uList = customerDao.getCustomerList();
        return uList;
    }

    public PaginationVO<Customer> pageList(Map<String, Object> map) {
        //取得total
        int total = customerDao.getTotalByCondition(map);

        //取得dataList
        List<Customer> dataList = customerDao.getActivityListByCondition(map);

        //创建一个vo对象，将total和dataList封装到vo中
        PaginationVO<Customer> vo = new PaginationVO<Customer>();
        vo.setTotal(total);
        vo.setDataList(dataList);

        //将vo返回
        return vo;
    }
}
















