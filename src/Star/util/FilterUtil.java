package Star.util;

import java.util.HashMap;

public class FilterUtil {
    static HashMap<String, Integer> scoreMap = new HashMap<>();
    static {
        scoreMap.put("--", 0);
        scoreMap.put("底標", 1);
        scoreMap.put("後標", 2);
        scoreMap.put("均標", 3);
        scoreMap.put("前標", 4);
        scoreMap.put("頂標", 5);
        scoreMap.put("F級", 1);
        scoreMap.put("C級", 2);
        scoreMap.put("B級", 3);
        scoreMap.put("A級", 4);
    }

    public static boolean filter (String[] ranks, int[] scores) {
        boolean valid = true;
        for (int i = 0; i < ranks.length; i++) {
            if (scoreMap.get(ranks[i]) > scores[i]) {
                valid = false;
                break;
            }
        }
        return valid;
    }
}
