package nxu.it.controller;



import nxu.it.model.ApiResult;
import nxu.it.model.TodoItem;
import nxu.it.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.noear.solon.annotation.*;
import org.noear.solon.core.handle.Context;
import org.noear.solon.core.handle.ModelAndView;


import java.util.*;


@Controller
public class UserController {

    @Inject
    UserService userService;

    @Mapping("/login")
    @Get
    public ModelAndView login() {
        ModelAndView mv = new ModelAndView("login.ftl");
        return mv;
    }

    @Mapping("/loginout")
    @Get
    public ModelAndView loginout(Context ctx) {
        ctx.sessionRemove("username");
        ctx.sessionRemove("id");
        ModelAndView mv = new ModelAndView("login.ftl");
        return mv;
    }

    @Mapping("/logincheck")
    @Post
    public ApiResult logincheck(@Param String username, @Param String password, Context ctx) {
        String s = DigestUtils.md5Hex(password.getBytes());
        int id = userService.login(username, s);
        int size = 5;

        if (id > 0) {
            ctx.sessionSet("id", id);
            ctx.sessionSet("username", username);

            List<TodoItem> todoItems = userService.getAllItems(id, 1, size);
            int count = userService.countTodo(id);
            int num = (count - 1) / size + 1;


            Map<String, Object> data = new HashMap<>();
            data.put("todoItems", todoItems);
            data.put("page", 1);
            data.put("num", num-1);
            data.put("username", username);

            return ApiResult.ok("登录成功").setData(data);
        } else {
            return ApiResult.fail("登录失败");
        }
    }

    @Mapping("/register")
    @Get
    public ModelAndView registerFtl(Context ctx) {
        List<String> list = Arrays.asList("宁夏", "陕西", "甘肃", "其他");
        List<String> hobbies = Arrays.asList("唱歌", "跳舞", "篮球", "说唱");
        Map<String, Object> model = new HashMap<>();
        model.put("hobbies", hobbies);
        model.put("provinces", list);
        return new ModelAndView("reg.ftl", model);
    }

    @Mapping("/doRegister")
    @Post
    public ApiResult doRegister(
            @Param String username,
            @Param String password,
            @Param String gender,
            @Param int age,
            @Param String province,
            @Param String email,
            @Param List<String> hobbies,
            Context ctx) {
        String str = "";
        for (String s : hobbies) {
            str += s + " ";
        }
        String s = DigestUtils.md5Hex(password.getBytes());
        if (userService.reg(username, s, gender, age, province, email, str)) {
            return ApiResult.ok("注册成功");
        } else {
            return ApiResult.fail("注册失败");
        }
    }
}
