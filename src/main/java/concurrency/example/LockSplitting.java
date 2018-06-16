package concurrency.example;

import java.util.HashSet;
import java.util.Set;

class ServerStatusWithoutSplitting {
    //queries and users are two completely independent variable, guard by one lock('this')
    private final Set<String> users = new HashSet<>();
    private final Set<String> queries = new HashSet<>();

    public synchronized void addUser(String u) { users.add(u); }
    public synchronized void removeUser(String u) { users.remove(u); }
    public synchronized void addQuery(String q) { queries.add(q);}
    public synchronized void removeQuery(String q) { queries.remove(q);}
}


class ServerStatusWithSplitting {
    //queries and users are two completely independent variable, so they can guard by two different locks
    private final Set<String> users = new HashSet<>();
    private final Set<String> queries = new HashSet<>();

    public  void addUser(String u) { synchronized (users) {users.add(u);} }
    public  void removeUser(String u) { synchronized (users) {users.remove(u); } }
    public  void addQuery(String q) { synchronized (queries) { queries.add(q); }}
    public  void removeQuery(String q) { synchronized (queries) { queries.remove(q);} }
}


public class LockSplitting {
}
