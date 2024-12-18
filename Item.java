package services;
import java.util.Date;


sealed public interface Item permits Book, Magazine, Reference {
    enum Status {
        Exist, Borrowed, Banned
    }

    enum Type {
        Book, Magazine, Reference
    }

    enum RequestType {
        Return, Borrow, Nothing
    }

    String getTitle();
    void setTitle(String title);

    String getAuthor();
    void setAuthor(String author);

    Date getBorrowed();
    void setBorrowed(Date borrowed);

    Date getReturned();
    void setReturned(Date returned);

    Status getStatus();
    void setStatus(Status status);

    Type getType();
    void setType(Type type);

    RequestType getRequestType();
    void setRequestType(RequestType requestType);

    void addObserver(User observer);
    void removeObserver(User observer);
    void sendMessage(String message);

    void display();
}
