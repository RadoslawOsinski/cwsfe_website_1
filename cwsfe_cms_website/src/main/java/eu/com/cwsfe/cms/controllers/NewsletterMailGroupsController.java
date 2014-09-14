package eu.com.cwsfe.cms.controllers;

import eu.com.cwsfe.cms.EmailValidator;
import eu.com.cwsfe.cms.UUIDGenerator;
import eu.com.cwsfe.cms.dao.CmsLanguagesDAO;
import eu.com.cwsfe.cms.dao.NewsletterMailAddressDAO;
import eu.com.cwsfe.cms.dao.NewsletterMailGroupDAO;
import eu.com.cwsfe.cms.model.NewsletterMailAddress;
import eu.com.cwsfe.cms.model.NewsletterMailGroup;
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
class NewsletterMailGroupsController implements JsonController {

    @Autowired
    private NewsletterMailGroupDAO newsletterMailGroupDAO;
    @Autowired
    private NewsletterMailAddressDAO newsletterMailAddressDAO;
    @Autowired
    private CmsLanguagesDAO cmsLanguagesDAO;

    @RequestMapping(value = "/CWSFE_CMS/newsletterMailGroups", method = RequestMethod.GET)
    public String defaultView(ModelMap model, Locale locale, HttpServletRequest httpServletRequest) {
        model.addAttribute("additionalJavaScriptCode", setAdditionalJS(httpServletRequest.getContextPath()));
        model.addAttribute("breadcrumbs", getBreadcrumbs(locale));
        return "cms/newsletterMailGroups/NewsletterMailGroups";
    }

    private Object setAdditionalJS(String contextPath) {
        List<String> jsUrl = new ArrayList<>(3);
        jsUrl.add(contextPath + "/resources-cwsfe-cms/js/cms/newsletterMailGroups/NewsletterMailGroups.js");
        return jsUrl;
    }

    private List<String> getBreadcrumbs(Locale locale) {
        //todo pozmieniać fromCurrentContextPath() aby działało na produkcji
        List<String> breadcrumbs = new ArrayList<>(1);
        breadcrumbs.add("<a href=\"" +
                ServletUriComponentsBuilder.fromCurrentContextPath().path("/CWSFE_CMS/newsletterMailGroups").build().toUriString() +
                "\" tabindex=\"-1\">" + ResourceBundle.getBundle(CWSFE_CMS_RESOURCE_BUNDLE_PATH, locale).getString("NewsletterMailGroupsManagement") + "</a>");
        return breadcrumbs;
    }

    private Object setSingleNewsletterMailGroupsAdditionalJS(String contextPath) {
        List<String> jsUrl = new ArrayList<>(3);
        jsUrl.add(contextPath + "/resources-cwsfe-cms/js/cms/newsletterMailGroups/SingleNewsletterMailGroup.js");
        return jsUrl;
    }

    private List<String> getSingleNewsletterMailGroupsBreadcrumbs(Locale locale, Long id) {
        List<String> breadcrumbs = new ArrayList<>(1);
        breadcrumbs.add("<a href=\"" +
                ServletUriComponentsBuilder.fromCurrentContextPath().path("/CWSFE_CMS/newsletterMailGroups").build().toUriString() +
                "\" tabindex=\"-1\">" + ResourceBundle.getBundle(CWSFE_CMS_RESOURCE_BUNDLE_PATH, locale).getString("NewsletterMailGroupsManagement") + "</a>");
        breadcrumbs.add("<a href=\"" +
                ServletUriComponentsBuilder.fromCurrentContextPath().path("/CWSFE_CMS/newsletterMailGroups/" + id).build().toUriString() +
                "\" tabindex=\"-1\">" + ResourceBundle.getBundle(CWSFE_CMS_RESOURCE_BUNDLE_PATH, locale).getString("CurrentNewsletterMailGroup") + "</a>");
        return breadcrumbs;
    }

