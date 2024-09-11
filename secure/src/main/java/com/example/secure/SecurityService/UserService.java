package com.example.secure.SecurityService;

import com.example.secure.SecurityModel.Users;
import com.example.secure.SecurityRepo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepo repo;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtService jwtService;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);
    public Users register(Users user){
        user.setPassword(encoder.encode(user.getPassword()));
        return repo.save(user);
    }

    public List<Users> getAllRegisterdUsers(){
        return repo.findAll();
    }

    public String verifyLogin(Users user){
        long accessTime = (long) (15 * 60 * 1000L);
        long refreshTime = (long) (1440L * 30 * 60 * 1000L);

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));

        if (authentication.isAuthenticated()){
            String tokens = "Access : "+jwtService.generateToken(user.getUsername(),"access",accessTime)+"\n";
            tokens+= "Refresh : "+jwtService.generateToken(user.getUsername(),"refresh",refreshTime);
            return tokens;
        }
        return  "Fail";
    }

    public Users findByUserName(String userName){
        return repo.findByUsername(userName);
    }

}
