package com.app;
import java.security.SecureRandom;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BajajApplication {

	public static void main(String[] args) {
		if(args.length < 2) {
			return;
		}
		String prnNumber = args[0];
        String jsonFilePath = args[1];
		SpringApplication.run(BajajApplication.class, args);
		String destinationKeyValue = JsonUtility.findDestinationValue(jsonFilePath);
		String randomAlphNumStr = generateRandomString(8);
		String concatenatedString = prnNumber + jsonFilePath + randomAlphNumStr;
		String mdHashFinalStr = generteMDHashCode(concatenatedString);
		String finalString = mdHashFinalStr + ";" + randomAlphNumStr;
		System.out.println(finalString);
	}
	
	
	public static String generateRandomString(int length) {
		String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(str.length());
            sb.append(str.charAt(randomIndex));
        }
        return sb.toString();
    }
	
	public static String generteMDHashCode(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(input.getBytes());
            byte[] digest = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

}
