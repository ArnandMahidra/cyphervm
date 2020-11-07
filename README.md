# cyphervm java virtual machine

a java virtual machine (in progress) and a play ground project for me where I implement what I consider to be the best possible design choices for a modern , high performance and a head of time compilation virtual machine while learning inspired from parrt

# cyphervm instruction set

![# cyphervm-instruction-set](https://github.com/cycl0matic/cyphervm/blob/main/images/instructionSet.png)

# Instruction Format

code memory : `32 bit word addressable`
data memory : `32 bit word addressable`
operands    : `32 bit integers`
addresses   : `normal integers`
bytecodes   : `bytes stored as ints`

# stack machine

![# cyphervm-Stack-machine](https://github.com/cycl0matic/cyphervm/blob/main/images/stackMachine.png)

``` 
fetch   : opcode = code[ip]
decode  : switch(opcode){...}
execute : stack[++sp--] + stack[sp--] (iadd instruction)

```
