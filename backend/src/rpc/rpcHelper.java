package rpc;

import javax.servlet.http.HttpServletResponse;

public class RpcHelper {
    //write a JSONArray to http response
    public static void writeJsonArray(HttpServletResponse response, JSONArray array)
            throws IOException{
        response.setContentType("application/json");
        response.setHeader("Access-Control-AllowOrigin", "*");
        PrintWriter out = response.getWriter();
        out.print(array);
        out.close();
    }

    //write a jsonobject to http response
    public static void writeJsonObject(HttpServletResponse response, JSONObject obj)
            throws IOException{
        response.setContentType("application/json");
        response.setHeader("Access-Control-Allow-Origin", "*");
        PrintWriter out = response.getWriter();
        out.print(obj);
        out.close();
    }

    // Parses a JSONObject from http request.
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
