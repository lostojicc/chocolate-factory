package Controllers;

import java.util.HashMap;
import java.util.Map;

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


        registerController(UserController.class, userController);
        registerController(ChocholateController.class, chocholateController);

        connectControllers();
    }
}