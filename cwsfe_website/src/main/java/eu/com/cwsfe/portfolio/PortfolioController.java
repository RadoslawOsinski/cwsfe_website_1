package eu.com.cwsfe.portfolio;

import eu.com.cwsfe.GenericController;
import eu.com.cwsfe.model.Keyword;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author Radoslaw Osinski
 */
@Controller
class PortfolioController extends GenericController {

    private void setPageMetadata(ModelMap model, Locale locale, String portfolioTitle) {
        model.addAttribute("headerPageTitle", ResourceBundle.getBundle(CWSFE_RESOURCE_BUNDLE, locale).getString("Portfolio") + " " + portfolioTitle);
        model.addAttribute("keywords", setPageKeywords(locale, portfolioTitle));
        model.addAttribute("additionalCssCode", setAdditionalCss());
    }

    public List<Keyword> setPageKeywords(Locale locale, String portfolioTitle) {
        List<Keyword> keywords = new ArrayList<>(5);
        keywords.add(new Keyword(ResourceBundle.getBundle(CWSFE_RESOURCE_BUNDLE, locale).getString("CWSFEPortfolio")));
        keywords.add(new Keyword(ResourceBundle.getBundle(CWSFE_RESOURCE_BUNDLE, locale).getString("CWSFEProjects")));
        keywords.add(new Keyword(portfolioTitle));
        return keywords;
    }

    private List<String> setAdditionalCss() {
        List<String> cssUrl = new ArrayList<>(3);
        cssUrl.add("/resources-cwsfe/css/tipsy/tipsy-min.css");
        cssUrl.add("/resources-cwsfe/css/prettyPhoto/prettyPhoto-min.css");
        cssUrl.add("/resources-cwsfe/img/layout/css/pages-min.css");
        cssUrl.add("/resources-cwsfe/css/Portfolio-min.css");
        return cssUrl;
    }

    @RequestMapping(value = "/portfolio", method = RequestMethod.GET)
    public String listPortfolio(ModelMap model, Locale locale,
                                             @RequestParam(value = "currentPage", required = false) Integer currentPage,
                                             @RequestParam(value = "newsFolder", required = false) String newsFolder
    ) {
        setPageMetadata(model, locale, "");
        model.addAttribute("additionalJavaScriptCode", "/resources-cwsfe/js/Portfolio.js");
        if (currentPage == null) {
            currentPage = 0;
        }
        model.addAttribute("localeLanguage", locale.getLanguage());
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("newsFolder", newsFolder);
        return "portfolio/Portfolio";
    }

    @RequestMapping(value = "/portfolio/singleNews/{cmsNewsId}/{cmsNewsI18nContentsId}", method = RequestMethod.GET)
    public String singleNewsView(ModelMap model, Locale locale, @PathVariable("cmsNewsId") Integer cmsNewsId, @PathVariable("cmsNewsI18nContentsId") Integer cmsNewsI18nContentsId) {
        setPageMetadata(model, locale, "");
        model.addAttribute("additionalJavaScriptCode", "/resources-cwsfe/js/SinglePortfolioEntry.js");
        model.addAttribute("cmsNewsId", cmsNewsId);
        model.addAttribute("cmsNewsI18nContentsId", cmsNewsI18nContentsId);
        return "portfolio/PortfolioSingleView";
    }

}

