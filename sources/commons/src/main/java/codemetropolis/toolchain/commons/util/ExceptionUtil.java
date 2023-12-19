package codemetropolis.toolchain.commons.util;

public class ExceptionUtil {
    @FunctionalInterface
    public interface FunctionWithException<TIn, TOut, TExc extends Exception> {
        TOut apply(TIn in) throws TExc;
    }

    @FunctionalInterface
    public interface ActionWithException<TOut, TExc extends Exception> {
        TOut apply() throws TExc;
    }

    public static <TIn, TOut, TExc extends Exception> TOut tryUnchecked(TIn in, FunctionWithException<TIn, TOut, TExc> function) {
        try {
            return function.apply(in);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <TOut, TExc extends Exception> TOut tryUnchecked(ActionWithException<TOut, TExc> function) {
        try {
            return function.apply();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
