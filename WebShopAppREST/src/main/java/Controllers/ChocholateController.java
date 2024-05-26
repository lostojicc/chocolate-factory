package Controllers;
import java.util.ArrayList;

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
}
