package com.shopsphere.apigateway.filter;

import com.shopsphere.apigateway.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class JwtAuthenticationFilter implements GlobalFilter, Ordered {

    private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    private final JwtUtil jwtUtil;
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    private static final List<String> PUBLIC_AUTH_PATHS = List.of(
            "/api/auth/**"
    );

    private static final List<String> PUBLIC_GET_PATHS = List.of(
            "/api/catalog/products/**",
            "/api/catalog/categories/**"
    );

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();
        HttpMethod method = request.getMethod();

        //  Striping client-supplied internal headers to prevent spoofing
        ServerHttpRequest.Builder requestBuilder = request.mutate()
                .headers(headers -> {
                    headers.remove("X-User-Id");
                    headers.remove("X-User-Role");
                    headers.remove("X-Internal-Secret");
                });

        //  Check public endpoints
        boolean isAuthEndpoint = PUBLIC_AUTH_PATHS.stream()
                .anyMatch(pattern -> pathMatcher.match(pattern, path));

        boolean isPublicGetEndpoint = HttpMethod.GET.equals(method) && PUBLIC_GET_PATHS.stream()
                .anyMatch(pattern -> pathMatcher.match(pattern, path));

        if (isAuthEndpoint || isPublicGetEndpoint) {
            // Forward sanitized request without requiring JWT
            return chain.filter(exchange.mutate().request(requestBuilder.build()).build());
        }

        // 3. Validate Authorization header
        String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
             return Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Missing or malformed Authorization header"));
        }

        String token = authHeader.substring(7);
        if (!jwtUtil.isTokenValid(token)) {
            return Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid or expired token"));
        }

        // 4. Extract claims and populate internal headers
        String userId = jwtUtil.extractUserId(token);
        String role = jwtUtil.extractRole(token);

        ServerHttpRequest mutatedRequest = requestBuilder
                .header("X-User-Id", userId)
                .header("X-User-Role", role)
                .build();

        return chain.filter(exchange.mutate().request(mutatedRequest).build());
    }


    @Override
    public int getOrder() {
        return -1;
    }
}
