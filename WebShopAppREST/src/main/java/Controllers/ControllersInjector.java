package Controllers;

import java.util.HashMap;
import java.util.Map;

import models.Location;

public class ControllersInjector {
    private Map<Class<?>, Object> controllers = new HashMap<>();
    private String contextPath;

    private void registerController(Class<?> clazz, Object controller) {
        controllers.put(clazz, controller);
    }

    public <T> T getController(Class<T> clazz) {
        return clazz.cast(controllers.get(clazz));
    }

    public ControllersInjector(String context) {
    	contextPath = context;
    	initializeControllers();
    }
    
    //-------------------------------------------------
    
    
    private void connectControllers() {
        UserController userController = getController(UserController.class);
        ChocholateController chocholateController = getController(ChocholateController.class);
        FactoryController factoryController = getController(FactoryController.class);
        LocationController locationController = getController(LocationController.class);
        AddressController addressController = getController(AddressController.class);
        CommentController commentController = getController(CommentController.class);
        

        ShoppingCartController shopingCartControler = getController(ShoppingCartController.class);
        ChocholateInstanceController chochoInstanceControler = getController(ChocholateInstanceController.class);
        OrderController orderController = getController(OrderController.class);
        CustomerController customerController = getController(CustomerController.class);
        
        locationController.setDependency(addressController);
        factoryController.setDependency(locationController, userController, chocholateController, addressController);
        userController.setDependency(customerController);
        chocholateController.setDependency();
        shopingCartControler.setDependency(userController,chochoInstanceControler,chocholateController,orderController);
        customerController.setDependency(userController);
        orderController.setDependency(chochoInstanceControler, customerController, factoryController);
        commentController.SetDependency(factoryController);
    }

    private void initializeControllers() {
        UserController userController = new UserController(contextPath);
        ChocholateController chocholateController = new ChocholateController(contextPath);
        FactoryController factoryController = new FactoryController(contextPath);
        LocationController locationController = new LocationController(contextPath);
        AddressController addressController = new AddressController(contextPath);
        CommentController commentController = new CommentController(contextPath);
        ShoppingCartController shoppingCartControler = new ShoppingCartController(contextPath);
        ChocholateInstanceController chochoInstanceControler = new ChocholateInstanceController(contextPath);
        OrderController orderController = new OrderController(contextPath);
        CustomerController customerController = new CustomerController(contextPath);

        registerController(UserController.class, userController);
        registerController(ChocholateController.class, chocholateController);
        registerController(FactoryController.class, factoryController);
        registerController(LocationController.class, locationController);
        registerController(AddressController.class, addressController);
        registerController(CommentController.class, commentController);
        registerController(ShoppingCartController.class, shoppingCartControler);
        registerController(ChocholateInstanceController.class, chochoInstanceControler);
        registerController(OrderController.class, orderController);
        registerController(CustomerController.class, customerController);
        
        connectControllers();
    }
}