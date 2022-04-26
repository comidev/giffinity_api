package comidev.giffinity.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import comidev.giffinity.dto.FavDTO;
import comidev.giffinity.dto.FavsDTO;
import comidev.giffinity.dto.JwtAuthResponseDTO;
import comidev.giffinity.dto.LastSearchDTO;
import comidev.giffinity.dto.UserDTO;
import comidev.giffinity.jwt.JwtTokenService;
import comidev.giffinity.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserService userService;
    @Autowired
    private JwtTokenService jwtService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponseDTO> login(@RequestBody UserDTO login) {
        Authentication auth = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        login.getUsername(), login.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(auth);
        String token = jwtService.getToken(auth);
        return ResponseEntity.ok(new JwtAuthResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity<HttpStatus> register(@RequestBody UserDTO register) {
        String prevPassword = register.getPassword();
        String cryptPassword = passwordEncoder.encode(prevPassword);
        register.setPassword(cryptPassword);
        boolean added = userService.register(register);
        HttpStatus response = added ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<HttpStatus>(response);
    }

    @GetMapping("/lastSearch")
    public ResponseEntity<LastSearchDTO> lastSearch(@RequestHeader("Authorization") String token) {
        String username = jwtService.getUsernameByTokenBearer(token);
        String lastSearch = userService.getLastSearch(username);
        HttpStatus response = lastSearch != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return new ResponseEntity<LastSearchDTO>(new LastSearchDTO(lastSearch), response);
    }

    @GetMapping("/favs")
    public ResponseEntity<FavsDTO> favs(@RequestHeader("Authorization") String token) {
        String username = jwtService.getUsernameByTokenBearer(token);
        List<FavDTO> favs = userService.getFavs(username);
        HttpStatus response = favs != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return new ResponseEntity<FavsDTO>(new FavsDTO(favs), response);
    }
}
