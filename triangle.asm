;use a nested loop to create a triangle with a base of 10

section.data
    global_start
    char db " "        ;declares char to store space and newlines
    basesize db 10     ;base size is 10
    i db 1             ;starting size is 1

section.text
_start:
    mov rax, [basesize]
    outer_loop:
        mov rbx, [0]
        inner_loop:
            call dot
            inc bx
            cmp [i],bx
            jne inner_loop
        call linefeed
        dec ax
        cmp ax,0
        jne outer_loop
        call linefeed
        call exit

exit:
	mov eax,1						;system call to exit            
	mov ebx,0						;exit value             
	int 80h							;call kernel with interrupt to exit program
	ret


;new line code section

linefeed:	
	;move newline character into char
	mov [char],  byte 10
	push rax						;push rax on to stack to allow outer loop to occur
	push rbx						;push rbx on to stack to allow inner loop to occur 
	mov eax,4						;system call to write out           
	mov ebx,1						;standard out to screen 
	mov ecx, char				;go to next line on screen 
	mov edx,1						;size of new line
	int 80h							;interrupt to kernel to go to next line by looking into registers
	pop rbx							;pop rbx on to stack to allow outer loop to occur
	pop rax							;pop rax on to stack to allow inner loop to occur 
	ret
	
dot:	

	mov [char],  byte '.'					;put dot (.) in char
	push rax						;push rax on to stack to allow outer loop to occur
	push rbx						;push rbx on to stack to allow inner loop to occur 
	mov eax,4						;system call to write out           
	mov ebx,1						;standard out to screen 
	mov ecx, char				;put dot in register ecx 
	mov edx,1						;size of dot in register
	int 80h							;interrupt to kernel to print dot on screen by looking into registers        
	pop rbx
	pop rax
	ret 
      
        
    
    
   
