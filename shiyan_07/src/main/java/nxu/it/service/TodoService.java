package nxu.it.service;

import nxu.it.model.TodoItem;

import java.util.List;

public interface TodoService {
    List<TodoItem> getAllItems(int uid, int page, int pageSize);
    int countTodo(int uid);
    TodoItem getItemById(int id);
    void addItem(TodoItem item);
    void updateItem(TodoItem item);
    void deleteItem(int id);
}