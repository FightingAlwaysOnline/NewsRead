package tool;

import org.json.JSONArray;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class MyTool {

    private void handleJsonObject(JSONObject object){

        object.keys().forEachRemaining(key->{

            Object value=object.get(key);

            if(!(value instanceof JSONObject || value instanceof JSONArray)){
                System.out.println("KEY:"+key+"VALUES:"+value);
            }else{
                handleValue(value);
            }

        });

    }

    private void handleValue(Object value){

        if (value instanceof JSONObject){
            handleJsonObject((JSONObject) value);
        }else if(value instanceof JSONArray){
            handleJsonArray((JSONArray) value);
        }

    }

    private void handleJsonArray(JSONArray value){

        value.iterator().forEachRemaining(element->{
            handleValue(element);
                }

                );

    }

    public static String ChineseToH(String to_hash_name){
        String genstr="";
        try {
            MessageDigest md=MessageDigest.getInstance("MD5");

            md.update(to_hash_name.getBytes());

            byte[] bytes = md.digest();

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));

            }
            genstr = sb.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return genstr;
    }
}
