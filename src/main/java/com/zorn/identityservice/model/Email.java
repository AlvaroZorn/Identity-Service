package com.zorn.identityservice.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Email {

    private String from;
    private String subject;
    private String to;
    private String text;
}
