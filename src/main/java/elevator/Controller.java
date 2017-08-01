package elevator;


import java.util.List;

public class Controller {

    public void startThread(List<Passenger> mansList, Elevator elevator) throws InterruptedException {
        for (Passenger m : mansList) {
            m.start();
        }
        elevator.start();
        for (Passenger m : mansList) {
            m.join();
        }

    }
}
