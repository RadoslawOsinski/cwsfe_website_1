package eu.com.cwsfe.services;

import eu.com.cwsfe.cms.dao.*;
import eu.com.cwsfe.cms.model.*;
import eu.com.cwsfe.model.Keyword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author Radoslaw Osinski
 */
@Controller
class ServicesController {

    @Autowired
    private CmsFoldersDAO cmsFoldersDAO;
    @Autowired
    private CmsNewsDAO cmsNewsDAO;
    @Autowired
    private NewsTypesDAO newsTypesDAO;
    @Autowired
    private CmsLanguagesDAO langsDAO;
    @Autowired
    private CmsNewsI18nContentsDAO cmsNewsI18nContentsDAO;

    @RequestMapping(value = "/services", method = RequestMethod.GET)
    public String showServicesPage(ModelMap model, Locale locale) {
        setPageMetadata(model, locale);
        CmsFolder cmsFolder = cmsFoldersDAO.getByFolderName("Services");
        NewsType newsType = newsTypesDAO.getByFolderName("Services");
        CmsNews cmsNews = cmsNewsDAO.getByNewsTypeFolderAndNewsCode(newsType.getId(), cmsFolder.getId(), "Services");
        Lang currentPLang = langsDAO.getByCode(locale.getLanguage());
        if (currentPLang == null) {
            currentPLang = langsDAO.getByCode("en");
        }
        CmsNewsI18nContent cmsNewsI18nContent = cmsNewsI18nContentsDAO.getByLanguageForNews(cmsNews.getId(), currentPLang.getId());
        model.addAttribute("cmsNewsI18nContent", cmsNewsI18nContent);
        return "services/Services";
    }

    @RequestMapping(value = "/services/serviceDetails", method = RequestMethod.GET)
    public String showMixedServicesPage(ModelMap model, Locale locale) {
        setPageMetadata(model, locale);
        CmsFolder cmsFolder = cmsFoldersDAO.getByFolderName("Services");
        NewsType newsType = newsTypesDAO.getByFolderName("Services");
        CmsNews cmsNews = cmsNewsDAO.getByNewsTypeFolderAndNewsCode(newsType.getId(), cmsFolder.getId(), "ServiceDetails");
        Lang currentPLang = langsDAO.getByCode(locale.getLanguage());
        if (currentPLang == null) {
            currentPLang = langsDAO.getByCode("en");
        }
        CmsNewsI18nContent cmsNewsI18nContent = cmsNewsI18nContentsDAO.getByLanguageForNews(cmsNews.getId(), currentPLang.getId());
        model.addAttribute("cmsNewsI18nContent", cmsNewsI18nContent);
        return "services/ServicesDetails";
    }

    @RequestMapping(value = "/services/serviceStages", method = RequestMethod.GET)
    public String showServiceStagesPage(ModelMap model, Locale locale) {
        setPageMetadata(model, locale);
        CmsFolder cmsFolder = cmsFoldersDAO.getByFolderName("Services");
        NewsType newsType = newsTypesDAO.getByFolderName("Services");
        CmsNews cmsNews = cmsNewsDAO.getByNewsTypeFolderAndNewsCode(newsType.getId(), cmsFolder.getId(), "ServiceStages");
        Lang currentPLang = langsDAO.getByCode(locale.getLanguage());
        if (currentPLang == null) {
            currentPLang = langsDAO.getByCode("en");
        }
        CmsNewsI18nContent cmsNewsI18nContent = cmsNewsI18nContentsDAO.getByLanguageForNews(cmsNews.getId(), currentPLang.getId());
        model.addAttribute("cmsNewsI18nContent", cmsNewsI18nContent);
        return "services/ServicesStages";
    }

    private void setPageMetadata(ModelMap model, Locale locale) {
        model.addAttribute("headerPageTitle", ResourceBundle.getBundle("cwsfe_i18n", locale).getString("Services"));
        model.addAttribute("keywords", setPageKeywords(locale));
        model.addAttribute("additionalCssCode", setAdditionalCss());
        model.addAttribute("additionalJavaScriptCode", setAdditionalJS());
    }

    public List<Keyword> setPageKeywords(Locale locale) {
        List<Keyword> keywords = new ArrayList<>(5);
        keywords.add(new Keyword(ResourceBundle.getBundle("cwsfe_i18n", locale).getString("CWSFEServices")));
        keywords.add(new Keyword(ResourceBundle.getBundle("cwsfe_i18n", locale).getString("UniqueSkills")));
        keywords.add(new Keyword(ResourceBundle.getBundle("cwsfe_i18n", locale).getString("jPalioProgramming")));
        keywords.add(new Keyword(ResourceBundle.getBundle("cwsfe_i18n", locale).getString("MasovianDeveloper")));
        return keywords;
    }

    private List<String> setAdditionalCss() {
        List<String> cssUrl = new ArrayList<>(3);
        cssUrl.add("/resources-cwsfe/css/tipsy/tipsy-min.css");
        cssUrl.add("/resources-cwsfe/css/Services-min.css");
        return cssUrl;
    }

    private Object setAdditionalJS() {
        List<String> jsUrl = new ArrayList<>(3);
        jsUrl.add("/resources-cwsfe/js/tipsy/tipsy.js");
        jsUrl.add("/resources-cwsfe/js/jquery/jquery.accordion.js");
        jsUrl.add("/resources-cwsfe/js/jquery/cycle.all.min.js");
        jsUrl.add("/resources-cwsfe/js/Services.js");
        return jsUrl;
    }
}