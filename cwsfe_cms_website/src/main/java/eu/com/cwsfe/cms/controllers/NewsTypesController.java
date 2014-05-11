package eu.com.cwsfe.cms.controllers;

import eu.com.cwsfe.cms.dao.NewsTypesDAO;
import eu.com.cwsfe.cms.model.NewsType;
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
class NewsTypesController {

    @Autowired
    private NewsTypesDAO newsTypesDAO;

    @RequestMapping(value = "/CWSFE_CMS/newsTypes", method = RequestMethod.GET)
    public String defaultView(ModelMap model, Locale locale, HttpServletRequest httpServletRequest) {
        model.addAttribute("additionalJavaScriptCode", setAdditionalJS(httpServletRequest.getContextPath()));
        model.addAttribute("breadcrumbs", getBreadcrumbs(locale));
        return "cms/newsTypes/NewsTypes";
    }

    private Object setAdditionalJS(String contextPath) {
        List<String> jsUrl = new ArrayList<>(3);
        jsUrl.add(contextPath + "/resources-cwsfe-cms/js/cms/newsTypes/NewsTypes.js");
        return jsUrl;
    }

    private List<String> getBreadcrumbs(Locale locale) {
        List<String> breadcrumbs = new ArrayList<>(1);
        breadcrumbs.add("<a href=\"" +
                ServletUriComponentsBuilder.fromCurrentContextPath().path("/CWSFE_CMS/newsTypes").build().toUriString() +
                "\" tabindex=\"-1\">" + ResourceBundle.getBundle("cwsfe_cms_i18n", locale).getString("NewsTypesManagement") + "</a>");
        return breadcrumbs;
    }

    @RequestMapping(value = "/CWSFE_CMS/newsTypesList", method = RequestMethod.GET, produces = "application/json;charset=UTF-8;pageEncoding=UTF-8")
    public @ResponseBody
    String listNewsTypes(
            @RequestParam int iDisplayStart,
            @RequestParam int iDisplayLength,
            @RequestParam String sEcho
    ) {
        final List<NewsType> cmsNewsTypes = newsTypesDAO.listAjax(iDisplayStart, iDisplayLength);
        JSONObject responseDetailsJson = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < cmsNewsTypes.size(); i++) {
            JSONObject formDetailsJson = new JSONObject();
            formDetailsJson.put("#", iDisplayStart + i + 1);
            formDetailsJson.put("type", cmsNewsTypes.get(i).getType());
            formDetailsJson.put("id", cmsNewsTypes.get(i).getId());
            jsonArray.add(formDetailsJson);
        }
        responseDetailsJson.put("sEcho", sEcho);
        final int numberOfNewsTypes = newsTypesDAO.countForAjax();
        responseDetailsJson.put("iTotalRecords", numberOfNewsTypes);
        responseDetailsJson.put("iTotalDisplayRecords", numberOfNewsTypes);
        responseDetailsJson.put("aaData", jsonArray);
        return responseDetailsJson.toString();
    }

    @RequestMapping(value = "/CWSFE_CMS/news/newsTypesDropList", method = RequestMethod.GET, produces = "application/json;charset=UTF-8;pageEncoding=UTF-8")
    public @ResponseBody String listNewsTypesForDropList(
            @RequestParam String term,
            @RequestParam Integer limit
    ) {
        final List<NewsType> results = newsTypesDAO.listNewsTypesForDropList(term, limit);
        JSONObject responseDetailsJson = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (NewsType newsType : results) {
            JSONObject formDetailsJson = new JSONObject();
            formDetailsJson.put("id", newsType.getId());
            formDetailsJson.put("type", newsType.getType());
            jsonArray.add(formDetailsJson);
        }
        responseDetailsJson.put("data", jsonArray);
        return responseDetailsJson.toString();
    }

    @RequestMapping(value = "/CWSFE_CMS/addNewsType", method = RequestMethod.POST, produces = "application/json;charset=UTF-8;pageEncoding=UTF-8")
    public @ResponseBody String addNewsType(
            @ModelAttribute(value = "newsType") NewsType newsType,
            BindingResult result, Locale locale
    ) {
        ValidationUtils.rejectIfEmpty(result, "type", ResourceBundle.getBundle("cwsfe_cms_i18n", locale).getString("NewsTypeMustBeSet"));
        JSONObject responseDetailsJson = new JSONObject();
        if (!result.hasErrors()) {
            newsTypesDAO.add(newsType);
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

    @RequestMapping(value = "/CWSFE_CMS/deleteNewsType", method = RequestMethod.POST, produces = "application/json;charset=UTF-8;pageEncoding=UTF-8")
    public @ResponseBody String deleteNewsType(
            @ModelAttribute(value = "cmsNewsType") NewsType cmsNewsType,
            BindingResult result, Locale locale
    ) {
        ValidationUtils.rejectIfEmpty(result, "id", ResourceBundle.getBundle("cwsfe_cms_i18n", locale).getString("NewsTypeMustBeSet"));
        JSONObject responseDetailsJson = new JSONObject();
        if (!result.hasErrors()) {
            newsTypesDAO.delete(cmsNewsType);
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
