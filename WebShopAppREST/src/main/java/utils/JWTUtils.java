package utils;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;

import models.UserRole;

public class JWTUtils {
	private static final String SECRET = "uNulft/8h3lKq9QKQeZSDRkLDlo0PZSBjJfnLs1bG+Q=";
	
	public static String generateToken(String username, UserRole role) throws JOSEException {
        JWSSigner signer = new MACSigner(SECRET);

        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(username)
                .claim("role", role.toString())
                .expirationTime(new Date(new Date().getTime() + 86400000))
                .build();

        JWSObject jwsObject = new JWSObject(
                new JWSHeader(JWSAlgorithm.HS256),
                new Payload(claimsSet.toJSONObject())
        );

        jwsObject.sign(signer);

        return jwsObject.serialize();
    }
	
	private static String convertAuthorizationHeader(String authorizationHeader)
    {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) 
            return authorizationHeader.substring(7);
        return "";
    }
	
	public static <T> boolean IsRoleCorrect(String authorizationHeader, T role) {
		JWTClaimsSet claimsSet = null;
		
		try {
			claimsSet = JWTUtils.validateToken(convertAuthorizationHeader(authorizationHeader));
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		if(UserRole.valueOf(claimsSet.getClaim("role").toString()) != role)
			return false;
		
		return true;
	}
	
	private static JWTClaimsSet validateToken(String token) throws ParseException, JOSEException {
        JWSObject jwsObject = JWSObject.parse(token);
        JWSVerifier verifier = new MACVerifier(SECRET);

        if (!jwsObject.verify(verifier)) 
            throw new JOSEException("Token signature is invalid");

        return JWTClaimsSet.parse(jwsObject.getPayload().toJSONObject());
    }
}
