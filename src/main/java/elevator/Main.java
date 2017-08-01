package elevator;





import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;

public class Main {
    private static  int numberPassengers;
    private static  int numberFloor;
    private static int capacity;
    public static final String PATH_TO_PROPERTIES = "src/main/resources/config.properties";
    public static final Logger LOGGER = Logger.getLogger(Main.class);

    public static void main(String[] args) throws InterruptedException {
        FileInputStream fileInputStream;
        List<Passenger> mansList= new ArrayList<Passenger>();
        List<Floor> floorList=new ArrayList<Floor>();
        Properties prop = new Properties();
        try {
            //We address to a file and we receive the data
            fileInputStream = new FileInputStream(PATH_TO_PROPERTIES);
            prop.load(fileInputStream);
            numberPassengers = Integer.parseInt(prop.getProperty("number_passengers"));
            numberFloor = Integer.parseInt(prop.getProperty("number_Floor"));
            capacity = Integer.parseInt(prop.getProperty("capacity"));
        } catch (IOException e) {
            LOGGER.info("Error in the program: file " + PATH_TO_PROPERTIES + " not found");
            e.printStackTrace();
        }

    Random random = new Random(1);
    int finishFloor=0;
    int startFloor;
    for(int i=0; i<numberFloor; i++){
        floorList.add(new Floor(i+1));
    }
        Elevator elevator = new Elevator(capacity,floorList);
    for(int i=0; i< numberPassengers; i++)
    {
        boolean f = false;
        startFloor = random.nextInt(floorList.size());
        while (f == false) {
            finishFloor = random.nextInt(floorList.size());
            if (finishFloor != startFloor) f = true;
        }
        mansList.add(new Passenger(i + 1, floorList.get(startFloor), floorList.get(finishFloor)));
    }
        /*for (Passenger p: mansList) {
            System.out.println(p.toString());
        }*/
        Controller controller = new Controller();
        controller.startThread(mansList, elevator);
    }

}
