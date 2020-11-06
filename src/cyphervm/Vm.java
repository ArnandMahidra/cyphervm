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

import java.util.ArrayList;
import java.util.List;

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

    public boolean trace = false;

    public VM(int[] code, int nglobals, FuncMetaData[] metadata) {
		this.code = code;
		globals = new int[nglobals];
		stack = new int[DEFAULT_STACK_SIZE];
		this.metadata = metadata;
    }

    public void exec(int startip) {
        ip = startip;
        ctx = new Context(null, 0, metadata[0]); // simulate a call to main()
        cpu();
    }

    // start FDE cycle semilation
    protected void cpu() {
        int opcode = code[ip];
        int a, b, addr, regnum;
        while (opcode != HALT && ip < code.length) {
            if (trace)
                System.err.printf("%-35s", disInstr());
            ip++; // jump to next instruction or to operand
            switch (opcode) {
                case IADD:
                    b = stack[sp--]; // 2nd opnd at top of stack
                    a = stack[sp--]; // 1st opnd 1 below top
                    stack[++sp] = a + b; // push result
                    break;
                case ISUB:
                    b = stack[sp--];
                    a = stack[sp--];
                    stack[++sp] = a - b;
                    break;
                case IMUL:
                    b = stack[sp--];
                    a = stack[sp--];
                    stack[++sp] = a * b;
                    break;
                case ILT:
                    b = stack[sp--];
                    a = stack[sp--];
                    stack[++sp] = (a < b) ? TRUE : FALSE;
                    break;
                case IEQ:
                    b = stack[sp--];
                    a = stack[sp--];
                    stack[++sp] = (a == b) ? TRUE : FALSE;
                    break;
                case BR:
                    ip = code[ip++];
                    break;
                case BRT:
                    addr = code[ip++];
                    if (stack[sp--] == TRUE)
                        ip = addr;
                    break;
                case BRF:
                    addr = code[ip++];
                    if (stack[sp--] == FALSE)
                        ip = addr;
                    break;
                case ICONST:
                    stack[++sp] = code[ip++]; // push operand
                    break;
                case LOAD: // load local or arg
                    regnum = code[ip++];
                    stack[++sp] = ctx.locals[regnum];
                    break;
                case GLOAD:// load from global memory
                    addr = code[ip++];
                    stack[++sp] = globals[addr];
                    break;
                case STORE:
                    regnum = code[ip++];
                    ctx.locals[regnum] = stack[sp--];
                    break;
                case GSTORE:
                    addr = code[ip++];
                    globals[addr] = stack[sp--];
                    break;
                case PRINT:
                    System.out.println(stack[sp--]);
                    break;
                case POP:
                    --sp;
                    break;
                case CALL:
                    // expects all args on stack
                    int findex = code[ip++]; // index of target function
                    int nargs = metadata[findex].nargs; // how many args got pushed
                    ctx = new Context(ctx, ip, metadata[findex]);
                    // copy args into new context
                    int firstarg = sp - nargs + 1;
                    for (int i = 0; i < nargs; i++) {
                        ctx.locals[i] = stack[firstarg + i];
                    }
                    sp -= nargs;
                    ip = metadata[findex].address; // jump to function
                    break;
                case RET:
                    ip = ctx.returnip;
                    ctx = ctx.invokingContext; // pop
                    break;
                default:
                    throw new Error("invalid opcode: " + opcode + " at ip=" + (ip - 1));
            }
            if (trace)
                System.err.printf("%-22s %s\n", stackString(), callStackString());
            opcode = code[ip];
        }
        if (trace)
            System.err.printf("%-35s", disInstr());
        if (trace)
            System.err.println(stackString());
        if (trace)
            dumpDataMemory();
    }

    protected String stackString() {
        StringBuilder buf = new StringBuilder();
        buf.append("stack=[");
        for (int i = 0; i <= sp; i++) {
            int o = stack[i];
            buf.append(" ");
            buf.append(o);
        }
        buf.append(" ]");
        return buf.toString();
    }

    protected String disInstr() {
        int opcode = code[ip];
        String opName = Bytecode.instructions[opcode].name;
        StringBuilder buf = new StringBuilder();
        buf.append(String.format("%04d:\t%-11s", ip, opName));
        int nargs = Bytecode.instructions[opcode].n;
        if (opcode == CALL) {
            buf.append(metadata[code[ip + 1]].name);
        } else if (nargs > 0) {
            List<String> operands = new ArrayList<String>();
            for (int i = ip + 1; i <= ip + nargs; i++) {
                operands.add(String.valueOf(code[i]));
            }
            for (int i = 0; i < operands.size(); i++) {
                String s = operands.get(i);
                if (i > 0)
                    buf.append(", ");
                buf.append(s);
            }
        }
        return buf.toString();
    }
}
