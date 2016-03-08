### Makefile ###

CFLAGS = -Wall -Wextra -ansi -pedantic -O2
OBJS = $(patsubst %.c,%.o,$(wildcard *.c))
LIBS = 

gestatus: $(OBJS)
			$(CC) $(CFLAGS) -o gesthiper $(OBJS) $(LIBS)

clean:
			rm gesthiper $(wildcard *.o)
