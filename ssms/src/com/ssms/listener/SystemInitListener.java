package com.ssms.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.ssms.bean.SystemInfo;
import com.ssms.dao.impl.BaseDaoImpl;

/**
 * 系统初始化
 * @author liuzhuojin
 *
 */
public class SystemInitListener implements ServletContextListener {

    public SystemInitListener() {
    	
    }

    public void contextInitialized(ServletContextEvent sce)  { 
    	ServletContext application = sce.getServletContext();
    	//获取系统初始化对象
    	SystemInfo sys = (SystemInfo) new BaseDaoImpl().getObject(SystemInfo.class, "SELECT * FROM system", null);
    	//放到域中
    	application.setAttribute("systemInfo", sys);
    }

    public void contextDestroyed(ServletContextEvent sce)  { 
         
    }
	
}
