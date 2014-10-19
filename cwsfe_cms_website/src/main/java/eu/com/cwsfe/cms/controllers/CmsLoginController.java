package eu.com.cwsfe.cms.controllers;

import eu.com.cwsfe.cms.dao.CmsGlobalParamsDAO;
import eu.com.cwsfe.cms.model.CmsGlobalParam;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Radoslaw Osinski
 */
@Controller
class CmsLoginController {

    private static final Logger LOGGER = LogManager.getLogger(CmsLoginController.class);

    @Autowired
    private CmsGlobalParamsDAO cmsGlobalParamsDAO;

    @RequestMapping(value = "/CWSFE_CMS", method = RequestMethod.GET)
    public String loginPage(ModelMap model) {
        try {
            addMainSiteUrl(model);
            CmsGlobalParam cwsfeCmsIsConfigured = cmsGlobalParamsDAO.getByCode("CWSFE_CMS_IS_CONFIGURED");
            if (cwsfeCmsIsConfigured == null || cwsfeCmsIsConfigured.getValue() == null || "N".equals(cwsfeCmsIsConfigured.getValue())) {
                return "cms/configuration/InitialConfiguration";
            } else {
                return "cms/login/Login";
            }
        } catch (DataAccessException e) {
            LOGGER.error("Problem with initial configuration", e);
            return "cms/configuration/InitialConfiguration";
        }
    }

    @RequestMapping(value = "/CWSFE_CMS/loginPage")
    public String login(ModelMap model) {
        addMainSiteUrl(model);
        return "cms/login/Login";
    }

    @RequestMapping(value = "/CWSFE_CMS/loginFailed", method = RequestMethod.GET)
    public String loginError(ModelMap model) {
        model.addAttribute("error", "true");
        addMainSiteUrl(model);
        return "cms/login/Login";
    }

    @RequestMapping(value = "/CWSFE_CMS/logout", method = RequestMethod.GET)
    public String logout(ModelMap model) {
        addMainSiteUrl(model);
        return "cms/login/Login";
    }

    private void addMainSiteUrl(ModelMap model) {
        model.addAttribute("mainSiteUrl", cmsGlobalParamsDAO.getByCode("MAIN_SITE").getValue());
    }

}
