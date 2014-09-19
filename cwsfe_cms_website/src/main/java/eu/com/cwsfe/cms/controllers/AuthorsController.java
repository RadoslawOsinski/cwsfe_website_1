package eu.com.cwsfe.cms.controllers;

import eu.com.cwsfe.cms.BreadCrumbBuilder;
import eu.com.cwsfe.cms.dao.CmsAuthorsDAO;
import eu.com.cwsfe.cms.model.CmsAuthor;
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
public class AuthorsController extends JsonController {


    @Autowired
    private CmsAuthorsDAO cmsAuthorsDAO;

    @RequestMapping(value = "/CWSFE_CMS/authors", method = RequestMethod.GET)
    public String defaultView(ModelMap model, Locale locale, HttpServletRequest httpServletRequest) {
        model.addAttribute("additionalJavaScriptCode", setAdditionalJS(httpServletRequest.getContextPath()));
        model.addAttribute("breadcrumbs", getBreadcrumbs(locale));
        return "cms/authors/Authors";
    }

    private Object setAdditionalJS(String contextPath) {
        List<String> jsUrl = new ArrayList<>(3);
        jsUrl.add(contextPath + "/resources-cwsfe-cms/js/cms/authors/Authors.js");
        return jsUrl;
    }

    private List<String> getBreadcrumbs(Locale locale) {
        List<String> breadcrumbs = new ArrayList<>(1);
        breadcrumbs.add(BreadCrumbBuilder.getBreadCrumb(
                ServletUriComponentsBuilder.fromCurrentContextPath().path("/CWSFE_CMS/authors").build().toUriString(),
                ResourceBundle.getBundle(CWSFE_CMS_RESOURCE_BUNDLE_PATH, locale).getString("AuthorsManagement")));
        return breadcrumbs;
    }

    @RequestMapping(value = "/CWSFE_CMS/authorsList", method = RequestMethod.GET, produces = "application/json;charset=UTF-8;pageEncoding=UTF-8")
    @ResponseBody
    public String listAuthors(
            @RequestParam int iDisplayStart,
            @RequestParam int iDisplayLength,
            @RequestParam String sEcho
    ) {
        final List<CmsAuthor> cmsAuthors = cmsAuthorsDAO.listAjax(iDisplayStart, iDisplayLength);
        JSONObject responseDetailsJson = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < cmsAuthors.size(); i++) {
            JSONObject formDetailsJson = new JSONObject();
            formDetailsJson.put("#", iDisplayStart + i + 1);
            formDetailsJson.put("lastName", cmsAuthors.get(i).getLastName());
            formDetailsJson.put("firstName", cmsAuthors.get(i).getFirstName());
            formDetailsJson.put("googlePlusAuthorLink", cmsAuthors.get(i).getGooglePlusAuthorLink() == null ? "" : cmsAuthors.get(i).getGooglePlusAuthorLink());
            formDetailsJson.put("id", cmsAuthors.get(i).getId());
            jsonArray.add(formDetailsJson);
        }
        responseDetailsJson.put("sEcho", sEcho);
        final int numberOfAuthors = cmsAuthorsDAO.countForAjax();
        responseDetailsJson.put("iTotalRecords", numberOfAuthors);
        responseDetailsJson.put("iTotalDisplayRecords", numberOfAuthors);
        responseDetailsJson.put("aaData", jsonArray);
        return responseDetailsJson.toString();
    }

    @RequestMapping(value = "/CWSFE_CMS/authorsDropList", method = RequestMethod.GET, produces = "application/json;charset=UTF-8;pageEncoding=UTF-8")
    @ResponseBody
    public String listAuthorsForDropList(
            @RequestParam String term,
            @RequestParam Integer limit
    ) {
        final List<CmsAuthor> cmsAuthors = cmsAuthorsDAO.listAuthorsForDropList(term, limit);
        JSONObject responseDetailsJson = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (CmsAuthor cmsAuthor : cmsAuthors) {
            JSONObject formDetailsJson = new JSONObject();
            formDetailsJson.put("id", cmsAuthor.getId());
            formDetailsJson.put("lastName", cmsAuthor.getLastName());
            formDetailsJson.put("firstName", cmsAuthor.getFirstName());
            formDetailsJson.put("googlePlusAuthorLink", cmsAuthor.getGooglePlusAuthorLink());
            jsonArray.add(formDetailsJson);
        }
        responseDetailsJson.put("data", jsonArray);
        return responseDetailsJson.toString();
    }

    @RequestMapping(value = "/CWSFE_CMS/addAuthor", method = RequestMethod.POST, produces = "application/json;charset=UTF-8;pageEncoding=UTF-8")
    @ResponseBody
    public String addAuthor(
            @ModelAttribute(value = "cmsAuthor") CmsAuthor cmsAuthor,
            BindingResult result, Locale locale
    ) {
        ValidationUtils.rejectIfEmpty(result, "firstName", ResourceBundle.getBundle(CWSFE_CMS_RESOURCE_BUNDLE_PATH, locale).getString("FirstNameMustBeSet"));
        ValidationUtils.rejectIfEmpty(result, "lastName", ResourceBundle.getBundle(CWSFE_CMS_RESOURCE_BUNDLE_PATH, locale).getString("LastNameMustBeSet"));
        JSONObject responseDetailsJson = new JSONObject();
        if (!result.hasErrors()) {
            cmsAuthorsDAO.add(cmsAuthor);
            addJsonSuccess(responseDetailsJson);
        } else {
            prepareErrorResponse(result, responseDetailsJson);
        }
        return responseDetailsJson.toString();
    }

    @RequestMapping(value = "/CWSFE_CMS/deleteAuthor", method = RequestMethod.POST, produces = "application/json;charset=UTF-8;pageEncoding=UTF-8")
    @ResponseBody
    public String deleteAuthor(
            @ModelAttribute(value = "cmsAuthor") CmsAuthor cmsAuthor,
            BindingResult result, Locale locale
    ) {
        ValidationUtils.rejectIfEmpty(result, "id", ResourceBundle.getBundle(CWSFE_CMS_RESOURCE_BUNDLE_PATH, locale).getString("AuthorMustBeSet"));
        JSONObject responseDetailsJson = new JSONObject();
        if (!result.hasErrors()) {
            cmsAuthorsDAO.delete(cmsAuthor);
            addJsonSuccess(responseDetailsJson);
        } else {
            prepareErrorResponse(result, responseDetailsJson);
        }
        return responseDetailsJson.toString();
    }

}
