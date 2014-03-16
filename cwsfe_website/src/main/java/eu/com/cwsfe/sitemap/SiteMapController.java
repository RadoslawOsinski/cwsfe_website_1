package eu.com.cwsfe.sitemap;

import eu.com.cwsfe.cms.dao.BlogPostsDAO;
import eu.com.cwsfe.cms.dao.CmsLanguagesDAO;
import eu.com.cwsfe.cms.dao.CmsNewsDAO;
import eu.com.cwsfe.cms.model.Lang;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Locale;

/**
 * @author Radoslaw Osinski
 */
@Controller
class SiteMapController {

    @Autowired
    private CmsNewsDAO cmsNewsDAO;
    @Autowired
    private CmsLanguagesDAO langsDAO;
    @Autowired
    private BlogPostsDAO blogPostsDAO;

    @RequestMapping(value = "/sitemap.xml", produces = "application/xml;charset=UTF-8;pageEncoding=UTF-8")
    public
    @ResponseBody
    String generateSiteMap(Locale locale, HttpServletRequest request) {
        return generateSiteMap(request.getServerName().contains("www."), locale);
    }

    private String generateSiteMap(boolean withWww, Locale locale) {
        String domainPrefix = locale.getLanguage().equals("pl") ? "pl" : "eu";
        String wwwString = "";
        if (withWww) {
            wwwString = "www.";
        }
        StringBuilder stringBuilder = new StringBuilder(5000);
        stringBuilder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        stringBuilder.append("<urlset xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\">");
        String finalDomainPrefix = "http://" + wwwString + "cwsfe." + domainPrefix;
        if ((domainPrefix.equals("pl") || domainPrefix.equals("eu"))) {
            stringBuilder.append(addStaticUrl("0.9", finalDomainPrefix, "/"));
            stringBuilder.append(addStaticUrl("0.9", finalDomainPrefix, "/about"));
            stringBuilder.append(addStaticUrl("0.8", finalDomainPrefix, "/services"));
            stringBuilder.append(addStaticUrl("0.5", finalDomainPrefix, "/services/serviceDetails"));
            stringBuilder.append(addStaticUrl("0.5", finalDomainPrefix, "/services/serviceStages"));
            stringBuilder.append(addStaticUrl("0.8", finalDomainPrefix, "/portfolio"));
            stringBuilder.append(addPortfolioUrls("0.8", finalDomainPrefix, locale));
            stringBuilder.append(addProductsUrls("0.8", finalDomainPrefix, locale));
            stringBuilder.append(addStaticUrl("0.6", finalDomainPrefix, "/blog"));
            stringBuilder.append(addBlogUrls("0.8", finalDomainPrefix, locale));
            stringBuilder.append(addStaticUrl("0.8", finalDomainPrefix, "/contact"));
            stringBuilder.append(addStaticUrl("0.3", finalDomainPrefix, "/rssFeed"));
        }
        stringBuilder.append("</urlset>");
        return stringBuilder.toString();
    }

    private String addStaticUrl(String priority, String finalDomainPrefix, String relativeUrl) {
        return "<url>" +
                "<loc>" + finalDomainPrefix + relativeUrl + "</loc>" +
                "<changefreq>monthly</changefreq>" +
                "<priority>" + priority + "</priority>" +
                "</url>";
    }

    private String addPortfolioUrls(String priority, String finalDomainPrefix, Locale locale) {
        StringBuilder stringBuilder = new StringBuilder(400);
        final Lang lang = langsDAO.getByCode(locale.getLanguage());
        List<Object[]> projects = cmsNewsDAO.listI18nProjects(lang.getId());
        for (Object[] project : projects) {
            stringBuilder.append("<url>");
            stringBuilder.
                    append("<loc>").
                    append(finalDomainPrefix).
                    append("/portfolio/singleNews/").append(project[0]).append("/").append(project[1]).
                    append("</loc>");
            stringBuilder.append("<changefreq>monthly</changefreq>");
            stringBuilder.append("<priority>").append(priority).append("</priority>");
            stringBuilder.append("</url>");
        }
        return stringBuilder.toString();
    }

    private String addProductsUrls(String priority, String finalDomainPrefix, Locale locale) {
        StringBuilder stringBuilder = new StringBuilder(400);
        final Lang lang = langsDAO.getByCode(locale.getLanguage());
        List<Object[]> products = cmsNewsDAO.listI18nProducts(lang.getId());
        for (Object[] product : products) {
            stringBuilder.append("<url>");
            stringBuilder.
                    append("<loc>").
                    append(finalDomainPrefix).
                    append("/products/singleNews/").append(product[0]).append("/").append(product[1]).
                    append("</loc>");
            stringBuilder.append("<changefreq>monthly</changefreq>");
            stringBuilder.append("<priority>").append(priority).append("</priority>");
            stringBuilder.append("</url>");
        }
        return stringBuilder.toString();
    }

    private String addBlogUrls(String priority, String finalDomainPrefix, Locale locale) {
        StringBuilder stringBuilder = new StringBuilder(400);
        final Lang lang = langsDAO.getByCode(locale.getLanguage());
        List<Object[]> posts = blogPostsDAO.listI18nPosts(lang.getId());
        for (Object[] post : posts) {
            stringBuilder.append("<url>");
            stringBuilder.
                    append("<loc>").
                    append(finalDomainPrefix).
                    append("/blog/singlePost/").append(post[0]).append("/").append(post[1]).
                    append("</loc>");
            stringBuilder.append("<changefreq>monthly</changefreq>");
            stringBuilder.append("<priority>").append(priority).append("</priority>");
            stringBuilder.append("</url>");
        }
        return stringBuilder.toString();
    }

}
