//critical_example2.c
#include <sys/ipc.h>
#include <sys/sem.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>


#include "se207_sems.h"

int main(int argc, char argv[]){
  //Our key is the source file
  int id=se207_semget("critical_example2.c",1);

  int pid=fork();
  if(pid){
    
    //Lady Macbeth
    //set counter for Lady Macbeth   
    int ladyM = 0;
    
    //Loop through to print her lines to stdout
    while(ladyM < 6){
      se207_wait(id);
      
      //Set Conditionals to print lines in order
      if (ladyM == 0){
        printf("LADY MACBETH Donalbain.\n");
        printf("--------------------\n"); //for purpose of clarity
        rsleep();
        printf("--------------------\n");
        }
      
      if (ladyM == 1){
        printf("LADY MACBETH A foolish thought, to say a sorry sight.\n");
        printf("--------------------\n");
        rsleep();
        printf("--------------------\n");
        }
      
      if (ladyM == 2){
        printf("LADY MACBETH There are two lodged together.\n");
        printf("--------------------\n");
        rsleep();
        printf("--------------------\n");
        }
      
      if (ladyM == 3){
        printf("LADY MACBETH Consider it not so deeply.\n");
        printf("--------------------\n");
        rsleep();
        printf("--------------------\n");
        }
      
      if (ladyM == 4){
        printf("LADY MACBETH These deeds must not be thought\n");
        printf("After these ways; so, it will make us mad.\n");
        printf("--------------------\n");
        rsleep();
        printf("--------------------\n");
        }
      
      if (ladyM == 5){
        printf("LADY MACBETH What do you mean?\n");
        printf("--------------------\n");
        rsleep();
        printf("--------------------\n");
        printf("-----END-----\n"); //signify completion
        }
      
      ladyM = ladyM + 1; //increment counter
      se207_signal(id); //call semiphore
      }
  }
  else{
    //Macbeth
    //set counter for Macbeth
    int mac = 0;
    
    //Loop through to print his lines to stdout
    while(mac < 5){
      se207_wait(id);
      
      if (mac == 0){
        printf("MACBETH This is a sorry sight.\n");
        printf("--------------------\n");
        rsleep();
        printf("--------------------\n");
        }
      
      if (mac == 1){
        printf("MACBETH There's one did laugh in's sleep, and one cried\n");
        printf("'Murder!'\n");
        printf("That they did wake each other: I stood and heard them:\n");
        printf("But they did say their prayers, and address'd them\n");
        printf("Again to sleep.\n");
        printf("--------------------\n");
        rsleep();
        printf("--------------------\n");
        }
      
      if (mac == 2){
        printf("MACBETH One cried 'God bless us!' and 'Amen' the other;\n");
        printf("As they had seen me with these hangman's hands.\n");
        printf("Listening their fear, I could not say 'Amen,'\n");
        printf("When they did say 'God bless us!'\n");
        printf("--------------------\n");
        rsleep();
        printf("--------------------\n");
        }
      
      if (mac == 3){
        printf("MACBETH But wherefore could not I pronounce 'Amen'?\n");
        printf("I had most need of blessing, and 'Amen'\n");
        printf("Stuck in my throat.'\n");
        printf("--------------------\n");
        rsleep();
        printf("--------------------\n");
        }
      
      if (mac == 4){
        printf("MACBETH Methought I heard a voice cry 'Sleep no more!\n");
        printf("Macbeth does murder sleep', the innocent sleep,\n");
        printf("Sleep that knits up the ravell'd sleeve of care,\n");
        printf("The death of each day's life, sore labour's bath,\n");
        printf("Balm of hurt minds, great nature's second course,\n");
        printf("Chief nourisher in life's feast,--\n");
        printf("--------------------\n");
        rsleep();
        printf("--------------------\n");
        }
      mac = mac + 1; // increment counter
      se207_signal(id); //call semiphore
      }
    }
  }
