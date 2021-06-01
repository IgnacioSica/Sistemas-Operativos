import java.util.*;

public class RequestPlanner {
    RequestPlanner(){
        list = new ArrayList<LinkedList<Request>>(7);
        for(int i = 0; i < 7; i++){
            list.add(i, new LinkedList<Request>());
        }
    }
    ArrayList<LinkedList<Request>> list;

    void addRequest(Request request){
        int level = request.priorityLevel;
        LinkedList<Request> levelList = list.get(level);
        boolean added = false;
            ListIterator<Request> it = levelList.listIterator();
            while(it.hasNext()) {
              if(it.next().priorityScore <= request.priorityScore) {
                  it.previous();
                  it.add(request);
                  added = true;
                  break;
              }
            } 
            if (!added) { 
                levelList.add(request);
            }
    }

    Request extractRequestByPriority(){
        for(int i = 0; i < list.size(); i++){
            if(!list.get(i).isEmpty()){
                return list.get(i).removeFirst();
            }
        }
        return null;
    }

    boolean isEmpty(){
        for(int i = 0; i < list.size(); i++){
            if(!list.get(i).isEmpty()){
                return false;
            }
        }
        return true;
    }

    void printByLevel(){
        for(int i = 0; i < list.size(); i++){
            System.out.println("LEVEL " + i);
            ListIterator<Request> it = list.get(i).listIterator();
            while(it.hasNext()) {
                Request thisRequest = it.next();
              System.out.println(thisRequest.cedula + "con puntje de " + thisRequest.priorityScore);
            } 
        }
    }
}
