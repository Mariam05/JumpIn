package JumpIn;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;

/*
 * This class will generate the game again by using load method 
 * and will also save the state of the game by using save method
 */
public class ResourceManager {
	
	public static void save(Serializable data, String fileName) throws Exception {
		try(ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(Paths.get(fileName)))){
			oos.writeObject(data);
		}
	}
	
	public static Object load(String fileName) throws Exception {
		try(ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(Paths.get(fileName)))){
			return ois.readObject();
		}
	}
	
}
