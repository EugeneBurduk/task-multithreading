package elevator;


import org.apache.log4j.Logger;

public class Passenger extends  Thread{
    public static final Logger LOGGER = Logger.getLogger(Passenger.class);
    public Floor startFloor;
    public Floor finishFloor;
    int id;

    public String toString(){
        return  id+ " "+ startFloor+finishFloor;
    }

    public Passenger(int id, Floor startFloor, Floor finishFloor){
        this.startFloor=startFloor;
        this.finishFloor=finishFloor;
        this.id=id;
    }

    @Override
    public void run() {
        LOGGER.info("пассажир "+ this.id+" пришел на ЭТАЖ "+ this.startFloor);
        //System.out.println("пассажир "+ this.id+" пришел на ЭТАЖ "+ this.startFloor);
        this.startFloor.waitElevator().waitForFloor(this.finishFloor);
       LOGGER.info("пассажир "+ this.id+" приехал на свой этаж");
      // System.out.println("пассажир "+ this.id+" приехал на свой этаж");
    }
}
