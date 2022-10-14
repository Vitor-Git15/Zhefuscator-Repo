package Zhefuscator.Agent;

public class CheckStrings {

  public String Hide(String S) {

    String S_aux = "Password:";
    int size = S_aux.length();
    for (int i = 0; i < (S.length() - size); i++) {
      S_aux += "*";
    }

    return S_aux;
  }

  public boolean IsPasswords(String str) {
    return str.contains("Password:");
  }
  public String ConvertSpaces(String S){
    S = S.replace(' ', '_');
    return S;
  }
}
