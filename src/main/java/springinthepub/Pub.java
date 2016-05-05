package springinthepub;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.*;

public class Pub {
    private static final int maxCapacity = 50;
    private String pubName;
    private int currCapacity = 0;
    private double beerLiterLimit;
    private final double maxBeerLimit;
    private Queue<Beerman> visitorsQueue = new PriorityQueue();
    private List<Beerman> visitors = new ArrayList<>();

    public static int getMaxCapacity() {
        return maxCapacity;
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

    public List<Beerman> getVisitors() {
        return visitors;
    }

    public void addVisitorToTheQueue(Beerman visitor) {
        visitorsQueue.add(visitor);
    }

    public void userFilter() {
        if (visitorsQueue.isEmpty()) {
            System.out.println("The queue is empty. Waiting for visitors");
            //interrupt the Thread to wait;
        } else {
            Iterator it = visitorsQueue.iterator();
            List<Beerman> temp = new ArrayList<>();
            while (it.hasNext()) {
                Beerman visitor = (Beerman) it.next();
                boolean isEnoughBeer = this.beerLiterLimit - visitor.getLitersToDrink() >= 0;
                if (!isEnoughBeer) {
                    makeAnOrder();
                }
                if (visitor.age >= 18 && this.currCapacity <= maxCapacity &&
                        this.beerLiterLimit - visitor.getLitersToDrink() >= 0) {
                    System.out.println(visitor.getName() + " came in");
                    temp.add(visitor);
                    this.currCapacity++;
                    this.beerLiterLimit -= visitor.getLitersToDrink();
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
        return "Pub{" +
                "pubName='" + pubName + '\'' +
                ", capacity=" + currCapacity + "/" + maxCapacity +
                ", beerLiterLimit=" + beerLiterLimit +
                ", \n\tvisitors=" + visitors +
                '}';
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
}
