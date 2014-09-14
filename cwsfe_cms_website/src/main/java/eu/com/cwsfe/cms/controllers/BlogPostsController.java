package eu.com.cwsfe.cms.controllers;

import eu.com.cwsfe.cms.dao.*;
import eu.com.cwsfe.cms.model.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Radoslaw Osinski
 */
@Controller
public class BlogPostsController extends JsonController {

    private static final Logger LOGGER = LogManager.getLogger(BlogPostsController.class);

    @Autowired
    private BlogKeywordsDAO blogKeywordsDAO;
    @Autowired
    private BlogPostKeywordsDAO blogPostKeywordsDAO;
    @Autowired
    private BlogPostI18nContentsDAO blogPostI18nContentsDAO;
    @Autowired
    private BlogPostsDAO blogPostsDAO;
    @Autowired
    private CmsAuthorsDAO cmsAuthorsDAO;
    @Autowired
    private CmsLanguagesDAO cmsLanguagesDAO;

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @RequestMapping(value = "/CWSFE_CMS/blogPosts", method = RequestMethod.GET)
    public String defaultView(ModelMap model, Locale locale, HttpServletRequest httpServletRequest) {
        model.addAttribute("additionalJavaScriptCode", setAdditionalJS(httpServletRequest.getContextPath()));
        model.addAttribute("breadcrumbs", getBreadcrumbs(locale));
        return "cms/blog/Posts";
    }

    private Object setAdditionalJS(String contextPath) {
        List<String> jsUrl = new ArrayList<>(3);
        jsUrl.add(contextPath + "/resources-cwsfe-cms/js/cms/blog/Posts.js");
        return jsUrl;
    }

    private List<String> getBreadcrumbs(Locale locale) {
        List<String> breadcrumbs = new ArrayList<>(1);
        breadcrumbs.add("<a href=\"" +
                ServletUriComponentsBuilder.fromCurrentContextPath().path("/CWSFE_CMS/blogPosts").build().toUriString() +
                "\" tabindex=\"-1\">" + ResourceBundle.getBundle(CWSFE_CMS_RESOURCE_BUNDLE_PATH, locale).getString("BlogPostManagement") + "</a>");
        return breadcrumbs;
    }

    private Object setSingleBlogPostsAdditionalJS(String contextPath) {
        List<String> jsUrl = new ArrayList<>(3);
        jsUrl.add(contextPath + "/resources-cwsfe-cms/js/cms/blog/SinglePost.js");
        return jsUrl;
    }

    private List<String> getSingleBlogPostsBreadcrumbs(Locale locale, Long id) {
        List<String> breadcrumbs = new ArrayList<>(1);
        breadcrumbs.add("<a href=\"" +
                ServletUriComponentsBuilder.fromCurrentContextPath().path("/CWSFE_CMS/blogPosts").build().toUriString() +
                "\" tabindex=\"-1\">" + ResourceBundle.getBundle(CWSFE_CMS_RESOURCE_BUNDLE_PATH, locale).getString("BlogPostsManagement") + "</a>");
        breadcrumbs.add("<a href=\"" +
                ServletUriComponentsBuilder.fromCurrentContextPath().path("/CWSFE_CMS/blogPosts/" + id).build().toUriString() +
                "\" tabindex=\"-1\">" + ResourceBundle.getBundle(CWSFE_CMS_RESOURCE_BUNDLE_PATH, locale).getString("CurrentBlogPost") + "</a>");
        return breadcrumbs;
    }

