package Main;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

import java.util.HashMap;
import java.util.Map;

public class EmployerAgent extends Agent {
    private Map<String, Integer> availableJobs;

    @Override
    protected void setup() {
        // Inițializarea joburilor disponibile
        availableJobs = new HashMap<>();
        availableJobs.put("Software Developer", 3000);
        availableJobs.put("System Analyst", 3500);
        availableJobs.put("Data Scientist", 4000);
        availableJobs.put("Web Developer", 2500);

        System.out.println("Employer agent " + getAID().getName() + " started.");

        // Comportamentul care ascultă cererile de la JobSeeker
        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                ACLMessage message = receive();
                if (message != null) {
                    String requestedJob = message.getContent();
                    ACLMessage reply = message.createReply();
                    if (availableJobs.containsKey(requestedJob)) {
                        reply.setPerformative(ACLMessage.AGREE);
                        reply.setContent(requestedJob + " available with salary: " + availableJobs.get(requestedJob));
                    } else {
                        reply.setPerformative(ACLMessage.REFUSE);
                        reply.setContent("Job not found");
                    }
                    send(reply);
                } else {
                    block();
                }
            }
        });
    }

    @Override
    protected void takeDown() {
        System.out.println("Employer agent " + getAID().getName() + " is terminating.");
    }
}
