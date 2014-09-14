package eu.com.cwsfe.cms.controllers;

import eu.com.cwsfe.cms.application.monitoring.ServerWatch;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author Radoslaw Osinski
 */
@Controller
public class MonitoringGeneralInformationController implements JsonController {

    @Autowired
    private ServerWatch serverWatch;

    @RequestMapping(value = "/CWSFE_CMS/monitoring/generalInformation", method = RequestMethod.GET)
    public String defaultView(ModelMap model, Locale locale, HttpServletRequest httpServletRequest) {
        model.addAttribute("additionalJavaScriptCode", setAdditionalJS(httpServletRequest.getContextPath()));
        model.addAttribute("breadcrumbs", getBreadcrumbs(locale));
        model.addAttribute("osName", serverWatch.getOSName());
        model.addAttribute("osVersion", serverWatch.getOSVersion());
        model.addAttribute("architecture", serverWatch.getArchitecture());
        model.addAttribute("availableCPUs", serverWatch.getAvailableCPUs());
        model.addAttribute("usedMemoryInMb", serverWatch.usedMemoryInMb());
        model.addAttribute("availableMemoryInMB", serverWatch.availableMemoryInMB());
        return "cms/monitoring/GeneralInformation";
    }

    private Object setAdditionalJS(String contextPath) {
        List<String> jsUrl = new ArrayList<>(3);
        jsUrl.add(contextPath + "/resources-cwsfe-cms/js/cms/monitoring/GeneralInformation.js");
        return jsUrl;
    }

    private List<String> getBreadcrumbs(Locale locale) {
        List<String> breadcrumbs = new ArrayList<>(1);
        breadcrumbs.add("<a href=\"" +
                ServletUriComponentsBuilder.fromCurrentContextPath().path("/CWSFE_CMS/monitoring/generalInformation").build().toUriString() +
                "\" tabindex=\"-1\">" + ResourceBundle.getBundle(CWSFE_CMS_RESOURCE_BUNDLE_PATH, locale).getString("MonitoringGeneralInformation") + "</a>");
        return breadcrumbs;
    }

    @RequestMapping(value = "/CWSFE_CMS/monitoring/generalMemoryInfo", method = RequestMethod.GET, produces = "application/json;charset=UTF-8;pageEncoding=UTF-8")
    @ResponseBody
    public String getGeneralMemoryInfo() {
        JSONObject responseDetailsJson = new JSONObject();
        responseDetailsJson.put("usedMemoryInMb", serverWatch.usedMemoryInMb());
        responseDetailsJson.put("availableMemoryInMB", serverWatch.availableMemoryInMB());
        return responseDetailsJson.toString();
    }

}
