import java.util.*;

public class Main {
    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();

    public static void main(String[] args) throws InterruptedException {

        String[] routes = new String[1000];
        for (int i = 0; i < routes.length; i++) {
            routes[i] = generateRoute("RLRFR", 100);
        }

        List<Thread> threads = new ArrayList<>();

        for (String route : routes) {
            Thread thread = new Thread(() -> {
                Integer numberOfRoute = 0;
                for (int i = 0; i < route.length(); i++) {
                    if (route.charAt(i) == 'R') {
                        numberOfRoute++;
                    }
                }
                synchronized (numberOfRoute) {
                    if (sizeToFreq.containsKey(numberOfRoute)) {
                        sizeToFreq.put(numberOfRoute, sizeToFreq.get(numberOfRoute) + 1);
                    } else {
                        sizeToFreq.put(numberOfRoute, 1);
                    }
                }
            });
            threads.add(thread);
            thread.start();
        }
        for (Thread thread : threads) {
            thread.join();
        }

        int maxValue = 0;
        int maxKey = 0;
        for (Integer key : sizeToFreq.keySet()) {
            if (sizeToFreq.get(key) > maxValue) {
                maxValue = sizeToFreq.get(key);
                maxKey = key;
            }
        }

        System.out.println("Самое частое количество повторений "
                + maxKey + " (встретилость "
                + maxValue + " раз)\n");

        System.out.println("Другие размеры:");
        for (Integer key : sizeToFreq.keySet()) {
            if (sizeToFreq.get(key) > maxKey) {
                int value = sizeToFreq.get(key);
                System.out.println("- " + key + " (" + value + " раз)");
            }
        }
    }


    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }
}