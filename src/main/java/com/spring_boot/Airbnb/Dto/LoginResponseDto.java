package com.spring_boot.Airbnb.Dto;

import lombok.Builder;
import lombok.Data;

@Data

public class LoginResponseDto {
private String accessToken;

public LoginResponseDto(String accessToken){
    this.accessToken = accessToken;
}

}
