package eu.com.cwsfe.cms.controllers;

import eu.com.cwsfe.cms.dao.CmsGlobalParamsDAO;
import eu.com.cwsfe.cms.dao.CmsRolesDAO;
import eu.com.cwsfe.cms.dao.CmsUserRolesDAO;
import eu.com.cwsfe.cms.dao.CmsUsersDAO;
import eu.com.cwsfe.cms.model.CmsGlobalParam;
import eu.com.cwsfe.cms.model.CmsRole;
import eu.com.cwsfe.cms.model.CmsUser;
import eu.com.cwsfe.cms.model.CmsUserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author Radoslaw Osinski
 */
@Controller
class InitialConfigurationController extends JsonController {

    @Autowired
    private CmsGlobalParamsDAO cmsGlobalParamsDAO;

    @Autowired
    private CmsUsersDAO cmsUsersDAO;

    @Autowired
    private CmsRolesDAO cmsRolesDAO;

    @Autowired
    private CmsUserRolesDAO cmsUserRolesDAO;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private ResourceLoader resourceLoader;

    @RequestMapping(value = "/CWSFE_CMS/configuration/initialConfiguration", method = RequestMethod.GET)
    public String showInitialConfiguration() {
        try {
            CmsGlobalParam cwsfeCmsIsConfigured = cmsGlobalParamsDAO.getByCode("CWSFE_CMS_IS_CONFIGURED");
            if (cwsfeCmsIsConfigured == null || cwsfeCmsIsConfigured.getValue() == null || "N".equals(cwsfeCmsIsConfigured.getValue())) {
                return "cms/configuration/InitialConfiguration";
            } else {
                return "cms/login/Login";
            }
        } catch (DataAccessException e) {
            return "cms/configuration/InitialConfiguration";
        }
    }

    @RequestMapping(value = "/CWSFE_CMS/configuration/addAdminUser", method = RequestMethod.POST)
    public ModelAndView addAdminUser(
            @ModelAttribute(value = "cmsUser") CmsUser cmsUser,
            BindingResult result, Locale locale
    ) {
        ValidationUtils.rejectIfEmpty(result, "username", ResourceBundle.getBundle(CWSFE_CMS_RESOURCE_BUNDLE_PATH, locale).getString("FirstNameMustBeSet"));
        ValidationUtils.rejectIfEmpty(result, "passwordHash", ResourceBundle.getBundle(CWSFE_CMS_RESOURCE_BUNDLE_PATH, locale).getString("PasswordMustBeSet"));
        ModelAndView modelAndView = new ModelAndView();
        if (!result.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            try {
                final ResourceDatabasePopulator rdp = new ResourceDatabasePopulator();
                Resource resource = resourceLoader.getResource("classpath:/META-INF/release/cms.sql");
                rdp.addScript(resource);
                rdp.populate(dataSource.getConnection());
            } catch (SQLException e) {
                for (int i = 0; i < result.getAllErrors().size(); i++) {
                    errorMessage.append(result.getAllErrors().get(i).getCode());
                    errorMessage.append("<br/>");
                }
                modelAndView.getModel().put("errors", errorMessage);
            }
            if (errorMessage.toString().isEmpty()) {
                cmsUser.setPasswordHash(BCrypt.hashpw(cmsUser.getPasswordHash(), BCrypt.gensalt(13)));
                cmsUser.setId(cmsUsersDAO.add(cmsUser));
                CmsRole cwsfeCmsAdminRole = cmsRolesDAO.getByCode("ROLE_CWSFE_CMS_ADMIN");
                cmsUserRolesDAO.add(new CmsUserRole(cmsUser.getId(), cwsfeCmsAdminRole.getId()));
                CmsGlobalParam cwsfeCmsIsConfigured = cmsGlobalParamsDAO.getByCode("CWSFE_CMS_IS_CONFIGURED");
                cwsfeCmsIsConfigured.setValue("Y");
                cmsGlobalParamsDAO.update(cwsfeCmsIsConfigured);
                modelAndView.setView(new RedirectView("/CWSFE_CMS/", true, false, false));
            }
        } else {
            StringBuilder errorMessage = new StringBuilder();
            ;
            for (int i = 0; i < result.getAllErrors().size(); i++) {
                errorMessage.append(result.getAllErrors().get(i).getCode());
                errorMessage.append("<br/>");
            }
            modelAndView.getModel().put("errors", errorMessage);
            modelAndView.setView(new RedirectView("/CWSFE_CMS/configuration/initialConfiguration", true, false, false));
        }
        return modelAndView;
    }

}
