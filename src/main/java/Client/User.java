package Client;

import Remote.OriginalServer;
import tools.MyLog;

/**
 * Created by JackPeng(pengjunkun@gmail.com) on 2021/1/29.
 */
public class User
{
	EdgeChooser edgeChooser;

	public User(int id, float lat, float lon)
	{
		//create a EdgeChooser for this user
		edgeChooser = new EdgeChooser(lat, lon);
	}

	public int request(long timestamp, int tid, long vid)
	{
		//1. get the optimal edge
		int optimalEdge = edgeChooser.getEdge(tid);
		//2. use this edge to request
		return OriginalServer.getInstance().getEdge(optimalEdge)
				.getContent(timestamp, tid, vid);

	}
}
