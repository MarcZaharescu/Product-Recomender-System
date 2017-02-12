package General;
import java.util.*;

public class MapUtil {

	public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(
			Map<K, V> map) {
		List<Map.Entry<K, V>> list = new LinkedList<>(map.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
			@Override
			public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
				return (o2.getValue()).compareTo(o1.getValue());
			}
		});

		Map<K, V> result = new LinkedHashMap<>();
		int index=0;
		for (Map.Entry<K, V> entry : list) {
			result.put(entry.getKey(), entry.getValue());
			
			//only first 5000 features
			if(index>=5000)
				break;
			
			index++;
		}
		return result;
	}
}
