/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wayerless;

import java.io.*;

/**
 *
 * @author Novak
 */
public class IOFile {

    public Object loadFile(String filename) throws IOException {
        Object ob = null;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            ob = ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("File not Found");
            ob = null;
        } catch (ClassNotFoundException ex) {
            System.out.println("No class found");
            ob = null;
        }
        return ob;
    }

    public void saveFile(Object o, String filename) throws IOException {
        try (FileOutputStream fout = new FileOutputStream(filename)) {
            ObjectOutputStream oout = new ObjectOutputStream(fout);
            oout.writeObject(o);
            oout.flush();
            System.out.println("File Saved");
        } catch (IOException ex) {
            System.out.println("Eror File");
        }
    }
}
