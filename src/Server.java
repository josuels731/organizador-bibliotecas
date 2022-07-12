import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class Server {

    Statement stmt;

    Server(Statement stmt) throws IOException {
        this.stmt = stmt;
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/api/createuser", new CreateUser());
        server.createContext("/api/createbook", new CreateBook());
        server.createContext("/api/createemprestimo", new CreateEmprestimo());
        server.createContext("/api/devolveremprestimo", new DevolverEmprestimo());
        server.createContext("/api/readbooks", new Server.ReadBooks());
        server.createContext("/api/login", new Login());

        server.setExecutor(null);
        server.start();
    }

    class CreateUser implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            t.getResponseHeaders().add("Access-Control-Allow-Origin", "*");

            if (t.getRequestMethod().equalsIgnoreCase("OPTIONS")) {
                t.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, OPTIONS");
                t.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type,Authorization");
                t.sendResponseHeaders(204, -1);
                return;
            }

            switch (t.getRequestMethod()) {
                case "POST":
                    try {
                        Map<String, String> params = queryToMap(t.getRequestURI().getQuery());
                        String sql;
                        sql = "insert into usuarios (nomeUsuario, enderecoUsuario, administrador, senhaUsuario) values ('"
                                + params.get("nome") + "', '" + params.get("endereco") + "', " + params.get("admin")
                                + ", '" + params.get("senha") + "');";
                        System.out.println(sql);
                        stmt.execute(sql);

                        String response = "Created";
                        t.sendResponseHeaders(200, response.getBytes().length);
                        OutputStream os = t.getResponseBody();
                        os.write(response.getBytes());
                        os.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                        String response = "Bad Request";
                        t.sendResponseHeaders(400, response.getBytes().length);
                        OutputStream os = t.getResponseBody();
                        os.write(response.getBytes());
                        os.close();
                        break;
                    }
                    break;
                default:
                    String response = "Method not Allowed";
                    t.sendResponseHeaders(200, response.getBytes().length);
                    OutputStream os = t.getResponseBody();
                    os.write(response.getBytes());
                    os.close();
                    break;
            }
        }

        public Map<String, String> queryToMap(String query) {
            if (query == null) {
                return null;
            }
            Map<String, String> result = new HashMap<>();
            for (String param : query.split("&")) {
                String[] entry = param.split("=");
                if (entry.length > 1) {
                    result.put(entry[0], entry[1]);
                } else {
                    result.put(entry[0], "");
                }
            }
            return result;
        }
    }

    class CreateBook implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            switch (t.getRequestMethod()) {
                case "POST":
                    try {
                        Map<String, String> params = queryToMap(t.getRequestURI().getQuery());
                        String sql;
                        sql = "insert into livros (nomeLivro, disponibilidade) values ('"
                                + params.get("nome") + "', " + params.get("disponibilidade")
                                + ");";
                        System.out.println(sql);
                        stmt.execute(sql);

                        String response = "Created";
                        t.sendResponseHeaders(200, response.getBytes().length);
                        OutputStream os = t.getResponseBody();
                        os.write(response.getBytes());
                        os.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                        String response = "Bad Request";
                        t.sendResponseHeaders(400, response.getBytes().length);
                        OutputStream os = t.getResponseBody();
                        os.write(response.getBytes());
                        os.close();
                        break;
                    }
                    break;
                default:
                    String response = "Method not Allowed";
                    t.sendResponseHeaders(200, response.getBytes().length);
                    OutputStream os = t.getResponseBody();
                    os.write(response.getBytes());
                    os.close();
                    break;
            }
        }

        public Map<String, String> queryToMap(String query) {
            if (query == null) {
                return null;
            }
            Map<String, String> result = new HashMap<>();
            for (String param : query.split("&")) {
                String[] entry = param.split("=");
                if (entry.length > 1) {
                    result.put(entry[0], entry[1]);
                } else {
                    result.put(entry[0], "");
                }
            }
            return result;
        }
    }

    class Login implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            switch (t.getRequestMethod()) {
                case "POST":
                    try {
                        Map<String, String> params = queryToMap(t.getRequestURI().getQuery());
                        String sql;
                        sql = "select nomeUsuario from usuarios where nomeUsuario = '"
                                + params.get("nome") + "' and senhaUsuario = '" + params.get("senha") + "';";
                        System.out.println(sql);
                        ResultSet rs = stmt.executeQuery(sql);
                        if (rs.next()) {
                            String response = "Authorized";
                            t.sendResponseHeaders(200, response.getBytes().length);
                            OutputStream os = t.getResponseBody();
                            os.write(response.getBytes());
                            os.close();
                        } else {
                            String response = "Unauthorized";
                            t.sendResponseHeaders(401, response.getBytes().length);
                            OutputStream os = t.getResponseBody();
                            os.write(response.getBytes());
                            os.close();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                        String response = "Bad Request";
                        t.sendResponseHeaders(400, response.getBytes().length);
                        OutputStream os = t.getResponseBody();
                        os.write(response.getBytes());
                        os.close();
                        break;
                    }
                    break;
                default:
                    String response = "Method not Allowed";
                    t.sendResponseHeaders(200, response.getBytes().length);
                    OutputStream os = t.getResponseBody();
                    os.write(response.getBytes());
                    os.close();
                    break;
            }
        }

        public Map<String, String> queryToMap(String query) {
            if (query == null) {
                return null;
            }
            Map<String, String> result = new HashMap<>();
            for (String param : query.split("&")) {
                String[] entry = param.split("=");
                if (entry.length > 1) {
                    result.put(entry[0], entry[1]);
                } else {
                    result.put(entry[0], "");
                }
            }
            return result;
        }
    }

    class ReadBooks implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            switch (t.getRequestMethod()) {
                case "GET":
                    try {
                        Map<String, String> params = queryToMap(t.getRequestURI().getQuery());
                        String sql;
                        sql = "select * from livros where nomeLivro = '"
                                + params.get("nome") + "';";

                        ResultSet rs = stmt.executeQuery(sql);
                        String response = "{items: [";
                        while (rs.next()) {
                            response += "\"id\": " + rs.getInt("cdLivro") + ", \"nome\": \"" + rs.getString("nomeLivro")
                                    + "\", \"disponibilidade\": \"" + rs.getBoolean("disponibilidade") + "\""
                                    + (rs.isLast() ? "]}" : ",");
                        }
                        t.getResponseHeaders().add("Content-Type", "text/html");
                        t.sendResponseHeaders(200, response.getBytes().length);
                        OutputStream os = t.getResponseBody();
                        os.write(response.getBytes());
                        os.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                        String response = "Bad Request";
                        t.sendResponseHeaders(400, response.getBytes().length);
                        OutputStream os = t.getResponseBody();
                        os.write(response.getBytes());
                        os.close();
                        break;
                    }
                    break;
                default:
                    String response = "Method not Allowed";
                    t.sendResponseHeaders(200, response.getBytes().length);
                    OutputStream os = t.getResponseBody();
                    os.write(response.getBytes());
                    os.close();
                    break;
            }
        }

        public Map<String, String> queryToMap(String query) {
            if (query == null) {
                return null;
            }
            Map<String, String> result = new HashMap<>();
            for (String param : query.split("&")) {
                String[] entry = param.split("=");
                if (entry.length > 1) {
                    result.put(entry[0], entry[1]);
                } else {
                    result.put(entry[0], "");
                }
            }
            return result;
        }
    }

    class CreateEmprestimo implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            switch (t.getRequestMethod()) {
                case "POST":
                    try {
                        Map<String, String> params = queryToMap(t.getRequestURI().getQuery());
                        String sql;
                        Date now = new Date();
                        SimpleDateFormat formatador = new SimpleDateFormat("yyyy-MM-dd");
                        sql = "insert into emprestimos (dataInicial, dataFinalLimite, cdUsuario, cdLivro) values ('"
                                + formatador.format(now) + "', '"
                                + formatador.format(new Date(now.getTime() + 1000 * 60 * 60 * 24 * 15)) + "', "
                                + params.get("idUsuario") + ", " + params.get("idLivro")
                                + ");";
                        System.out.println(sql);
                        stmt.execute(sql);

                        sql = "update livros set disponibilidade = 0 where cdLivro = " + params.get("idLivro") + ";";
                        stmt.execute(sql);

                        String response = "Created";
                        t.sendResponseHeaders(200, response.getBytes().length);
                        OutputStream os = t.getResponseBody();
                        os.write(response.getBytes());
                        os.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                        String response = "Bad Request";
                        t.sendResponseHeaders(400, response.getBytes().length);
                        OutputStream os = t.getResponseBody();
                        os.write(response.getBytes());
                        os.close();
                        break;
                    }
                    break;
                default:
                    String response = "Method not Allowed";
                    t.sendResponseHeaders(200, response.getBytes().length);
                    OutputStream os = t.getResponseBody();
                    os.write(response.getBytes());
                    os.close();
                    break;
            }
        }

        public Map<String, String> queryToMap(String query) {
            if (query == null) {
                return null;
            }
            Map<String, String> result = new HashMap<>();
            for (String param : query.split("&")) {
                String[] entry = param.split("=");
                if (entry.length > 1) {
                    result.put(entry[0], entry[1]);
                } else {
                    result.put(entry[0], "");
                }
            }
            return result;
        }
    }

    class DevolverEmprestimo implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            switch (t.getRequestMethod()) {
                case "PATCH":
                    try {
                        Map<String, String> params = queryToMap(t.getRequestURI().getQuery());
                        String sql;
                        Date now = new Date();
                        SimpleDateFormat formatador = new SimpleDateFormat("yyyy-MM-dd");
                        sql = "update emprestimos set dataEntrega = '" + formatador.format(now) + "' where cdLivro = "
                                + params.get("idLivro") + " and cdUsuario = " + params.get("idUsuario") + ";";
                        System.out.println(sql);
                        stmt.execute(sql);

                        sql = "update livros set disponibilidade = 1 where cdLivro = " + params.get("idLivro") + ";";
                        stmt.execute(sql);

                        String response = "Created";
                        t.sendResponseHeaders(200, response.getBytes().length);
                        OutputStream os = t.getResponseBody();
                        os.write(response.getBytes());
                        os.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                        String response = "Bad Request";
                        t.sendResponseHeaders(400, response.getBytes().length);
                        OutputStream os = t.getResponseBody();
                        os.write(response.getBytes());
                        os.close();
                        break;
                    }
                    break;
                default:
                    String response = "Method not Allowed";
                    t.sendResponseHeaders(200, response.getBytes().length);
                    OutputStream os = t.getResponseBody();
                    os.write(response.getBytes());
                    os.close();
                    break;
            }
        }

        public Map<String, String> queryToMap(String query) {
            if (query == null) {
                return null;
            }
            Map<String, String> result = new HashMap<>();
            for (String param : query.split("&")) {
                String[] entry = param.split("=");
                if (entry.length > 1) {
                    result.put(entry[0], entry[1]);
                } else {
                    result.put(entry[0], "");
                }
            }
            return result;
        }
    }

}