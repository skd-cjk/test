package nxu.it.service;

import nxu.it.model.TodoItem;

import java.util.List;

public interface UserService {
    List<TodoItem> getAllItems(int uid, int page, int pageSize);
    int countTodo(int uid);
    int login(String username, String password);
    boolean reg(String username, String password, String gender, int age, String province, String email, String hobbies);
}