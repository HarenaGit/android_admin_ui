package mg.ny.adminui;

@FunctionalInterface
public interface HorizentalListCallBack<T, U, R>{
    public R apply(T t, U u);
}
