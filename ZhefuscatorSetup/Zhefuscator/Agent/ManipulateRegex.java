package Zhefuscator.Agent;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class ManipulateRegex {
  public String getGroup(String str, String regex, int group){
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(str);
    matcher.find();
    return matcher.group(group);
  }
}
