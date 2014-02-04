from pylab import *

width = 0.30       # the width of the bars








def plot_and_save(title_name, x_label, fn, axislimits):
    
    figure()
     
    tm, sp, t1, t5 = [], [], [], []
    
    for l in open(fn+'.txt'):
        t = l.strip().split()
        if len(t) < 4: continue
        tmcut = float(t[0])
        #if tmcut >=0.5: continue
        tm.append(tmcut)
        scorp = float(t[1])
        if t[2] != '-': top1 = float(t[2])
        else: top1 = 0.0
        if t[3] != '-': top5 = float(t[3])
        else: top5 = 0.0
        sp.append(scorp)
        t1.append(top1)
        t5.append(top5)
                
    rects = plot(tm, t1, 'k-', )
    rects = plot(tm, t5, 'k--', )
    rects = plot(tm, sp, 'r-', )
    
    title(title_name)
    xlabel(x_label)
    ylabel('%')
    axis(axislimits)
    savefig('figure_%s.png'%fn)
    
 
 
 
 
 
 
axislimits = [0, 1.0, 0, 1.0]

for fn in ['n10','n15','n20','n25','n30']:
    
    
    plot_and_save('Assignment of Toplogy using CATH for LaJolla (n-gram size %s)'%fn[1:], 'cutoff TM-Score', fn, axislimits)


plot_and_save('Assignment of Toplogy using CATH for TM-Align', 'cutoff TM-Score', 'tXX', axislimits)


axislimits = [0, 7.0, 0, 1.0]

plot_and_save('Assignment of Toplogy using CATH for CE', 'cutoff Z-Score', 'cXX', axislimits)


show()




