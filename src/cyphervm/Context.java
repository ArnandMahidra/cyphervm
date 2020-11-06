package cyphervm;

public class Context {

    Context invokingContext;
    FuncMetaData metadata;
    int returnip;
    int[] locals;

    public Context(Context invokingContext, int returnip, FuncMetaData metadata) {
        this.invokingContext = invokingContext;
        this.returnip = returnip;
        this.metadata = metadata;
        locals = new int[metadata.nargs + metadata.nlocals];
    }
}
