package com.bytes.bytes.contexts.kitchen.adapters.outbound.providers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.bytes.bytes.contexts.kitchen.domain.port.outbound.TokenProviderPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JwtProvider implements TokenProviderPort {
    @Value("${security.token.secret}")
    private String secretKey;

    @Override
    public String generate(String subject, List<String> roles) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        return JWT.create()
                .withIssuer("Bytes")
                .withClaim("roles", roles)
                .withSubject(subject)
                .sign(algorithm);
    }
}
