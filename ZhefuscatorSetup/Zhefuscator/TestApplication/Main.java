package Zhefuscator.TestApplication;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.Writer;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;

import Zhefuscator.Agent.CheckStrings;
// classe principal, realiza os testes 

public class Main {
  // realiza os testes de ofuscação de partes sensíveis
  public static void main(String[] args) {
    try {
      //abre o arquivo com os dados a serem ofuscados
      FileReader arq = new FileReader("Zhefuscator/Texts/original.txt");
      BufferedReader lerArq = new BufferedReader(arq);

      //abre o arquivo para armazenar dados ofuscados
      OutputStream os = new FileOutputStream(
          "Zhefuscator/Texts/encrypted.txt");
      Writer wr = new OutputStreamWriter(os);
      BufferedWriter br = new BufferedWriter(wr);

      //pega a primeira linha do arquivo de leitura
      String linha = lerArq.readLine();

      while (linha != null) {
        //printa o dados originais + dados ofuscados
        System.out.println(linha);

        //insere os dados ofuscados em um arquivo
        CheckStrings CS = new CheckStrings();
        if (CS.isIPHours(linha, ".*\\d{1,3}[.]\\d{1,3}[.]\\d{1,3}[.]\\d{1,3}\\s+\\d{1,2}h\\d{1,2}.*")) {
          br.write(CS.ofuscator(linha, "(.*?)(\\d{1,3}[.]\\d{1,3}[.]\\d{1,3}[.]\\d{1,3})(.*)"));
          br.newLine();
        } else{
          br.write(linha);
          br.newLine();
        }
        

        //lê a proxima linha
        linha = lerArq.readLine();
      }

      //fecha os arquivos
      arq.close();
      br.close();

      //caso exista algum problema na abertura dos arquivos
    } catch (IOException e) {
      System.err.printf("Erro na abertura do arquivo: %s.\n",
          e.getMessage());
    }
  }
}