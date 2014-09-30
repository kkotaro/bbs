package model;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
public class HashPass {
	public String encryptionSha(String userPass) throws NoSuchAlgorithmException {
		MessageDigest md = null;
		md = MessageDigest.getInstance("SHA-256");
		md.update(userPass.getBytes());
		StringBuilder sb = new StringBuilder();
		for (byte digest : md.digest()) {
			String hex = String.format("%02x", digest);
			sb.append(hex);
		}
		return sb.toString();
	}
}