package serializer;

public interface ISerializable {
	String[] ToCSV();
	
	void FromCSV(String[] values);
}
