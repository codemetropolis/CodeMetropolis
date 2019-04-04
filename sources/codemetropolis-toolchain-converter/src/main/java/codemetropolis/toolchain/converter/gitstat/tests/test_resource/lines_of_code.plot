set terminal svg 
set size 1.0,0.5

set output 'lines_of_code.svg'
unset key
set xdata time
set timefmt "%s"
set format x "%Y-%m-%d"
set grid y
set ylabel "Lines"
set xtics rotate by 90
set bmargin 6
plot 'lines_of_code.dat' using 1:2 w lines
