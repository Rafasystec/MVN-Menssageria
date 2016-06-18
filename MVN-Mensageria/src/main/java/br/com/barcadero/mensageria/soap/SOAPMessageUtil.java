package br.com.barcadero.mensageria.soap;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

/**
 * 
 * @author Rafael Rocha
 *
 */
public class SOAPMessageUtil {

	/**
	 * 
	 * @return
	 * @throws SOAPException
	 */
	public static SOAPMessage buildMessage() throws SOAPException {
		MessageFactory messageFactory 	= MessageFactory.newInstance();
        return messageFactory.createMessage();
	}
}
