
from pylab import *


"""
performance measured on core 2 duo 2,2 ghz (only one processor used by programs,
                                    linux 32 bit.)

performance for 10k comparisons

- on testset of 100 random proteins as descibe din list-randon
- TM Align and CE => output is > to /dev/null
-- => this is good, but a disadvantage for lajolla as it writes out good results



TM align
CE
CE RESULT OF on dir: /lustrefs/workbig/bcsrbaue/performance_datasets/cath_50/
2002.41630006
CE RESULT OF on dir: /lustrefs/workbig/bcsrbaue/performance_datasets/cath_75/
3333.93603301
CE RESULT OF on dir: /lustrefs/workbig/bcsrbaue/performance_datasets/cath_100/
4860.26960683
TM ALIGN RESULT OF on dir: /lustrefs/workbig/bcsrbaue/performance_datasets/cath_50/
189.936295033
TM ALIGN RESULT OF on dir: /lustrefs/workbig/bcsrbaue/performance_datasets/cath_75/
423.009778976
TM ALIGN RESULT OF on dir: /lustrefs/workbig/bcsrbaue/performance_datasets/cath_100/
658.408638954
LAJOLLA RESULT OF 30 and on dir: /lustrefs/workbig/bcsrbaue/performance_datasets/cath_50/
8.68718194962
LAJOLLA RESULT OF 30 and on dir: /lustrefs/workbig/bcsrbaue/performance_datasets/cath_75/
5.8398771286
LAJOLLA RESULT OF 30 and on dir: /lustrefs/workbig/bcsrbaue/performance_datasets/cath_100/
6.79515600204
LAJOLLA RESULT OF 25 and on dir: /lustrefs/workbig/bcsrbaue/performance_datasets/cath_50/
22.5643000603
LAJOLLA RESULT OF 25 and on dir: /lustrefs/workbig/bcsrbaue/performance_datasets/cath_75/
13.069065094
LAJOLLA RESULT OF 25 and on dir: /lustrefs/workbig/bcsrbaue/performance_datasets/cath_100/
12.5136330128
LAJOLLA RESULT OF 20 and on dir: /lustrefs/workbig/bcsrbaue/performance_datasets/cath_50/
97.944852829
LAJOLLA RESULT OF 20 and on dir: /lustrefs/workbig/bcsrbaue/performance_datasets/cath_75/
71.4415180683
LAJOLLA RESULT OF 20 and on dir: /lustrefs/workbig/bcsrbaue/performance_datasets/cath_100/
63.4907929897
LAJOLLA RESULT OF 15 and on dir: /lustrefs/workbig/bcsrbaue/performance_datasets/cath_50/
455.219074011
LAJOLLA RESULT OF 15 and on dir: /lustrefs/workbig/bcsrbaue/performance_datasets/cath_75/
404.147770882
LAJOLLA RESULT OF 15 and on dir: /lustrefs/workbig/bcsrbaue/performance_datasets/cath_100/
444.437678099
LAJOLLA RESULT OF 10 and on dir: /lustrefs/workbig/bcsrbaue/performance_datasets/cath_50/
2055.13985801
LAJOLLA RESULT OF 10 and on dir: /lustrefs/workbig/bcsrbaue/performance_datasets/cath_75/
2478.14512801
LAJOLLA RESULT OF 10 and on dir: /lustrefs/workbig/bcsrbaue/performance_datasets/cath_100/
3016.24674106
TM ALIGN RESULT OF on dir: /lustrefs/workbig/bcsrbaue/performance_datasets/cath_50/
189.936295033
TM ALIGN RESULT OF on dir: /lustrefs/workbig/bcsrbaue/performance_datasets/cath_75/
423.009778976
TM ALIGN RESULT OF on dir: /lustrefs/workbig/bcsrbaue/performance_datasets/cath_100/
658.408638954


"""


lajolla30 = 6
lajolla25 = 12
lajolla20 = 63
lajolla15 = 444
lajolla10 = 3016

tmalign = 658
ce = 4860


algorithmname = ["LJ 30", "LJ 25", "LJ 20", "LJ 15", "LJ 10", "CE", "TM-align"]


timetaken =[lajolla30, lajolla25, lajolla20, lajolla15, lajolla10, ce, tmalign]
width = 0.5

N=7
ind = np.arange(N)

     # the width of the bars

figure()
rects = bar(ind, timetaken, width, color="k")
xlabel("Algorithm Name")
ylabel("Performance in seconds on dataset CATH_100")
xticks(ind+width/2., algorithmname)
# title('B')
#legend( (rects1[0], rects2[0]), ('#tRNA', 'RMS') )
#axis([3, 25, 0, 10000])
savefig("performance1.png")


show()
