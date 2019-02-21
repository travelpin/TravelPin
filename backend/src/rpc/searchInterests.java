package rpc;

import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/searchinterests")
public class searchInterests extends HttpServlet {
    public searchInterests(){
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            String name = request.getParameter("interestName");

            JSONObject resObject = new JSONObject();
            //waiting the search function return a list of Interest

            RpcHelper.writeJsonObject(response, resObject);
        }catch (Exception e){
            e.printStackTrace();
        }finally {

        }


    }
}
