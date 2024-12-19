package Lab6;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.SequentialBehaviour;
import jade.lang.acl.ACLMessage;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.ControllerException;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
public class ReadFileMobileAgent extends Agent {
    @Override
    protected void setup() {
        SequentialBehaviour behaviour = new SequentialBehaviour();

        // Subcomportament pentru mutarea in containerul secundar
        behaviour.addSubBehaviour(new OneShotBehaviour() {
            @Override
            public void action() {
            	try {
                    // Creează un ACLMessage pentru a cere locația
                    ACLMessage request = new ACLMessage(ACLMessage.REQUEST);
                    request.addReceiver(getAID("FilePathInfoAgent")); // Specifică agentul din containerul secundar
                    send(request);

                    // Așteaptă răspuns de la FilePathInfoAgent
                    ACLMessage response = blockingReceive();
                    if (response != null && response.getPerformative() == ACLMessage.INFORM) {
                        String filePath = response.getContent();

                        // Adaugă comportamentul pentru citirea fișierului
                        addBehaviour(new ReadFileBehaviour(filePath));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        // Subcomportament pentru cererea locatiei fisierului
        behaviour.addSubBehaviour(new OneShotBehaviour() {
            @Override
            public void action() {
                ACLMessage request = new ACLMessage(ACLMessage.REQUEST);
                request.addReceiver(getAID("FilePathInfoAgent"));
                send(request);

                ACLMessage response = blockingReceive();
                if (response != null && response.getPerformative() == ACLMessage.INFORM) {
                    String filePath = response.getContent();
                    addBehaviour(new ReadFileBehaviour(filePath));
                }
            }
        });

        addBehaviour(behaviour);
    }

    private class ReadFileBehaviour extends OneShotBehaviour {
    private final String filePath;

    public ReadFileBehaviour(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void action() {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
                msg.addReceiver(getAID("DisplayAgent"));
                msg.setContent(line);
                send(msg);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

}