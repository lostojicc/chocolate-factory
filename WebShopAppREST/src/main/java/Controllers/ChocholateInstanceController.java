package Controllers;

import java.util.ArrayList;

import dao.DAO;
import models.Chocholate;
import models.ChocholateInstance;

public class ChocholateInstanceController {
	private String contextPath;
	
	private DAO ChocholateInstanceDAO;
	
	public void setDependency() {
		
	}
	
	public ChocholateInstanceController(String context) {
		contextPath = context;
		ChocholateInstanceDAO = new DAO<ChocholateInstance>(contextPath, ChocholateInstance.class);
	}
	@SuppressWarnings("unchecked")
	public ArrayList<ChocholateInstance> GetAll(){
		return ChocholateInstanceDAO.GetAll();
	}
	
	@SuppressWarnings("unchecked")
	public void Save(ChocholateInstance chocoInstance) {
		ChocholateInstanceDAO.Save(chocoInstance);
	}
	
	public ArrayList<ChocholateInstance> GetByCartId(){
		ArrayList<ChocholateInstance> chocholates = new ArrayList<ChocholateInstance>();
		
		for(ChocholateInstance choco : this.GetAll()) {
			chocholates.add(choco);
		}
		
		return chocholates;
	}
	
	public ChocholateInstance GetByCartAndChocholateId(int cartId, int chocholateId) {	
		for(ChocholateInstance choco : this.GetAll()) {
			if(choco.getCartId() == cartId && choco.getChocholateId() == chocholateId) {
				return choco;
			}
		}
		
		return null;
	}
	
	public void Update(ChocholateInstance choco) {
		ChocholateInstanceDAO.Update(choco);
	}
	
}
