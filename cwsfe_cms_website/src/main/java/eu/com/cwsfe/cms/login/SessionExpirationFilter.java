//package eu.com.cwsfe.cms.login;
//
//import org.springframework.beans.factory.InitializingBean;
//import org.springframework.util.Assert;
//
//import javax.servlet.*;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import java.io.IOException;
//
///**
// * Created by Radoslaw Osinski.
// */
//public class SessionExpirationFilter implements Filter, InitializingBean {
//
//    private String expiredUrl;
//
//    public void afterPropertiesSet() throws Exception {
//        Assert.hasText(expiredUrl, "ExpiredUrl required");
//    }
//
//    /**
//     * Does nothing. We use IoC container lifecycle services instead.
//     */
//    public void destroy() {
//    }
//
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//            throws IOException, ServletException {
//        Assert.isInstanceOf(HttpServletRequest.class, request, "Can only process HttpServletRequest");
//        Assert.isInstanceOf(HttpServletResponse.class, response, "Can only process HttpServletResponse");
//
//        HttpServletRequest httpRequest = (HttpServletRequest) request;
//        HttpServletResponse httpResponse = (HttpServletResponse) response;
//
//        String path = httpRequest.getServletPath();
//// if (path.indexOf("login.jsp")<0 && path.indexOf("logout")<0 && path.indexOf("index.jsp")<0)
//// {
//        HttpSession session = httpRequest.getSession(false);
//        if (session == null && !httpRequest.isRequestedSessionIdValid()) {
//            String targetUrl = httpRequest.getContextPath() + "/j_spring_security_logout";
//            httpResponse.sendRedirect(httpResponse.encodeRedirectURL(targetUrl));
//            return;
//        }
//// }
//        chain.doFilter(request, response);
//    }
//
//    /**
//     * Does nothing. We use IoC container lifecycle services instead.
//     *
//     * @param arg0 ignored
//     * @throws ServletException ignored
//     */
//    public void init(FilterConfig arg0) throws ServletException {
//    }
//
//    public void setExpiredUrl(String expiredUrl) {
//        this.expiredUrl = expiredUrl;
//    }
//
//}
