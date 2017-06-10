#!/bin/bash
make
./gramatica programas/mat.tuga > emul/teste.vm
java -jar emul/dist/vm.jar