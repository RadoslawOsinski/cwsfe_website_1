package eu.com.cwsfe.about;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import eu.com.cwsfe.model.Keyword;

import java.util.*;

/**
 * @author Radoslaw Osinski
 */
@Controller
public class AboutController {

    @RequestMapping(value = "/about", method = RequestMethod.GET)
    public String showLoginPage(ModelMap model, Locale locale) {
        setPageMetadata(model, locale);
        return "about/About";
    }

    private void setPageMetadata(ModelMap model, Locale locale) {
        model.addAttribute("headerPageTitle", ResourceBundle.getBundle("cwsfe_i18n", locale).getString("AboutCompany"));
        model.addAttribute("keywords", setPageKeywords(locale));
        model.addAttribute("additionalJavaScriptCode", setAdditionalJS());
    }

    List<Keyword> setPageKeywords(Locale locale) {
        List<Keyword> keywords = new ArrayList<>(5);
        keywords.add(new Keyword(ResourceBundle.getBundle("cwsfe_i18n", locale).getString("AboutCWSFECompany")));
        keywords.add(new Keyword(ResourceBundle.getBundle("cwsfe_i18n", locale).getString("CompleteWorkingSolutionForEveryone")));
        return keywords;
    }

    private Object setAdditionalJS() {
        List<String> jsUrl = new ArrayList<>(3);
        jsUrl.add("/resources-cwsfe/js/prettyPhoto/prettyPhoto.js");
        jsUrl.add("/resources-cwsfe/js/jquery/cycle.all.min.js");
        jsUrl.add("/resources-cwsfe/js/About.js");
        return jsUrl;
    }

}
