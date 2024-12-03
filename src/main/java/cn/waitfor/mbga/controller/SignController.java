package cn.waitfor.mbga.controller;

import cn.waitfor.mbga.config.security.CookieJwt;
import cn.waitfor.mbga.dto.sign.SignInDto;
import cn.waitfor.mbga.dto.sign.SignUpDto;
import cn.waitfor.mbga.service.SignService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class SignController {

  private final SignService signService;

  private final CookieJwt cookieJwt;

  @ResponseStatus(HttpStatus.OK)
  @PostMapping("/sign-in")
  void signIn(
      HttpServletRequest request,
      HttpServletResponse response,
      @RequestBody @Valid SignInDto signInDto) {
    cookieJwt.createJwtCookie(request, response, String.valueOf(signService.signIn(signInDto)));
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("/sign-up")
  void signUp(@RequestBody @Valid SignUpDto signUpDto) {
    signService.signUp(signUpDto);
  }

  @ResponseStatus(HttpStatus.OK)
  @PostMapping("/sign-out")
  void signOut(HttpServletRequest request, HttpServletResponse response) {
    cookieJwt.removeJwtCookie(request, response);
  }
}
