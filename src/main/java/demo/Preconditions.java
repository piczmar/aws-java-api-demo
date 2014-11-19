package demo;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: Marcin.Piczkowski
 * Date: 11/19/14
 * Time: 6:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class Preconditions {
    public static void checkNotNull(InputStream credentialsAsStream, String errorMsg) throws Exception {
        Properties properties = new Properties();
        properties.load(credentialsAsStream);

        if (properties.get("accessKey") == null || properties.get("secretKey") == null) {
            throw new Exception(errorMsg);
        }

        System.out.println("accessKey="+properties.get("accessKey") );
        System.out.println("secretKey="+properties.get("secretKey") );
    }
}
