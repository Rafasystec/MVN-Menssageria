package br.com.barcadero.mensageria.soap;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;

import javax.xml.bind.JAXBException;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFault;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

/**
 * Para transmitir os arquivos de XML do PAF
 * @author Rafael Rocha
 */
public class SOAPClienteSAAJ {

//    public static void main(String args[]) throws Exception {
//    	new SOAPClienteSAAJ().sendAndReceive(getXMLReducao(), EnumTypeEnv.REDUCAO_Z);
//    	//getResponse(getResponseReducaoZ());
//    }
    
    /**
     * Enviar mensagem soap ao endpoint e receber a resposta
     * @param message
     * @return
     * @throws Exception
     */
    public String  sendAndReceive(IServices service,String message) throws Exception{
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
	        message = new Signature().sing(message);
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
    
//    /**
//     * Extrair o objeti de retorno direto da mensagem
//     * @param soapResponse
//     * @return
//     * @throws Exception 
//     * @throws JAXBException 
//     */
//    private XMLResponse getResponseSOAP(SOAPMessage soapResponse, EnumTypeEnv type) throws JAXBException, Exception {
//    	XMLResponse response 	= new XMLResponse();
//    	SOAPBody body 			= soapResponse.getSOAPBody();
//    	if(body.hasFault()){
//    		//Houve uma falha ao obter a falha na mensagem
//    		SOAPFault fault = body.getFault();
//    		response.setSoapFailt(fault.getFaultCode());
//    		response.setSoapMessage(fault.getFaultActor() + fault.getDetail().getValue());
//    		response.setHasFault(true);
//    	}else{
//    		if(type == EnumTypeEnv.REDUCAO_Z){
//	    		ReducaoZResposta reducao = getReducaoZResposta(body.getTextContent());
//	    		if(reducao != null){
//	    			String recibo = reducao.getRecibo();
//	    			if(recibo.trim().isEmpty()){
//	    				response.setHasFault(true);
//    					response.setLote("0");
//    					response.setMsgServer(reducao.getMensagem());
//	    			}else{
//	    				response.setHasFault(false);
//    					response.setLote(recibo);
//    					response.setMsgServer(reducao.getMensagem());
//	    			}
//	    		}
//    		}else{
//    			//Para o Estoque
//    			EstoqueResposta estoque = getEstoqueResposta(body.getTextContent());
//    			if(estoque != null){
//    				String recibo = estoque.getRecibo();
//    				if(recibo.trim().isEmpty()){
//	    				response.setHasFault(true);
//    					response.setLote("0");
//    					response.setMsgServer(estoque.getMensagem());
//	    			}else{
//	    				response.setHasFault(false);
//    					response.setLote(recibo);
//    					response.setMsgServer(estoque.getMensagem());
//	    			}
//    			}
//    		} 
//    		response.setTextContent(body.getTextContent());
//    	}
//    	return response;
//	}

    /**
     * Criar a mensagem SOAP que sera enviada.
     * @param message
     * @return
     * @throws Exception
     */
    private static SOAPMessage createSOAPRequest(IServices service, String message) throws Exception {
        //MessageFactory messageFactory 	= MessageFactory.newInstance();
        //SOAPMessage soapMessage 		= messageFactory.createMessage();
//    	SOAPMessage soapMessage = SOAPMessageUtil.buildMessage();
//    	SOAPPart soapPart 		= soapMessage.getSOAPPart();

        //String serverURI = "http://ws.cdyne.com/";
        //-------------------------------------------------------
        // SOAP Envelope
        //-------------------------------------------------------
//        SOAPEnvelope envelope = soapPart.getEnvelope();
//        envelope.addNamespaceDeclaration("", service.getNameSpaceDeclaration());
        /*
        Constructed SOAP Request Message:
        <SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/" xmlns:example="http://ws.cdyne.com/">
            <SOAP-ENV:Header/>
            <SOAP-ENV:Body>
                <example:VerifyEmail>
                    <example:email>mutantninja@gmail.com</example:email>
                    <example:LicenseKey>123</example:LicenseKey>
                </example:VerifyEmail>
            </SOAP-ENV:Body>
        </SOAP-ENV:Envelope>
         */
        //-------------------------------------------------------
        // SOAP Body
        //-------------------------------------------------------
//        soapMessage = service.buildSoapMessage(message);
//        SOAPBody soapBody 			= envelope.getBody();
//        SOAPElement soapBodyElem 	= soapBody.addChildElement("Enviar", "");
//        SOAPElement soapBodyElem1 	= soapBodyElem.addChildElement("pXml", "");
//        soapBodyElem1.addTextNode(message);
//        MimeHeaders headers 		= soapMessage.getMimeHeaders();
//        headers.addHeader("SOAPAction", "http://tempuri.org/Enviar");
//        soapMessage.saveChanges();
        //-------------------------------------------------------
        /* Print the request message */
        //-------------------------------------------------------
        System.out.print("Request SOAP Message:");
        soapMessage = service.buildSoapMessage(message);
        soapMessage.writeTo(System.out);
        System.out.println();
        return soapMessage;
    }
    
//    private static String getXMLReducao() {
//		return XMLSender.normalize("<?xml version=\"1.0\" encoding=\"utf-8\"?><ReducaoZ Versao=\"2.0\"><Mensagem><Estabelecimento><Ie>00111111111111</Ie><Cnpj>61099008000141</Cnpj><NomeEmpresarial>COMMERCE LTDA LJ 05</NomeEmpresarial></Estabelecimento><PafEcf><NumeroCredenciamento>00002215232</NumeroCredenciamento><NomeComercial>NEXGEN PDV</NomeComercial><Versao>2.4.1</Versao><CnpjDesenvolvedor>03240156000138</CnpjDesenvolvedor><NomeEmpresarialDesenvolvedor>SECREL SOLUCOES DE INFORMATICA LTDA</NomeEmpresarialDesenvolvedor></PafEcf><Ecf><NumeroFabricacao>DR0207BR000000119418</NumeroFabricacao><Tipo>ECF-IF</Tipo><Marca>DARUMA AUTOMACAO</Marca><Modelo>FS600</Modelo><Versao>010500</Versao><Caixa>10</Caixa><DadosReducaoZ><DataReferencia>2016-05-03</DataReferencia><CRZ>001290</CRZ><COO>000033134</COO><CRO>000019</CRO><VendaBrutaDiaria>2388,42</VendaBrutaDiaria><GT>12179179,85</GT><TotalizadoresParciais><TotalizadorParcial><Nome>06S0300</Nome><Valor>0,00</Valor><ProdutosServicos/></TotalizadorParcial><TotalizadorParcial><Nome>05S0500</Nome><Valor>27,13</Valor><ProdutosServicos><Servico><Descricao>BIG POTE FRUTT</Descricao><Codigo>0000031</Codigo><CodigoTipo>EAN</CodigoTipo><Quantidade>1,00</Quantidade><Unidade>CX</Unidade><ValorUnitario>180,90</ValorUnitario></Servico><Servico><Descricao>BIG POTE FRUTT</Descricao><Codigo>0000031</Codigo><CodigoTipo>EAN</CodigoTipo><Quantidade>1,00</Quantidade><Unidade>CX</Unidade><ValorUnitario>180,90</ValorUnitario></Servico></ProdutosServicos></TotalizadorParcial></TotalizadoresParciais></DadosReducaoZ></Ecf></Mensagem>" + getSgnature() + "</ReducaoZ>");
//	}
    
    private static String getSgnature() {
		return "<Signature xmlns=\"http://www.w3.org/2000/09/xmldsig#\"><SignedInfo><CanonicalizationMethod Algorithm=\"http://www.w3.org/TR/2001/REC-xml-c14n-20010315\" /><SignatureMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#rsa-sha1\" /><Reference URI=\"\"><Transforms><Transform Algorithm=\"http://www.w3.org/2000/09/xmldsig#enveloped-signature\" /></Transforms><DigestMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#sha1\" /><DigestValue>0G6GVAG5xL6GnE2lW9xxJydpUQs8=</DigestValue></Reference></SignedInfo><SignatureValue>DtYo84382tS9nQHZjmCR5sOSLwXQGu0P29BQC1v8bWLkCbrkUpH8LkqZSKAT3BNakfmcPIHrdDXveQcXerwR/2H3WM8m0q4kKB3knKpDZJcbDT+kgDHo1E21utAy54DyDRThT5vN9mqoJsNnasGnipm4xsy4Jl7UQWtfThejof4+eAvnBBlNhsC1cCopoB2LmIvGvcZr9Y56KmvI1YFBK7pPaK9T5r+GkU1wkLQX4BlMPLez4Gr/23Fg/dN2qSpgr9yewJE5GZZlDAK/Sooe1ACY/NPYtEVUsO4y9DrS/vz9on3nil1PXzmLDF3in3AzyLLTvVlJwVj1exqJz6rBDajeA==</SignatureValue><KeyInfo><X509Data><X509Certificate>XIIDCzCCAfOgAwIBAgIQJyeEWFflS6hPDf21PNRTDjANBgkqhkiG9w0BAQsFADAeMRwwGgYDVQQDDBNXaW5kb3dzIEF6dXJlIFRvb2xzMB4XDTE1MDgyNjIxMTMxMVoXDTE2MDgyNjIxMjMxMlowHjEcMBoGA1UEAwwTV2luZG93cyBBenVyZSBUb29sczCCASIwDQYJKSoZIhvcNAQEBBQADggEPADCCAQoCggEBAK3Duc/We4doidpQVFEGSRqi7VIBpoSttyn6+qEALeWyFZrUdRODY0fT7yzLfI+GNEOkypYkhVgSv3rRD4LDF927ARrN9jp1uly2m5ck6x02Ahh5tvC/XeL0JsvTzoE1cUJNANvqZbVBw5KJoIjxoFLucKhiDsiyop5KbR+dCnYqDbVHp656KVZQ3O3cgGKoBwNJ8A43ZYO66ZzwpMOOe3EgzSzUGXeWR++EMO7MpLbmf7B7z9z36wgaOAVgb3aG7571yfeLzn+YQ/mCydRWc8YazMciUMdfehE5jS3mLceIS0efVb4M3R2uzG26/8wnbteERNIavj4J5GcmUzAKZEsCAwEAAaNFMEMwEgYDVR0TAQH/BAgwBgIEf////zAdBgNVHQ4EFgQU0Ws2wk8yXvmQHmAecizrMy7XyDV0wDgYDVR0PAQH/BAQDAgUgMA0GCSqGSIb3DQEBCwUAA4IBAQAcRVTnJGegjQNt7g0Qe5YftSStmm0+unG94sdlVhhhIsGqaKhijZ7BJxmC+B7lvGmkahAbX59Wz0NbMHpjj8r3hOn1oV6wjBwzxAXVzujLsceP7G6YvhYph2P6pgKrULipdX7KAc2VvGGpJRbvTKnG4R3GNu8E7ZhyPKPtNtRZ0FCfRF/d1qhAZ0Kzqa1u5tsWLD9hstz9ZhBs2tJkVkEz32yok6xZFKXcPgj8FE0z9LOxQ6EHdqxfPpY0sMCdca1xgOE5gqQXpBLp5FHEEdvJbuEuurAic1cI23MkpTWjL1n0ea4g9cYBJJPLaaa9GeMW/KMmerrhrZ1Ou4y2QLw2</X509Certificate></X509Data></KeyInfo></Signature>";
    	
	}
    
    
//    private static ReducaoZResposta getReducaoZResposta(String xmlResponse) throws JAXBException, Exception {
//    	ReducaoZResposta response = (ReducaoZResposta) XMLHelper.unMarshal(xmlResponse, EnviarResponse.class);
//    	return response;
//	}
//    
//    private static EstoqueResposta getEstoqueResposta(String xmlResponse) throws JAXBException, Exception {
//    	EstoqueResposta response = (EstoqueResposta) XMLHelper.unMarshal(xmlResponse, EstoqueResposta.class);
//    	return response;
//	}
    
    
    public static String normalize(String xml) {		
    	//return Normalizer.normalize(xml, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "").replaceAll("&amp;quot;", "&quot;").replaceAll("&amp;#39;", "&#39;");
    	return xml.replaceAll("&lt;", "<").replaceAll("&gt;", ">").replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>","").replace("xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"","")
    			.replace("xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"", "").replace("xmlns=\"http://tempuri.org/\"", "");
    }
    
    
    
}
