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
	private HashMap<Integer,Integer> type2edge_hashMap;
	public EdgeChooser(float lat,float log)
	{
		type2edge_hashMap=new HashMap<>();
		addDefaultChoice(lat,log);
	}

	private void addDefaultChoice(float lat,float log)
	{
		int defaultEdge= OriginalServer.getInstance().getDefaultEdge(lat,log);
		for (int type:MyConf.types)
		{
			type2edge_hashMap.put(type,defaultEdge);
		}
	}

	public int getEdge(int tid)
	{
		return type2edge_hashMap.get(tid);
	}
}
