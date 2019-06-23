package com.codekutter.grant.model.roles;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Getter
@Setter
@DiscriminatorValue("ROLE")
public class Role extends AbstractRole {

}
