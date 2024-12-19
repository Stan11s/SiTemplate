package Lab5Tema1;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;

public class MainContainer {
    public static void main(String[] args) {
        try {
            Runtime runtime = Runtime.instance();
            Profile profile = new ProfileImpl();
            profile.setParameter(Profile.MAIN_HOST, "localhost");
            ContainerController container = runtime.createMainContainer(profile);

            // Creează agentul distribuitor
            AgentController distribuitorAgent = container.createNewAgent("DistribuitorAgent", "Lab5Tema1.DistribuitorAgent", null);
            distribuitorAgent.start();

            // Creează cei 3 agenți de calcul
            for (int i = 1; i <= 3; i++) {
                AgentController calcAgent = container.createNewAgent("CalcAgent" + i, "Lab5Tema1.CalcAgent", null);
                calcAgent.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
