package Lab6;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;

public class Main {
    public static void main(String[] args) {
        try {
            // Inițializează platforma JADE
            Runtime runtime = Runtime.instance();
            runtime.setCloseVM(true);

            // Setări pentru containerul principal
            Profile mainProfile = new ProfileImpl();
            mainProfile.setParameter(Profile.MAIN, "true");
            mainProfile.setParameter(Profile.GUI, "true");
            AgentContainer mainContainer = runtime.createMainContainer(mainProfile);

            // Creare agenți în containerul principal
            AgentController displayAgent = mainContainer.createNewAgent(
                    "DisplayAgent", "DisplayAgent", null); // Nume + clasa agentului
            AgentController readFileAgent = mainContainer.createNewAgent(
                    "ReadFileMobileAgent", "ReadFileMobileAgent", null);

            displayAgent.start();
            readFileAgent.start();

            // Setări pentru containerul secundar
            Profile secondaryProfile = new ProfileImpl();
            secondaryProfile.setParameter(Profile.MAIN, "false");
            secondaryProfile.setParameter(Profile.CONTAINER_NAME, "secondary");
            ContainerController secondaryContainer = runtime.createAgentContainer(secondaryProfile);

            // Creare agent în containerul secundar
            AgentController filePathAgent = secondaryContainer.createNewAgent(
                    "FilePathInfoAgent", "FilePathInfoAgent", null);

            filePathAgent.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
