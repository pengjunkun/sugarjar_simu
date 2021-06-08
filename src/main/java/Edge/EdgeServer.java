package Edge;

import tools.LRUCache;
import tools.MyConf;
import tools.MyTool;

import java.util.*;

/**
 * Created by JackPeng(pengjunkun@gmail.com) on 2021/1/29.
 */
public class EdgeServer {
    private int id;
    private double lat;
    private double lon;
    private int size;
    private int usedSize;
    public int[] fromMEC=new int[MyConf.edgesInfo.length];
    public int[] fromMEC_hit=new int[MyConf.edgesInfo.length];

    private HashMap<Integer, Integer> cacheTypeInfo;
    // in LRUCache <vid,tid>
    private LRUCache lruCache;
    private long totalRequest;
    private long hitRequest;
    private long missRequest;

    //for now, treated all the edges as each other's neighbor
    private HashMap<Integer,NeighborEdgeInfo> neighbors;

    public EdgeServer(int id, double lat, double lon, int size) {
        this.id = id;
        this.lat = lat;
        this.lon = lon;
        this.size = size;
        lruCache = new LRUCache(size);
        cacheTypeInfo = new HashMap<>();
        neighbors=new HashMap<>();

        //every MEC knows its top K neighbors
        addNearnestK_neigbors(8,this.lat,this.lon);

    }

    private void addNearnestK_neigbors(int k,double lat,double lon){
        PriorityQueue<Map.Entry<Integer,Double>> neighborsPQ=new PriorityQueue<>(new Comparator<Map.Entry<Integer, Double>>()
        {
            @Override public int compare(Map.Entry<Integer, Double> o1, Map.Entry<Integer, Double> o2)
            {
                return o1.getValue().compareTo(o2.getValue());
            }
        });
        for (String[] edge : MyConf.edgesInfo)
        {
            int nid = Integer.parseInt(edge[0]);
            double nlat = Double.parseDouble(edge[1]);
            double nlon = Double.parseDouble(edge[2]);
            double dist=MyTool.distance(lat,lon,nlat,nlon,"K");
            neighborsPQ.add(new AbstractMap.SimpleEntry<Integer,Double>(nid, dist));
        }
        for (int i=0;i<k;i++){
            Map.Entry<Integer,Double> entry=neighborsPQ.poll();
            //for now, we suppose the latency is the distence*10
            neighbors.put(entry.getKey(), new NeighborEdgeInfo(entry.getKey(),(int)(10*entry.getValue())));
        }
    }

    public int getEid() {
        return id;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    /**
     * get content from edge, if edge don't has it, edge will fetch from remote server and trigger the caching mechanism
     * may evict old ones
     * update the different type saving info
     *
     * @param timestamp
     * @param tid:if    =-1, means get without type
     * @param vid
     * @return the latency caused in this request
     */
    public int getContent(long timestamp, int tid, String vid,int homeMEC) {
        fromMEC[homeMEC-1]++;
        totalRequest++;
        //hit in this edge
        if (lruCache.get(vid) != null) {
            hitRequest++;
            fromMEC_hit[homeMEC-1]++;
            return MyConf.HIT_LATENCY;
        }

        //missed in this edge, will get from remote server
        missRequest++;
        if (checkAdmission(timestamp, tid, vid)) {
            //if pass the admission
            //use LRU now
            int preTid = lruCache.putContentGetReplacedType(vid, tid);
            if (preTid != MyConf.NOT_REPLACEMENT) {
                //minus old type info
                cacheTypeInfo.put(preTid,
                        cacheTypeInfo.getOrDefault(preTid, 0) - MyConf.FILE_SIZE);
            }

            //add new added type info
            cacheTypeInfo.put(tid,
                    MyConf.FILE_SIZE + cacheTypeInfo.getOrDefault(tid, 0));
        }

        return MyConf.MISS_LATENCY;
    }

    public int getContent(long timestamp, int tid, int vid,int homeMEC) {
        return getContent(timestamp, tid, "" + vid,homeMEC);
    }

    public HashMap getCacheTypeInfo() {
        return cacheTypeInfo;
    }

    public HashMap<Integer,NeighborEdgeInfo> getNeighbors(){
        return neighbors;
    }

    /**
     * this is the admission hook for a Cache Algorithm
     *
     * @param timestamp
     * @param tid
     * @param vid
     * @return
     */
    private boolean checkAdmission(long timestamp, int tid, String vid) {
        //use LRU now, so cache all
        return true;
    }

    /**
     * report the request statistics
     *
     * @return { totalRequest, hitRequest, missRequest }
     */
    public long[] getRequestStat() {
        return new long[]{totalRequest, hitRequest, missRequest};
    }

//    public int getContentWithoutType(long timestamp, String vid) {
//        return getContent(timestamp, MyConf.WITHOUT_TYPE, vid);
//    }

    /**
     * used to clear all the cached files in Edge
     */
    public void clear() {
        cacheTypeInfo.clear();
        lruCache.clear();

    }

    public int getAllContentSize() {
        return lruCache.size();
    }

}
