package com.bodycodi.test.controller;

import com.bodycodi.test.dto.TokenDto;
import com.bodycodi.test.dto.UserDto;
import com.bodycodi.test.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
	
	@Autowired
	UserService userService;

    /**
     * 사용자를 가입한다.
     * @param userDto
     * @return
     */
    @PostMapping("/users")
    @ResponseBody
    public ResponseEntity<UserDto> insert(@RequestBody UserDto userDto) {
        if(StringUtils.isEmpty(userDto.getUsername()) || StringUtils.isEmpty(userDto.getPassword())) {
            throw new RuntimeException("Required username and password");
        }
//        int id = 1; // user Id 정보 가지고 오는 구조 개발
        
        UserDto dto = new UserDto();
       dto.setId(userService.insert(userDto));
        dto.setUsername(userDto.getUsername());
        dto.setPassword(userDto.getPassword());       
        
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    /**
     * 로그인 한뒤 토큰을 생성한다.
     * @param userDto
     * @return
     */
    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<TokenDto> login(@RequestBody UserDto userDto) {
        boolean isLogin = true;  // 로그인 체크
        TokenDto tokenDto = new TokenDto(); // 인증 토크 전달
        if(isLogin) {
            return ResponseEntity.status(HttpStatus.OK).body(tokenDto);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(tokenDto);
        }

    }

    /**
     * 토큰 테스트
     * @param userDto
     * @return
     */
    @PostMapping("/tokentest")
    @ResponseBody
    public ResponseEntity<UserDto> tokentest(@RequestAttribute("user") UserDto userDto) {

        return ResponseEntity.status(HttpStatus.OK).body(userDto);
    }
}
