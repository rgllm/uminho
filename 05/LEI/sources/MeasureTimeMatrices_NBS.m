%write NBS matrices .mat file
name='C:\Users\Marcelo Queirós\Documents\MIEI\Semestre 2\Laboratorio\gitProject\lei\sources\TestData_';
name_mat_file='C:\Users\Marcelo Queirós\Documents\MIEI\Semestre 2\Laboratorio\gitProject\lei\sources\TestData_Matrix_C0.mat';

n_participants=1;
n_cond=1;
%n_participants=size(filenames,2);

index=1;
for p=1:(n_participants*n_cond)
    
    %%[file,pathname] = uigetfile('*.txt','select txt files','MultiSelect','off');
    
    %file=filenames{p};
    %disp(file)
    file = 'C:\Users\Marcelo Queirós\Documents\MIEI\Semestre 2\Laboratorio\gitProject\lei\sources\TestData_fnc_vol_stime_mcf_bet_mni_denoised_wScrubbing_filter_aal.txt';
    mean_sig = import_txtfile(file, 1, inf);
    
    %disp(mean_sig)
    
    %Pearson Correlation
    tic
    R=corrcoef(mean_sig);
    toc
    %disp(R)
    
    %%disp(size(R,1))
    %%disp(size(R,2))
    
    %Fisher's Z-transform of the correlation coefficient R
    z=zeros(size(R,1),size(R,2));
    for j=1:size(R,1)
        for n=1:size(R,2)
            z(j,n)=.5.*log((1+R(j,n))./(1-R(j,n)));
        end
    end
    %toc
    
    %Put '1's in the diagonal matrix
    z(isinf(z))=1; 
    index=index+1;
    
   
end

 %disp(z)
 
%if z(j,n)>1.0
   % disp('HELLO')
%end

save(name_mat_file) 

figure;

title('FC');

image(1:116,1:116,z,'CDataMapping','scaled');

colormap(jet);

colorbar;

caxis([-1 1])

saveas(gcf,strcat(name,'FC'));
