package eu.com.cwsfe.cms.controllers;

import eu.com.cwsfe.cms.dao.BlogPostCommentsDAO;
import eu.com.cwsfe.cms.model.BlogPostComment;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author Radoslaw Osinski
 */
@Controller
public class BlogPostCommentsController extends JsonController {

    @Autowired
    private BlogPostCommentsDAO blogPostCommentsDAO;

    @RequestMapping(value = "/CWSFE_CMS/publishBlogPostComment", method = RequestMethod.POST, produces = "application/json;charset=UTF-8;pageEncoding=UTF-8")
    @ResponseBody
    public String blogPostCommentPublish(
            @ModelAttribute(value = "blogPostComment") BlogPostComment blogPostComment,
            BindingResult result, Locale locale
    ) {
        ValidationUtils.rejectIfEmpty(result, "id", ResourceBundle.getBundle(CWSFE_CMS_RESOURCE_BUNDLE_PATH, locale).getString("CommentMustBeSet"));
        JSONObject responseDetailsJson = new JSONObject();
        if (!result.hasErrors()) {
            blogPostCommentsDAO.publish(blogPostComment);
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

    @RequestMapping(value = "/CWSFE_CMS/blockBlogPostComment", method = RequestMethod.POST, produces = "application/json;charset=UTF-8;pageEncoding=UTF-8")
    @ResponseBody
    public String blogPostCommentBlock(
            @ModelAttribute(value = "blogPostComment") BlogPostComment blogPostComment,
            BindingResult result, Locale locale
    ) {
        ValidationUtils.rejectIfEmpty(result, "id", ResourceBundle.getBundle(CWSFE_CMS_RESOURCE_BUNDLE_PATH, locale).getString("CommentMustBeSet"));
        JSONObject responseDetailsJson = new JSONObject();
        if (!result.hasErrors()) {
            blogPostCommentsDAO.block(blogPostComment);
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

    @RequestMapping(value = "/CWSFE_CMS/markAsSpamBlogPostComment", method = RequestMethod.POST, produces = "application/json;charset=UTF-8;pageEncoding=UTF-8")
    @ResponseBody
    public String blogPostCommentMarkAsSpam(
            @ModelAttribute(value = "blogPostComment") BlogPostComment blogPostComment,
            BindingResult result, Locale locale
    ) {
        ValidationUtils.rejectIfEmpty(result, "id", ResourceBundle.getBundle(CWSFE_CMS_RESOURCE_BUNDLE_PATH, locale).getString("CommentMustBeSet"));
        JSONObject responseDetailsJson = new JSONObject();
        if (!result.hasErrors()) {
            blogPostCommentsDAO.markAsSpam(blogPostComment);
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

    @RequestMapping(value = "/CWSFE_CMS/deleteBlogPostComment", method = RequestMethod.POST, produces = "application/json;charset=UTF-8;pageEncoding=UTF-8")
    @ResponseBody
    public String blogPostCommentDelete(
            @ModelAttribute(value = "blogPostComment") BlogPostComment blogPostComment,
            BindingResult result, Locale locale
    ) {
        ValidationUtils.rejectIfEmpty(result, "id", ResourceBundle.getBundle(CWSFE_CMS_RESOURCE_BUNDLE_PATH, locale).getString("CommentMustBeSet"));
        JSONObject responseDetailsJson = new JSONObject();
        if (!result.hasErrors()) {
            blogPostCommentsDAO.delete(blogPostComment);
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
