
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import Zhefuscator.Agent.ManipulateRegex;

import java.io.OutputStream;
import java.io.Writer;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;

import java.io.BufferedReader;
import java.io.FileReader;

import java.util.Base64;

public class Decrypt {
    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES";
    // realiza a desencriptação dos dados com base em uma chave passada
    public static String doDecrypt(String text, String key) {
        try {
            // cria a chave e a cifra
            Key aesKey = new SecretKeySpec(key.getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            
            // seleciona a parte ofuscada, dentro de <e>'s
            ManipulateRegex MR = new ManipulateRegex();

            String encrypt = MR.getGroup(text, "(.*?)(<e>)(.*)(<e/>)(.*)" , 3);
            String enc = new String(Base64.getDecoder().decode(encrypt));
            // converte a string em um array de byte para desencriptação
            //Verificar perda de caracteres de char para bytes 
            byte[] bb = new byte[enc.length()];
            for (int i = 0; i < enc.length(); i++) {
                //converter para byte antes de anexar
                bb[i] = (byte) enc.charAt(i);
                
            }
            // desencripta o texto
            cipher.init(Cipher.DECRYPT_MODE, aesKey);
            String decrypted = new String(cipher.doFinal(bb));

            String group1, group5;
            group1 = MR.getGroup(text, "(.*?)(<e>)(.*)(<e/>)(.*)" , 1);
            group5 = MR.getGroup(text, "(.*?)(<e>)(.*)(<e/>)(.*)" , 5);
            //retorna o texto com a parte desencriptada
            return group1 + decrypted + group5;

        } catch (Exception e) {
            //retorna o texto original caso não seja desencriptado
            return text + "#FAIL";
        }
    }

    public static String decrypt(String text) {
        // realiza a desencriptação dos dados com base na chave de um arquivo
        try {
            // abre o arquivo que contém a chave
            FileReader arq = new FileReader(
                    "/home/vitor/Iniciação Científica/ZhefuscatorSetup/Zhefuscator/Texts/key.txt");
            BufferedReader lerArq = new BufferedReader(arq);
            // obtém a chave
            String key = lerArq.readLine(); // 128 bit key
            lerArq.close();
            // retorna o texto desencriptado
            return doDecrypt(text, key);

        } catch (Exception e) {
            // retorna o texto original, caso não obtenha sucesso
            return "";
        }
    }

    public static void main(String args[]) {

        try {
            // abre o arquivo com texto encriptado
            FileReader arq = new FileReader(args[0]);
            BufferedReader lerArq = new BufferedReader(arq);

            // abre o arquivo para imprimir o texto desencripitado
            OutputStream os = new FileOutputStream(args[1]);
            Writer wr = new OutputStreamWriter(os);
            BufferedWriter br = new BufferedWriter(wr);

            // lê a primeira linha
            String linha = lerArq.readLine();

            // cria uma linha auxiliar que aponta para próxima linha
            String linha_aux;
            while (linha != null) {
                linha_aux = lerArq.readLine();

                while (linha_aux != null) {

                    // caso a proxima linha não faça parte do texto da primeira
                    if (linha_aux.matches("^>>.*"))
                        break;

                    // caso a proxima linha faça parte do texto da primeira
                    else
                        linha += linha_aux;

                    linha_aux = lerArq.readLine();
                }

                // escreve o texto desencriptado no arquivo
                br.write(decrypt(linha));
                br.newLine();

                // lê da segunda até a última linha
                linha = linha_aux;
            }

            // fecha os arquivos de entrada e saída
            br.close();
            arq.close();
        } catch (IOException e) {
            // caso ocorra um erro na execução do programa
            System.err.printf("Erro na abertura do arquivo: %s.\n",
                    e.getMessage());
        }
    }
}
