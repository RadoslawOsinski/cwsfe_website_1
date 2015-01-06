//package eu.com.cwsfe.newsletter;
//
//import eu.com.cwsfe.GenericController;
//import eu.com.cwsfe.cms.EmailValidator;
//import eu.com.cwsfe.cms.UUIDGenerator;
//import eu.com.cwsfe.cms.dao.CmsLanguagesDAO;
//import eu.com.cwsfe.cms.dao.NewsletterMailAddressDAO;
//import eu.com.cwsfe.cms.dao.NewsletterMailGroupDAO;
//import eu.com.cwsfe.cms.model.Language;
//import eu.com.cwsfe.cms.model.NewsletterMailAddress;
//import eu.com.cwsfe.cms.model.NewsletterMailGroup;
//import eu.com.cwsfe.model.Keyword;
//import eu.com.cwsfe.model.NewsletterSubscription;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.mail.MailSender;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.ModelMap;
//import org.springframework.validation.BindingResult;
//import org.springframework.validation.ValidationUtils;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Locale;
//import java.util.ResourceBundle;
//
///**
// * @author Radoslaw Osinski
// */
//@Controller
//class NewsletterController extends GenericController {
//
//    @Autowired
//    private NewsletterMailGroupDAO newsletterMailGroupDAO;
//    @Autowired
//    private NewsletterMailAddressDAO newsletterMailAddressDAO;
//    @Autowired
//    private CmsLanguagesDAO cmsLanguagesDAO;
//    @Autowired
//    @Qualifier("mailSender")
//    private MailSender cmsMailSender;
//
//    @RequestMapping(value = "/newsletterAddedToList", method = RequestMethod.GET)
//    public String showNewsletterAddedToList(ModelMap model, Locale locale, HttpServletRequest httpServletRequest) {
//        setPageMetadata(model, locale, httpServletRequest);
//        return "newsletter/NewsletterAddedToList";
//    }
//
//    @RequestMapping(value = "/confirmNewsletterAddress/{confirmString}", method = RequestMethod.GET)
//    public String newsletterConfirmAddress(
//            ModelMap model, Locale locale, @PathVariable(value = "confirmString") String confirmString, HttpServletRequest httpServletRequest
//    ) {
//        NewsletterMailAddress newsletterMailAddress = newsletterMailAddressDAO.getByConfirmString(confirmString);
//        setPageMetadata(model, locale, httpServletRequest);
//        if (newsletterMailAddress == null) {
//            return "newsletter/NewsletterAddressDoesNotExist";
//        } else if (newsletterMailAddress.getStatus().equals(NewsletterMailAddress.STATUS_INACTIVE)) {
//            newsletterMailAddressDAO.activate(newsletterMailAddress);
//            return "newsletter/NewsletterAddressConfirmed";
//        } else {
//            return "newsletter/NewsletterAddressAlreadyConfirmed";
//        }
//    }
//
//    private void setPageMetadata(ModelMap model, Locale locale, HttpServletRequest httpServletRequest) {
//        model.addAttribute("headerPageTitle", "CWSFE Newsletter");
//        model.addAttribute("keywords", setPageKeywords(locale));
//        model.addAttribute("additionalCssCode", setAdditionalCss());
//        model.addAttribute("mainJavaScript", getPageJS(httpServletRequest.getContextPath()));
//    }
//
//    public List<Keyword> setPageKeywords(Locale locale) {
//        List<Keyword> keywords = new ArrayList<>(1);
//        keywords.add(new Keyword(ResourceBundle.getBundle(CWSFE_RESOURCE_BUNDLE, locale).getString("CWSFENewsletter")));
//        return keywords;
//    }
//
//    private List<String> setAdditionalCss() {
//        return new ArrayList<>(0);
//    }
//
//    private String getPageJS(String contextPath) {
//        return contextPath + "/resources-cwsfe-cms/js/cms/newsletter/Newsletter.js";
//    }
//
//    @RequestMapping(value = "/addAddressToNewsletter", method = RequestMethod.POST)
//    public String addAddressToNewsletter(
//            @ModelAttribute("newsletterSubscription") NewsletterSubscription newsletterSubscription,
//            BindingResult result, ModelMap model, Locale locale, HttpServletRequest httpServletRequest
//    ) {
//        ValidationUtils.rejectIfEmptyOrWhitespace(result, "email", ResourceBundle.getBundle(CWSFE_RESOURCE_BUNDLE, locale).getString("EmailMustBeSet"));
//        if (!EmailValidator.isValidEmailAddress(newsletterSubscription.getEmail())) {
//            result.rejectValue("email", ResourceBundle.getBundle(CWSFE_RESOURCE_BUNDLE, locale).getString("EmailIsInvalid"));
//        }
//        if (!result.hasErrors()) {
//            Language lang = cmsLanguagesDAO.getByCode(locale.getLanguage());
//            NewsletterMailGroup mailGroup = newsletterMailGroupDAO.getByNameAndLanguage("General", lang.getId());
//            NewsletterMailAddress existingMailAddress = newsletterMailAddressDAO.getByEmailAndMailGroup(newsletterSubscription.getEmail(), mailGroup.getId());
//            if (existingMailAddress == null) {
//                NewsletterMailAddress newsletterMailAddress = addNewsletterMailAddress(newsletterSubscription, mailGroup);
//                sendConfirmationEmail(newsletterMailAddress);
//                return showNewsletterAddedToList(model, locale, httpServletRequest);
//            } else if (existingMailAddress.getStatus().equals(NewsletterMailAddress.STATUS_ACTIVE)) {
//                setPageMetadata(model, locale, httpServletRequest);
//                return "newsletter/NewsletterAddressAlreadyConfirmed";
//            } else if (existingMailAddress.getStatus().equals(NewsletterMailAddress.STATUS_INACTIVE)) {
//                sendConfirmationEmail(existingMailAddress);
//                return showNewsletterAddedToList(model, locale, httpServletRequest);
//            } else if (existingMailAddress.getStatus().equals(NewsletterMailAddress.STATUS_DELETED)) {
//                newsletterMailAddressDAO.deactivate(existingMailAddress);
//                sendConfirmationEmail(existingMailAddress);
//                return showNewsletterAddedToList(model, locale, httpServletRequest);
//            } else if (existingMailAddress.getStatus().equals(NewsletterMailAddress.STATUS_ERROR)) {
//                newsletterMailAddressDAO.deactivate(existingMailAddress);
//                sendConfirmationEmail(existingMailAddress);
//                return showNewsletterAddedToList(model, locale, httpServletRequest);
//            }
//        }
//        return "main/Main";
//    }
//
//    private NewsletterMailAddress addNewsletterMailAddress(NewsletterSubscription newsletterSubscription, NewsletterMailGroup mailGroup) {
//        NewsletterMailAddress newsletterMailAddress = new NewsletterMailAddress();
//        newsletterMailAddress.setMailGroupId(mailGroup.getId());
//        newsletterMailAddress.setConfirmString(UUIDGenerator.getRandomUniqueID());
//        while (newsletterMailAddressDAO.getByConfirmString(newsletterMailAddress.getConfirmString()) != null) {
//            newsletterMailAddress.setConfirmString(UUIDGenerator.getRandomUniqueID());
//        }
//        newsletterMailAddress.setUnSubscribeString(UUIDGenerator.getRandomUniqueID());
//        while (newsletterMailAddressDAO.getByUnSubscribeString(newsletterMailAddress.getUnSubscribeString()) != null) {
//            newsletterMailAddress.setUnSubscribeString(UUIDGenerator.getRandomUniqueID());
//        }
//        newsletterMailAddress.setEmail(newsletterSubscription.getEmail());
//        newsletterMailAddressDAO.add(newsletterMailAddress);
//        return newsletterMailAddress;
//    }
//
//    private void sendConfirmationEmail(NewsletterMailAddress newsletterMailAddress) {
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setTo(newsletterMailAddress.getEmail());
//        message.setSubject("[CWSFE] - newsletter confirmation");
//        //todo ładny tekst!
//        message.setText("Confirm account over link http://localhost:8080/confirmNewsletterAddress/" + newsletterMailAddress.getConfirmString());
//        message.setReplyTo("info@cwsfe.pl");
//        cmsMailSender.send(message);
//    }
//
//}
