
import java.io.IOException;
import java.util.Collection;
import java.util.Map;

import massim.javaagents.Agent;
import massim.javaagents.agents.CityUtil;
import eis.EILoader;
import eis.EnvironmentInterfaceStandard;
import eis.exceptions.NoEnvironmentException;
import eis.exceptions.PerceiveException;
import eis.iilang.Action;
import eis.iilang.Percept;

/**
 * Hello world!
 *
 */
public class Simulation 
{
    public static void main( String[] args )
    {
        Agent agent = new MyAgent("AgentA1", "A");
        
        //System.out.println(agent.getName());
        
        //creation d'une instance de l'environnement
        EnvironmentInterfaceStandard ei = null;
        try {
        String cn = "massim.eismassim.EnvironmentInterface";
        ei = EILoader.fromClassName(cn);
        } catch (IOException e) {
        // TODO handle the exception
        }
        
        //lier l'agent a l'environnement
        try {
			ei.registerAgent(agent.getName());
		} catch (Exception e) {
			// TODO: handle exception
		}
        
        //associer l'agent a un vehicule
        try {
			ei.associateEntity(agent.getName(), "connectionA1");
		} catch (Exception e) {
			// TODO: handle exception
		}
        
        //commencer l'execution
        try {
			ei.start();
		} catch (Exception e) {
			// TODO: handle exception
		}
      
//        
//        try {
//			ei.getAllPercepts(agent.getName());
//			
//		} catch (PerceiveException | NoEnvironmentException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//        try {
//			Collection<Percept> percepts = ei.getAllPercepts(agent.getName());
//			
//			
//		} catch (PerceiveException | NoEnvironmentException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
        Action a = CityUtil.action("goto","facility=shop1");
        while(true){
        	 try {
				ei.performAction(agent.getName(), a);
			} catch (Exception e) {
				// TODO: handle exception
			}
         }
        
    }
}
