package spring.md5;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;


public class Md5Test {

   public static void main(String[] args) {
	   
	   
	   //System.out.println(md5Hash("ILoveJava"));
	   String s="757fd4dcdebe950d98c4234ab1d556db3452aad9002d955c6083ccd8e41ccb99";
	   String s1= s.substring(0, 32);
	   String s2= s.substring(32, 64);
	   System.out.println(s1+"=="+s2);
	   System.out.println(md5Hash("ILoveJava", s2));
	   
	   //String Url = "ILoveJava";
	   //System.out.println( DigestUtils.md5Hex(Url));
   }
   
   public static String md5Hash(String str) {
	   
	   String plaintext = str;
	   String hashtext=null;
	   byte[] salt=null;
	   try {
		   salt = getSalt();
	   } catch (NoSuchAlgorithmException e1) {
		   e1.printStackTrace();
	   }
	   
	   String saltStr= Hex.encodeHexString(salt);  //convert byte[] to hex string
	   try {
		   final MessageDigest m = MessageDigest.getInstance("MD5");
		   m.reset();
		   m.update(salt);  //add salt
		   byte[] digest = m.digest(plaintext.getBytes(Charset.forName("UTF-8")));
		   BigInteger bigInt = new BigInteger(1,digest);
		   hashtext= bigInt.toString(16);		   
	   } catch (NoSuchAlgorithmException e) {
		   e.printStackTrace();
	   }
	   return hashtext+saltStr;  //return hash+salt 64 byte hex string
   }
   
   public static String md5Hash(String str, String saltStr) {   //decrypt md5
	   
	   String plaintext = str;
	   String hashtext=null;
	   byte[] salt=null;
	   try {
		   salt = Hex.decodeHex(saltStr);    //restore hex to byte[] salt
	   } catch (DecoderException e1) {
		   e1.printStackTrace();
	   }
	   
	   try {
		   final MessageDigest m = MessageDigest.getInstance("MD5");
		   m.reset();
		   m.update(salt);  //add salt
		   byte[] digest = m.digest(plaintext.getBytes(Charset.forName("UTF-8")));
		   BigInteger bigInt = new BigInteger(1,digest);
		   hashtext= bigInt.toString(16);		   
	   } catch (NoSuchAlgorithmException e) {
		   e.printStackTrace();
	   }
	   return hashtext;
   }
   
   private static byte[] getSalt() throws NoSuchAlgorithmException
   {
       SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
       byte[] salt = new byte[16];
       sr.nextBytes(salt);
       return salt;
   }
}

	
