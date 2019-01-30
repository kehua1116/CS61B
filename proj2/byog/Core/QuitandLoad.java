package byog.Core;

import byog.TileEngine.TETile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

//@source SaveDemo\Main.java

public class QuitandLoad implements java.io.Serializable {

    private static final long serialVersionUID = 45498234798734234L;

    public static void serialize(TETile[][] world, int[] point, int[][] position, int[] player) {
        QuitandLoad2 object = new QuitandLoad2(world, point, position, player);
        File f = new File("./quitandload.txt");
        try {
            if (!f.exists()) {
                f.createNewFile();
            }
            FileOutputStream fs = new FileOutputStream(f);
            ObjectOutputStream os = new ObjectOutputStream(fs);
            os.writeObject(object);
            os.close();
        } catch (FileNotFoundException e) {
            System.out.println("file not found");
            System.exit(0);
        } catch (IOException e) {
            System.out.println(e);
            System.exit(0);
        }
    }

    public static QuitandLoad2 deserialize() {
        File f = new File("./quitandload.txt");
        if (f.exists()) {
            try {
                FileInputStream fs = new FileInputStream(f);
                ObjectInputStream os = new ObjectInputStream(fs);
                QuitandLoad2 w = (QuitandLoad2) os.readObject();
                os.close();
                return w;
            } catch (FileNotFoundException e) {
                System.out.println("file not found");
                System.exit(0);
            } catch (IOException e) {
                System.out.println(e);
                System.exit(0);
            } catch (ClassNotFoundException e) {
                System.out.println("class not found");
                System.exit(0);
            }
        }
        return new QuitandLoad2();
    }

}
