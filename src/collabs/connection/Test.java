package collabs.connection;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: Aleksey A.
 * Date: 07.03.14
 * Time: 22:08
 */
public class Test {
    public static void main(String[] args) {
        Map<Integer, String> map = new HashMap<Integer, String>();
        for (int i = 0; i < 5000; i++) {
            map.put(i, Integer.toString(i) + "xhigoer hgorae hge");
        }
        for (int i = 0; i < 5000; i++) {
            map.put(i, Integer.toString(i) + "xhigoer hgorae hge");
        }
        System.out.println(map.size());
        System.out.println(map.get(1));
    }
}
