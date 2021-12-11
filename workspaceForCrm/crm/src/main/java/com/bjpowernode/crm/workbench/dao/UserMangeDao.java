package com.bjpowernode.crm.workbench.dao;

import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.workbench.domain.UserManage;

import java.util.List;
import java.util.Map;

public interface UserMangeDao {
    int save(UserManage a);

    List<UserManage> getActivityListByCondition(Map<String, Object> map);

    int getTotalByCondition(Map<String, Object> map);

    int delete(String[] ids);


    int update(UserManage a);

    UserMangeDao detail(String id);


    List<UserManage> getUserManageList();

    List<UserManage> getUserManageList_Id(String id);
}
