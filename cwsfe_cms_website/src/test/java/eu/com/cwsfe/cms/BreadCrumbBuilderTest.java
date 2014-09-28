package eu.com.cwsfe.cms;

import org.junit.Test;

import static org.junit.Assert.*;

public class BreadCrumbBuilderTest {

    @Test
    public void testGetBreadCrumb() throws Exception {
        //Given
        String googleUrl = "https://google.com";
        String googleUrlName = "Google";

        //When
        String breadCrumbResult = BreadCrumbBuilder.getBreadCrumb(googleUrl, googleUrlName);

        //Then
        assertNotNull(breadCrumbResult);
        assertEquals("<a href=\"https://google.com\" tabindex=\"-1\">Google</a>", breadCrumbResult);
    }
}