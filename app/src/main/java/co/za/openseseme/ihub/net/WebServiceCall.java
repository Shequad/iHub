package co.za.openseseme.ihub.net;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class WebServiceCall {
    String namespace = "http://tempuri.org/";

    private String url = "http://evolve-ict.co.za/service.asmx";

    String SOAP_ACTION;

    SoapObject request = null, objMessages = null;

    SoapSerializationEnvelope envelope;

    HttpTransportSE androidHttpTransport;


    public String getSoapResponse(String MethodName)

    {

        try {
            SOAP_ACTION = namespace + MethodName;

//Adding values to request object

            request = new SoapObject(namespace, MethodName);


//Adding String value to request object (User name in this case)



//Adding String value to request object (password in this case)

            PropertyInfo mPassword =new PropertyInfo();

            mPassword.setName("name");

            mPassword.setValue("122");

            mPassword.setType(String.class);

            request.addProperty(mPassword);



// Creating SOAP envelope

            envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

//You can comment that line if your web service is not .NET one.

            envelope.dotNet = true;

            envelope.setOutputSoapObject(request);

            androidHttpTransport = new HttpTransportSE(url);

            androidHttpTransport.debug = true;//need to be set true if it is a .net service

//SOAP calling webservice

            androidHttpTransport.call(SOAP_ACTION, envelope);//making the soap call here

// Web service response

            String result = envelope.getResponse().toString();//getting the service response here

            return result;

        }

        catch (Exception e) {

// TODO: handle exception

            return e.toString();


        }

    }

}
