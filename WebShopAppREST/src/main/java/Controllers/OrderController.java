package Controllers;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import dao.DAO;
import models.ChocholateInstance;
import models.Order;
import models.OrderState;
import models.ShoppingCart;

public class OrderController {
	private String contextPath;
	private DAO OrderDAO;
	
	private ChocholateInstanceController chochoInstanceController;
	
	public void setDependency(ChocholateInstanceController chochoInstCont) {
		this.chochoInstanceController = chochoInstCont;
	}
	
	public OrderController(String context) {
		contextPath = context;
		OrderDAO = new DAO<Order>(contextPath, Order.class);
	}
	
	public Boolean CreateOrder(ShoppingCart cart, ArrayList<Integer> chocholateIds, int factoryId) {
		try {
			String uniqueKey = GenerateUniqueIdentification();
			Instant now = Instant.now();
			Date currentDate = Date.from(now);
		
			Order order = new Order(uniqueKey, chocholateIds, factoryId, currentDate, cart.getPrice(), cart.getUserId(), OrderState.Processing);
			OrderDAO.Save(order);
		
			return true;
		}
		catch(Exception e) {
			return false;
		}
	}
	
	private String GenerateUniqueIdentification() {
		UUID uuid = UUID.randomUUID();
        
        String uniqueString = uuid.toString().replace("-", "");
        
        if (uniqueString.length() < 10) {
            uniqueString += "0".repeat(10 - uniqueString.length());
        }

        return uniqueString.substring(0, 10);
	}
	
	
	
}
