package eu.com.cwsfe.cms.controllers;

import eu.com.cwsfe.cms.dao.CmsLanguagesDAO;
import eu.com.cwsfe.cms.model.Lang;
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
class LanguagesController {

    @Autowired
    private CmsLanguagesDAO cmsLanguagesDAO;

    @RequestMapping(value = "/CWSFE_CMS/languages", method = RequestMethod.GET)
    public String defaultView(ModelMap model, Locale locale, HttpServletRequest httpServletRequest) {
        model.addAttribute("additionalJavaScriptCode", setAdditionalJS(httpServletRequest.getContextPath()));
        model.addAttribute("breadcrumbs", getBreadcrumbs(locale));
        return "cms/languages/Languages";
    }

    private Object setAdditionalJS(String contextPath) {
        List<String> jsUrl = new ArrayList<>(3);
        jsUrl.add(contextPath + "/resources-cwsfe-cms/js/cms/languages/Languages.js");
        return jsUrl;
    }

    private List<String> getBreadcrumbs(Locale locale) {
        List<String> breadcrumbs = new ArrayList<>(1);
        breadcrumbs.add("<a href=\"" +
                ServletUriComponentsBuilder.fromCurrentContextPath().path("/CWSFE_CMS/languages").build().toUriString() +
                "\" tabindex=\"-1\">" + ResourceBundle.getBundle("cwsfe_cms_i18n", locale).getString("LanguagesManagement") + "</a>");
        return breadcrumbs;
    }

    @RequestMapping(value = "/CWSFE_CMS/languagesList", method = RequestMethod.GET, produces = "application/json;charset=UTF-8;pageEncoding=UTF-8")
    public @ResponseBody String listLanguages(
            @RequestParam int iDisplayStart,
            @RequestParam int iDisplayLength,
            @RequestParam String sEcho
    ) {
        final List<Lang> cmsLanguages = cmsLanguagesDAO.listAjax(iDisplayStart, iDisplayLength);
        JSONObject responseDetailsJson = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < cmsLanguages.size(); i++) {
            JSONObject formDetailsJson = new JSONObject();
            formDetailsJson.put("#", iDisplayStart + i + 1);
            formDetailsJson.put("code", cmsLanguages.get(i).getCode());
            formDetailsJson.put("name", cmsLanguages.get(i).getName());
            formDetailsJson.put("id", cmsLanguages.get(i).getId());
            jsonArray.add(formDetailsJson);
        }
        responseDetailsJson.put("sEcho", sEcho);
        final int numberOfLanguages = cmsLanguagesDAO.countForAjax();
        responseDetailsJson.put("iTotalRecords", numberOfLanguages);
        responseDetailsJson.put("iTotalDisplayRecords", numberOfLanguages);
        responseDetailsJson.put("aaData", jsonArray);
        return responseDetailsJson.toString();
    }

    @RequestMapping(value = "/CWSFE_CMS/cmsLanguagesDropList", method = RequestMethod.GET, produces = "application/json;charset=UTF-8;pageEncoding=UTF-8")
    public @ResponseBody String listCmsLanguagesForDropList(
            @RequestParam String term,
            @RequestParam Integer limit
    ) {
        final List<Lang> langs = cmsLanguagesDAO.listForDropList(term, limit);
        JSONObject responseDetailsJson = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (Lang lang : langs) {
            JSONObject formDetailsJson = new JSONObject();
            formDetailsJson.put("id", lang.getId());
            formDetailsJson.put("code", lang.getCode());
            formDetailsJson.put("name", lang.getName());
            jsonArray.add(formDetailsJson);
        }
        responseDetailsJson.put("data", jsonArray);
        return responseDetailsJson.toString();
    }

    @RequestMapping(value = "/CWSFE_CMS/addLanguage", method = RequestMethod.POST, produces = "application/json;charset=UTF-8;pageEncoding=UTF-8")
    public @ResponseBody String addLanguage(
            @ModelAttribute(value = "cmsLanguage") Lang cmsLanguage,
            BindingResult result, Locale locale
    ) {
        ValidationUtils.rejectIfEmpty(result, "code", ResourceBundle.getBundle("cwsfe_cms_i18n", locale).getString("LanguageCodeMustBeSet"));
        ValidationUtils.rejectIfEmpty(result, "name", ResourceBundle.getBundle("cwsfe_cms_i18n", locale).getString("LanguageNameMustBeSet"));
        if (Locale.forLanguageTag(cmsLanguage.getCode()).getLanguage().isEmpty()) {
            result.rejectValue("code", ResourceBundle.getBundle("cwsfe_cms_i18n", locale).getString("Language2LetterCodeIsInvalid"));
        }
        JSONObject responseDetailsJson = new JSONObject();
        if (!result.hasErrors()) {
            cmsLanguagesDAO.add(cmsLanguage);
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

    @RequestMapping(value = "/CWSFE_CMS/deleteLanguage", method = RequestMethod.POST, produces = "application/json;charset=UTF-8;pageEncoding=UTF-8")
    public @ResponseBody String deleteLanguage(
            @ModelAttribute(value = "cmsLanguage") Lang cmsLanguage,
            BindingResult result, Locale locale
    ) {
        ValidationUtils.rejectIfEmpty(result, "id", ResourceBundle.getBundle("cwsfe_cms_i18n", locale).getString("LanguageMustBeSet"));
        JSONObject responseDetailsJson = new JSONObject();
        if (!result.hasErrors()) {
            cmsLanguagesDAO.delete(cmsLanguage);
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
