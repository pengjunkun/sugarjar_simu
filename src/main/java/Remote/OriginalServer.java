package Remote;

import Edge.EdgeServer;
import tools.MyConf;

import java.util.ArrayList;

/**
 * Created by JackPeng(pengjunkun@gmail.com) on 2021/1/29.
 * <p>
 * this class is used to simulate the original content provider server which contains all the requested content.
 */
public class OriginalServer
{
	private static OriginalServer originalServer;
	private static ArrayList<EdgeServer> edges;

	//use singleton model
	private OriginalServer()
	{
		edges = new ArrayList<>();
		for (String[] edge : MyConf.edgesInfo)
		{
			int id = Integer.parseInt(edge[0]);
			float lat = Float.parseFloat(edge[1]);
			float lon = Float.parseFloat(edge[2]);
			int size = Integer.parseInt(edge[3]);
			edges.add(new EdgeServer(id, lat, lon, size));
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

	public int getDefaultEdge(float lat, float log)
	{
		//TODO:
		return 0;
	}

	public EdgeServer getEdge(int eid)
	{
		return edges.get(eid);
	}
}
