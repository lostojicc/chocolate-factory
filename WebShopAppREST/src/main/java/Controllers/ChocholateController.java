package Controllers;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.swing.colorchooser.ColorChooserComponentFactory;

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
	
	public Chocholate GetById(int id){
		return (Chocholate) CHODAo.GetById(id);
	}

	public void Save(Chocholate chocolate) {
		CHODAo.Save(chocolate);
	}
	
	public Boolean Update(Chocholate chocolate) {
		return CHODAo.Update(chocolate);
	}
	
	public Boolean Delete(int id) {
		return CHODAo.Delete(GetById(id));
	}
	
	public ArrayList<Chocholate> getByFactoryId(int id){
		ArrayList<Chocholate> chocholates = new ArrayList<>();
		
		for (Chocholate chocholate : (ArrayList<Chocholate>)CHODAo.GetAll()) {
			if(chocholate.getFactoryId() == id)
				chocholates.add(chocholate);
		}
		
		return chocholates;
	}
	
	public Boolean ValidateChocolate(Chocholate chocolate) {
		if(chocolate.getName() == null || chocolate.getName().equals("")) {
			return false;
		}
		if(chocolate.getPrice() == 0.0) {
			return false;
		}
		if(chocolate.getGrams() == 0.0) {
			return false;
		}
		if(chocolate.getDescription() == null || chocolate.getDescription().equals("")) {
			return false;
		}
		if(chocolate.getImagePath() == null || chocolate.getImagePath().equals("")) {
			return false;
		}
		
		return true;
	}
}
	