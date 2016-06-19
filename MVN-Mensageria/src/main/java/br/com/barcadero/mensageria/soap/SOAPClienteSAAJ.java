package br.com.barcadero.mensageria.soap;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.Security;

import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

import br.com.barcadero.mensageria.cte.CTeService;

/**
 * Para transmitir os arquivos de XML do PAF
 * @author Rafael Rocha
 */
public class SOAPClienteSAAJ {


	
	
	public static void main(String args[]) throws Exception {
		
		//new SOAPClienteSAAJ().sendAndReceive(new CTeService(), getXMLStatusServico());
		//getResponse(getResponseReducaoZ());
	}

	/**
	 * Enviar mensagem soap ao endpoint e receber a resposta
	 * @param message
	 * @return
	 * @throws Exception
	 */
	public String  sendAndReceive(IServices service,String message) throws Exception{
		initializeSSL();
		SOAPConnection soapConnection 				 = null;
		SOAPConnectionFactory soapConnectionFactory  = null;
		try{
			//-------------------------------------------------------
			// Create SOAP Connection
			//-------------------------------------------------------
			soapConnectionFactory 	= SOAPConnectionFactory.newInstance();
			soapConnection 			= soapConnectionFactory.createConnection();
			//-------------------------------------------------------
			//Assinar arquivo xml
			//-------------------------------------------------------
			//message = new Signature().sing(message);
			// Send SOAP Message to SOAP Server
			//-------------------------------------------------------
			String url = service.getURLService(); //"http://webservices.sathomologa.sef.sc.gov.br/wsDfeSiv/Recepcao.asmx";
			SOAPMessage soapResponse = soapConnection.call(createSOAPRequest(service, message), url);
			//-------------------------------------------------------
			//Print SOAP Response
			//-------------------------------------------------------
			System.out.print("Response SOAP Message:");
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			soapResponse.writeTo(stream);
			return new String(stream.toByteArray(),"UTF-8");
			//return getResponseSOAP(soapResponse,type);
		}catch(Exception e){
			throw e;
		}finally {
			if(soapConnection != null){
				try {
					soapConnection.close();
				} catch (SOAPException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	private SOAPMessage createSOAPRequest(IServices service, String message) throws SOAPException, IOException {
		return service.buildSoapMessage(message);
	}


	private static String getSgnature() {
		return "<Signature xmlns=\"http://www.w3.org/2000/09/xmldsig#\"><SignedInfo><CanonicalizationMethod Algorithm=\"http://www.w3.org/TR/2001/REC-xml-c14n-20010315\" /><SignatureMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#rsa-sha1\" /><Reference URI=\"\"><Transforms><Transform Algorithm=\"http://www.w3.org/2000/09/xmldsig#enveloped-signature\" /></Transforms><DigestMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#sha1\" /><DigestValue>0G6GVAG5xL6GnE2lW9xxJydpUQs8=</DigestValue></Reference></SignedInfo><SignatureValue>DtYo84382tS9nQHZjmCR5sOSLwXQGu0P29BQC1v8bWLkCbrkUpH8LkqZSKAT3BNakfmcPIHrdDXveQcXerwR/2H3WM8m0q4kKB3knKpDZJcbDT+kgDHo1E21utAy54DyDRThT5vN9mqoJsNnasGnipm4xsy4Jl7UQWtfThejof4+eAvnBBlNhsC1cCopoB2LmIvGvcZr9Y56KmvI1YFBK7pPaK9T5r+GkU1wkLQX4BlMPLez4Gr/23Fg/dN2qSpgr9yewJE5GZZlDAK/Sooe1ACY/NPYtEVUsO4y9DrS/vz9on3nil1PXzmLDF3in3AzyLLTvVlJwVj1exqJz6rBDajeA==</SignatureValue><KeyInfo><X509Data><X509Certificate>XIIDCzCCAfOgAwIBAgIQJyeEWFflS6hPDf21PNRTDjANBgkqhkiG9w0BAQsFADAeMRwwGgYDVQQDDBNXaW5kb3dzIEF6dXJlIFRvb2xzMB4XDTE1MDgyNjIxMTMxMVoXDTE2MDgyNjIxMjMxMlowHjEcMBoGA1UEAwwTV2luZG93cyBBenVyZSBUb29sczCCASIwDQYJKSoZIhvcNAQEBBQADggEPADCCAQoCggEBAK3Duc/We4doidpQVFEGSRqi7VIBpoSttyn6+qEALeWyFZrUdRODY0fT7yzLfI+GNEOkypYkhVgSv3rRD4LDF927ARrN9jp1uly2m5ck6x02Ahh5tvC/XeL0JsvTzoE1cUJNANvqZbVBw5KJoIjxoFLucKhiDsiyop5KbR+dCnYqDbVHp656KVZQ3O3cgGKoBwNJ8A43ZYO66ZzwpMOOe3EgzSzUGXeWR++EMO7MpLbmf7B7z9z36wgaOAVgb3aG7571yfeLzn+YQ/mCydRWc8YazMciUMdfehE5jS3mLceIS0efVb4M3R2uzG26/8wnbteERNIavj4J5GcmUzAKZEsCAwEAAaNFMEMwEgYDVR0TAQH/BAgwBgIEf////zAdBgNVHQ4EFgQU0Ws2wk8yXvmQHmAecizrMy7XyDV0wDgYDVR0PAQH/BAQDAgUgMA0GCSqGSIb3DQEBCwUAA4IBAQAcRVTnJGegjQNt7g0Qe5YftSStmm0+unG94sdlVhhhIsGqaKhijZ7BJxmC+B7lvGmkahAbX59Wz0NbMHpjj8r3hOn1oV6wjBwzxAXVzujLsceP7G6YvhYph2P6pgKrULipdX7KAc2VvGGpJRbvTKnG4R3GNu8E7ZhyPKPtNtRZ0FCfRF/d1qhAZ0Kzqa1u5tsWLD9hstz9ZhBs2tJkVkEz32yok6xZFKXcPgj8FE0z9LOxQ6EHdqxfPpY0sMCdca1xgOE5gqQXpBLp5FHEEdvJbuEuurAic1cI23MkpTWjL1n0ea4g9cYBJJPLaaa9GeMW/KMmerrhrZ1Ou4y2QLw2</X509Certificate></X509Data></KeyInfo></Signature>";

	}

	public static String normalize(String xml) {		
		//return Normalizer.normalize(xml, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "").replaceAll("&amp;quot;", "&quot;").replaceAll("&amp;#39;", "&#39;");
		return xml.replaceAll("&lt;", "<").replaceAll("&gt;", ">").replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>","").replace("xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"","")
				.replace("xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"", "").replace("xmlns=\"http://tempuri.org/\"", "");
	}


	public static String  getXMLStatusServico() {
		return "<consStatServCte versao=\"2.00\"> <tpAmb>2</tpAmb><xServ>STATUS</xServ></consStatServCte>";
	}

	private void initializeSSL() throws URISyntaxException {

		System.clearProperty("java.protocol.handler.pkgs");  
		System.clearProperty("javax.net.ssl.keyStoreType");  
		System.clearProperty("javax.net.ssl.keyStore");  
		System.clearProperty("javax.net.ssl.keyStorePassword");  
		System.clearProperty("javax.net.ssl.trustStore");  
		System.clearProperty("javax.net.ssl.trustStoreType");  

		//URL resource = SOAPClienteSAAJ.class.getResource("NFeCacerts");
		
		System.setProperty("java.protocol.handler.pkgs", "com.sun.net.ssl.internal.www.protocol");  
		Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());  
		System.setProperty("java.net.useSystemProxies", "true");
		System.setProperty("javax.net.ssl.keyStoreType", "PKCS12");  
		System.setProperty("javax.net.ssl.keyStore", getPath2());  
		System.setProperty("javax.net.ssl.keyStorePassword", "_cert@gbo");  
		System.setProperty("javax.net.ssl.trustStoreType", "JKS");  
		System.setProperty("javax.net.ssl.trustStore", getPath());  
		System.setProperty("sun.security.ssl.allowUnsafeRenegotiation", "true");

	}
	
	public String getPath() {
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("AllCacerts").getFile());
		String ab = file.getAbsolutePath(); 
		System.out.println(ab);
		return ab;
	}
	
	public String getPath2() {
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("07221070v2016.pfx").getFile());
		String ab = file.getAbsolutePath(); 
		System.out.println(ab);
		return ab;
	}

}
