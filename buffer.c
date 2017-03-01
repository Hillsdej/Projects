#include <fcntl.h>
#include <stdlib.h>
#include <unistd.h>
#include <stdio.h>

#define BUF_SIZE 500      /*Defines the buffer size*/
#define OUTPUT_MODE 0700  /*Sets permissions*/

int main(int argc, char *argv[])
{
    int in_fd, out_fd;
    int rd_size = 1, wr_size;
    char buf[BUF_SIZE];   /*Declares buffer size*/
    int countBuf = 0;
    int totalChar = 0;
    int countWord = 0;
    
    if (argc != 3) /*if there aren't 3 arguments, the program will exit*/
    {
        printf("insufficient number of arguments given");
        exit(1);
    }

    in_fd = open(argv[1], O_RDONLY); /*Get input file name. in_fd stores num related to file*/
    
    if (in_fd < 0) /*gets negative number if file is not there*/
    {
        /*another print statement,saying file not found*/
        printf("file cannot be found");
        exit(2);
    }

    out_fd = creat(argv[2],OUTPUT_MODE); /*create output file*/
    
    if (out_fd < 0) /*checks if file is created*/
    {
        /*print statement which says file not created*/
        printf("file not created");
        exit(3);
    }
    
      
    while (rd_size > 0)
    {
        rd_size = read(in_fd, buf,BUF_SIZE); 
        
        
        int i;
        for(i=0; (i<=rd_size); i++)
        {
            if (buf[i] == " ")
            {
                countWord++;
            }
        }
        
        
        if (rd_size <0)
        {
            printf("Something went wrong");
            exit(4);
        }
        
        /*printf("this is the number of characters read at a time: ");
        printf("%i\n",rd_size);*/
        
        wr_size = write(out_fd, buf, rd_size); /*repeatedly write from buffer to new file*/
        
        totalChar = totalChar + rd_size;
        
        countBuf++;
                
        if (wr_size<=0)
        {
            close(in_fd);
            close(out_fd); /*close files*/
            printf("this is the number of characters read in total: ");
            printf("%i\n",totalChar);
            countBuf--;
            printf("this is the number of times the buffer is filled: ");
            printf("%i\n",countBuf);
            printf("this is the number of words: ");
            printf("%i\n",countWord);
            exit(5);
        }
        
        
        /*for counting the number of times the loop goes around*/
        
    }
}

/*count number of times the while loop goes around to count the number of times the buffer is filled, remember to subtract one*/
/*declare another buffer, to compare the two documents. declare other numbers to store these. compare character to character "easy way"*/

