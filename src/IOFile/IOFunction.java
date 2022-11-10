package IOFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class IOFunction<T> {
    public IOFunction() {

    }
    public List<T> readFile(String path) {
        File file = new File(path);
        List<T> list = new ArrayList<>();
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            if (fileInputStream.available() > 0) {
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                list = (List<T>) objectInputStream.readObject();
            }
        } catch ( ClassNotFoundException | IOException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

    public void writeFile(List<T> list, String path) {
        File file = new File(path);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(list);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
