package HttpProject;

import java.net.URI;

public class URIBasics {

    public static void main(String[] args) {

        URI timsSite=URI.create("https://learnprogramming.academy/courses/complete-java-masterclass");
        print(timsSite);
    }

    private static void print(URI uri){
        System.out.printf(
                """
                        ---------------------------------------------
                        [scheme:]scheme-specific-part[#fragment]
                        ---------------------------------------------
                        Scheme:%s
                        Scheme-specific part :%s
                            Authority: %s
                                User info: %s
                                Host: %s
                                Port: %d
                                Path: %s
                                Query: %s
                        Fragment: %s
                        """,
                uri.getScheme(),
                uri.getSchemeSpecificPart(),
                uri.getAuthority(),
                uri.getUserInfo(),
                uri.getHost(),
                uri.getPort(),
                uri.getPath(),
                uri.getQuery(),
                uri.getFragment()
        );
    }
}
