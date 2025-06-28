set terminal svg enhanced font 'Helvetica,12' size 1600,180
set out '___OUT___'

set title font ',20'

set title "___TITLE___"
set xdata time
set format x '' 
set timefmt '%Y-%m-%d-%H:%M:%S'
today=system("date +'%F'")
now=system("date +'%F-%T'")

set xrange [ "___VON___" : "___BIS___"]
set yrange [ 0:14000 ]

set xtics textcolor rgb "black"
set xtics (___XTICS___)
set arrow  from "___BIS___", 0 to "___BIS___", 14000 lw 1 lc rgb '#808080' nohead

unset ytics
set xtics rotate by 30 right
set key bottom left
set arrow  from now, 0 to now, 14000 lw 1 lc rgb 'black' dt 3 nohead

set style rect fc rgb 'gold' fs solid 0.1 noborder
#___SUNDAY___
set style rect fc lt -1 fs solid 0.03 noborder
#___RECTANGLES___

plot 14000 lc rgb '#808080' notitle, \
     10000 lc rgb 'black' dt 3 notitle, \
      8000 lc rgb 'black'  dt 3 notitle, \
     'up.dat'   using 1:2  title 'Erreichbar'  with points pt 7 ps 0.5 lc rgb 'sea-green', \
     'down.dat' using 1:2  title 'nicht erreichbar'  with points pt 7 ps 0.5 lc rgb 'dark-red'
