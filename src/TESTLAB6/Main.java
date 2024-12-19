package TESTLAB6;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.ContainerController;

public class Main {
    public static void main(String[] args) {
        try {
            // Configurarea mediului JADE
            Runtime runtime = Runtime.instance();
            Profile profile = new ProfileImpl();
            profile.setParameter(Profile.GUI, "true"); // Activează interfața GUI JADE

            // Crearea containerului principal
            ContainerController mainContainer = runtime.createMainContainer(profile);

            // Lansarea agentului CreatorAgent
            String agentName = "creator";
            mainContainer.createNewAgent(agentName, CreatorAgent.class.getName(), null).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

