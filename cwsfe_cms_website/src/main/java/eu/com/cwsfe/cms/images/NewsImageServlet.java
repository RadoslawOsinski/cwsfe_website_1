package eu.com.cwsfe.cms.images;

import org.springframework.beans.factory.annotation.Autowired;
import eu.com.cwsfe.cms.dao.CmsNewsImagesDAO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Radoslaw Osinski
 */
public class NewsImageServlet extends CachingImageServlet {

    private static final long serialVersionUID = -8378252746962850154L;

    @Autowired
    private CmsNewsImagesDAO cmsNewsImagesDAO;

    @Override
    protected Long validateRequestedImage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String imageIdString = request.getParameter("imageId");
        Long imageId;
        try {
            imageId = Long.parseLong(imageIdString);
        } catch (NumberFormatException ignored) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }
        return imageId;
    }

    @Override
    protected String getImageFileName(Long imageId) {
        return cmsNewsImagesDAO.getWithContent(imageId).getFileName();
    }

    @Override
    protected Long getFileSize(Long imageId) {
        return cmsNewsImagesDAO.getWithContent(imageId).getFileSize();
    }

    @Override
    protected Long getLastModified(Long imageId) {
        return cmsNewsImagesDAO.getWithContent(imageId).getCreated().getTime();   //todo should be last modified!;
    }

    @Override
    protected String getMimeType(Long imageId) {
        return cmsNewsImagesDAO.getWithContent(imageId).getMimeType();
    }

    @Override
    protected byte[] getContent(Long imageId) {
        return cmsNewsImagesDAO.getWithContent(imageId).getContent();
    }

}