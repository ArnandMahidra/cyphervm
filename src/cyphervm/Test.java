package cyphervm;

import static cyphervm.Bytecode.BR;
import static cyphervm.Bytecode.BRF;
import static cyphervm.Bytecode.CALL;
import static cyphervm.Bytecode.GLOAD;
import static cyphervm.Bytecode.GSTORE;
import static cyphervm.Bytecode.HALT;
import static cyphervm.Bytecode.IADD;
import static cyphervm.Bytecode.ICONST;
import static cyphervm.Bytecode.ILT;
import static cyphervm.Bytecode.IMUL;
import static cyphervm.Bytecode.ISUB;
import static cyphervm.Bytecode.LOAD;
import static cyphervm.Bytecode.PRINT;
import static cyphervm.Bytecode.RET;
import static cyphervm.Bytecode.STORE;

public class Test {
    static int[] hello = { ICONST, 1, ICONST, 2, IADD, PRINT, HALT };

    static int[] loop = { ICONST, 10, GSTORE, 0, ICONST, 0, GSTORE, 1, GLOAD, 1, GLOAD, 0, ILT, BRF, 24, GLOAD, 1,
            ICONST, 1, IADD, GSTORE, 1, BR, 8, HALT };


    static FuncMetaData[] loop_metadata = { new FuncMetaData("main", 0, 0, 0) };

    
}
