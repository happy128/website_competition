package com.bjpowernode.crm.workbench.web.controller;

import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.utils.*;
import com.bjpowernode.crm.workbench.domain.Customer;
import com.bjpowernode.crm.workbench.domain.UserManage;
import com.bjpowernode.crm.workbench.service.CustomerService;
import com.bjpowernode.crm.workbench.service.UserManageService;
import com.bjpowernode.crm.workbench.service.impl.ActivityServiceImpl;
import com.bjpowernode.crm.workbench.service.impl.CustomerServiceImpl;
import com.bjpowernode.crm.workbench.service.impl.UserManageServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class CustomerController extends HttpServlet {
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("进入到用户管理控制器");

        String path = request.getServletPath();

        if("/workbench/customer/save.do".equals(path)){

            save(request,response);

        }else if("/workbench/customer/delete.do".equals(path)){

            delete(request,response);

        }else if("/workbench/customer/update.do".equals(path)){

            update(request,response);

        }else if("/workbench/customer/detail.do".equals(path)) {

            detail(request, response);

        }else if("/workbench/customer/getCustomerList.do".equals(path)){

            getCustomerList(request,response);

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

        System.out.println("执行客户信息的删除操作");

        String ids[] = request.getParameterValues("id");

        UserManageService as = (UserManageService) ServiceFactory.getService(new UserManageServiceImpl());

        boolean flag = as.delete(ids);

        PrintJson.printJsonFlag(response, flag);


    }



    private void save(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("执行11111111保存操作");

        String id = request.getParameter("id");
        String company_name = request.getParameter("company_name");
        String address = request.getParameter("address");
        String principal = request.getParameter("principal");
        String industry = request.getParameter("industry");
        String salesman = request.getParameter("salesman");
        String consumer_preferences = request.getParameter("consumer_preferences");

        //创建时间：当前系统时间
        String createTime = DateTimeUtil.getSysTime();
        //创建人：当前登录用户
        String createBy = ((User)request.getSession().getAttribute("user")).getName();

        Customer a = new Customer();
        a.setId(id);
        a.setCompany_name(company_name);
        a.setAddress(address);
        a.setPrincipal(principal);
        a.setIndustry(industry);
        a.setSalesman(salesman);
        a.setConsumer_preferences(consumer_preferences);

//        a.setCreateTime(createTime);
//        a.setCreateBy(createBy);

        CustomerService as = (CustomerService) ServiceFactory.getService(new CustomerServiceImpl());

        boolean flag = as.save(a);

        PrintJson.printJsonFlag(response, flag);

    }

    private void getCustomerList(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("取得getCustomerList信息列表");

        CustomerService us = (CustomerService) ServiceFactory.getService(new CustomerServiceImpl());

        List<Customer> uList = us.getCustomerList();

        System.out.println("xmxmxmxmxmmx"+uList);

        PrintJson.printJsonObj(response, uList);

    }

}
