package com.microservice.login.auth.pwd;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PwdAuthenticationToken extends AbstractAuthenticationToken {

    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    private final Object principal;
    private final String passWord;
    private final String loginType = "pwd";

    public PwdAuthenticationToken(final String sfzh,final String passWord) {
        super(null);
        this.principal = sfzh;
        this.passWord = passWord;
        setAuthenticated(false);
    }

    public PwdAuthenticationToken(final Object principal, Collection<? extends GrantedAuthority> authorities,final String passWord) {
        super(authorities);
        this.principal = principal;
        super.setAuthenticated(true); // must use super, as we override
        this.passWord = passWord;
    }

    /**
     * The credentials that prove the principal is correct. This is usually a
     * password, but could be anything relevant to the
     * <code>AuthenticationManager</code>. Callers are expected to populate the
     * credentials.
     *
     * @return the credentials that prove the identity of the <code>Principal</code>
     */
    @Override
    public Object getCredentials() {
        return null;
    }

    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if (isAuthenticated) {
            throw new IllegalArgumentException(
                    "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        }

        super.setAuthenticated(false);
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
    }

}
