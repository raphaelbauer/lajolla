from pylab import *
import os
from timeit import Timer


numberoftests = 5

tmpdir = "/media/truecrypt1/tmp/"

locationlajolla = "/home/ra/workspace_work/lajolla2-royal-flush/target/lajolla-2.0-RC1-jar-with-dependencies.jar"
  
wheretostoreresults = ""

resultdir50 = "/home/ra/benchmarking/datasets/cath_50/"
resultdir75 = "/home/ra/benchmarking/datasets/cath_75/"
resultdir100 = "/home/ra/benchmarking/datasets/cath_100/"

allresultdirs = [resultdir50, resultdir75, resultdir100]



def run_lajolla(resultset, ngramsize, outputdir):
  
    command = "java -cp " + locationlajolla + " PRO -t " + resultset + " -zn " + ngramsize + " -o " + outputdir +" > /dev/null"
    
    os.system(command)
    
    
    
##### only run in homedir of CE => because of scratch and pathes and so on...
def run_ce(resultset):

    for templatefile in os.listdir(resultset):
        
         for targetfile in os.listdir(resultset):       
             command = "./ce/ce - " + resultset + templatefile + " - " + resultset + targetfile + " - scratch > /dev/null" 
             #command = locationtmalign + " PRO -t " + resultset + " -zn " + ngramsize + " > /dev/null"
    
             os.system(command)
    
def run_tmalign(resultset):
    
    locationtm = "/home/ra/applications/TMalign_32"
    
    for templatefile in os.listdir(resultset):
        
         for targetfile in os.listdir(resultset):       
    
             command = locationtm + " " + resultset + templatefile + " " + resultset + targetfile + " > /dev/null"
                
             #command = locationtmalign + " PRO -t " + resultset + " -zn " + ngramsize + " > /dev/null"
    
             os.system(command)
        


if __name__=="__main__":
    
    
    results = ""
    
        ## benchmark lajolla 
    for ngram in ['30','25','20','15','10']:
        for thisresultdir in allresultdirs:
            t = Timer("run_lajolla(\"" + thisresultdir + "\", \""+ ngram +"\", \""+ tmpdir +"\")", "from __main__ import run_lajolla")
            
            results += "LAJOLLA RESULT OF " + ngram + " and on dir: " + thisresultdir + "\n"
            results += str(t.timeit(numberoftests)) + "\n"
            
            open(wheretostoreresults + "lajolla_results.txt", "w").writelines(results)

    results = ""

    
    
        ### benchmark ce
    for thisresultdir in allresultdirs:
        t = Timer("run_ce(\"" + thisresultdir + "\")", "from __main__ import run_ce")
            
        results += "CE RESULT OF on dir: " + thisresultdir + "\n"
        results += str(t.timeit(numberoftests)) + "\n"
            
        open(wheretostoreresults + "ce_results.txt", "w").writelines(results)
  
    results = ""     
            
    
    ### benchmark tm-align
    for thisresultdir in allresultdirs:
        t = Timer("run_tmalign(\"" + thisresultdir + "\")", "from __main__ import run_tmalign")
            
        results += "TM ALIGN RESULT OF on dir: " + thisresultdir + "\n"
        results += str(t.timeit(numberoftests))  + "\n"
            
            
        open(wheretostoreresults + "tmalign_results.txt", "w").writelines(results)


       
    results = ""     
            
    


#    for templatefile in os.listdir(resultdir):
    
        #counter_number_of_protein_classes_to_be_processed = counter_number_of_protein_classes_to_be_processed + 1
        #thisresultdir = resultdir + templatefile + "/"
        #os.makedirs(thisresultdir)   
 
#        for targetfile in os.listdir(cathdir):        
            #print templatefile + " and " + targetfile
       
#            command =  "CE " + cathdir + templatefile + " " + cathdir + targetfile + " > /dev/null"
#            os.system(command)




