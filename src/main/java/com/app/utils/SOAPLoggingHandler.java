package com.app.utils;

import lombok.extern.slf4j.Slf4j;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Set;

/** This simple SOAPHandler will output the contents of incoming and outgoing messages. */
@Slf4j
public class SOAPLoggingHandler implements SOAPHandler<SOAPMessageContext> {

  // change this to redirect output is desired
  private static PrintStream stream = System.out;

  @Override
  public Set<QName> getHeaders() {
    return null;
  }

  @Override
  public boolean handleMessage(SOAPMessageContext context) {
    logToSystemOut(context);
    return true;
  }

  @Override
  public boolean handleFault(SOAPMessageContext context) {
    logToSystemOut(context);
    return true;
  }

  // nothing to clean up
  @Override
  public void close(MessageContext context) {}

  /**
   * Chek the MESSAGE_OUTBOUND_PROPERTY in the context to see if this is on outgoing or incoming
   * message. Write a brief message to the print stream and output the message. The writeTo() method
   * can throw SOAPException or IOException.
   *
   * @param context
   */
  private void logToSystemOut(SOAPMessageContext context) {

    if (!log.isDebugEnabled()) return;
    Boolean outboundProperty = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);

    // change this to redirect output is desired
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    try {

      if (outboundProperty.booleanValue())
        byteArrayOutputStream.write("Outbound message: ".getBytes());
      else byteArrayOutputStream.write("Inbound message: ".getBytes());
      SOAPMessage message = context.getMessage();
      message.writeTo(byteArrayOutputStream);
      byteArrayOutputStream.flush();
      byteArrayOutputStream.close();
      log.debug(new String(byteArrayOutputStream.toByteArray()));

    } catch (Exception e) {

      log.error("Error while reading token header value {}", e.getMessage(), e);
    } finally {

      try {
        byteArrayOutputStream.close();
      } catch (IOException e) {
        log.error("Error  close Stream : {}", e.getMessage(), e);
      }
    }
  }
}
