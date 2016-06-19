package br.com.barcadero.mensageria.cte;

import java.io.IOException;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPHeaderElement;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

import br.com.barcadero.mensageria.soap.IServices;
import br.com.barcadero.mensageria.soap.SOAPMessageUtil;

public abstract class SuperCTeService implements IServices{

	public SOAPMessage getSOAPMessage(String xMLMessage) throws SOAPException, IOException {
		SOAPMessage soapMessage = SOAPMessageUtil.buildMessage();
		SOAPPart soapPart 		= soapMessage.getSOAPPart();
		SOAPEnvelope envelope 	= soapPart.getEnvelope();
		envelope.addNamespaceDeclaration("xsi", "http://www.w3.org/2001/XMLSchema-instance");
		envelope.addNamespaceDeclaration("xsd", "http://www.w3.org/2001/XMLSchema");
		SOAPHeader soapHeader			= envelope.getHeader();
		SOAPHeaderElement cteCabecMsg	= soapHeader.addHeaderElement(
						new QName("http://www.portalfiscal.inf.br/cte/wsdl/CteRecepcao", "cteCabecMsg"));
		SOAPElement cUF				= cteCabecMsg.addChildElement("cUF");
		cUF.addTextNode("CE");
		SOAPElement versaoDados		= cteCabecMsg.addChildElement("versaoDados");
		versaoDados.addTextNode("2.00");
		
		SOAPBody soapBody 				= envelope.getBody();
		SOAPBodyElement soapBodyElem 	= soapBody.addBodyElement(
						new QName("http://www.portalfiscal.inf.br/cte/wsdl/CteRecepcao","cteDadosMsg")); 
		soapBodyElem.addTextNode( "<![CDATA[" + xMLMessage + "]]>");
		soapMessage.saveChanges();
		soapMessage.writeTo(System.out);
		return soapMessage;
	}
}
