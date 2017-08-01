package elevator;

import org.apache.log4j.Logger;

import java.util.Iterator;
import java.util.List;

public class Elevator extends Thread {
    public static final Logger LOGGER = Logger.getLogger(Elevator.class);
    private int capacity;//Lift capacity
    private int count;//number of passengers, who are in an elevator
    private Floor currentFloor;//Current floor
    private List<Floor> route;

    public Elevator(int capacity, List<Floor> route) {
        this.capacity=capacity;
        this.route=route;
        setDaemon(true);
    }
    public Floor getFloor() {
        return currentFloor;
    }

    // check whether the passenger was able to get into the elevator
    public synchronized boolean inter(){
        if(count<capacity){
            count++;
            LOGGER.info("пассажир сел " + "на этаже "+ this.currentFloor);
            //System.out.println("пассажир сел " + "на этаже "+ this.currentFloor);

            return true;
        }
        LOGGER.info("Elevator is full");
       // System.out.println("Elevator is full");
        return false;
    }

    public void leave(){
        this.count--;
    }

    @Override
    public void run() {
        LOGGER.info("лифт " +" начал работу");
        // System.out.println("лифт " +" начал работу");
        Iterator<Floor> i = this.route.iterator();
        while (true) {
            if (!i.hasNext()) {
                i = this.route.iterator();
            }
            Floor floor = i.next();
            floor.setElevator(this);
            this.currentFloor = floor;

            try {
                this.arrived();
                this.currentFloor.elevatorArrived();
            } catch (Exception e) {
                e.printStackTrace();
            }
            //лифт должен остановиться
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
    //when a passenger needs to leave
    public synchronized void waitForFloor(Floor fl){
        while (true){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(fl==this.getFloor()){
              //  System.out.println("параметры"+fl+this.getFloor());
                this.leave();//число--
                LOGGER.info("пассажир вышел "+ "на этаже "+fl.id);
                 // System.out.println("пассажир вышел "+ "на этаже "+fl.id);
                return;
            }
        }
    }

    //Passengers leave the waiting state
    synchronized public void arrived(){

        this.notifyAll();
        LOGGER.info("лифт приехал на этаж"+ currentFloor+ " пассажиры заходят");
       // System.out.println("лифт приехал на этаж"+ currentFloor+ " пассажиры заходят");
    }
}
