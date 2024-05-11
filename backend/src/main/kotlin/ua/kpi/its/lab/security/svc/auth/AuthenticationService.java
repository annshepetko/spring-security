package ua.kpi.its.lab.security.svc.auth;



import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.kpi.its.lab.security.dto.RegisterRequestDto;
import ua.kpi.its.lab.security.entity.User;
import ua.kpi.its.lab.security.repo.UserRepository;
import ua.kpi.its.lab.security.svc.jwt.JwtService;


@Service
public class AuthenticationService {

    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService
            (
                    AuthenticationManager authenticationManager,
                    UserDetailsService userDetailsService,
                    UserRepository userRepository,
                    PasswordEncoder passwordEncoder,
                    JwtService jwtService
            ){
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public String register(RegisterRequestDto registerRequestDto){
        User existingUser = (User) userDetailsService.loadUserByUsername(registerRequestDto.getUsername());

        User newUser = new User();
        newUser.setUsername(registerRequestDto.getUsername());
        newUser.setPassword(passwordEncoder.encode(registerRequestDto.getPassword()));

        userRepository.save(newUser);
        return jwtService.generateToken(existingUser);
    }
    public String authenticate(RegisterRequestDto authenticateRequestDto){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticateRequestDto.getUsername(), authenticateRequestDto.getPassword()));

        User user = (User) userDetailsService.loadUserByUsername(authenticateRequestDto.getUsername());
        return jwtService.generateToken(user);
    }
}
