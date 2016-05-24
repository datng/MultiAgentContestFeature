import massim.javaagents.Agent;
import massim.javaagents.agents.CityUtil;
import eis.iilang.Action;
import eis.iilang.Percept;

public class MyAgent extends Agent {

	public MyAgent(String name, String team) {
		super(name, team);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Action step() {
		// TODO deliberate and Parameter action	
		for(Percept p : getAllPercepts())
			handlePercept(p);
		
		return CityUtil.action("goto", "facility=shop1");
	}

	@Override
	public void handlePercept(Percept p) {
		
	}

}
