package test;

import java.io.File;
import java.util.ArrayList;

import dao.DAO;
import models.Chocholate;
import models.ChocholateKind;
import models.ChocholateType;
import serializer.Serializer;

public class testic {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Chocholate cokoladica = new Chocholate("djoka", 0, ChocholateKind.Classic , 0, ChocholateType.Black,
				50, "dobra cokoladica", "krejzi file path....////sdfdsf/s", true, 90);
		
		DAO<Chocholate> dao = new DAO<Chocholate>("././Resources", Chocholate.class);
		
		dao.Save(cokoladica);
		
		cokoladica.setId(3);
		cokoladica.setName("nije djoka");
		
		//System.out.println(dao.GetById(1).getName());
		
		System.out.println(dao.Update(cokoladica)); 
	}

}
