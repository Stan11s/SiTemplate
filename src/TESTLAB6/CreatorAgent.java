package TESTLAB6;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;

public class CreatorAgent extends Agent {
    @Override
    protected void setup() {
        System.out.println("Agentul " + getLocalName() + " a fost lansat.");

        // Adaugă un comportament pentru a crea un agent dinamic
        addBehaviour(new CreateAgentBehaviour());
    }

    private class CreateAgentBehaviour extends Behaviour {

        private boolean isDone = false;

        @Override
        public void action() {
            try {
                // Obține controlerul containerului în care rulează agentul curent
                AgentContainer container = getContainerController();

                // Nume și clasă pentru agentul dinamic
                String newAgentName = getLocalName() + "_DynamicAgent";
                String agentClass = DynamicAgent.class.getName();

                // Creează și pornește noul agent
                System.out.println("Crearea agentului dinamic: " + newAgentName);
                AgentController dynamicAgent = container.createNewAgent(newAgentName, agentClass, null);
                dynamicAgent.start();

                // Marchează comportamentul ca finalizat
                isDone = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public boolean done() {
            return isDone;
        }
    }

    @Override
    protected void takeDown() {
        System.out.println("Agentul " + getLocalName() + " este terminat.");
    }
}

// Definiția clasei pentru agentul dinamic
class DynamicAgent extends Agent {
    @Override
    protected void setup() {
        System.out.println("Agentul dinamic " + getLocalName() + " a fost creat și este funcțional.");

        // Adaugă un comportament simplu pentru agentul dinamic
        addBehaviour(new Behaviour() {

            private int counter = 0;

            @Override
            public void action() {
                System.out.println("Agentul " + getLocalName() + " execută acțiuni. Contor: " + counter);
                counter++;
            }

            @Override
            public boolean done() {
                return counter >= 5; // Rulează 5 cicluri înainte de terminare
            }
        });
    }

    @Override
    protected void takeDown() {
        System.out.println("Agentul dinamic " + getLocalName() + " este terminat.");
    }
}