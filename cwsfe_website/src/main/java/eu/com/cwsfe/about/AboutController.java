package eu.com.cwsfe.about;

import eu.com.cwsfe.GenericController;
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
public class AboutController implements GenericController {

    @RequestMapping(value = "/about", method = RequestMethod.GET)
    public String showLoginPage(ModelMap model, Locale locale) {
        setPageMetadata(model, locale);
        return "about/About";
    }

    private void setPageMetadata(ModelMap model, Locale locale) {
        model.addAttribute("headerPageTitle", ResourceBundle.getBundle(CWSFE_RESOURCE_BUNDLE, locale).getString("AboutCompany"));
        model.addAttribute("keywords", setPageKeywords(locale));
        model.addAttribute("additionalJavaScriptCode", "/resources-cwsfe/js/About.js");
    }

    List<Keyword> setPageKeywords(Locale locale) {
        List<Keyword> keywords = new ArrayList<>(5);
        keywords.add(new Keyword(ResourceBundle.getBundle(CWSFE_RESOURCE_BUNDLE, locale).getString("AboutCWSFECompany")));
        keywords.add(new Keyword(ResourceBundle.getBundle(CWSFE_RESOURCE_BUNDLE, locale).getString("CompleteWorkingSolutionForEveryone")));
        return keywords;
    }

}
