#!/bin/bash

status=$(curl -s -o /dev/null -w '%{http_code}' https://informatik.hs-bremerhaven.de/docker-swe3-2024-02-java/) 
cd /home/$USER/repos/swe3-2024-02
if [ $status -eq 200 ]; then
  echo "$(date +'%F-%T') 10000 erreichbar" >> bin/up.dat
else 
  echo "$(date +'%F-%T') 8000 nicht erreichbar" >> bin/down.dat
fi
