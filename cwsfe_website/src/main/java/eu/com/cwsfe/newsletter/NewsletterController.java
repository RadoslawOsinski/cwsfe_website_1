package eu.com.cwsfe.newsletter;

import eu.com.cwsfe.cms.EmailValidator;
import eu.com.cwsfe.cms.UUIDGenerator;
import eu.com.cwsfe.cms.dao.CmsLanguagesDAO;
import eu.com.cwsfe.cms.dao.NewsletterMailAddressDAO;
import eu.com.cwsfe.cms.dao.NewsletterMailGroupDAO;
import eu.com.cwsfe.cms.model.Lang;
import eu.com.cwsfe.cms.model.NewsletterMailAddress;
import eu.com.cwsfe.cms.model.NewsletterMailGroup;
import eu.com.cwsfe.model.Keyword;
import eu.com.cwsfe.model.NewsletterSubscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
class NewsletterController {

    @Autowired
    private NewsletterMailGroupDAO newsletterMailGroupDAO;
    @Autowired
    private NewsletterMailAddressDAO newsletterMailAddressDAO;
    @Autowired
    private CmsLanguagesDAO cmsLanguagesDAO;
    @Autowired
    private MailSender mailSender;

    @RequestMapping(value = "/newsletterAddedToList", method = RequestMethod.GET)
    public String showNewsletterAddedToList(ModelMap model, Locale locale) {
        setPageMetadata(model, locale);
        return "newsletter/NewsletterAddedToList";
    }

    @RequestMapping(value = "/confirmNewsletterAddress/{confirmString}", method = RequestMethod.GET)
    public String newsletterConfirmAddress(
            ModelMap model, Locale locale, @PathVariable(value = "confirmString") String confirmString
    ) {
        NewsletterMailAddress newsletterMailAddress = newsletterMailAddressDAO.getByConfirmString(confirmString);
        setPageMetadata(model, locale);
        if (newsletterMailAddress == null) {
            return "newsletter/NewsletterAddressDoesNotExist";
        } else if (newsletterMailAddress.getStatus().equals(NewsletterMailAddress.STATUS_INACTIVE)) {
            newsletterMailAddressDAO.activate(newsletterMailAddress);
            return "newsletter/NewsletterAddressConfirmed";
        } else {
            return "newsletter/NewsletterAddressAlreadyConfirmed";
        }
    }

    private void setPageMetadata(ModelMap model, Locale locale) {
        model.addAttribute("headerPageTitle", "CWSFE Newsletter");
        model.addAttribute("keywords", setPageKeywords(locale));
        model.addAttribute("additionalCssCode", setAdditionalCss());
        model.addAttribute("additionalJavaScriptCode", setAdditionalJS());
    }

    public List<Keyword> setPageKeywords(Locale locale) {
        List<Keyword> keywords = new ArrayList<>(1);
        keywords.add(new Keyword(ResourceBundle.getBundle("cwsfe_i18n", locale).getString("CWSFENewsletter")));
        return keywords;
    }

    private List<String> setAdditionalCss() {
        return new ArrayList<>(0);
    }

    private Object setAdditionalJS() {
        return new ArrayList<>(0);
    }

    @RequestMapping(value = "/addAddressToNewsletter", method = RequestMethod.POST)
    public String addAddressToNewsletter(
            @ModelAttribute("newsletterSubscription") NewsletterSubscription newsletterSubscription,
            BindingResult result, ModelMap model, Locale locale
    ) {
        ValidationUtils.rejectIfEmptyOrWhitespace(result, "email", ResourceBundle.getBundle("cwsfe_i18n", locale).getString("EmailMustBeSet"));
        if (!EmailValidator.isValidEmailAddress(newsletterSubscription.getEmail())) {
            result.rejectValue("email", ResourceBundle.getBundle("cwsfe_i18n", locale).getString("EmailIsInvalid"));
        }
        if (!result.hasErrors()) {
            Lang lang = cmsLanguagesDAO.getByCode(locale.getLanguage());
            NewsletterMailGroup mailGroup = newsletterMailGroupDAO.getByNameAndLanguage("General", lang.getId());
            NewsletterMailAddress existingMailAddress = newsletterMailAddressDAO.getByEmailAndMailGroup(newsletterSubscription.getEmail(), mailGroup.getId());
            if (existingMailAddress == null) {
                NewsletterMailAddress newsletterMailAddress = new NewsletterMailAddress();
                newsletterMailAddress.setMailGroupId(mailGroup.getId());
                newsletterMailAddress.setConfirmString(UUIDGenerator.getRandomUniqueID());
                while (newsletterMailAddressDAO.getByConfirmString(newsletterMailAddress.getConfirmString()) != null) {
                    newsletterMailAddress.setConfirmString(UUIDGenerator.getRandomUniqueID());
                }
                newsletterMailAddress.setUnSubscribeString(UUIDGenerator.getRandomUniqueID());
                while (newsletterMailAddressDAO.getByUnSubscribeString(newsletterMailAddress.getUnSubscribeString()) != null) {
                    newsletterMailAddress.setUnSubscribeString(UUIDGenerator.getRandomUniqueID());
                }
                newsletterMailAddress.setEmail(newsletterSubscription.getEmail());
                newsletterMailAddressDAO.add(newsletterMailAddress);
                sendConfirmationEmail(newsletterMailAddress);
                return showNewsletterAddedToList(model, locale);
            } else if (existingMailAddress.getStatus().equals(NewsletterMailAddress.STATUS_ACTIVE)) {
                setPageMetadata(model, locale);
                return "newsletter/NewsletterAddressAlreadyConfirmed";
            } else if (existingMailAddress.getStatus().equals(NewsletterMailAddress.STATUS_INACTIVE)) {
                sendConfirmationEmail(existingMailAddress);
                return showNewsletterAddedToList(model, locale);
            } else if (existingMailAddress.getStatus().equals(NewsletterMailAddress.STATUS_DELETED)) {
                newsletterMailAddressDAO.deactivate(existingMailAddress);
                sendConfirmationEmail(existingMailAddress);
                return showNewsletterAddedToList(model, locale);
            } else if (existingMailAddress.getStatus().equals(NewsletterMailAddress.STATUS_ERROR)) {
                newsletterMailAddressDAO.deactivate(existingMailAddress);
                sendConfirmationEmail(existingMailAddress);
                return showNewsletterAddedToList(model, locale);
            }
        }
        return "main/Main";
    }

    private void sendConfirmationEmail(NewsletterMailAddress newsletterMailAddress) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(newsletterMailAddress.getEmail());
        message.setSubject("[CWSFE] - newsletter confirmation");
        //todo ładny tekst!
        message.setText("Confirm account over link http://localhost:8080/confirmNewsletterAddress/" + newsletterMailAddress.getConfirmString());
        message.setReplyTo("info@cwsfe.pl");
        mailSender.send(message);
    }

}
