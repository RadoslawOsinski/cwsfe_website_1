package eu.com.cwsfe.blog;

import eu.com.cwsfe.cms.dao.*;
import eu.com.cwsfe.cms.model.*;
import eu.com.cwsfe.contact.EmailValidator;
import eu.com.cwsfe.model.Keyword;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Radoslaw Osinski
 */
@Controller
public class BlogController {

    @Autowired
    private BlogPostsDAO blogPostsDAO;
    @Autowired
    private BlogPostI18nContentsDAO blogPostI18nContentsDAO;
    @Autowired
    private BlogKeywordsDAO blogKeywordsDAO;
    @Autowired
    private BlogPostKeywordsDAO blogPostKeywordsDAO;
    @Autowired
    private BlogPostCommentsDAO blogPostCommentsDAO;
    @Autowired
    private BlogPostCodesDAO blogPostCodesDAO;
    @Autowired
    private CmsAuthorsDAO cmsAuthorsDAO;
    @Autowired
    private CmsLanguagesDAO languagesDAO;
    @Autowired
    private CmsTextI18nDAO cmsTextI18nDAO;
    @Autowired
    private MailSender mailSender;

    private static final int DEFAULT_CURRENT_PAGE = 0;

    @RequestMapping(value = "/blog", method = RequestMethod.GET)
    public String defaultView(ModelMap model, Locale locale) {
        setPageMetadata(model, locale, "");
        String searchText = "";
        BlogListHelper blogListHelper = listPosts(locale, DEFAULT_CURRENT_PAGE, null, searchText, null, null);
        model.addAttribute("blogPosts", blogListHelper.blogPosts);
        model.addAttribute("blogPostI18nContents", blogListHelper.blogPostI18nContents);
        model.addAttribute("currentPage", blogListHelper.currentPage);
        model.addAttribute("categoryId", blogListHelper.categoryId);
        model.addAttribute("archiveYear", blogListHelper.archiveYear);
        model.addAttribute("archiveMonth", blogListHelper.archiveMonth);
        model.addAttribute("numberOfPages", blogListHelper.numberOfPages);
        model.addAttribute("postsArchiveStatistics", blogListHelper.postsArchiveStatistics);
        model.addAttribute("articlesPerPage", blogListHelper.articlesPerPage);
        model.addAttribute("blogKeywords", blogListHelper.blogKeywords);
        model.addAttribute("searchText", blogListHelper.searchText);
        return "blog/Blog";
    }

    private void setPageMetadata(ModelMap model, Locale locale, String additionalTitle) {
        model.addAttribute("headerPageTitle", ResourceBundle.getBundle("cwsfe_i18n", locale).getString("Blog") + " " + additionalTitle);
        model.addAttribute("keywords", setPageKeywords(locale));
        model.addAttribute("additionalCssCode", setAdditionalCss());
        model.addAttribute("additionalJavaScriptCode", setAdditionalJS());
    }

    List<Keyword> setPageKeywords(Locale locale) {
        List<Keyword> keywords = new ArrayList<>(5);
        keywords.add(new Keyword(ResourceBundle.getBundle("cwsfe_i18n", locale).getString("CWSFEBlog")));
        keywords.add(new Keyword(ResourceBundle.getBundle("cwsfe_i18n", locale).getString("jPalioTipsAndTrix")));
        keywords.add(new Keyword(ResourceBundle.getBundle("cwsfe_i18n", locale).getString("GroovyTips")));
        keywords.add(new Keyword(ResourceBundle.getBundle("cwsfe_i18n", locale).getString("GroovyScripts")));
        keywords.add(new Keyword(ResourceBundle.getBundle("cwsfe_i18n", locale).getString("SQLAdvices")));
        return keywords;
    }

    private List<String> setAdditionalCss() {
        List<String> cssUrl = new ArrayList<>(3);
        cssUrl.add("/resources-cwsfe/img/layout/css/pages-min.css");
        cssUrl.add("/resources-cwsfe/css/Blog-min.css");
        return cssUrl;
    }

    private Object setAdditionalJS() {
        List<String> jsUrl = new ArrayList<>(3);
        jsUrl.add("/resources-cwsfe/js/AjaxCodeFetcher.js");
        return jsUrl;
    }

