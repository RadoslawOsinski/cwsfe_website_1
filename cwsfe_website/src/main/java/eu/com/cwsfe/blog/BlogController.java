package eu.com.cwsfe.blog;

import eu.com.cwsfe.GenericController;
import eu.com.cwsfe.cms.dao.*;
import eu.com.cwsfe.cms.model.*;
import eu.com.cwsfe.contact.EmailValidator;
import eu.com.cwsfe.model.Keyword;
import net.sf.json.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import java.util.*;

/**
 * @author Radoslaw Osinski
 */
@Controller
public class BlogController extends GenericController {

    private static final Logger LOGGER = LogManager.getLogger(BlogController.class);

    /**
     * trick for printing code examples inside of blog posts
     */
    private static final String CURRENT_BLOG_POST_LABEL = "#!CURRENT_BLOG_POST_ID!#";

    public static final String BLOG_POSTS = "blogPosts";
    public static final String BLOG_POST_I_18_N_CONTENT = "blogPostI18nContent";
    public static final String BLOG_POST_I_18_N_CONTENTS = "blogPostI18nContents";
    public static final String CURRENT_PAGE = "currentPage";
    public static final String CATEGORY_ID = "categoryId";
    public static final String NUMBER_OF_PAGES = "numberOfPages";
    public static final String ARTICLES_PER_PAGE = "articlesPerPage";
    public static final String SEARCH_TEXT = "searchText";
    public static final String BLOG_VIEW_PATH = "blog/Blog";

    @Autowired
    private BlogPostsDAO blogPostsDAO;
    @Autowired
    private BlogPostI18nContentsDAO blogPostI18nContentsDAO;
    @Autowired
    private BlogPostCommentsDAO blogPostCommentsDAO;
    @Autowired
    private BlogPostCodesDAO blogPostCodesDAO;
    @Autowired
    private CmsAuthorsDAO cmsAuthorsDAO;
    @Autowired
    private CmsLanguagesDAO languagesDAO;

    private static final int DEFAULT_CURRENT_PAGE = 0;

    @RequestMapping(value = "/blog", method = RequestMethod.GET)
    public String defaultView(ModelMap model, Locale locale) {
        setPageMetadata(model, locale, "");
        model.addAttribute("additionalJavaScriptCode", "/resources-cwsfe/js/Blog.js");
        model.addAttribute("localeLanguage", locale.getLanguage());
        BlogListHelper blogListHelper = listPosts(locale, DEFAULT_CURRENT_PAGE, null);
        addBlogSearchResults(model, blogListHelper);
        return BLOG_VIEW_PATH;
    }

