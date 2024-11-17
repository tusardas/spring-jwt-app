package com.tusardas.spring_jwt_app.config;

import java.io.BufferedReader;
import java.io.FileReader;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import com.tusardas.spring_jwt_app.config.utils.MyPasswordEncoder;
import com.tusardas.spring_jwt_app.config.utils.MyUserDetailsService;

@Configuration
public class WebSecurityConfig {
    Logger log = LoggerFactory.getLogger(WebSecurityConfig.class);
    private RSAKey rsaKey;
    
    @Bean
    public UserDetailsService userDetailsService() {
        return new MyUserDetailsService();
    }
     
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new MyPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authManager() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(authProvider);
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
        .csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(
            auth -> auth.requestMatchers("/static/**", "/templates/**" , "/loginPage").permitAll()
                        .requestMatchers(HttpMethod.POST, "/getAccessToken").permitAll()
                        .anyRequest().authenticated()
        )
        .cors(AbstractHttpConfigurer::disable)
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
        .build()
        ;
    }
    
    private RSAKey generateRsa() throws Exception {
        //KeyPair keyPair = generateRsaKey();
        KeyPair keyPair = loadExistingKey();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        
        return new RSAKey.Builder(publicKey)
                .privateKey(privateKey)
                .keyID(UUID.randomUUID().toString())
                .build();
    }

    /*
    private KeyPair generateRsaKey() {
        KeyPair keyPair;
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            keyPair = keyPairGenerator.generateKeyPair();
        } catch (Exception ex) {
            throw new IllegalStateException(ex);
        }
        return keyPair;
    }
    */

    private KeyPair loadExistingKey() throws Exception {
        String privateKeyFilePath = "D:\\workspace2\\spring-jwt-app\\src\\main\\resources\\private.properties";
        String publicKeyFilePath = "D:\\workspace2\\spring-jwt-app\\src\\main\\resources\\public.properties";
        
        String privateKey = readFile(privateKeyFilePath);
        String publicKey = readFile(publicKeyFilePath);
        
        privateKey = privateKey
            .replace("-----BEGIN PRIVATE KEY-----", "")
            .replace("-----END PRIVATE KEY-----", "")
            .replace("\n", "")
            .trim();

        publicKey = publicKey
            .replace("-----BEGIN PUBLIC KEY-----", "")
            .replace("-----END PUBLIC KEY-----", "")
            .replace("\n", "")
            .trim();

        log.info("privateKey --------------> " + privateKey);
        log.info("publicKey --------------> " + publicKey);
        
        Decoder decoder = Base64.getMimeDecoder();

        byte[] private_key_array = decoder.decode(privateKey);
        byte[] public_key_array = decoder.decode(publicKey);

        // Reconstruct public key
        PrivateKey reconstructed_private_key = reconstruct_private_key("RSA", private_key_array);
        PublicKey reconstructed_public_key = reconstruct_public_key("RSA", public_key_array);
        
        KeyPair keyPair = new KeyPair(reconstructed_public_key, reconstructed_private_key);
        return keyPair;
    }

    private String readFile(String path) throws Exception {
        String everything;
        BufferedReader br = new BufferedReader(new FileReader(path));
        
        StringBuilder sb = new StringBuilder();
        String line = br.readLine();

        while (line != null) {
            sb.append(line);
            sb.append(System.lineSeparator());
            line = br.readLine();
        }
        everything = sb.toString();
    
        br.close();
        
        return everything;
    }
    
    private PrivateKey reconstruct_private_key(String algorithm, byte[] private_key_array) {
        PrivateKey private_key = null;

        try {
            KeyFactory kf = KeyFactory.getInstance(algorithm);
            EncodedKeySpec private_key_spec = new PKCS8EncodedKeySpec(private_key_array);
            private_key = kf.generatePrivate(private_key_spec);
        } catch(NoSuchAlgorithmException e) {
            log.info("Could not reconstruct the private key, the given algorithm oculd not be found.");
        } catch(InvalidKeySpecException e) {
            log.info("Could not reconstruct the private key");
            e.printStackTrace();
        }

        return private_key;
    }

    private PublicKey reconstruct_public_key(String algorithm, byte[] pub_key) {
        PublicKey public_key = null;

        try {
            KeyFactory kf = KeyFactory.getInstance(algorithm);
            EncodedKeySpec pub_key_spec = new X509EncodedKeySpec(pub_key);
            public_key = kf.generatePublic(pub_key_spec);
        } catch(NoSuchAlgorithmException e) {
            log.info("Could not reconstruct the public key, the given algorithm oculd not be found.");
        } catch(InvalidKeySpecException e) {
            log.info("Could not reconstruct the public key");
        }

        return public_key;
    }

    @Bean
    public JWKSource<SecurityContext> jwkSource() throws Exception {
        rsaKey = generateRsa();
        JWKSet jwkSet = new JWKSet(rsaKey);
        return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
    }

    @Bean
    JwtEncoder jwtEncoder(JWKSource<SecurityContext> jwks) {
        return new NimbusJwtEncoder(jwks);
    }

    @Bean
    JwtDecoder jwtDecoder() {
        try {
            
            return NimbusJwtDecoder.withPublicKey(rsaKey.toRSAPublicKey()).build();
        }
        catch(JOSEException joseException) {

        }
        return null;
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:3000"));
        configuration.setAllowedMethods(List.of("GET","POST"));
        configuration.setAllowedHeaders(List.of("Authorization","Content-Type","recaptcha"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
    
}
