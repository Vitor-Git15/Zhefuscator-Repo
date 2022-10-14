package Zhefuscator.TestApplication;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("\n  Bem Vindo   \nFaça seu login");

        Scanner in = new Scanner(System.in);

        System.out.print("username: ");
        String user = in.nextLine();

        System.out.print("password: ");
        String password = in.nextLine();

        System.out.println("Logado com sucesso");
        System.out.println("User: " + user);
        System.out.println("Password: " + password);

        in.close();
    }
}