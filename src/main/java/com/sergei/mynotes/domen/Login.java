package com.sergei.mynotes.domen;

import lombok.Data;

import javax.persistence.Embeddable;
@Data
@Embeddable
public class Login {
    private String userName;
    private String password;
}
