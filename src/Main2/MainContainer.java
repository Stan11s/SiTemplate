package Main2;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;

public class MainContainer {
    public static void main(String[] args) {
        // Configurare platformă JADE
        Runtime rt = Runtime.instance();
        Profile profile = new ProfileImpl();
        profile.setParameter(Profile.MAIN_HOST, "localhost");
        
        AgentContainer mainContainer = rt.createMainContainer(profile);
        
        try {
            // Crearea agenților
            AgentController agent1 = mainContainer.createNewAgent("Agent_1", MyAgent.class.getName(), null);
            AgentController agent2 = mainContainer.createNewAgent("Agent_2", MyAgent.class.getName(), null);
            AgentController agent3 = mainContainer.createNewAgent("Agent_3", MyAgent.class.getName(), null);
            
            // Lansarea agenților
            agent1.start();
            agent2.start();
            agent3.start();
        } catch (StaleProxyException e) {
            e.printStackTrace();
        }
    }
}
