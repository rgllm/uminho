SRCS = $(wildcard *.c)

CFLAGS= -Wall -pedantic

PROGS = $(patsubst %.c,%,$(SRCS))

all: $(PROGS)

%: %.c

	$(CC) $(CFLAGS)  -o $@ $<