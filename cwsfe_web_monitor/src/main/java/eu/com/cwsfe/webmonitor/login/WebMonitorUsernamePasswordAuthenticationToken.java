package eu.com.cwsfe.webmonitor.login;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * Created by Radosław Osiński
 */
public class WebMonitorUsernamePasswordAuthenticationToken extends UsernamePasswordAuthenticationToken {
    public WebMonitorUsernamePasswordAuthenticationToken(Object principal, Object credentials) {
        super(principal, credentials);
    }

    public WebMonitorUsernamePasswordAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }
}
