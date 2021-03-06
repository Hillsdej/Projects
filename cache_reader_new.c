#include "cache_reader.h"

//http://www.phim.unibe.ch/comp_doc/c_manual/C/SYNTAX/struct.html
//http://vergil.chemistry.gatech.edu/resources/programming/c-tutorial/structs.html

int refill(cr_file* buff){
  //Refills a buffer
  //Only works when completely used buffer
  if(buff->usedbuffer!=buff->bufferlength)
    return 0;
  else{
    buff->usedbuffer=0;
    int len=read(buff->file, buff->buffer, buff->bufferlength); //reordered.
    /If we didn't fill the buffer, fill up with EOF
    if(len<buff->bufferlength)
      for(int i=len;i<buff->bufferlength;i++)
        buff->buffer[i]=EOF;  //Accessing like an array!
    return len;
  }

}

void cr_close(cr_file* f){ /*close changed*/
  free(f->buffer);
  close(f->file);
}

cr_file* cr_open(char * filename, int buffersize){

  //Info on malloc
  //http://www.space.unibe.ch/comp_doc/c_manual/C/FUNCTIONS/malloc.html
  FILE* f;
  if ((f = open(filename, O_RDONLY)) == NULL){  /*replaced with open*/
    fprintf(stderr, "Cannot open %s\n", filename);
    return 0;
  }

  cr_file* a=(cr_file*)malloc(sizeof(cr_file));
  a->file=f;
  a->bufferlength=buffersize;
  a->usedbuffer=buffersize; //Start off with no characters, so refill will work as expected
  a->buffer=(char*)malloc(sizeof(char)*buffersize); /*replace with memaline*/

  refill(a);
  return a;
}

char cr_read_byte(cr_file* buff){
  
  if (buff -> usedbuffer == buff ->bufferlength) /* check if the buffer pointer is the same as the buffer length*/
  {
    refill(buff);
  }
  
  /*otherwise load that char into the buffer,and then move to the next one*/
  char b = buff -> buffer[buff -> usedbuffer];
  /*store in a variable, then return*/
    
  buff -> usedbuffer++;
  return b;
   
  return EOF; // this is just so the compile works...
}
