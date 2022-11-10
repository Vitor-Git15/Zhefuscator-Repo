package Zhefuscator.Agent;

import java.security.Key;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import java.io.BufferedReader;
import java.io.FileReader;

public class Encrypt {
    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES";

    //realiza a encriptação dos dados com base em uma chave passada
    public String doCrypto(String text, String key) {
        try {
            // cria a chave e a cifra
            Key aesKey = new SecretKeySpec(key.getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            
            // encripta o texto
            cipher.init(Cipher.ENCRYPT_MODE, aesKey);
            byte[] encrypted = cipher.doFinal(text.getBytes());
            //tentar converter para base64 aqui
            StringBuilder sb = new StringBuilder();
            for (byte b : encrypted) {

                //sb.append(Byte.parseByte(b));
                sb.append((char) b);
            }

            // transoforma o texto encriptado em string
            String enc = sb.toString();
            return Base64.getEncoder().encodeToString(enc.getBytes());

        } catch (Exception e) {
            return "";
        }
    }
    //realiza a encriptação dos dados com base na chave presente em um arquivo
    public String encrypt(String text) {
        try { 
            FileReader arq = new FileReader("/home/vitor/Iniciação Científica/ZhefuscatorSetup/Zhefuscator/Texts/key.txt");
            BufferedReader lerArq = new BufferedReader(arq);
            String key = lerArq.readLine(); // 128 bit key

            arq.close();
            return doCrypto(text,  key);

        } catch (Exception e) {
            return "";
        }
    }
}
