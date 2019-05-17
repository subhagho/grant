package com.codekutter.grant.model.user;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.security.auth.Subject;
import java.security.Principal;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User implements Principal {
    @Column(name = "user_id")
    private String userId;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "state")
    private EUserState state;

    @Override
    public String getName() {
        return userId;
    }

    @Override
    public boolean implies(Subject subject) {
        if (subject != null) {
            Set<Principal> principals = subject.getPrincipals();
            if (principals != null && !principals.isEmpty()) {
                for(Principal principal : principals) {
                    if (principal instanceof User) {
                        User user = (User)principal;
                        if (userId.compareTo(user.userId) == 0) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
