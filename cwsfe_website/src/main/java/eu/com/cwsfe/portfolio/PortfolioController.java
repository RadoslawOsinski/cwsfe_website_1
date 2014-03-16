package eu.com.cwsfe.portfolio;

import eu.com.cwsfe.cms.dao.*;
import eu.com.cwsfe.cms.model.CmsFolder;
import eu.com.cwsfe.cms.model.CmsNews;
import eu.com.cwsfe.cms.model.CmsNewsI18nContent;
import eu.com.cwsfe.cms.model.Lang;
import eu.com.cwsfe.model.Keyword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

/**
 * @author Radoslaw Osinski
 */
@Controller
class PortfolioController {

    @Autowired
    private CmsFoldersDAO cmsFoldersDAO;
    @Autowired
    private CmsNewsDAO cmsNewsDAO;
    @Autowired
    private CmsLanguagesDAO langsDAO;
    @Autowired
    private CmsNewsI18nContentsDAO cmsNewsI18nContentsDAO;
    @Autowired
    private CmsTextI18nDAO cmsTextI18nDAO;
    @Autowired
    private CmsNewsImagesDAO cmsNewsImagesDAO;

    private static final int DEFAULT_NEWS_PER_PAGE = 6;

    private void setPageMetadata(ModelMap model, Locale locale, String portfolioTitle) {
        model.addAttribute("headerPageTitle", ResourceBundle.getBundle("cwsfe_i18n", locale).getString("Portfolio") + " " + portfolioTitle);
        model.addAttribute("keywords", setPageKeywords(locale, portfolioTitle));
        model.addAttribute("additionalCssCode", setAdditionalCss());
        model.addAttribute("additionalJavaScriptCode", setAdditionalJS());
    }

