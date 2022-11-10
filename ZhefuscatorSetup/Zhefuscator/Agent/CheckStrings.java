package Zhefuscator.Agent;

public class CheckStrings {

  public boolean isIPHours(String s, String regex) {
    // grammar
    // tentar converter o regex para um móvel, tornando capaz de alterá-lo ao chamar a função 
    return s.matches(regex);
  }

  public String ofuscator(String s, String regex){
    // sensitive info
    ManipulateRegex MR = new ManipulateRegex();
    Encrypt ENC = new Encrypt();
    s = MR.getGroup(s, regex, 1) +
    "<e>" + ENC.encrypt(MR.getGroup(s, regex, 2)) +
    "<e/>" + MR.getGroup(s, regex, 3);
    return s;
  }
}
