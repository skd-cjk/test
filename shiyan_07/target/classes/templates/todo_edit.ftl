<!DOCTYPE html>
<html>
<head>
    <title>Edit TODO Item</title>
    <link rel="stylesheet" href="/static/styles/base.css" />
    <script>
        document.addEventListener('DOMContentLoaded', function() {
            const form = document.querySelector('.edit-container form');
            form.onsubmit = function(event) {
                event.preventDefault(); // 阻止表单的默认提交行为

                const formData = new FormData(form);
                const data = {};
                formData.forEach((value, key) => {
                    // 特殊处理复选框
                    if (key === 'complete' && !value) {
                        data[key] = '0';
                    } else {
                        data[key] = value;
                    }
                });

                fetch('/todo/update', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify(data),
                })
                    .then(response => response.json())
                    .then(data => {
                        console.log("dafadfad,",data)
                        if (data.code != 200) {
                            // 显示错误信息节点，并设置文本内容
                            const errorMessage = document.querySelector('.error-message');
                            errorMessage.textContent = data.message;
                            errorMessage.style.display = 'block'; // 显示错误信息
                        } else {
                            // 修改成功，重定向到待办事项列表
                            alert(data.message)
                            window.location.href = '/todo/toList';
                        }
                    })
                    .catch(error => {
                        console.error('Error:', error);
                    });
            };
        });
    </script>
</head>
<body>
<div class="edit-container">
    <h1>修改待办事项</h1>
    <form>
        <input type="hidden" name="id" value="${todoItem.id}">
        <label>待办内容:</label>
        <input type="text" name="content" value="${todoItem.content}">
        <br>
        <label>待办类型:</label>
        <select name="categoryId">
            <option value="1" <#if todoItem.categoryId == 1>selected</#if>>学习成长类</option>
            <option value="2" <#if todoItem.categoryId == 2>selected</#if>>休闲娱乐类</option>
            <option value="3" <#if todoItem.categoryId == 3>selected</#if>>生活作息类</option>
            <option value="4" <#if todoItem.categoryId == 4>selected</#if>>家务劳动类</option>
            <option value="5" <#if todoItem.categoryId == 5>selected</#if>>社交活动类</option>
            <option value="6" <#if todoItem.categoryId == 6>selected</#if>>健康锻炼类</option>
        </select>
        <br>
        <label>是否完成:</label>
        <div class="checkbox-wrapper">
            <input type="checkbox" name="complete" value="1" <#if todoItem.complete  == 1>checked</#if>>
        </div>
        <br>
        <div class="error-message" style="display: none; color: red;"></div>
        <input type="submit" value="保存">
    </form>
</div>
</body>
</html>