    @RequestMapping(value = "/CWSFE_CMS/blogPostsList", method = RequestMethod.GET, produces = "application/json;charset=UTF-8;pageEncoding=UTF-8")
    public
    @ResponseBody
    String listBlogPosts(
            @RequestParam int iDisplayStart,
            @RequestParam int iDisplayLength,
            @RequestParam String sEcho,
            @RequestParam(required = false) String searchPostTextCode,
            WebRequest webRequest
    ) {
        Integer searchAuthorId = null;
        try {
            searchAuthorId = Integer.parseInt(webRequest.getParameter("searchAuthorId"));
        } catch (NumberFormatException e) {
            LOGGER.error("Search author id is not a number: " + webRequest.getParameter("searchAuthorId"));
        }
        List<Object[]> dbList = blogPostsDAO.searchByAjax(iDisplayStart, iDisplayLength, searchAuthorId, searchPostTextCode);
        Integer dbListDisplayRecordsSize = blogPostsDAO.searchByAjaxCount(searchAuthorId, searchPostTextCode);
        JSONObject responseDetailsJson = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < dbList.size(); i++) {
            JSONObject formDetailsJson = new JSONObject();
            formDetailsJson.put("#", iDisplayStart + i + 1);
            final Object[] objects = dbList.get(i);
            if (objects[1] == null || ((String) objects[1]).isEmpty()) {
                formDetailsJson.put("author", "---");
            } else {
                formDetailsJson.put("author", objects[1]);
            }
            formDetailsJson.put("postTextCode", objects[2]);
            formDetailsJson.put("postCreationDate", DATE_FORMAT.format(((Date) objects[3]).toInstant()));
            formDetailsJson.put("id", objects[0]);
            jsonArray.add(formDetailsJson);
        }
        responseDetailsJson.put("sEcho", sEcho);
        responseDetailsJson.put("iTotalRecords", blogPostsDAO.getTotalNumberNotDeleted());
        responseDetailsJson.put("iTotalDisplayRecords", dbListDisplayRecordsSize);
        responseDetailsJson.put("aaData", jsonArray);
        return responseDetailsJson.toString();
    }

    @RequestMapping(value = "/CWSFE_CMS/addBlogPost", method = RequestMethod.POST, produces = "application/json;charset=UTF-8;pageEncoding=UTF-8")
    public
    @ResponseBody
    String addBlogPost(
            @ModelAttribute(value = "blogPost") BlogPost blogPost,
            BindingResult result, Locale locale
    ) {
        ValidationUtils.rejectIfEmpty(result, "postAuthorId", ResourceBundle.getBundle(CWSFE_CMS_RESOURCE_BUNDLE_PATH, locale).getString("AuthorMustBeSet"));
        ValidationUtils.rejectIfEmpty(result, "postTextCode", ResourceBundle.getBundle(CWSFE_CMS_RESOURCE_BUNDLE_PATH, locale).getString("PostTextCodeMustBeSet"));
        JSONObject responseDetailsJson = new JSONObject();
        if (!result.hasErrors()) {
            blogPost.setPostCreationDate(new Date());
            blogPost.setStatus("H");
            blogPostsDAO.add(blogPost);
            responseDetailsJson.put(JSON_STATUS, JSON_STATUS_SUCCESS);
            responseDetailsJson.put(JSON_RESULT, "");
        } else {
            responseDetailsJson.put(JSON_STATUS, JSON_STATUS_FAIL);
            JSONArray jsonArray = new JSONArray();
            for (int i = 0; i < result.getAllErrors().size(); i++) {
                JSONObject formDetailsJson = new JSONObject();
                formDetailsJson.put("error", result.getAllErrors().get(i).getCode());
                jsonArray.add(formDetailsJson);
            }
            responseDetailsJson.put(JSON_RESULT, jsonArray);
        }
        return responseDetailsJson.toString();
    }

    @RequestMapping(value = "/CWSFE_CMS/deleteBlogPost", method = RequestMethod.POST, produces = "application/json;charset=UTF-8;pageEncoding=UTF-8")
    public
    @ResponseBody
    String delete(
            @ModelAttribute(value = "blogPost") BlogPost blogPost,
            BindingResult result, Locale locale
    ) {
        ValidationUtils.rejectIfEmpty(result, "id", ResourceBundle.getBundle(CWSFE_CMS_RESOURCE_BUNDLE_PATH, locale).getString("BlogPostMustBeSet"));
        JSONObject responseDetailsJson = new JSONObject();
        if (!result.hasErrors()) {
            blogPostsDAO.delete(blogPost);
            responseDetailsJson.put(JSON_STATUS, JSON_STATUS_SUCCESS);
            responseDetailsJson.put(JSON_RESULT, "");
        } else {
            responseDetailsJson.put(JSON_STATUS, JSON_STATUS_FAIL);
            JSONArray jsonArray = new JSONArray();
            for (int i = 0; i < result.getAllErrors().size(); i++) {
                JSONObject formDetailsJson = new JSONObject();
                formDetailsJson.put("error", result.getAllErrors().get(i).getCode());
                jsonArray.add(formDetailsJson);
            }
            responseDetailsJson.put(JSON_RESULT, jsonArray);
        }
        return responseDetailsJson.toString();
    }

    @RequestMapping(value = "/CWSFE_CMS/blogPosts/{id}", method = RequestMethod.GET)
    public String browseBlogPost(ModelMap model, Locale locale, @PathVariable("id") Long id, HttpServletRequest httpServletRequest) {
        model.addAttribute("additionalJavaScriptCode", setSingleBlogPostsAdditionalJS(httpServletRequest.getContextPath()));
        model.addAttribute("breadcrumbs", getSingleBlogPostsBreadcrumbs(locale, id));
        BlogPost blogPost = blogPostsDAO.get(id);
        final List<Lang> langs = cmsLanguagesDAO.listAll();
        model.addAttribute("cmsLangs", langs);
        Map<String, BlogPostI18nContent> blogPostI18nContents = new HashMap<>(langs.size());
        for (Lang lang : langs) {
            blogPostI18nContents.put(lang.getCode(), blogPostI18nContentsDAO.getByLanguageForPost(blogPost.getId(), lang.getId()));
        }
        blogPost.setBlogPostI18nContent(blogPostI18nContents);
        blogPost.setBlogKeywords(blogPostKeywordsDAO.listForPost(blogPost.getId()));
        List<Long> blogPostSelectedKeywords = new ArrayList<>(5);
        blogPostSelectedKeywords.addAll(blogPost.getBlogKeywords().stream().map(BlogKeyword::getId).collect(Collectors.toList()));
        model.addAttribute("blogPostSelectedKeywords", blogPostSelectedKeywords);
        model.addAttribute("blogKeywords", blogKeywordsDAO.list());
        model.addAttribute("blogPost", blogPost);
        model.addAttribute("cmsAuthor", cmsAuthorsDAO.get(blogPost.getPostAuthorId()));
        return "cms/blog/SingleBlogPost";
    }

    @RequestMapping(value = "/CWSFE_CMS/blogPosts/updatePostBasicInfo", method = RequestMethod.POST, produces = "application/json;charset=UTF-8;pageEncoding=UTF-8")
    public
    @ResponseBody
    String updatePostBasicInfo(
            @ModelAttribute(value = "blogPost") BlogPost blogPost,
            BindingResult result, Locale locale
    ) {
        ValidationUtils.rejectIfEmpty(result, "id", ResourceBundle.getBundle(CWSFE_CMS_RESOURCE_BUNDLE_PATH, locale).getString("BlogPostMustBeSet"));
        ValidationUtils.rejectIfEmpty(result, "postTextCode", ResourceBundle.getBundle(CWSFE_CMS_RESOURCE_BUNDLE_PATH, locale).getString("PostTextCodeMustBeSet"));
        ValidationUtils.rejectIfEmpty(result, "status", ResourceBundle.getBundle(CWSFE_CMS_RESOURCE_BUNDLE_PATH, locale).getString("StatusMustBeSet"));
        JSONObject responseDetailsJson = new JSONObject();
        if (!result.hasErrors()) {
            blogPostsDAO.updatePostBasicInfo(blogPost);
            responseDetailsJson.put(JSON_STATUS, JSON_STATUS_SUCCESS);
            responseDetailsJson.put(JSON_RESULT, "");
        } else {
            responseDetailsJson.put(JSON_STATUS, JSON_STATUS_FAIL);
            JSONArray jsonArray = new JSONArray();
            for (int i = 0; i < result.getAllErrors().size(); i++) {
                JSONObject formDetailsJson = new JSONObject();
                formDetailsJson.put("error", result.getAllErrors().get(i).getCode());
                jsonArray.add(formDetailsJson);
            }
            responseDetailsJson.put(JSON_RESULT, jsonArray);
        }
        return responseDetailsJson.toString();
    }

    @RequestMapping(value = "addBlogPostsI18nContent", method = RequestMethod.POST)
    public ModelAndView addBlogPostsI18nContent(
            @ModelAttribute(value = "blogPostI18nContent") BlogPostI18nContent blogPostI18nContent,
            ModelMap model, Locale locale, HttpServletRequest httpServletRequest
    ) {
        blogPostI18nContentsDAO.add(blogPostI18nContent);
        browseBlogPost(model, locale, blogPostI18nContent.getPostId(), httpServletRequest);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setView(new RedirectView("/CWSFE_CMS/blogPosts/" + blogPostI18nContent.getPostId(), true, false, false));
        return modelAndView;
    }

    @RequestMapping(value = "/CWSFE_CMS/blogPosts/updateBlogPostI18nContent", method = RequestMethod.POST)
    public ModelAndView updateBlogPostI18nContent(
            @ModelAttribute(value = "BlogPostI18nContent") BlogPostI18nContent blogPostI18nContent,
            ModelMap model, Locale locale, HttpServletRequest httpServletRequest
    ) {
        blogPostI18nContent.setPostTitle(blogPostI18nContent.getPostTitle().trim());
        blogPostI18nContent.setPostShortcut(blogPostI18nContent.getPostShortcut().trim());
        blogPostI18nContent.setPostDescription(blogPostI18nContent.getPostDescription().trim());
        blogPostI18nContentsDAO.updateContentWithStatus(blogPostI18nContent);
        browseBlogPost(model, locale, blogPostI18nContent.getPostId(), httpServletRequest);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setView(new RedirectView("/CWSFE_CMS/blogPosts/" + blogPostI18nContent.getPostId(), true, false, false));
        return modelAndView;
    }

    @RequestMapping(value = "/CWSFE_CMS/postCategoriesUpdate", method = RequestMethod.POST)
    public ModelAndView postCategoriesUpdate(
            @ModelAttribute(value = "blogPost") BlogPost blogPost,
            ModelMap model, Locale locale,
            WebRequest webRequest, HttpServletRequest httpServletRequest
    ) {
        String[] postCategoriesStrings = webRequest.getParameterValues("postCategories");
        //todo add transactions!
        blogPostKeywordsDAO.deleteForPost(blogPost.getId());
        if (postCategoriesStrings != null) {
            for (String keywordIdString : postCategoriesStrings) {
                blogPostKeywordsDAO.add(new BlogPostKeyword(
                        blogPost.getId(),
                        Long.parseLong(keywordIdString)
                ));
            }
        }
        /////////// end transaction
        browseBlogPost(model, locale, blogPost.getId(), httpServletRequest);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setView(new RedirectView("/CWSFE_CMS/blogPosts/" + blogPost.getId(), true, false, false));
        return modelAndView;
    }

}
