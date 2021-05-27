package Remote;

import Edge.EdgeServer;
import tools.MyConf;
import tools.MyLog;
import tools.MyTool;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by JackPeng(pengjunkun@gmail.com) on 2021/1/29.
 * <p>
 * this class is used to simulate the original content provider server which contains all the requested content.
 */
public class OriginalServer
{
	private static OriginalServer originalServer;
	//<Eid,EdgeServer>
	private static HashMap<Integer, EdgeServer> edges = new HashMap<>();

	//use singleton model
	private OriginalServer()
	{
		updateEdges();
	}

	public void updateEdges()
	{
		edges.clear();
		for (String[] edge : MyConf.edgesInfo)
		{
			int id = Integer.parseInt(edge[0]);
			double lat = Double.parseDouble(edge[1]);
			double lon = Double.parseDouble(edge[2]);
			int size = Integer.parseInt(edge[3]);
			edges.put(id, new EdgeServer(id, lat, lon, size));
		}
	}

	public static OriginalServer getInstance()
	{
		if (originalServer == null)
		{
			originalServer = new OriginalServer();
		}
		return originalServer;
	}

	public int getContent(String id)
	{
		// TODO: 2021/1/29
		return 100;
	}

	public int getEdgeByDistance(double lat, double log)
	{
		//as all edges has been read above
		//calculate the nearest one
		float minDistance = Integer.MAX_VALUE;
		int eid = -1;
		for (EdgeServer edge : edges.values())
		{
			float temp = calDistance(edge, lat, log);
			if (temp < minDistance)
			{
				eid = edge.getEid();
				minDistance = temp;
			}
		}
		return eid;
	}

	private float calDistance(EdgeServer edge, double lat, double log)
	{
		//use lat and log to calculate the distance
		return (float) MyTool.distance(edge.getLat(), edge.getLon(), lat, log, "K");

	}

	public EdgeServer getEdge(int eid)
	{
		return edges.get(eid);
	}

	public long[] reportAllStat()
	{
		long totalReq = 0;
		long missReq = 0;
		long hitReq = 0;
		ArrayList<Double> edgeRatio = new ArrayList<>();

		MyLog.initTagWriter("totalStatic");

		for (EdgeServer edge : edges.values())
		{
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
			HashMap<Integer,Integer> typeMap=edge.getCacheTypeInfo();
			ArrayList<Integer> typeInfo=new ArrayList<>();
			for(int i=0;i<MyConf.typeNum;i++){
				typeInfo.add(typeMap.getOrDefault(i,0));
			}
			MyLog.tagWriter("categories:" +typeInfo.toString());
			MyLog.tagWriter("----------------");
		}
		MyLog.tagWriter("----------------total----------------");
		MyLog.tagWriter("Total request:" + totalReq);
		MyLog.tagWriter("Hit request:" + hitReq);
		double totalHitRatio = 1.0 * hitReq / totalReq;
		MyLog.tagWriter("Hit ratio:" + totalHitRatio);
		MyLog.closeTagWriter();
		return new long[] { totalReq, hitReq, missReq };
	}
}
