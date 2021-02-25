package Client;

import Edge.EdgeServer;
import Remote.OriginalServer;
import tools.MyLog;

import java.util.HashMap;

/**
 * Created by JackPeng(pengjunkun@gmail.com) on 2021/1/29.
 */
public class User
{
	long uid;
	double lat;
	double lon;
	EdgeChooser edgeChooser;

	public User(long uid, double lat, double lon)
	{
		this.uid = uid;
		this.lat = lat;
		this.lon = lon;
		//create a EdgeChooser for this user
		edgeChooser = new EdgeChooser(lat, lon);
	}

	public int request(long timestamp, int tid, int vid)
	{
		return request(timestamp, tid, "" + vid);
	}

	public long getUid()
	{
		return uid;
	}

	public double getLat()
	{
		return lat;
	}

	public double getLon()
	{
		return lon;
	}

	public int request(long timestamp, int tid, String vid)
	{
		//1. get the optimal edge
		int optimalEdgeId = edgeChooser.getEdge(tid);
		EdgeServer optimalEdge = OriginalServer.getInstance().getEdge(optimalEdgeId);
		//2. use this edge to request
		int latency= optimalEdge.getContent(timestamp, tid, vid);
		//3. everytime, user send a request to get video. He can get the edges' type info at the same time
		HashMap<Integer,Integer> edgeTypeInfo=optimalEdge.getCacheTypeInfo();
		//todo:update


		return latency;

	}

	public void updateLocation(double lat, double lon)
	{
		edgeChooser.updateDefaultChoice(lat,lon);
	}

	public int requestWithoutType(long timestamp, String vid)
	{
		EdgeServer edge = OriginalServer.getInstance().getEdge(edgeChooser.getDefaultEdgeId());
		return edge.getContentWithoutType(timestamp, vid);

	}
}
