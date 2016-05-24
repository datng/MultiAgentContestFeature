import massim.javaagents.Agent;
import massim.javaagents.agents.CityUtil;
import eis.iilang.Action;
import eis.iilang.Percept;

import java.util.HashMap;
import java.util.Stack;

public class MyAgent extends Agent {

	private boolean startRole = false;
	private Role r;

	public MyAgent(String name, String team) {
		super(name, team);



	}

	@Override
	public Action step() {	
		if (!startRole){
			for(Percept p : getAllPercepts())
				if(p.getName() == "entity"){
					String selfType = p.getParameters().getLast().toString();
					// selfType contains "truck", "drone", "motorbike", "car"
					if (selfType == "truck"){
						this.r = Role.Acheteur;
						break;
					}else if (selfType == "drone"){
						this.r = Role.JobDeliver;
						break;
					}else if (selfType == "motorbike"){
						this.r = Role.Acheteur;
						break;
					}else if (selfType == "car"){
						this.r = Role.Assembleur;
						break;
					}else{
						System.out.println("FAIL SUR START ROLE");
					}
				
				}
		}

		return CityUtil.action("goto", "facility=shop1");
	}


	@Override
	public void handlePercept(Percept p) {

	}

}
