package db_files.JavaBD.src.jmysql;


import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Programa {

	public static void main(String[] args)  throws NoSuchAlgorithmException,
			UnsupportedEncodingException {
//		Utils.menu();
		//-------------- Senha Admin
		String senhaAdmin = "admin1";

		MessageDigest algorithm = MessageDigest.getInstance("SHA-256");
		byte messageDigestSenhaAdmin[] = algorithm.digest(senhaAdmin.getBytes("UTF-8"));

		StringBuilder hexStringSenhaAdmin = new StringBuilder();
		for (byte b : messageDigestSenhaAdmin) {
			hexStringSenhaAdmin.append(String.format("%02X", 0xFF & b));
		}
		String senhahexAdmin = hexStringSenhaAdmin.toString();

		System.out.println(senhahexAdmin);
	}
}
