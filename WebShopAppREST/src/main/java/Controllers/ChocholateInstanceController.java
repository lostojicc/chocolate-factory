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
		ArrayList<ChocholateInstance> list = ChocholateInstanceDAO.GetAll();
		ArrayList<ChocholateInstance> newList = new ArrayList<ChocholateInstance>();
		for(ChocholateInstance chocoInstance: list) {
			if(!chocoInstance.isDeleted()) 
				newList.add(chocoInstance);
		}
		
		return newList;
	}
	
	public void DeleteByChocholateId(int chocolateId) {
		for(ChocholateInstance choco: this.GetNotCheckedByChocolateId(chocolateId)) {
			ChocholateInstanceDAO.Delete(choco);
		}
	}
	
	@SuppressWarnings("unchecked")
	public void Save(ChocholateInstance chocoInstance) {
		ChocholateInstanceDAO.Save(chocoInstance);
	}
	
	public ChocholateInstance GetById(int id) {
		return (ChocholateInstance) ChocholateInstanceDAO.GetById(id);
	}
	
	public ArrayList<ChocholateInstance> GetNotCheckedByCartId(int cartId){
		ArrayList<ChocholateInstance> chocholates = new ArrayList<ChocholateInstance>();
		
		for(ChocholateInstance choco : this.GetAll()) {
			if(choco.getCartId() == cartId && !choco.getCheckedOut())
				chocholates.add(choco);
		}
		
		return chocholates;
	}
	
	public ChocholateInstance GetByNotCheckedCartAndChocholateId(int cartId, int chocholateId) {	
		for(ChocholateInstance choco : this.GetAll()) {
			if(choco.getCartId() == cartId && choco.getChocholateId() == chocholateId && !choco.getCheckedOut()) {
				return choco;
			}
		}
		
		return null;
	}
	
	public ArrayList<ChocholateInstance> GetNotCheckedByChocolateId(int chocolateId){
		ArrayList<ChocholateInstance> chocholates = new ArrayList<ChocholateInstance>();
		
		for(ChocholateInstance choco : this.GetAll()) {
			if(choco.getChocholateId() == chocolateId && !choco.getCheckedOut())
				chocholates.add(choco);
		}
		
		return chocholates;
	}
	
	public void Update(ChocholateInstance choco) {
		ChocholateInstanceDAO.Update(choco);
	}
	
	public Boolean Delete(ChocholateInstance choco) {
		return ChocholateInstanceDAO.Delete(choco);
	}
}
