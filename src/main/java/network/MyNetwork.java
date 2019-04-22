package network;

import java.io.*;
import java.net.URL;

public class MyNetwork {


    public String GetUrlContent(URL url){
        String content="";

        if(url.equals("")) return null;
        try {
            InputStreamReader in=new InputStreamReader(url.openStream());
            BufferedReader reader=new BufferedReader(in);

            while(reader.ready()){
                content+=reader.readLine();
            }

            if(content==""){
                return null;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return content;
    }

}
