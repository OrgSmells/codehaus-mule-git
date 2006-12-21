package org.jbpm.msg.mule;

import org.mule.umo.UMOMessage;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jbpm.graph.exe.Token;
import org.jbpm.msg.Message;

public class MuleMessageService implements org.jbpm.msg.MessageService {

  private static final long serialVersionUID = 1L;

  protected static org.mule.providers.bpm.MessageService proxy;

  public MuleMessageService() {
  }

  public static void setMessageService(org.mule.providers.bpm.MessageService msgService) {
      proxy = msgService;
  }

  // TODO This should be replaced by the standard send() method below, which would make
  // Mule the default messaging service within jBpm.
  public UMOMessage generateMessage(String endpoint, Object payloadObject, Map messageProperties, boolean synchronous) throws Exception {
      return proxy.generateMessage(endpoint, payloadObject, messageProperties, synchronous);
  }

  public void send(Message message) {
  }

  public void suspendMessages(Token token) {
  }

  public void resumeMessages(Token token) {
  }

  public void close() {
  }

  private static Log log = LogFactory.getLog(MuleMessageService.class);
}
