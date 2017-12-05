package com.example.phanv.camera.Model.ServerModel;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.List;

/**
 * Created by phanv on 17-Nov-17.
 */

public class ConnectServer {
    private final String WSDL_TARGET_NAMESPACE = "http://tempuri.org/";
    private final String SOAP_ADDRESS = "http://cameradn.somee.com/Camera.asmx";

    public ConnectServer() {
    }

    public String processString(List<Property> list, final String soap, final String operation) {
        String result = "";
        try {
            SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, operation);
            PropertyInfo pi;
            for (Property property : list
                    ) {
                pi = new PropertyInfo();
                pi.setName(property.getName());
                pi.setValue(property.getValue());
                pi.setType(String.class);
                request.addProperty(pi);
            }
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS);
            httpTransport.call(soap, envelope);
            result = envelope.getResponse().toString();
        } catch (Exception exception) {
            result = "er";
        }
        return result;
    }

    public SoapObject process(List<Property> list, final String soap, final String operation) {
        SoapObject result;
        try {
            SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, operation);
            PropertyInfo pi;
            for (Property property : list
                    ) {
                pi = new PropertyInfo();
                pi.setName(property.getName());
                pi.setValue(property.getValue());
                pi.setType(String.class);
                request.addProperty(pi);
            }
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS);
            httpTransport.call(soap, envelope);
            result = (SoapObject) envelope.getResponse();
        } catch (Exception exception) {
            result = null;
        }
        return result;
    }

}
