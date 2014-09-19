package eu.com.cwsfe.cms;

/**
 * Created by Radosław Osiński
 */
public class BreadCrumbBuilder {

    private BreadCrumbBuilder() {
    }

    public static String getBreadCrumb(final String breadCrumbUrl, final String breadCrumbText) {
        return "<a href=\"" + breadCrumbUrl + "\" tabindex=\"-1\">" + breadCrumbText + "</a>";
    }

}
