package algorithm;

import java.util.List;
import java.util.ArrayList;
import entity.Interest;
import entity.Interest.InterestBuilder;

// Route Optimization Algorithm
// Zhenyu Pan

// 问题简化：在一个xy平面内(New York City)选择N个点(pinned interests)，每个点有各自的数值(interest.time.visit), 然后需要画M个圈(travel days)，
//          使得每个圈内有2-3个点，每个点只可存在于一个圈内，并且满足所有圈分布均匀，圈总面积较小，且圈内点的数值合不大于10(daily possible visit time)

// 优化目标：保证用户在整个旅行中，景点均匀分配到每天，不至于某天特别累，某天特别闲；并且保证每天游玩的景点距离之间较近，不至于前后奔波劳累；
//          还需要确保用户去景点时，景点开门营业，并且能充分游玩，不至于吃闭门羹，或者被关门轰出

// (try later)  Option 1: Vehicle Routing Algorithm
// (need AI/ML) Option 2: Clustering Algorithm
//    ==>       Option 3: Develop Route Optimization Algorithm on our own

// Algorithm:
// Step 1: briefly check if selection is valid: based on travel days and the numbers of interests, check if it is possible to generate schedule.
//         the number of interests should be smaller than the maximum possible interests that can be visited during travel
//         briefly check the number of interests vs days (approximately 2-3 interests per day)
// Step 2: calculate distances between each interests. make sure shortest travel distance between interests each day
//         using BFS. find the shortest distance of two dots then put them into one day slot;
//         continue BFS for the rest of the dots with same procedure;
// Step 3: put each interests into different days slots: morning(08-12 4h)/afternoon(14-18 4h)/evening(20-22 2h)
//         check time.open and time.close is valid for that day's visit
//         [optimize shortest distance first, if cannot meet demand then give up distance and ask for optimized time schedule]
//         this also can fail to generate if interest's visit time cannot be put into slots

// eg.
// input: travel days: 3 days;
//        travel person: 2 persons;
//        pinned interests:
//                        StatueOfLiberty{ id: 0001, coordinate.x: 10, coordinate.y: 12, time.visit: 4.0, price: 50.0 }
//                        +
//                        EmpireState{ id: 0003, coordinate.x: 20, coordinate.y: 47, time.visit: 3.0, price: 30.0 }
//                        +
//                        BrooklynBridge{ id: 0006, coordinate.x: 17, coordinate.y: 50, time.visit: 2.0, price: 0.0 }
//                        +
//                        911Museum{ id: 0012, coordinate.x: 33, coordinate.y: 15, time.visit: 3.0, price: 20 }
//                        +
//                        5thAvenue{ id: 0031, coordinate.x: 25, coordinate.y: 6, time.visit: 3.0, price: 0 }
//
// output: optimized route: {{0001, 0012} {0003, 0006} {0031}}
//         total ticket price: 200.0

// Extra Feature...pending
// pretty schedule:
// {{day 1:
//         07:00 - 08:00: breakfast
//         08:00 - 12:00: [0001]
//         12:00 - 14:00: lunch
//         14:00 - 17:00: [0012]
//         17:00 - 18:00: free => recommend interests
//         18:00 - 20:00: dinner
//         20:00 - 22:00: free => recommend interests
//         22:00 - 07:00: sleep
//         }
//  {day 2:
//         07:00 - 08:00: breakfast
//         08:00 - 11:00: [0003]
//         11:00 - 12:00: free => recommend interests
//         12:00 - 14:00: lunch
//         14:00 - 16:00: [0006]
//         16:00 - 18:00: free => recommend interests
//         18:00 - 20:00: dinner
//         20:00 - 22:00: free => recommend interests
//         22:00 - 07:00: sleep
//         }
//  {day 3:
//         07:00 - 08:00: breakfast
//         08:00 - 11:00: [0031]
//         11:00 - 12:00: free => recommend interests
//         12:00 - 14:00: lunch
//         14:00 - 18:00: free => recommend interests
//         18:00 - 20:00: dinner
//         20:00 - 22:00: free => recommend interests
//         22:00 - 07:00: sleep
//         }}
// ==> display them on google map with routes, pictures and chart



