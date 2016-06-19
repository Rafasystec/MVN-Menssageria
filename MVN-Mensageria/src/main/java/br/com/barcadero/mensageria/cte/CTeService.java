package br.com.barcadero.mensageria.cte;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPHeaderElement;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import br.com.barcadero.mensageria.soap.EnumTpAmbiente;
import br.com.barcadero.mensageria.soap.IServices;
import br.com.barcadero.mensageria.soap.SOAPMessageUtil;

public class CTeService implements IServices {

	public String getURLService(EnumTpAmbiente tpAmb) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getNameSpaceDeclaration() {
		// TODO Auto-generated method stub
		return "";
	}
	/**
	 * <?xml version="1.0" encoding="UTF-8"?>
<soap12:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap12="http://www.w3.org/2003/05/soap-envelope">
	<soap12:Header>
		<cteCabecMsg xmlns="http://www.portalfiscal.inf.br/cte/wsdl/CteRecepcao">
			<cUF>string</cUF>
			<versaoDados>string</versaoDados>
		</cteCabecMsg>
	</soap12:Header>
	<soap12:Body>
		<cteDadosMsg xmlns="http://www.portalfiscal.inf.br/cte/wsdl/CteRecepcao">xml</cteDadosMsg>
	</soap12:Body>
</soap12:Envelope>
	 */
	public SOAPMessage buildSoapMessage(String xMLMessage, SOAPMessage sOPAMessage) throws SOAPException {
		SOAPMessage soapMessage = SOAPMessageUtil.buildMessage();
		SOAPPart soapPart 		= soapMessage.getSOAPPart();
		SOAPEnvelope envelope = soapPart.getEnvelope();
		envelope.addNamespaceDeclaration("", getNameSpaceDeclaration());
		SOAPHeader soapHeader		= envelope.getHeader();
		SOAPElement cteCabecMsg		= soapHeader.addChildElement("cteCabecMsg", "");
		SOAPElement cUF				= cteCabecMsg.addChildElement("cUF");
		
		SOAPBody soapBody 			= envelope.getBody();
		SOAPElement soapBodyElem 	= soapBody.addChildElement("cteCabecMsg", "");
		SOAPElement soapBodyElem1 	= soapBodyElem.addChildElement("", "");
		soapBodyElem1.addTextNode(xMLMessage);
		MimeHeaders headers 		= sOPAMessage.getMimeHeaders();
		// headers.addHeader("SOAPAction", "http://tempuri.org/Enviar");
		sOPAMessage.saveChanges();
		return soapMessage;
	}

	public static void main(String[] args) {
		try {
			SOAPMessage msg = new CTeService().buildSoapMessage("XmlDoCTe");
		} catch (SOAPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public SOAPMessage buildSoapMessage(String xMLMessage) throws SOAPException, IOException {
		SOAPMessage soapMessage = SOAPMessageUtil.buildMessage();
		SOAPPart soapPart 		= soapMessage.getSOAPPart();
		SOAPEnvelope envelope 	= soapPart.getEnvelope();
		envelope.addNamespaceDeclaration("xsi", "http://www.w3.org/2001/XMLSchema-instance");
		envelope.addNamespaceDeclaration("xsd", "http://www.w3.org/2001/XMLSchema");
		//envelope.addNamespaceDeclaration("", "http://www.portalfiscal.inf.br/cte/wsdl/CteRecepcao");
		SOAPHeader soapHeader			= envelope.getHeader();
		SOAPHeaderElement cteCabecMsg	= soapHeader.addHeaderElement(
						new QName("http://www.portalfiscal.inf.br/cte/wsdl/CteRecepcao", "cteCabecMsg"));
				//.addNamespaceDeclaration("", "http://www.portalfiscal.inf.br/cte/wsdl/CteRecepcao");
		SOAPElement cUF				= cteCabecMsg.addChildElement("cUF");
		cUF.addTextNode("CE");
		SOAPElement versaoDados		= cteCabecMsg.addChildElement("versaoDados");
		versaoDados.addTextNode("0.02");
		
		SOAPBody soapBody 				= envelope.getBody();

//		StringBuilder bodyContent = new StringBuilder();
//		bodyContent.append("<cteDadosMsg xmlns=\"http://www.portalfiscal.inf.br/cte/wsdl/CteStatusServico\">")
//		.append(xMLMessage).append("</cteDadosMsg>");
//		System.out.println(bodyContent.toString());
//		soapBody.addDocument(convertStringToDocument(bodyContent.toString()));
		
		SOAPBodyElement soapBodyElem 	= soapBody.addBodyElement(
						new QName("http://www.portalfiscal.inf.br/cte/wsdl/CteRecepcao","cteDadosMsg")); 
			//soapBody.addChildElement("cteDadosMsg", "");
		//SOAPElement soapBodyElem1 	= soapBodyElem.addChildElement("cteDadosMsg", "");
		soapBodyElem.addTextNode( "<![CDATA[" + xMLMessage + "]]>");// addTextNode(xMLMessage);
		//soapBodyElem1.addTextNode(xMLMessage);
		//MimeHeaders headers 		= soapMessage.getMimeHeaders();
		// headers.addHeader("SOAPAction", "http://tempuri.org/Enviar");
		soapMessage.saveChanges();
		soapMessage.writeTo(System.out);
		return soapMessage;
	}

	public String getURLService() {
		// TODO Auto-generated method stub
		return "https://homologacao.nfe.fazenda.sp.gov.br/cteWEB/services/CteStatusServico.asmx";
	}
	
	public String getXMLBody() {
		StringBuilder xmlBody = new StringBuilder();
		xmlBody.append("");
		return xmlBody.toString();
	}

	private static Document convertStringToDocument(String xmlStr) {
	    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder builder;
	    try {
	        builder = factory.newDocumentBuilder();
	        Document doc = builder.parse(new InputSource(new StringReader(xmlStr)));
	        return doc;
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return null;
	}

}



