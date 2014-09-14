package eu.com.cwsfe.cms.controllers;

import eu.com.cwsfe.cms.dao.BlogKeywordsDAO;
import eu.com.cwsfe.cms.model.BlogKeyword;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author Radoslaw Osinski
 */
@Controller
public class BlogKeywordsController implements JsonController {

    @Autowired
    private BlogKeywordsDAO blogKeywordsDAO;

    @RequestMapping(value = "/CWSFE_CMS/blogKeywords", method = RequestMethod.GET)
    public String defaultView(ModelMap model, Locale locale, HttpServletRequest httpServletRequest) {
        model.addAttribute("additionalJavaScriptCode", setAdditionalJS(httpServletRequest.getContextPath()));
        model.addAttribute("breadcrumbs", getBreadcrumbs(locale));
        return "cms/blogkeywords/BlogKeywords";
    }

    private Object setAdditionalJS(String contextPath) {
        List<String> jsUrl = new ArrayList<>(3);
        jsUrl.add(contextPath + "/resources-cwsfe-cms/js/cms/blogkeywords/Blogkeywords.js");
        return jsUrl;
    }

    private List<String> getBreadcrumbs(Locale locale) {
        List<String> breadcrumbs = new ArrayList<>(1);
        breadcrumbs.add("<a href=\"" +
                ServletUriComponentsBuilder.fromCurrentContextPath().path("/CWSFE_CMS/blogKeywords").build().toUriString() +
                "\" tabindex=\"-1\">" + ResourceBundle.getBundle("cwsfe_cms_i18n", locale).getString("BlogKeywordsManagement") + "</a>");
        return breadcrumbs;
    }

    @RequestMapping(value = "/CWSFE_CMS/blogKeywordsList", method = RequestMethod.GET, produces = "application/json;charset=UTF-8;pageEncoding=UTF-8")
    public @ResponseBody String listBlogKeywords(
            @RequestParam int iDisplayStart,
            @RequestParam int iDisplayLength,
            @RequestParam String sEcho
    ) {
        final List<BlogKeyword> blogKeywords = blogKeywordsDAO.listAjax(iDisplayStart, iDisplayLength);
        JSONObject responseDetailsJson = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < blogKeywords.size(); i++) {
            JSONObject formDetailsJson = new JSONObject();
            formDetailsJson.put("#", iDisplayStart + i + 1);
            formDetailsJson.put("keywordName", blogKeywords.get(i).getKeywordName());
            formDetailsJson.put("id", blogKeywords.get(i).getId());
            jsonArray.add(formDetailsJson);
        }
        responseDetailsJson.put("sEcho", sEcho);
        final int numberOfBlogKeywords = blogKeywordsDAO.countForAjax();
        responseDetailsJson.put("iTotalRecords", numberOfBlogKeywords);
        responseDetailsJson.put("iTotalDisplayRecords", numberOfBlogKeywords);
        responseDetailsJson.put("aaData", jsonArray);
        return responseDetailsJson.toString();
    }

    @RequestMapping(value = "/CWSFE_CMS/addBlogKeyword", method = RequestMethod.POST, produces = "application/json;charset=UTF-8;pageEncoding=UTF-8")
    public @ResponseBody String addBlogKeyword(
            @ModelAttribute(value = "blogKeyword") BlogKeyword blogKeyword,
            BindingResult result, Locale locale
    ) {
        ValidationUtils.rejectIfEmpty(result, "keywordName", ResourceBundle.getBundle("cwsfe_cms_i18n", locale).getString("KeywordNameMustBeSet"));
        JSONObject responseDetailsJson = new JSONObject();
        if (!result.hasErrors()) {
            blogKeywordsDAO.add(blogKeyword);
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

    @RequestMapping(value = "/CWSFE_CMS/deleteBlogKeyword", method = RequestMethod.POST, produces = "application/json;charset=UTF-8;pageEncoding=UTF-8")
    public @ResponseBody String deleteFolder(
            @ModelAttribute(value = "blogKeyword") BlogKeyword blogKeyword,
            BindingResult result, Locale locale
    ) {
        ValidationUtils.rejectIfEmpty(result, "id", ResourceBundle.getBundle("cwsfe_cms_i18n", locale).getString("FolderMustBeSet"));
        JSONObject responseDetailsJson = new JSONObject();
        if (!result.hasErrors()) {
            blogKeywordsDAO.delete(blogKeyword);
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

}
