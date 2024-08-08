<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" href="/static/styles/base.css" />
    <script>
        document.addEventListener('DOMContentLoaded', function() {
            const form = document.querySelector('form');
            form.onsubmit = function(event) {
                event.preventDefault(); // 阻止表单默认提交行为

                const username = document.getElementById('username').value;
                const password = document.getElementById('password').value;
                const data = { username: username, password: password };

                fetch('/logincheck', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify(data),
                })
                    .then(response => response.json())
                    .then(data => {
                        console.log(data);
                        if (!data.success) {
                            // 显示错误信息节点，并设置文本内容
                            const errorMessage = document.querySelector('.error-message');
                            errorMessage.textContent = data.message;
                            errorMessage.style.display = 'block'; // 显示错误信息
                        } else {
                            // 登录成功后跳转到任务列表页
                            window.location.href = '/todo/toList';
                        }
                    })
                    .catch((error) => {
                        console.error('Error:', error);
                    });
            };
        });
    </script>
</head>
<body>
<div class="login-container">
    <h2>登录</h2>
    <form>
        <label for="username">用户名:</label>
        <input type="text" id="username" name="username" required>
        <label for="password">密码:</label>
        <input type="password" id="password" name="password" required>
        <input type="submit" value="登录">
    </form>
    <!-- 添加一个默认隐藏的节点 -->
    <div class="error-message" style="display: none; color: red;"></div>
</div>
</body>
</html>
