import numpy as np

def dot_2(a, b):
    assert len(a) == len(b), 'Vector sizes must match'
    return sum(aterm * bterm for aterm, bterm in zip(a, b))


def dot_3(a, b, c):
    assert len(a) == len(b) == len(c), 'Vector sizes must match'
    return sum(aterm * bterm * cterm for aterm, bterm, cterm in zip(a, b, c))


def dot_4(a, b, c, d):
    assert len(a) == len(b) == len(c) == len(d), 'Vector sizes must match'
    return sum(aterm * bterm * cterm * dterm for aterm, bterm, cterm, dterm in zip(a, b, c, d))


#def dot_N:
    #meta programação - ao chamar o corrcoef_N gerar o programa com o número respetivo de for

#dot(a, b)[i,j,k,m] = sum(a[i,j,:] * b[k,:,m])