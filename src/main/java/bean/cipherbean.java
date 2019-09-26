
package application;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Security;
import java.util.Random;
import java.util.prefs.Preferences;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.eclipse.jdt.internal.compiler.batch.Main;

import com.sun.net.ssl.internal.ssl.Provider;

public enum cipherbean {
	INSTANCE;
	 private IvParameterSpec ivParameterSpec;
		private SecretKeySpec secretKeySpec;
		private Cipher cipher;
		private String key;
		private String IV;
		
		
	//this method intializes the above variables
		public void setParameters() throws NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException {
			

			// Get Cipher Instance
			cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

			// Create SecretKeySpec
			secretKeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

			// Create IvParameterSpec
			ivParameterSpec = new IvParameterSpec(IV.getBytes("UTF-8"));
		}

		
	//this is the method for encryption
		public boolean Encrypt(File file) throws javax.crypto.IllegalBlockSizeException, javax.crypto.BadPaddingException,
				java.io.IOException, InvalidKeyException, InvalidAlgorithmParameterException {
			File destinationFile;
			
			String path = file.getAbsolutePath().replace("/", "//");//get path of file

			cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);

			destinationFile = new File(path.concat(".enc"));//creation of new file with .enc extension 
			if (destinationFile.exists()) {
				destinationFile.delete();
				destinationFile = new File(path.concat(".enc"));
			}

			BufferedInputStream in = new BufferedInputStream(new FileInputStream(path));//read from this file
	        FileOutputStream out = new FileOutputStream(destinationFile, true);//write to this file

			byte[] ibuf = new byte[1024];
			int len;
			while ((len = in.read(ibuf)) != -1) {
				byte[] obuf = cipher.update(ibuf, 0, len);
				if (obuf != null)
					out.write(obuf);
			}
			
			byte[] obuf = cipher.doFinal();
			if (obuf != null)
				out.write(obuf);//encrypted file is created
			in.close();
			out.close();
			
			File outFile = new File(path);
			if (outFile.exists()) {
				outFile.delete();//original file is deleted
			}

			
			return true;
		}

		

		//this method is used for decryption of files
		public boolean Decrypt(File file)

				throws javax.crypto.IllegalBlockSizeException, javax.crypto.BadPaddingException, java.io.IOException,
				InvalidKeyException, InvalidAlgorithmParameterException {

			File destinationFile;
			
			cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);

			String path = file.getAbsolutePath().replace("/", "//");
			
			destinationFile = new File(path.replaceAll(".enc",""));
			if (destinationFile.exists()) {
				destinationFile.delete();
				destinationFile = new File(path.replaceAll(".enc",""));
			}

			BufferedInputStream in = new BufferedInputStream(new FileInputStream(path));//read from encrypted file

			FileOutputStream out = new FileOutputStream(destinationFile, true);//write to original file

			byte[] ibuf = new byte[1024];
			int len;
			while ((len = in.read(ibuf)) != -1) {
				byte[] obuf = cipher.update(ibuf, 0, len);
				if (obuf != null)
					out.write(obuf);
			}
			byte[] obuf = cipher.doFinal();
			if (obuf != null) {
				out.write(obuf);//file is decrypted
			}
		   in.close();
			out.close();
			
	        File outFile = new File(path);
			if (outFile.exists()) 
				outFile.delete();//encrypted file is deleted
		return true;
			}

		//this method is for password hashing
		public String getSecurePassword(String passwordToHash, byte[] salt)

		{
			String newPassword = null;
			MessageDigest md;
			try {
				// Select the message digest for the hash computation -> SHA-256
				md = MessageDigest.getInstance("SHA-256");

				// Passing the salt to the digest for the computation
				md.update(salt);

				// Generate the salted hash
				byte[] hashedPassword = md.digest(passwordToHash.getBytes(StandardCharsets.UTF_8));

				StringBuilder sb = new StringBuilder();
				for (byte b : hashedPassword) {
					sb.append(String.format("%02x", b));
				}
				newPassword = sb.toString();
				// System.out.println(newPassword);
			} catch (NoSuchAlgorithmException e) {
			
				e.printStackTrace();
			}
			return newPassword;
		}

	}



