package jpabook.jpashop.exception;

public class NotEnoughStockException extends RuntimeException{
 // alt + insert 매소드 재정의 (Override Method) > RuntimeException 하위 모두 선택

    public NotEnoughStockException() {
        super();
    }

    public NotEnoughStockException(String message) {
        super(message);
    }

    public NotEnoughStockException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotEnoughStockException(Throwable cause) {
        super(cause);
    }

// 필요 없어서 주석 처리
//    protected NotEnoughStockException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
//        super(message, cause, enableSuppression, writableStackTrace);
//    }
}
