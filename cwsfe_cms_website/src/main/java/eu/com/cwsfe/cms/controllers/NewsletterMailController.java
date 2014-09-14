package eu.com.cwsfe.cms.controllers;

import eu.com.cwsfe.cms.EmailValidator;
import eu.com.cwsfe.cms.dao.NewsletterMailAddressDAO;
import eu.com.cwsfe.cms.dao.NewsletterMailDAO;
import eu.com.cwsfe.cms.dao.NewsletterMailGroupDAO;
import eu.com.cwsfe.cms.model.NewsletterMail;
import eu.com.cwsfe.cms.model.NewsletterMailAddress;
import eu.com.cwsfe.cms.model.NewsletterMailGroup;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
public class NewsletterMailController extends JsonController {

    private static final Logger LOGGER = LogManager.getLogger(NewsletterMailController.class);

    @Autowired
    private NewsletterMailDAO newsletterMailDAO;
    @Autowired
    private NewsletterMailAddressDAO newsletterMailAddressDAO;
    @Autowired
    private NewsletterMailGroupDAO newsletterMailGroupDAO;
    @Autowired
    private JavaMailSender cmsMailSender;

    @RequestMapping(value = "/CWSFE_CMS/newsletterMails", method = RequestMethod.GET)
    public String defaultView(ModelMap model, Locale locale, HttpServletRequest httpServletRequest) {
        model.addAttribute("additionalJavaScriptCode", setAdditionalJS(httpServletRequest.getContextPath()));
        model.addAttribute("breadcrumbs", getBreadcrumbs(locale));
        return "cms/newsletterMails/NewsletterMails";
    }

    private Object setAdditionalJS(String contextPath) {
        List<String> jsUrl = new ArrayList<>(3);
        jsUrl.add(contextPath + "/resources-cwsfe-cms/js/cms/newsletterMails/NewsletterMails.js");
        return jsUrl;
    }

    private List<String> getBreadcrumbs(Locale locale) {
        List<String> breadcrumbs = new ArrayList<>(1);
        breadcrumbs.add("<a href=\"" +
                ServletUriComponentsBuilder.fromCurrentContextPath().path("/CWSFE_CMS/newsletterMails").build().toUriString() +
                "\" tabindex=\"-1\">" + ResourceBundle.getBundle(CWSFE_CMS_RESOURCE_BUNDLE_PATH, locale).getString("NewsletterMailsManagement") + "</a>");
        return breadcrumbs;
    }

    private Object setSingleNewsletterMailsAdditionalCss(String contextPath) {
        List<String> cssUrl = new ArrayList<>(3);
        cssUrl.add(contextPath + "/resources-cwsfe-cms/css/apprise/apprise-v2-min.css");
        return cssUrl;
    }

    private Object setSingleNewsletterMailsAdditionalJS(String contextPath) {
        List<String> jsUrl = new ArrayList<>(3);
        jsUrl.add(contextPath + "/resources-cwsfe-cms/js/apprise/apprise-v2.js");
        jsUrl.add(contextPath + "/resources-cwsfe-cms/js/cms/newsletterMails/SingleNewsletterMail.js");
        return jsUrl;
    }

    private List<String> getSingleNewsletterMailsBreadcrumbs(Locale locale, Long id) {
        List<String> breadcrumbs = new ArrayList<>(1);
        breadcrumbs.add("<a href=\"" +
                ServletUriComponentsBuilder.fromCurrentContextPath().path("/CWSFE_CMS/newsletterMails").build().toUriString() +
                "\" tabindex=\"-1\">" + ResourceBundle.getBundle(CWSFE_CMS_RESOURCE_BUNDLE_PATH, locale).getString("NewsletterMailsManagement") + "</a>");
        breadcrumbs.add("<a href=\"" +
                ServletUriComponentsBuilder.fromCurrentContextPath().path("/CWSFE_CMS/newsletterMails/" + id).build().toUriString() +
                "\" tabindex=\"-1\">" + ResourceBundle.getBundle(CWSFE_CMS_RESOURCE_BUNDLE_PATH, locale).getString("CurrentNewsletter") + "</a>");
        return breadcrumbs;
    }

