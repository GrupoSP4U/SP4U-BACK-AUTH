package com.bandtec.api.controllers;

import com.bandtec.api.security.JwtTokenUtil;
import com.bandtec.api.security.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.*;

@RestController
@CrossOrigin
@RequestMapping("/token")
public class TokenController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @PostMapping
    public ResponseEntity validateToken(@RequestHeader("Authorization") String token,
                                        @RequestBody String email){
        if (token != null && token.startsWith("Bearer ")) {
            UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(email);
            boolean response = jwtTokenUtil.validateToken(token.substring(7), userDetails);
            if(response){
                return ok().build();
            }
            else return status(HttpStatus.UNAUTHORIZED).build();
        }
        return badRequest().build();
    }
}
