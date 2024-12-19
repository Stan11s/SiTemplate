package Main2;

import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class MyAgent extends Agent {
    private String[] agents = {"Agent_1", "Agent_2", "Agent_3"};
    
    @Override
    protected void setup() {
        System.out.println(getLocalName() + " este pregătit.");
        
        // Comportament periodic
        addBehaviour(new TickerBehaviour(this, 3000) { // Trimitere mesaj la fiecare 3 secunde
            @Override
            protected void onTick() {
                // Alege un agent aleator către care să trimită mesaj
                String targetAgent = agents[new Random().nextInt(agents.length)];
                if (targetAgent.equals(getLocalName())) {
                    return; // Nu trimite mesaj către el însuși
                }
                
                // Creare mesaj REQUEST
                ACLMessage request = new ACLMessage(ACLMessage.REQUEST);
                request.addReceiver(getAID(targetAgent));
                request.setContent("Ce ora este?");
                send(request);
                System.out.println(getLocalName() + " a întrebat pe " + targetAgent);
            }
        });
        
        // Comportament pentru a primi mesaje
        addBehaviour(new jade.core.behaviours.CyclicBehaviour() {
            @Override
            public void action() {
                ACLMessage msg = receive();
                if (msg != null) {
                    if (msg.getPerformative() == ACLMessage.REQUEST) {
                        // Răspunde la întrebarea "Ce ora este?"
                        ACLMessage reply = msg.createReply();
                        reply.setPerformative(ACLMessage.INFORM);
                        reply.setContent("Ora este " + getCurrentTime());
                        send(reply);
                        System.out.println(getLocalName() + " a răspuns către " + msg.getSender().getLocalName());
                    } else if (msg.getPerformative() == ACLMessage.INFORM) {
                        System.out.println(getLocalName() + " a primit răspuns: " + msg.getContent());
                    }
                } else {
                    block();
                }
            }
        });
    }
    
    // Metodă pentru obținerea orei curente
    private String getCurrentTime() {
        return new SimpleDateFormat("HH:mm:ss").format(new Date());
    }
}
