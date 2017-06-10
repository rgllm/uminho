#!/bin/bash
make
./gramatica inputs/mat.tuga > emul/teste.vm
java -jar ~/uni/PL/TP3/emul/dist/vm.jar