package eu.com.cwsfe.cms.controllers;

import eu.com.cwsfe.cms.dao.BlogPostCommentsDAO;
import eu.com.cwsfe.cms.dao.BlogPostsDAO;
import eu.com.cwsfe.cms.model.BlogPostComment;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Radoslaw Osinski
 */
@Controller
class MainCmsController {

    @Autowired
    private BlogPostsDAO blogPostsDAO;
    @Autowired
    private BlogPostCommentsDAO blogPostCommentsDAO;

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    @RequestMapping(value="/CWSFE_CMS/Main", method = RequestMethod.GET)
    public String printDashboard(ModelMap model, Principal principal, HttpServletRequest httpServletRequest) {
        String name = principal.getName();
        model.addAttribute("username", name);
        model.addAttribute("additionalCssCode", setAdditionalCss(httpServletRequest.getContextPath()));
        model.addAttribute("additionalJavaScriptCode", setAdditionalJS(httpServletRequest.getContextPath()));
        return "cms/main/Main";
    }

    private List<String> setAdditionalCss(String contextPath) {
        List<String> cssUrl = new ArrayList<>(3);
        cssUrl.add(contextPath + "/resources-cwsfe-cms/css/tipsy/tipsy-min.css");
        return cssUrl;
    }

    private List<String> setAdditionalJS(String contextPath) {
        List<String> jsUrl = new ArrayList<>(3);
        jsUrl.add(contextPath + "/resources-cwsfe-cms/js/tipsy/tipsy.js");
//        jsUrl.add(contextPath + "/resources-cwsfe-cms/js/jqplot/jquery.jqplot.min.js");
//        jsUrl.add(contextPath + "/resources-cwsfe-cms/js/jqplot/jqplot.dateAxisRenderer.min.js");
        jsUrl.add(contextPath + "/resources-cwsfe-cms/js/cms/main/Dashboard.js");
        return jsUrl;
    }

    @RequestMapping(value = "/CWSFE_CMS/blogPostsListForChart", method = RequestMethod.GET, produces = "application/json;charset=UTF-8;pageEncoding=UTF-8")
    public @ResponseBody String listBlogPosts() {
        List<Object[]> dbList = blogPostsDAO.listArchiveStatistics();
        JSONObject responseDetailsJson = new JSONObject();
        JSONArray jsonArray1 = new JSONArray();
        JSONArray jsonArray2 = new JSONArray();
        for (int i = dbList.size() - 1; i >= 0; i--) {
            JSONArray array = new JSONArray();
            Object[] object = dbList.get(i);
            array.add("\"" + object[1] + "-" + object[2] + "-01\"");
            array.add(object[0]);
            jsonArray2.add(array);
        }
        jsonArray1.add(jsonArray2);
        responseDetailsJson.put("statistics", jsonArray1);
        responseDetailsJson.put("status", "OK");
        return responseDetailsJson.toString();
    }

    @RequestMapping(value = "/CWSFE_CMS/blogPostCommentsList", method = RequestMethod.GET, produces = "application/json;charset=UTF-8;pageEncoding=UTF-8")
    public @ResponseBody String listBlogPostComments(
            @RequestParam int iDisplayStart,
            @RequestParam int iDisplayLength,
            @RequestParam String sEcho
    ) {
        List<BlogPostComment> dbList = blogPostCommentsDAO.searchByAjax(iDisplayStart, iDisplayLength);
        Integer dbListDisplayRecordsSize = blogPostCommentsDAO.searchByAjaxCount();
        JSONObject responseDetailsJson = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < dbList.size(); i++) {
            JSONObject formDetailsJson = new JSONObject();
            formDetailsJson.put("#", iDisplayStart + i + 1);
            final BlogPostComment objects = dbList.get(i);
            formDetailsJson.put("username", objects.getUsername() + "[" + objects.getEmail() + "]");
            formDetailsJson.put("comment", objects.getComment());
            formDetailsJson.put("created", DATE_FORMAT.format(objects.getCreated()));
            formDetailsJson.put("status", objects.getStatus());
            formDetailsJson.put("id", objects.getId());
            jsonArray.add(formDetailsJson);
        }
        responseDetailsJson.put("sEcho", sEcho);
        responseDetailsJson.put("iTotalRecords", blogPostCommentsDAO.getTotalNumberNotDeleted());
        responseDetailsJson.put("iTotalDisplayRecords", dbListDisplayRecordsSize);
        responseDetailsJson.put("aaData", jsonArray);
        return responseDetailsJson.toString();
    }

}
