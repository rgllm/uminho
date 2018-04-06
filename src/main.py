import os
import numpy as np
import matplotlib.pyplot as plt
import math as m

def dot_2(a, b):
    assert len(a) == len(b), 'Vector sizes must match'
    return sum(aterm * bterm for aterm, bterm in zip(a, b))

def corrcoef_2(x):
    c = np.zeros((x.shape[0],x.shape[0]))
    for k1 in range(0,x.shape[0]):
        for k2 in range(0,x.shape[0]):
            c[k1,k2] = dot_2(x[k1,:],x[k2,:])
    return c

file = open("testdata.txt","r")

mean_sig = np.loadtxt(file)
file.close()

print("mean_sig Shape: ",mean_sig.shape)

#Matlab: R = corrcoef(A) returns the matrix of correlation coefficients for A,
# where the columns of A represent random variables and the rows represent observations.
#NumPy: If rowvar is True (default), then each row represents a variable,
# with observations in the columns. Otherwise, the relationship is transposed:
# each column represents a variable, while the rows contain observations.
R=corrcoef_2(mean_sig)
print
print("R Shape: ",R.shape)

z = np.zeros(R.shape)

print("Z shape: ",z.shape)

print(R)
for j in range(0,R.shape[0]):
    for n in range(0,R.shape[1]):
        if R[j,n]!=1:
            z[j,n]=0.5*m.log((1.0+R[j,n])/(1.0-R[j,n]))

np.fill_diagonal(z,1.0)

plt.matshow(z, cmap=plt.cm.jet,vmin=-1,vmax=1)
plt.show()