//----------------------- Algorithm Guidance -----------------------//
// 1. function: optimizeRoute()
//    input:    List<Interest>        pinnedInterests
//              int                   days
//    output:   List<List<Interest>>  optimizedDailyInterests
//
// 2. function: calculateExpense()
//    input:    List<List<Interest>>  optimizedDailyInterests
//              int                   persons
//    output:   double                totalTravelExpense
//-------------------------------------------------------------------//

public class algorithm {

    // optimize travel route
    public List<List<Interest>> optimizeRoute(List<Interest> pinnedInterests, int days) {

        List<List<Interest>> result = new ArrayList<>();
        System.out.println("OptimizeRoute() in algorithm was called");
        System.out.println("Pinned interests are: ");
        printPinnedInterests(pinnedInterests);
        System.out.println("Total travel days are: " + days);
        // calculate total visit time and check if valid
        int dailyVisitTime = 10;
        int totalVisitTime = days * dailyVisitTime;
        int pinnedVisitTime = 0;
        for (Interest interest : pinnedInterests) {
            pinnedVisitTime += interest.getSuggestVisitTime();
        }
        if (pinnedVisitTime > totalVisitTime) {
            System.out.println("Too many interests in limited travel days. Schedule is too tight. Please reduce interests or increase travel days!");
            return null;
        }

        // briefly check the "total number of interests / days" rate. Approximately 2-3 interests per day is good
        // there will be three different cases
        // then implement route optimization algorithms in each cases
        int numberOfInterests = pinnedInterests.size();
        if (numberOfInterests / days >= 3) {
            System.out.println("Case 1: numberOfInterests / days >= 3");
            // three interest per day. final days may apply four or more interests per day
            // It still has chance to be done (cuz already passed the total travel time check) (Need to double check open time and close time)

            // e.g.
            // 7 interests:   {a b c d e f g}
            // interest_index: 0 1 2 3 4 5 6
            // 2 days:    {{} {}}
            // day_index:  0  1
            // 1st step: {{a} {b}}
            // ...
            //           {{a c e g} {b d f}}

            // generate empty daily schedule
            for (int i = 0; i < days; i++) {
                List<Interest> daily = new ArrayList<>();
                result.add(daily);
            }
            // add interests into daily schedule sequentially
            for (int index = 0; index < numberOfInterests; index++) {
                result.get(index % days).add(pinnedInterests.get(index));
            }

        } else if (numberOfInterests / days < 2) {
            System.out.println("Case 2: numberOfInterests / days < 2");
            // one interest per day. final days may apply free time

            // e.g.
            // 5 interests: (a b c d e}
            // 3 days: {{} {} {}}
            // 1st step: {{a} {b} {c}}
            // buffer: {a b c}
            // extra to be scheduled: {d e} total 5-3=2
            // 2nd step: find closest of d and e from {a b c}
            // {b d} d scheduled into {b} day
            // buffer-b = {a c}
            // {c e} e scheduled into {c} day
            // final result: {{a} {b d} {c e}}

            // put one interest in each day first
            List<Interest> buffer = new ArrayList<>(); // to store scheduled interests in 1st step
            for (int i = 0; i < days; i++) {
                List<Interest> daily = new ArrayList<>();
                daily.add(pinnedInterests.get(i));
                buffer.add(pinnedInterests.get(i));
                result.add(daily);
            }
            // post-check if there are extra interests. put them in each days again. check closest interests and put extra into that slot
            if (days < numberOfInterests) {
                int numberOfExtraInterests = numberOfInterests - days;
                for (int i = numberOfExtraInterests; i <= days; i++) {
                    Interest spot = pinnedInterests.get(i + 1);
                    Interest closest = findClosest(buffer, spot);
                    List<Interest> specificDay = new ArrayList<>();
                    specificDay.add(closest);
                    result.get(result.indexOf(specificDay)).add(spot);
                    buffer.remove(closest);
                }
            }

        } else { // numberOfInterests / days >= 2 && numberOfInterests / days <= 3
            System.out.println("Case 3: numberOfInterests / days >= 2 && numberOfInterests / days <= 3");
            // two interests per day. final days may apply three interests per day

            // e.g.
            // 8 interests: (a b c d e f g h}
            // 3 days: {{} {} {}}
            // 1st step: {{a} {} {}}
            // find closest to a in {b c d e f} => e
            // {{a e} {} {}}
            // ...
            // {{a e} {c f} {b g}}
            // extra 2 interests: {d h} put these two into two of the three days
            // result: {{a e h} {c f} {b g d}}

            // put two close interests in each day first
            List<List<Interest>> buffer = new ArrayList<>(); // to store scheduled list of list of interests in 1st step
            for (int i = 0; i < days; i++) {
                List<Interest> daily = new ArrayList<>();
                Interest one = pinnedInterests.get(0);
                daily.add(one);
                Interest two = findClosest(pinnedInterests, one);
                daily.add(two);
                pinnedInterests.remove(one);
                pinnedInterests.remove(two);
                result.add(daily);
                buffer.add(daily);
            }
            // post-check if there are extra interests. put them in each days again. check closest interests and put extra into that slot
            if ((days * 2) < numberOfInterests) {
                int numberOfExtraInterests = numberOfInterests - days * 2;
                for (int i = 0; i < numberOfExtraInterests; i++) {
                    // calculate total distance of extra interest to everyday's two interests and find the closest
                    List<Interest> optimizeDay = result.get(result.indexOf(findClosestDaily(buffer, pinnedInterests.get(i))));
                    optimizeDay.add(pinnedInterests.get(i)); // add one extra interest to that optimize day
                    buffer.remove(optimizeDay);
                }
            }
        }

        // generate route
        System.out.println("Optimized daily interests are: ");
        printResult(result);
        return result;
    }

