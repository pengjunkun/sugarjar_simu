import Client.EdgeChooser;
import Client.User;
import Remote.OriginalServer;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class SendController
{
	HashMap<Long, User> userHashMap = new HashMap<>();

	public void readFileAndSent(String requestFile, String typeInfoFile)
	{
		//read in the corresponding type info
		BufferedReader typeReader;
		HashMap<String, Integer> typeMapping = new HashMap<>();

		try
		{
			typeReader = new BufferedReader(new FileReader(typeInfoFile));
			String oneLine = typeReader.readLine();
			while (oneLine != null)
			{
				if (oneLine.length() == 0)
				{
					oneLine = typeReader.readLine();
					continue;
				}
				String[] tmp = oneLine.split(",");
				typeMapping.put(tmp[0], Integer.parseInt(tmp[1]));

				oneLine = typeReader.readLine();
			}

		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		BufferedReader bufferedReader;
		try
		{
			bufferedReader = new BufferedReader(new FileReader(requestFile));
			String oneLine = bufferedReader.readLine();
			//check the first line
			if (oneLine.startsWith("user"))
			{
				oneLine = bufferedReader.readLine();
			}
			while (oneLine != null)
			{

				if (oneLine.length() == 0)
				{
					oneLine = bufferedReader.readLine();
					continue;
				}
				String[] tmp = oneLine.split(",");
				long uid = Long.parseLong(tmp[0]);
				long timestamp = Long.parseLong(tmp[1]);
				double lon = Double.parseDouble(tmp[2]);
				double lat = Double.parseDouble(tmp[3]);
				//Todo: get the type in future
				int tid = 0;
				String vid = oneLine.split(",")[4];

				//1.find the user
				User user = userHashMap.get(uid);
				if (user == null)
				{
					user = new User(uid, lat, lon);
					userHashMap.put(uid, user);
				} else
					//caution: user may change its requesting location
					//2.check if this user's location has changed
					if (Math.abs(user.getLat() - lat) > 0.1 || Math.abs(user.getLon() - lon) > 0.1)
					{
						user.updateLocation(lat, lon);
					}

				//3.send request
//								user.requestWithoutType(timestamp, vid);
				user.request(timestamp, typeMapping.get(vid), vid);
				//try with random type
//				user.request(timestamp, (int) (Long.parseLong(vid)%200), vid);

				oneLine = bufferedReader.readLine();
			}

		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		OriginalServer.getInstance().reportAllStat();

		System.out.println("all updated:"+ EdgeChooser.count+"times!");

	}
}
