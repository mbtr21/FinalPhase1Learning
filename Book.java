
package services;
import utils.ObserverManager;

import java.util.*;


non-sealed public class Book implements Item, ObserverManager {
    private String title;
    private String author;
    private Date borrowed;
    private Date returned;
    private Status status;
    private Type type;
    private RequestType requestType;
    private LinkedList<User> observers;

    private Integer publishedYear;
    private String description;

    private static final Type DEFAULT_TYPE = Type.Book;
    private static final Status DEFAULT_STATUS = Status.Exist;

    public Book(String title, String author, Integer publishedYear, Date borrowed, Date returned, Status status, Type type) {
        this.title = title;
        this.author = author;
        this.publishedYear = publishedYear;
        this.description = title;
        this.borrowed = borrowed;
        this.returned = returned;
        this.status = (status != null) ? status : DEFAULT_STATUS;
        this.type = (type != null) ? type : DEFAULT_TYPE;
        this.observers = new LinkedList<>();
    }

    public Integer getPublishedYear() {
        return publishedYear;
    }

    public void setPublishedYear(Integer publishedYear) {
        this.publishedYear = publishedYear;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getAuthor() {
        return author;
    }

    @Override
    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public Date getBorrowed() {
        return borrowed;
    }

    @Override
    public void setBorrowed(Date borrowed) {
        this.borrowed = borrowed;
    }

    @Override
    public Date getReturned() {
        return returned;
    }

    @Override
    public void setReturned(Date returned) {
        this.returned = returned;
    }

    @Override
    public Status getStatus() {
        return status;
    }

    @Override
    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public RequestType getRequestType() {
        return requestType;
    }

    @Override
    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }

    @Override
    public void addObserver(User observer) {
        this.observers.add(observer);
    }

    @Override
    public void removeObserver(User observer) {
        this.observers.remove(observer);
    }

    @Override
    public void sendMessage(String message) {
        for (User user : observers) {
            user.update(message);
        }
    }

    @Override
    public void display() {
        System.out.println("Title: " + getTitle() +
                ", Author: " + getAuthor() +
                ", Published Year: " + getPublishedYear() +
                ", Description: " + getDescription());
    }
}