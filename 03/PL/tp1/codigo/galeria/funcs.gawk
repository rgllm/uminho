function trim( s ) {
    sub(/^[ \t\r\n]+/, "", s);
    sub(/[ \t\r\n]+$/, "", s);
    return s;
}
function pertence( arr , s ){
    for( x in arr ){
        if( arr[x] == s )
            return 1;
    }
    return 0;
}
function getCampoDefault( campo ){
    split( campo , arr , "[><]" );
    return arr[3];
}
function getCampo( campo ){
    split( campo , arr , "\"" );
    return arr[2];
}
