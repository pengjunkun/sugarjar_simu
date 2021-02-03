package Remote;

import Edge.EdgeServer;
import tools.MyConf;
import tools.MyLog;
import tools.MyTool;

import java.util.ArrayList;

/**
 * Created by JackPeng(pengjunkun@gmail.com) on 2021/1/29.
 * <p>
 * this class is used to simulate the original content provider server which contains all the requested content.
 */
public class OriginalServer {
    private static OriginalServer originalServer;
    private static ArrayList<EdgeServer> edges = new ArrayList<>();

    //use singleton model
    private OriginalServer() {
        updateEdges();
    }

    public void updateEdges() {
        edges.clear();
        for (String[] edge : MyConf.edgesInfo) {
            int id = Integer.parseInt(edge[0]);
            float lat = Float.parseFloat(edge[1]);
            float lon = Float.parseFloat(edge[2]);
            int size = Integer.parseInt(edge[3]);
            edges.add(new EdgeServer(id, lat, lon, size));
        }
    }

    public static OriginalServer getInstance() {
        if (originalServer == null) {
            originalServer = new OriginalServer();
        }
        return originalServer;
    }

    public int getContent(String id) {
        // TODO: 2021/1/29
        return 100;
    }

    public int getDefaultEdge(float lat, float log) {
        //as all edges has been read above
        //calculate the nearest one
        float minDistance = Integer.MAX_VALUE;
        int eid = -1;
        for (EdgeServer edge : edges) {
            float temp = calDistance(edge, lat, log);
            if (temp < minDistance) {
                eid = edge.getEid();
                minDistance = temp;
            }
        }
        return eid;
    }

    private float calDistance(EdgeServer edge, float lat, float log) {
        //use lat and log to calculate the distance
        return (float) MyTool.distance(edge.getLat(), edge.getLon(), lat, log, "K");

    }

    public EdgeServer getEdge(int eid) {
        return edges.get(eid);
    }

    public long[] reportAllStat() {
        long totalReq = 0;
        long missReq = 0;
        long hitReq = 0;
        ArrayList<Double> edgeRatio = new ArrayList<>();

        MyLog.initTagWriter("totalStatic");

        for (EdgeServer edge : edges) {
//            return new long[] { totalRequest, hitRequest, missRequest };
            long[] tmp = edge.getRequestStat();
            double ratio = 0;
            if (tmp[0] != 0)
                ratio = 1.0 * tmp[1] / tmp[0];
            totalReq += tmp[0];
            hitReq += tmp[1];
            missReq += tmp[2];
            edgeRatio.add(ratio);
            MyLog.tagWriter("Eid:" + edge.getEid());
            MyLog.tagWriter("Total request:" + tmp[0]);
            MyLog.tagWriter("Hit request:" + tmp[1]);
            MyLog.tagWriter("Hit ratio:" + ratio);
            MyLog.tagWriter("----------------");
        }
        MyLog.tagWriter("----------------total----------------");
        MyLog.tagWriter("Total request:" + totalReq);
        MyLog.tagWriter("Hit request:" + hitReq);
        MyLog.closeTagWriter();
        return new long[]{totalReq, hitReq, missReq};
    }
}
