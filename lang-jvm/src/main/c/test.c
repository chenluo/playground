// gcc -S -masm=intel test.c produces test.s
// -O2 seems not necessary
// link: https://preshing.com/20120625/memory-ordering-at-compile-time/
void test(int i) {
    int j = 0;
    i = 0;
    anotherCall(i);
    j = i;
}

void anotherCall(int i) {
}
