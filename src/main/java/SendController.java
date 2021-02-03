import Client.User;
import Remote.OriginalServer;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class SendController {
    HashMap<Long, User> userHashMap = new HashMap<>();

    public void readFileAndSent(String fPath) {
        BufferedReader bufferedReader;
        try {
            bufferedReader = new BufferedReader(new FileReader(fPath));
            String oneLine = bufferedReader.readLine();
            //check the first line
            if (oneLine.startsWith("user")) {
                oneLine = bufferedReader.readLine();
            }
            while (oneLine != null) {
                String[] tmp = oneLine.split(",");
                long uid = Long.parseLong(tmp[0]);
                long timestamp = Long.parseLong(tmp[1]);
                double lon = Double.parseDouble(tmp[2]);
                double lat = Double.parseDouble(tmp[3]);
                //Todo: get the type in future
                int tid=0;
                String vid = oneLine.split(",")[4];

                //1.find the user
                User user = userHashMap.get(uid);
                if (user == null) {
                    user = new User(uid, lat, lon);
                } else
                    //caution: user may change its requesting location
                    //2.check if this user's location has changed
                    if (Math.abs(user.getLat() - lat) > 0.1 || Math.abs(user.getLon() - lon) > 0.1) {
                        user.updateLocation(lat, lon);
                    }

                //3.send request
                user.requestWithoutType(timestamp,vid);

                oneLine = bufferedReader.readLine();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        OriginalServer.getInstance().reportAllStat();

    }
}