    @RequestMapping(value = "/blog/category/{categoryId}", method = RequestMethod.GET)
    public String browseWithCategory(ModelMap model, Locale locale, @PathVariable("categoryId") Long categoryId) {
        setPageMetadata(model, locale, "");
        String searchText = "";
        BlogListHelper blogListHelper = listPosts(locale, 0, categoryId, searchText, null, null);
        model.addAttribute("blogPosts", blogListHelper.blogPosts);
        model.addAttribute("blogPostI18nContents", blogListHelper.blogPostI18nContents);
        model.addAttribute("currentPage", blogListHelper.currentPage);
        model.addAttribute("categoryId", blogListHelper.categoryId);
        model.addAttribute("archiveYear", blogListHelper.archiveYear);
        model.addAttribute("archiveMonth", blogListHelper.archiveMonth);
        model.addAttribute("numberOfPages", blogListHelper.numberOfPages);
        model.addAttribute("postsArchiveStatistics", blogListHelper.postsArchiveStatistics);
        model.addAttribute("articlesPerPage", blogListHelper.articlesPerPage);
        model.addAttribute("blogKeywords", blogListHelper.blogKeywords);
        model.addAttribute("searchText", blogListHelper.searchText);
        return "blog/Blog";
    }

    @RequestMapping(value = "/blog/date/{archiveYear}/{archiveMonth}", method = RequestMethod.GET)
    public String browseByDate(ModelMap model, Locale locale, @PathVariable("archiveYear") Long archiveYear, @PathVariable("archiveMonth") Long archiveMonth) {
        setPageMetadata(model, locale, "");
        BlogListHelper blogListHelper = listPosts(locale, 0, null, "", archiveYear, archiveMonth);
        model.addAttribute("blogPosts", blogListHelper.blogPosts);
        model.addAttribute("blogPostI18nContents", blogListHelper.blogPostI18nContents);
        model.addAttribute("currentPage", blogListHelper.currentPage);
        model.addAttribute("categoryId", blogListHelper.categoryId);
        model.addAttribute("archiveYear", blogListHelper.archiveYear);
        model.addAttribute("archiveMonth", blogListHelper.archiveMonth);
        model.addAttribute("numberOfPages", blogListHelper.numberOfPages);
        model.addAttribute("postsArchiveStatistics", blogListHelper.postsArchiveStatistics);
        model.addAttribute("articlesPerPage", blogListHelper.articlesPerPage);
        model.addAttribute("blogKeywords", blogListHelper.blogKeywords);
        model.addAttribute("searchText", blogListHelper.searchText);
        return "blog/Blog";
    }

    @RequestMapping(value = "/blog/search/", method = RequestMethod.POST)
    public String browseBySearch(ModelMap model, Locale locale, @RequestParam(value = "searchText", required = false) String searchText) {
        setPageMetadata(model, locale, "");
        BlogListHelper blogListHelper = listPosts(locale, 0, null, searchText, null, null);
        model.addAttribute("blogPosts", blogListHelper.blogPosts);
        model.addAttribute("blogPostI18nContents", blogListHelper.blogPostI18nContents);
        model.addAttribute("currentPage", blogListHelper.currentPage);
        model.addAttribute("categoryId", blogListHelper.categoryId);
        model.addAttribute("archiveYear", blogListHelper.archiveYear);
        model.addAttribute("archiveMonth", blogListHelper.archiveMonth);
        model.addAttribute("numberOfPages", blogListHelper.numberOfPages);
        model.addAttribute("postsArchiveStatistics", blogListHelper.postsArchiveStatistics);
        model.addAttribute("articlesPerPage", blogListHelper.articlesPerPage);
        model.addAttribute("blogKeywords", blogListHelper.blogKeywords);
        model.addAttribute("searchText", blogListHelper.searchText);
        return "blog/Blog";
    }

