package springinthepub.process;



import springinthepub.Pub;
import springinthepub.RandomGenerator;

import java.io.IOException;

public class AddToQueueProcess implements Runnable{
    Pub pub;

    public Pub getPub() {
        return pub;
    }

    public void setPub(Pub pub) {
        this.pub = pub;
    }

    public AddToQueueProcess(Pub pub){
        this.pub = pub;
    }


    @Override
    public void run() {
        try {
            System.out.println("Runnable running");
            this.getPub().getVisitorsQueue().add(RandomGenerator.personRandomGenerator());
            this.getPub().increaseQueueSize();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
