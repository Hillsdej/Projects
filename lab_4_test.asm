start:
	mov ax, 07C0h		; Set up 4K stack space after this bootloader
	add ax, 288		; (4096 + 512) / 16 bytes per paragraph
	mov ss, ax
	mov sp, 4096

	mov ax, 07C0h		; Set data segment to where we're loaded
	mov ds, ax


	mov si, text_string	; Put string position into SI
	call print_string	; Call our string-printing routine

	mov si, text_string2
	call print_string

	mov si, text_string3
	call print_string

	mov si, text_string4
	call print_string

	mov si, text_string5
	call print_string

	mov si, text_string6
	call print_string

	jmp $			; Jump here - infinite loop!


	text_string db 'Name: Jack Hillsden',13,10,0
	text_string2 db 'Email: hillsdej@uni.coventry.ac.uk',13,10,0
	text_string3 db 'Fav Module: 207SE',13,10,0
	text_string4 db 'dob: 07/03/1997',13,10,0
	text_string5 db 'age: 19',13,10,0
	text_string6 db 'sid: 6472135',13,10,0


print_string:			; Routine: output string in SI to screen
	mov ah, 0Eh		; int 10h 'print char' function

.repeat:
	lodsb			; Get character from string
	cmp al, 0
	je .done		; If char is zero, end of string
	int 10h			; Otherwise, print it
	jmp .repeat

.done:
	ret


	times 510-($-$$) db 0	; Pad remainder of boot sector with 0s
	dw 0xAA55		; The standard PC boot signature
