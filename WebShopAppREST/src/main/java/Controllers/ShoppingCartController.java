package Controllers;

import java.util.ArrayList;

import dao.DAO;
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
		
		ChocholateInstance oldChoco = chochoInstanceContr.GetByCartAndChocholateId(chochoInstance.getCartId(), chochoInstance.getChocholateId());
		if(oldChoco == null) {
			chochoInstanceContr.Save(chochoInstance);
		}
		else {
			oldChoco.setQuantity(oldChoco.getQuantity() + chochoInstance.getQuantity());
			chochoInstanceContr.Update(oldChoco);
		}
		
		return true;
	}
}
