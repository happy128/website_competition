package com.bjpowernode.crm.workbench.web.controller;

import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.settings.service.impl.UserServiceImpl;
import com.bjpowernode.crm.utils.DateTimeUtil;
import com.bjpowernode.crm.utils.PrintJson;
import com.bjpowernode.crm.utils.ServiceFactory;
import com.bjpowernode.crm.utils.UUIDUtil;
import com.bjpowernode.crm.vo.PaginationVO;
import com.bjpowernode.crm.workbench.domain.ActivityRemark;
import com.bjpowernode.crm.workbench.domain.UserManage;
import com.bjpowernode.crm.workbench.service.ActivityService;
import com.bjpowernode.crm.workbench.service.UserManageService;
import com.bjpowernode.crm.workbench.service.impl.ActivityServiceImpl;
import com.bjpowernode.crm.workbench.service.impl.UserManageServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserManageController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("进入到用户管理控制器");

        String path = request.getServletPath();

        if("/workbench/usermanage/getUserList.do".equals(path)){

            getUserList(request,response);

        }else if("/workbench/usermanage/save.do".equals(path)){

            save(request,response);

        }else if("/workbench/usermanage/delete.do".equals(path)){

            delete(request,response);

        }else if("/workbench/usermanage/update.do".equals(path)){

            update(request,response);

        }else if("/workbench/usermanage/detail.do".equals(path)){

            detail(request,response);

        }else if("/workbench/usermanage/getRemarkListByAid.do".equals(path)){

            getRemarkListByAid(request,response);

        }else if("/workbench/usermanage/deleteRemark.do".equals(path)){

            deleteRemark(request,response);

        }else if("/workbench/usermanage/saveRemark.do".equals(path)){

            saveRemark(request,response);

        }else if("/workbench/usermanage/updateRemark.do".equals(path)){

            updateRemark(request,response);

        }else if("/workbench/usermanage/getUserManageList.do".equals(path)){

            getUserManageList(request,response);

        }


    }

    private void updateRemark(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("执行修改备注的操作");

        String id = request.getParameter("id");
        String noteContent = request.getParameter("noteContent");
        String editTime = DateTimeUtil.getSysTime();
        String editBy = ((User)request.getSession().getAttribute("user")).getName();
        String editFlag = "1";

        ActivityRemark ar = new ActivityRemark();

        ar.setId(id);
        ar.setNoteContent(noteContent);
        ar.setEditFlag(editFlag);
        ar.setEditBy(editBy);
        ar.setEditTime(editTime);

        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        boolean flag = as.updateRemark(ar);

        Map<String,Object> map = new HashMap<String,Object>();
        map.put("success", flag);
        map.put("ar", ar);

        PrintJson.printJsonObj(response, map);

    }

    private void saveRemark(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("执行添加备注操作");

        String noteContent = request.getParameter("noteContent");
        String activityId = request.getParameter("activityId");
        String id = UUIDUtil.getUUID();
        String createTime = DateTimeUtil.getSysTime();
        String createBy = ((User)request.getSession().getAttribute("user")).getName();
        String editFlag = "0";

        ActivityRemark ar = new ActivityRemark();
        ar.setId(id);
        ar.setNoteContent(noteContent);
        ar.setActivityId(activityId);
        ar.setCreateBy(createBy);
        ar.setCreateTime(createTime);
        ar.setEditFlag(editFlag);

        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        boolean flag = as.saveRemark(ar);

        Map<String,Object> map = new HashMap<String,Object>();
        map.put("success", flag);
        map.put("ar", ar);

        PrintJson.printJsonObj(response, map);
    }

    private void deleteRemark(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("删除备注操作");

        String id = request.getParameter("id");

        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        boolean flag = as.deleteRemark(id);

        PrintJson.printJsonFlag(response, flag);

    }

    private void getRemarkListByAid(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("根据市场活动id，取得备注信息列表");

        String activityId = request.getParameter("activityId");

        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        List<ActivityRemark> arList = as.getRemarkListByAid(activityId);

        PrintJson.printJsonObj(response, arList);

    }

    private void detail(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {

        System.out.println("进入到跳转到详细信息页的操作");

//        String id = request.getParameter("id");
//
//        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
//
//        Activity a = as.detail(id);
//
//        request.setAttribute("a", a);

        request.getRequestDispatcher("/workbench/usermanage/detail.jsp").forward(request, response);


    }

    private void update(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("执行市场活动修改操作");

        String id = request.getParameter("id");
        String loginAct = request.getParameter("loginAct");
        String name = request.getParameter("name");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String cost = request.getParameter("cost");
        String description = request.getParameter("description");
        //修改时间：当前系统时间
        String editTime = DateTimeUtil.getSysTime();
        //修改人：当前登录用户
        String editBy = ((User)request.getSession().getAttribute("user")).getName();

        UserManage a = new UserManage();
        a.setId(id);
        a.setLoginAct(loginAct);
        a.setName(startDate);
        //a.setLoginPwd(owner);
        a.setName(name);
        a.setEmail(endDate);
        a.setExpireTime(description);
        a.setDeptno(endDate);

        a.setEditBy(editBy);
        a.setEditTime(editTime);

        UserManageService as = (UserManageService) ServiceFactory.getService(new ActivityServiceImpl());

        boolean flag = as.update(a);

        PrintJson.printJsonFlag(response, flag);

    }


    private void delete(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("执行用户的删除操作");

        String ids[] = request.getParameterValues("id");

        UserManageService as = (UserManageService) ServiceFactory.getService(new UserManageServiceImpl());

        boolean flag = as.delete(ids);

        PrintJson.printJsonFlag(response, flag);


    }



    private void save(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("执行角色添加操作");

        String id = UUIDUtil.getUUID();
        String loginAct = request.getParameter("loginAct");
        String loginPwd = request.getParameter("loginPwd");
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String expireTime = request.getParameter("expireTime");
        String deptno = request.getParameter("deptno");

        //创建时间：当前系统时间
        String createTime = DateTimeUtil.getSysTime();
        //创建人：当前登录用户
        String createBy = ((User)request.getSession().getAttribute("user")).getName();

        UserManage a = new UserManage();
        a.setId(id);
        a.setLoginAct(loginAct);
        a.setName(name);
        a.setLoginPwd(loginPwd);
        a.setEmail(email);
        a.setExpireTime(expireTime);
        a.setDeptno(deptno);

        a.setCreateTime(createTime);
        a.setCreateBy(createBy);

        UserManageService as = (UserManageService) ServiceFactory.getService(new UserManageServiceImpl());

        boolean flag = as.save(a);

        PrintJson.printJsonFlag(response, flag);

    }

    private void getUserList(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("取得用户guanli信息列表");

        UserService us = (UserService) ServiceFactory.getService(new UserServiceImpl());

        List<User> uList = us.getUserList();

        System.out.println("444444444444444444444444444"+uList);

        PrintJson.printJsonObj(response, uList);

    }
    private void getUserManageList(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("取得用户******************************");

        UserManageService us = (UserManageService) ServiceFactory.getService(new UserManageServiceImpl());

        List<UserManage> uList = us.getUserManageList();

        System.out.println("senc"+uList);

        PrintJson.printJsonObj(response, uList);

    }
}
