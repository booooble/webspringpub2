package springinthepub;

import org.springframework.beans.factory.annotation.Autowired;

/*import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;*/

import java.io.IOException;
import java.util.*;

public class Pub {
    protected int maxCapacity = 15;
    private String pubName;
    public int currCapacity = 0;
    protected double beerLiterLimit;
    private double maxBeerLimit;
    public Deque<Beerman> visitorsQueue = new LinkedList<Beerman>();
    public List<Beerman> visitors = new ArrayList<>();
    public int visitorsQueueSize = 0;
    protected double drunkBeer = 0;
    private double relativeCapacity = 0;
    public StringBuffer historyText = new StringBuffer();

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

    public void setVisitorsQueue(Deque<Beerman> queue) {
        this.visitorsQueue = queue;
    }

    public void setVisitors(List<Beerman> visitors) {
        this.visitors = visitors;
    }

    public void increaseQueueSize(){
        visitorsQueueSize++;
    }

    public void decreaseCurrCapacity(){
        currCapacity--;
    }

    public void increaseCurrCapacity(){
        currCapacity++;
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

    public int getVisitorsQueueSize() {
        return visitorsQueue.size();
    }

    public void addRandomVisitorToTheQueue() throws IOException {
    	Beerman vis = RandomGenerator.personRandomGenerator();
        this.visitorsQueue.addLast(vis);
        increaseQueueSize();
        this.historyText.append(vis.getName() + " has came and is waiting at the end of the queue.\n");
    }
    
    public void addRandomVisitor() throws IOException {
    	
    	if (currCapacity == maxCapacity){
    		this.historyText.append("The Bar is full!!! Ask someoone to leave! The visitor is still in the queue!\n");
    	}
    	else{    		
    		Beerman vis = RandomGenerator.personRandomGenerator();
    		boolean isEnoughBeer = this.beerLiterLimit - vis.getLitersToDrink() >= 0;    		
    		if(vis.age > 18){
    			if(!isEnoughBeer){
        			makeAnOrder();
        		}
    			this.visitors.add(vis);
    			this.historyText.append(vis.getName() + " came in. And drunk " + vis.litersToDrink + " liter(s).\n");
                this.currCapacity++;
                this.beerLiterLimit -= vis.getLitersToDrink();
                this.drunkBeer += vis.getLitersToDrink();
                this.updateRelativeCapacity();
                Beerman.beerManCount++;
    		}
    		else{this.historyText.append(vis.getName() + " was rejected ");           
    			if (vis.getAge() < 18) {
    				this.historyText.append("(" + vis.getAge() + " years old).\n");
        	
    			}
    		}
    	}

    	
    }
    
    public void removeVisitor(){
    	if(this.visitors.size() > 0){
    		String removedVisitorName = visitors.get(0).getName();
    		this.visitors.remove(0);
    		this.currCapacity--;
    		updateRelativeCapacity();
    		this.historyText.append(removedVisitorName + " has been removed.\n");
    	}
    	else{
    		this.historyText.append("There is noone to be removed... Add someone!\n");
    	}
    }

    public void userFilter() {
        if (visitorsQueue.isEmpty()) {
        	this.historyText.append("The queue is empty. Waiting for visitors...\n");      	
        }
        else if (currCapacity == maxCapacity){
    		this.historyText.append("The Bar is full!!! Ask someoone to leave! The visitor is still in the queue!\n");
    	}

        else {
            while (!visitorsQueue.isEmpty()) {
                boolean isEnoughBeer = this.beerLiterLimit - visitorsQueue.getFirst().getLitersToDrink() >= 0;
                if (!isEnoughBeer) {
                    makeAnOrder();
                }
                Beerman visitor = visitorsQueue.pollFirst();
                if (visitor.age >= 18 && this.currCapacity < maxCapacity) {
                	visitors.add(visitor);
                	this.historyText.append(visitor.getName() + " came in. And drunk " + visitor.litersToDrink + " liter(s).\n");
                    this.currCapacity++;
                    this.beerLiterLimit -= visitor.getLitersToDrink();
                    this.drunkBeer += visitor.getLitersToDrink();
                    this.updateRelativeCapacity();
                    Beerman.beerManCount++;
                }                 
                else {
                	if(currCapacity == maxCapacity){
                		visitorsQueue.addFirst(visitor);
                		this.historyText.append(visitor.getName() + " is not able to come in because the bar is full). "
                				+ "This visitor is still waiting in the queue...\n");         		
                		break;
                	}
                	else {this.historyText.append(visitor.getName() + " was rejected ");           
                		if (visitor.getAge() < 18) {
                			this.historyText.append("(" + visitor.getAge() + " years old).\n");
                    	}

                		else {
                			this.historyText.append("(The reason is unknown).\n");
                		}
                	}
                }
            }
        }
    }

    public void increaseBeerLimit(double order){
    	this.beerLiterLimit += order;
    }
    
    public void decreaseBeerLimit(double minus){
    	this.beerLiterLimit -= minus;
    }
    
    public boolean makeAnOrder() {
    	this.historyText.append("The beerlimit has been ended. Composing the order...\n");
        double order = this.maxBeerLimit - this.beerLiterLimit;
        this.historyText.append("The order for " + order + " liters was sent to the provider...\n");
        boolean isOrderReceived = Provider.sendOrder(order);
        if (isOrderReceived) {
        	this.historyText.append("We ordered " + order + " liters successfully. The party is going on!\n");
        	increaseBeerLimit(order);
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

/*    public static void main(String[] args) throws InterruptedException {

//        Pub blueLagoon1 = new Pub("name", 500.0);
//        blueLagoon1.setVisitors(new Beerman("Jim", 40, 20.0));
//        blueLagoon1.setVisitors(new Beerman("Pol", 15, 20.0));
//        blueLagoon1.setVisitors(new Beerman("Dave", 45, 50.5));

//        System.out.println(blueLagoon1);
        ApplicationContext ac = new ClassPathXmlApplicationContext("config.xml");
        Pub blueLagoon = (Pub) ac.getBean("pub");
        System.out.println(blueLagoon);
    }*/

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
