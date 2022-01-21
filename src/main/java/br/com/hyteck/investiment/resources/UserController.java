package br.com.hyteck.investiment.resources;

import br.com.hyteck.investiment.config.JwtTokenUtil;
import br.com.hyteck.investiment.framework.AbstractController;
import br.com.hyteck.investiment.services.MyUserDetailsService;
import br.com.hyteck.investiment.users.JwtRequest;
import br.com.hyteck.investiment.users.JwtResponse;
import br.com.hyteck.investiment.users.MyUserDetails;
import br.com.hyteck.investiment.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController extends AbstractController<User, String> {
    private final AuthenticationManager authenticationManager;

    private final JwtTokenUtil jwtTokenUtil;

    public UserController(MyUserDetailsService service, AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil) {
        super(service);
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    protected MyUserDetailsService getService() {
        return (MyUserDetailsService) super.getService();
    }


    @PostMapping(value = "/login")
    public ResponseEntity<?> createAuthenticationToken(@Valid @RequestBody JwtRequest authenticationRequest) throws Exception {
        Authentication authentication= authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();

        final String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    private Authentication authenticate(String username, String password) throws Exception {
        try {
            Authentication authentication= authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return authentication;
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

}
