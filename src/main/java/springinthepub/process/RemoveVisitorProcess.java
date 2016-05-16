package springinthepub.process;

import springinthepub.Beerman;
import springinthepub.Pub;

import java.util.List;

public class RemoveVisitorProcess implements Runnable{
    Pub pub;

    public RemoveVisitorProcess(Pub pub){
        this.pub = pub;
    }

    @Override
    public void run(){
        List<Beerman> visitors = this.getPub().getVisitors();
        if(visitors.size() > 0){
            String removedVisitorName = visitors.get(0).getName();
            visitors.remove(0);
            this.getPub().decreaseCurrCapacity();
            this.getPub().updateRelativeCapacity();
            this.getPub().getHistoryText().append(removedVisitorName + " has been removed.\n");
        }
        else{
            this.getPub().getHistoryText().append("There is noone to be removed... Add someone!\n");
        }
    }

    public Pub getPub() {
        return pub;
    }

    public void setPub(Pub pub) {
        this.pub = pub;
    }
}
