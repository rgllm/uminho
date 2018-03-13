import os
import numpy as np
import nibabel as nib
import matplotlib.pyplot as plt
from nibabel.testing import data_path




epi_img = nib.load('/Users/rgllm/Downloads/someones_epi.nii.gz')

epi_img_data = epi_img.get_data()

print(epi_img_data.shape)

def show_slices(slices):
    fig,axes = plt.subplots(1, len(slices))
    for i, slice in enumerate(slices):
        axes[i].imshow(slice.T, cmap="gray", origin="lower")

#Slices over the first, second and third dimensions of the array.

slice_0=epi_img_data[30, :, :]
slice_1=epi_img_data[:, 10, :]
slice_2=epi_img_data[:, :, 5]
show_slices([slice_0, slice_1, slice_2])
plt.suptitle("Center slices for EPI image")
plt.show()