package LAB5;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;

public class MainContainer {
    public static void main(String[] args) {
        try {
            // Inițializează platforma JADE
            Runtime runtime = Runtime.instance();
            Profile profile = new ProfileImpl();
            profile.setParameter(Profile.MAIN_HOST, "localhost");
            profile.setParameter(Profile.MAIN_PORT, "1099");
            ContainerController container = runtime.createMainContainer(profile);

            // Creează și lansează agenții
            AgentController sellerAgent = container.createNewAgent("SellerAgent", "LAB5.BookSellerAgent", null);
            sellerAgent.start();

            // Argument pentru agentul cumpărător (titlul cărții dorite)
            Object[] buyerArgs = {"The Great Gatsby"};
            AgentController buyerAgent = container.createNewAgent("BuyerAgent", "LAB5.BookBuyerAgent", buyerArgs);
            buyerAgent.start();

            System.out.println("Agenții au fost lansați.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
