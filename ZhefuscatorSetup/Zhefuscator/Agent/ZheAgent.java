package Zhefuscator.Agent;

import java.lang.instrument.Instrumentation;

public class ZheAgent {
    public static void premain(String args, Instrumentation inst) {
      System.setOut(new CustomPrintStream(System.out));
      System.out.println("Hello from Agent!");
    }
  }
