package nxu.it;

import freemarker.cache.NullCacheStorage;
import io.undertow.server.session.Session;
import org.noear.solon.Solon;
import org.noear.solon.annotation.SolonMain;

import static org.noear.solon.core.handle.Endpoint.before;

@SolonMain
public class App {
    public static void main(String[] args) {
        Solon.start(App.class, args, app->{
            app.onEvent(freemarker.template.Configuration.class, e->{
               e.setCacheStorage(new NullCacheStorage());
            });
            app.before("/todo/*", c -> {
//                String str = c.cookie("username");
                String username = (String) c.session("username");
                if (username == null) {
                    c.redirect("/login");
                    c.setHandled(true);
                }
            });

            // 记录日志
            app.before("/todo/*", c -> {
                System.out.println("记录一下拦截");
            });

            // 请求处理完后记录时间耗费
            app.after("/todo/*", c -> {
                System.out.println("拦截成功");
            });
        });
    }
}