package Zhefuscator.Agent;

import java.security.Key;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import java.io.BufferedReader;
import java.io.FileReader;

public class CryptoUtils{

  private static final String ALGORITHM = "AES";
  private static final String TRANSFORMATION = "AES";

  public static StringBuilder doStringBuilder(byte[] outputBytes){
    StringBuilder sb = new StringBuilder();
    for (byte b : outputBytes) {
        sb.append((char) b);
    }
    return sb;
  }
  public static byte[] doByteBuilder(String enc){
    byte[] bb = new byte[enc.length()];
    for (int i = 0; i < enc.length(); i++) {
      //converter para byte antes de anexar
      bb[i] = (byte) enc.charAt(i);
                
    }
    return bb;
  }
  public static String getKey(){
    try { 
      FileReader arq = new FileReader("Zhefuscator/Texts/key.txt");
      BufferedReader lerArq = new BufferedReader(arq);
      String key = lerArq.readLine(); // 128 bit key

      arq.close();
      return key;

    } catch (Exception e) {
      return "";
    }
  }
  public String encrypt(String key, String text){
    return doCrypto(Cipher.ENCRYPT_MODE, key, text);
  }
  public String encrypt(String text){
    return doCrypto(Cipher.ENCRYPT_MODE, getKey(), text);
  }

  public String decrypt(String key, String text){
    return doCrypto(Cipher.DECRYPT_MODE, key, text);
  }
  public String decrypt(String text){
    return doCrypto(Cipher.DECRYPT_MODE, getKey(), text);
  }
  
  private static String doCrypto(int cipherMode, String key, String text){
    try{

      Key aesKey = new SecretKeySpec(key.getBytes(), ALGORITHM);
      Cipher cipher = Cipher.getInstance(TRANSFORMATION);
      cipher.init(cipherMode, aesKey);

      String outString = text;

      if(cipherMode == Cipher.DECRYPT_MODE){
        outString = new String(Base64.getDecoder().decode(text));
      }

      byte[] outputBytes = new byte[outString.length()];

      if(cipherMode == Cipher.ENCRYPT_MODE){
        byte[] inputBytes = cipher.doFinal(text.getBytes());
        outString = doStringBuilder(inputBytes).toString();
        outString = Base64.getEncoder().encodeToString(outString.getBytes());
      }

      else if(cipherMode == Cipher.DECRYPT_MODE){
        outputBytes = doByteBuilder(outString);
        outString = new String(cipher.doFinal(outputBytes));
      }

      return outString;
    }
    catch(Exception e){
      return "#ERRO#";
    }
  }
}