    private void addBlogSearchResults(ModelMap model, BlogListHelper blogListHelper) {
        model.addAttribute(BLOG_POSTS, blogListHelper.blogPosts);
        model.addAttribute(BLOG_POST_I_18_N_CONTENTS, blogListHelper.blogPostI18nContents);
        model.addAttribute(CURRENT_PAGE, blogListHelper.currentPage);
        model.addAttribute(CATEGORY_ID, blogListHelper.categoryId);
        model.addAttribute(NUMBER_OF_PAGES, blogListHelper.numberOfPages);
        model.addAttribute(ARTICLES_PER_PAGE, blogListHelper.articlesPerPage);
        model.addAttribute(SEARCH_TEXT, blogListHelper.searchText);
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

    @RequestMapping(value = "/blog/category/{categoryId}", method = RequestMethod.GET)
    public String browseWithCategory(ModelMap model, Locale locale, @PathVariable("categoryId") Long categoryId) {
        setPageMetadata(model, locale, "");
        model.addAttribute("additionalJavaScriptCode", "/resources-cwsfe/js/Blog.js");
        BlogListHelper blogListHelper = listPosts(locale, 0, categoryId);
        addBlogSearchResults(model, blogListHelper);
        return BLOG_VIEW_PATH;
    }

    @RequestMapping(value = "/blog/list/", method = RequestMethod.GET)
    public String listPosts(ModelMap model, Locale locale,
                            @RequestParam(value = "currentPage", required = false) Integer currentPage,
                            @RequestParam(value = "categoryId", required = false) String categoryString
    ) {
        setPageMetadata(model, locale, "");
        model.addAttribute("additionalJavaScriptCode", "/resources-cwsfe/js/Blog.js");
        model.addAttribute("localeLanguage", locale.getLanguage());
        Long categoryId = parseStringParameter(categoryString);
        BlogListHelper blogListHelper = listPosts(locale, currentPage, categoryId);
        addBlogSearchResults(model, blogListHelper);
        return BLOG_VIEW_PATH;
    }

    private Long parseStringParameter(String stringParameter) {
        Long parserParameter = null;
        if (stringParameter != null && !stringParameter.isEmpty()) {
            try {
                parserParameter = Long.parseLong(stringParameter);
            } catch (Exception e) {
                LOGGER.error("String parameter cannot be parsed: " + stringParameter, e);
            }
        }
        return parserParameter;
    }

    BlogListHelper listPosts(
            Locale locale,
            Integer currentPage,
            Long categoryId
    ) {
        Language currentLang = getCurrentOrDefaultLanguage(locale);
        BlogListHelper blogListHelper = new BlogListHelper();
        blogListHelper.articlesPerPage = 5;
        if (currentPage == null) {
            blogListHelper.currentPage = 0;
        } else {
            blogListHelper.currentPage = currentPage;
        }
        List<Object[]> postI18nIds;
        long foundedArticlesTotal;
        if (categoryId != null) {
            postI18nIds = blogPostsDAO.listForPageWithCategoryAndPaging(categoryId, currentLang.getId(), blogListHelper.articlesPerPage, blogListHelper.currentPage);
            foundedArticlesTotal = blogPostsDAO.listCountForPageWithCategoryAndPaging(categoryId, currentLang.getId());
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
                BlogPostI18nContent blogPostI18nContent = blogPostI18nContentsDAO.get((Long) postI18nId[1]);
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
        blogListHelper.numberOfPages = (int)
                (Math.floor(foundedArticlesTotal / (double) blogListHelper.articlesPerPage) +
                        (foundedArticlesTotal % blogListHelper.articlesPerPage > 0 ? 1 : 0));
        return blogListHelper;
    }

    private Language getCurrentOrDefaultLanguage(Locale locale) {
        Language currentLang = languagesDAO.getByCode(locale.getLanguage());
        if (currentLang == null) {
            currentLang = getDefaultLanguage();
        }
        return currentLang;
    }

    private Language getDefaultLanguage() {
        return languagesDAO.getByCode("en");
    }

    @RequestMapping(value = "/blog/singlePost/{blogPostId}/{blogPostI18nContentId}", method = RequestMethod.GET)
    public String singlePostView(ModelMap model, Locale locale,
                                 @PathVariable("blogPostId") Long blogPostId,
                                 @PathVariable("blogPostI18nContentId") Long blogPostI18nContentId) {
        final BlogPost blogPost = blogPostsDAO.get(blogPostId);
        blogPost.setCmsAuthor(cmsAuthorsDAO.get(blogPost.getPostAuthorId()));
        model.addAttribute("blogPost", blogPost);
        BlogPostI18nContent blogPostI18nContent = blogPostI18nContentsDAO.get(blogPostI18nContentId);
        blogPostI18nContent.setPostShortcut(blogPostI18nContent.getPostShortcut().replaceAll(CURRENT_BLOG_POST_LABEL, Long.toString(blogPostId)));
        blogPostI18nContent.setPostDescription(blogPostI18nContent.getPostDescription().replaceAll(CURRENT_BLOG_POST_LABEL, Long.toString(blogPostId)));
        List<BlogPostComment> blogPostComments = blogPostCommentsDAO.listPublishedForPostI18nContent(blogPostI18nContent.getId());
        for (BlogPostComment blogPostComment : blogPostComments) {
            blogPostComment.setEmail(MD5Util.md5Hex(blogPostComment.getEmail().trim().toLowerCase()));
        }
        blogPostI18nContent.setBlogPostComments(blogPostComments);
        model.addAttribute(BLOG_POST_I_18_N_CONTENT, blogPostI18nContent);
        model.addAttribute(SEARCH_TEXT, "");
        setPageMetadata(model, locale, " " + blogPostI18nContent.getPostTitle());
        model.addAttribute("additionalJavaScriptCode", "/resources-cwsfe/js/SingleBlogPost.js");
        model.addAttribute("localeLanguage", locale.getLanguage());
        return "blog/SinglePostView";
    }

    @RequestMapping(value = "/blog/addBlogPostComment", method = RequestMethod.POST)
    public String addComment(
            @ModelAttribute(value = "blogPostComment") BlogPostComment blogPostComment,
            @RequestParam(value = "blogPostId") Long blogPostId,
            ModelMap modelMap, BindingResult result, Locale locale
    ) {
        ValidationUtils.rejectIfEmpty(result, "blogPostI18nContentId", ResourceBundle.getBundle(CWSFE_CMS_RESOURCE_BUNDLE, locale).getString("BlogPostMustBeSet"));
        ValidationUtils.rejectIfEmpty(result, "userName", ResourceBundle.getBundle(CWSFE_CMS_RESOURCE_BUNDLE, locale).getString("UsernameMustBeSet"));
        ValidationUtils.rejectIfEmpty(result, "email", ResourceBundle.getBundle(CWSFE_RESOURCE_BUNDLE, locale).getString("EmailMustBeSet"));
        if (blogPostComment.getEmail() != null && !EmailValidator.isValidEmailAddress(blogPostComment.getEmail())) {
            result.rejectValue("email", ResourceBundle.getBundle(CWSFE_RESOURCE_BUNDLE, locale).getString("EmailIsInvalid"));
        }
        ValidationUtils.rejectIfEmpty(result, "comment", ResourceBundle.getBundle(CWSFE_CMS_RESOURCE_BUNDLE, locale).getString("CommentMustBeSet"));
        if (!result.hasErrors()) {
            blogPostComment.setCreated(new Date());
            blogPostCommentsDAO.add(blogPostComment);
            modelMap.addAttribute("addCommentInfoMessage", ResourceBundle.getBundle(CWSFE_RESOURCE_BUNDLE, locale).getString("AddedSuccessfullyWaitForModeratorPublication"));
        } else {
            String addCommentErrorMessage = "";
            for (int i = 0; i < result.getAllErrors().size(); i++) {
                addCommentErrorMessage += result.getAllErrors().get(i).getCode() + "<br/>";
            }
            modelMap.addAttribute("addCommentErrorMessage", addCommentErrorMessage);
        }
        return singlePostView(modelMap, locale, blogPostId, blogPostComment.getBlogPostI18nContentId());
    }

    @RequestMapping(value = "/blogPostCode/{postId}/{codeId}", method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json;charset=UTF-8;pageEncoding=UTF-8")
    @ResponseBody
    public String getBlogPostCode(
            @PathVariable("postId") Long postId,
            @PathVariable("codeId") String codeId
    ) {
        JSONObject responseDetailsJson = new JSONObject();
        final BlogPostCode codeForPost = blogPostCodesDAO.getCodeForPostByCodeId(postId, codeId);
        if (codeForPost == null) {
            responseDetailsJson.put("code", "...");
        } else {
            responseDetailsJson.put("code", "<pre>" + HtmlUtils.htmlEscape(codeForPost.getCode()) + "</pre>");
        }
        return responseDetailsJson.toString();
    }

}
