# GlobalAlignment
Global alignment of two sequences DNA sequences using a given mismatch cost matrix.

Calling convention: GlobalAlignment sequences.fasta matrix.txt.

matrix.txt format:
- i) all first lines starting with '#' are comments 
- ii) first non-comment line contains alphabet (letters are space-separated)
- iii) |sigma| lines follow
- iiia) line i contains |sigma| integer values
- iiib) k-th value corresponds to the cost of replacing the i-th letter by the k-th

sequences.fasta contains 2 sequences to align

Docs: https://kubajal.github.io/GlobalAlignment/
