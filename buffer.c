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

    if (argc != 3)
    {
        exit(1);
    }

    in_fd = open(argv[1], O_RDONLY); /*Get input file name*/
    if (in_fd < 0)
    {
        exit(2);
    }

    out_fd = creat(argv[2],OUTPUT_MODE); /*create output file*/
    if (out_fd < 0)
    {
        exit(3);
    }
    
    while (rd_size > 0)
    {
        rd_size = read(in_fd, buf,BUF_SIZE); /*repeatedly read from original file into buffer*/
        if (rd_size <0)
        {
            exit(4);
        }
        
        wr_size = write(out_fd, buf, rd_size); /*repeatedly write from buffer to new file*/
        if (wr_size<=0)
        {
            close(in_fd);
            close(out_fd); /*close files*/
            exit(5);
        }
    }
}
