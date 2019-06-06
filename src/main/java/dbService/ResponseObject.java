package dbService;

import dbService.entity.Entity;

public class ResponseObject<T extends Entity> {

    private T ob;
    private int code;

    public ResponseObject(T ob, int code) {
        this.ob = ob;
        this.code = code;
    }

    public T getOb() {
        return ob;
    }

    public int getCode() {
        return code;
    }

}
