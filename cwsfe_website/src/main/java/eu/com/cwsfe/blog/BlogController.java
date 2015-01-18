package eu.com.cwsfe.blog;

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
public class BlogController extends GenericController {

    public static final String BLOG_VIEW_PATH = "blog/Blog";

    @RequestMapping(value = "/blog", method = RequestMethod.GET)
    public String defaultView(ModelMap model, Locale locale,
                              @RequestParam(value = "currentPage", required = false) Integer currentPage,
                              @RequestParam(value = "categoryId", required = false) Integer categoryId) {
        setPageMetadata(model, locale, "");
        model.addAttribute("additionalJavaScriptCode", "/resources-cwsfe/js/Blog.js");
        if (currentPage == null) {
            currentPage = 0;
        }
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("localeLanguage", locale.getLanguage());
        model.addAttribute("categoryId", categoryId);
        return BLOG_VIEW_PATH;
    }

    @RequestMapping(value = "/blog_category/{categoryId}", method = RequestMethod.GET)
    public String browseWithCategory(ModelMap model, Locale locale,
                                     @RequestParam(value = "currentPage", required = false) Integer currentPage,
                                     @PathVariable("categoryId") Long categoryId) {
        setPageMetadata(model, locale, "");
        model.addAttribute("additionalJavaScriptCode", "/resources-cwsfe/js/Blog.js");
        model.addAttribute("currentPage", 0);
        model.addAttribute("localeLanguage", locale.getLanguage());
        model.addAttribute("categoryId", categoryId);
        return BLOG_VIEW_PATH;
    }

    private void setPageMetadata(ModelMap model, Locale locale, String additionalTitle) {
        model.addAttribute("headerPageTitle", ResourceBundle.getBundle(CWSFE_RESOURCE_BUNDLE, locale).getString("Blog") + " " + additionalTitle);
        model.addAttribute("keywords", setPageKeywords(locale));
        model.addAttribute("additionalCssCode", setAdditionalCss());
    }

    List<Keyword> setPageKeywords(Locale locale) {
        List<Keyword> keywords = new ArrayList<>(5);
        keywords.add(new Keyword(ResourceBundle.getBundle(CWSFE_RESOURCE_BUNDLE, locale).getString("CWSFEBlog")));
        keywords.add(new Keyword(ResourceBundle.getBundle(CWSFE_RESOURCE_BUNDLE, locale).getString("jPalioTipsAndTrix")));
        keywords.add(new Keyword(ResourceBundle.getBundle(CWSFE_RESOURCE_BUNDLE, locale).getString("GroovyTips")));
        keywords.add(new Keyword(ResourceBundle.getBundle(CWSFE_RESOURCE_BUNDLE, locale).getString("GroovyScripts")));
        keywords.add(new Keyword(ResourceBundle.getBundle(CWSFE_RESOURCE_BUNDLE, locale).getString("SQLAdvices")));
        return keywords;
    }

    private List<String> setAdditionalCss() {
        List<String> cssUrl = new ArrayList<>(3);
        cssUrl.add("/resources-cwsfe/img/layout/css/pages-min.css");
        cssUrl.add("/resources-cwsfe/css/Blog-min.css");
        return cssUrl;
    }

    @RequestMapping(value = "/blog/singlePost/{blogPostId}/{blogPostI18nContentId}", method = RequestMethod.GET)
    public String singlePostView(ModelMap model, Locale locale,
                                 @PathVariable("blogPostId") Long blogPostId,
                                 @PathVariable("blogPostI18nContentId") Long blogPostI18nContentId) {
        setPageMetadata(model, locale, "");
        model.addAttribute("additionalJavaScriptCode", "/resources-cwsfe/js/SingleBlogPost.js");
        model.addAttribute("localeLanguage", locale.getLanguage());
        model.addAttribute("blogPostId", blogPostId);
        model.addAttribute("blogPostI18nContentId", blogPostI18nContentId);
        return "blog/SinglePostView";
    }

}
