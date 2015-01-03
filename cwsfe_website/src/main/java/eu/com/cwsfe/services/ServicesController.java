package eu.com.cwsfe.services;

import eu.com.cwsfe.GenericController;
import eu.com.cwsfe.model.Keyword;
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
class ServicesController extends GenericController {

    @RequestMapping(value = "/services", method = RequestMethod.GET)
    public String showServicesPage(ModelMap model, Locale locale) {
        setPageMetadata(model, locale);
        model.addAttribute("additionalJavaScriptCode", "/resources-cwsfe/js/Services.js");
        return "services/Services";
    }

    @RequestMapping(value = "/services/serviceDetails", method = RequestMethod.GET)
    public String showMixedServicesPage(ModelMap model, Locale locale) {
        setPageMetadata(model, locale);
        model.addAttribute("additionalJavaScriptCode", "/resources-cwsfe/js/ServicesDetails.js");
        return "services/ServicesDetails";
    }

    @RequestMapping(value = "/services/serviceStages", method = RequestMethod.GET)
    public String showServiceStagesPage(ModelMap model, Locale locale) {
        setPageMetadata(model, locale);
        model.addAttribute("additionalJavaScriptCode", "/resources-cwsfe/js/ServicesStages.js");
        return "services/ServicesStages";
    }

    private void setPageMetadata(ModelMap model, Locale locale) {
        model.addAttribute("headerPageTitle", ResourceBundle.getBundle(CWSFE_RESOURCE_BUNDLE, locale).getString("Services"));
        model.addAttribute("keywords", setPageKeywords(locale));
        model.addAttribute("additionalCssCode", setAdditionalCss());
        model.addAttribute("localeLanguage", locale.getLanguage());
    }

    public List<Keyword> setPageKeywords(Locale locale) {
        List<Keyword> keywords = new ArrayList<>(5);
        keywords.add(new Keyword(ResourceBundle.getBundle(CWSFE_RESOURCE_BUNDLE, locale).getString("CWSFEServices")));
        keywords.add(new Keyword(ResourceBundle.getBundle(CWSFE_RESOURCE_BUNDLE, locale).getString("UniqueSkills")));
        keywords.add(new Keyword(ResourceBundle.getBundle(CWSFE_RESOURCE_BUNDLE, locale).getString("jPalioProgramming")));
        keywords.add(new Keyword(ResourceBundle.getBundle(CWSFE_RESOURCE_BUNDLE, locale).getString("MasovianDeveloper")));
        return keywords;
    }

    private List<String> setAdditionalCss() {
        List<String> cssUrl = new ArrayList<>(3);
        cssUrl.add("/resources-cwsfe/css/tipsy/tipsy-min.css");
        cssUrl.add("/resources-cwsfe/css/Services-min.css");
        return cssUrl;
    }

}
