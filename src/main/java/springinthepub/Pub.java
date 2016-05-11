package springinthepub;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.util.*;

public class Pub {
    private int maxCapacity = 15;
    private String pubName;
    private int currCapacity = 0;
    private double beerLiterLimit = 0;
    private double maxBeerLimit;
    private Queue<Beerman> visitorsQueue = new LinkedList<Beerman>();
    private List<Beerman> visitors = new ArrayList<>();
    private int visitorsQueueSize = 0;
    private double drunkBeer = 0;
    private String stringBeerLiterLimit;
    private double relativeCapacity = 0;

    

    
    public double getRelativeCapacity() {
		return relativeCapacity;
	}

	public void setRelativeCapacity(double relativeCapacity) {
		this.relativeCapacity = relativeCapacity;
	}

	public void updateRelativeCapacity() {
		System.out.println(this.currCapacity);
		System.out.println(this.maxCapacity);
		this.relativeCapacity = (double) this.currCapacity/this.maxCapacity;
	}
	
	public int getMaxCapacity() {
        return maxCapacity;
    }
    
    public void setMaxCapacity(int max) {
    	this.maxCapacity = max;
    }

    public double getMaxBeerLimit() {
        return maxBeerLimit;
    }

    public Queue<Beerman> getVisitorsQueue() {
        return visitorsQueue;
    }

    public void setVisitorsQueue(Queue<Beerman> queue) {
        this.visitorsQueue = queue;
    }

    public void setVisitors(List<Beerman> visitors) {
        this.visitors = visitors;
    }

    @Autowired
    public Pub(String pubName, double maxBeerLimit) {
        this.pubName = pubName;
        this.maxBeerLimit = maxBeerLimit;
        this.beerLiterLimit = maxBeerLimit;
        System.out.println(this.pubName);
    }
    public Pub() {
    
    }

    public List<Beerman> getVisitors() {
        return visitors;
    }

    public void addRandomVisitorToTheQueue() throws IOException {
        visitorsQueue.add(RandomGenerator.personRandomGenerator());
        visitorsQueueSize++;
        this.userFilter();
        this.updateRelativeCapacity();
        System.out.println("RelativeCapacity" + this.relativeCapacity);

        
        System.out.println(visitorsQueue);
        System.out.println(visitorsQueue.size());
    }

    public int getVisitorsQueueSize() {
        return visitorsQueue.size();
    }

    public void userFilter() {
        if (visitorsQueue.isEmpty()) {
            System.out.println("The queue is empty. Waiting for visitors");
            //interrupt the Thread to wait;
        } else {
            Iterator<Beerman> it = visitorsQueue.iterator();
            List<Beerman> temp = new ArrayList<>();
            while (it.hasNext()) {
                Beerman visitor = (Beerman) it.next();
                boolean isEnoughBeer = this.beerLiterLimit - visitor.getLitersToDrink() >= 0;
                if (!isEnoughBeer) {
                    makeAnOrder();
                }
                if (visitor.age >= 18 && this.currCapacity <= maxCapacity &&
                        this.beerLiterLimit - visitor.getLitersToDrink() >= 0) {
                	visitors.add(visitor);
                	System.out.println(visitor.getName() + " came in");
                   // temp.add(visitor);
                    this.currCapacity++;
                    this.beerLiterLimit -= visitor.getLitersToDrink();
                    this.drunkBeer += visitor.getLitersToDrink();
                    Beerman.beerManCount++;
                } else {
                    System.out.print(visitor.getName() + " was rejected ");
                    if (visitor.getAge() < 18) {
                        System.out.println("(" + visitor.getAge() + "years old)");
                    } else {
                        System.out.println("No beer - no fun, man...");
                    }
                }
            }
            visitors.addAll(temp);
        }
    }

    public boolean makeAnOrder() {
        System.out.println("The beerlimit has been ended. Composing the order...");
        double order = this.maxBeerLimit - this.beerLiterLimit;
        System.out.println("The order for " + order + " liters was sent to the provider...");
        boolean isOrderReceived = Provider.sendOrder(order);
        if (isOrderReceived) {
            System.out.println("We ordered " + order + " liters successfully. The party is going on!");
            this.beerLiterLimit += order;
            return true;
        } else {
            System.out.println("The order has been rejected. The party is over...");
            return false;
        }
    }



    @Override
	public String toString() {
		return "Pub [maxCapacity=" + maxCapacity + ", pubName=" + pubName + ", currCapacity=" + currCapacity
				+ ", beerLiterLimit=" + beerLiterLimit + ", maxBeerLimit=" + maxBeerLimit + ", visitorsQueue="
				+ visitorsQueue + ", visitors=" + visitors + ", visitorsQueueSize=" + visitorsQueueSize + ", drunkBeer="
				+ drunkBeer + ", stringBeerLiterLimit=" + stringBeerLiterLimit + "]";
	}

	public String getPubName() {
        return pubName;
    }

    public void setPubName(String pubName) {
        this.pubName = pubName;
    }

    public int getCurrCapacity() {
        return currCapacity;
    }

    public void setCurrCapacity(int currCapacity) {
        this.currCapacity = currCapacity;
    }

    public double getBeerLiterLimit() {
        return beerLiterLimit;
    }

    public void setBeerLiterLimit(double beerLiterLimit) {
        this.beerLiterLimit = beerLiterLimit;
    }

    public static void main(String[] args) throws InterruptedException {

//        Pub blueLagoon1 = new Pub("name", 500.0);
//        blueLagoon1.setVisitors(new Beerman("Jim", 40, 20.0));
//        blueLagoon1.setVisitors(new Beerman("Pol", 15, 20.0));
//        blueLagoon1.setVisitors(new Beerman("Dave", 45, 50.5));

//        System.out.println(blueLagoon1);
        ApplicationContext ac = new ClassPathXmlApplicationContext("config.xml");
        Pub blueLagoon = (Pub) ac.getBean("pub");
        System.out.println(blueLagoon);
    }

	public void setVisitorsQueueSize(int visitorsQueueSize) {
		this.visitorsQueueSize = visitorsQueueSize;
	}

	public double getDrunkBeer() {
		return drunkBeer;
	}

	public void setDrunkBeer(double drunkBeer) {
		this.drunkBeer = drunkBeer;
	}

	public String getStringBeerLiterLimit() {
		return beerLiterLimit + "L";
	}

	public void setStringBeerLiterLimit(Double stringBeerLiterLimit) {
		this.stringBeerLiterLimit = stringBeerLiterLimit.toString() + "L";
	}
}
