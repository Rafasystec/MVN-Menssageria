package br.com.barcadero.mensageria.soap;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import javax.xml.crypto.dsig.CanonicalizationMethod;
import javax.xml.crypto.dsig.DigestMethod;
import javax.xml.crypto.dsig.Reference;
import javax.xml.crypto.dsig.SignatureMethod;
import javax.xml.crypto.dsig.SignedInfo;
import javax.xml.crypto.dsig.Transform;
import javax.xml.crypto.dsig.XMLSignature;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import javax.xml.crypto.dsig.keyinfo.KeyValue;
import javax.xml.crypto.dsig.keyinfo.X509Data;
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
import javax.xml.crypto.dsig.spec.TransformParameterSpec;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
//import org.xml.sax.SAXException;

/**
 * Para Assinar os arquivos XML do PAF-ECF
 * @author Rafael Rocha
 *
 */
public class Signature {

//----------------------------------------------------------------------------
// Habilitar bloco somente para testes	
//----------------------------------------------------------------------------
//
	public static void main(String[] args) {
		try {
			new Signature().sing(getXMLEstoque());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace(); 
		}
	}
	
	private static String getXMLEstoque() {
		return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><Estoque Versao=\"2.0\"><Mensagem><Estabelecimento><Ie>00111111111111</Ie><Cnpj>61099008000141</Cnpj><NomeEmpresarial>COMMERCE LTDA LJ 05</NomeEmpresarial></Estabelecimento><PafEcf><NumeroCredenciamento>00002215232</NumeroCredenciamento><NomeComercial>NEXGEN PDV</NomeComercial><Versao>2.4.1</Versao><CnpjDesenvolvedor>03240156000138</CnpjDesenvolvedor><NomeEmpresarialDesenvolvedor>SECREL SOLUCOES DE INFORMATICA LTDA</NomeEmpresarialDesenvolvedor></PafEcf><DadosEstoque><DataReferenciaInicial>2016-06-01</DataReferenciaInicial><DataReferenciaFinal>2016-05-01</DataReferenciaFinal><Produtos><Produto><Descricao>PRODUTO MARCO</Descricao><Codigo>0000012</Codigo><CodigoTipo>EAN</CodigoTipo><Quantidade>4,00</Quantidade><Unidade>UN</Unidade><ValorUnitario>60,68</ValorUnitario><SituacaoTributaria>Nao tributado</SituacaoTributaria><Aliquota>12,00</Aliquota><IsArredondado>false</IsArredondado><Ippt>Terceiros</Ippt><SituacaoEstoque>Negativo</SituacaoEstoque></Produto><Produto><Descricao>&lt;Livro Dr Paulo &lt;&gt;</Descricao><Codigo>000002</Codigo><CodigoTipo>EAN</CodigoTipo><Quantidade>7,00</Quantidade><Unidade>UND</Unidade><ValorUnitario>50,00</ValorUnitario><SituacaoTributaria>Tributado pelo ICMS</SituacaoTributaria><Aliquota>12,00</Aliquota><IsArredondado>false</IsArredondado><Ippt>Terceiros</Ippt><SituacaoEstoque>Negativo</SituacaoEstoque></Produto><Produto><Descricao>BIG POTE FRUTT</Descricao><Codigo>0000031</Codigo><CodigoTipo>EAN</CodigoTipo><Quantidade>4,00</Quantidade><Unidade>CX</Unidade><ValorUnitario>180,90</ValorUnitario><SituacaoTributaria>Tributado pelo ICMS</SituacaoTributaria><Aliquota>12,00</Aliquota><IsArredondado>false</IsArredondado><Ippt>Terceiros</Ippt><SituacaoEstoque>Negativo</SituacaoEstoque></Produto><Produto><Descricao>TIMBO DO PARA VER A QUANTIDADE</Descricao><Codigo>000005</Codigo><CodigoTipo>EAN</CodigoTipo><Quantidade>10000,00</Quantidade><Unidade>UN</Unidade><ValorUnitario>100,00</ValorUnitario><SituacaoTributaria>Isento</SituacaoTributaria><Aliquota>12,00</Aliquota><IsArredondado>false</IsArredondado><Ippt>Terceiros</Ippt><SituacaoEstoque>Negativo</SituacaoEstoque></Produto><Produto><Descricao>TESTE</Descricao><Codigo>00001</Codigo><CodigoTipo>EAN</CodigoTipo><Quantidade>11,00</Quantidade><Unidade>UND</Unidade><ValorUnitario>120,00</ValorUnitario><SituacaoTributaria>Tributado pelo ICMS</SituacaoTributaria><Aliquota>12,00</Aliquota><IsArredondado>false</IsArredondado><Ippt>Terceiros</Ippt><SituacaoEstoque>Negativo</SituacaoEstoque></Produto><Produto><Descricao>&lt; &quot;Adocante&quot; &amp; zero-cal ' &lt;&gt;</Descricao><Codigo>0001</Codigo><CodigoTipo>EAN</CodigoTipo><Quantidade>8,00</Quantidade><Unidade>PC</Unidade><ValorUnitario>25,50</ValorUnitario><SituacaoTributaria>Isento</SituacaoTributaria><Aliquota>17,00</Aliquota><IsArredondado>false</IsArredondado><Ippt>Terceiros</Ippt><SituacaoEstoque>Negativo</SituacaoEstoque></Produto></Produtos></DadosEstoque></Mensagem></Estoque>";
	}
//----------------------------------------------------------------------------
	
