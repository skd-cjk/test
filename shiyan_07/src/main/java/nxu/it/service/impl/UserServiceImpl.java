package nxu.it.service.impl;

import com.mysql.cj.jdbc.MysqlDataSource;
import nxu.it.model.TodoItem;
import nxu.it.service.UserService;
import org.noear.solon.annotation.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public  class UserServiceImpl implements UserService {

    private static final String jdbcUrl = "jdbc:mysql://localhost:3306/myapp?serverTimezone=Asia/Shanghai";
    private static MysqlDataSource ds;

    // 使用静态初始化块来初始化数据源
    static {
        ds = new MysqlDataSource();
        ds.setURL(jdbcUrl);
        ds.setUser("root");
        ds.setPassword("412876");
    }

    @Override
    public int login(String username, String password) {
        String query = "SELECT id FROM user WHERE username = ? AND password = ?";
        int userId = -1; // 默认为-1，表示未找到用户或登录失败

        try (Connection conn = ds.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                userId = rs.getInt("id");
            }

        } catch (SQLException e) {
            e.printStackTrace(); // 可以根据实际情况处理异常
        }

        return userId;
    }


    @Override
    public boolean reg(String username, String password, String gender, int age, String province, String email, String hobbies) {
        String insertQuery = "INSERT INTO user (username, password, gender, age, province, email, hobbies) VALUES (?, ?, ?, ?, ?, ?, ?)";
        boolean registered = false;

        try (Connection conn = ds.getConnection();
             PreparedStatement stmt = conn.prepareStatement(insertQuery)) {

            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, gender);
            stmt.setInt(4, age);
            stmt.setString(5, province);
            stmt.setString(6, email);
            stmt.setString(7, hobbies);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                registered = true;
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Handle exceptions as per your application's requirements
        }

        return registered;
    }

    @Override
    public List<TodoItem> getAllItems(int uid, int page, int pageSize) {
        List<TodoItem> todoList = new ArrayList<>();
        int offset = (page - 1) * pageSize;

        String query = "SELECT t.id, t.content, t.complete, t.create_at,t.update_at,t.category_id,c.name FROM todo t left join category c on t.category_id = c.id WHERE t.uid = ? LIMIT ? OFFSET ?";

        try (Connection conn = ds.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, uid);
            stmt.setInt(2, pageSize);
            stmt.setInt(3, offset);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    TodoItem item = new TodoItem();
                    item.setId(rs.getInt("id"));
                    item.setContent(rs.getString("content"));
                    item.setComplete(rs.getInt("complete"));
                    item.setCreateAt(rs.getString("create_at"));
                    item.setUpdateAt(rs.getString("update_at"));
                    item.setCategoryId(rs.getInt("category_id"));
                    item.setName(rs.getString("name"));
                    todoList.add(item);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return todoList;
    }

    @Override
    public int countTodo(int uid) {
        int countNum = 0;
        try(Connection conn =  ds.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) AS todo_count FROM todo WHERE uid = ?");
        ) {
            stmt.setInt(1, uid);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()) {
                countNum = rs.getInt("todo_count");
                System.out.println("用户ID为 " + uid + " 的待办事项数量为: " + countNum);
            }

            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return countNum;
    }

}
