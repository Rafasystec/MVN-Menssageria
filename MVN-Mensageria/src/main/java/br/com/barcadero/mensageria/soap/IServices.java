package br.com.barcadero.mensageria.soap;

import java.io.IOException;

import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

public interface IServices {
	@Deprecated
	public String getURLService(EnumTpAmbiente tpAmb);
	public String getURLService();
	public String getNameSpaceDeclaration();
	public SOAPMessage buildSoapMessage(String xMLMessage, SOAPMessage sOPAMessage) throws SOAPException;
	public SOAPMessage buildSoapMessage(String xMLMessage) throws SOAPException, IOException;
}
