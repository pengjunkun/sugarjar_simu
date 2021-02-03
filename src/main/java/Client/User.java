package Client;

import Remote.OriginalServer;
import tools.MyLog;

/**
 * Created by JackPeng(pengjunkun@gmail.com) on 2021/1/29.
 */
public class User {
    long uid;
    double lat;
    double lon;
    EdgeChooser edgeChooser;

    public User(long uid, double lat, double lon) {
        this.uid = uid;
        this.lat = lat;
        this.lon = lon;
        //create a EdgeChooser for this user
        edgeChooser = new EdgeChooser(lat, lon);
    }

    public int request(long timestamp, int tid, int vid) {
        return request(timestamp, tid, "" + vid);
    }

    public long getUid() {
        return uid;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public int request(long timestamp, int tid, String vid) {
        //1. get the optimal edge
        int optimalEdge = edgeChooser.getEdge(tid);
        //2. use this edge to request
        return OriginalServer.getInstance().getEdge(optimalEdge)
                .getContent(timestamp, tid, vid);

    }

    public void updateLocation(double lat, double lon) {
        //TODO:
    }

    public int requestWithoutType(long timestamp, String vid) {
        return OriginalServer.getInstance().getEdge(edgeChooser.getDefaultEdgeId()).getContentWithoutType(timestamp,vid);

    }
}
