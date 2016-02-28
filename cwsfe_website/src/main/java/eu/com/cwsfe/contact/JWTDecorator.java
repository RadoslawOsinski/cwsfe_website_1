package eu.com.cwsfe.contact;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.security.Key;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Radoslaw Osinski
 */
@Service
public class JWTDecorator {

    @Resource
    private Environment environment;

    private static final Logger LOGGER = LoggerFactory.getLogger(JWTDecorator.class);

    /**
     * @param replayToEmail replay email
     * @param emailText email text
     * @return jwt message for sending email via CMS
     */
    String getJws(String replayToEmail, String emailText) {
        Key key = getPrivateKey();
        return createJWSContent(replayToEmail, emailText).signWith(SignatureAlgorithm.RS512, key).compact();
    }

    /**
     * @param replayToEmail replay email
     * @param emailText email text
     * @return content of JWT for sending email
     */
    private JwtBuilder createJWSContent(String replayToEmail, String emailText) {
        final Map<String, Object> claims = new HashMap<>();
        claims.put("replayToEmail", replayToEmail);
        claims.put("emailText", emailText);
        return Jwts.builder().setSubject("cwsfe_cms")
                .setIssuer("cwsfe")
                .setClaims(claims).setAudience("cwsfe_cms");
    }

    /**
     * @return key for signing JWT
     */
    private PrivateKey getPrivateKey() {
        PrivateKey privateKey = null;
        try {
            KeyStore keystore = KeyStore.getInstance("JKS");
            ClassPathResource classPathResource = new ClassPathResource(environment.getRequiredProperty("frontendAppKeystore.file"));
            keystore.load(classPathResource.getInputStream(), environment.getRequiredProperty("frontendAppKeystore.password").toCharArray());
            privateKey = (PrivateKey) keystore.getKey(environment.getRequiredProperty("frontendAppKeystore.signingKey"),
                    environment.getRequiredProperty("frontendAppKeystore.signingKey.password").toCharArray());
        } catch (Exception e) {
            LOGGER.error("Failed to retrieve private key from keystore.");
        }
        if (privateKey == null) {
            LOGGER.error("Failed to retrieve private key from keystore.");
        }
        return privateKey;
    }

}