    @RequestMapping(value = "/CWSFE_CMS/newsletterMailGroupsDropList", method = RequestMethod.GET, produces = "application/json;charset=UTF-8;pageEncoding=UTF-8")
    public @ResponseBody String listNewsletterMailGroupsForDropList(
            @RequestParam String term,
            @RequestParam Integer limit
    ) {
        final List<NewsletterMailGroup> results = newsletterMailGroupDAO.listNewsletterMailGroupsForDropList(term, limit);
        JSONObject responseDetailsJson = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (NewsletterMailGroup newsletterMailGroup : results) {
            JSONObject formDetailsJson = new JSONObject();
            formDetailsJson.put("id", newsletterMailGroup.getId());
            formDetailsJson.put("newsletterMailGroupName", newsletterMailGroup.getName());
            jsonArray.add(formDetailsJson);
        }
        responseDetailsJson.put("data", jsonArray);
        return responseDetailsJson.toString();
    }

    @RequestMapping(value = "/CWSFE_CMS/newsletterMailGroupsList", method = RequestMethod.GET, produces = "application/json;charset=UTF-8;pageEncoding=UTF-8")
    public
    @ResponseBody
    String listNewsletterMailGroups(
            @RequestParam int iDisplayStart,
            @RequestParam int iDisplayLength,
            @RequestParam String sEcho,
            @RequestParam(required = false) Long searchLanguageId,
            @RequestParam(required = false) String searchName
    ) {
        List<NewsletterMailGroup> dbList = newsletterMailGroupDAO.searchByAjax(iDisplayStart, iDisplayLength, searchName, searchLanguageId);
        Integer dbListDisplayRecordsSize = newsletterMailGroupDAO.searchByAjaxCount(searchName, searchLanguageId);
        JSONObject responseDetailsJson = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < dbList.size(); i++) {
            JSONObject formDetailsJson = new JSONObject();
            formDetailsJson.put("#", iDisplayStart + i + 1);
            final NewsletterMailGroup objects = dbList.get(i);
            formDetailsJson.put("language2LetterCode", cmsLanguagesDAO.getById(objects.getLanguageId()).getCode());
            formDetailsJson.put("newsletterMailGroupName", objects.getName());
            formDetailsJson.put("id", objects.getId());
            jsonArray.add(formDetailsJson);
        }
        responseDetailsJson.put("sEcho", sEcho);
        responseDetailsJson.put("iTotalRecords", dbListDisplayRecordsSize);
        responseDetailsJson.put("iTotalDisplayRecords", dbListDisplayRecordsSize);
        responseDetailsJson.put("aaData", jsonArray);
        return responseDetailsJson.toString();
    }

    @RequestMapping(value = "/CWSFE_CMS/addNewsletterMailGroup", method = RequestMethod.POST, produces = "application/json;charset=UTF-8;pageEncoding=UTF-8")
    public
    @ResponseBody
    String addNewsletterMailGroup(
            @ModelAttribute(value = "newsletterMailGroup") NewsletterMailGroup newsletterMailGroup,
            BindingResult result, Locale locale
    ) {
        ValidationUtils.rejectIfEmpty(result, "name", ResourceBundle.getBundle(CWSFE_CMS_RESOURCE_BUNDLE_PATH, locale).getString("NewsletterMailGroupMustBeSet"));
        ValidationUtils.rejectIfEmpty(result, "languageId", ResourceBundle.getBundle(CWSFE_CMS_RESOURCE_BUNDLE_PATH, locale).getString("LanguageMustBeSet"));
        JSONObject responseDetailsJson = new JSONObject();
        if (!result.hasErrors()) {
            newsletterMailGroupDAO.add(newsletterMailGroup);
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

    @RequestMapping(value = "/CWSFE_CMS/deleteNewsletterMailGroup", method = RequestMethod.POST, produces = "application/json;charset=UTF-8;pageEncoding=UTF-8")
    public
    @ResponseBody
    String deleteNewsletterMailGroup(
            @ModelAttribute(value = "newsletterMailGroup") NewsletterMailGroup newsletterMailGroup,
            BindingResult result, Locale locale
    ) {
        ValidationUtils.rejectIfEmpty(result, "id", ResourceBundle.getBundle(CWSFE_CMS_RESOURCE_BUNDLE_PATH, locale).getString("NewsletterMailGroupMustBeSet"));
        JSONObject responseDetailsJson = new JSONObject();
        if (!result.hasErrors()) {
            newsletterMailGroupDAO.delete(newsletterMailGroup);
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

    @RequestMapping(value = "/CWSFE_CMS/newsletterMailGroups/{id}", method = RequestMethod.GET)
    public String browseNewsletterMailGroup(ModelMap model, Locale locale, @PathVariable("id") Long id, HttpServletRequest httpServletRequest) {
        model.addAttribute("additionalJavaScriptCode", setSingleNewsletterMailGroupsAdditionalJS(httpServletRequest.getContextPath()));
        model.addAttribute("breadcrumbs", getSingleNewsletterMailGroupsBreadcrumbs(locale, id));
        NewsletterMailGroup newsletterMailGroup = newsletterMailGroupDAO.get(id);
        model.addAttribute("newsletterMailGroup", newsletterMailGroup);
        model.addAttribute("newsletterMailGroupLanguageCode", cmsLanguagesDAO.getById(newsletterMailGroup.getLanguageId()).getCode());
        return "cms/newsletterMailGroups/SingleNewsletterMailGroup";
    }

    @RequestMapping(value = "/CWSFE_CMS/newsletterMailGroups/newsletterMailAddressesList", method = RequestMethod.GET, produces = "application/json;charset=UTF-8;pageEncoding=UTF-8")
    public
    @ResponseBody
    String newsletterMailAddressesList(
            @RequestParam int iDisplayStart,
            @RequestParam int iDisplayLength,
            @RequestParam String sEcho,
            @RequestParam(required = false) String searchMail,
            @RequestParam(required = false) Long mailGroupId
    ) {
        List<NewsletterMailAddress> dbList = newsletterMailAddressDAO.searchByAjax(iDisplayStart, iDisplayLength, searchMail, mailGroupId);
        Integer dbListDisplayRecordsSize = newsletterMailAddressDAO.searchByAjaxCount(searchMail, mailGroupId);
        JSONObject responseDetailsJson = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < dbList.size(); i++) {
            JSONObject formDetailsJson = new JSONObject();
            formDetailsJson.put("#", iDisplayStart + i + 1);
            final NewsletterMailAddress objects = dbList.get(i);
            formDetailsJson.put("email", objects.getEmail());
            formDetailsJson.put("status", objects.getStatus());
            formDetailsJson.put("id", objects.getId());
            jsonArray.add(formDetailsJson);
        }
        responseDetailsJson.put("sEcho", sEcho);
        responseDetailsJson.put("iTotalRecords", dbListDisplayRecordsSize);
        responseDetailsJson.put("iTotalDisplayRecords", dbListDisplayRecordsSize);
        responseDetailsJson.put("aaData", jsonArray);
        return responseDetailsJson.toString();
    }

    @RequestMapping(value = "/CWSFE_CMS/newsletterMailGroups/updateNewsletterMailGroup", method = RequestMethod.POST, produces = "application/json;charset=UTF-8;pageEncoding=UTF-8")
    public
    @ResponseBody
    String updateNewsletterMailGroup(
            @ModelAttribute(value = "newsletterMailGroup") NewsletterMailGroup newsletterMailGroup,
            BindingResult result, Locale locale
    ) {
        ValidationUtils.rejectIfEmpty(result, "id", ResourceBundle.getBundle(CWSFE_CMS_RESOURCE_BUNDLE_PATH, locale).getString("NewsletterMailGroupMustBeSet"));
        ValidationUtils.rejectIfEmpty(result, "name", ResourceBundle.getBundle(CWSFE_CMS_RESOURCE_BUNDLE_PATH, locale).getString("NewsletterMailGroupNameMustBeSet"));
        ValidationUtils.rejectIfEmpty(result, "languageId", ResourceBundle.getBundle(CWSFE_CMS_RESOURCE_BUNDLE_PATH, locale).getString("LanguageMustBeSet"));
        JSONObject responseDetailsJson = new JSONObject();
        if (!result.hasErrors()) {
            newsletterMailGroupDAO.update(newsletterMailGroup);
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

    @RequestMapping(value = "/CWSFE_CMS/newsletterMailGroups/addNewsletterMailAddresses", method = RequestMethod.POST, produces = "application/json;charset=UTF-8;pageEncoding=UTF-8")
    public
    @ResponseBody
    String addNewsletterMailAddresses(
            @ModelAttribute(value = "newsletterMailAddress") NewsletterMailAddress newsletterMailAddress,
            BindingResult result, Locale locale
    ) {
        ValidationUtils.rejectIfEmpty(result, "mailGroupId", ResourceBundle.getBundle(CWSFE_CMS_RESOURCE_BUNDLE_PATH, locale).getString("NewsletterMailAddressGroupMustBeSet"));
        ValidationUtils.rejectIfEmpty(result, "email", ResourceBundle.getBundle(CWSFE_CMS_RESOURCE_BUNDLE_PATH, locale).getString("NewsletterMailAddressMustBeSet"));
        if (!EmailValidator.isValidEmailAddress(newsletterMailAddress.getEmail())) {
            result.rejectValue("email", ResourceBundle.getBundle(CWSFE_CMS_RESOURCE_BUNDLE_PATH, locale).getString("EmailIsInvalid"));
        } else {
            NewsletterMailAddress existingMailAddress = newsletterMailAddressDAO.getByEmailAndMailGroup(newsletterMailAddress.getEmail(), newsletterMailAddress.getMailGroupId());
            if (existingMailAddress != null) {
                result.rejectValue("email", ResourceBundle.getBundle(CWSFE_CMS_RESOURCE_BUNDLE_PATH, locale).getString("EmailAlreadyAdded"));
            }
        }
        JSONObject responseDetailsJson = new JSONObject();
        if (!result.hasErrors()) {
            newsletterMailAddress.setConfirmString(UUIDGenerator.getRandomUniqueID());
            while (newsletterMailAddressDAO.getByConfirmString(newsletterMailAddress.getConfirmString()) != null) {
                newsletterMailAddress.setConfirmString(UUIDGenerator.getRandomUniqueID());
            }
            newsletterMailAddress.setUnSubscribeString(UUIDGenerator.getRandomUniqueID());
            while (newsletterMailAddressDAO.getByUnSubscribeString(newsletterMailAddress.getUnSubscribeString()) != null) {
                newsletterMailAddress.setUnSubscribeString(UUIDGenerator.getRandomUniqueID());
            }
            newsletterMailAddressDAO.add(newsletterMailAddress);
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

    @RequestMapping(value = "/CWSFE_CMS/newsletterMailGroups/deleteNewsletterMailAddress", method = RequestMethod.POST, produces = "application/json;charset=UTF-8;pageEncoding=UTF-8")
    public
    @ResponseBody
    String deleteNewsletterMailAddress(
            @ModelAttribute(value = "newsletterMailAddress") NewsletterMailAddress newsletterMailAddress,
            BindingResult result, Locale locale
    ) {
        ValidationUtils.rejectIfEmpty(result, "id", ResourceBundle.getBundle(CWSFE_CMS_RESOURCE_BUNDLE_PATH, locale).getString("NewsletterMailAddressMustBeSet"));
        JSONObject responseDetailsJson = new JSONObject();
        if (!result.hasErrors()) {
            newsletterMailAddressDAO.delete(newsletterMailAddress);
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

    @RequestMapping(value = "/CWSFE_CMS/newsletterMailGroups/activateNewsletterMailAddress", method = RequestMethod.POST, produces = "application/json;charset=UTF-8;pageEncoding=UTF-8")
    public
    @ResponseBody
    String activateNewsletterMailAddress(
            @ModelAttribute(value = "newsletterMailAddress") NewsletterMailAddress newsletterMailAddress,
            BindingResult result, Locale locale
    ) {
        ValidationUtils.rejectIfEmpty(result, "id", ResourceBundle.getBundle(CWSFE_CMS_RESOURCE_BUNDLE_PATH, locale).getString("NewsletterMailAddressMustBeSet"));
        JSONObject responseDetailsJson = new JSONObject();
        if (!result.hasErrors()) {
            newsletterMailAddressDAO.activate(newsletterMailAddress);
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

    @RequestMapping(value = "/CWSFE_CMS/newsletterMailGroups/deactivateNewsletterMailAddress", method = RequestMethod.POST, produces = "application/json;charset=UTF-8;pageEncoding=UTF-8")
    public
    @ResponseBody
    String deactivateNewsletterMailAddress(
            @ModelAttribute(value = "newsletterMailAddress") NewsletterMailAddress newsletterMailAddress,
            BindingResult result, Locale locale
    ) {
        ValidationUtils.rejectIfEmpty(result, "id", ResourceBundle.getBundle(CWSFE_CMS_RESOURCE_BUNDLE_PATH, locale).getString("NewsletterMailAddressMustBeSet"));
        JSONObject responseDetailsJson = new JSONObject();
        if (!result.hasErrors()) {
            newsletterMailAddressDAO.deactivate(newsletterMailAddress);
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
