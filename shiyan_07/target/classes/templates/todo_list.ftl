<!DOCTYPE html>
<head>
    <title>Todolist</title>
    <link rel="stylesheet" href="/static/styles/todo_list.css" />
    <script>
        var currentPage = 1
        // 创建完成按钮的点击事件处理函数
        function completeTodo(itemId) {
            var xhr = new XMLHttpRequest();
            var url = '/todo/check/' + itemId;

            xhr.open('GET', url, true);

            xhr.onload = function() {
                if (xhr.status >= 200 && xhr.status < 300) {
                    var response = xhr.responseText;
                    fetchData(currentPage)
                } else {
                    console.error('Error marking todo item as completed:', xhr.status, xhr.statusText);
                }
            };

            xhr.onerror = function() {
                console.error('Request failed');
            };
            xhr.send();
        }

        function editTodo(itemId) {
            var url = '/todo/edit/' + itemId;
            window.location.href = url;
        }

        function loginOut() {
            window.location.href = '/loginout';
        }

        function deleteTodo(itemId) {
            var xhr = new XMLHttpRequest();
            var url = '/todo/delete/' + itemId;

            xhr.open('GET', url, true);

            xhr.onload = function() {
                if (xhr.status >= 200 && xhr.status < 300) {
                    var response = xhr.responseText;
                    fetchData(currentPage)
                } else {
                    console.error('Error deleting todo item:', xhr.status, xhr.statusText);
                  
                }
            };

            xhr.onerror = function() {
                
                console.error('Request failed');
            };

            xhr.send();
        }


        function fetchData(page) {
            var xhr = new XMLHttpRequest();
            xhr.open('GET', '/todo/list?page=' + page, true);
            xhr.onload = function() {
                if (xhr.status >= 200 && xhr.status < 400) {
                    var response = JSON.parse(xhr.responseText);
                    var todoItems = response.data.todoItems;
                    var totalPages = response.data.num +1;
                    var username = response.data.username;

                    document.getElementById('username_placeholder').textContent = username;

                    // 更新待办事项列表
                    var tableBody = document.querySelector('#todo-table tbody');
                    tableBody.innerHTML = ''; // Clear current contents
                    todoItems.forEach(function(item) {
                        var row = '<tr>' +
                            '<td>' + item.id + '</td>' +
                            '<td><input type="checkbox"' + (item.complete == 1 ? ' checked' : '') + '></td>' +
                            '<td>' + (item.complete == 1 ? '<s>' + item.content + '</s>' : item.content) + '</td>' +
                            '<td>' + item.name + '</td>' +
                            '<td>' + item.createAt + '</td>' +
                            '<td>' + item.updateAt + '</td>' +
                            '<td>' +
                            ' <button onclick="completeTodo(' + item.id + ')">完成</button> ' +
                            ' <button onclick="editTodo(' + item.id + ')">修改</button> ' +
                            ' <button onclick="deleteTodo(' + item.id + ')">删除</button>'+
                            '</td>' +
                            '</tr>';
                        tableBody.insertAdjacentHTML('beforeend', row);
                    });


                    var pagination = document.querySelector('.pagination');
                    pagination.innerHTML = ''; // 清空当前内容

                    // 添加上一页
                    if (page > 1) {
                        pagination.insertAdjacentHTML('beforeend', '<a href="#" class="page-link" data-page="' + (page - 1) + '">上一页</a>');
                    }

                    // 添加当前页
                    pagination.insertAdjacentHTML('beforeend', '<a class="current-page">' + page + '</a>');

                    // 添加下一页
                    if (page < totalPages) {
                        pagination.insertAdjacentHTML('beforeend', '<a href="#" class="page-link" data-page="' + (page + 1) + '">下一页</a>');
                    }

                    currentPage = page;
                } else {
                    console.error('Error fetching data:', xhr.statusText);
                }
            };
            xhr.onerror = function() {
                console.error('Request failed.');
            };
            xhr.send();
        }

        document.addEventListener('DOMContentLoaded', function() {

            const todoForm = document.getElementById('todoForm');


            // 处理分页链接点击事件
            document.addEventListener('click', function(e) {
                if (e.target && e.target.classList.contains('page-link')) {
                    e.preventDefault();
                    var page = parseInt(e.target.getAttribute('data-page'), 5);
                    fetchData(page);
                }
            });

            fetchData(currentPage);

            todoForm.addEventListener('submit', function(event) {
                event.preventDefault(); // 阻止表单的默认提交行为

                const formData = new FormData(todoForm);
                const data = {};
                formData.forEach((value, key) => {
                    data[key] = value;
                });

                fetch('/todo/save', {
                    method: 'POST',
                    // headers: {
                    //     'Content-Type': 'application/json',
                    // },
                    body: formData,
                })
                    .then(response => response.json())
                    .then(data => {
                        if (data.code != 200) {
                            // 显示消息
                           alert(data.message)
                        } else {
                            alert(data.message)
                            fetchData(currentPage)
                        }
                    })
                    .catch(error => {
                        console.error('Error:', error);
                    });
            });




        });
    </script>
</head>
<body>
<div style="float: right">
    <h4>当前用户: <span id="username_placeholder"></span> | <button onclick="loginOut()">退出登录</button></h4>
</div>

<h1>我的待办事项</h1>
<form id="todoForm">
    待办事项：<input type="text" name="content" placeholder="请加入待办事项内容">
    <select name="categoryId">
        <option value="1">学习成长类</option>
        <option value="2">休闲娱乐类</option>
        <option value="3">生活作息类</option>
        <option value="4">家务劳动类</option>
        <option value="5">社交活动类</option>
        <option value="6">健康锻炼类</option>
    </select>
    <input type="submit" value="添加事项">
</form>

<br> <!-- 添加一个空行 -->

<table id="todo-table">
    <thead>
    <tr>
        <th>ID</th>
        <th>COMPLETE</th>
        <th>CONTENT</th>
        <th>CATEGORY</th>
        <th>CREATETIME</th>
        <th>UPDATETIME</th>
        <th>ACTIONS</th>
    </tr>
    </thead>
    <tbody>
    <!-- 待办事项内容将通过Ajax动态填充 -->
    </tbody>
</table>
<div class="pagination">
    <!-- 分页链接将通过Ajax动态填充 -->
</div>
</body>
</html>
