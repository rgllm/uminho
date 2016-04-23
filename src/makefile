#
#	Makefile Code
#

CFLAGS= -Wall -ansi -pedantic -O2

OBJS=$(patsubst %.c,%.o,$(wildcard *.c))

hipergest: $(OBJS)
	$(CC) $(CFLAGS) -o hipergest $(OBJS)
	rm *.o
