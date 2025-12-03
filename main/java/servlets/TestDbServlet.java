// src/main/java/servlets/TestDbServlet.java
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import dao.TestNoteDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/testdb")
public class TestDbServlet extends HttpServlet {

    private final TestNoteDao dao = new TestNoteDao();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String body = req.getParameter("body");
        if (body != null && !body.isBlank()) {
            try {
                dao.insert(body);
            } catch (SQLException e) {
                throw new ServletException(e);
            }
        }
        // POST 後は GET にリダイレクト（F5 で二重送信防止）
        resp.sendRedirect(req.getContextPath() + "/testdb");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("text/html; charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");

        List<String> notes;
        try {
            notes = dao.findAll();
        } catch (SQLException e) {
            throw new ServletException(e);
        }

        try (PrintWriter out = resp.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html lang='ja'><head><meta charset='UTF-8'>");
            out.println("<title>Renderテスト - DB接続確認</title></head><body>");
            out.println("<h1>Renderテスト用 DB接続ページ</h1>");

            out.println("<h2>メモ追加</h2>");
            out.println("<form method='post'>");
            out.println("<input type='text' name='body' size='40' />");
            out.println("<button type='submit'>追加</button>");
            out.println("</form>");

            out.println("<h2>登録済みメモ</h2>");
            out.println("<ul>");
            for (String s : notes) {
                out.println("<li>" + escapeHtml(s) + "</li>");
            }
            out.println("</ul>");

            out.println("</body></html>");
        }
    }

    // 簡易エスケープ（ちゃんとやるなら Apache Commons Text とか）
    private String escapeHtml(String s) {
        return s == null ? "" :
                s.replace("&", "&amp;")
                 .replace("<", "&lt;")
                 .replace(">", "&gt;")
                 .replace("\"", "&quot;");
    }
}