    public List<Keyword> setPageKeywords(Locale locale, String portfolioTitle) {
        List<Keyword> keywords = new ArrayList<>(5);
        keywords.add(new Keyword(ResourceBundle.getBundle("cwsfe_i18n", locale).getString("CWSFEPortfolio")));
        keywords.add(new Keyword(ResourceBundle.getBundle("cwsfe_i18n", locale).getString("CWSFEProjects")));
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

    private Object setAdditionalJS() {
        List<String> jsUrl = new ArrayList<>(3);
        jsUrl.add("/resources-cwsfe/js/tipsy/tipsy.js");
        jsUrl.add("/resources-cwsfe/js/jquery/cycle.all.min.js");
        jsUrl.add("/resources-cwsfe/js/prettyPhoto/prettyPhoto.js");
        jsUrl.add("/resources-cwsfe/js/Portfolio.js");
        return jsUrl;
    }

    @RequestMapping(value = "/portfolio", method = RequestMethod.GET)
    public String listPortfolio(ModelMap model, Locale locale,
                                             @RequestParam(value = "currentPage", required = false) Integer currentPage,
                                             @RequestParam(value = "newsFolderId", required = false) String newsFolderIdString
    ) {
        if (currentPage == null) {
            currentPage = 0;
        }
        Integer newsFolderId = null;
        if (newsFolderIdString != null && !newsFolderIdString.isEmpty()) {
            try {
                newsFolderId = Integer.parseInt(newsFolderIdString);
            } catch (Exception ignored) {
            }
        }
        setPageMetadata(model, locale, "");
        PortfolioListHelper portfolioListHelper = listPortfolio(locale, currentPage, newsFolderId);
        model.addAttribute("cmsNewsList", portfolioListHelper.cmsNewsList);
        model.addAttribute("cmsNewsI18nContents", portfolioListHelper.cmsNewsI18nContents);
        model.addAttribute("newsFolderId", portfolioListHelper.newsFolderId);
        model.addAttribute("numberOfPages", portfolioListHelper.numberOfPages);
        List<CmsFolder> cmsNewsFolders = cmsFoldersDAO.list();
        model.addAttribute("cmsNewsFolders", cmsNewsFolders);
        model.addAttribute("i18nCmsNewsFolders", i18nCmsFolders(locale.getLanguage(), cmsNewsFolders));
        model.addAttribute("currentPage", currentPage);
        return "portfolio/Portfolio";
    }

    private Map<String, String> i18nCmsFolders(String currentLangCode, List<CmsFolder> cmsFolders) {
        Map<String, String> i18nCmsFolders = new HashMap<>(cmsFolders.size());
        for (CmsFolder cmsFolder : cmsFolders) {
            String folderNameI18n = cmsTextI18nDAO.findTranslation(currentLangCode, "Folders", cmsFolder.getFolderName());
            if (folderNameI18n == null) {
                folderNameI18n = "missing translation ...";
            }
            i18nCmsFolders.put(cmsFolder.getFolderName(), folderNameI18n);
        }
        return i18nCmsFolders;
    }

    private PortfolioListHelper listPortfolio(Locale locale, Integer currentPage, Integer newsFolderId) {
        return listPortfolio(locale, currentPage, newsFolderId, DEFAULT_NEWS_PER_PAGE);
    }

    private PortfolioListHelper listPortfolio(Locale locale, Integer currentPage, Integer newsFolderId, int newsPerPage) {
        List<Object[]> cmsNewsI18nContentIds;
        Integer foundedNewsTotal;
        Lang currentPLang = langsDAO.getByCode(locale.getLanguage());
        if (currentPLang == null) {
            currentPLang = langsDAO.getByCode("en");
        }
        if (newsFolderId != null) {
            cmsNewsI18nContentIds = cmsNewsDAO.listByFolderLangWithPagingForProjects(newsFolderId, currentPLang.getId(), newsPerPage, currentPage * newsPerPage);
            foundedNewsTotal = cmsNewsDAO.countListByFolderLangWithPagingForProjects(newsFolderId, currentPLang.getId());
        } else {
            cmsNewsI18nContentIds = cmsNewsDAO.listLangWithPagingForProjects(currentPLang.getId(), newsPerPage, currentPage * newsPerPage);
            foundedNewsTotal = cmsNewsDAO.countListLangWithPagingForProjects(currentPLang.getId());
        }
        List<CmsNews> cmsNewsList;
        List<CmsNewsI18nContent> cmsNewsI18nContents;
        if (cmsNewsI18nContentIds == null) {
            cmsNewsList = new ArrayList<>(0);
            cmsNewsI18nContents = new ArrayList<>(0);
        } else {
            cmsNewsList = new ArrayList<>(cmsNewsI18nContentIds.size());
            cmsNewsI18nContents = new ArrayList<>(cmsNewsI18nContentIds.size());
            for (Object[] postI18nId : cmsNewsI18nContentIds) {
                CmsNews cmsNews = cmsNewsDAO.get((Long) postI18nId[0]);
                cmsNews.setCmsNewsImages(cmsNewsImagesDAO.listImagesForNewsWithoutThumbnails(cmsNews.getId()));
                cmsNews.setThumbnailImage(cmsNewsImagesDAO.getThumbnailForNews(cmsNews.getId()));
                CmsNewsI18nContent cmsNewsI18nContent = cmsNewsI18nContentsDAO.get((Long) postI18nId[1]);
                if (cmsNewsI18nContent != null) {
                    cmsNewsList.add(cmsNews);
                    cmsNewsI18nContents.add(cmsNewsI18nContent);
                }
            }
        }
        PortfolioListHelper portfolioListHelper = new PortfolioListHelper();
        portfolioListHelper.cmsNewsList = cmsNewsList;
        portfolioListHelper.cmsNewsI18nContents = cmsNewsI18nContents;
        portfolioListHelper.newsFolderId = newsFolderId;
        portfolioListHelper.numberOfPages = (int) (Math.floor(foundedNewsTotal / newsPerPage) + (foundedNewsTotal % newsPerPage > 0 ? 1 : 0));
        return portfolioListHelper;
    }

    @RequestMapping(value = "/portfolio/singleNews/{cmsNewsId}/{cmsNewsI18nContentsId}", method = RequestMethod.GET)
    public String singleNewsView(ModelMap model, Locale locale, @PathVariable("cmsNewsId") Integer cmsNewsId, @PathVariable("cmsNewsI18nContentsId") Integer cmsNewsI18nContentsId) {
        CmsNews cmsNews = cmsNewsDAO.get(Long.valueOf(cmsNewsId));
        cmsNews.setCmsNewsImages(cmsNewsImagesDAO.listImagesForNewsWithoutThumbnails(cmsNews.getId()));
        cmsNews.setThumbnailImage(cmsNewsImagesDAO.getThumbnailForNews(cmsNews.getId()));
        model.addAttribute("cmsNews", cmsNews);
        CmsNewsI18nContent cmsNewsI18nContent = cmsNewsI18nContentsDAO.get(Long.valueOf(cmsNewsI18nContentsId));
        model.addAttribute("cmsNewsI18nContent", cmsNewsI18nContent);

        Lang currentLang = langsDAO.getByCode(locale.getLanguage());     //bellow there is code for finding news for previous/next links
        if (currentLang == null) {
            currentLang = langsDAO.getByCode("en");    //default language
        }
        List<Object[]> newsAndContents = cmsNewsDAO.listI18nProjects(currentLang.getId());
        Object[] iNews;
        Object[] previousNews = null;
        Object[] nextNews = null;
        for (int i = 0; i < newsAndContents.size(); ++i) {
            iNews = newsAndContents.get(i);
            if (iNews[0].equals(cmsNews.getId()) && iNews[1].equals(cmsNewsI18nContent.getId())) {
                try {
                    nextNews = newsAndContents.get(i + 1);
                } catch (Exception e) {
                    nextNews = null;
                }
                try {
                    previousNews = newsAndContents.get(i - 1);
                } catch (Exception e) {
                    previousNews = null;
                }
                break;
            }
        }
        model.addAttribute("previousNews", previousNews);
        model.addAttribute("nextNews", nextNews);
        setPageMetadata(model, locale, " " + cmsNewsI18nContent.getNewsTitle());
        return "portfolio/PortfolioSingleView";
    }

}

