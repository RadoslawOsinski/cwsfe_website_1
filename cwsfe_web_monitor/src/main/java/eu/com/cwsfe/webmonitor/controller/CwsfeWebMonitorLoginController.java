package eu.com.cwsfe.webmonitor.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Radoslaw Osinski
 */
@Controller
class CwsfeWebMonitorLoginController {

    private static final Logger LOGGER = LogManager.getLogger(CwsfeWebMonitorLoginController.class);

//    @Autowired
//    private CmsGlobalParamsDAO cmsGlobalParamsDAO;

    @RequestMapping(value="/CWSFE_WEB_MONITOR", method = RequestMethod.GET)
    public String loginPage(ModelMap model) {
        try {
            addMainSiteUrl(model);
//            CmsGlobalParam cwsfeCmsIsConfigured = cmsGlobalParamsDAO.getByCode("CWSFE_WEB_MONITOR_IS_CONFIGURED");
//            if (cwsfeCmsIsConfigured == null || cwsfeCmsIsConfigured.getValue() == null || "N".equals(cwsfeCmsIsConfigured.getValue())) {
//                return "web_monitor/configuration/InitialConfiguration";
//            } else {
                return "web_monitor/login/Login";
//            }
        } catch (DataAccessException e) {
            LOGGER.error("Problem with initial configuration", e);
            return "web_monitor/configuration/InitialConfiguration";
        }
    }

    @RequestMapping(value="/CWSFE_WEB_MONITOR/loginPage")
    public String login(ModelMap model) {
        addMainSiteUrl(model);
        return "web_monitor/login/Login";
    }

    @RequestMapping(value="/CWSFE_WEB_MONITOR/loginFailed", method = RequestMethod.GET)
    public String loginError(ModelMap model) {
        model.addAttribute("error", "true");
        addMainSiteUrl(model);
        return "web_monitor/login/Login";
    }

    @RequestMapping(value="/CWSFE_WEB_MONITOR/logout", method = RequestMethod.GET)
    public String logout(ModelMap model) {
        addMainSiteUrl(model);
        return "web_monitor/login/Login";
    }

    private void addMainSiteUrl(ModelMap model) {
//        model.addAttribute("mainSiteUrl", cmsGlobalParamsDAO.getByCode("MAIN_SITE").getValue());
        model.addAttribute("mainSiteUrl", "http://localhost:8080");
    }

}
