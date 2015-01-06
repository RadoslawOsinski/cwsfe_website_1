//package eu.com.cwsfe.rss;
//
//import eu.com.cwsfe.cms.dao.BlogPostI18nContentsDAO;
//import eu.com.cwsfe.cms.dao.BlogPostsDAO;
//import eu.com.cwsfe.cms.dao.CmsLanguagesDAO;
//import eu.com.cwsfe.cms.model.BlogPost;
//import eu.com.cwsfe.cms.model.BlogPostI18nContent;
//import eu.com.cwsfe.cms.model.RssContent;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.servlet.ModelAndView;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Locale;
//
///**
// * Created by Radoslaw Osinski.
// */
//@Controller
//class RssController {
//
//    @Autowired
//    private CmsLanguagesDAO cmsLanguagesDAO;
//    @Autowired
//    private BlogPostsDAO blogPostsDAO;
//    @Autowired
//    private BlogPostI18nContentsDAO blogPostI18nContentsDAO;
//
//    @RequestMapping(value="/rssFeed", method = RequestMethod.GET)
//    public ModelAndView getFeedInRss(Locale locale, HttpServletRequest request) {
//        List<RssContent> items = new ArrayList<>();
//        List<Object[]> posts = blogPostsDAO.listI18nPosts(cmsLanguagesDAO.getByCode(locale.getLanguage()).getId());
//        final String wwwString = request.getServerName().contains("www.") ? "www." : "";
//        final String domainString = locale.getLanguage().contains("pl") ? "pl" : "eu";
//        for (Object[] post : posts) {
//            RssContent content  = new RssContent();
//            BlogPost blogPost = blogPostsDAO.get((Long) post[0]);
//            BlogPostI18nContent blogPostI18nContent = blogPostI18nContentsDAO.get((Long) post[1]);
//            content.setTitle(blogPostI18nContent.getPostTitle());
//            content.setUrl("http://" +
//                    wwwString +
//                    "cwsfe." + domainString +
//                    "/blog/singlePost/" + post[0] + "/" + post[1]
//            );
//            content.setSummary(blogPostI18nContent.getPostDescription());
//            content.setCreatedDate(blogPost.getPostCreationDate());
//            items.add(content);
//        }
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("rssViewer");
//        modelAndView.addObject("feedContent", items);
//        return modelAndView;
//    }
//
//}
