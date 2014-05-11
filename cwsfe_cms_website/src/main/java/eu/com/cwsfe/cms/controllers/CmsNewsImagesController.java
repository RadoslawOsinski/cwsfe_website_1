package eu.com.cwsfe.cms.controllers;

import eu.com.cwsfe.cms.dao.CmsNewsImagesDAO;
import eu.com.cwsfe.cms.model.CmsNewsImage;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author Radoslaw Osinski
 */
@Controller
public class CmsNewsImagesController {

    @Autowired
    private CmsNewsImagesDAO cmsNewsImagesDAO;

    @RequestMapping(value = "/CWSFE_CMS/news/cmsNewsImagesList", method = RequestMethod.GET, produces = "application/json;charset=UTF-8;pageEncoding=UTF-8")
    public @ResponseBody String list(
            @RequestParam int iDisplayStart,
            @RequestParam int iDisplayLength,
            @RequestParam String sEcho,
            WebRequest webRequest
    ) {
        Long newsId = null;
        try {
            newsId = Long.parseLong(webRequest.getParameter("cmsNewsId"));
        } catch (NumberFormatException ignored) {
        }
        List<CmsNewsImage> dbList = cmsNewsImagesDAO.searchByAjaxWithoutContent(iDisplayStart, iDisplayLength, newsId);
        Integer dbListDisplayRecordsSize = cmsNewsImagesDAO.searchByAjaxCountWithoutContent(newsId);
        JSONObject responseDetailsJson = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < dbList.size(); i++) {
            JSONObject formDetailsJson = new JSONObject();
            formDetailsJson.put("#", iDisplayStart + i + 1);
            final CmsNewsImage object = dbList.get(i);
            formDetailsJson.put("title", object.getTitle());
            formDetailsJson.put("image", object.getId());
            formDetailsJson.put("id", object.getId());
            jsonArray.add(formDetailsJson);
        }
        responseDetailsJson.put("sEcho", sEcho);
        responseDetailsJson.put("iTotalRecords", cmsNewsImagesDAO.getTotalNumberNotDeleted());
        responseDetailsJson.put("iTotalDisplayRecords", dbListDisplayRecordsSize);
        responseDetailsJson.put("aaData", jsonArray);
        return responseDetailsJson.toString();
    }

    @RequestMapping(value = "/CWSFE_CMS/news/addCmsNewsImage", method = RequestMethod.POST)
    public ModelAndView addCmsNewsImage(
            @ModelAttribute(value = "cmsNewsImage") CmsNewsImage cmsNewsImage,
            BindingResult result, Locale locale
    ) {
        BufferedImage image;
        try {
            image = ImageIO.read(cmsNewsImage.getFile().getFileItem().getInputStream());
            cmsNewsImage.setWidth(image.getWidth());
            cmsNewsImage.setHeight(image.getHeight());
        } catch (IOException ignored) {
        }
        cmsNewsImage.setFileName(cmsNewsImage.getFile().getName());
        cmsNewsImage.setFileSize(cmsNewsImage.getFile().getSize());
        cmsNewsImage.setMimeType(cmsNewsImage.getFile().getContentType());
        cmsNewsImage.setContent(cmsNewsImage.getFile().getFileItem().get());
        cmsNewsImage.setCreated(new Date());
        ValidationUtils.rejectIfEmpty(result, "title", ResourceBundle.getBundle("cwsfe_cms_i18n", locale).getString("TitleMustBeSet"));
//        ValidationUtils.rejectIfEmpty(result, "file", ResourceBundle.getBundle("cwsfe_cms_i18n", locale).getString("FileNotChoosen"));
//        ValidationUtils.rejectIfEmpty(result, "fileName", ResourceBundle.getBundle("cwsfe_cms_i18n", locale).getString("FileNotChoosen"));
//        if (fileName == null || fileSize == null || mimeType == null) {
//                throw new BusinessException("<br>" + LANG.getTranslation("FileIsBrokenOrEmpty"));
//            } else if (!isImageMimeTypeValid(mimeType)) {
//                throw new BusinessException("<br>" + LANG.getTranslation("InvalidFileFormat") + ": " + mimeType);
//            } else {
        ValidationUtils.rejectIfEmpty(result, "newsId", ResourceBundle.getBundle("cwsfe_cms_i18n", locale).getString("CmsNewsMustBeSet"));
        if (!result.hasErrors()) {
            cmsNewsImagesDAO.add(cmsNewsImage);
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setView(new RedirectView("/CWSFE_CMS/news/" + cmsNewsImage.getNewsId(), true, false, false));
        return modelAndView;
    }

    @RequestMapping(value = "/CWSFE_CMS/news/deleteCmsNewsImage", method = RequestMethod.POST, produces = "application/json;charset=UTF-8;pageEncoding=UTF-8")
    public @ResponseBody String deleteCmsNewsImage(
            @ModelAttribute(value = "cmsNewsImage") CmsNewsImage cmsNewsImage,
            BindingResult result, Locale locale
    ) {
        ValidationUtils.rejectIfEmpty(result, "id", ResourceBundle.getBundle("cwsfe_cms_i18n", locale).getString("ImageMustBeSet"));
        JSONObject responseDetailsJson = new JSONObject();
        if (!result.hasErrors()) {
            cmsNewsImagesDAO.delete(cmsNewsImage);
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

    private static boolean isImageMimeTypeValid(String mimeType) {
        if (mimeType == null) {
            return false;
        }
        mimeType = mimeType.trim().toLowerCase();
        return mimeType.equals("image/gif") ||
                mimeType.equals("image/jpg") ||
                mimeType.equals("image/jpeg") ||
                mimeType.equals("image/pjpeg") ||
                mimeType.equals("image/bmp") ||
                mimeType.equals("image/png");
    }

    protected void initBinder(ServletRequestDataBinder binder) {
        binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());

    }

}
