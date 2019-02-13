package algorithm;

// Route Optimization Algorithm
// Zhenyu Pan

// 问题简化：在一个xy平面内(New York City)选择N个点(pinned interests)，每个点有各自的数值(interest.time.visit), 然后需要画M个圈(travel days)，
//          使得每个圈内有2-3个点，每个点只可存在于一个圈内，并且满足所有圈分布均匀，圈总面积较小，且圈内点的数值合不大于10(daily possible visit time)

// 优化目标：保证用户在整个旅行中，景点均匀分配到每天，不至于某天特别累，某天特别闲；并且保证每天游玩的景点距离之间较近，不至于前后奔波劳累；
//          还需要确保用户去景点时，景点开门营业，并且能充分游玩，不至于吃闭门羹，或者被关门轰出

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


// Algorithm:
// Step 1: briefly check if selection is valid: based on travel days and the numbers of interests, check if it is possible to generate schedule.
//         the number of interests should be smaller than the maximum possible interests that can be visited during travel
//         briefly check the number of interests vs days (approximately 2-3 interests per day)
// Step 2: calculate distances between each interests. make sure shortest travel distance between interests each day
//         using BFS. find the shortest distance of two dots then put them into one day slot;
//         continue BFS for the rest of the dots with same procedure;
// Step 3: put each interests into different days slots: morning(08-12 4h)/afternoon(14-18 4h)/evening(20-22 2h)
//         check time.open and time.close is valid for that day's visit
//         [optimize shortest distance first, if cannot meet demand then give up distance and ask for optimized time shedule]
//         this also can fail to generate if interest's visit time cannot be put into slots


class algorithm {
    // optimize travel route
    public List<List<interests>> optimizeRoute(List<interests> pinnedInterests, int days) {

        List<List<interests>> result = new ArrayList<>();

        // calculate total visit time and check if valid
        int dailyVisitTime = 10;
        int totalVisitTime = days * dailyVisitTime;
        int pinnedVisitTime = 0;
        for (interests interest : pinnedInterests) {
            pinnedVisitTime += interest.time.visit;
        }
        if (pinnedVisitTime > totalVisitTime) {
            return null;
        }

        // briefly check the total number of interests / days rate. Approximately 2-3 interests per day is good
        int numberOfInterests = 0;
        for (interests interest : pinnedInterests) {
            numberOfInterests++;
        }
        if (numberOfInterests / days >= 3) {
            // TODO. It still has chance to be done. Need to double check open time and close time

        } else if {numberOfInterests / days < 2} {
            // one interest per day. final days may apply free time

        } else { // numberOfInterests / days >= 2 && numberOfInterests / days <= 3
            // two interests per day. final days may apply three interests per day

        }

        // generate route


        return result;
    }

    // calculate straight distance between two spots on map spot1(x1, y1), spot2(x2, y2)
    private double calculateDistance(double x1, double y1, double x2, double y2) {
        return Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
    }

    // get the closest spot of one given spot
    private interest findClosest(List<interests> pinnedInterests, interest spot) {
        double min = Integer.MAX_VALUE;
        interest closest = null;
        pinnedInterests.remove(spot);
        for (interest candidate : pinnedInterests) {
            double distance = calculateDistance(candidate.x, candidate.y, spot.x, spot.y);
            if (distance < min) {
                closet = candidate;
                min = distance;
            }
        }
        return closest;
    }

    // calculate travel expense
    public int calculateExpense(List<List<interests>> result, int persons) {
        int sum = 0;
        for (List<interests> daily : result) {
            for (interests interest : daily) {
                sum += interest.price;
            }
        }
        return sum * persons;
    }

    // generete travel schedule
    public List<Schedule> generateSchedule(List<List<interests>> result) {
        // TO DO
    }

    public static void main(String[] args) {

    }
}
