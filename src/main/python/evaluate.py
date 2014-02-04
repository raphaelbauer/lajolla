#! /usr/bin/env python
# eval.py

import os,  os.path, sys,  re, math

INPUT_PATHS = [
               'nr95_chains_20_0.2/', 
               'nr95_chains_15_0.2/', 
               'nr95_chains_10_0.2/', 
               'nr95_chains_5_0.2/', 
               ]
    
def get_struct_dirs(dir):
    for fn in os.listdir(dir):
        path = dir+fn
        if os.path.isdir(path): yield fn

def summary(out):
    avg,  stdd = 0, 0
    bins = [0]*7
    n = len(out)
    n_valid = 0
    for o in out: 
        if o>CUTOFF: n_valid += 1
    if n > 0:
        avg = sum(out)*1.0/len(out)
    if n > 1:
        stdd = math.sqrt(sum([(x-avg)**2 for x in out])/(n-1))
    # fill size bins
    for i in range(7):
        for o in out:
            if o>=i*10: bins[i] += 1
    return [n,  n_valid,  avg,  stdd, bins]
    
def compare(r1, r2):
    s1 = -float(r1['tmscore'])
    s2 = -float(r2['tmscore'])
    return cmp(s1, s2)

def get_result_files(dir):
    """
    Identifies and parses names of LaJolla file names. 
    Generates parsed results.
    """
    records = []
    qname = dir.split('/')[1].split('.')[0][3:] + '_' + dir.split('-')[-1][:-1]
    for fn in os.listdir(dir):
        result = {'query':qname}
        if fn[-4:].upper() in ['.PDB', '.ENT']:
            tokens = fn[:-4].split('-')
            query = tokens[0] == 'q'
            if query: continue
            result['hit'] = re.sub('.pdb|.ent', '', tokens[1].lower())[3:] + '_' + tokens[5]
            #if query in EXCLUDE or result['hit'] in EXCLUDE: continue
            result['rmsd'] = tokens[7]
            result['tmscore'] = tokens[9]
            result['len_seq_1'] = tokens[11]
            result['len_seq_2'] = tokens[13]
            result['len_aligned'] = tokens[15]
            if result['tmscore']=='1.00': continue
            records.append(result)
            
    records.sort(compare)
    #print [r['tmscore'] for r in records]
    for r in records:
            yield r
            
def format_record(result):
    """Makes string out of parsed result dict"""
    return "%s\t%s\t%s\t%s\t%s\t%s\t%s\n"%(
            result['query'], 
            result['hit'], 
            result['rmsd'], 
            result['tmscore'], 
            result['len_seq_1'], 
            result['len_seq_2'], 
            result['len_aligned'],
               )

    

if __name__ == '__main__':
    """
    Goes through several result sets of LaJolla and summarizes them.
    """
    summary = []
    details = []
    for inpath in INPUT_PATHS:
        summary.append('\nDIRECTORY: %s\n'%inpath)
        summary.append('STRUC\tN\t<RMS>\t<TM>\t<Lalign>\n')
        avg_n, aa_rms,  aa_tm, aa_alen = [], [], [], []
        for d in get_struct_dirs(inpath):
            rmsds = []
            tmscores = []
            alen = []
            for i, record in enumerate(get_result_files(inpath + d+'/')):
                rmsds.append(float(record['rmsd']))
                tmscores.append(float(record['tmscore']))
                alen.append(int(record['len_aligned']))
                if inpath == 'nr95_chains_10_0.2/':
                    details.append("%s\t%s\t%s\n"%(record['rmsd'], record['tmscore'], record['len_aligned']))
            n = len(rmsds)
            if n>0:
                avg_rmsd = sum(rmsds)/n
                avg_tm = sum(tmscores)/n
                avg_alen = sum(alen)*1.0/n
                summary.append("%s\t%i\t%4.2f\t%4.2f\t%5.2f\n"%(d[:6], n, avg_rmsd, avg_tm, avg_alen))
                avg_n.append(n)
                aa_rms.append(avg_rmsd)
                aa_tm.append(avg_tm)
                aa_alen.append(avg_alen)
            else:
                summary.append("%s\t0\t-\t-\t-\n"%(d[:6]))
            
        summary.append('----------------------------------------------------\n')
        nn = len(avg_n)*1.0
        if nn>0:
            summary.append("TOTAL\t%i\t%5.2f\t%4.2f\t%4.2f\t%5.2f\n"%(nn, sum(avg_n)/nn, sum(aa_rms)/nn, sum(aa_tm)/nn, sum(aa_alen)/nn))
            print inpath
            print summary[-1], 
    open('summary.txt', 'w').writelines(summary)
    open('details.txt', 'w').writelines(details)
    
            
    
