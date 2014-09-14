package eu.com.cwsfe.contact;

import eu.com.cwsfe.model.Keyword;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author Radoslaw Osinski
 */
@Controller
public class ContactController {

    private static final Logger LOGGER = LogManager.getLogger(ContactController.class);

    @Autowired
    private MailSender mailSender;

    @RequestMapping(value = "/contact", method = RequestMethod.GET)
    public String showPage(ModelMap model, Locale locale) {
        setPageMetadata(model, locale);
        return "contact/Contact";
    }

    @RequestMapping(value = "/contact/sendEmail", method = RequestMethod.GET)
    public String showPageAfterMailSend(ModelMap model, Locale locale) {
        return showPage(model, locale);
    }

    private void setPageMetadata(ModelMap model, Locale locale) {
        model.addAttribute("headerPageTitle", ResourceBundle.getBundle("cwsfe_i18n", locale).getString("Contact"));
        model.addAttribute("keywords", setPageKeywords(locale));
        model.addAttribute("additionalCssCode", setAdditionalCss());
        model.addAttribute("additionalJavaScriptCode", "/resources-cwsfe/js/Contact.js");
    }

    @RequestMapping(value = "/contact/sendEmail", method = RequestMethod.POST)
    public String sendMail(
            @ModelAttribute(value = "contactMail") ContactMail contactMail,
            BindingResult result, ModelMap model, Locale locale
    ) {
        ValidationUtils.rejectIfEmpty(result, "name", ResourceBundle.getBundle("cwsfe_i18n", locale).getString("NameMustBeSet"));
        ValidationUtils.rejectIfEmpty(result, "email", ResourceBundle.getBundle("cwsfe_i18n", locale).getString("EmailMustBeSet"));
        ValidationUtils.rejectIfEmpty(result, "message", ResourceBundle.getBundle("cwsfe_i18n", locale).getString("MessageIsRequired"));
        if (!EmailValidator.isValidEmailAddress(contactMail.getEmail())) {
            result.rejectValue("email", ResourceBundle.getBundle("cwsfe_i18n", locale).getString("EmailIsInvalid"));
            LOGGER.warn("Email " + contactMail.getEmail() + " is invalid");
        }
        if (!result.hasErrors()) {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo("Radoslaw.Osinski@cwsfe.pl");
            message.setSubject("[CWSFE] - contact mail from " + contactMail.getName());
            message.setText(contactMail.getMessage());
            message.setReplyTo(contactMail.getEmail());
            mailSender.send(message);
            model.addAttribute("mailSended", ResourceBundle.getBundle("cwsfe_i18n", locale).getString("MessageHasBeenSent"));
        } else {
            String errors = "";
            for (int i = 0; i < result.getAllErrors().size(); i++) {
                errors += result.getAllErrors().get(i).getCode() + "<br/>";
            }
            model.addAttribute("mailSendOperationErrors", errors);
        }
        return showPage(model, locale);
    }

    List<Keyword> setPageKeywords(Locale locale) {
        List<Keyword> keywords = new ArrayList<>(5);
        keywords.add(new Keyword(ResourceBundle.getBundle("cwsfe_i18n", locale).getString("CWSFEContact")));
        keywords.add(new Keyword(ResourceBundle.getBundle("cwsfe_i18n", locale).getString("MasovianDeveloper")));
        keywords.add(new Keyword(ResourceBundle.getBundle("cwsfe_i18n", locale).getString("SonskProgramming")));
        keywords.add(new Keyword(ResourceBundle.getBundle("cwsfe_i18n", locale).getString("CiechanowProgramming")));
        keywords.add(new Keyword(ResourceBundle.getBundle("cwsfe_i18n", locale).getString("DevelopingJPalioApplications")));
        return keywords;
    }

    private List<String> setAdditionalCss() {
        List<String> cssUrl = new ArrayList<>(3);
        cssUrl.add("/resources-cwsfe/css/Contact-min.css");
        return cssUrl;
    }

}
