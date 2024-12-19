package Main;

import jade.core.Agent;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;

public class MainContainer {
    public static void main(String[] args) {
        try {
            // Inițializarea platformei JADE
            Runtime runtime = Runtime.instance();
            Profile profile = new ProfileImpl(); // Profil pentru container
            AgentContainer mainContainer = runtime.createMainContainer(profile);

            // Crearea agenților
            AgentController employerAgent = mainContainer.createNewAgent("Employer", EmployerAgentWithGUI.class.getName(), null);
            employerAgent.start();

            // Crearea agentului JobSeeker
            AgentController jobSeekerAgent = mainContainer.createNewAgent("JobSeeker", JobSeekerAgent.class.getName(), null);
            jobSeekerAgent.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
