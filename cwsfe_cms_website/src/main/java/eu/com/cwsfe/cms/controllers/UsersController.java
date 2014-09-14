package eu.com.cwsfe.cms.controllers;

import eu.com.cwsfe.cms.dao.CmsRolesDAO;
import eu.com.cwsfe.cms.dao.CmsUserRolesDAO;
import eu.com.cwsfe.cms.dao.CmsUsersDAO;
import eu.com.cwsfe.cms.model.CmsRole;
import eu.com.cwsfe.cms.model.CmsUser;
import eu.com.cwsfe.cms.model.CmsUserRole;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author Radoslaw Osinski
 */
@Controller
class UsersController implements JsonController {

    @Autowired
    private CmsUsersDAO cmsUsersDAO;
    @Autowired
    private CmsRolesDAO cmsRolesDAO;
    @Autowired
    private CmsUserRolesDAO cmsUserRolesDAO;

    @RequestMapping(value = "/CWSFE_CMS/users", method = RequestMethod.GET)
    public String defaultView(ModelMap model, Locale locale, HttpServletRequest httpServletRequest) {
        model.addAttribute("additionalJavaScriptCode", setAdditionalJS(httpServletRequest.getContextPath()));
        model.addAttribute("breadcrumbs", getBreadcrumbs(locale));
        return "cms/users/Users";
    }

    private Object setAdditionalJS(String contextPath) {
        List<String> jsUrl = new ArrayList<>(3);
        jsUrl.add(contextPath + "/resources-cwsfe-cms/js/cms/users/Users.js");
        return jsUrl;
    }

    private List<String> getBreadcrumbs(Locale locale) {
        List<String> breadcrumbs = new ArrayList<>(1);
        breadcrumbs.add("<a href=\"" +
                ServletUriComponentsBuilder.fromCurrentContextPath().path("/CWSFE_CMS/users").build().toUriString() +
                "\" tabindex=\"-1\">" + ResourceBundle.getBundle(CWSFE_CMS_RESOURCE_BUNDLE_PATH, locale).getString("UsersManagement") + "</a>");
        return breadcrumbs;
    }

    private Object setSingleUserAdditionalJS(String contextPath) {
        List<String> jsUrl = new ArrayList<>(3);
        jsUrl.add(contextPath + "/resources-cwsfe-cms/js/cms/users/SingleUser.js");
        return jsUrl;
    }

    private List<String> getSingleUserBreadcrumbs(Locale locale, Long id) {
        List<String> breadcrumbs = new ArrayList<>(1);
        breadcrumbs.add("<a href=\"" +
                ServletUriComponentsBuilder.fromCurrentContextPath().path("/CWSFE_CMS/users").build().toUriString() +
                "\" tabindex=\"-1\">" + ResourceBundle.getBundle(CWSFE_CMS_RESOURCE_BUNDLE_PATH, locale).getString("UsersManagement") + "</a>");
        breadcrumbs.add("<a href=\"" +
                ServletUriComponentsBuilder.fromCurrentContextPath().path("/CWSFE_CMS/users/" + id).build().toUriString() +
                "\" tabindex=\"-1\">" + ResourceBundle.getBundle(CWSFE_CMS_RESOURCE_BUNDLE_PATH, locale).getString("SelectedUser") + "</a>");
        return breadcrumbs;
    }

