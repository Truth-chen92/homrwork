package cn.tedu;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet(name = "RegServlet", urlPatterns = "/reg")
public class RegServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        try (Connection conn = DBUtils.getConn()) {
            PreparedStatement ps= conn.prepareStatement("select username from user where username=?");
            ps.setString(1,username);
            ResultSet rs=ps.executeQuery();
            String info=null;
            if(rs.next()){
                info="用户已存在!";
            }else{
                info="注册成功!";
                PreparedStatement add=conn.prepareStatement("insert into user value(null,?,?)");
                add.setString(1,username);
                add.setString(2,password);
                add.executeUpdate();
            }
            response.setContentType("text/html;charset=utf-8");
            PrintWriter pw=response.getWriter();
            pw.println(info);
            pw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
