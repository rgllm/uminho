%write NBS matrices .mat file
name='TestData_';
name_mat_file='TestData_Matrix_C0.mat';

n_participants=1;
n_cond=1;
%n_participants=size(filenames,2);

index=1;
for p=1:(n_participants*n_cond)
    
    [file,pathname] = uigetfile('*.txt','select txt files','MultiSelect','off');
    
    %file=filenames{p};
    disp(file)
    
    mean_sig = import_txtfile(strcat(pathname,file));
    
    %Pearson Correlation
    R=corrcoef(mean_sig);
    
    %Fisher's Z-transform of the correlation coefficient R
    z=zeros(size(R,1),size(R,2));
    for j=1:size(R,1)
        for n=1:size(R,2)
            z(j,n)=.5.*log((1+R(j,n))./(1-R(j,n)));
        end
    end
    
    %Put '1's in the diagonal matrix
    z(isinf(z))=1; 
    index=index+1;
end

save(name_mat_file) 

figure;

title('FC');

image(1:116,1:116,z,'CDataMapping','scaled');

colormap(jet);

colorbar;

caxis([-1 1])

saveas(gcf,strcat(name,'FC'));
