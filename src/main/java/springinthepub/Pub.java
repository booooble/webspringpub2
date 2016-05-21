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
    public static final String EMPTY_BAR_NOTIFICATION = "There is noone to be removed... Add someone!\n";
    public static final String EMPTY_QUEUE_NOTIFICATION = "The queue is empty. Waiting for visitors...\n";
    public static final String OUT_OF_BEER_NOTIFICATION = "The beerlimit has been ended. Composing the order...\n";
    public static final String PARTY_IS_OVER_NOTIFICATION = "The order has been rejected. The party is over...\n";

    @Autowired
    public Pub(String pubName, double maxBeerLimit) {
        this.pubName = pubName;
        this.maxBeerLimit = maxBeerLimit;
        this.beerLiterLimit = maxBeerLimit;
    }
    
    public void addRandomVisitorToBar() throws IOException, InterruptedException {
    	addVisitorToBar(RandomGenerator.personRandomGenerator());
    }
    
    public void addVisitorToBar(Beerman visitor) throws IOException, InterruptedException {    	
    	if (isBarFull()){
            visitorsQueue.addLast(visitor);
            increaseQueueSize();
    		historyText.append(generateBarIsFullNotification(visitor));
    	}
    	else{    		
    		if(isVisitorEligible(visitor)){
    			if(!isEnoughBeer(visitor)){
        			makeAnOrder();
        		}
    			visitors.add(visitor);
    			updateAfterAddingToBar(visitor);
    		}
    		else{
    			notifyRejectionReason(visitor);
    		}
    	}  	
    }
    
    public void updateAfterAddingToBar(Beerman visitor){
    	historyText.append(generateCameInNotification(visitor));
        currCapacity++;
        beerLiterLimit -= visitor.getLitersToDrink();
        drunkBeer += visitor.getLitersToDrink();
        updateRelativeCapacity();
        Beerman.beerManCount++;
    }
    
    public void addRandomVisitorToTheQueue() throws IOException {
    	Beerman visitor = RandomGenerator.personRandomGenerator();
        visitorsQueue.addLast(visitor);
        increaseQueueSize();
        historyText.append(visitor.getName() + " has came and is waiting at the end of the queue.\n");
    }
    
    public void processTheQueue() {
        if (visitorsQueue.isEmpty()) {
        	historyText.append(EMPTY_QUEUE_NOTIFICATION);      	
        }
        else {
            while (!visitorsQueue.isEmpty()) {
                Beerman visitor = visitorsQueue.pollFirst();
                if (!isEnoughBeer(visitor)) {
                    makeAnOrder();
                }
                if (isVisitorEligible(visitor) && !isBarFull()) {
                	visitors.add(visitor);
                	updateAfterAddingToBar(visitor);
                }                 
                else {
                	if(isBarFull()){
                		visitorsQueue.addFirst(visitor);
                		historyText.append(generateBarIsFullNotification(visitor));         		
                		break;
                	}
                	else {
                		notifyRejectionReason(visitor);
                	}
                }
            }
        }
    }
    
    public void removeVisitor(){
    	if(visitors.size() > 0){
    		String removedVisitorName = visitors.get(0).getName();
    		visitors.remove(0);
    		updateAfterRemoving(removedVisitorName);
    	}
    	else{
    		historyText.append(EMPTY_BAR_NOTIFICATION);
    	}
    }
    
    public void updateAfterRemoving(String visitorName){
		currCapacity--;
		updateRelativeCapacity();
		historyText.append(visitorName + " has been removed.\n");
    }
    
    public boolean makeAnOrder() {
    	historyText.append(OUT_OF_BEER_NOTIFICATION);
    	double order = this.maxBeerLimit - this.beerLiterLimit;
        historyText.append("The order for " + order + " liters was sent to the provider...\n");
        boolean isOrderReceived = Provider.sendOrder(order);
        if (isOrderReceived) {
        	historyText.append("We ordered " + order + " liters successfully. The party is going on!\n");
        	increaseBeerLimit(order);
            return true;
        } else {
        	historyText.append(PARTY_IS_OVER_NOTIFICATION);
            return false;
        }
    }
    
    public Boolean isEnoughBeer(Beerman visitor){
    	return beerLiterLimit - visitor.getLitersToDrink() >= 0;
    }
    
    public Boolean isBarFull(){
    	return currCapacity == maxCapacity;
    }
    
    public Boolean isVisitorEligible(Beerman visitor){
    	return visitor.age >= 18;
    }
    
    public void notifyRejectionReason(Beerman visitor){
    	historyText.append(visitor.getName() + " was rejected ");           
		if (!isVisitorEligible(visitor)) {
			historyText.append("(" + visitor.getAge() + " years old).\n");       	
		}
		else{
			historyText.append("(For unknown reason).\n");
		}
    }
    
    public String generateCameInNotification(Beerman visitor){
    	return visitor.getName() + " came in. And drunk " + visitor.litersToDrink + " liter(s).\n";
    }
    
    public String generateBarIsFullNotification(Beerman visitor){
    	return "No way! The Bar is full!!! " + visitor.getName() + 
    			" is not  able to come in! Ask someone to leave! This visitor is waiting in the queue!\n";
    }
  
    public double getRelativeCapacity() {
		return relativeCapacity;
	}

	public void setRelativeCapacity(double relativeCapacity) {
		this.relativeCapacity = relativeCapacity;
	}

	public void updateRelativeCapacity() {
		this.relativeCapacity = (double) currCapacity / maxCapacity;
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
   
    public StringBuffer getHistoryText() {
		return historyText;
	}

	public void setHistoryText(StringBuffer historyText) {
		this.historyText = historyText;
	}

    public List<Beerman> getVisitors() {
        return visitors;
    }

    public int getVisitorsQueueSize() {
        return visitorsQueue.size();
    }

    public void increaseBeerLimit(double order){
    	this.beerLiterLimit += order;
    }
    
    public void decreaseBeerLimit(double minus){
    	this.beerLiterLimit -= minus;
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
	
    @Override
	public String toString() {
		return "Pub [maxCapacity=" + maxCapacity + ", pubName=" + pubName + ", currCapacity=" + currCapacity
				+ ", beerLiterLimit=" + beerLiterLimit + ", maxBeerLimit=" + maxBeerLimit + ", visitorsQueue="
				+ visitorsQueue + ", visitors=" + visitors + ", visitorsQueueSize=" + visitorsQueueSize + ", drunkBeer="
				+ drunkBeer + "]";
	}

}
