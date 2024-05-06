/**
 * Test Class
 */
public class Test {
    public static void main(String[] args) {
        Map<Integer, String> map = new AVLTreeMap<>();
        map.put(1, "A");
        map.put(2, "B");
        map.put(3, "C");
        map.put(4, "D");
        map.put(5, "E");
        map.put(6, "F");
        map.put(7, "G");
        map.put(8, "H");
        map.put(9, "I");
        map.put(10, "J");
        System.out.println("AVLTreeMap Test:");
        System.out.println("AVLTreeMap Initial Content");
        Iterable<Entry<Integer, String>> entries = map.entrySet();
        for (Entry<Integer, String> entry : entries) {
            System.out.print("(" + entry.getKey() + "," + entry.getValue() + ")");
        }
        System.out.println();

        System.out.println("Delete KEY=3 Result:");
        map.remove(3);
        entries = map.entrySet();
        for (Entry<Integer, String> entry : entries) {
            System.out.print("(" + entry.getKey() + "," + entry.getValue() + ")");
        }
        System.out.println();

        System.out.println("The result after modifying the value of KEY=5 to M5:");
        map.put(5, "M5");
        entries = map.entrySet();
        for (Entry<Integer, String> entry : entries) {
            System.out.print("(" + entry.getKey() + "," + entry.getValue() + ")");
        }
        System.out.println();

        System.out.println("Get Value with KEY=9:" + map.get(9));
        System.out.println("The result after adding KEY=100 VALUE=HELLO:");
        map.put(100, "HELLO");
        entries = map.entrySet();
        for (Entry<Integer, String> entry : entries) {
            System.out.print("(" + entry.getKey() + "," + entry.getValue() + ")");
        }
        System.out.println();
        System.out.println("The number of elements in AVLTreeMap = " + map.size());
        System.out.println("Is AVLTreeMap empty = " + map.isEmpty());


        System.out.println("\n\n\nHashMap Test:");
        map = new HashMap<>();
        map.put(1, "A");
        map.put(2, "B");
        map.put(3, "C");
        map.put(4, "D");
        map.put(5, "E");
        map.put(6, "F");
        map.put(7, "G");
        map.put(8, "H");
        map.put(9, "I");
        map.put(10, "J");
        System.out.println("HashMap Initial content");
        entries = map.entrySet();
        for (Entry<Integer, String> entry : entries) {
            System.out.print("(" + entry.getKey() + "," + entry.getValue() + ")");
        }
        System.out.println();

        System.out.println("Delete KEY=3 Result:");
        map.remove(3);
        entries = map.entrySet();
        for (Entry<Integer, String> entry : entries) {
            System.out.print("(" + entry.getKey() + "," + entry.getValue() + ")");
        }
        System.out.println();

        System.out.println("The result after modifying the value of KEY=5 to M5:");
        map.put(5, "M5");
        entries = map.entrySet();
        for (Entry<Integer, String> entry : entries) {
            System.out.print("(" + entry.getKey() + "," + entry.getValue() + ")");
        }
        System.out.println();
        System.out.println("Get Value with KEY=9:" + map.get(9));

        System.out.println("The result after adding KEY=100 VALUE=HELLO:");
        map.put(100, "HELLO");
        entries = map.entrySet();
        for (Entry<Integer, String> entry : entries) {
            System.out.print("(" + entry.getKey() + "," + entry.getValue() + ")");
        }
        System.out.println();


        System.out.println("The number of elements in HashMap = " + map.size());
        System.out.println("Is HashMap empty = " + map.isEmpty());

    }

}
