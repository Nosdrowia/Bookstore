package filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebFilter(servletNames = {"Cart","Category"})
public class SessionTimeoutFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request,ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;

        HttpSession session = req.getSession(false);
        HttpServletResponse res = (HttpServletResponse) response;

        if(session != null && !"true".equals(req.getParameter("forceFilter"))){
            chain.doFilter(request, response);
        }
        else{
            res.sendRedirect(req.getContextPath() + "/home");
        }

    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void destroy() {}

}