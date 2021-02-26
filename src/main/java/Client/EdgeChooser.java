package Client;

import Edge.EdgeServer;
import Edge.NeighborEdgeInfo;
import Remote.OriginalServer;
import tools.MyConf;

import java.util.HashMap;

/**
 * this class is a part of User. It's used to maintain the edges' info and choose a optimal edge for the user
 * <p>
 * Created by JackPeng(pengjunkun@gmail.com) on 2021/1/29.
 */
public class EdgeChooser
{
	private static EdgeChooser instance;
	private HashMap<Integer, Integer> type2edgeId_hashMap;
	private HashMap<Integer, Integer> type2capacity_hashMap;
	private int defaultEdgeId = -1;

	private EdgeChooser(double lat, double log)
	{
		type2edgeId_hashMap = new HashMap<>();
		type2capacity_hashMap = new HashMap<>();
		addDefaultChoice(lat, log);
	}

	public static EdgeChooser getInstance()
	{
		if (instance == null)
		{
			instance = new EdgeChooser(39.0, 116.0);
		}
		return instance;
	}

	private void addDefaultChoice(double lat, double lon)
	{
		int defaultEdge = OriginalServer.getInstance().getEdgeByDistance(lat, lon);
		this.defaultEdgeId = defaultEdge;
		for (int type : MyConf.types)
		{
			type2edgeId_hashMap.put(type, defaultEdge);
			//when user get default edge, he doesn't know the edge's info
			type2capacity_hashMap.put(type, 0);
		}
	}

	public void updateDefaultChoice(double lat, double lon)
	{
		type2edgeId_hashMap.clear();
		addDefaultChoice(lat, lon);
	}

	public int getEdge(int tid)
	{
		return type2edgeId_hashMap.get(tid);
	}

	public int getDefaultEdgeId()
	{
		return defaultEdgeId;
	}

	public void updateTypeInfo(int eid, HashMap<Integer, Integer> typeInfo)
	{
		//compare each capacity with current
		for (int typeId : typeInfo.keySet())
		{
			int tmp = typeInfo.get(typeId);
			if (tmp > type2capacity_hashMap.get(typeId))
			{
				type2edgeId_hashMap.put(typeId, eid);
				type2capacity_hashMap.put(typeId, tmp);
			}

		}
	}

	public void updateEdgeCandidates(HashMap<Integer, NeighborEdgeInfo> candidates)
	{
		//todo

	}

	public static int count=0;
	public void updateAllTypeInfo()
	{
		count++;
		//for now, we think every user main all the edges info
		for (String[] edge : MyConf.edgesInfo)
		{
			int eid = Integer.parseInt(edge[0]);
			HashMap typeInfo = OriginalServer.getInstance().getEdge(eid).getCacheTypeInfo();
			updateTypeInfo(eid, typeInfo);
		}

	}
}
