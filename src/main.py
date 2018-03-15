import os
import numpy as np
import matplotlib.pyplot as plt
import math as m

file = open("testdata.txt","r")
delimiter = ' '
n_lines=0

mean_sig = np.loadtxt(file)
file.close()

print(mean_sig)

R=np.corrcoef(mean_sig)

print(R)

z = np.zeros(R.shape)

print(z)

for region in range(0,R.shape[0]):
    for time in range(0,R.shape[1]):
        z[region,time]=0.5*m.log((1+R[region,time])/(1-R[region,time]))

print(z)

np.fill_diagonal(z,1)

print(z)

plt.matshow(z, cmap=plt.cm.inferno)
plt.show()