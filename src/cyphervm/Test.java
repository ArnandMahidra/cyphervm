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

    static int FACTORIAL_INDEX = 1;
    static int FACTORIAL_ADDRESS = 0;
    static int MAIN_ADDRESS = 21;

    static int[] factorial = { LOAD, 0, ICONST, 2, ILT, BRF, 10, ICONST, 1, RET, LOAD, 0, LOAD, 0, ICONST, 1, ISUB,
            CALL, FACTORIAL_INDEX, IMUL, RET, ICONST, 5, CALL, FACTORIAL_INDEX, PRINT, HALT };

    static FuncMetaData[] factorial_metadata = {
            // .def factorial: ARGS=1, LOCALS=0 ADDRESS
            new FuncMetaData("main", 0, 0, MAIN_ADDRESS), new FuncMetaData("factorial", 1, 0, FACTORIAL_ADDRESS) };

    static int[] f = { ICONST, 10, CALL, 1, PRINT, HALT,
            // .def f(x): ARGS=1, LOCALS=1
            // a = x;
            LOAD, 0, // 6 <-- start of f
            STORE, 1,
            // return 2*a
            LOAD, 1, ICONST, 2, IMUL, RET };

    static FuncMetaData[] f_metadata = { new FuncMetaData("main", 0, 0, 0), new FuncMetaData("f", 1, 1, 6) };

    // main method

    public static void main(String[] args) {
        Vm vm = new Vm(factorial, 0, factorial_metadata);
        vm.trace = true;
        vm.exec(factorial_metadata[0].address);
        vm = new Vm(f, 2, f_metadata);
        vm.exec(f_metadata[0].address);
        vm.dumpDataMemory();

        vm = new Vm(loop, 2, loop_metadata);
        vm.exec(loop_metadata[0].address);
    }
}
