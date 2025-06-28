#!/bin/bash
source local/config.txt || exit 1
action="$3"
threads=$(($1/10))
ergebnis=$(wrk -c $1 -d $2 -t $threads  $baseurl/$webapp/$action | grep -e "Latency" -e "Req/" -e "Requests"| tr -s " " | sed 's/^ //g' | cut -d " " -f2 | sed 's/[^0-9]/./g' | sed 's/\.\.$//; s/\.$//' | tr "\n" " ")
echo "$1  $ergebnis" >> bin/lasttest.dat
