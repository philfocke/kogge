#!/bin/bash
from=$(date -d '6 day ago' +%F-00:00:00)
to=$(date +'%F-24:00:00')
XTICS=
SUNDAY=

# 7 Tage rückwärts
for i in 6 5 4 3 2 1 0 -1; do
  day=$(date -d "$i day ago" +%F-00:00:00)
  XTICS+=",'$day'"
  dayzero=$(date -d "$i day ago" +%F-00:00:00)
  daytwentyfour=$(date -d "$i day ago" +%F-24:00:00)
  weekday=$(LC_ALL=de_DE.utf8 date -d "$i day ago" +%A)
  if [ "$weekday" = Sonntag ] || [ "$weekday" = Samstag ]; then
    SUNDAY+="set obj rectangle from '$dayzero',14000 to '$daytwentyfour', 0 \n"
  fi
  if [ "$i" != -1 ]; then
    SUNDAY+="set label at '$dayzero',12000 '   $(date -d "$i day ago" +"$weekday %d.%m.%Y")' front left\n"
  fi
done
# erstes Komma weg
XTICS=${XTICS:1}

# Platzhalter mit sed ersetzen
sed -e 's:___OUT___:/var/www/html/docker-darkerckhoff-web/uptime.svg:g' \
  -e "s/___VON___/$from/g" \
  -e "s/___BIS___/$to/g" \
  -e "s/___XTICS___/$XTICS/g" \
  -e "s/#___SUNDAY___/$SUNDAY/g" \
  template.gp > uptime.gp

sed -i -e "s/___TITLE___/Verfügbarkeit/g" uptime.gp

gnuplot uptime.gp
