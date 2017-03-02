#include <stdio.h>
#include <stdlib.h>
#include <fcntl.h>
#include <sys/stat.h>
#include <sys/types.h>
#include <unistd.h>
#inlcude <malloc.h>

/*sys.types are probs needed*/
/*find out what includes need to be present for the system calls for open,read and close*/ 

//The internals of this struct aren't important
//from the user's point of view
typedef struct{
  FILE* file;        //File needs to be represented by an integer
  int bufferlength;  //Fixed buffer length
  int usedbuffer;    //Current point in the buffer
  char* buffer;      //A pointer to a piece of memory
                     //  same length as "bufferlength"
} cr_file;




//Open a file with a given size of buffer to cache with
cr_file* cr_open(char* filename, int buffersize);


//Close an open file
void cr_close(cr_file* f);

//Read a byte.  Will return EOF if empty.
char cr_read_byte(cr_file* f);



//---------------------------------------------------------

//Refill an empty buffer.  Not intended for users
int refill(cr_file* buff);
