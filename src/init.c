int main(){
    system("mkdir ~/.backup");
    system("mkdir ~/.backup/data");
    system("mkdir ~/.backup/metadata");
    system("mkfifo ~/.backup/pipe");
}