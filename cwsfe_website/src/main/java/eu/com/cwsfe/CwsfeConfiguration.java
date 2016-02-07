package eu.com.cwsfe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

/**
 * @author Radoslaw Osinski
 */
@Service
public class CwsfeConfiguration {

    @Autowired
    Environment environment;

    public String getRequiredProperty(String propertyName) {
        return environment.getRequiredProperty(propertyName);
    }
}
