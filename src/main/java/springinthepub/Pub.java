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
    private double relativeCapacity = 0;
    private StringBuffer historyText = new StringBuffer();
   
    public double getRelativeCapacity() {
		return relativeCapacity;
	}

	public void setRelativeCapacity(double relativeCapacity) {
		this.relativeCapacity = relativeCapacity;
	}

	public void updateRelativeCapacity() {
		this.relativeCapacity = (double) this.currCapacity / this.maxCapacity;
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
    }
    public StringBuffer getHistoryText() {
		return historyText;
	}

	public void setHistoryText(StringBuffer historyText) {
		this.historyText = historyText;
	}

	public Pub() {   
    }

    public List<Beerman> getVisitors() {
        return visitors;
    }

    public void addRandomVisitorToTheQueue() throws IOException {
        visitorsQueue.add(RandomGenerator.personRandomGenerator());
        visitorsQueueSize++;
        userFilter();
        updateRelativeCapacity();
     }

    public int getVisitorsQueueSize() {
        return visitorsQueue.size();
    }
    
    public void saveHistoryToFile(){
    	
    }
    
    public void removeVisitor(){
    	if(this.visitors.size() > 0){
    		String removedVisitorName = visitors.get(0).getName();
    		this.visitors.remove(0);
    		this.currCapacity--;
    		this.historyText.append(removedVisitorName + " has been removed.\n");
    	}
    	else{
    		this.historyText.append("There is noone to be removed... Add someone!\n");
    	}
    }

    public void userFilter() {
        if (visitorsQueue.isEmpty()) {
        	this.historyText.append("The queue is empty. Waiting for visitors...\n");
            //interrupt the Thread to wait;
        } else {
           // Iterator<Beerman> it = visitorsQueue.iterator();
           // List<Beerman> temp = new ArrayList<>();
            while (!visitorsQueue.isEmpty()) {
                //Beerman visitor = (Beerman) it.next();
                Beerman visitor = visitorsQueue.poll();
                boolean isEnoughBeer = this.beerLiterLimit - visitor.getLitersToDrink() >= 0;
                if (!isEnoughBeer) {
                    makeAnOrder();
                }
                if (visitor.age >= 18 && this.currCapacity <= maxCapacity &&
                        this.beerLiterLimit - visitor.getLitersToDrink() >= 0) {
                	visitors.add(visitor);
                	this.historyText.append(visitor.getName() + " came in.\n");
                   // temp.add(visitor);
                    this.currCapacity++;
                    this.beerLiterLimit -= visitor.getLitersToDrink();
                    this.drunkBeer += visitor.getLitersToDrink();
                    Beerman.beerManCount++;
                } else {
                	this.historyText.append(visitor.getName() + " was rejected ");
                    if (visitor.getAge() < 18) {
                    	this.historyText.append("(" + visitor.getAge() + " years old)\n");
                    } else {
                        this.historyText.append("No beer - no fun, man...\n");
                    }
                }
            }
            //visitors.addAll(temp);
        }
    }

    public boolean makeAnOrder() {
    	this.historyText.append("The beerlimit has been ended. Composing the order...\n");
        double order = this.maxBeerLimit - this.beerLiterLimit;
        this.historyText.append("The order for " + order + " liters was sent to the provider...\n");
        boolean isOrderReceived = Provider.sendOrder(order);
        if (isOrderReceived) {
        	this.historyText.append("We ordered " + order + " liters successfully. The party is going on!\n");
            this.beerLiterLimit += order;
            return true;
        } else {
        	this.historyText.append("The order has been rejected. The party is over...\n");
            return false;
        }
    }



    @Override
	public String toString() {
		return "Pub [maxCapacity=" + maxCapacity + ", pubName=" + pubName + ", currCapacity=" + currCapacity
				+ ", beerLiterLimit=" + beerLiterLimit + ", maxBeerLimit=" + maxBeerLimit + ", visitorsQueue="
				+ visitorsQueue + ", visitors=" + visitors + ", visitorsQueueSize=" + visitorsQueueSize + ", drunkBeer="
				+ drunkBeer + "]";
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

}
