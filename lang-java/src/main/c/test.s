	.file	"test.c"
	.intel_syntax noprefix
	.text
	.globl	test
	.type	test, @function
test:
.LFB0:
	.cfi_startproc
	push	rbp
	.cfi_def_cfa_offset 16
	.cfi_offset 6, -16
	mov	rbp, rsp
	.cfi_def_cfa_register 6
	sub	rsp, 32
	mov	DWORD PTR [rbp-20], edi
	mov	DWORD PTR [rbp-8], 0
	mov	DWORD PTR [rbp-4], 0
	mov	eax, DWORD PTR [rbp-4]
	mov	edi, eax
	mov	eax, 0
	call	anotherCall
	mov	eax, DWORD PTR [rbp-4]
	mov	DWORD PTR [rbp-8], eax
	leave
	.cfi_def_cfa 7, 8
	ret
	.cfi_endproc
.LFE0:
	.size	test, .-test
	.globl	anotherCall
	.type	anotherCall, @function
anotherCall:
.LFB1:
	.cfi_startproc
	push	rbp
	.cfi_def_cfa_offset 16
	.cfi_offset 6, -16
	mov	rbp, rsp
	.cfi_def_cfa_register 6
	mov	DWORD PTR [rbp-4], edi
	pop	rbp
	.cfi_def_cfa 7, 8
	ret
	.cfi_endproc
.LFE1:
	.size	anotherCall, .-anotherCall
	.ident	"GCC: (Ubuntu/Linaro 4.7.3-12ubuntu1) 4.7.3"
	.section	.note.GNU-stack,"",@progbits
