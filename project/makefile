#
#	Makefile Code LI3 (v1.0)
#

CFLAGS = -ansi
OBJS = $(patsubst %.c,%.o,$(wildcard *.c))

hipergest: $(OBJS)
	$(CC) $(CFLAGS) -o hipergest $(OBJS)

clean:
		rm hipergest $(wildcard *.o)
