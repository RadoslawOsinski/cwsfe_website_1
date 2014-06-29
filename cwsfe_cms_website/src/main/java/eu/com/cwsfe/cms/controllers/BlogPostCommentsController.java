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
public class BlogPostCommentsController {

    @Autowired
    private BlogPostCommentsDAO blogPostCommentsDAO;

    @RequestMapping(value = "/CWSFE_CMS/publishBlogPostComment", method = RequestMethod.POST, produces = "application/json;charset=UTF-8;pageEncoding=UTF-8")
    public @ResponseBody String blogPostCommentPublish(
            @ModelAttribute(value = "blogPostComment") BlogPostComment blogPostComment,
            BindingResult result, Locale locale
    ) {
        ValidationUtils.rejectIfEmpty(result, "id", ResourceBundle.getBundle("cwsfe_cms_i18n", locale).getString("CommentMustBeSet"));
        JSONObject responseDetailsJson = new JSONObject();
        if (!result.hasErrors()) {
            blogPostCommentsDAO.publish(blogPostComment);
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

    @RequestMapping(value = "/CWSFE_CMS/blockBlogPostComment", method = RequestMethod.POST, produces = "application/json;charset=UTF-8;pageEncoding=UTF-8")
    public @ResponseBody String blogPostCommentBlock(
            @ModelAttribute(value = "blogPostComment") BlogPostComment blogPostComment,
            BindingResult result, Locale locale
    ) {
        ValidationUtils.rejectIfEmpty(result, "id", ResourceBundle.getBundle("cwsfe_cms_i18n", locale).getString("CommentMustBeSet"));
        JSONObject responseDetailsJson = new JSONObject();
        if (!result.hasErrors()) {
            blogPostCommentsDAO.block(blogPostComment);
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

    @RequestMapping(value = "/CWSFE_CMS/markAsSpamBlogPostComment", method = RequestMethod.POST, produces = "application/json;charset=UTF-8;pageEncoding=UTF-8")
    public @ResponseBody String blogPostCommentMarkAsSpam(
            @ModelAttribute(value = "blogPostComment") BlogPostComment blogPostComment,
            BindingResult result, Locale locale
    ) {
        ValidationUtils.rejectIfEmpty(result, "id", ResourceBundle.getBundle("cwsfe_cms_i18n", locale).getString("CommentMustBeSet"));
        JSONObject responseDetailsJson = new JSONObject();
        if (!result.hasErrors()) {
            blogPostCommentsDAO.markAsSpam(blogPostComment);
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

    @RequestMapping(value = "/CWSFE_CMS/deleteBlogPostComment", method = RequestMethod.POST, produces = "application/json;charset=UTF-8;pageEncoding=UTF-8")
    public @ResponseBody String blogPostCommentDelete(
            @ModelAttribute(value = "blogPostComment") BlogPostComment blogPostComment,
            BindingResult result, Locale locale
    ) {
        ValidationUtils.rejectIfEmpty(result, "id", ResourceBundle.getBundle("cwsfe_cms_i18n", locale).getString("CommentMustBeSet"));
        JSONObject responseDetailsJson = new JSONObject();
        if (!result.hasErrors()) {
            blogPostCommentsDAO.delete(blogPostComment);
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
