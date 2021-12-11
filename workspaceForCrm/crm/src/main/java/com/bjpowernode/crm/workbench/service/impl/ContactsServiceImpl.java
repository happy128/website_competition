package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.utils.SqlSessionUtil;
import com.bjpowernode.crm.vo.PaginationVO;

import com.bjpowernode.crm.workbench.dao.ContactsDao;
import com.bjpowernode.crm.workbench.domain.Contacts;
import com.bjpowernode.crm.workbench.service.ContactsService;


import java.util.List;
import java.util.Map;

public class ContactsServiceImpl implements ContactsService {
    private ContactsDao contactsDao = SqlSessionUtil.getSqlSession().getMapper(ContactsDao.class);

    public boolean delete(String[] ids) {
        boolean flag = true;


        //删除市场活动
        int count3 = contactsDao.delete(ids);
        if(count3!=ids.length){

            flag = false;

        }

        return flag;

    }

    public boolean save(Contacts a) {
        boolean flag = true;

        int count = contactsDao.save(a);
        if(count!=1){

            flag = false;

        }

        return flag;

    }

    public PaginationVO<Contacts> pageList(Map<String, Object> map) {
        return null;
    }


    public List<Contacts> getContactsList(){

        List<Contacts> uList = contactsDao.getContactsList();
        return uList;
    }


}
