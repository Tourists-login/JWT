package com.example.secure.SecurityController;

import com.example.secure.SecurityModel.Users;
import com.example.secure.SecurityService.UserService;
import org.apache.tomcat.util.http.parser.Authorization;
//import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/register")
    public Users register(@RequestBody Users user){

        return userService.register(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody Users user){

        return userService.verifyLogin(user);
    }

    @PostMapping("/info")
    public String info(Authentication authentication){
          String userName = (String) authentication.getPrincipal();
          Users user = userService.findByUserName(userName);
          String userInfo = "username : " + user.getUsername() + "\n" + "email :" + user.getEmail();
          return userInfo;
    }

}
