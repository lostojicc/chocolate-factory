package Controllers;

import java.util.ArrayList;

import dao.DAO;
import dto.ChocholateInstanceDTO;
import models.Chocholate;
import models.ChocholateInstance;
import models.ShoppingCart;
import models.User;

public class ShoppingCartController {
	private String contextPath;
	private DAO ShoppingCartDAO;
	
	private UserController userControler;
	private ChocholateInstanceController chochoInstanceContr;
	private ChocholateController chochoControler;
	
	public void setDependency(UserController userControler, ChocholateInstanceController chochoInstContr,ChocholateController chochoControler) {
		this.userControler = userControler;
		this.chochoInstanceContr = chochoInstContr;
		this.chochoControler = chochoControler;
	}
	
	public ShoppingCartController(String context) {
		contextPath = context;
		ShoppingCartDAO = new DAO<ShoppingCart>(contextPath, ShoppingCart.class);
	}
	
	public Boolean Save(ShoppingCart shoppingCart) {
		if(userControler.getById(shoppingCart.getUserId()) == null) {
			return false;
		}
		if(this.GetByUserId(shoppingCart.getUserId()) != null) {
			return false;
		}
		
		ShoppingCartDAO.Save(shoppingCart);
		
		return true;
	}
	
	@SuppressWarnings("unchecked")
	public void Update(ShoppingCart shCart) {
		ShoppingCartDAO.Update(shCart);
	}
	
	public ShoppingCart GetById(int id) {
		return (ShoppingCart) ShoppingCartDAO.GetById(id);
	}
	
	public ShoppingCart GetByUsername(String username) {
		User user = userControler.GetByUsername(username);
		if(user == null)
			return null;
		
		return this.GetByUserId(user.getId());
	}
	
	@SuppressWarnings("unchecked")
	public ShoppingCart GetByUserId(int userId) {
		for(ShoppingCart sh: (ArrayList<ShoppingCart>)ShoppingCartDAO.GetAll()) {
			if(sh.getUserId() == userId) {
				return sh;
			}
		}
		
		return null;
	}
	
	public Boolean AddToCart(ChocholateInstance chochoInstance) {
		Chocholate chocholate = chochoControler.GetById(chochoInstance.getChocholateId());
		ShoppingCart shoppingCart = this.GetById(chochoInstance.getCartId());
		
		if(shoppingCart == null) {
			return false;
		}
		
		if(chocholate.getQuantity() < chochoInstance.getQuantity()) {
			return false;
		}
		
		shoppingCart.setPrice(shoppingCart.getPrice() + chochoInstance.getQuantity() * chocholate.getPrice());
		chocholate.setQuantity(chocholate.getQuantity() - chochoInstance.getQuantity());
		
		chochoControler.Update(chocholate);
		this.Update(shoppingCart);
		
		ChocholateInstance oldChoco = chochoInstanceContr.GetByNotCheckedCartAndChocholateId(chochoInstance.getCartId(), chochoInstance.getChocholateId());
		if(oldChoco == null) {
			chochoInstanceContr.Save(chochoInstance);
		}
		else {
			oldChoco.setQuantity(oldChoco.getQuantity() + chochoInstance.getQuantity());
			chochoInstanceContr.Update(oldChoco);
		}
		
		return true;
	}
	
	public Boolean UpdateChocholateInstance(ChocholateInstanceDTO chochoInstanceDTO) {
		Chocholate chocholate = chochoControler.GetById(chochoInstanceDTO.getChocholateId());
		ShoppingCart shoppingCart = this.GetById(chochoInstanceDTO.getCartId());
		ChocholateInstance oldChocoInstance = chochoInstanceContr.GetById(chochoInstanceDTO.getId());
		
		if(shoppingCart == null || chocholate == null || oldChocoInstance == null) {
			return false;
		}
		
		int maxQuantity = oldChocoInstance.getQuantity() + chocholate.getQuantity();
		
		if(chochoInstanceDTO.getQuantity() < 0 || chochoInstanceDTO.getQuantity() > maxQuantity) {
			return false;
		}
		
		chocholate.setQuantity(maxQuantity - chochoInstanceDTO.getQuantity());
		oldChocoInstance.setQuantity(chochoInstanceDTO.getQuantity());
		
		shoppingCart.setPrice(shoppingCart.getPrice() - chochoInstanceDTO.getTotalPrice());
		shoppingCart.setPrice(shoppingCart.getPrice() + oldChocoInstance.getQuantity() * chocholate.getPrice());
		
		this.Update(shoppingCart);
		chochoControler.Update(chocholate);
		chochoInstanceContr.Update(oldChocoInstance);
		
		return true;
	}
	
	public Boolean DeleteChocholateInstance(int chochoInstanceId) {
		ChocholateInstance chocoInstance = chochoInstanceContr.GetById(chochoInstanceId);
		Chocholate chocholate = chochoControler.GetById(chocoInstance.getChocholateId());
		ShoppingCart shoppingCart = this.GetById(chocoInstance.getCartId());
		
		
		if(shoppingCart == null || chocholate == null || chocoInstance == null) {
			return false;
		}
		
		chocholate.setQuantity(chocholate.getQuantity() + chocoInstance.getQuantity());
		shoppingCart.setPrice(shoppingCart.getPrice() - chocoInstance.getQuantity() * chocholate.getPrice());		
		
		if(!chochoInstanceContr.Delete(chocoInstance)) {
			return false;
		}
		
		this.Update(shoppingCart);
		chochoControler.Update(chocholate);
		
		return true;
	}
	
	
}
