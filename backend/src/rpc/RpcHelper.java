package rpc;

import db.DBConnection;
import db.DBConnectionFactory;
import entity.Interest;
import entity.Interest.InterestBuilder;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class RpcHelper {
    public static void writeJsonArray(HttpServletResponse response, JSONArray array)throws IOException {
        response.setContentType("application/json");
        response.setHeader("Access-Control-Allow-Origin", "*");
        PrintWriter out = response.getWriter();
        out.print(array);
        out.close();
    }
    public static void writeJsonObject(HttpServletResponse response, JSONObject obj)throws IOException{
        response.setContentType("application/json");
        response.setHeader("Access-Control-Allow-Origin", "*");
        PrintWriter out = response.getWriter();
        out.print(obj);
        out.close();
    }

    public static JSONObject readJSONObject(HttpServletRequest request) {
        StringBuilder sBuilder = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line = null;
            while((line = reader.readLine()) != null) {
                sBuilder.append(line);
            }
            return new JSONObject(sBuilder.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new JSONObject();
    }
}
