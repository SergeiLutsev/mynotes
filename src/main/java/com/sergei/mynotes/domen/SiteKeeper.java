
package com.sergei.mynotes.domen;

import javax.persistence.*;

import lombok.Data;

@Data
@Entity
public class SiteKeeper {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String url;
    @Embedded
    private Login login;
    @Lob
    private String note;
    
}
