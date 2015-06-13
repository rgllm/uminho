#
#	Makefile Code
#

CFLAGS=-Wall -Wextra -ansi -pedantic -O2

OBJS=$(patsubst %.c,%.o,$(wildcard *.c))

bn: $(OBJS)
	$(CC) $(CFLAGS) -o bn $(OBJS)
	cp bn ../tests/T2
	cp bn ../tests/T3

limpar:
	rm bm *.o