    // calculate travel expense
    public double calculateExpense(List<List<Interest>> result, int persons) {
        System.out.println("CalculateExpense() in algorithm was called");
        // check corner case first!
        if (result == null) {
            System.out.println("The input interests are null");
            System.out.println("Total travel expense of " + persons + " persons is " + 0.0 + " dollars");
            return 0.0;
        }
        System.out.println("Optimized daily interests are: ");
        printResult(result);
        System.out.println("Total travel persons are: " + persons);
        double sum = 0;
        for (List<Interest> daily : result) {
            for (Interest interest : daily) {
                sum += interest.getPrice();
            }
        }
        double totalExpense = sum * persons;
        System.out.println("Total travel expense of " + persons + " persons is " + totalExpense + " dollars");
        return totalExpense;
    }

    // Convert longitude to a X value in World Coordinates
    private static final double lon2x(double lon) {
        return (lon + 180f) / 360f * 256f;
    }

    // Convert latitude to a Y value in World Coordinates
    private static final double lat2y(double aLat) {
        return ((1 - Math.log(Math.tan(aLat * Math.PI / 180) + 1 / Math.cos(aLat * Math.PI / 180)) / Math.PI) / 2 * Math.pow(2, 0)) * 256;
    }

    // Convert X value in World Coordinates to longitude
    private static final double x2lng(double x) {
        return x * 360 / 256 - 180;
    }

    // Convert Y value in World Coordinates to latitude
    private static final double y2lat(double y) {
        double z = Math.pow(Math.E, (2 * Math.PI * (1 - y / 128)));
        return Math.asin((z - 1) / (z + 1)) * 180 / Math.PI;
    }

    // calculate straight distance between two spots on map spot1(x1, y1), spot2(x2, y2)
    private double calculateDistance(double x1, double y1, double x2, double y2) {
        return Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
    }

