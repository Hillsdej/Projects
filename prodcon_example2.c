#include <sys/ipc.h>
#include <sys/sem.h>
#include <sys/shm.h>
#include <stdio.h>
#include <stdlib.h>
#include "se207_sems.h"

int bufferlength=8; //Limited buffer length 
//what could we do about this?

int main(int argc, char argv[]){
  
  int prodposition=bufferlength+1;
  int conposition=bufferlength+2;
  
  //Create shared memory segment
  int shm_id=shmget(ftok("prodcon_example2.c",2),bufferlength, 
		    0666|IPC_CREAT);

  //Use source file as the "key"
  int id=se207_semget("prodcon_example2.c",0);

  char* data; //For our pointer to shared memory...

  int pid=fork();  
  if(pid){
    
    //P1 - CONSUMER
    shm_id=shmget(ftok("prodcon_example2.c",2),0,006);

    //Attach the shared buffer
    data = shmat(shm_id, (void *)0, 0);
    data[prodposition] = 0;
    data[conposition] = 0;
    
    while(1){
    
      se207_wait(id);
      while (data[prodposition] == data[conposition]); //check if positions are same, if so do nothing
      printf("Consuming item number %d...\n",data[conposition]);
      sleep(10);
      char item=data[data[conposition]];
      
      printf("Consumed item number %d.  Item value was %d\n",data[conposition],item);
      data[conposition] = (data[conposition] + 1) % bufferlength; //loop back to start, make sure behind producer
    }

    //Detatch
    shmdt(data);
    printf("All done consuming.\n");

    wait(); //For child process so that we can

    //Delete the shared memory
    printf("Child ended, removing shm\n");
    shmctl(shm_id, IPC_RMID, NULL);    
  }else{
  
  
    //P2 - PRODUCER
    shm_id=shmget(ftok("prodcon_example2.c",2),0,006);                   
    //Attach the shared buffer
    data = shmat(shm_id, (void *)0, 0);
    data[prodposition] = 0;
    data[conposition] = 0;
    while(1){
    
    while((data[prodposition] + 1) % bufferlength == data[conposition]); //check if positions are the same, if so do nothing
      printf("Producing item number %d...\n",data[prodposition]);
      sleep(2);
      data[data[prodposition]]=data[prodposition]*2; //Simple data, easy to check.
      printf("Produced item number %d.  Value is %d\n",data[prodposition],data[data[prodposition]]);
           data[prodposition] = (data[prodposition] +1) % bufferlength; //loop back to start, make sure in front of consumer
      se207_signal(id);
    }
    //Detatch
    shmdt(data);
    printf("Producer finished.");
  }  
}