    @RequestMapping(value = "/CWSFE_CMS/newsletterMailsList", method = RequestMethod.GET, produces = "application/json;charset=UTF-8;pageEncoding=UTF-8")
    @ResponseBody
    public String listNewsletterMails(
            @RequestParam int iDisplayStart,
            @RequestParam int iDisplayLength,
            @RequestParam String sEcho,
            @RequestParam(required = false) Long searchRecipientGroupId,
            @RequestParam(required = false) String searchName
    ) {
        List<NewsletterMail> dbList = newsletterMailDAO.searchByAjax(iDisplayStart, iDisplayLength, searchName, searchRecipientGroupId);
        Integer dbListDisplayRecordsSize = newsletterMailDAO.searchByAjaxCount(searchName, searchRecipientGroupId);
        JSONObject responseDetailsJson = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < dbList.size(); i++) {
            JSONObject formDetailsJson = new JSONObject();
            formDetailsJson.put("#", iDisplayStart + i + 1);
            final NewsletterMail objects = dbList.get(i);
            formDetailsJson.put("recipientGroupName", newsletterMailGroupDAO.get(objects.getRecipientGroupId()).getName());
            formDetailsJson.put("newsletterMailName", objects.getName());
            formDetailsJson.put("newsletterMailSubject", objects.getSubject());
            formDetailsJson.put("newsletterMailStatus", objects.getStatus());
            formDetailsJson.put("id", objects.getId());
            jsonArray.add(formDetailsJson);
        }
        responseDetailsJson.put("sEcho", sEcho);
        responseDetailsJson.put("iTotalRecords", dbListDisplayRecordsSize);
        responseDetailsJson.put("iTotalDisplayRecords", dbListDisplayRecordsSize);
        responseDetailsJson.put("aaData", jsonArray);
        return responseDetailsJson.toString();
    }

    @RequestMapping(value = "/CWSFE_CMS/addNewsletterMail", method = RequestMethod.POST, produces = "application/json;charset=UTF-8;pageEncoding=UTF-8")
    @ResponseBody
    public String addNewsletterMail(
            @ModelAttribute(value = "newsletterMail") NewsletterMail newsletterMail,
            BindingResult result, Locale locale
    ) {
        ValidationUtils.rejectIfEmpty(result, "recipientGroupId", ResourceBundle.getBundle(CWSFE_CMS_RESOURCE_BUNDLE_PATH, locale).getString("RecipientGroupMustBeSet"));
        ValidationUtils.rejectIfEmpty(result, "name", ResourceBundle.getBundle(CWSFE_CMS_RESOURCE_BUNDLE_PATH, locale).getString("NewsletterMailNameMustBeSet"));
        ValidationUtils.rejectIfEmpty(result, "subject", ResourceBundle.getBundle(CWSFE_CMS_RESOURCE_BUNDLE_PATH, locale).getString("SubjectMustBeSet"));
        JSONObject responseDetailsJson = new JSONObject();
        if (!result.hasErrors()) {
            newsletterMail.setMailContent("");
            newsletterMailDAO.add(newsletterMail);
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

    @RequestMapping(value = "/CWSFE_CMS/deleteNewsletterMail", method = RequestMethod.POST, produces = "application/json;charset=UTF-8;pageEncoding=UTF-8")
    @ResponseBody
    public String deleteNewsletterMail(
            @ModelAttribute(value = "newsletterMail") NewsletterMail newsletterMail,
            BindingResult result, Locale locale
    ) {
        ValidationUtils.rejectIfEmpty(result, "id", ResourceBundle.getBundle(CWSFE_CMS_RESOURCE_BUNDLE_PATH, locale).getString("NewsletterMailMustBeSet"));
        JSONObject responseDetailsJson = new JSONObject();
        if (!result.hasErrors()) {
            newsletterMailDAO.delete(newsletterMail);
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

    @RequestMapping(value = "/CWSFE_CMS/unDeleteNewsletterMail", method = RequestMethod.POST, produces = "application/json;charset=UTF-8;pageEncoding=UTF-8")
    @ResponseBody
    public String unDeleteNewsletterMail(
            @ModelAttribute(value = "newsletterMail") NewsletterMail newsletterMail,
            BindingResult result, Locale locale
    ) {
        ValidationUtils.rejectIfEmpty(result, "id", ResourceBundle.getBundle(CWSFE_CMS_RESOURCE_BUNDLE_PATH, locale).getString("NewsletterMailMustBeSet"));
        JSONObject responseDetailsJson = new JSONObject();
        if (!result.hasErrors()) {
            newsletterMailDAO.undelete(newsletterMail);
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

    @RequestMapping(value = "/CWSFE_CMS/newsletterMails/{id}", method = RequestMethod.GET)
    public String browseNewsletterMail(ModelMap model, Locale locale, @PathVariable("id") Long id, HttpServletRequest httpServletRequest) {
        model.addAttribute("additionalCssCode", setSingleNewsletterMailsAdditionalCss(httpServletRequest.getContextPath()));
        model.addAttribute("additionalJavaScriptCode", setSingleNewsletterMailsAdditionalJS(httpServletRequest.getContextPath()));
        model.addAttribute("breadcrumbs", getSingleNewsletterMailsBreadcrumbs(locale, id));
        NewsletterMail newsletterMail = newsletterMailDAO.get(id);
        model.addAttribute("newsletterMail", newsletterMail);
        NewsletterMailGroup newsletterMailGroup = newsletterMailGroupDAO.get(newsletterMail.getRecipientGroupId());
        model.addAttribute("newsletterMailGroupName", newsletterMailGroup.getName());
        return "cms/newsletterMails/SingleNewsletterMail";
    }

    @RequestMapping(value = "/CWSFE_CMS/newsletterMails/updateNewsletterMail", method = RequestMethod.POST)
    public String updateNewsletterMail(
            @ModelAttribute(value = "newsletterMail") NewsletterMail newsletterMail,
            BindingResult result, ModelMap model, Locale locale, HttpServletRequest httpServletRequest
    ) {
        ValidationUtils.rejectIfEmpty(result, "id", ResourceBundle.getBundle(CWSFE_CMS_RESOURCE_BUNDLE_PATH, locale).getString("NewsletterMailMustBeSet"));
        ValidationUtils.rejectIfEmpty(result, "recipientGroupId", ResourceBundle.getBundle(CWSFE_CMS_RESOURCE_BUNDLE_PATH, locale).getString("RecipientGroupMustBeSet"));
        ValidationUtils.rejectIfEmpty(result, "name", ResourceBundle.getBundle(CWSFE_CMS_RESOURCE_BUNDLE_PATH, locale).getString("NewsletterMailNameMustBeSet"));
        ValidationUtils.rejectIfEmpty(result, "subject", ResourceBundle.getBundle(CWSFE_CMS_RESOURCE_BUNDLE_PATH, locale).getString("SubjectMustBeSet"));
        ValidationUtils.rejectIfEmpty(result, "mailContent", ResourceBundle.getBundle(CWSFE_CMS_RESOURCE_BUNDLE_PATH, locale).getString("ContentMustBeSet"));
        if (!result.hasErrors()) {
            newsletterMail.setMailContent(newsletterMail.getMailContent().trim());
            newsletterMailDAO.update(newsletterMail);
            model.addAttribute("updateSuccessfull", ResourceBundle.getBundle(CWSFE_CMS_RESOURCE_BUNDLE_PATH, locale).getString("Saved"));
        } else {
            StringBuilder errors = new StringBuilder();
            for (int i = 0; i < result.getAllErrors().size(); i++) {
                errors.append(result.getAllErrors().get(i).getCode()).append("<br/>");
            }
            model.addAttribute("updateErrors", errors);
        }
        return browseNewsletterMail(model, locale, newsletterMail.getId(), httpServletRequest);
    }

    @RequestMapping(value = "/CWSFE_CMS/newsletterMails/newsletterSend", method = RequestMethod.POST, produces = "application/json;charset=UTF-8;pageEncoding=UTF-8")
    public
    @ResponseBody
    String newsletterSend(
            @ModelAttribute(value = "newsletterMail") NewsletterMail newsletterMail,
            BindingResult result, Locale locale
    ) {
        ValidationUtils.rejectIfEmpty(result, "id", ResourceBundle.getBundle(CWSFE_CMS_RESOURCE_BUNDLE_PATH, locale).getString("NewsletterMailMustBeSet"));
        newsletterMail = newsletterMailDAO.get(newsletterMail.getId());
        JSONObject responseDetailsJson = new JSONObject();
        if (!result.hasErrors()) {
            sendNewsletterEmail(newsletterMail);
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

    @RequestMapping(value = "/CWSFE_CMS/newsletterMails/newsletterTestSend", method = RequestMethod.POST, produces = "application/json;charset=UTF-8;pageEncoding=UTF-8")
    @ResponseBody
    public String newsletterTestSend(
            @ModelAttribute(value = "newsletterMail") NewsletterMail newsletterMail,
            @ModelAttribute(value = "newsletterMailAddress") NewsletterMailAddress newsletterMailAddress,
            BindingResult result, Locale locale
    ) {
        ValidationUtils.rejectIfEmpty(result, "id", ResourceBundle.getBundle(CWSFE_CMS_RESOURCE_BUNDLE_PATH, locale).getString("NewsletterMailMustBeSet"));
        ValidationUtils.rejectIfEmpty(result, "email", ResourceBundle.getBundle(CWSFE_CMS_RESOURCE_BUNDLE_PATH, locale).getString("EmailIsInvalid"));
        if (!EmailValidator.isValidEmailAddress(newsletterMailAddress.getEmail())) {
            result.rejectValue("email", ResourceBundle.getBundle(CWSFE_CMS_RESOURCE_BUNDLE_PATH, locale).getString("EmailIsInvalid"));
        }
        NewsletterMail newsletterMailFromDb = newsletterMailDAO.get(newsletterMail.getId());
        JSONObject responseDetailsJson = new JSONObject();
        if (!result.hasErrors()) {
            sendTestEmail(newsletterMailFromDb, newsletterMailAddress);
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

    private void sendNewsletterEmail(NewsletterMail newsletterMail) {
        MimeMessage mimeMessage = cmsMailSender.createMimeMessage();
        MimeMessageHelper helper;
        try {
            helper = new MimeMessageHelper(mimeMessage, false, "utf-8");
            mimeMessage.setContent(newsletterMail.getMailContent(), "text/html");
            helper.setSubject(newsletterMail.getSubject());
            helper.setReplyTo("info@cwsfe.pl");
            List<NewsletterMailAddress> newsletterMailAddresses = newsletterMailAddressDAO.listByRecipientGroup(newsletterMail.getRecipientGroupId());
            for (NewsletterMailAddress newsletterMailAddress : newsletterMailAddresses) {
                helper.setTo(newsletterMailAddress.getEmail());
                cmsMailSender.send(mimeMessage);
            }
        } catch (MessagingException e) {
            LOGGER.error("Message sending problem", e);
        }
    }


    private void sendTestEmail(NewsletterMail newsletterMail, NewsletterMailAddress newsletterMailAddress) {
        MimeMessage mimeMessage = cmsMailSender.createMimeMessage();
        MimeMessageHelper helper;
        try {
            helper = new MimeMessageHelper(mimeMessage, false, "utf-8");
            helper.setTo(newsletterMailAddress.getEmail());
            mimeMessage.setContent(newsletterMail.getMailContent(), "text/html");
            helper.setSubject(newsletterMail.getSubject());
            helper.setReplyTo("info@cwsfe.pl");
        } catch (MessagingException e) {
            LOGGER.error("Problem with sending message", e);
        }
        cmsMailSender.send(mimeMessage);
    }

}