    @RequestMapping(value = "/blog/list/", method = RequestMethod.GET)
    public String listPosts(ModelMap model, Locale locale,
                            @RequestParam(value = "currentPage", required = false) Integer currentPage,
                            @RequestParam(value = "categoryId", required = false) String categoryString,
                            @RequestParam(value = "searchText", required = false) String searchText,
                            @RequestParam(value = "archiveYear", required = false) String archiveYearString,
                            @RequestParam(value = "archiveMonth", required = false) String archiveMonthString
    ) {
        setPageMetadata(model, locale, "");
        if (currentPage == null) {
            currentPage = 0;
        }
        if (searchText == null) {
            searchText = "";
        }
        Long categoryId = null;
        if (categoryString != null && !categoryString.isEmpty()) {
            try {
                categoryId = Long.parseLong(categoryString);
            } catch (Exception ignored) {
            }
        }
        Long archiveYear = null;
        if (archiveYearString != null && !archiveYearString.isEmpty()) {
            try {
                archiveYear = Long.parseLong(archiveYearString);
            } catch (Exception ignored) {
            }
        }
        Long archiveMonth = null;
        if (archiveMonthString != null && !archiveMonthString.isEmpty()) {
            try {
                archiveMonth = Long.parseLong(archiveMonthString);
            } catch (Exception ignored) {
            }
        }
        BlogListHelper blogListHelper = listPosts(locale, currentPage, categoryId, searchText, archiveYear, archiveMonth);
        model.addAttribute("blogPosts", blogListHelper.blogPosts);
        model.addAttribute("blogPostI18nContents", blogListHelper.blogPostI18nContents);
        model.addAttribute("currentPage", blogListHelper.currentPage);
        model.addAttribute("categoryId", blogListHelper.categoryId);
        model.addAttribute("archiveYear", blogListHelper.archiveYear);
        model.addAttribute("archiveMonth", blogListHelper.archiveMonth);
        model.addAttribute("numberOfPages", blogListHelper.numberOfPages);
        model.addAttribute("postsArchiveStatistics", blogListHelper.postsArchiveStatistics);
        model.addAttribute("articlesPerPage", blogListHelper.articlesPerPage);
        model.addAttribute("blogKeywords", blogListHelper.blogKeywords);
        model.addAttribute("searchText", blogListHelper.searchText);
        return "blog/Blog";
    }

