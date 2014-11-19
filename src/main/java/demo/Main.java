package demo;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.model.*;
import org.apache.commons.codec.binary.Base64;

import java.io.InputStream;
import java.util.List;

public class Main {
    public static void main(String[] args)throws Exception {
        // CONNECT TO EC2

        InputStream credentialsAsStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("AwsCredentials.properties");
        Preconditions.checkNotNull(credentialsAsStream, "File 'AwsCredentials.properties' NOT found in the classpath");
        AWSCredentials credentials = new BasicAWSCredentials("XXX","XXX");// new PropertiesCredentials(credentialsAsStream);

        AmazonEC2 ec2 = new AmazonEC2Client(credentials);
        ec2.setEndpoint("ec2.us-east-1.amazonaws.com");

// CREATE EC2 INSTANCES
        RunInstancesRequest runInstancesRequest = new RunInstancesRequest()
                .withInstanceType("t1.micro")
                .withImageId("ami-8caa1ce4")
                .withMinCount(2)
                .withMaxCount(2)
                .withSecurityGroupIds("la-dev-group")
                .withKeyName("la-dev-20141112")
               // .withUserData(Base64.encodeBase64String(myUserData.getBytes()))
                ;

        RunInstancesResult runInstances = ec2.runInstances(runInstancesRequest);

// TAG EC2 INSTANCES
        List<Instance> instances = runInstances.getReservation().getInstances();
        int idx = 1;
        for (Instance instance : instances) {
            CreateTagsRequest createTagsRequest = new CreateTagsRequest();
            createTagsRequest.withResources(instance.getInstanceId()) //
                    .withTags(new Tag("Name", "travel-ecommerce-" + idx));
            ec2.createTags(createTagsRequest);

            idx++;
        }
    }
}
