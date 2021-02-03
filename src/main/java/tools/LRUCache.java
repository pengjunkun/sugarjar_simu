package tools;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by JackPeng(pengjunkun@gmail.com) on 2021/1/29.
 * <p>
 * used to store caching info <vid,tid>
 */
public class LRUCache extends LinkedHashMap<String, Integer>
{
	private int capacity;

	public LRUCache(int capactiy)
	{
		super(capactiy, 0.75F, true);
		this.capacity = capactiy;
	}

	/**
	 * use this to put in and get replaced video's type
	 * @param vid
	 * @param tid
	 * @return the tid of replaced video( NOT_REPLACEMENT means no replacement happened)
	 */
	public int putContentGetReplacedType(String vid, int tid)
	{
		int res = MyConf.NOT_REPLACEMENT;
		if (size() * MyConf.FILE_SIZE + MyConf.FILE_SIZE > capacity)
		{
			res = entrySet().iterator().next().getValue();
		}
		put(vid, tid);
		return res;

	}

	@Override protected boolean removeEldestEntry(Map.Entry eldest)
	{
		if (size() * MyConf.FILE_SIZE > capacity)
			return true;
		return false;
	}

}
