package com.singame.admin.domain;

import lombok.Data;

@Data
public class Permission {

    private Long id;
    private String name;
    private String descritpion;
    private String url;
    private Long pid;
}
