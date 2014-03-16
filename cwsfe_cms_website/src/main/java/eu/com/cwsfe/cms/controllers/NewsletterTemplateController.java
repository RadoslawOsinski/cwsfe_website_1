package eu.com.cwsfe.cms.controllers;

import eu.com.cwsfe.cms.EmailValidator;
import eu.com.cwsfe.cms.dao.CmsLanguagesDAO;
import eu.com.cwsfe.cms.dao.NewsletterTemplateDAO;
import eu.com.cwsfe.cms.model.NewsletterMailAddress;
import eu.com.cwsfe.cms.model.NewsletterTemplate;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author Radoslaw Osinski
 */
@Controller
class NewsletterTemplateController {

    @Autowired
    private NewsletterTemplateDAO newsletterTemplateDAO;
    @Autowired
    private CmsLanguagesDAO cmsLanguagesDAO;
    @Autowired
    private JavaMailSender cmsMailSender;

    @RequestMapping(value = "/CWSFE_CMS/newsletterTemplates", method = RequestMethod.GET)
    public String defaultView(ModelMap model, Locale locale, HttpServletRequest httpServletRequest) {
        model.addAttribute("additionalJavaScriptCode", setAdditionalJS(httpServletRequest.getContextPath()));
        model.addAttribute("breadcrumbs", getBreadcrumbs(locale));
        return "cms/newsletterTemplates/NewsletterTemplates";
    }

    private Object setAdditionalJS(String contextPath) {
        List<String> jsUrl = new ArrayList<>(3);
        jsUrl.add(contextPath + "/resources-cwsfe-cms/js/cms/newsletterTemplates/NewsletterTemplates.js");
        return jsUrl;
    }

    private List<String> getBreadcrumbs(Locale locale) {
        List<String> breadcrumbs = new ArrayList<>(1);
        breadcrumbs.add("<a href=\"" +
                ServletUriComponentsBuilder.fromCurrentContextPath().path("/CWSFE_CMS/newsletterTemplates").build().toUriString() +
                "\" tabindex=\"-1\">" + ResourceBundle.getBundle("cwsfe_cms_i18n", locale).getString("NewsletterTemplatesManagement") + "</a>");
        return breadcrumbs;
    }

    private Object setSingleNewsletterTemplatesAdditionalJS(String contextPath) {
        List<String> jsUrl = new ArrayList<>(3);
        jsUrl.add(contextPath + "/resources-cwsfe-cms/js/cms/newsletterTemplates/SingleNewsletterTemplate.js");
        return jsUrl;
    }

    private List<String> getSingleNewsletterTemplatesBreadcrumbs(Locale locale, Long id) {
        List<String> breadcrumbs = new ArrayList<>(1);
        breadcrumbs.add("<a href=\"" +
                ServletUriComponentsBuilder.fromCurrentContextPath().path("/CWSFE_CMS/newsletterTemplates").build().toUriString() +
                "\" tabindex=\"-1\">" + ResourceBundle.getBundle("cwsfe_cms_i18n", locale).getString("NewsletterTemplatesManagement") + "</a>");
        breadcrumbs.add("<a href=\"" +
                ServletUriComponentsBuilder.fromCurrentContextPath().path("/CWSFE_CMS/newsletterTemplates/" + id).build().toUriString() +
                "\" tabindex=\"-1\">" + ResourceBundle.getBundle("cwsfe_cms_i18n", locale).getString("CurrentNewsletterTemplate") + "</a>");
        return breadcrumbs;
    }

