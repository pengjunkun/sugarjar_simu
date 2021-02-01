package Edge;

import tools.LRUCache;
import tools.MyConf;

import java.util.HashMap;

/**
 * Created by JackPeng(pengjunkun@gmail.com) on 2021/1/29.
 */
public class EdgeServer
{
	private int id;
	private float lat;
	private float lon;
	private int size;
	private int usedSize;

	private HashMap<Integer, Integer> cacheTypeInfo;
	// in LRUCache <vid,tid>
	private LRUCache lruCache;
	private long totalRequest;
	private long hitRequest;
	private long missRequest;

	public EdgeServer(int id, float lat, float lon, int size)
	{
		this.id = id;
		this.lat = lat;
		this.lon = lon;
		this.size = size;
		lruCache = new LRUCache(size);
		cacheTypeInfo = new HashMap<>();
	}

	/**
	 * get content from edge, if edge don't has it, edge will fetch from remote server and trigger the caching mechanism
	 * may evict old ones
	 * update the different type saving info
	 *
	 * @param timestamp
	 * @param tid
	 * @param vid
	 * @return the latency caused in this request
	 */
	public int getContent(long timestamp, int tid, long vid)
	{
		totalRequest++;
		//hit in this edge
		if (lruCache.get(vid) != null)
		{
			hitRequest++;
			return MyConf.HIT_LATENCY;
		}

		//missed in this edge, will get from remote server
		missRequest++;
		if (checkAdmission(timestamp, tid, vid))
		{
			//if pass the admission
			//use LRU now
			int preTid = lruCache.putContentGetReplacedType(vid, tid);
			if (preTid != -1)
			{
				//minus old type info
				cacheTypeInfo.put(preTid,
						cacheTypeInfo.getOrDefault(tid, 0) - MyConf.FILE_SIZE);
			}

			//add new added type info
			cacheTypeInfo.put(tid,
					MyConf.FILE_SIZE + cacheTypeInfo.getOrDefault(tid, 0));
		}

		return MyConf.MISS_LATENCY;
	}

	public HashMap getCacheTypeInfo()
	{
		return cacheTypeInfo;
	}

	/**
	 * this is the admission hook for a Cache Algorithm
	 *
	 * @param timestamp
	 * @param tid
	 * @param vid
	 * @return
	 */
	private boolean checkAdmission(long timestamp, int tid, long vid)
	{
		//use LRU now, so cache all
		return true;
	}

	/**
	 * report the request statistics
	 * @return { totalRequest, hitRequest, missRequest }
	 */
	public long[] getRequestStat()
	{
		return new long[] { totalRequest, hitRequest, missRequest };
	}
}
