#include <stdlib.h>
#include <stdio.h>
#include <string.h>


char *my_strdup(const char *s) {
    char *p = malloc(strlen(s) + 1);
    if(p) { strcpy(p, s); }
    return p;
}