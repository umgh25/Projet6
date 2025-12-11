package com.openclassrooms.mddapi.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AuthSuccessDto {

    private String token;

}