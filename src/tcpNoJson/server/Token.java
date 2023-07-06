package tcpNoJson.server;

import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.util.Base64;
import java.util.Date;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Token {
	
	public static boolean check(String token, int UID, Timestamp time) throws Exception {
		// Base64 decode
		byte[] encrypted = Base64.getDecoder().decode(token);
		SecretKeySpec keySpec = new SecretKeySpec(String.format("%016d", UID).getBytes(), "AES");
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.DECRYPT_MODE, keySpec);

		// cipher => byteBuffer
		byte[] decrypted = cipher.doFinal(encrypted);
		ByteBuffer bb = ByteBuffer.wrap(decrypted);
		long timeInMillis = bb.getLong();

		// token.millis compare => System.millis
		if (System.currentTimeMillis() - timeInMillis <= 5*60*1000) return true;
		else return false;	// cookie:token outdate => need update(5min) => TODO: config(ºÁÃë)
	}
	
	// AES encode: KEY(16b UID) => DATA(time) => Base64
	public static String update(int UID, Timestamp time) throws Exception {
		
		SecretKeySpec keySpec = new SecretKeySpec(String.format("%016d", UID).getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        
        // byteBuffer => cipher
        ByteBuffer bb = ByteBuffer.allocate(Long.BYTES).putLong(time.getTime());
        byte[] encrypted = cipher.doFinal(bb.array());
        // Base64 encode
        return Base64.getEncoder().encodeToString(encrypted);
	}
}
