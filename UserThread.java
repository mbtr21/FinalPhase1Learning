package services;
import ui.OutputHandel;
import data.Request;
import java.util.LinkedList;
import java.util.Queue;


class UserThread implements Runnable {
    private Queue<Request> queue;
    private LinkedList<Request> requests;
    private OutputHandel output;


    public UserThread(LinkedList<Request> requests, LinkedList<Request> queue) {
        this.requests = requests;
        this.queue = queue;
        this.output = new OutputHandel();
    }

    @Override
    public void run() {
        Request request = requests.poll();
        if (request != null) {
            queue.add(request);
            output.printGivenMessage(request.title() + request.type() + " Added  Successfully");
        }

    }
}
