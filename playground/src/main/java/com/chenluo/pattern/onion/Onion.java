package com.chenluo.pattern.onion;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Onion<T> {

    private Middleware<T> core = (ctx, nxt) -> nxt.next();

    public final void use(Middleware<T> middleware) {
        Objects.requireNonNull(middleware, "Middleware must be not null");
        this.core = compose(this.core, middleware);
    }

    public void handle(T context) throws Exception {

        this.core.via(context, () -> {
        });
    }

    public interface Middleware<T> {

        void via(T context,  Next next) throws Exception;
    }

    public interface Next {

        void next() throws Exception;
    }

    @SafeVarargs
    public static <U> Middleware<U> compose(Middleware<U>... middlewares) {
        return Arrays.stream(middlewares).reduce((ctx, nxt) -> nxt.next(), (before, after) -> (ctx, nxt) -> before.via(ctx, () -> after.via(ctx, nxt)));
    }

    public static void main(String[] args) {
        Middleware middleware1 = new Middleware() {
            @Override
            public void via(Object context, Next next) throws Exception {
                System.out.println("middleware 1: enter");
                next.next();
                System.out.println("middleware 1: enter");
            }
        };
        Middleware middleware2 = new Middleware() {
            @Override
            public void via(Object context, Next next) throws Exception {
                System.out.println("middleware 2: enter");
                next.next();
                System.out.println("middleware 2: enter");
            }
        };
        Middleware middleware3 = new Middleware() {
            @Override
            public void via(Object context, Next next) throws Exception {
                System.out.println("middleware 3: enter");
                next.next();
                System.out.println("middleware 3: enter");
            }
        };
        Middleware middleware4 = new Middleware() {
            @Override
            public void via(Object context, Next next) throws Exception {
                System.out.println("middleware 4: enter");
                next.next();
                System.out.println("middleware 4: enter");
            }
        };
        List<Middleware> middlewareList = new ArrayList<>();
        middlewareList.add(middleware1);
        middlewareList.add(middleware2);
        middlewareList.add(middleware3);
        middlewareList.add(middleware4);
        Onion<Object> objectOnion = new Onion<>();
        compose(middleware1, middleware2, middleware3, middleware4);


    }
}
