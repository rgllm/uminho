#
#	Makefile Code
#

CFLAGS=-Wall -Wextra -ansi -pedantic -O2

OBJS=$(patsubst %.c,%.o,$(wildcard *.c))

hipergest: $(OBJS)
	$(CC) $(CFLAGS) -o hipergest $(OBJS)

limpar:
	rm bm *.o
