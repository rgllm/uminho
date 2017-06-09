@include "funcs.gawk"
BEGIN {
        RS="<foto ";
        FS=">[ ]*\n";
        headerTitle="Processamento de Linguagens 16/17 TP1 Grupo XX";
        bodyTitle="Álbum Fotográfico em HTML";
        headerFormat= "<html><head><meta charset = 'UTF-8'/><title>%s</title></head><body><center><h1>%s</h1></center><ul>\n";
        item="<center><li><b><a href=\"%s\">%s</a></b></li></center>\n";
        desc="<center><p>%s , %s</p><p>%s</p><img src=\"fotos/%s\" width=500><p>%s</p></center>\n";
        printf headerFormat , headerTitle , bodyTitle > "index.html";
    }  
NR>1 {
        onde=quem=facto=quando=foto=legenda="";
        foto=foto2=getCampo($1);
        sub( /\.[a-zA-Z]+/ ,"",foto2);
        for( i=2 ; i<=NF ; i++ ){
            if($i~/<quem/){
                 quem=trim( getCampoDefault($i) );
            }
            if($i~/<onde/){
                 onde=trim( getCampoDefault($i) );
                 if( pertence( locais , onde ) == 0 )
                    locais[locLength++]=onde;
            }
            if($i~/<quando/){
                 split( $i , arr , "\"" );
                 quando= getCampo($i);
            }
            if($i~/<facto/){
                facto=trim( getCampoDefault($i) );
            }
            if($i~/<legenda/){
                legenda=trim( getCampoDefault($i) );
                gsub(/\"/,"''",legenda);
            }
        }
        printf headerFormat ,onde , quem > foto2".html"; 
        printf desc, onde , quando , facto , foto , legenda > foto2".html";  
        printf item , foto2".html" , quem > "index.html";      
    }
END {
	    for(x in locais)
            print locais[x] ;
        print "</ul></body></html>" > "index.html";
    }
