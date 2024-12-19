package Lab5Tema1;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class CalcAgent extends Agent {
    private int value;

    protected void setup() {
        System.out.println(getAID().getName() + " este activ.");
  
        addBehaviour(new CyclicBehaviour() {
            public void action() {
                ACLMessage msg = receive();
                if (msg != null) {
 
                    value = Integer.parseInt(msg.getContent());
                    System.out.println(getAID().getName() + " a procesat valoarea: " + value + ", rezultat: " + (value * value));
                    
                
                    ACLMessage reply = msg.createReply();
                    reply.setContent(String.valueOf(value * value)); 
                    reply.setConversationId(msg.getConversationId());

                    send(reply);
                } else {
                    block();
                }
            }
        });
    }
}

