import java.net.URI;
import java.net.URISyntaxException;

public class Main {

    public static void main(String[] args) throws URISyntaxException {

        String protocol = "http";
        String domain = "google.com";
        String company = "localdev";

        System.out.println(new URI(protocol, domain.replaceAll("\\*", company), "/", null).toString());
    }


}
