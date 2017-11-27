package user;

import jade.wrapper.ContainerController;
import main.ContainerHandler;

public class Mother{
   private ContainerHandler cont;
   private ContainerController c;
   private int time;
	
   public Mother(ContainerController c, int t) {
	   cont = new ContainerHandler();
	   this.c = c;
	   this.time = t;
   }
   
   public void giveBirth() {
	   int i = 1;
	   while(true) {
		   try {
			   cont.addAgentToContainer("user-"+i, "user.User", c);
			   Thread.sleep(time);
			   i ++;
		   }catch(Exception e) {}
	   }
   }
   public void setTime(int time) {
	   this.time = time;
   }
}
