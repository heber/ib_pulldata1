#This script takes as argument the path to a folder of files with entries
#to be imported into tsdb, and imports those files.
#Afterwards, it deletes the files already imported.

#argv[1] - folder path

import sys, commands, string, os

def join(rootPath, subfolders, filePath):
    path=str(rootPath)+"/"+str(filePath)
    return path

if __name__=="__main__":
    folderPath=sys.argv[1]

    listOfFiles=[]
    for root, subFolders, files in os.walk(folderPath):
        for file in files:
            absolutePath=join(str(root), str(subFolders), str(file))
            listOfFiles.append(absolutePath)
    
    print len(listOfFiles)
    for filePath in listOfFiles:
        if "entries" in filePath:
            co=commands.getoutput("/home/jadiel/Downloads/opentsdb/build/tsdb import "+str(filePath))
            co=commands.getoutput("rm "+str(filePath))

            
