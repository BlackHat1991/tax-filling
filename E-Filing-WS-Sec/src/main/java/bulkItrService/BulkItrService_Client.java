package bulkItrService;

import java.io.File;
import java.net.URL;
import java.util.Random;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;

import org.apache.cxf.Bus;
import org.apache.cxf.BusFactory;
import org.apache.cxf.bus.spring.SpringBusFactory;

public final class BulkItrService_Client {

    private BulkItrService_Client() {
    }

    public static void main(String argsMme[]) throws Exception {
    	
    	String xmlFileName = "ClientConfig.xml";

        URL wsdlURL = new URL("https://services.incometaxindiaefiling.gov.in/e-FilingSecWS/ditsecws/BulkItrService?wsdl");

        SpringBusFactory bf = new SpringBusFactory();
        
        File busFile = new File("bulkItrService/ClientConfig.xml");

        Bus bus = bf.createBus(busFile.toString());
        BusFactory.setDefaultBus(bus);
        
        FileDataSource fileDataSource = new FileDataSource(xmlFileName);
        DataHandler itrXMLFileData = new DataHandler(fileDataSource);

        System.out.println(wsdlURL);
        BulkItrService_Service ss = new BulkItrService_Service();
        BulkItrService port = ss.getPort(BulkItrService.class);

        System.out.println("Invoking BulkItrService...");
        try {

        	BulkItrRequest ditRequest = new BulkItrRequest();
        	//String uniqueID = UUID.randomUUID().toString();
        	
        	ditRequest.setUniqueRequestId("ERIA101853-"+getRandomNumber());
        	ditRequest.setDataHandler(itrXMLFileData);
        	
        	BulkItrResponse resp = port.uploadBulkItr(ditRequest);

            System.out.println("Server responded with: " + resp);
            System.out.println();

        } catch (Exception e) {
            System.out.println("Invocation failed with the following: " + e.getCause());
            System.out.println();
            e.printStackTrace();
        }

        System.exit(0);
    }
    
    private static String getRandomNumber() {
        Random rnd = new Random();
        int n = 100000 + rnd.nextInt(900000);
        
        return String.valueOf(n);
      }

}

