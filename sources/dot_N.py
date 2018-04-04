import numpy as np  

def dot_4(a,b,c,d):
    assert len(a) == len(b) == len(c) == len(d), 'Vector sizes must match'
    return sum(aterm * bterm * cterm * dterm for aterm,bterm,cterm,dterm in zip(a, b, c, d))

def dot_3(a,b,c):
    assert len(a) == len(b) == len(c), 'Vector sizes must match'
    return sum(aterm * bterm * cterm for aterm,bterm,cterm in zip(a, b, c))

def dot_2(a,b):
	assert len(a) == len(b), 'Vector sizes must match'
	return sum(aterm * bterm for aterm,bterm in zip(a,b))

def corrcoef_2(x, y=None, rowvar=True, bias=np._NoValue, ddof=np._NoValue):
	for k1 in range(0, x.shape[0]):
		for k2 in range(0, x.shape[0]):

a=[1,2,3]
b=[1,2,3]

print("dotp_2:", dotp_2(a,b))
print("dot", np.dot(a,b))