    @RequestMapping(value = "/CWSFE_CMS/newsletterTemplatesList", method = RequestMethod.GET, produces = "application/json;charset=UTF-8;pageEncoding=UTF-8")
    public
    @ResponseBody
    String listNewsletterTemplates(
            @RequestParam int iDisplayStart,
            @RequestParam int iDisplayLength,
            @RequestParam String sEcho,
            @RequestParam(required = false) Long searchLanguageId,
            @RequestParam(required = false) String searchName
    ) {
        List<NewsletterTemplate> dbList = newsletterTemplateDAO.searchByAjax(iDisplayStart, iDisplayLength, searchName, searchLanguageId);
        Integer dbListDisplayRecordsSize = newsletterTemplateDAO.searchByAjaxCount(searchName, searchLanguageId);
        JSONObject responseDetailsJson = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < dbList.size(); i++) {
            JSONObject formDetailsJson = new JSONObject();
            formDetailsJson.put("#", iDisplayStart + i + 1);
            final NewsletterTemplate objects = dbList.get(i);
            formDetailsJson.put("language2LetterCode", cmsLanguagesDAO.getById(objects.getLanguageId()).getCode());
            formDetailsJson.put("newsletterTemplateName", objects.getName());
            formDetailsJson.put("newsletterTemplateSubject", objects.getSubject());
            formDetailsJson.put("newsletterTemplateStatus", objects.getStatus());
            formDetailsJson.put("id", objects.getId());
            jsonArray.add(formDetailsJson);
        }
        responseDetailsJson.put("sEcho", sEcho);
        responseDetailsJson.put("iTotalRecords", dbListDisplayRecordsSize);
        responseDetailsJson.put("iTotalDisplayRecords", dbListDisplayRecordsSize);
        responseDetailsJson.put("aaData", jsonArray);
        return responseDetailsJson.toString();
    }

    @RequestMapping(value = "/CWSFE_CMS/addNewsletterTemplate", method = RequestMethod.POST, produces = "application/json;charset=UTF-8;pageEncoding=UTF-8")
    public
    @ResponseBody
    String addNewsletterTemplate(
            @ModelAttribute(value = "newsletterTemplate") NewsletterTemplate newsletterTemplate,
            BindingResult result, Locale locale
    ) {
        ValidationUtils.rejectIfEmpty(result, "languageId", ResourceBundle.getBundle("cwsfe_cms_i18n", locale).getString("LanguageMustBeSet"));
        ValidationUtils.rejectIfEmpty(result, "name", ResourceBundle.getBundle("cwsfe_cms_i18n", locale).getString("NewsletterTemplateNameMustBeSet"));
        JSONObject responseDetailsJson = new JSONObject();
        if (!result.hasErrors()) {
            newsletterTemplateDAO.add(newsletterTemplate);
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

    @RequestMapping(value = "/CWSFE_CMS/deleteNewsletterTemplate", method = RequestMethod.POST, produces = "application/json;charset=UTF-8;pageEncoding=UTF-8")
    public
    @ResponseBody
    String deleteNewsletterTemplate(
            @ModelAttribute(value = "newsletterTemplate") NewsletterTemplate newsletterTemplate,
            BindingResult result, Locale locale
    ) {
        ValidationUtils.rejectIfEmpty(result, "id", ResourceBundle.getBundle("cwsfe_cms_i18n", locale).getString("NewsletterTemplateMustBeSet"));
        JSONObject responseDetailsJson = new JSONObject();
        if (!result.hasErrors()) {
            newsletterTemplateDAO.delete(newsletterTemplate);
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

    @RequestMapping(value = "/CWSFE_CMS/unDeleteNewsletterTemplate", method = RequestMethod.POST, produces = "application/json;charset=UTF-8;pageEncoding=UTF-8")
    public
    @ResponseBody
    String unDeleteNewsletterTemplate(
            @ModelAttribute(value = "newsletterTemplate") NewsletterTemplate newsletterTemplate,
            BindingResult result, Locale locale
    ) {
        ValidationUtils.rejectIfEmpty(result, "id", ResourceBundle.getBundle("cwsfe_cms_i18n", locale).getString("NewsletterTemplateMustBeSet"));
        JSONObject responseDetailsJson = new JSONObject();
        if (!result.hasErrors()) {
            newsletterTemplateDAO.undelete(newsletterTemplate);
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

    @RequestMapping(value = "/CWSFE_CMS/newsletterTemplates/{id}", method = RequestMethod.GET)
    public String browseNewsletterTemplate(ModelMap model, Locale locale, @PathVariable("id") Long id, HttpServletRequest httpServletRequest) {
        model.addAttribute("additionalJavaScriptCode", setSingleNewsletterTemplatesAdditionalJS(httpServletRequest.getContextPath()));
        model.addAttribute("breadcrumbs", getSingleNewsletterTemplatesBreadcrumbs(locale, id));
        NewsletterTemplate newsletterTemplate = newsletterTemplateDAO.get(id);
        model.addAttribute("newsletterTemplate", newsletterTemplate);
        model.addAttribute("newsletterTemplateLanguageCode", cmsLanguagesDAO.getById(newsletterTemplate.getLanguageId()).getCode());
        return "cms/newsletterTemplates/SingleNewsletterTemplate";
    }

    @RequestMapping(value = "/CWSFE_CMS/newsletterTemplates/updateNewsletterTemplate", method = RequestMethod.POST)
    public String updateNewsletterTemplate(
            @ModelAttribute(value = "newsletterTemplate") NewsletterTemplate newsletterTemplate,
            BindingResult result, ModelMap model, Locale locale, HttpServletRequest httpServletRequest
    ) {
        ValidationUtils.rejectIfEmpty(result, "id", ResourceBundle.getBundle("cwsfe_cms_i18n", locale).getString("NewsletterTemplateMustBeSet"));
        ValidationUtils.rejectIfEmpty(result, "languageId", ResourceBundle.getBundle("cwsfe_cms_i18n", locale).getString("LanguageMustBeSet"));
        ValidationUtils.rejectIfEmpty(result, "name", ResourceBundle.getBundle("cwsfe_cms_i18n", locale).getString("NewsletterTemplateNameMustBeSet"));
        ValidationUtils.rejectIfEmpty(result, "subject", ResourceBundle.getBundle("cwsfe_cms_i18n", locale).getString("SubjectMustBeSet"));
        ValidationUtils.rejectIfEmpty(result, "content", ResourceBundle.getBundle("cwsfe_cms_i18n", locale).getString("ContentMustBeSet"));
        if (!result.hasErrors()) {
            newsletterTemplate.setContent(newsletterTemplate.getContent().trim());
            newsletterTemplateDAO.update(newsletterTemplate);
            model.addAttribute("updateSuccessfull", ResourceBundle.getBundle("cwsfe_cms_i18n", locale).getString("Saved"));
        } else {
            String errors = "";
            for (int i = 0; i < result.getAllErrors().size(); i++) {
                errors += result.getAllErrors().get(i).getCode() + "<br/>";
            }
            model.addAttribute("updateErrors", errors);
        }
        return browseNewsletterTemplate(model, locale, newsletterTemplate.getId(), httpServletRequest);
    }

    @RequestMapping(value = "/CWSFE_CMS/newsletterTemplates/newsletterTemplateTestSend", method = RequestMethod.POST, produces = "application/json;charset=UTF-8;pageEncoding=UTF-8")
    public
    @ResponseBody
    String newsletterTemplateTestSend(
            @ModelAttribute(value = "newsletterTemplate") NewsletterTemplate newsletterTemplate,
            @ModelAttribute(value = "newsletterMailAddress") NewsletterMailAddress newsletterMailAddress,
            BindingResult result, Locale locale
    ) {
        ValidationUtils.rejectIfEmpty(result, "id", ResourceBundle.getBundle("cwsfe_cms_i18n", locale).getString("NewsletterTemplateMustBeSet"));
        ValidationUtils.rejectIfEmpty(result, "email", ResourceBundle.getBundle("cwsfe_cms_i18n", locale).getString("EmailIsInvalid"));
        if (!EmailValidator.isValidEmailAddress(newsletterMailAddress.getEmail())) {
            result.rejectValue("email", ResourceBundle.getBundle("cwsfe_cms_i18n", locale).getString("EmailIsInvalid"));
        }
        newsletterTemplate = newsletterTemplateDAO.get(newsletterTemplate.getId());
        JSONObject responseDetailsJson = new JSONObject();
        if (!result.hasErrors()) {
            sendTestTemplateEmail(newsletterTemplate, newsletterMailAddress);
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

    private void sendTestTemplateEmail(NewsletterTemplate newsletterTemplate, NewsletterMailAddress newsletterMailAddress) {
        MimeMessage mimeMessage = cmsMailSender.createMimeMessage();
        MimeMessageHelper helper;
        try {
            helper = new MimeMessageHelper(mimeMessage, false, "utf-8");
            helper.setTo(newsletterMailAddress.getEmail());
            mimeMessage.setContent(newsletterTemplate.getContent(), "text/html");
            helper.setSubject(newsletterTemplate.getSubject());
            helper.setReplyTo("info@cwsfe.pl");
        } catch (MessagingException ignored) {
        }
        cmsMailSender.send(mimeMessage);
    }

}
