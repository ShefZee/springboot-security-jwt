package com.shefzee.springsecurity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.UUID;

@SpringBootTest
class SpringSecurityApplicationTests {

	private String key = "MyKey";
	private String issuer ="ShefZee";


	@Test
	void contextLoads() {
	}


	@Test
	public void createJWT(){

		String token = generateJWT();
		System.out.println(token);
		Assertions.assertNotNull(token);
	}

	@Test
	public void parseJWT(){

		String jwt = generateJWT();

		Claims claims = parseJWT(jwt);

		System.out.println("Subject = " + claims.getSubject());

		System.out.println("Issuer = " + claims.getIssuer());

		System.out.println("Expiration " + claims.getExpiration());

		System.out.println("Issued At = " + claims.getIssuedAt());
	}

	@Test
	public void isJWTValid_True(){

		String jwt = generateJWT();

		Claims claims = parseJWT(jwt);
		boolean isValid =  !claims.getExpiration().before(new Date());
		Assertions.assertTrue(isValid);
	}

	@Test
	public void isJWTValid_False() throws InterruptedException {

		String jwt = generateJWT();

		Claims claims = parseJWT(jwt);
		Thread.sleep(1000*6);

		boolean isValid =  !claims.getExpiration().before(new Date());
		Assertions.assertFalse(isValid);
	}


	private String generateJWT(){

		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);
		//expiration date
		Date expDate = new Date(nowMillis+(1000*5));

		// The JWT signature algorithm used to sign the token
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

		// sign JWT with our ApiKey secret
		byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(key);
		Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

		return Jwts.builder()
				.setId(UUID.randomUUID().toString())
				.setIssuedAt(now)
				.setSubject("shef1")
				.setIssuer(issuer)
				.signWith(signatureAlgorithm, signingKey)
				.setExpiration(expDate)
				.compact();
	}

	private Claims parseJWT(String jwt){
		return Jwts.
				parser()
				.setSigningKey(DatatypeConverter.parseBase64Binary(key))
				.parseClaimsJws(jwt)
				.getBody();
	}



}
