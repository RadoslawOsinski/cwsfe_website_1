package eu.com.cwsfe.webmonitor.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Radoslaw Osinski
 */
@Controller
class MainWebMonitorController extends JsonController {

    @RequestMapping(value="/CWSFE_WEB_MONITOR/Main", method = RequestMethod.GET)
    public String printDashboard(ModelMap model, Principal principal, HttpServletRequest httpServletRequest) {
        String name = principal.getName();
        model.addAttribute("username", name);
        model.addAttribute("additionalCssCode", setAdditionalCss(httpServletRequest.getContextPath()));
        model.addAttribute("additionalJavaScriptCode", setAdditionalJS(httpServletRequest.getContextPath()));
        model.addAttribute("mainSiteUrl", "http://localhost:8080");
        return "web_monitor/main/Main";
    }

    private List<String> setAdditionalCss(String contextPath) {
        List<String> cssUrl = new ArrayList<>(3);
        return cssUrl;
    }

    private List<String> setAdditionalJS(String contextPath) {
        List<String> jsUrl = new ArrayList<>(3);
        return jsUrl;
    }

}
