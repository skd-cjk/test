package nxu.it.controller;

import nxu.it.model.*;
import nxu.it.service.TodoService;
import org.noear.solon.annotation.*;
import org.noear.solon.core.handle.Context;
import org.noear.solon.core.handle.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class TodoController {

    @Inject
    TodoService todoService;

    @Mapping("/todo/toList")
    @Get
    public ModelAndView todoList() {
        return new ModelAndView("todo_list.ftl");
    }

    @Mapping("/todo/list")
    @Get
    public ApiResult list(@Param(defaultValue = "1") int page, Context ctx) {
        ModelAndView mv = new ModelAndView("todo_list.ftl");
        String username = (String) ctx.session("username");
        mv.put("username", username);
        int uid = (int) ctx.session("id");
        int pageSize = 5;
        int count = todoService.countTodo(uid);
        int num = (count - 1) / pageSize + 1;
        List<TodoItem> todoItems = todoService.getAllItems(uid, page, pageSize);
        Map<String, Object> data = new HashMap<>();
        data.put("todoItems", todoItems);
        data.put("page", 1);
        data.put("num", num-1);
        data.put("username", username);

        return ApiResult.ok("查询成功").setData(data);
    }

    @Mapping("/todo/check/{id}")
    @Get
    public ApiResult toggleComplete(@Param int id, Context ctx) {
        TodoItem item = todoService.getItemById(id);
        if (item != null) {
            item.setComplete(item.getComplete() == 1 ? 0 : 1);
            todoService.updateItem(item);
            return ApiResult.ok("操作成功");
        } else {
            return ApiResult.fail("未找到指定的Todo项");
        }
    }

    @Mapping("/todo/edit/{id}")
    @Get
    public ModelAndView edit(@Param int id) {
        ModelAndView mv = new ModelAndView();
        TodoItem item = todoService.getItemById(id);
        if (item != null) {
            mv = new ModelAndView("todo_edit.ftl");
            mv.put("todoItem", item);
        } else {
            mv = new ModelAndView("fail.ftl");
        }
        return mv;
    }

    @Mapping("/todo/update")
    @Post
    public ApiResult update(@Body TodoItem updatedItem, Context ctx) {
        TodoItem item = todoService.getItemById(updatedItem.getId());
        if (item != null) {
            todoService.updateItem(updatedItem);
            return ApiResult.ok("修改成功");
        } else {
            return ApiResult.fail("修改失败");
        }
    }

    @Mapping("/todo/save")
    @Post
    public ApiResult save(@Body TodoItem todoItem, Context ctx) {
        int uid = (int) ctx.session("id");
        todoItem.setUid(uid);
        todoService.addItem(todoItem);
        return ApiResult.ok("添加成功");
    }

    @Mapping("/todo/delete/{id}")
    @Get
    public ApiResult delete(@Param int id, Context ctx) {
        TodoItem item = todoService.getItemById(id);
        if (item != null) {
            todoService.deleteItem(id);
            return ApiResult.ok("删除成功");
        } else {
            return ApiResult.fail("删除失败，未找到对应的待办项");
        }
    }
    @Mapping("/fail")
    @Get
    public ModelAndView fail() {
        return new ModelAndView("fail.ftl");
    }
}
