set terminal svg 
set size 1.0,0.5

set output 'day_of_week.svg'
unset key
set xrange [0.5:7.5]
set xtics 1
set grid y
set ylabel "Commits"
plot 'day_of_week.dat' using 1:2:(0.5) w boxes fs solid
