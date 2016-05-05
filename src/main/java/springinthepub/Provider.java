package springinthepub;


import static java.lang.Thread.sleep;

public class Provider {
    public static double beerLimit = 10000.0;

    public static boolean sendOrder(double orderSize) {
        boolean isOrderApproved = false;
        if (beerLimit - orderSize > 0) {
            beerLimit -= orderSize;
            isOrderApproved = true;
        }
        return isOrderApproved;
    }

    public static boolean deliverOrder() {
        try {
            sleep((long) Math.random() * 10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return true;
    }
}
