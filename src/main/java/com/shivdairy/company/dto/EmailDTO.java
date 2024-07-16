package com.shivdairy.company.dto;

import lombok.Data;

@Data
public class EmailDTO {
    private String sender;
    private String recipient;
    private String subject;
    private String cc;
    private String bodyHtml;
}
