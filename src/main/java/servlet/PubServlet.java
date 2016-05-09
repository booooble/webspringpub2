package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import springinthepub.Pub;

@WebServlet("pubservlet")
public class PubServlet  extends HttpServlet {
	
	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Pub pub = new Pub();

        if (request.getParameter("add") != null) {
            pub.addRandomVisitorToTheQueue();
/*        } else if (request.getParameter("button2") != null) {
            pub.method2();
        } else if (request.getParameter("button3") != null) {
            pub.method3();*/
        } else {
            // ???
        	System.out.println("Ooooops!!!");
        }

        request.getRequestDispatcher("/WEB-INF/pages/pub.jsp").forward(request, response);
    }

}