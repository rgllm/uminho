#
#	Makefile Code
#

CFLAGS= -Wall -O2
all: sobusrv sobucli init

sobusrv:
	$(CC) sobusrv.c -o sobusrv $(OBJS)

sobucli:
	$(CC) sobucli.c -o sobucli $(OBJS)

init:
	$(CC) init.c -o init $(OBJS)
