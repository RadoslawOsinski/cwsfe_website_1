package eu.com.cwsfe.main;

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
public class MainController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String showMainPage(ModelMap model, Locale locale) {
        setPageMetadata(model, locale);
        return "main/Main";
    }

    @RequestMapping(value = "/index.html", method = RequestMethod.GET)
    public String showMainPageByIndexHtml(ModelMap model, Locale locale) {
        setPageMetadata(model, locale);
        return "main/Main";
    }

    private void setPageMetadata(ModelMap model, Locale locale) {
        model.addAttribute("headerPageTitle", "CWSFE");
        model.addAttribute("keywords", setPageKeywords(locale));
        model.addAttribute("additionalCssCode", setAdditionalCss());
        model.addAttribute("additionalJavaScriptCode", setAdditionalJS());
    }

    public List<Keyword> setPageKeywords(Locale locale) {
        List<Keyword> keywords = new ArrayList<>(5);
        keywords.add(new Keyword(ResourceBundle.getBundle("cwsfe_i18n", locale).getString("CompleteWorkingSolutionForEveryone")));
        keywords.add(new Keyword(ResourceBundle.getBundle("cwsfe_i18n", locale).getString("RadoslawOsinskiCompany")));
        keywords.add(new Keyword(ResourceBundle.getBundle("cwsfe_i18n", locale).getString("JPalioDeveloper")));
        keywords.add(new Keyword(ResourceBundle.getBundle("cwsfe_i18n", locale).getString("UniqueSkills")));
        keywords.add(new Keyword(ResourceBundle.getBundle("cwsfe_i18n", locale).getString("MasovianDeveloper")));
        return keywords;
    }

    private List<String> setAdditionalCss() {
        List<String> cssUrl = new ArrayList<>(3);
        cssUrl.add("/resources-cwsfe/css/tipsy/tipsy-min.css");
        return cssUrl;
    }

    private Object setAdditionalJS() {
        List<String> jsUrl = new ArrayList<>(3);
        jsUrl.add("/resources-cwsfe/js/tipsy/tipsy.js");
        jsUrl.add("/resources-cwsfe/js/jquery/cycle.all.min.js");
        jsUrl.add("/resources-cwsfe/js/Main.js");
        return jsUrl;
    }

}
