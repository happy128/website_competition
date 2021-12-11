package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.settings.dao.UserDao;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.utils.SqlSessionUtil;
import com.bjpowernode.crm.vo.PaginationVO;
import com.bjpowernode.crm.workbench.dao.UserMangeDao;
import com.bjpowernode.crm.workbench.dao.ActivityRemarkDao;
import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.domain.ActivityRemark;
import com.bjpowernode.crm.workbench.domain.UserManage;
import com.bjpowernode.crm.workbench.service.UserManageService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserManageServiceImpl implements UserManageService {
    private UserMangeDao userManageDao = SqlSessionUtil.getSqlSession().getMapper(UserMangeDao.class);

    public boolean save(UserManage a) {
        boolean flag = true;

        int count = userManageDao.save(a);
        if(count!=1){

            flag = false;

        }

        return flag;

    }

    public PaginationVO<UserManage> pageList(Map<String, Object> map) {
        //取得total
        int total = userManageDao.getTotalByCondition(map);

        //取得dataList
        List<UserManage> dataList = userManageDao.getActivityListByCondition(map);

        //创建一个vo对象，将total和dataList封装到vo中
        PaginationVO<UserManage> vo = new PaginationVO<UserManage>();
        vo.setTotal(total);
        vo.setDataList(dataList);

        //将vo返回
        return vo;
    }

    public boolean delete(String[] ids) {
        boolean flag = true;


        //删除市场活动
        int count3 = userManageDao.delete(ids);
        if(count3!=ids.length){

            flag = false;

        }

        return flag;

    }

    public Map<String, Object> getUserListAndActivity(String id) {
        return null;
    }

    public List<UserManage> getUserManageList() {

        List<UserManage> uList = userManageDao.getUserManageList();

        return uList;
    }
    public List<UserManage> getUserManageList_Id(String id) {

        //取uList
        List<UserManage> uList = userManageDao.getUserManageList_Id(id);


        //返回map就可以了
        return uList;
    }

    public boolean update(UserManage a) {

        boolean flag = true;

        int count = userManageDao.update(a);
        if(count!=1){

            flag = false;

        }

        return flag;
    }

    public UserManage detail(String id) {
        return null;
    }

    public List<UserManage> getRemarkListByAid(String activityId) {

        List<UserManage> arList = userManageDao.getUserManageList_Id(activityId);

        System.out.println("进入到IMpl");

        return arList;
    }

    public boolean deleteRemark(String id) {
        return false;
    }

    public boolean saveRemark(ActivityRemark ar) {
        return false;
    }

    public boolean updateRemark(ActivityRemark ar) {
        return false;
    }

    public List<UserManage> getActivityListByClueId(String clueId) {
        return null;
    }

    public List<UserManage> getActivityListByNameAndNotByClueId(Map<String, String> map) {
        return null;
    }

    public List<UserManage> getActivityListByName(String aname) {
        return null;
    }
}
