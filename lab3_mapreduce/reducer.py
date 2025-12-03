#!/usr/bin/env python3
import sys

# Reducer: lit les paires triées "mot\tcount" et agrège les counts par mot
current_word = None
current_count = 0

for line in sys.stdin:
    line = line.strip()
    if not line:
        continue
    parts = line.split('\t', 1)
    if len(parts) != 2:
        continue
    word, count = parts
    try:
        count = int(count)
    except ValueError:
        continue

    if current_word == word:
        current_count += count
    else:
        if current_word is not None:
            print(f"{current_word}\t{current_count}")
        current_word = word
        current_count = count

# dernier mot
if current_word is not None:
    print(f"{current_word}\t{current_count}")
