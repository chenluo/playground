// gcc -S -masm=intel test.c produces test.s
// -O2 seems not necessary
void test(int i) {
    int j = 0;
    i = 0;
    anotherCall(i);
    j = i;
}

void anotherCall(int i) {
}
