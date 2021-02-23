package idv.cm.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Log4jServletContextListener implements ServletContextListener {
	
	private static final Logger logger = LogManager.getLogger();

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		logger.info("---contextInitialized---");
		ServletContext ctx = sce.getServletContext();
		String projectName = ctx.getInitParameter("log4jContextName");
		logger.info(projectName);
		ctx.setAttribute("projectName", projectName);
		ctx.log("projectName set to "+projectName);
		//ServletContextListener.super.contextInitialized(sce);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		logger.info("---contextDestroyed---");
		//ServletContextListener.super.contextDestroyed(sce);
	}
}
