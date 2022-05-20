package dev.alexengrig.sample.ecq;

public class Main {

    public static void main(String[] args) {
        System.out.println("CRUD");
        CrudUserFlow crudFlow = new CrudUserFlow();
        crudFlow.run();
        System.out.println("CQRS");
        CqrsUserFlow cqrsFlow = new CqrsUserFlow();
        cqrsFlow.run();
        System.out.println("Event-sourcing");
        EventSourcingUserFlow esFlow = new EventSourcingUserFlow();
        esFlow.run();
        System.out.println("Event-sourcing + CQRS");
        EsCqrsUserFlow esCqrsFlow = new EsCqrsUserFlow();
        esCqrsFlow.run();
    }

}