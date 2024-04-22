package test;

import java.io.File;
import java.util.ArrayList;

import models.Chocholate;
import models.ChocholateKind;
import models.ChocholateType;
import serializer.Serializer;

public class testic {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Chocholate cokoladica = new Chocholate("djoka", 0, ChocholateKind.Classic , 0, ChocholateType.Black,
				50, "dobra cokoladica", "krejzi file path....////sdfdsf/s", true, 90);
		
		Serializer<Chocholate> serilaizer = new Serializer<Chocholate>("././Resources", Chocholate.class);
		
		ArrayList<Chocholate> list = new ArrayList<Chocholate>();
		list.add(cokoladica);
		
		serilaizer.ToCSV(list);
		
		list = (ArrayList<Chocholate>) serilaizer.fromCSV();
		System.out.println(list.size());
	
	}

}
