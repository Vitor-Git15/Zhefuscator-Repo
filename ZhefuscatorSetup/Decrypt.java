
import java.io.FileOutputStream;
import java.io.IOException;

import Zhefuscator.Agent.ManipulateRegex;

import java.io.OutputStream;
import java.io.Writer;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;

import java.io.BufferedReader;
import java.io.FileReader;

import Zhefuscator.Agent.CryptoUtils;
import Zhefuscator.Agent.CheckStrings;
public class Decrypt {
    public static void main(String args[]){
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
            CryptoUtils ENC = new CryptoUtils();
            ManipulateRegex MR = new ManipulateRegex();
            CheckStrings CS = new CheckStrings();
            // cria uma linha auxiliar que aponta para próxima linha
            String linha_aux;
            while (linha != null) {
                linha_aux = lerArq.readLine();

                // escreve o texto desencriptado no arquivo
                if (CS.isIPHours(linha, ".*<e>.*</e>.*")){
                    br.write(MR.getGroup(linha, "(.*?)(<e>)(.*)(</e>)(.*)", 1) + 
                    ENC.decrypt(MR.getGroup(linha, "(.*?)(<e>)(.*)(</e>)(.*)", 3)) +
                    MR.getGroup(linha, "(.*?)(<e>)(.*)(</e>)(.*)", 5));
                    br.newLine();
                } else{
                    br.write(linha);
                    br.newLine();
                }
                

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
