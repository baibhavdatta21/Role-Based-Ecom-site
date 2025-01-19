package Spb.Ecom.security;

import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
	private String secretKey="Iax5+gIYQsITpUJLZLLMMKeAhnDzo6S6y9DUnlld6F4kB89cG80ZlJpX3vXTSO8a9vvzvwkN3C+8SZryP17ESQ==";
    public JwtUtil(){
        try{
        	System.out.println("In 1 "+secretKey);
        	
            KeyGenerator keyGenerator=KeyGenerator.getInstance("HmacSHA512");
            SecretKey sk=keyGenerator.generateKey();  //to genretate key till this part is enough
            
            System.out.println("In 1.5 "+sk);
            
            secretKey=Base64.getEncoder().encodeToString(sk.getEncoded());
            System.out.println("In 2 "+secretKey);
        }catch (NoSuchAlgorithmException e){
            throw new RuntimeException();
        }

    }
    public String  generateToken(UserDetails userDetails){
    	Map<String, Object> claims = new HashMap<>();
        // Extract role names from granted authorities
//        List<Map<String, String>> roles = userDetails.getAuthorities().stream()
//                .map(authority -> Map.of("authority", authority.getAuthority())) // Create a map for each authority
//                .collect(Collectors.toList()); // Collect to a List of maps
        claims.put("roles", userDetails.getAuthorities()); // Put roles as a List in claims
        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 60*60*30*30))
                .and()
                .signWith(getKey())
                .compact();

    }

    private SecretKey getKey() {
        byte [] keyBytes= Decoders.BASE64.decode(secretKey);
        System.out.println("In 3 "+Keys.hmacShaKeyFor(keyBytes));
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }
//    public List<String> extractRoles(String token) {
//        // This method retrieves the roles from the token
//        final Claims claims = extractAllClaims(token);
//        // Extract the roles, mapping them to their authority values
//        List<Map<String, String>> rolesList = claims.get("roles", List.class);
//        return rolesList.stream()
//                .map(role -> role.get("authority")) // Extract authority from each role
//                .collect(Collectors.toList()); // Collect to a List of role names
//    }
    private <T> T extractClaim(String token, Function<Claims,T> claimResolver){
        final Claims claims=extractAllClaims(token);
        //List<String> roles = extractRoles(token);
        //System.out.println("Extracted roles: " + roles);
        return claimResolver.apply(claims);
    }
    private Claims extractAllClaims(String token){
        return Jwts.parser().verifyWith(getKey()).build().parseSignedClaims(token).getPayload();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String username=extractUserName(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }
    private boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }
    private Date extractExpiration(String token){
        return extractClaim(token,Claims::getExpiration);
    }
}
