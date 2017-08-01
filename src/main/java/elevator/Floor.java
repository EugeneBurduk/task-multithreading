package elevator;


import org.apache.log4j.Logger;

public class Floor {
    Elevator elevator;
    int id;
    public Floor(int id){
        this.id=id;
    }
    public static final Logger LOGGER = Logger.getLogger(Floor.class);

    public void setElevator(Elevator elevator) {
        this.elevator = elevator;
    }

    // The passenger is standing on the floor and waiting for the elevator

    public Elevator waitElevator() {
        Elevator elevator1=null;
        while (elevator1==null){
            try {
                this.syncWait();
                elevator1=elevator;
                if(!elevator1.inter()){
                    elevator1=null;
                     LOGGER.info("пассажир сел в лифт на этаже"+id);
                    //System.out.println("пассажир сел в лифт на этаже"+id);
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return elevator1;
    }

    public synchronized void elevatorArrived(){
        notifyAll();
    }

    //Waiting
    private synchronized void syncWait() throws InterruptedException {
        this.wait();
    }
    public String toString(){
        return " "+ id;
    }
}
