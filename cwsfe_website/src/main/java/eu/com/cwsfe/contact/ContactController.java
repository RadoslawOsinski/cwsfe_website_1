package eu.com.cwsfe.contact;

import eu.com.cwsfe.GenericController;
import eu.com.cwsfe.model.Keyword;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author Radoslaw Osinski
 */
@Controller
public class ContactController extends GenericController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ContactController.class);

    @Autowired
    Environment environment;

    @Autowired
    JWTDecorator jwtDecorator;

    @RequestMapping(value = "/contact", method = RequestMethod.GET)
    public String showPage(ModelMap model, Locale locale, HttpServletRequest httpServletRequest) {
        setPageMetadata(model, locale, httpServletRequest);
        return "contact/Contact";
    }

    @RequestMapping(value = "/contact/sendEmail", method = RequestMethod.GET)
    public String showPageAfterMailSend(ModelMap model, Locale locale, HttpServletRequest httpServletRequest) {
        return showPage(model, locale, httpServletRequest);
    }

    private void setPageMetadata(ModelMap model, Locale locale, HttpServletRequest httpServletRequest) {
        model.addAttribute("headerPageTitle", ResourceBundle.getBundle(CWSFE_RESOURCE_BUNDLE, locale).getString("Contact"));
        model.addAttribute("keywords", setPageKeywords(locale));
        model.addAttribute("additionalCssCode", setAdditionalCss());
        model.addAttribute("mainJavaScript", httpServletRequest.getContextPath() + "/resources-cwsfe/js/Contact.js");
    }

    @RequestMapping(value = "/contact/sendEmail", method = RequestMethod.POST)
    public String sendMail(
            @ModelAttribute(value = "contactMail") ContactMail contactMail,
            BindingResult result, ModelMap model, Locale locale, HttpServletRequest httpServletRequest
    ) {
        ValidationUtils.rejectIfEmpty(result, "name", ResourceBundle.getBundle(CWSFE_RESOURCE_BUNDLE, locale).getString("NameMustBeSet"));
        ValidationUtils.rejectIfEmpty(result, "email", ResourceBundle.getBundle(CWSFE_RESOURCE_BUNDLE, locale).getString("EmailMustBeSet"));
        ValidationUtils.rejectIfEmpty(result, "message", ResourceBundle.getBundle(CWSFE_RESOURCE_BUNDLE, locale).getString("MessageIsRequired"));
        if (!EmailValidator.isValidEmailAddress(contactMail.getEmail())) {
            result.rejectValue("email", ResourceBundle.getBundle(CWSFE_RESOURCE_BUNDLE, locale).getString("EmailIsInvalid"));
            LOGGER.warn("Email {} is invalid", contactMail.getEmail());
        }
        if (!result.hasErrors()) {
            sendMailViaCms(contactMail.getEmail(), contactMail.getMessage());
            model.addAttribute("mailSended", ResourceBundle.getBundle(CWSFE_RESOURCE_BUNDLE, locale).getString("MessageHasBeenSent"));
        } else {
            StringBuilder errors = new StringBuilder();
            for (int i = 0; i < result.getAllErrors().size(); i++) {
                errors.append(result.getAllErrors().get(i).getCode()).append("<br/>");
            }
            model.addAttribute("mailSendOperationErrors", errors);
        }
        return showPage(model, locale, httpServletRequest);
    }

    private void sendMailViaCms(String replayToEmail, String emailText) {
        RestTemplate restTemplate = new RestTemplate();
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        messageConverters.add(new MappingJackson2HttpMessageConverter());
        restTemplate.setMessageConverters(messageConverters);
        String cmsAddress = environment.getRequiredProperty("CMS_ADDRESS");
        Map<String, String> map = new HashMap<>();
        String cmsSendEmailUrl = cmsAddress + "/rest/sendEmail?requestJWT=" + jwtDecorator.getJws(replayToEmail, emailText);
        try {
            restTemplate.postForObject(cmsSendEmailUrl, map, String.class);
        } catch (RestClientException e) {
            LOGGER.error("Problem with sending email via CMS: {}. Replay to email: {}. Email text: {}", cmsSendEmailUrl, replayToEmail, emailText, e);
        }
    }

    List<Keyword> setPageKeywords(Locale locale) {
        List<Keyword> keywords = new ArrayList<>(5);
        keywords.add(new Keyword(ResourceBundle.getBundle(CWSFE_RESOURCE_BUNDLE, locale).getString("CWSFEContact")));
        keywords.add(new Keyword(ResourceBundle.getBundle(CWSFE_RESOURCE_BUNDLE, locale).getString("MasovianDeveloper")));
        keywords.add(new Keyword(ResourceBundle.getBundle(CWSFE_RESOURCE_BUNDLE, locale).getString("SonskProgramming")));
        keywords.add(new Keyword(ResourceBundle.getBundle(CWSFE_RESOURCE_BUNDLE, locale).getString("CiechanowProgramming")));
        keywords.add(new Keyword(ResourceBundle.getBundle(CWSFE_RESOURCE_BUNDLE, locale).getString("DevelopingJPalioApplications")));
        return keywords;
    }

    private List<String> setAdditionalCss() {
        List<String> cssUrl = new ArrayList<>(3);
        cssUrl.add("/resources-cwsfe/css/Contact-min.css");
        return cssUrl;
    }

}
