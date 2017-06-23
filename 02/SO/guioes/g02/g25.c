#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <sys/wait.h>
#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <sys/wait.h>

int main()
{	
	int pidf=0,i,status;
	
	for(i=0;i<10;i++)
	{
		if(pidf==0)
		{
			printf("%d - PID: %d  PPID: %d\n",i,getpid(),getppid() );
			pidf=fork();
		}
		else wait(&status);
	}
	return 1;
}