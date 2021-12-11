package com.bjpowernode.crm.workbench.dao;

import com.bjpowernode.crm.workbench.domain.Contacts;

import java.util.List;

public interface ContactsDao {
    int delete(String[] ids);

    int save(Contacts con);

    List<Contacts> getContactsList();
}
