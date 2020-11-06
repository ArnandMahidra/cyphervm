package cyphervm;

import static cyphervm.Bytecode.BR;
import static cyphervm.Bytecode.BRF;
import static cyphervm.Bytecode.BRT;
import static cyphervm.Bytecode.CALL;
import static cyphervm.Bytecode.GLOAD;
import static cyphervm.Bytecode.GSTORE;
import static cyphervm.Bytecode.HALT;
import static cyphervm.Bytecode.IADD;
import static cyphervm.Bytecode.ICONST;
import static cyphervm.Bytecode.IEQ;
import static cyphervm.Bytecode.ILT;
import static cyphervm.Bytecode.IMUL;
import static cyphervm.Bytecode.ISUB;
import static cyphervm.Bytecode.LOAD;
import static cyphervm.Bytecode.POP;
import static cyphervm.Bytecode.PRINT;
import static cyphervm.Bytecode.RET;
import static cyphervm.Bytecode.STORE;

// implement a stack based interpreter
public class Vm {
    public static final int DEFAULT_STACK_SIZE = 1000;
    public static final int DEFAULT_CALL_STACK_SIZE = 1000;
    public static final int FALSE = 0;
    public static final int TRUE = 1;

    // registers
    int ip;
    int sp = -1;

    // Memory
    int[] code;
    int[] globals;
    int[] stack;
    Context ctx;

    FuncMetaData[] metadata;
}
