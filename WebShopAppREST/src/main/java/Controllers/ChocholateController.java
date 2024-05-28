package Controllers;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import dao.DAO;
import models.Chocholate;
import models.ChocholateKind;
import models.ChocholateType;


public class ChocholateController {
private String contextPath;
	
	private DAO CHODAo;
	
	public void setDependency() {
		
	}
	
	public ChocholateController(String context) {
		contextPath = context;
		CHODAo = new DAO<Chocholate>(contextPath, Chocholate.class);
	}
	public ArrayList<Chocholate> GetAll(){
		return CHODAo.GetAll();
	}
	
	public ArrayList<Chocholate> getByFactoryId(int id){
		ArrayList<Chocholate> chocholates = new ArrayList<>();
		
		for (Chocholate chocholate : (ArrayList<Chocholate>)CHODAo.GetAll()) {
			if(chocholate.getFabricId() == id)
				chocholates.add(chocholate);
		}
		
		return chocholates;
	}
}
