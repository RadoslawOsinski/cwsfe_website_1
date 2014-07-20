#!/bin/bash

#optimizes jpg images for size without loosing image quality
for i in `find ./ -name \*.jpg`; do
	outputFile=${i%.jpg}_optimized.jpg;
	jpegtran -optimize $i > $outputFile;
done
