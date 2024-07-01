package Controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import dao.DAO;
import dto.SearchOrderParamsDTO;
import models.ChocholateInstance;
import models.Factory;
import models.Order;
import models.OrderState;
import models.ShoppingCart;

public class OrderController {
	private String contextPath;
	private DAO OrderDAO;
	
	private ChocholateInstanceController chochoInstanceController;
	private CustomerController customerController;
	private FactoryController factoryController;
	
	public void setDependency(ChocholateInstanceController chochoInstCont, CustomerController customerContr, FactoryController factoryController) {
		this.chochoInstanceController = chochoInstCont;
		this.customerController = customerContr;
		this.factoryController = factoryController;
	}
	
	public OrderController(String context) {
		contextPath = context;
		OrderDAO = new DAO<Order>(contextPath, Order.class);
	}
	
	public Order GetByid(int id) {
		return (Order) OrderDAO.GetById(id);
	}
	
	public Boolean Update(Order order) {
		return OrderDAO.Update(order);
	}
	
	public Boolean CreateOrder(ShoppingCart cart, ArrayList<Integer> chocholateIds, int factoryId) {
		try {
			String uniqueKey = GenerateUniqueIdentification();
			Instant now = Instant.now();
			Date currentDate = Date.from(now);
		
			Order order = new Order(uniqueKey, chocholateIds, factoryId, currentDate, cart.getPrice(), cart.getUserId(), OrderState.Processing);
			
			double discount = customerController.GetDiscount(order.getUserId());
			
			customerController.AddPointsForOrder(order);
			
			order.setPrice(order.getPrice() - order.getPrice() * discount);
			
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
	
	public ArrayList<Order> GetAll(){
		return OrderDAO.GetAll();
	}
	
	public ArrayList<Order> GetByUserId(int userId){
		ArrayList<Order> list = new ArrayList<Order>();
		
		for(Order o : this.GetAll()) {
			if(o.getUserId() == userId) {
				list.add(o);
			}
		}
		
		return list;
	} 
	
	public ArrayList<Order> GetAcceptedByUserAndFactoryId(int userId, int factoryId){
		ArrayList<Order> list = new ArrayList<Order>();
		
		for(Order o : this.GetAll()) {
			if(o.getUserId() == userId && o.getFactoryId() == factoryId && o.getState() == OrderState.Accepted) {
				list.add(o);
			}
		}
		
		return list;
	} 
	
	
	
	public ArrayList<Order> GetByFactoryId(int factoryId){
		ArrayList<Order> list = new ArrayList<Order>();
		
		for(Order o : this.GetAll()) {
			if(o.getFactoryId() == factoryId) {
				list.add(o);
			}
		}
		
		return list;
	} 
	
	public Boolean CancelOrder(int orderId) {
		Order order = this.GetByid(orderId);
		if(order== null)
			return false;
		
		order.setState(OrderState.Cancelled);
		
		customerController.DecreasePointsForOrder(order);
		
		this.Update(order);
		return true;
	}
	
	public Boolean RejectOrAcceptOrder(int orderId, int logic) {
		Order order = this.GetByid(orderId);
		if(order== null)
			return false;
		
		if(logic == 0)
			order.setState(OrderState.Rejected);
		if(logic == 1)
			order.setState(OrderState.Accepted);
		
		return this.Update(order);
	}
	
	public ArrayList<Order> SearchByUserId(int userId, SearchOrderParamsDTO searchParams){
		ArrayList<Order> list = new ArrayList<Order>();
		
		for(Order o : this.GetAll()) {
			if(o.getUserId() == userId && CheckSearchParams(o, searchParams)) {
				list.add(o);
			}
		}
		
		return list;
	}
	
	public ArrayList<Order> SearchByFactoryId(int factoryId, SearchOrderParamsDTO searchParams){
		ArrayList<Order> list = new ArrayList<Order>();
		
		for(Order o : this.GetAll()) {
			if(o.getFactoryId() == factoryId && CheckSearchParams(o, searchParams)) {
				list.add(o);
			}
		}
		
		return list;
	}
	
	public Boolean CheckSearchParams(Order o, SearchOrderParamsDTO searchParams) {
		Factory factory = factoryController.getById(o.getFactoryId());
		
		if(!searchParams.getFactoryName().isEmpty()) {
			String facName = factory.getName().toUpperCase();
			String search = searchParams.getFactoryName().toUpperCase();
			if(!facName.contains(search))
				return false;
		}
		
		if(!searchParams.getMinPrice().isEmpty()) {
			double minPrice = Double.parseDouble(searchParams.getMinPrice());
			if(o.getPrice() < minPrice) {
				return false;
			}
		}
		
		if(!searchParams.getMaxPrice().isEmpty()) {
			double maxPrice = Double.parseDouble(searchParams.getMaxPrice());
			if(o.getPrice() > maxPrice) {
				return false;
			}
		}
		
		if(!searchParams.getMinDate().isEmpty()) {
	        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	        try {
	            Date minDate = formatter.parse(searchParams.getMinDate());
	            if(o.getDateTime().before(minDate)) {
	            	return false;
	            }
			} catch (Exception e) {
            e.printStackTrace();
			}
		}
		
		if(!searchParams.getMaxDate().isEmpty()) {
	        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	        try {
	            Date maxDate = formatter.parse(searchParams.getMaxDate());
	            if(o.getDateTime().after(maxDate)) {
	            	return false;
	            }
			} catch (Exception e) {
            e.printStackTrace();
			}
		}
		
		return true;
	}
	
}
