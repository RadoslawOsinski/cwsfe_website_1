#!/bin/bash

#optimizes jpg images
for i in `find ./ -name \*.jpg`; do
	outputFile=${i%.jpg}_optimized.jpg;
	jpegtran -optimize $i > $outputFile;
done
