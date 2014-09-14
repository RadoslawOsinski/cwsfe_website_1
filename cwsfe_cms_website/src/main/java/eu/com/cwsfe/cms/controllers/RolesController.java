package eu.com.cwsfe.cms.controllers;

import eu.com.cwsfe.cms.dao.CmsRolesDAO;
import eu.com.cwsfe.cms.model.CmsRole;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
public class RolesController extends JsonController {

    @Autowired
    private CmsRolesDAO cmsRolesDAO;

    @RequestMapping(value = "/CWSFE_CMS/roles", method = RequestMethod.GET)
    public String defaultView(ModelMap model, Locale locale, HttpServletRequest httpServletRequest) {
        model.addAttribute("additionalJavaScriptCode", setAdditionalJS(httpServletRequest.getContextPath()));
        model.addAttribute("breadcrumbs", getBreadcrumbs(locale));
        return "cms/roles/Roles";
    }

    private Object setAdditionalJS(String contextPath) {
        List<String> jsUrl = new ArrayList<>(3);
        jsUrl.add(contextPath + "/resources-cwsfe-cms/js/cms/roles/Roles.js");
        return jsUrl;
    }

    private List<String> getBreadcrumbs(Locale locale) {
        List<String> breadcrumbs = new ArrayList<>(1);
        breadcrumbs.add("<a href=\"" +
                ServletUriComponentsBuilder.fromCurrentContextPath().path("/CWSFE_CMS/roles").build().toUriString() +
                "\" tabindex=\"-1\">" + ResourceBundle.getBundle(CWSFE_CMS_RESOURCE_BUNDLE_PATH, locale).getString("RolesManagement") + "</a>");
        return breadcrumbs;
    }

    @RequestMapping(value = "/CWSFE_CMS/rolesList", method = RequestMethod.GET, produces = "application/json;charset=UTF-8;pageEncoding=UTF-8")
    public @ResponseBody String listRoles(
            @RequestParam int iDisplayStart,
            @RequestParam int iDisplayLength,
            @RequestParam String sEcho
    ) {
        final List<CmsRole> cmsRoles = cmsRolesDAO.listAjax(iDisplayStart, iDisplayLength);
        JSONObject responseDetailsJson = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < cmsRoles.size(); i++) {
            JSONObject formDetailsJson = new JSONObject();
            formDetailsJson.put("#", iDisplayStart + i + 1);
            formDetailsJson.put("roleCode", cmsRoles.get(i).getRoleCode());
            formDetailsJson.put("roleName", cmsRoles.get(i).getRoleName());
            jsonArray.add(formDetailsJson);
        }
        responseDetailsJson.put("sEcho", sEcho);
        final int numberOfRoles = cmsRolesDAO.countForAjax();
        responseDetailsJson.put("iTotalRecords", numberOfRoles);
        responseDetailsJson.put("iTotalDisplayRecords", numberOfRoles);
        responseDetailsJson.put("aaData", jsonArray);
        return responseDetailsJson.toString();
    }

    @RequestMapping(value = "/CWSFE_CMS/rolesDropList", method = RequestMethod.GET, produces = "application/json;charset=UTF-8;pageEncoding=UTF-8")
    public @ResponseBody String listRolesForDropList(
            @RequestParam String term,
            @RequestParam Integer limit
    ) {
        final List<CmsRole> cmsRoles = cmsRolesDAO.listRolesForDropList(term, limit);
        JSONObject responseDetailsJson = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (CmsRole cmsRole : cmsRoles) {
            JSONObject formDetailsJson = new JSONObject();
            formDetailsJson.put("id", cmsRole.getId());
            formDetailsJson.put("roleName", cmsRole.getRoleName());
            jsonArray.add(formDetailsJson);
        }
        responseDetailsJson.put("data", jsonArray);
        return responseDetailsJson.toString();
    }

}
