package Client;

import Remote.OriginalServer;
import tools.MyConf;

import java.util.HashMap;

/**
 * this class is a part of User. It's used to maintain the edges' info and choose a optimal edge for the user
 *
 * Created by JackPeng(pengjunkun@gmail.com) on 2021/1/29.
 */
public class EdgeChooser
{
	private HashMap<Integer,Integer> type2edgeId_hashMap;
	private int defaultEdgeId=-1;
	public EdgeChooser(double lat,double log)
	{
		type2edgeId_hashMap=new HashMap<>();
		addDefaultChoice(lat,log);
	}

	private void addDefaultChoice(double lat,double log)
	{
		int defaultEdge= OriginalServer.getInstance().getDefaultEdge(lat,log);
		this.defaultEdgeId=defaultEdge;
		for (int type:MyConf.types)
		{
			type2edgeId_hashMap.put(type,defaultEdge);
		}
	}

	public int getEdge(int tid)
	{
		return type2edgeId_hashMap.get(tid);
	}

	public int getDefaultEdgeId() {
		return defaultEdgeId;
	}
}
