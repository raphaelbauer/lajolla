
from pylab import *

ngram = [20, 15, 10, 5]
nfound =[65.90, 97.11, 99.94, 99.94]
rms =[1.85, 1.88, 1.74, 1.76]
tm = [0.37, 0.34, 0.45, 0.44]

width = 2.0       # the width of the bars

figure()
rects = bar(ngram, nfound, width, color='b')
xlabel('n-gram size')
ylabel('% tRNAs aligned')
# title('B')
#legend( (rects1[0], rects2[0]), ('#tRNA', 'RMS') )
axis([3, 25, 0, 100])
savefig('figure1.png')


figure()
rects = bar(ngram, rms, width, color='b')
xlabel('n-gram size')
ylabel('<RMS>')
axis([3, 25, 0, 3.0])
savefig('figure2.png')

figure()
rects = bar(ngram, tm, width, color='b')
xlabel('n-gram size')
ylabel('<TM-SCORE>')
axis([3, 25, 0, 0.5])
savefig('figure3.png')

show()
