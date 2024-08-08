<!DOCTYPE html>
<html>
<head>
    <title>Register</title>
    <link rel="stylesheet" href="/static/styles/base.css" />
</head>
<body>
<div class="register-container">
    <h2>注册</h2>
    <form id="registerForm">
        用户名：<input type="text" name="username" required><br>
        密码：<input type="password" name="password" required><br>
        性别：
        <div class="checkbox-wrapper">
            <input type="radio" name="gender" value="男" required>男
        </div>
        <div class="checkbox-wrapper">
            <input type="radio" name="gender" value="女" required>女
        </div>
        <br>
        年龄：<input type="number" name="age" required><br>
        籍贯：<select name="province" required>
            <!-- 循环输出省份选项 -->
            <#list provinces as province>
                <option value="${province}">${province}</option>
            </#list>
        </select><br>
        邮箱：<input type="email" name="email" required><br>
        爱好：
        <#list hobbies as hobby>
        <!-- 循环输出爱好复选框 -->
        <div class="checkbox-wrapper">
            <input type="checkbox" name="hobbies" value="${hobby}">${hobby}
        </div>
        </#list><br>
        <input type="submit" value="注册">
    </form>
    <#if message??>
        <p id="message">${message}</p>
    </#if>
</div>

<script>
    document.addEventListener('DOMContentLoaded', function() {
        const registerForm = document.getElementById('registerForm');
        registerForm.addEventListener('submit', function(event) {
            event.preventDefault(); // 阻止表单的默认提交行为

            const formData = new FormData(registerForm);
            const data = {};
            formData.forEach((value, key) => {
                data[key] = value;
            });

            fetch('/doRegister', {
                method: 'POST',

                body: formData,
            })
                .then(response => response.json())
                .then(data => {
                    if (data.code == 200) {
                        // 注册成功，可以显示消息或重定向
                        alert("注册成功")
                        window.location.href = "/login";
                    } else {
                        // 注册失败，显示错误消息
                        alert("注册失败")
                    }
                })
                .catch(error => {
                    console.error('Error:', error);
                });
        });
    });
</script>
</body>
</html>
