package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.settings.dao.UserDao;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.utils.SqlSessionUtil;
import com.bjpowernode.crm.vo.PaginationVO;
import com.bjpowernode.crm.workbench.dao.UserMangeDao;
import com.bjpowernode.crm.workbench.dao.ActivityRemarkDao;
import com.bjpowernode.crm.workbench.domain.ActivityRemark;
import com.bjpowernode.crm.workbench.domain.UserManage;
import com.bjpowernode.crm.workbench.service.UserManageService;

import java.util.List;
import java.util.Map;

public class UserManageServiceImpl implements UserManageService {
    private UserMangeDao userManageDao = SqlSessionUtil.getSqlSession().getMapper(UserMangeDao.class);
    private ActivityRemarkDao activityRemarkDao = SqlSessionUtil.getSqlSession().getMapper(ActivityRemarkDao.class);
    private UserDao userDao = SqlSessionUtil.getSqlSession().getMapper(UserDao.class);

    public boolean save(UserManage a) {
        boolean flag = true;

        int count = userManageDao.save( a);
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

        //查询出需要删除的备注的数量
        int count1 = activityRemarkDao.getCountByAids(ids);

        //删除备注，返回受到影响的条数（实际删除的数量）
        int count2 = activityRemarkDao.deleteByAids(ids);

        if(count1!=count2){

            flag = false;

        }

        //删除市场活动
//        int count3 = UserMangeDao.delete(ids);
//        if(count3!=ids.length){
//
//            flag = false;
//
//        }

        return flag;

    }

    public Map<String, Object> getUserListAndActivity(String id) {
        return null;
    }

    public List<UserManage> getUserManageList() {

        List<UserManage> uList = userManageDao.getUserManageList();

        return uList;
    }

    public boolean update(UserManage a) {
        return false;
    }

    public UserManage detail(String id) {
        return null;
    }

    public List<ActivityRemark> getRemarkListByAid(String activityId) {
        return null;
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
