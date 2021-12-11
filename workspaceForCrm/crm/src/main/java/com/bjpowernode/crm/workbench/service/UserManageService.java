package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.vo.PaginationVO;
import com.bjpowernode.crm.workbench.domain.UserManage;
import com.bjpowernode.crm.workbench.domain.ActivityRemark;

import java.util.List;
import java.util.Map;

public interface UserManageService {
    boolean save(UserManage a);

    PaginationVO<UserManage> pageList(Map<String, Object> map);

    boolean delete(String[] ids);


    boolean update(UserManage a);

    UserManage detail(String id);

    List<UserManage> getRemarkListByAid(String activityId);


    boolean saveRemark(ActivityRemark ar);

    boolean updateRemark(ActivityRemark ar);

    List<UserManage> getUserManageList();

    List<UserManage> getUserManageList_Id(String id);
}
