#
# Makefile
#
all : testA testB testC testD testE doc tex/relatorio.pdf
src/TA/Tarefa1: src/TA/Tarefa1.hs
	ghc src/TA/Tarefa1.hs
src/TB/Tarefa2: src/TB/Tarefa2.hs
	ghc src/TB/Tarefa2.hs
src/TC/Tarefa3: src/TC/Tarefa3.hs
	ghc src/TC/Tarefa3.hs
src/TD/Tarefa4: src/TD/tarefa4.hs
	ghc src/TD/Tarefa4.hs
src/TE/Tarefa5: src/TE/tarefa5.hs
	ghc src/TE/Tarefa5.hs
testA: src/TA/Tarefa1
	cd tests; bash runtests.sh tA ../src/TA/Tarefa1.hs
testB: src/TB/Tarefa2
	cd tests; bash runtests.sh tB ../src/TB/Tarefa2.hs
testC: src/TC/Tarefa3
	cd tests; bash runtests.sh tC ../src/TC/Tarefa3.hs
testD: src/TD/Tarefa4
	cd tests; bash runtests.sh tC ../src/TD/tarefa4.hs
testE: src/TE/Tarefa5
	cd tests; bash runtests.sh tC ../src/TE/tarefa5.hs
doc: docA docB docC docD docE
docA: src/TA/Tarefa1.hs
	haddock -h -o doc/TA src/TA/Tarefa1.hs
docB: src/TB/Tarefa2.hs
	haddock -h -o doc/TB src/TB/Tarefa2.hs
docC: src/TC/Tarefa3.hs
	haddock -h -o doc/TC src/TC/Tarefa3.hs
docD: src/TD/Tarefa4.hs
	haddock -h -o doc/TD src/TD/tarefa4.hs
docE: src/TE/Tarefa5.hs
	haddock -h -o doc/TE src/TE/tarefa5.hs
tex/relatorio.pdf: tex/relatorio.tex
	cd tex; pdflatex relatorio.tex; pdflatex relatorio.tex
clean:
	rm -f src/TA/Tarefa1.{hi,o} src/TB/Tarefa2.{hi,o} src/TC/Tarefa3.{hi,o} src/TD/Tarefa4.{hi,o} src/TE/Tarefa5.{hi,o}
	rm -f tex/relatorio.{aux,log,out,toc,lof}
realclean: clean
	rm -rf doc/TA doc/TB doc/TC doc/TD doc/TE src/TA/Tarefa1 src/TB/Tarefa2 src/TC/Tarefa3 src/TD/Tarefa4 src/TE/Tarefa5
	rm -f tex/relatorio.pdf