package eu.com.cwsfe;

import eu.com.cwsfe.cms.dao.CmsNewsImagesDAO;
import eu.com.cwsfe.cms.model.CmsNewsImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Radoslaw Osinski.
 */
@Controller
public class ImageController {

    @Autowired
    private CmsNewsImagesDAO cmsNewsImagesDAO;

    public void setCmsNewsImagesDAO(CmsNewsImagesDAO cmsNewsImagesDAO) {
        this.cmsNewsImagesDAO = cmsNewsImagesDAO;
    }

    @RequestMapping(value = "/getImage/{id}", method = RequestMethod.GET)
    public void getImage(HttpServletResponse response, @PathVariable("id") final Long id) throws IOException {
        final CmsNewsImage cmsNewsImage = cmsNewsImagesDAO.get(id);
        response.setContentType(cmsNewsImage.getMimeType());
        response.getOutputStream().write(cmsNewsImage.getContent());
        response.getOutputStream().flush();
    }

}
