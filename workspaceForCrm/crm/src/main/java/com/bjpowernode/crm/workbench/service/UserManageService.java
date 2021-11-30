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

    Map<String, Object> getUserListAndActivity(String id);

    boolean update(UserManage a);

    UserManage detail(String id);

    List<ActivityRemark> getRemarkListByAid(String activityId);

    boolean deleteRemark(String id);

    boolean saveRemark(ActivityRemark ar);

    boolean updateRemark(ActivityRemark ar);

    List<UserManage> getActivityListByClueId(String clueId);

    List<UserManage> getActivityListByNameAndNotByClueId(Map<String, String> map);

    List<UserManage> getActivityListByName(String aname);

    List<UserManage> getUserManageList();
}
