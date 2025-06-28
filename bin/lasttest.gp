set terminal pngcairo  size 1280,600 font 'sans,12' 
set out '/var/www/html/docker-darkerckhoff-web/lasttest.png'
set title "Lasttest"
set xrange [ 50 : 100 ]
plot 'lasttest.dat' using 1:2  title 'Auslastung'  with  lines linewidth 4  linecolor rgb '#204080'
