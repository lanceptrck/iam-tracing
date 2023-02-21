package com.cict.iamtracing.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class EmailRequest {

    private String to;
    private String from;
    private String subject;
    private String text;
    private String pathToAttachment;

    private String templateName;
    private String contextName;
    private String contextIdentifier;
    private Object contextBody;

}
