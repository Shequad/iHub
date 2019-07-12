package co.za.openseseme.ihub.net;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class WebService {
    //Namespace of the Webservice - It is <a class="vglnk" href="http://tempuri.org" rel="nofollow"><span>http</span><span>://</span><span>tempuri</span><span>.</span><span>org</span></a> for .NET webservice
    //private static String NAMESPACE = "<a class='vglnk' href='http://tempuri.org/' rel='nofollow'><span>http</span><span>://</span><span>tempuri</span><span>.</span><span>org</span><span>/</span></a>";
    //Webservice URL - It is asmx file location hosted in the server in case of .Net
    //Change the IP address to your machine IP address
    //private static String URL= "<a class='vglnk' href='http://192.168.112.1/WebService/Service.asmx' rel='nofollow'><span>http</span><span>://</span><span>192</span><span>.</span><span>168</span><span>.</span><span>2</span><span>.</span><span>3</span><span>/</span><span>HelloWorldWebService</span><span>/</span><span>Service</span><span>.</span><span>asmx</span></a>";
    //SOAP Action URI again <a class="vglnk" href="http://tempuri.org" rel="nofollow"><span>http</span><span>://</span><span>tempuri</span><span>.</span><span>org</span></a>
    //private static String SOAP_ACTION= "<a class='vglnk' href='http://tempuri.org/' rel='nofollow'><span>http</span><span>://</span><span>tempuri</span><span>.</span><span>org</span><span>/</span></a>";


    public static String SOAP_ACTION = "http://tempuri.org/HelloWorld";

    public  static String OPERATION_NAME = "Add";

    public  static String NAMESPACE = "http://tempuri.org/";

    public  static String URL ="http://evolve-ict.co.za/service.asmx";


    public static String invokeHelloWorldWS(String name, String webMethName) {
        String resTxt = null;
        // Create request
        SoapObject request = new SoapObject(NAMESPACE, webMethName);
        // Property which holds input parameters
        PropertyInfo sayHelloPI = new PropertyInfo();
        // Set Name
        sayHelloPI.setName("name");
        // Set Value
        sayHelloPI.setValue(name);
        // Set dataType
        sayHelloPI.setType(String.class);
        // Add the property to request object
        request.addProperty(sayHelloPI);
        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        //Set envelope as dotNet
        envelope.dotNet = true;
        // Set output SOAP object
        envelope.setOutputSoapObject(request);
        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            androidHttpTransport.debug = true;//need to be set true if it is a .net service

            // Invoke web service
            androidHttpTransport.call(SOAP_ACTION + webMethName, envelope);
            // Get the response
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            // Assign it to resTxt variable static variable
            resTxt = response.toString();

        } catch (Exception e) {
            //Print error
            e.printStackTrace();
            //Assign error message to resTxt
            resTxt = "Error occured " + e.toString();
        }
        //Return resTxt to calling object
        return resTxt;
    }
}
