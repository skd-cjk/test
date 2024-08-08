package nxu.it.model;

public class ApiResult {
    private int code = 200;
    private boolean success = true;
    private String message;
    private Object data; // 新增数据字段

    public ApiResult() {
    }

    public ApiResult(int code, boolean success, String message, Object data) {
        this.code = code;
        this.success = success;
        this.message = message;
        this.data = data; // 初始化数据字段
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public ApiResult setMessage(String message) {
        this.message = message;
        return this;
    }

    public Object getData() {
        return data; // 添加获取数据字段的方法
    }

    public ApiResult setData(Object data) {
        this.data = data; // 设置数据字段
        return this;
    }

    public static ApiResult ok(String message) {
        return new ApiResult(200, true, message, null);
    }

    public static ApiResult ok(int code, String message) {
        return new ApiResult(code, true, message, null);
    }

    public static ApiResult ok(String message, Object data) {
        return new ApiResult(200, true, message, data); // 新增方法以支持返回数据
    }

    public static ApiResult fail(String message) {
        return fail(400, message);
    }

    public static ApiResult fail(int code, String message) {
        return new ApiResult(code, false, message, null);
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "ApiResult{" +
                "code=" + code +
                ", success=" + success +
                ", message='" + message + '\'' +
                ", data=" + data + // 包含数据字段
                '}';
    }
}