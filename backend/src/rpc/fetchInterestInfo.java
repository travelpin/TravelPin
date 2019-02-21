package rpc;

import entity.Interest;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/fetchinterestinfo")
public class fetchInterestInfo extends HttpServlet {
    public fetchInterestInfo(){
        super();
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String interestId = request.getParameter("interestId");
        try{

            JSONObject resJSONObject = new JSONObject();

            rpc.RpcHelper.writeJsonObject(response, resJSONObject);
        }catch(Exception e){
            e.printStackTrace();
        }finally{

        }
    }
}
