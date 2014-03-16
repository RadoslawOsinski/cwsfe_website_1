package eu.com.cwsfe.cms.controllers;

import eu.com.cwsfe.cms.dao.BlogPostImagesDAO;
import eu.com.cwsfe.cms.model.BlogPostImage;
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
public class BlogPostImagesController {

    @Autowired
    private BlogPostImagesDAO blogPostImagesDAO;

    @RequestMapping(value = "/CWSFE_CMS/blogPosts/blogPostImagesList", method = RequestMethod.GET, produces = "application/json;charset=UTF-8;pageEncoding=UTF-8")
    public @ResponseBody String listBlogKeywords(
            @RequestParam int iDisplayStart,
            @RequestParam int iDisplayLength,
            @RequestParam String sEcho,
            WebRequest webRequest
    ) {
        Long blogPostId = null;
        try {
            blogPostId = Long.parseLong(webRequest.getParameter("blogPostId"));
        } catch (NumberFormatException ignored) {
        }
        List<BlogPostImage> dbList = blogPostImagesDAO.searchByAjaxWithoutContent(iDisplayStart, iDisplayLength, blogPostId);
        Integer dbListDisplayRecordsSize = blogPostImagesDAO.searchByAjaxCountWithoutContent(blogPostId);
        JSONObject responseDetailsJson = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < dbList.size(); i++) {
            JSONObject formDetailsJson = new JSONObject();
            formDetailsJson.put("#", iDisplayStart + i + 1);
            final BlogPostImage object = dbList.get(i);
            formDetailsJson.put("title", object.getTitle());
            formDetailsJson.put("image", object.getId());
            formDetailsJson.put("id", object.getId());
            jsonArray.add(formDetailsJson);
        }
        responseDetailsJson.put("sEcho", sEcho);
        responseDetailsJson.put("iTotalRecords", blogPostImagesDAO.getTotalNumberNotDeleted());
        responseDetailsJson.put("iTotalDisplayRecords", dbListDisplayRecordsSize);
        responseDetailsJson.put("aaData", jsonArray);
        return responseDetailsJson.toString();
    }

    @RequestMapping(value = "/CWSFE_CMS/blogPosts/addBlogPostImage", method = RequestMethod.POST)
    public ModelAndView addBlogPostImage(
            @ModelAttribute(value = "blogPostImage") BlogPostImage blogPostImage,
            BindingResult result, Locale locale
    ) {
        BufferedImage image;
        try {
            image = ImageIO.read(blogPostImage.getFile().getFileItem().getInputStream());
            blogPostImage.setWidth(image.getWidth());
            blogPostImage.setHeight(image.getHeight());
        } catch (IOException ignored) {
        }
        blogPostImage.setFileName(blogPostImage.getFile().getName());
        blogPostImage.setFileSize(blogPostImage.getFile().getSize());
        blogPostImage.setMimeType(blogPostImage.getFile().getContentType());
        blogPostImage.setContent(blogPostImage.getFile().getFileItem().get());
        blogPostImage.setCreated(new Date());
        ValidationUtils.rejectIfEmpty(result, "title", ResourceBundle.getBundle("cwsfe_cms_i18n", locale).getString("TitleMustBeSet"));
//        ValidationUtils.rejectIfEmpty(result, "file", ResourceBundle.getBundle("cwsfe_cms_i18n", locale).getString("FileNotChoosen"));
//        ValidationUtils.rejectIfEmpty(result, "fileName", ResourceBundle.getBundle("cwsfe_cms_i18n", locale).getString("FileNotChoosen"));
//        if (fileName == null || fileSize == null || mimeType == null) {
//                throw new BusinessException("<br>" + LANG.getTranslation("FileIsBrokenOrEmpty"));
//            } else if (!isImageMimeTypeValid(mimeType)) {
//                throw new BusinessException("<br>" + LANG.getTranslation("InvalidFileFormat") + ": " + mimeType);
//            } else {
        ValidationUtils.rejectIfEmpty(result, "blogPostId", ResourceBundle.getBundle("cwsfe_cms_i18n", locale).getString("BlogPostMustBeSet"));
        if (!result.hasErrors()) {
            blogPostImagesDAO.add(blogPostImage);
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setView(new RedirectView("/CWSFE_CMS/blogPosts/" + blogPostImage.getBlogPostId(), true, false, false));
        return modelAndView;
    }

    @RequestMapping(value = "/CWSFE_CMS/blogPosts/deleteBlogPostImage", method = RequestMethod.POST, produces = "application/json;charset=UTF-8;pageEncoding=UTF-8")
    public @ResponseBody String deleteBlogPostImage(
            @ModelAttribute(value = "blogPostImage") BlogPostImage blogPostImage,
            BindingResult result, Locale locale
    ) {
        ValidationUtils.rejectIfEmpty(result, "id", ResourceBundle.getBundle("cwsfe_cms_i18n", locale).getString("ImageMustBeSet"));
        JSONObject responseDetailsJson = new JSONObject();
        if (!result.hasErrors()) {
            blogPostImagesDAO.delete(blogPostImage);
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