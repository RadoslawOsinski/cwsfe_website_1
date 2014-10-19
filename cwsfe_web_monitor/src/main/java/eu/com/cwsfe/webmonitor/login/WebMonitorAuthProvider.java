package eu.com.cwsfe.webmonitor.login;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Radoslaw Osinski
 */
public class WebMonitorAuthProvider implements AuthenticationProvider {

    @Override
    public boolean supports(Class<?> authentication) {
        return (WebMonitorUsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }

    @Override
    public Authentication authenticate(Authentication auth) {
        //todo pozniej zaimplementowac w zaleznosci od projektu w cassandrze
        List<GrantedAuthority> authorities = new ArrayList<>(1);
        authorities.add(new SimpleGrantedAuthority("ROLE_CWSFE_WEB_MONITOR_ADMIN"));
        return new WebMonitorUsernamePasswordAuthenticationToken("admin", "admin", authorities);
    }

}