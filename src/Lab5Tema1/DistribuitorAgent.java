package Lab5Tema1;

import jade.core.Agent;
import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DistribuitorAgent extends Agent {
    private DistribuitorGui gui;

    protected void setup() {
        gui = new DistribuitorGui(this);
        gui.showGui();
        System.out.println("DistribuitorAgent " + getAID().getName() + " este activ.");
    }

    public void sendValues(int[] values) {
        for (int i = 0; i < values.length; i++) {
            final int index = i;
            addBehaviour(new OneShotBehaviour() {
                public void action() {
                    ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
                    msg.addReceiver(new AID("CalcAgent" + (index + 1), AID.ISLOCALNAME));
                    msg.setContent(String.valueOf(values[index]));
                    send(msg);
                }
            });
        }
        addBehaviour(new ReceiveResultsBehaviour());
    }

    private class ReceiveResultsBehaviour extends CyclicBehaviour {
        private int receivedResults = 0;
        private int[] results = new int[3];

        public void action() {
            ACLMessage msg = receive();
            if (msg != null) {
                try {
                    int index = Integer.parseInt(msg.getConversationId());  
                    System.out.println("DistribuitorAgent a primit mesaj de la " + msg.getSender().getName() + 
                                       " cu ConversationId: " + msg.getConversationId() + 
                                       " și conținutul: " + msg.getContent());
     
                    if (index >= 0 && index < 3) {
                        results[index] = Integer.parseInt(msg.getContent());
                        receivedResults++;
                    } else {
                        System.out.println("Index invalid primit: " + index);
                    }

                    if (receivedResults == 3) {
                        gui.displayResults(results);
                        receivedResults = 0;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Eroare la procesarea mesajului: " + msg.getContent() + " pentru ConversationId: " + msg.getConversationId());
                }
                String conversationId = msg.getConversationId();
                try {
                    int index = Integer.parseInt(conversationId);
                    
           
                    if (index >= 0 && index < 3) {
                        results[index] = Integer.parseInt(msg.getContent());
                        receivedResults++;
                    } else {
                        System.out.println("Mesaj cu un ConversationId necorespunzător primit: " + conversationId);
                    }
                    
        
                    if (receivedResults == 3) {
                        gui.displayResults(results);
                        receivedResults = 0; 
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Eroare: ConversationId invalid primit: " + conversationId);
                }
            } else {
                block();
            }
        }
    }


    protected void takeDown() {
        gui.dispose();
        System.out.println("DistribuitorAgent " + getAID().getName() + " este închis.");
    }
}
