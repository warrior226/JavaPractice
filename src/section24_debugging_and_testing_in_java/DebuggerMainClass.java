package section24_debugging_and_testing_in_java;

import com.mysql.cj.util.StringUtils;

public class DebuggerMainClass {
    public static void main(String[] args) {

        StringUtililies utils = new StringUtililies();
        StringBuilder sb = new StringBuilder();
        while(sb.length()<10){
            utils.addChar(sb,'a');
        }
        System.out.println(sb);
    }
}
