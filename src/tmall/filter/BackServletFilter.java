package tmall.filter;

import org.apache.commons.lang.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Miracle- on 2020/8/17 18:11
 */
public class BackServletFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String contextPath = request.getServletContext().getContextPath();
        String uri = request.getRequestURI();
        uri = StringUtils.remove(uri,contextPath);
        if (uri.startsWith("/admin")) {
            String servletPath = StringUtils.substringBetween(uri, "_", "_")+"Servlet";
            String method = StringUtils.substringAfterLast(uri, "_");
            request.setAttribute("method",method);
            request.getRequestDispatcher("/"+servletPath).forward(request,response);
            return;
        }
        filterChain.doFilter(request,response);
    }

    @Override
    public void destroy() {

    }
}
