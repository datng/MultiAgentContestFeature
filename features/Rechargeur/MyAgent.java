import massim.javaagents.Agent;
import massim.javaagents.agents.CityUtil;
import eis.iilang.Percept;
import eis.iilang.Action;
import eis.iilang.Parameter;
import massim.javaagents.agents.util.map.Location;
import massim.javaagents.agents.util.map.Route;
import java.util.ArrayList;
import java.util.List;

public class MyAgent extends Agent {

	public Role role = Role.Rechargeur;
	public double lon=0;
	public double lat=0;
	
	
	private boolean checkBattery(Location from, Location to){
		int currentBattery = 0;
		int speed = 0;
		String role = "";
		Route route = new Route();
		route.addPoint(to);
		route.addPoint(from);
		
		for(Percept percept : getAllPercepts()){
			//Recuperer le role et la position de l'agent
			if(percept.getName().equalsIgnoreCase("entity")){
				if(this.getName().compareTo(percept.getParameters().get(0).toString())==0){
					role = percept.getParameters().get(4).toString();
					lat = Integer.parseInt(percept.getParameters().get(2).toString());
					lon = Integer.parseInt(percept.getParameters().get(3).toString());
					
				}
			}
			
			//recuperer sa vitesse en focntion de son role
			if(percept.getName().equalsIgnoreCase("role")){
				if(role.compareTo(percept.getParameters().get(0).toString())==0){
					speed = Integer.parseInt(percept.getParameters().get(1).toString());
				}
			}
			
			//recuperer la battery courante de l'agent
			if(percept.getName().equalsIgnoreCase("charge")){
				currentBattery = Integer.parseInt(percept.getParameters().get(0).toString());
			}
		}
		
		//Si la battery de l'agent est inferieur à la longueur de la route plus un certain seuil
		//on change son role en Rechargeur
		if(currentBattery<= route.getRouteDuration(speed)){
			this.role = Role.Rechargeur;
			return false;
		}
		
		return true;
	}
	@Override
	public Action step() {
		original();
		if(this.role == Role.Rechargeur){
			List<Percept> chargingStationList = new ArrayList<Percept>();
			for(Percept percept : getAllPercepts()){
				if(percept.getName().equalsIgnoreCase("chargingStation")){
					chargingStationList.add(percept);
				}
			}
			int dist = Integer.MAX_VALUE;
			double clon=0,clat=0;
			String chargeStationName = "";
			for(Percept p : chargingStationList){
				chargeStationName = p.getParameters().get(0).toString();
				clat = Double.parseDouble(p.getParameters().get(1).toString());
				clon = Double.parseDouble(p.getParameters().get(2).toString());
				Location from = new Location(clon, clat);
				Location to = new Location(lon, lat);
				Route r = new Route();
				r.addPoint(from);
				r.addPoint(to);
				
				if(dist>r.getRouteLength()){
					dist = r.getRouteLength();
					chargeStationName = p.getParameters().get(0).toString();
				}
			}
			return CityUtil.action("goto", "facility="+chargeStationName);
		}
		
		if(this.role == Role.JobSelector){
		
			for(Percept percept : getAllPercepts()){
				// On ne considère uniquement les pricedJob pour la simulation minimale
				if(percept.getName().equalsIgnoreCase("pricedJob")){
					Parameter itemsForJob = percept.getParameters().get(5);
					System.out.println("========================================");
					System.out.println(itemsForJob.toString());
				}
				if(percept.getName().equalsIgnoreCase("shop")){
					Parameter name = percept.getParameters().get(0);
					System.out.println(name.toString());
				}
				if(percept.getName().equalsIgnoreCase("product")){
					List<Parameter> param = percept.getParameters();
					for(Parameter p : param){
						System.out.print(p.toString()+" ");
					}
				}
			}
		}
		
		return CityUtil.action("skip");
	}

}
