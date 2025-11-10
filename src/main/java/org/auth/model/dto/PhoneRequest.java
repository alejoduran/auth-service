package org.auth.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PhoneRequest {

    private Long number;
    private Integer cityCode;
    private String countryCode;


}
