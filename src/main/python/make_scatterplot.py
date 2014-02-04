#!/usr/bin/env python

from pylab import *


def read_detail(fn):
    tmscore = []
    nalign = []
    for l in open(fn):
        rms, tm, na = l.strip().split()
        tmscore.append(float(tm))
        nalign.append(float(na))
    return tmscore, nalign
        
        
tm1, na1 = read_detail('details.txt')
tm2, na2 = read_detail('../trna_result/details.txt')
tm3, na3 = read_detail('../nr95_scor/details.txt')
tm4, na4 = read_detail('../nr95_scor/details_fp.txt')


width = 2.0       # the width of the bars


figure()
rects = plot(tm1, na1, 'bx')
rects = plot(tm2, na2, 'rx')
rects = plot(tm3, na3, 'gx')
rects = plot(tm4, na4, 'kx')
xlabel('tmscore')
ylabel('naligned')
# title('B')
#legend( (rects1[0], rects2[0]), ('#tRNA', 'RMS') )
#axis([0, 1, 0, 120])
savefig('figure1.png')

show()
