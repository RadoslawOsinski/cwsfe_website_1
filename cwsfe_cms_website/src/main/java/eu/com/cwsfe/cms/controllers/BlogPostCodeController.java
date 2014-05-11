package eu.com.cwsfe.cms.controllers;

import eu.com.cwsfe.cms.dao.BlogPostCodesDAO;
import eu.com.cwsfe.cms.model.BlogPostCode;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author Radoslaw Osinski
 */
@Controller
public class BlogPostCodeController {

    @Autowired
    private BlogPostCodesDAO blogPostCodesDAO;

    @RequestMapping(value = "/CWSFE_CMS/blogPosts/blogPostCodesList", method = RequestMethod.GET, produces = "application/json;charset=UTF-8;pageEncoding=UTF-8")
    public @ResponseBody String listBlogKeywords(
            @RequestParam int iDisplayStart,
            @RequestParam int iDisplayLength,
            @RequestParam String sEcho,
            WebRequest webRequest
    ) {
        Long blogPostId = null;
        try {
            blogPostId = Long.parseLong(webRequest.getParameter("blogPostId"));
        } catch (NumberFormatException ignored) {
        }
        List<BlogPostCode> dbList = blogPostCodesDAO.searchByAjax(iDisplayStart, iDisplayLength, blogPostId);
        Integer dbListDisplayRecordsSize = blogPostCodesDAO.searchByAjaxCount(blogPostId);
        JSONObject responseDetailsJson = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < dbList.size(); i++) {
            JSONObject formDetailsJson = new JSONObject();
            formDetailsJson.put("#", iDisplayStart + i + 1);
            final BlogPostCode object = dbList.get(i);
            formDetailsJson.put("codeId", object.getCodeId());
            if (object.getCode() == null || object.getCode().isEmpty()) {
                formDetailsJson.put("code", "---");
            } else {
                formDetailsJson.put("code", (object.getCode().length() < 100) ? object.getCode() : object.getCode().substring(0, 97) + "...");
            }
            formDetailsJson.put("id", object.getCodeId());
            jsonArray.add(formDetailsJson);
        }
        responseDetailsJson.put("sEcho", sEcho);
        responseDetailsJson.put("iTotalRecords", blogPostCodesDAO.getTotalNumberNotDeleted());
        responseDetailsJson.put("iTotalDisplayRecords", dbListDisplayRecordsSize);
        responseDetailsJson.put("aaData", jsonArray);
        return responseDetailsJson.toString();
    }

    @RequestMapping(value = "/CWSFE_CMS/blogPosts/addBlogPostCode", method = RequestMethod.POST, produces = "application/json;charset=UTF-8;pageEncoding=UTF-8")
    public @ResponseBody String addNews(
            @ModelAttribute(value = "blogPostCode") BlogPostCode blogPostCode,
            BindingResult result, Locale locale
    ) {
        ValidationUtils.rejectIfEmpty(result, "codeId", ResourceBundle.getBundle("cwsfe_cms_i18n", locale).getString("CodeIdMustBeSet"));
        ValidationUtils.rejectIfEmpty(result, "blogPostId", ResourceBundle.getBundle("cwsfe_cms_i18n", locale).getString("BlogPostMustBeSet"));
        ValidationUtils.rejectIfEmpty(result, "code", ResourceBundle.getBundle("cwsfe_cms_i18n", locale).getString("CodeMustBeSet"));
        final BlogPostCode existingCode = blogPostCodesDAO.getCodeForPostByCodeId(blogPostCode.getBlogPostId(), blogPostCode.getCodeId());
        if (existingCode != null) {
            result.rejectValue("code", ResourceBundle.getBundle("cwsfe_cms_i18n", locale).getString("CodeIdMustBeUnique"));
        }
        JSONObject responseDetailsJson = new JSONObject();
        if (!result.hasErrors()) {
            blogPostCodesDAO.add(blogPostCode);
            responseDetailsJson.put("status", "SUCCESS");
            responseDetailsJson.put("result", "");
        } else {
            responseDetailsJson.put("status", "FAIL");
            JSONArray jsonArray = new JSONArray();
            for (int i = 0; i < result.getAllErrors().size(); i++) {
                JSONObject formDetailsJson = new JSONObject();
                formDetailsJson.put("error", result.getAllErrors().get(i).getCode());
                jsonArray.add(formDetailsJson);
            }
            responseDetailsJson.put("result", jsonArray);
        }
        return responseDetailsJson.toString();
    }

    @RequestMapping(value = "/CWSFE_CMS/blogPosts/deleteBlogPostCode", method = RequestMethod.POST, produces = "application/json;charset=UTF-8;pageEncoding=UTF-8")
    public @ResponseBody String blogPostCodeDelete(
            @ModelAttribute(value = "blogPostCode") BlogPostCode blogPostCode,
            BindingResult result, Locale locale
    ) {
        ValidationUtils.rejectIfEmpty(result, "codeId", ResourceBundle.getBundle("cwsfe_cms_i18n", locale).getString("CodeIdMustBeSet"));
        ValidationUtils.rejectIfEmpty(result, "blogPostId", ResourceBundle.getBundle("cwsfe_cms_i18n", locale).getString("BlogPostMustBeSet"));
        JSONObject responseDetailsJson = new JSONObject();
        if (!result.hasErrors()) {
            blogPostCodesDAO.delete(blogPostCode);
            responseDetailsJson.put("status", "SUCCESS");
            responseDetailsJson.put("result", "");
        } else {
            responseDetailsJson.put("status", "FAIL");
            JSONArray jsonArray = new JSONArray();
            for (int i = 0; i < result.getAllErrors().size(); i++) {
                JSONObject formDetailsJson = new JSONObject();
                formDetailsJson.put("error", result.getAllErrors().get(i).getCode());
                jsonArray.add(formDetailsJson);
            }
            responseDetailsJson.put("result", jsonArray);
        }
        return responseDetailsJson.toString();
    }

}
