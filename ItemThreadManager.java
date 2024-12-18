package services;
import ui.OutputHandel;
import data.*;
import java.util.LinkedList;
import java.util.Queue;



class ItemThreadManager implements Runnable {
    private Library library;
    private Queue<Request> queue;
    private OutputHandel outputHandel;

    public ItemThreadManager(Library library, LinkedList<Request> queue) {
        this.library = library;
        this.queue = queue;
        this.outputHandel = new OutputHandel();
    }


    @Override
    public void run() {
        if (queue.isEmpty()) {
            this.outputHandel.printGivenMessage("The List of Requests  is Empty ");
            return;
        }
        else{
            int response = -1;
            Request request = queue.poll();
            if (request.type() == Request.Type.Borrow) {
                response = library.borrowItem(request.title());
                if (response == 1){this.outputHandel.printGivenMessage("Borrowed item  " +request.title() + "Successfully ");}

            }
            else if (request.type() == Request.Type.Return) {
                response = library.returnItem(request.title());
                if(response == 1){this.outputHandel.printGivenMessage("Returned item " +request.title() + "Successfully ");}
            }
        }
    }
    }
