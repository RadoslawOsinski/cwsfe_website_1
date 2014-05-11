package eu.com.cwsfe.cms.images;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.HttpRequestHandler;
import eu.com.cwsfe.cms.dao.CmsNewsImagesDAO;
import eu.com.cwsfe.cms.model.CmsNewsImage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Radoslaw Osinski
 */
//@Component("newsImageServlet")
class NewsImageServlet implements HttpRequestHandler {

    @Autowired
    private CmsNewsImagesDAO cmsNewsImagesDAO;

    private final ConcurrentHashMap<Long, CmsNewsImage> cachedNewsImages = new ConcurrentHashMap<>(100);

    @Override
    public void handleRequest(HttpServletRequest request,
                              HttpServletResponse response) throws ServletException, IOException {
        final String imageIdString = request.getParameter("imageId");
        Long imageId = null;
        try {
            imageId = Long.parseLong(imageIdString);
        } catch (NumberFormatException ignored) {
        }

        CmsNewsImage cmsNewsImage = cachedNewsImages.get(imageId);
        if (cmsNewsImage == null) {
            cmsNewsImage = cmsNewsImagesDAO.getWithContent(imageId);
            cachedNewsImages.put(imageId, cmsNewsImage);
        }

        response.setContentType(cmsNewsImage.getMimeType());
        final int fileSize = Integer.parseInt(cmsNewsImage.getFileSize().toString());
        response.setContentLength(fileSize);
        response.setHeader("Content-Disposition", "inline; filename=\"" + cmsNewsImage.getFileName() + "\"");

        BufferedInputStream input = null;
        BufferedOutputStream output = null;
        try {
            input = new BufferedInputStream(new ByteArrayInputStream(cmsNewsImage.getContent()));
            output = new BufferedOutputStream(response.getOutputStream());
            byte[] buffer = new byte[8192];
            int length;
            while ((length = input.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
        } catch (IOException e) {
            System.out.println("There are errors in reading/writing image stream " + e.getMessage());
        } finally {
            if (output != null)
                try {
                    output.close();
                } catch (IOException ignore) {
                }
            if (input != null)
                try {
                    input.close();
                } catch (IOException ignore) {
                }
        }

    }
}