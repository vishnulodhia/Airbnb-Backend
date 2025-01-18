
package com.spring_boot.Airbnb.Controller;

import com.spring_boot.Airbnb.Dto.*;
import com.spring_boot.Airbnb.Security.AuthService;
import com.spring_boot.Airbnb.Service.BookingService;
import com.spring_boot.Airbnb.Service.UserService;
import io.jsonwebtoken.lang.Collections;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    private ResponseEntity<UserDto> signUp(@RequestBody SignupRequestDto signupRequestDto){
        return ResponseEntity.ok(authService.signUp(signupRequestDto));
    }

    @PostMapping("/login")
    private ResponseEntity<LoginResponseDto> addGuests(@RequestBody LoginDto loginDto, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
//        log.info("GuestDto {}",guestDtoList);
        String[] token = authService.login(loginDto);
        Cookie cookie = new Cookie("refreshToken",token[1]);
        httpServletResponse.addCookie(cookie);
        return ResponseEntity.ok(new LoginResponseDto(token[0]));
    }

    @PostMapping("/refresh")
    private ResponseEntity<LoginResponseDto> addGuests(HttpServletRequest httpServletRequest){
//        log.info("GuestDto {}",guestDtoList);
        String refreshToken = Arrays.stream(httpServletRequest.getCookies())
                .filter(cookie -> "refreshToken".equals(cookie.getName()))
                .map(Cookie::getValue)
                .findFirst()
                .orElse(null);

        String accessToken = authService.generateRefreshToken(refreshToken);
        return ResponseEntity.ok(new LoginResponseDto(accessToken));
    }
}
