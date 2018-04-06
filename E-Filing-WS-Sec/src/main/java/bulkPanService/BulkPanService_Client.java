package bulkPanService;

import java.io.File;
import java.net.URL;

import org.apache.cxf.Bus;
import org.apache.cxf.BusFactory;
import org.apache.cxf.bus.spring.SpringBusFactory;

public final class BulkPanService_Client {

    private BulkPanService_Client() {
    }

    public static void main(String argsMme[]) throws Exception {

        URL wsdlURL = new URL("https://services.incometaxindiaefiling.gov.in/e-FilingSecWS/ditsecws/BulkPanService?wsdl");

        SpringBusFactory bf = new SpringBusFactory();
        
        File busFile = new File("bulkPanService/ClientConfig.xml");

        Bus bus = bf.createBus(busFile.toString());
        BusFactory.setDefaultBus(bus);

        System.out.println(wsdlURL);
        BulkPanService_Service ss = new BulkPanService_Service();
        BulkPanService port = ss.getPort(BulkPanService.class);

        System.out.println("Invoking bulkPanService...");
        try {

        	BulkPanRequest ditRequest = new BulkPanRequest();
        	BulkPanResponse resp = port.uploadBulkPan(ditRequest);

            System.out.println("Server responded with: " + resp);
            System.out.println();

        } catch (Exception e) {
            System.out.println("Invocation failed with the following: " + e.getCause());
            System.out.println();
            e.printStackTrace();
        }

        System.exit(0);
    }

}
