/*
 * Giray Berk Kuþhan 
 * Section 4
 * 10889878942
 * Homework 2 - Q1
 */
import java.util.*;

public class Q1 {
    static class Network {
        private int stops;
        private LinkedHashMap<String, List<String>> routeMap;
        private List<String> creationOrder;

        public Network(int stops) {
            this.stops = stops;
            routeMap = new LinkedHashMap<>();
            creationOrder = new ArrayList<>();
        }

        public void connectStops(String start, String end) {
            routeMap.computeIfAbsent(start, k -> {
                creationOrder.add(k);
                return new ArrayList<>();
            }).add(end);
            routeMap.putIfAbsent(end, new ArrayList<>());
        }
/*
 * "stops," "routeMap," and "creationOrder" are three private instance variables 
 * for the "Network" class that is defined. 
 * The "stops" variable is initialized by the constructor, 
 * and new instances of "routeMap" and "creationOrder" are also created. 
 * When two stops are connected using the "connectStops" technique, 
 * the start stop is also added to the "creationOrder" if it is not already there.
 */
        public LinkedHashMap<String, List<String>> getRouteMap() {
            return routeMap;
        }

        public List<String> getCreationOrder() {
            return creationOrder;
        }

        public boolean checkTree() {
            if (stops - 1 != countEdges()) {
                return false;
            }
            Set<String> visited = new HashSet<>();
            String initialStop = routeMap.keySet().iterator().next();
            return verifyConnectivity(initialStop, visited) && visited.size() == stops;
        }
        /*
         * The "Network" class's three methods are defined in this code. 
         * The "routeMap" instance variable is the result of the "getRouteMap" method.
         *  The "creationOrder" instance variable is returned by the "getCreationOrder" method.
         *   By confirming that there are exactly "stops - 1" edges, the graph is connected, 
         *   and all "stops" have been visited, the "checkTree" technique determines whether the "routeMap" forms a tree.
         */

        private boolean verifyConnectivity(String current, Set<String> visited) {
            visited.add(current);
            List<String> neighbors = routeMap.get(current);
            if (neighbors != null) {
                int i = 0;
                while (i < neighbors.size()) {
                    String neighbor = neighbors.get(i);
                    if (!visited.contains(neighbor)) {
                        if (!verifyConnectivity(neighbor, visited)) {
                            return false;
                        }
                    }
                    i++;
                }
            }
            return true;
        }
        /*
         * This program determines whether a graph represented by a map of routes is entirely 
         * connected when it starts at a specific node. 
         * Recursively traversing the graph, adding visited nodes to a set, 
         * and determining if all nodes are reachable from the initial 
         * node are the methods used to accomplish this. 
         * The method returns false if a node cannot be reached and true otherwise.
         */

        private int countEdges() {
            int edgeCount = 0;
            for (List<String> neighbors : routeMap.values()) {
                edgeCount += neighbors.size();
            }
            return edgeCount;
        }
    }
    /*
     * This code counts the total number of edges in a graph represented by a map of routes. 
     * It iterates over all the lists of neighbors in the map and adds the size of each list to a 
     * counter. The final count is returned.
     */

    public static void main(String[] args) {
        Scanner inputScanner = new Scanner(System.in);
        System.out.println("Enter the number of taxi pickups:");
        int stops = Integer.parseInt(inputScanner.nextLine());
        System.out.println("Enter the number of taxi rides:");
        int rides = Integer.parseInt(inputScanner.nextLine());
        Network network = new Network(stops);
        System.out.println("Enter the taxi rides:");
        int i = 0;
        /*
         * The main method of a Java program is this code. 
         * It constructs a Network object to represent the stops in the taxi network and 
         * prompts the user to submit details for each ride before creating a 
         * Scanner object to read input from the command line. 
         * For the purpose of keeping track of the number of rides entered, 
         * the variable "i" is initialized to 0.
         */
        do {
            String[] ride = inputScanner.nextLine().split(" ");
            network.connectStops(ride[0], ride[1]);
            i++;
        } while (i < rides);

        LinkedHashMap<String, List<String>> routeMap = network.getRouteMap();
        List<String> creationOrder = network.getCreationOrder();
        routeMap.forEach((k, v) -> {
            v.sort((a, b) -> creationOrder.indexOf(a) - creationOrder.indexOf(b));
            Collections.reverse(v);
            System.out.println(k + ": " + (v.isEmpty() ? "" : String.join(" ", v)));
        });
        System.out.println(network.checkTree() ? "This ride network can be kept in a tree structure." : 
        "This ride network cannot be kept in a tree structure.");
/*
 * This program gathers data from the user for each ride, 
 * links the associated stops in the taxi network, and then 
 * repeats the procedure for the predetermined number of rides. 
 * After that, it reverses the order of the neighbors in the lists of neighbors 
 * in the route map and publishes the resulting routes for each stop. 
 * In order to print the proper message, 
 * it then determines if the taxi network can be represented as a tree.
 */
    }
}