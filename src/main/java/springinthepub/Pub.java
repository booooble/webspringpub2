package springinthepub;

import org.springframework.beans.factory.annotation.Autowired;
import springinthepub.process.AddToQueueProcess;
import springinthepub.process.RemoveVisitorProcess;
import springinthepub.process.VisitorsFilterProcess;
/*import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;*/

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.DoubleAdder;

public class Pub {
    protected int maxCapacity = 15;
    private String pubName;
    public AtomicInteger currCapacity = new AtomicInteger(0);
    protected DoubleAdder beerLiterLimit = new DoubleAdder();
    private volatile double maxBeerLimit;
    public Queue<Beerman> visitorsQueue = new LinkedBlockingQueue<Beerman>();
    public List<Beerman> visitors = new CopyOnWriteArrayList<>();
    public int visitorsQueueSize = 0;
    protected volatile double drunkBeer = 0;
    private volatile double relativeCapacity = 0;
    public StringBuffer historyText = new StringBuffer();

    public double getRelativeCapacity() {
		return relativeCapacity;
	}

	public void setRelativeCapacity(double relativeCapacity) {
		this.relativeCapacity = relativeCapacity;
	}

	public void updateRelativeCapacity() {
		this.relativeCapacity = (double) this.currCapacity.get() / this.maxCapacity;
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

    public void increaseQueueSize(){
        visitorsQueueSize++;
    }

    public void decreaseCurrCapacity(){
        currCapacity.set(this.currCapacity.get() - 1);
    }

    public void increaseCurrCapacity(){
        currCapacity.set(this.currCapacity.get() + 1);
    }

    @Autowired
    public Pub(String pubName, double maxBeerLimit) {
        this.pubName = pubName;
        this.maxBeerLimit = maxBeerLimit;
        this.beerLiterLimit.add(maxBeerLimit);
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

//    public void addRandomVisitorToTheQueue() throws IOException {
//        visitorsQueue.add(RandomGenerator.personRandomGenerator());
//        visitorsQueueSize++;
//        userFilter();
//        updateRelativeCapacity();
//    }
    public void addRandomVisitorToTheQueue(){
        System.out.println("addThread started");
        Thread toQueueThread = new Thread(new AddToQueueProcess(this));
        toQueueThread.setPriority(Thread.MAX_PRIORITY);
        toQueueThread.start();
        Thread filterThread = new Thread(new VisitorsFilterProcess(this));
        filterThread.setPriority(Thread.MIN_PRIORITY);
        filterThread.start();
    }

    public void removeVisitor(){
        Thread removeThread = new Thread(new RemoveVisitorProcess(this));
        removeThread.setPriority(Thread.NORM_PRIORITY);
        removeThread.start();

    }

//    public void userFilter(){
//        Thread t = new Thread(new VisitorsFilterProcess(this));
//        t.setPriority(Thread.MIN_PRIORITY);
//        t.start();
//    }

//    public void addVisitorToTheQueueProcess(){
//        AddToQueueThread t = new AddToQueueThread();
//        t.setPriority(Thread.MAX_PRIORITY);
//        t.start();
//    }
//
//    public void removeVisitorProcess(){
//        RemoveVisitorThread t = new RemoveVisitorThread();
//        t.setPriority(Thread.MAX_PRIORITY);
//        t.start();
//    }
//
//    public void visitorFilterProcess(){
//        VisitorFilterThread t = new VisitorFilterThread();
//        t.setPriority(Thread.MIN_PRIORITY);
//        t.start();
//    }

//    class RemoveVisitorThread extends Thread
//    {
//        public void run(){
//            if(visitors.size() > 0){
//                String removedVisitorName = visitors.get(0).getName();
//                visitors.remove(0);
//                currCapacity--;
//                updateRelativeCapacity();
//                historyText.append(removedVisitorName + " has been removed.\n");
//            }
//            else{
//                historyText.append("There is noone to be removed... Add someone!\n");
//            }
//        }
//    }
//
//    class AddToQueueThread extends Thread
//    {
//        public void run(){
//            try {
//                visitorsQueue.add(RandomGenerator.personRandomGenerator());
//                visitorsQueueSize++;
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            if(visitorsQueue.size() > 0){
//            	visitorFilterProcess();
//            }
//        }
//    }
//
//    class VisitorFilterThread extends Thread
//    {
//        public void run(){
//                while (!visitorsQueue.isEmpty()) {
//                    Beerman visitor = visitorsQueue.poll();
//                    boolean isEnoughBeer = beerLiterLimit - visitor.getLitersToDrink() >= 0;
//                    if (!isEnoughBeer) {
//                        makeAnOrder();
//                    }
//                    if (visitor.age >= 18 && currCapacity <= maxCapacity &&
//                            beerLiterLimit - visitor.getLitersToDrink() >= 0) {
//                        visitors.add(visitor);
//                        historyText.append(visitor.getName() + " came in. And drunk " + visitor.litersToDrink + " liter(s).\n");
//                        currCapacity++;
//                        beerLiterLimit -= visitor.getLitersToDrink();
//                        drunkBeer += visitor.getLitersToDrink();
//                        Beerman.beerManCount++;
//                        updateRelativeCapacity();
//                    } else {
//                        historyText.append(visitor.getName() + " was rejected ");
//                        if (visitor.getAge() < 18) {
//                            historyText.append("(" + visitor.getAge() + " years old).\n");
//                        } else {
//                            historyText.append("(No beer - no fun, man...)\n");
//                        }
//                    }
//                }
//        }
//    }

    public int getVisitorsQueueSize() {
        return visitorsQueue.size();
    }
    
    public void saveHistoryToFile(){
    	//TODO: implement via Save As Dialogue window
    	this.historyText.append("Sorry, this feature is not implemented yet... (Hint: CTRL-C/CTRL-V &#9786;&#9996;).\n");
    }
    
//    public void removeVisitor(){
//    	if(this.visitors.size() > 0){
//    		String removedVisitorName = visitors.get(0).getName();
//    		this.visitors.remove(0);
//    		this.currCapacity--;
//    		updateRelativeCapacity();
//    		this.historyText.append(removedVisitorName + " has been removed.\n");
//    	}
//    	else{
//    		this.historyText.append("There is noone to be removed... Add someone!\n");
//    	}
//    }



//
//    public void userFilter() {
//        if (visitorsQueue.isEmpty()) {
//        	this.historyText.append("The queue is empty. Waiting for visitors...\n");
//            //interrupt the Thread to wait;
//        } else {
//           // Iterator<Beerman> it = visitorsQueue.iterator();
//           // List<Beerman> temp = new ArrayList<>();
//            while (!visitorsQueue.isEmpty()) {
//                //Beerman visitor = (Beerman) it.next();
//                Beerman visitor = visitorsQueue.poll();
//                boolean isEnoughBeer = this.beerLiterLimit - visitor.getLitersToDrink() >= 0;
//                if (!isEnoughBeer) {
//                    makeAnOrder();
//                }
//                if (visitor.age >= 18 && this.currCapacity <= maxCapacity &&
//                        this.beerLiterLimit - visitor.getLitersToDrink() >= 0) {
//                	visitors.add(visitor);
//                	this.historyText.append(visitor.getName() + " came in. And drunk " + visitor.litersToDrink + " liter(s).\n");
//                   // temp.add(visitor);
//                    this.currCapacity++;
//                    this.beerLiterLimit -= visitor.getLitersToDrink();
//                    this.drunkBeer += visitor.getLitersToDrink();
//                    Beerman.beerManCount++;
//                } else {
//                	this.historyText.append(visitor.getName() + " was rejected ");
//                    if (visitor.getAge() < 18) {
//                    	this.historyText.append("(" + visitor.getAge() + " years old).\n");
//                    } else {
//                        this.historyText.append("(No beer - no fun, man...)\n");
//                    }
//                }
//            }
//            //visitors.addAll(temp);
//        }
//    }

    public void increaseBeerLimit(double order){
    	this.beerLiterLimit.add(order);
    }
    
    public void decreaseBeerLimit(double minus){
    	this.beerLiterLimit.add(-minus);
    }
    
    public boolean makeAnOrder() {
    	this.historyText.append("The beerlimit has been ended. Composing the order...\n");
        double order = this.maxBeerLimit - this.beerLiterLimit.doubleValue();
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

    public AtomicInteger getCurrCapacity() {
        return currCapacity;
    }

    public void setCurrCapacity(AtomicInteger currCapacity) {
        this.currCapacity = currCapacity;
    }

    public DoubleAdder getBeerLiterLimit() {
        return beerLiterLimit;
    }

    public void setBeerLiterLimit(DoubleAdder beerLiterLimit) {
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
