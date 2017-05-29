import csv, sh, sys, git, os

#TODO: switch branch as well as reset to SHA.

with open('submissions.csv', 'r') as csv_file:
        
    reader = csv.reader(csv_file)
        
    for row in reader:
            
        for item in row:

            if "@github.kcl.ac.uk" in item:
                
                try:
                    
                    repo_name = item[item.find("/", 10) + 1:-(len(item) - item.find("/", item.find("/", 10) + 1))]
                    
                    path = "submitted_repos/" + row[0] + "/" + repo_name + "/";
                    
                    print "Cloning: " + row[0] + " " + item
                    sh.git.clone(item, path)
                    
                    repo = git.Repo(path)
                    repo.head.reset(row[11], index=True, working_tree=True)
                    
                    note_file = open("submitted_repos/" + row[0] + "/" + "student-submission-details.txt", 'w+')
                    note_file.write(str(row))
                    note_file.flush();
                    
                except Exception as e:
                    
                    if (not "fatal: destination path" in str(e)):
                        log_file = open('log.txt', 'a+')
                        log_file.write(str(e) + ": " + str(row) + "\n")
                        log_file.flush();
            
        