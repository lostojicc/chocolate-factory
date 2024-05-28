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

        userController.setDependency(/*npr chocholateController*/);
        chocholateController.setDependency();
    }

    private void initializeControllers() {
        UserController userController = new UserController(contextPath);
        ChocholateController chocholateController = new ChocholateController(contextPath);
        FactoryController factoryController = new FactoryController(contextPath);
        LocationController locationController = new LocationController(contextPath);
        AddressController addressController = new AddressController(contextPath);

        registerController(UserController.class, userController);
        registerController(ChocholateController.class, chocholateController);
        registerController(FactoryController.class, factoryController);
        registerController(LocationController.class, locationController);
        registerController(AddressController.class, addressController);

        connectControllers();
    }
}