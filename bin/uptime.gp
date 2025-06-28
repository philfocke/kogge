set terminal svg enhanced font 'Helvetica,12' size 1600,180
set out '/var/www/html/docker-darkerckhoff-web/uptime.svg'

set title font ',20'

set title "Verfügbarkeit"
set xdata time
set format x '' 
set timefmt '%Y-%m-%d-%H:%M:%S'
today=system("date +'%F'")
now=system("date +'%F-%T'")

set xrange [ "2025-03-13-00:00:00" : "2025-03-19-24:00:00"]
set yrange [ 0:14000 ]

set xtics textcolor rgb "black"
set xtics ('2025-03-13-00:00:00','2025-03-14-00:00:00','2025-03-15-00:00:00','2025-03-16-00:00:00','2025-03-17-00:00:00','2025-03-18-00:00:00','2025-03-19-00:00:00','2025-03-20-00:00:00')
set arrow  from "2025-03-19-24:00:00", 0 to "2025-03-19-24:00:00", 14000 lw 1 lc rgb '#808080' nohead

unset ytics
set xtics rotate by 30 right
set key bottom left
set arrow  from now, 0 to now, 14000 lw 1 lc rgb 'black' dt 3 nohead

set style rect fc rgb 'gold' fs solid 0.1 noborder
set label at '2025-03-13-00:00:00',12000 '   Donnerstag 13.03.2025' front left
set label at '2025-03-14-00:00:00',12000 '   Freitag 14.03.2025' front left
set obj rectangle from '2025-03-15-00:00:00',14000 to '2025-03-15-24:00:00', 0 
set label at '2025-03-15-00:00:00',12000 '   Samstag 15.03.2025' front left
set obj rectangle from '2025-03-16-00:00:00',14000 to '2025-03-16-24:00:00', 0 
set label at '2025-03-16-00:00:00',12000 '   Sonntag 16.03.2025' front left
set label at '2025-03-17-00:00:00',12000 '   Montag 17.03.2025' front left
set label at '2025-03-18-00:00:00',12000 '   Dienstag 18.03.2025' front left
set label at '2025-03-19-00:00:00',12000 '   Mittwoch 19.03.2025' front left

set style rect fc lt -1 fs solid 0.03 noborder
#___RECTANGLES___

plot 14000 lc rgb '#808080' notitle, \
     10000 lc rgb 'black' dt 3 notitle, \
      8000 lc rgb 'black'  dt 3 notitle, \
     'up.dat'   using 1:2  title 'Erreichbar'  with points pt 7 ps 0.5 lc rgb 'sea-green', \
     'down.dat' using 1:2  title 'nicht erreichbar'  with points pt 7 ps 0.5 lc rgb 'dark-red'
