package com.evg.authservice.entity;


import com.evg.authservice.converter.PasswordConverter;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@Entity
@Table(schema = "evg", name = "user")
@EqualsAndHashCode(callSuper = true)
public class User extends EntityBase<Long> {

    @Column(name = "NAME", columnDefinition = "VARCHAR(50)")
    private String name;
    @Column(name = "FIRST_NAME", columnDefinition = "VARCHAR(50)")
    private String firstName;
    @Column(name = "LAST_NAME", columnDefinition = "VARCHAR(50)")
    private String lastName;
    @Column(name = "EMAIL", columnDefinition = "VARCHAR(50)")
    private String email;
    @Column(name = "PHONE", columnDefinition = "VARCHAR(50)")
    private String phone;
    @Column(name = "password", columnDefinition = "VARCHAR(50)")
    //@Convert(converter = PasswordConverter.class)
    private String password;

}