	public String sing(String xml) throws Exception {
		/*
		 * First, we use a JAXP DocumentBuilderFactory to parse the XML document that we want to sign. An application obtains
		 *  the default implementation for DocumentBuilderFactory by calling the following line of code:
		 */
		DocumentBuilderFactory docBuilderFAC = DocumentBuilderFactory.newInstance();
		//We must also make the factory namespace-aware:
		docBuilderFAC.setNamespaceAware(true);
		//Next, we use the factory to get an instance of a DocumentBuilder, which is used to parse the document:
		DocumentBuilder docBuilder = docBuilderFAC.newDocumentBuilder();
		Document doc = docBuilder.parse(new ByteArrayInputStream(xml.getBytes("UTF-8")));
		/*
		 * Creating a Public Key Pair
		   We generate a public key pair. Later in the example, we will use the private key to generate the signature. 
		   We create the key pair with a KeyPairGenerator. In this example, we will create a DSA KeyPair with a length 
		   of 512 bytes :
		 */
		KeyPairGenerator keyGenerator = KeyPairGenerator.getInstance("DSA");
		keyGenerator.initialize(512);
		KeyPair keyPair = keyGenerator.generateKeyPair();
		/*
		 * Creating a Signing Context
		   We create an XML Digital Signature XMLSignContext containing input parameters for generating the signature. 
		   Since we are using DOM, we instantiate a DOMSignContext (a subclass of XMLSignContext), and pass it two parameters, 
		   the private key that will be used to sign the document and the root of the document to be signed:
		 */
		//DOMSignContext domContext = new DOMSignContext(keyPair.getPrivate(), doc.getDocumentElement());
		DOMSignContext domContext;
		
		domContext = new DOMSignContext(getPrivateKeyFromFile(), doc.getDocumentElement());
		
		/*
		 * Assembling the XML Signature
		   We assemble the different parts of the Signature element into an XMLSignature object. These objects are all created and 
		   assembled using an XMLSignatureFactory object. An application obtains a DOM implementation of XMLSignatureFactory by 
		   calling the following line of code:
		 */
		XMLSignatureFactory signFAC = XMLSignatureFactory.getInstance("DOM");
		/*
		 * We then invoke various factory methods to create the different parts of the XMLSignature object as shown below. We create a Reference object, passing to it the following:
		@ - The URI of the object to be signed (We specify a URI of "", which implies the root of the document.)
		@ - The DigestMethod (we use SHA1)
		@ - A single Transform, the enveloped Transform, which is required for enveloped signatures so that the signature itself is removed before calculating the signature value
		 */
		Reference reference = signFAC.newReference("", 
				signFAC.newDigestMethod(DigestMethod.SHA1, null), 
				Collections.singletonList(signFAC.newTransform(Transform.ENVELOPED, (TransformParameterSpec)null)),null , null);
		/*
		 * Next, we create the SignedInfo object, which is the object that is actually signed, as shown below. When creating the SignedInfo, we pass as parameters:
		 * @ - The CanonicalizationMethod (we use inclusive and preserve comments)
		 * @ - The SignatureMethod (we use DSA)
		 * @ - A list of References (in this case, only one)
		 * 
		 */
		SignedInfo signedInfo = signFAC.newSignedInfo(signFAC.newCanonicalizationMethod(CanonicalizationMethod.INCLUSIVE, (C14NMethodParameterSpec)null), 
				signFAC.newSignatureMethod(SignatureMethod.RSA_SHA1, null), Collections.singletonList(reference));
		/*
		 * Next, we create the optional KeyInfo object, which contains information that enables the recipient to find the key needed to validate 
		 * the signature. In this example, we add a KeyValue object containing the public key. To create KeyInfo and its various subtypes, we use 
		 * a KeyInfoFactory object, which can be obtained by invoking the getKeyInfoFactory method of the XMLSignatureFactory, as follows:
		 */
		KeyInfoFactory keyInfoFactory = signFAC.getKeyInfoFactory();
		//We then use the KeyInfoFactory to create the KeyValue object and add it to a KeyInfo object:
		//KeyValue keyValue = keyInfoFactory.newKeyValue(keyPair.getPublic());
		//KeyInfo keyInfo = keyInfoFactory.newKeyInfo(Collections.singletonList(keyValue));
		 
		X509Certificate signingCertificate = getX509Certificate();
	    X509Data x509data = keyInfoFactory.newX509Data(Collections.nCopies(1, signingCertificate));
	    KeyValue keyValue = keyInfoFactory.newKeyValue(keyPair.getPublic());
	    List keyInfoItems = new ArrayList();
	    keyInfoItems.add(keyValue);
	    keyInfoItems.add(x509data);
	    
	    KeyInfo keyInfo = keyInfoFactory.newKeyInfo(keyInfoItems);


		//NOTE: Finally, we create the XMLSignature object, passing as parameters the SignedInfo and KeyInfo objects that we created earlier:
		XMLSignature signature = signFAC.newXMLSignature(signedInfo, keyInfo);
		/*
		 *Generating the XML Signature
		 *Now we are ready to generate the signature, which we do by invoking the sign method on the 
		 *XMLSignature object, and pass it the signing context as follows: 
		 */
		signature.sign(domContext);
		//The resulting document now contains a signature, which has been inserted as the last child element of the root element.
		/*
		 * Printing or Displaying the Resulting Document
		 * You can use the following code to print the resulting signed document to a file or standard output:
		 */
		//OutputStream os;
		//os = System.out;
		String xmlSignatured = "";
		TransformerFactory transFAC = TransformerFactory.newInstance();
		try {
			Transformer transformer = transFAC.newTransformer();
			//transformer.transform(new DOMSource(doc), new StreamResult(os));
			StringWriter strWriter = new StringWriter();
			StreamResult result		= new StreamResult(strWriter);
			transformer.transform(new DOMSource(doc), result);
			StringBuffer buffer = strWriter.getBuffer();
			xmlSignatured = buffer.toString();
			System.out.println(xmlSignatured);
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return xmlSignatured;
	}
	
	
private char[] password = "goldenitalia".toCharArray() ;
private Key getKey() throws Exception {
	
	FileInputStream fis = null;
	
	try {
		
		KeyStore ksKeys = KeyStore.getInstance("PKCS12");
		//fis = new FileInputStream("resources/Certificado Otica Golden.pfx");
		InputStream is = getClass().getClassLoader().getResourceAsStream("Certificado Otica Golden.pfx");
		ksKeys.load(is, password);
		String alias = "";
		
		if (ksKeys.aliases().hasMoreElements()) {
			
            alias = (String) ksKeys.aliases().nextElement();
			if (!ksKeys.isKeyEntry(alias)) {
									
				throw new Exception("Não existe chave particular no armazém designado.");
				
			}				
			return ksKeys.getKey(alias, password);
			
        }
		throw new IllegalArgumentException("KeyStore não contem o alias");
		
	} catch (Exception e) {
		throw new IllegalArgumentException("Erro ao abrir KeyStore -  " + e.getMessage());
	} finally {
		if (fis != null) fis.close(); 
	}
	
}

private PrivateKey getPrivateKeyFromFile() throws Exception {  
    
    Key key = getKey();
    
    if( key instanceof PrivateKey ) {
        return (PrivateKey) key;
    }
    return null;
    
}

private X509Certificate getX509Certificate() throws Exception {  
    
    Key key;
    X509Certificate x509Certificate = null;
    InputStream inStream = null;
   
	inStream = getClass().getClassLoader().getResourceAsStream("Certificado Otica Golden.pfx");
	KeyStore ks = KeyStore.getInstance("PKCS12");
	ks.load(inStream, password);
	Enumeration<String> aliases = ks.aliases();
	while (aliases.hasMoreElements()) {
		String keyAlias = aliases.nextElement().toString();
		key = ks.getKey(keyAlias, password);
		if (key instanceof java.security.interfaces.RSAPrivateKey) {
			 Certificate[] certificateChain = ks.getCertificateChain(keyAlias);
			 x509Certificate = (X509Certificate)certificateChain[0];
			 return x509Certificate;
//			 boolean[] keyUsage = x509Certificate.getKeyUsage();
////			 if ((keyUsage == null) || keyUsage[0] || keyUsage[1]) {
////				 
////			 }
		}
	}
	 return x509Certificate;
}

}
