package serializer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class Serializer<T extends ISerializable> {
	private final String directoryName;
	private String fileName;
	private Class<T> classType;
	
	@SuppressWarnings("unchecked")
	public Serializer(String contextPath, Class<T> clazz){
		directoryName = contextPath;
		
		classType = clazz;
		fileName = String.format("%s/%s.csv", directoryName, classType.getSimpleName().toLowerCase());
		
		//Used for file system, in web case there is no need for dir creation
		/*
		File directory = new File(directoryName);
        if (!directory.exists()) {
            directory.mkdirs();
        }*/

		
		File file = new File(fileName);
		System.out.println(fileName);
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void ToCSV(ArrayList<T> objects) {
		StringBuilder csv = new StringBuilder();

        for (T obj : objects) {
            String line = String.join("|", obj.ToCSV());
            csv.append(line).append("\n");
        }
        
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName,true))) {
            writer.write(csv.toString());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error creating file: " + e.getMessage());
        }
	}
	
	public List<T> fromCSV() {
        List<T> objects = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] csvValues = line.split("\\|");
                T obj = null;
                try {
                    obj = classType.getDeclaredConstructor().newInstance();
                    obj.FromCSV(csvValues);
                    objects.add(obj);
                } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return objects;
    }
}
