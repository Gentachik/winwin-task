package arturnikytenko.authapi.Services;

import arturnikytenko.authapi.Models.DataModel.DTO.LoginRequest;
import arturnikytenko.authapi.Models.DataModel.DTO.LoginResponse;
import arturnikytenko.authapi.Models.DataModel.DTO.RegisterRequest;
import arturnikytenko.authapi.Models.DataModel.DataModel;
import arturnikytenko.authapi.Repositories.DataModelRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class AuthService {

    private final DataModelRepository dataModelRepository;
    private final PasswordEncoder passwordEncoder;
    private final SecretKey jwtKey;
    private final long jwtExpiration;

    public AuthService(
            DataModelRepository dataModelRepository,
            PasswordEncoder passwordEncoder,
            @Value("${jwt.secret}") String jwtSecret,
            @Value("${jwt.expiration}") long jwtExpiration
    ) {
        this.dataModelRepository = dataModelRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtKey = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
        this.jwtExpiration = jwtExpiration;
    }

    public void register(RegisterRequest request) {
        String email = normalizeEmail(request.email());

        if (dataModelRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email already registered");
        }

        String passwordHash = passwordEncoder.encode(request.password());

        DataModel dataModel = new DataModel(email, passwordHash);

        dataModelRepository.save(dataModel);
    }

    public LoginResponse login(LoginRequest request) {
        String email = normalizeEmail(request.email());

        DataModel dataModel = dataModelRepository.findByEmail(email)
                .orElseThrow(() ->
                        new IllegalArgumentException("Invalid email or password")
                );

        if (!passwordEncoder.matches(
                request.password(),
                dataModel.getPasswordHash()
        )) {
            throw new IllegalArgumentException("Invalid email or password");
        }

        String token = generateToken(dataModel);

        return new LoginResponse(token);
    }

    private String generateToken(DataModel dataModel) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + jwtExpiration);

        return Jwts.builder()
                .subject(dataModel.getId().toString())
                .claim("email", dataModel.getEmail())
                .issuedAt(now)
                .expiration(expiration)
                .signWith(jwtKey)
                .compact();
    }


    private String normalizeEmail(String email) {
        return email.trim().toLowerCase();
    }
}

