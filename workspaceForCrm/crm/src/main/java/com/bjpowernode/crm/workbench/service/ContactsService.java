package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.vo.PaginationVO;
import com.bjpowernode.crm.workbench.domain.Contacts;

import java.util.List;
import java.util.Map;

public interface ContactsService {
    boolean delete(String[] ids);

    boolean save(Contacts a);
    PaginationVO<Contacts> pageList(Map<String, Object> map);
    List<Contacts> getContactsList();
}
