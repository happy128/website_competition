package com.bjpowernode.crm.workbench.web.controller;

import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.utils.DateTimeUtil;
import com.bjpowernode.crm.utils.PrintJson;
import com.bjpowernode.crm.utils.ServiceFactory;
import com.bjpowernode.crm.workbench.domain.Contacts;
import com.bjpowernode.crm.workbench.domain.Customer;
import com.bjpowernode.crm.workbench.domain.UserManage;
import com.bjpowernode.crm.workbench.service.ContactsService;
import com.bjpowernode.crm.workbench.service.CustomerService;
import com.bjpowernode.crm.workbench.service.UserManageService;
import com.bjpowernode.crm.workbench.service.impl.ActivityServiceImpl;
import com.bjpowernode.crm.workbench.service.impl.ContactsServiceImpl;
import com.bjpowernode.crm.workbench.service.impl.CustomerServiceImpl;
import com.bjpowernode.crm.workbench.service.impl.UserManageServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ContactsController extends HttpServlet {
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("进入到用户管理控制器");

        String path = request.getServletPath();

        if("/workbench/contacts/save.do".equals(path)){

            save(request,response);

        }else if("/workbench/contacts/delete.do".equals(path)){

            delete(request,response);

        }else if("/workbench/contacts/update.do".equals(path)){

            update(request,response);

        }else if("/workbench/contacts/detail.do".equals(path)) {

            detail(request, response);

        }else if("/workbench/contacts/getContactsList.do".equals(path)){

            getContactsList(request,response);

        }


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

        request.getRequestDispatcher("/workbench/contacts/detail.jsp").forward(request, response);


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

        System.out.println("执行客户信息的删除操作");

        String ids[] = request.getParameterValues("id");

        ContactsService as = (ContactsService) ServiceFactory.getService(new ContactsServiceImpl());

        boolean flag = as.delete(ids);

        PrintJson.printJsonFlag(response, flag);


    }



    private void save(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("执行保存操作");

        String id = request.getParameter("id");
        String fullname = request.getParameter("fullname");
        String email = request.getParameter("email");
        String mphone = request.getParameter("mphone");
        String job = request.getParameter("job");
        String address = request.getParameter("address");
        String description = request.getParameter("description");
        //创建时间：当前系统时间
        String createTime = DateTimeUtil.getSysTime();
        //创建人：当前登录用户
        String createBy = ((User)request.getSession().getAttribute("user")).getName();

        Contacts a = new Contacts();
        a.setId(id);
        a.setFullname(fullname);
        a.setEmail(email);
        a.setMphone(mphone);
        a.setJob(job);
        a.setAddress(address);
        a.setDescription(description);
//        a.setCreateTime(createTime);
//        a.setCreateBy(createBy);

        ContactsService as = (ContactsService) ServiceFactory.getService(new ContactsServiceImpl());

        boolean flag = as.save(a);

        PrintJson.printJsonFlag(response, flag);

    }

    private void getContactsList(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("取得getContactsList信息列表");

        ContactsService us = (ContactsService) ServiceFactory.getService(new ContactsServiceImpl());

        List<Contacts> uList = us.getContactsList();

        PrintJson.printJsonObj(response, uList);

    }
}
