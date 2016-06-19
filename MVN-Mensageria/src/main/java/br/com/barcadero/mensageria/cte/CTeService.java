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

public class CTeService extends SuperCTeService {

	private ICTeService icTeService;
	
	public CTeService(EnumCTeServicos servico) {
		// TODO Auto-generated constructor stub
	}
	
	public String getURLService(EnumTpAmbiente tpAmb) {
		// TODO Auto-generated method stub
		return icTeService.getURLCteConsultaProtocolo();
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
	@Deprecated
	public SOAPMessage buildSoapMessage(String xMLMessage, SOAPMessage sOPAMessage) throws SOAPException {
		return null;
	}
	
	
	public SOAPMessage buildSoapMessage(String xMLMessage) throws SOAPException, IOException {
		return getSOAPMessage(xMLMessage);
	}

	public String getURLService() {
		// TODO Auto-generated method stub
		return "https://cte-homologacao.svrs.rs.gov.br/ws/ctestatusservico/CteStatusServico.asmx";
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



