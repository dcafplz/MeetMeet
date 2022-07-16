package meetmeet.model.dto;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;

import meetmeet.model.entity.Account;

public class PwSecurity {
	
	public static boolean checkPw(Account account, String pw) throws Exception {
		
		return account.getPw().contentEquals(hashing(pw, account.getHashSalt()));

	}
	
	public static String hashing(String pw, String salt) throws NoSuchAlgorithmException {
		
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		byte[] digest = md.digest((pw+salt).getBytes(StandardCharsets.UTF_8));
		String sha256 = DatatypeConverter.printHexBinary(digest).toLowerCase();
		
		return sha256;
	}

}
