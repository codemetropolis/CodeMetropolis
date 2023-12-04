set terminal svg 
set size 1.0,0.5

set output 'hour_of_day.svg'
unset key
set xrange [0.5:24.5]
set xtics 4
set grid y
set ylabel "Commits"
plot 'hour_of_day.dat' using 1:2:(0.5) w boxes fs solid