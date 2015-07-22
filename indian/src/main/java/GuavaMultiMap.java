import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

import java.util.List;

/**
 * Created by krayush on 08-07-2015.
 */
public class GuavaMultiMap {
    public static void main(String[] args)
    {
        ListMultimap<String, String> multimap = ArrayListMultimap.create();
        multimap.put("Ayush","TU");
        multimap.put("Ayush", "MU");
        multimap.put("PK", "Ayush");

        List<String> val= multimap.get("Ayush");
        System.out.println(val);
    }
}
