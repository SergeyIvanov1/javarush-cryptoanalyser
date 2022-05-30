**Crypto analyser**
____
Description: The program encode and decode text Caesar's 
cipher. 

At the beginning program's work user must 
choose the language. User indicate two path for each mode 
program's working (where from take text and where to save).

The program had six modes of using. 
- "Encryption". Text from first indicated file is encoding
to second file. 
- "Decryption with key". The program will request the key
for decryption.
- "Manual decryption "Brute Force" method's". Decryption will 
make to indicated directory. Amount of files depends from 
amount of language's letters (possible keys).
- "Auto Decryption "Brute Force" method's". Program will 
use all possible keys in order. Each of decrypted text will
automatically verify to correct decryption, using most 
frequently words of language. Decryption will make to 
indicate file.
- "Manual decryption with statistic". The program gets most
frequent letter of encrypted text and took it for six the 
greatest frequent letters of alphabets in order. The key is 
difference between letter's indexes. Decryption will 
make to indicate directory into different files.
- "Auto decryption with statistic". The program works like 
previous function, but decryption will make to indicate file 
after automatically verify to correct decryption, using most 
frequently words of language.

Building a project:


Project launch:


Limitations:
Amount keys can be from Integer.MIN_VALUE to Integer.MAX_VALUE.

