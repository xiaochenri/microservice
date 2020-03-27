package com.microservice.login.auth.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Collection;

/**
 * @author hqc
 * @date 2020/3/25 11:33
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "T_USER")
public class User implements UserDetails {

    @Id
    private String id;
    @Column
    private String username;
    @Column
    private String password;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    /**账户默认为过期
     * @return
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**账户默认未锁定
     * @return
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
