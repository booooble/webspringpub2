package springinthepub.process;

import springinthepub.Beerman;
import springinthepub.Pub;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.DoubleAdder;

public class VisitorsFilterProcess implements Runnable{
    Pub pub;

    public VisitorsFilterProcess(Pub pub){
        this.pub = pub;
    }

    public Pub getPub() {
        return pub;
    }

    public void setPub(Pub pub) {
        this.pub = pub;
    }

    @Override
    public void run(){
        Queue<Beerman> visitorsQueue = this.getPub().getVisitorsQueue();
        List<Beerman> visitors = this.getPub().getVisitors();
        DoubleAdder beerLiterLimit = this.getPub().getBeerLiterLimit();
        AtomicInteger currCapacity = this.getPub().getCurrCapacity();
        int maxCapacity = this.getPub().getMaxCapacity();
        StringBuffer historyText = this.getPub().getHistoryText();
        double drunkBeer = this.getPub().getDrunkBeer();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while (!visitorsQueue.isEmpty()) {
            Beerman visitor = visitorsQueue.poll();
            boolean isEnoughBeer = beerLiterLimit.doubleValue() - visitor.getLitersToDrink() >= 0;
            if (!isEnoughBeer) {
                this.getPub().makeAnOrder();
            }
            if (visitor.age >= 18 && currCapacity.get() <= maxCapacity &&
                    beerLiterLimit.doubleValue() - visitor.getLitersToDrink() >= 0) {
                visitors.add(visitor);
                historyText.append(visitor.getName() + " came in. And drunk " + visitor.litersToDrink + " liter(s).\n");
                this.getPub().increaseCurrCapacity();
                this.getPub().decreaseBeerLimit(visitor.getLitersToDrink());
                drunkBeer += visitor.getLitersToDrink();
                Beerman.beerManCount++;
                this.getPub().updateRelativeCapacity();
            } else {
                historyText.append(visitor.getName() + " was rejected ");
                if (visitor.getAge() < 18) {
                    historyText.append("(" + visitor.getAge() + " years old).\n");
                } else {
                    historyText.append("(No beer - no fun, man...)\n");
                }
            }
        }
    }
}
