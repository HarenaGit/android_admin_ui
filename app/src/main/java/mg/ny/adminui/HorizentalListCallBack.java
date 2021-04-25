package mg.ny.adminui;

@FunctionalInterface
public interface HorizentalListCallBack<T, U, V, R>{
    public R apply(T t, U u, V v);
}