    @RequestMapping(value = "/CWSFE_CMS/usersList", method = RequestMethod.GET, produces = "application/json;charset=UTF-8;pageEncoding=UTF-8")
    public @ResponseBody String listUsers(
            @RequestParam int iDisplayStart,
            @RequestParam int iDisplayLength,
            @RequestParam String sEcho
    ) {
        final List<CmsUser> cmsUsers = cmsUsersDAO.listAjax(iDisplayStart, iDisplayLength);
        JSONObject responseDetailsJson = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < cmsUsers.size(); i++) {
            JSONObject formDetailsJson = new JSONObject();
            formDetailsJson.put("#", iDisplayStart + i + 1);
            formDetailsJson.put("userName", cmsUsers.get(i).getUsername());
            formDetailsJson.put(JSON_STATUS, cmsUsers.get(i).getStatus());
            formDetailsJson.put("id", cmsUsers.get(i).getId());
            jsonArray.add(formDetailsJson);
        }
        responseDetailsJson.put("sEcho", sEcho);
        final int numberOfUsers = cmsUsersDAO.countForAjax();
        responseDetailsJson.put("iTotalRecords", numberOfUsers);
        responseDetailsJson.put("iTotalDisplayRecords", numberOfUsers);
        responseDetailsJson.put("aaData", jsonArray);
        return responseDetailsJson.toString();
    }

    @RequestMapping(value = "/CWSFE_CMS/usersDropList", method = RequestMethod.GET, produces = "application/json;charset=UTF-8;pageEncoding=UTF-8")
    public @ResponseBody String listUsersForDropList(
            @RequestParam String term,
            @RequestParam Integer limit
    ) {
        final List<CmsUser> cmsUsers = cmsUsersDAO.listUsersForDropList(term, limit);
        JSONObject responseDetailsJson = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (CmsUser cmsUser : cmsUsers) {
            JSONObject formDetailsJson = new JSONObject();
            formDetailsJson.put("id", cmsUser.getId());
            formDetailsJson.put("userName", cmsUser.getUsername());
            jsonArray.add(formDetailsJson);
        }
        responseDetailsJson.put("data", jsonArray);
        return responseDetailsJson.toString();
    }

    @RequestMapping(value = "/CWSFE_CMS/addUser", method = RequestMethod.POST, produces = "application/json;charset=UTF-8;pageEncoding=UTF-8")
    public @ResponseBody String addUser(
            @ModelAttribute(value = "cmsUser") CmsUser cmsUser,
            BindingResult result, Locale locale
    ) {
        ValidationUtils.rejectIfEmpty(result, "username", ResourceBundle.getBundle(CWSFE_CMS_RESOURCE_BUNDLE_PATH, locale).getString("FirstNameMustBeSet"));
        ValidationUtils.rejectIfEmpty(result, "passwordHash", ResourceBundle.getBundle(CWSFE_CMS_RESOURCE_BUNDLE_PATH, locale).getString("PasswordMustBeSet"));
        JSONObject responseDetailsJson = new JSONObject();
        if (!result.hasErrors()) {
            cmsUser.setPasswordHash(BCrypt.hashpw(cmsUser.getPasswordHash(), BCrypt.gensalt(13)));
            cmsUsersDAO.add(cmsUser);
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

    @RequestMapping(value = "/CWSFE_CMS/deleteUser", method = RequestMethod.POST, produces = "application/json;charset=UTF-8;pageEncoding=UTF-8")
    public @ResponseBody String deleteUser(
            @ModelAttribute(value = "cmsUser") CmsUser cmsUser,
            BindingResult result, Locale locale
    ) {
        ValidationUtils.rejectIfEmpty(result, "id", ResourceBundle.getBundle(CWSFE_CMS_RESOURCE_BUNDLE_PATH, locale).getString("UserMustBeSet"));
        JSONObject responseDetailsJson = new JSONObject();
        if (!result.hasErrors()) {
            cmsUsersDAO.delete(cmsUser);
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

    @RequestMapping(value = "/CWSFE_CMS/lockUser", method = RequestMethod.POST, produces = "application/json;charset=UTF-8;pageEncoding=UTF-8")
    public @ResponseBody String lockUser(
            @ModelAttribute(value = "cmsUser") CmsUser cmsUser,
            BindingResult result, Locale locale
    ) {
        ValidationUtils.rejectIfEmpty(result, "id", ResourceBundle.getBundle(CWSFE_CMS_RESOURCE_BUNDLE_PATH, locale).getString("UserMustBeSet"));
        JSONObject responseDetailsJson = new JSONObject();
        if (!result.hasErrors()) {
            cmsUsersDAO.lock(cmsUser);
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

    @RequestMapping(value = "/CWSFE_CMS/unlockUser", method = RequestMethod.POST, produces = "application/json;charset=UTF-8;pageEncoding=UTF-8")
    public @ResponseBody String unlockUser(
            @ModelAttribute(value = "cmsUser") CmsUser cmsUser,
            BindingResult result, Locale locale
    ) {
        ValidationUtils.rejectIfEmpty(result, "id", ResourceBundle.getBundle(CWSFE_CMS_RESOURCE_BUNDLE_PATH, locale).getString("UserMustBeSet"));
        JSONObject responseDetailsJson = new JSONObject();
        if (!result.hasErrors()) {
            cmsUsersDAO.unlock(cmsUser);
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

    @RequestMapping(value = "/CWSFE_CMS/users/{id}", method = RequestMethod.GET)
    public String browseUser(ModelMap model, Locale locale, @PathVariable("id") Long id, HttpServletRequest httpServletRequest) {
        model.addAttribute("additionalJavaScriptCode", setSingleUserAdditionalJS(httpServletRequest.getContextPath()));
        model.addAttribute("breadcrumbs", getSingleUserBreadcrumbs(locale, id));
        final CmsUser cmsUser = cmsUsersDAO.get(id);
        model.addAttribute("cmsUser", cmsUser);
        cmsUser.setUserRoles(cmsRolesDAO.listUserRoles(cmsUser.getId()));
        List<Long> userSelectedRoles = new ArrayList<>(5);
        for (CmsRole role : cmsUser.getUserRoles()) {
            userSelectedRoles.add(role.getId());
        }
        model.addAttribute("userSelectedRoles", userSelectedRoles);
        model.addAttribute("cmsRoles", cmsRolesDAO.list());
        return "cms/users/SingleUser";
    }

    @RequestMapping(value = "/CWSFE_CMS/userRolesUpdate", method = RequestMethod.POST)
    public ModelAndView userRolesUpdate(
            @ModelAttribute(value = "cmsUser") CmsUser cmsUser,
            ModelMap model, Locale locale,
            WebRequest webRequest, HttpServletRequest httpServletRequest
    ) {
        String[] userRolesStrings = webRequest.getParameterValues("cmsUserRoles");
        //todo add transactions!
        cmsUserRolesDAO.deleteForUser(cmsUser.getId());
        if (userRolesStrings != null) {
            for (String roleIdString : userRolesStrings) {
                cmsUserRolesDAO.add(new CmsUserRole(
                        cmsUser.getId(),
                        Long.parseLong(roleIdString)
                ));
            }
        }
//        /////////// end transaction
        browseUser(model, locale, cmsUser.getId(), httpServletRequest);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setView(new RedirectView("/CWSFE_CMS/users/" + cmsUser.getId(), true, false, false));
        return modelAndView;
    }

    @RequestMapping(value = "/CWSFE_CMS/users/updateUserBasicInfo", method = RequestMethod.POST, produces = "application/json;charset=UTF-8;pageEncoding=UTF-8")
    public @ResponseBody String updateUserBasicInfo(
            @ModelAttribute(value = "cmsUser") CmsUser cmsUser,
            BindingResult result, Locale locale
    ) {
        ValidationUtils.rejectIfEmpty(result, "id", ResourceBundle.getBundle(CWSFE_CMS_RESOURCE_BUNDLE_PATH, locale).getString("UserMustBeSet"));
        ValidationUtils.rejectIfEmpty(result, "username", ResourceBundle.getBundle(CWSFE_CMS_RESOURCE_BUNDLE_PATH, locale).getString("UsernameMustBeSet"));
        ValidationUtils.rejectIfEmpty(result, JSON_STATUS, ResourceBundle.getBundle(CWSFE_CMS_RESOURCE_BUNDLE_PATH, locale).getString("StatusMustBeSet"));
        JSONObject responseDetailsJson = new JSONObject();
        if (!result.hasErrors()) {
            cmsUsersDAO.updatePostBasicInfo(cmsUser);
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
