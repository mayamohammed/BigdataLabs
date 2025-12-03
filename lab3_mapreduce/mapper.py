#!/usr/bin/env python3
import sys

# Mapper: lit STDIN, découpe en mots et émet "mot\t1" par occurrence
for line in sys.stdin:
    line = line.strip()
    if not line:
        continue
    words = line.split()
    for word in words:
        print(f"{word}\t1")
