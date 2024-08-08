package nxu.it.service.impl;

import com.mysql.cj.jdbc.MysqlDataSource;
import nxu.it.model.TodoItem;
import nxu.it.service.TodoService;
import org.noear.solon.annotation.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class TodoServiceImpl implements TodoService {
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

    @Override
    public TodoItem getItemById(int id) {
        TodoItem item = null;
        String query = "SELECT t.id, t.content, t.complete, t.create_at,t.update_at,t.category_id,c.name FROM todo t left join category c on t.category_id = c.id WHERE t.id = ?";

        try (Connection conn = ds.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                item = new TodoItem();
                item.setId(rs.getInt("id"));
                item.setContent(rs.getString("content"));
                item.setComplete(rs.getInt("complete"));
                item.setCreateAt(rs.getString("create_at"));
                item.setUpdateAt(rs.getString("update_at"));
                item.setCategoryId(rs.getInt("category_id"));
                item.setName(rs.getString("name"));
            }

        } catch (SQLException e) {
            e.printStackTrace(); // 可以根据实际情况处理异常
        }

        return item;
    }

    @Override
    public void addItem(TodoItem item) {
        String insertQuery = "INSERT INTO todo (id, content, complete, create_at, update_at,uid,category_id) VALUES (?, ?, ?, now(), now(),?,?)";

        try (Connection conn = ds.getConnection();
             PreparedStatement stmt = conn.prepareStatement(insertQuery)) {

            stmt.setInt(1, item.getId());
            stmt.setString(2, item.getContent());
            stmt.setInt(3, item.getComplete());
            stmt.setInt(4, item.getUid());
            stmt.setInt(5, item.getCategoryId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace(); // 可以根据实际情况处理异常
        }
    }

    @Override
    public void updateItem(TodoItem item) {
        String updateQuery = "UPDATE todo SET content = ?, complete = ?, update_at = now(),category_id = ? WHERE id = ?";

        try (Connection conn = ds.getConnection();
             PreparedStatement stmt = conn.prepareStatement(updateQuery)) {

            stmt.setString(1, item.getContent());
            stmt.setInt(2, item.getComplete());
            stmt.setInt(3, item.getCategoryId());
            stmt.setInt(4, item.getId());


            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteItem(int id) {
        String deleteQuery = "DELETE FROM todo WHERE id = ?";

        try (Connection conn = ds.getConnection();
             PreparedStatement stmt = conn.prepareStatement(deleteQuery)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace(); // 可以根据实际情况处理异常
        }
    }
}