    // distance between candidate and spot
    private double distance(Interest candidate, Interest spot) {
        double candidateX = lon2x(candidate.getLng());
        double candidateY = lat2y(candidate.getLat());
        double spotX = lon2x(spot.getLng());
        double spotY = lat2y(spot.getLat());
        return calculateDistance(candidateX, candidateY, spotX, spotY);
    }

    // get the closest interest of one given interest
    private Interest findClosest(List<Interest> pinnedInterests, Interest spot) {
        double min = Integer.MAX_VALUE;
        Interest closest = null;
        pinnedInterests.remove(spot); // to avoid self to self which is 0 distance
        for (Interest candidate : pinnedInterests) {
            double distance = distance(candidate, spot);

            if (distance < min) {
                closest = candidate;
                min = distance;
            }
        }
        return closest;
    }

    // get the total distance and find the shortest of one spot to list of list of interests
    private List<Interest> findClosestDaily(List<List<Interest>> preResult, Interest spot) {
        double min = Integer.MAX_VALUE;
        int index = 0;
        double totalDistanceDaily = 0;
        for (List<Interest> day : preResult) {
            for (Interest candidate : day) {
                totalDistanceDaily += distance(candidate, spot);
            }
            if (totalDistanceDaily < min) {
                index = preResult.indexOf(day);
                min = totalDistanceDaily;
            }
        }
        return preResult.get(index);
    }

    // helper function for tests
    private void printResult(List<List<Interest>> result){
        System.out.print("{");
        for (List<Interest> daily : result) {
            System.out.print("{");
            for (Interest interest : daily) {
                System.out.print(interest.getName() + " ");
            }
            System.out.print("}");
        }
        System.out.println("}");
    }

    // helper function for debug
    private void printPinnedInterests(List<Interest> pinnedInterests){
        System.out.print("{");
        for (Interest interest : pinnedInterests) {
            System.out.print(interest.getName() + " ");
        }
        System.out.println("}");
    }

//    // generate travel schedule
//    public List<Schedule> generateSchedule(List<List<Interest>> result) {
//        // TODO
//    }