    BlogListHelper listPosts(
            Locale locale,
            Integer currentPage,
            Long categoryId,
            String searchText,
            Long archiveYear,
            Long archiveMonth
    ) {
        Lang currentLang = languagesDAO.getByCode(locale.getLanguage());
        if (currentLang == null) {
            currentLang = languagesDAO.getByCode("en");    //default language
        }
        BlogListHelper blogListHelper = new BlogListHelper();
        blogListHelper.articlesPerPage = 5;
        blogListHelper.currentPage = currentPage;
        blogListHelper.postsArchiveStatistics = blogPostsDAO.listArchiveStatistics(currentLang.getId());
        blogListHelper.blogKeywords = i18nBlogKeywords(currentLang, blogKeywordsDAO.list());
        List<Object[]> postI18nIds;
        Object[] foundedArticlesTotal;
        if (categoryId != null) {
            postI18nIds = blogPostsDAO.listForPageWithCategoryAndPaging(categoryId, currentLang.getId(), blogListHelper.articlesPerPage, blogListHelper.currentPage);
            foundedArticlesTotal = blogPostsDAO.listCountForPageWithCategoryAndPaging(categoryId, currentLang.getId());
        } else if (searchText != null && !searchText.isEmpty()) {
            postI18nIds = blogPostsDAO.listForPageWithSearchTextAndPaging(searchText, currentLang.getId(), blogListHelper.articlesPerPage, blogListHelper.currentPage);
            foundedArticlesTotal = blogPostsDAO.listCountForPageWithSearchTextAndPaging(searchText, currentLang.getId());
        } else if (archiveYear != null && archiveMonth != null) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = null;
            Date endDate = null;
            try {
                startDate = formatter.parse(archiveYear + "-" + ((archiveMonth < 10) ? "0" + archiveMonth : archiveMonth) + "-01");
                Calendar startDateCalendar = Calendar.getInstance();
                startDateCalendar.setTime(startDate);
                startDateCalendar.add(Calendar.MONTH, 1);
                endDate = startDateCalendar.getTime();
            } catch (ParseException ignored) {
            }
            postI18nIds = blogPostsDAO.listForPageWithArchiveDateAndPaging(startDate, endDate, currentLang.getId(), blogListHelper.articlesPerPage, blogListHelper.currentPage);
            foundedArticlesTotal = blogPostsDAO.listCountForPageWithArchiveDateAndPaging(startDate, endDate, currentLang.getId());
        } else {
            postI18nIds = blogPostsDAO.listForPageWithPaging(currentLang.getId(), blogListHelper.articlesPerPage, blogListHelper.currentPage);
            foundedArticlesTotal = blogPostsDAO.listCountForPageWithPaging(currentLang.getId());
        }
        List<BlogPost> blogPosts;
        List<BlogPostI18nContent> blogPostI18nContents;
        if (postI18nIds == null) {
            blogPosts = new ArrayList<>(0);
            blogPostI18nContents = new ArrayList<>(0);
        } else {
            blogPosts = new ArrayList<>(postI18nIds.size());
            blogPostI18nContents = new ArrayList<>(postI18nIds.size());
            for (Object[] postI18nId : postI18nIds) {
                final Long blogPostId = (Long) postI18nId[0];
                BlogPost blogPost = blogPostsDAO.get(blogPostId);
                blogPost.setCmsAuthor(cmsAuthorsDAO.get(blogPost.getPostAuthorId()));
                blogPost.setBlogKeywords(i18nBlogKeywords(currentLang, blogPostKeywordsDAO.listForPost(blogPost.getId())));
                BlogPostI18nContent blogPostI18nContent = blogPostI18nContentsDAO.get((Long) postI18nId[1]);
                final String CURRENT_BLOG_POST_LABEL = "#!CURRENT_BLOG_POST_ID!#";  //trick for printing code examples inside of blog posts
                blogPostI18nContent.setPostShortcut(blogPostI18nContent.getPostShortcut().replaceAll(CURRENT_BLOG_POST_LABEL, Long.toString(blogPostId)));
                blogPostI18nContent.setPostDescription(blogPostI18nContent.getPostDescription().replaceAll(CURRENT_BLOG_POST_LABEL, Long.toString(blogPostId)));
                blogPostI18nContent.setBlogPostComments(blogPostCommentsDAO.listPublishedForPostI18nContent(blogPostI18nContent.getId()));
                blogPosts.add(blogPost);
                blogPostI18nContents.add(blogPostI18nContent);
            }
        }
        blogListHelper.blogPosts = blogPosts;
        blogListHelper.blogPostI18nContents = blogPostI18nContents;
        blogListHelper.currentPage = currentPage;
        blogListHelper.categoryId = categoryId;
        blogListHelper.archiveYear = archiveYear;
        blogListHelper.archiveMonth = archiveMonth;
        blogListHelper.numberOfPages = (int)
                (Math.floor((Integer) foundedArticlesTotal[0] / blogListHelper.articlesPerPage) +
                        ((int) foundedArticlesTotal[0] % blogListHelper.articlesPerPage > 0 ? 1 : 0));
        return blogListHelper;
    }

    private List<BlogKeyword> i18nBlogKeywords(Lang currentLang, List<BlogKeyword> blogKeywords) {
        List<BlogKeyword> i18nBlogKeywords = new ArrayList<>(blogKeywords.size());
        for (BlogKeyword blogKeyword : blogKeywords) {
            String keywordI18n = cmsTextI18nDAO.findTranslation(currentLang.getCode(), "BlogKeywords", blogKeyword.getKeywordName());
            if (keywordI18n == null) {
                keywordI18n = "missing translation ...";
            }
            blogKeyword.setKeywordName(keywordI18n);
            i18nBlogKeywords.add(blogKeyword);
        }
        return i18nBlogKeywords;
    }

    @RequestMapping(value = "/blog/singlePost/{blogPostId}/{blogPostI18nContentId}", method = RequestMethod.GET)
    public String singlePostView(ModelMap model, Locale locale,
                                 @PathVariable("blogPostId") Long blogPostId,
                                 @PathVariable("blogPostI18nContentId") Long blogPostI18nContentId) {
        Lang currentLang = languagesDAO.getByCode(locale.getLanguage());
        if (currentLang == null) {
            currentLang = languagesDAO.getByCode("en");    //default language
        }
        final BlogPost blogPost = blogPostsDAO.get(blogPostId);
        blogPost.setCmsAuthor(cmsAuthorsDAO.get(blogPost.getPostAuthorId()));
        model.addAttribute("blogPost", blogPost);
        BlogPostI18nContent blogPostI18nContent = blogPostI18nContentsDAO.get(blogPostI18nContentId);
        final String CURRENT_BLOG_POST_LABEL = "#!CURRENT_BLOG_POST_ID!#";  //trick for printing code examples inside of blog posts
        blogPostI18nContent.setPostShortcut(blogPostI18nContent.getPostShortcut().replaceAll(CURRENT_BLOG_POST_LABEL, Long.toString(blogPostId)));
        blogPostI18nContent.setPostDescription(blogPostI18nContent.getPostDescription().replaceAll(CURRENT_BLOG_POST_LABEL, Long.toString(blogPostId)));
        List<BlogPostComment> blogPostComments = blogPostCommentsDAO.listPublishedForPostI18nContent(blogPostI18nContent.getId());
        for (BlogPostComment blogPostComment : blogPostComments) {
            blogPostComment.setEmail(MD5Util.md5Hex(blogPostComment.getEmail().trim().toLowerCase()));
        }
        blogPostI18nContent.setBlogPostComments(blogPostComments);
        model.addAttribute("blogPostI18nContent", blogPostI18nContent);
        model.addAttribute("postsArchiveStatistics", blogPostsDAO.listArchiveStatistics(currentLang.getId()));
        model.addAttribute("blogKeywords", i18nBlogKeywords(currentLang, blogKeywordsDAO.list()));
        model.addAttribute("searchText", "");
        setPageMetadata(model, locale, " " + blogPostI18nContent.getPostTitle());
        return "blog/SinglePostView";
    }

    @RequestMapping(value = "/blog/addBlogPostComment", method = RequestMethod.POST)
    public String addComment(
            @ModelAttribute(value = "blogPostComment") BlogPostComment blogPostComment,
            @RequestParam(value = "blogPostId") Long blogPostId,
            ModelMap modelMap, BindingResult result, Locale locale
    ) {
        ValidationUtils.rejectIfEmpty(result, "blogPostI18nContentId", ResourceBundle.getBundle("cwsfe_cms_i18n", locale).getString("BlogPostMustBeSet"));
        ValidationUtils.rejectIfEmpty(result, "username", ResourceBundle.getBundle("cwsfe_cms_i18n", locale).getString("UsernameMustBeSet"));
        ValidationUtils.rejectIfEmpty(result, "email", ResourceBundle.getBundle("cwsfe_i18n", locale).getString("EmailMustBeSet"));
        if (blogPostComment.getEmail() != null && !EmailValidator.isValidEmailAddress(blogPostComment.getEmail())) {
            result.rejectValue("email", ResourceBundle.getBundle("cwsfe_i18n", locale).getString("EmailIsInvalid"));
        }
        ValidationUtils.rejectIfEmpty(result, "comment", ResourceBundle.getBundle("cwsfe_cms_i18n", locale).getString("CommentMustBeSet"));
        if (!result.hasErrors()) {
            blogPostComment.setCreated(new Date());
            blogPostCommentsDAO.add(blogPostComment);
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo("Radoslaw.Osinski@cwsfe.pl");
            message.setSubject("New comment on cwsfe website by " +
                    blogPostComment.getUsername() + "[" + blogPostComment.getEmail() + "]"
            );
            message.setText("Comment: " + blogPostComment.getComment());
            mailSender.send(message);
            modelMap.addAttribute("addCommentInfoMessage", ResourceBundle.getBundle("cwsfe_i18n", locale).getString("AddedSuccessfullyWaitForModeratorPublication"));
        } else {
            String addCommentErrorMessage = "";
            for (int i = 0; i < result.getAllErrors().size(); i++) {
                addCommentErrorMessage += result.getAllErrors().get(i).getCode() + "<br/>";
            }
            modelMap.addAttribute("addCommentErrorMessage", addCommentErrorMessage);
        }
        return singlePostView(modelMap, locale, blogPostId, blogPostComment.getBlogPostI18nContentId());
    }

    @RequestMapping(value = "/blogPostCode/{postId}/{codeId}", method = RequestMethod.POST, produces = "application/json;charset=UTF-8;pageEncoding=UTF-8")
    public
    @ResponseBody
    String getBlogPostCode(
            @PathVariable("postId") Long postId,
            @PathVariable("codeId") String codeId
    ) {
        JSONObject responseDetailsJson = new JSONObject();
        final BlogPostCode codeForPost = blogPostCodesDAO.getCodeForPost(postId, codeId);
        if (codeForPost == null) {
            responseDetailsJson.put("code", "...");
        } else {
            responseDetailsJson.put("code", "<pre>" + HtmlUtils.htmlEscape(codeForPost.getCode()) + "</pre>");
        }
        return responseDetailsJson.toString();
    }

}
