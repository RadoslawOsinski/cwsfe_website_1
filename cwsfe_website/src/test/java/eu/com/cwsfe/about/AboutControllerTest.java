package eu.com.cwsfe.about;

import org.junit.Test;

import static org.springframework.test.web.server.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.server.setup.MockMvcBuilders.standaloneSetup;

public class AboutControllerTest {

    @Test
    public void testShowPage() throws Exception {
        standaloneSetup(new AboutController()).build()
                .perform(get("/about"))
                .andExpect(status().isOk())
                .andExpect(view().name("about/About"));
    }

}