    public static void main(String[] args) {
        // list of pinned interests by user
        List<Interest> pinnedInterests1 = new ArrayList<>();
        List<Interest> pinnedInterests2 = new ArrayList<>();
        List<Interest> pinnedInterests3 = new ArrayList<>();
        List<Interest> pinnedInterests4 = new ArrayList<>();

        // 5 interests with name and lat lng for tests
        InterestBuilder builder1 = new InterestBuilder();
        builder1.setName("StatueOfLiberty");
        builder1.setLat(40.6892534);
        builder1.setLng(-74.0466891);
        builder1.setSuggestVisitTime(4.0);
        builder1.setPrice(50.0);
        pinnedInterests1.add(builder1.build());
        pinnedInterests2.add(builder1.build());
        pinnedInterests3.add(builder1.build());
        pinnedInterests4.add(builder1.build());

        InterestBuilder builder2 = new InterestBuilder();
        builder2.setName("EmpireStateBuilding");
        builder2.setLat(40.7485492);
        builder2.setLng(-73.9879522);
        builder2.setSuggestVisitTime(3.0);
        builder2.setPrice(30.0);
        pinnedInterests1.add(builder2.build());
        pinnedInterests2.add(builder2.build());
        pinnedInterests3.add(builder2.build());
        pinnedInterests4.add(builder2.build());

        InterestBuilder builder3 = new InterestBuilder();
        builder3.setName("BrooklynBridge");
        builder3.setLat(40.7058134);
        builder3.setLng(-73.9981622);
        builder3.setSuggestVisitTime(2.0);
        builder3.setPrice(0.0);
        pinnedInterests1.add(builder3.build());
        pinnedInterests2.add(builder3.build());
        pinnedInterests3.add(builder3.build());
        pinnedInterests4.add(builder3.build());

        InterestBuilder builder4 = new InterestBuilder();
        builder4.setName("911Memorial");
        builder4.setLat(40.708788);
        builder4.setLng(-74.0095311);
        builder4.setSuggestVisitTime(3.0);
        builder4.setPrice(20.0);
        pinnedInterests1.add(builder4.build());
        pinnedInterests2.add(builder4.build());
        pinnedInterests3.add(builder4.build());
        pinnedInterests4.add(builder4.build());

        InterestBuilder builder5 = new InterestBuilder();
        builder5.setName("5thAvenue");
        builder5.setLat(40.7744186);
        builder5.setLng(-73.9678064);
        builder5.setSuggestVisitTime(3.0);
        builder5.setPrice(0.0);
        pinnedInterests1.add(builder5.build());
        pinnedInterests2.add(builder5.build());
        pinnedInterests3.add(builder5.build());
        pinnedInterests4.add(builder5.build());

        InterestBuilder builder6 = new InterestBuilder();
        builder6.setName("TheMuseumOfModernArt");
        builder6.setLat(40.7609406);
        builder6.setLng(-73.9801901);
        builder6.setSuggestVisitTime(2.0);
        builder6.setPrice(20.0);
        pinnedInterests4.add(builder6.build());

        InterestBuilder builder7 = new InterestBuilder();
        builder7.setName("WallStreet");
        builder7.setLat(40.7060179);
        builder7.setLng(-74.0110099);
        builder7.setSuggestVisitTime(1.0);
        builder7.setPrice(0.0);
        pinnedInterests4.add(builder7.build());

        algorithm test = new algorithm();

        System.out.println("===============================================================================\n");

        // test case one: 5 interests in 1 day, 3 persons
        System.out.println("Test case one: 5 interests in 1 day, 3 persons \n");
        System.out.println("########## Expected result ##########");
        System.out.println("Too many interests in limited travel days. Schedule is too tight. Please reduce interests or increase travel days!");
        System.out.println("Total travel expense of 3 persons is 0.0 dollars \n");
        System.out.println("########## Real result ##########");
        List<List<Interest>> result1;
        result1 = test.optimizeRoute(pinnedInterests1, 1);
        test.calculateExpense(result1, 3);
        System.out.println("\n===============================================================================\n");


        // test case two: 5 interests in 2 days, 1 person
        System.out.println("Test case two: 5 interests in 2 days, 1 person \n");
        System.out.println("########## Expected result ##########");
        System.out.println("{{StatueOfLiberty 911Memorial BrooklynBridge }{EmpireStateBuilding 5thAvenue }}");
        System.out.println("Total travel expense of 1 persons is 100.0 dollars \n");
        System.out.println("########## Real result ##########");
        List<List<Interest>> result2;
        result2 = test.optimizeRoute(pinnedInterests2, 2);
        test.calculateExpense(result2, 1);
        System.out.println("\n===============================================================================\n");


        // test case three: 5 interests in 3 days, 2 persons
        System.out.println("Test case three: 5 interests in 3 days, 2 persons \n");
        System.out.println("########## Expected result ##########");
        System.out.println("{{StatueOfLiberty }{EmpireStateBuilding 5thAvenue }{BrooklynBridge 911Memorial }}");
        System.out.println("Total travel expense of 2 persons is 200.0 dollars \n");
        System.out.println("########## Real result ##########");
        List<List<Interest>> result3;
        result3 = test.optimizeRoute(pinnedInterests3, 3);
        test.calculateExpense(result3, 2);
        System.out.println("\n===============================================================================\n");

        // test case four: 7 interests in 2 days, 3 persons
        System.out.println("Test case four: 7 interests in 2 days, 3 persons \n");
        System.out.println("########## Expected result ##########");
        System.out.println("{{StatueOfLiberty BrooklynBridge 5thAvenue WallStreet }{EmpireStateBuilding 911Memorial TheMuseumOfModernArt }}");
        System.out.println("Total travel expense of 3 persons is 360.0 dollars \n");
        System.out.println("########## Real result ##########");
        List<List<Interest>> result4;
        result4 = test.optimizeRoute(pinnedInterests4, 2);
        test.calculateExpense(result4, 3);
        System.out.println("\n===============================================================================\n");
    }
}
