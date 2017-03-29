#!/bin/sh
for FILE in *-in.txt

do
	echo $FILE
	base=${FILE%-in.txt}
    java GS $FILE > $base.antonyeskil.out.txt # replace with your command!
    diff $base.antonyeskil.out.txt $base-out.txt
done
