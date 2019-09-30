package app.message;

public class BaseResponse<T> {
    int code = 0;
    String message = "OK";
    T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

	public BaseResponse() {
		super();
	}

	public BaseResponse(int code, String message, T data) {
		super();
		this.code = code;
		this.message = message;
		this.data = data;
	}
    
    
}
