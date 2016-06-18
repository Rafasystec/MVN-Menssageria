package br.com.barcadero.mensageria.soap;

import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

public interface IServices {
	
	public String getURLService(EnumTpAmbiente tpAmb);
	public String getNameSpaceDeclaration();
	public SOAPMessage buildSoapMessage(String xMLMessage, SOAPMessage sOPAMessage) throws SOAPException;

}
