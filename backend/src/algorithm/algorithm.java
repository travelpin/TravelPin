package algorithm;

import java.util.List;
import java.util.ArrayList;

import entity.Interest;

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
//                        statueOfLiberty{
//                        id: 0001,
//                        coordinate.x: 10,
//                        coordinate.y: 12,
//                        category: museum,
//                        time.open: 08:00;
//                        time.close: 18:00;
//                        time.visit: 4.0;
//                        price: 50;
//                        rating: 5}
//                        +
//                        empireState{id: 0003,
//                        coordinate.x: 20,
//                        coordinate.y: 47,
//                        category: building,
//                        time.open: 08:00;
//                        time.close: 22:00;
//                        time.visit: 3.0;
//                        price: 30;
//                        rating: 5}
//                        +
//                        brooklynBridge{id: 0006,
//                        coordinate.x: 17,
//                        coordinate.y: 50,
//                        category: bridge,
//                        time.open: 00:00;
//                        time.close: 24:00;
//                        time.visit: 2.0;
//                        price: 0;
//                        rating: 4}
//                        +
//                        911Museum{id: 0012,
//                        coordinate.x: 33,
//                        coordinate.y: 15,
//                        category: museum,
//                        time.open: 08:00;
//                        time.close: 18:00;
//                        time.visit: 3.0;
//                        price: 20;
//                        rating: 4}
//                        +
//                        fifthAvenue{id: 0031,
//                        coordinate.x: 25,
//                        coordinate.y: 6,
//                        category: shopping,
//                        time.open: 00:00;
//                        time.close: 24:00;
//                        time.visit: 3.0;
//                        price: 0;
//                        rating: 5}
//
// output: optimized route: {{0001, 0012} {0003, 0006} {0031}}
//         total ticket price: 200

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


public class algorithm {
    // optimize travel route
    public List<List<Interest>> optimizeRoute(List<Interest> pinnedInterests, int days) {

        List<List<Interest>> result = new ArrayList<>();

        // calculate total visit time and check if valid
        int dailyVisitTime = 10;
        int totalVisitTime = days * dailyVisitTime;
        int pinnedVisitTime = 0;
        for (Interest interest : pinnedInterests) {
            pinnedVisitTime += interest.getSuggestVisitTime();
        }
        if (pinnedVisitTime > totalVisitTime) {
            System.out.println("Too many interests. Schedule is too tight. Please re-pin interests!");
        }

        // briefly check the "total number of interests / days" rate. Approximately 2-3 interests per day is good
        // then implement route optimization algorithms in each cases
        int numberOfInterests = pinnedInterests.size();
        if (numberOfInterests / days >= 3) {
            // TODO: It still has chance to be done. Need to double check open time and close time
            return null;
        } else if (numberOfInterests / days < 2) {
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
                for (int i = numberOfExtraInterests + 1; i < days; i++) {
                    Interest spot = pinnedInterests.get(i);
                    Interest closest = findClosest(buffer, spot);
                    List<Interest> specificDay = new ArrayList<>();
                    specificDay.add(closest);
                    result.get(result.indexOf(specificDay)).add(spot);
                    buffer.remove(closest);
                }
            }

        } else { // numberOfInterests / days >= 2 && numberOfInterests / days <= 3
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
            List<Interest> buffer = new ArrayList<>(); // to store scheduled interests in 1st step
            for (int i = 0; i < days; i++) {
                List<Interest> daily = new ArrayList<>();
                Interest one = pinnedInterests.get(0);
                daily.add(one);
                Interest two = findClosest(pinnedInterests, one);
                daily.add(two);
                pinnedInterests.remove(one);
                pinnedInterests.remove(two);
                result.add(daily);
            }
            // post-check if there are extra interests. put them in each days again. check closest interests and put extra into that slot
            if ((days * 2) < numberOfInterests) {
                int numberOfExtraInterests = numberOfInterests - days * 2;
                for (int i = 0; i < numberOfExtraInterests; i++) {
                    // calculate total distance of extra interest to everyday's two interests and find the closest

                }
            }
        }

        // generate route
        return result;
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

    // calculate travel expense
    public int calculateExpense(List<List<Interest>> result, int persons) {
        int sum = 0;
        for (List<Interest> daily : result) {
            for (Interest interest : daily) {
                // sum += interest.price;
            }
        }
        return sum * persons;
    }

    /*
    // generate travel schedule
    public List<Schedule> generateSchedule(List<List<Interest>> result) {
        // TO DO
    }

    public static void main(String[] args) {

    }
    */
}
