import os, shutil, sh, re, random, string, subprocess, errno, mmap

from encrypt import encrypt

def copy_anything(src, dst):
    try:
        if (os.path.isdir(dst)):
            shutil.rmtree(dst)
        shutil.copytree(src, dst)
    except OSError as exc: # python >2.5
        if exc.errno == errno.ENOTDIR:
            shutil.copy(src, dst)
        else: raise
        
def commit_push_all(repo_owners, message):
    
    if ( len(repo_owners) > 3 ):
    
        if ( raw_input('Commit and push to all repos? (Y/N)') != "Y" ):
            
            return;
   
    for owner in repo_owners:
        
        repo = sh.git.bake(_cwd="student-data/repos/" + owner)
        
        if ( "nothing to commit" not in repo.status() ):
            
            commit_push(owner, "pracoursework", "", "Coursework Resources", message);
            
def clone(repo_owner, repo_name):
    
    if (not os.path.isdir("student-data/repos/" + repo_owner)):
        
        os.mkdir("student-data/repos/" + repo_owner + "/")
        
        print "--> cloning " + repo_owner;
        
        sh.git.clone("https://[token]@github.kcl.ac.uk/" + repo_owner + "/" + repo_name + ".git", "student-data/repos/" + repo_owner + "/")
        
def pull(repo_owner):
    
    repo = sh.git.bake(_cwd="student-data/repos/" + repo_owner)
    
    print "--> pulling " + repo_owner;
    try:
        #repo.pull('all');
        repo.pull();
    except:
        print "Pull exception: " + str(repo.status());
        
def fetch(repo_owner):
    
    repo = sh.git.bake(_cwd="student-data/repos/" + repo_owner)
    
    print "--> fetching " + repo_owner;
    try:
        repo.fetch('--all');
    except:
        print "Fetch exception: " + str(repo.status());
    
      
def move_add(repo_owner, repo_name, relative, file, message):
    
    repo = sh.git.bake(_cwd="student-data/repos/" + repo_owner)
    
    pull(repo_owner);
        
    copy_anything(relative + file, "student-data/repos/" + repo_owner + "/" + file)
        
    repo.add(file)
    
def move_add_ONCE(repo_owner, repo_name, relative, file, message):
    
    if ( os.path.isfile("student-data/repos/" + repo_owner + "/" + file) or os.path.isdir("student-data/repos/" + repo_owner + "/" + file) ):
        
        return
        
    move_add(repo_owner, repo_name, relative, file, message);
    
def commit_push(repo_owner, repo_name, message):
    
    repo = sh.git.bake(_cwd="student-data/repos/" + repo_owner)
    
    # pull(repo_owner); this may cause an error
        
    repo.commit(m=message)
    
    print "Committed: " + message;
    
    print "Pushing --> " + repo_owner + "@" + repo_name;
    
    repo.push();
    
def delete_branch(repo_owner, branch_name):
    
    repo = sh.git.bake(_cwd="student-data/repos/" + repo_owner)
     
    repo.checkout('master')
    
    repo.branch("-d", branch_name)

def create_branch(repo_owner, branch_name):
    
    repo = sh.git.bake(_cwd="student-data/repos/" + repo_owner)
    
    repo.checkout('master')
    
    repo.branch(branch_name)
    
    repo.push("--set-upstream", "origin", branch_name)
    
    print "Pushing branch " + branch_name + " --> " + repo_owner;
    
def switch_branch(repo_owner, branch_name):
    
    repo = sh.git.bake(_cwd="student-data/repos/" + repo_owner)
    
    repo.checkout(branch_name)
    
    print "Switching to branch --> " + branch_name;
    
def add_ripley(repo_owner):
    
    repo = sh.git.bake(_cwd="student-data/repos/" + repo_owner)
    
    move_add(repo_owner, "ppa-major-coursework", "../library/", 'ripley.jar', '');
    
def add_ripley_docs(repo_owner):
    
    repo = sh.git.bake(_cwd="student-data/repos/" + repo_owner)
    
    move_add(repo_owner, "ppa-major-coursework", "../library/", 'ripley-docs.jar', '')
    
def add_auth_info(repo_owner):
    
    f = open('auth.txt','w')
    f.write("This assignment uses a closed API. To access this API, you will require the following two keys:\n")
    f.write("Your group's unique Ripley API private key: " + encrypt("[KeyA]", repo_owner) + "\n")
    f.write("Your group's unique Ripley API public key: " + encrypt("[KeyB]", repo_owner) + "\n")
    f.write("It is essential that these keys are kept confidential, and not shared with anyone outside of this group. These keys are unique, and if they are misplaced or revealed to students not in your group, they cannot be replaced." + "\n")
    f.close()
    
    move_add(repo_owner, "ppa-major-coursework", "", 'auth.txt', '')

def commit_push_ONCE(repo_owner, repo_name, relative, file, message):
    
    if ( os.path.isfile("student-data/repos/" + repo_owner + "/" + file) or os.path.isdir("student-data/repos/" + repo_owner + "/" + file) ):
        
        return
        
    commit_push(repo_owner, repo_name, relative, file, message);
    
def move_add_commit_push(repo_owner, repo_name, relative, file, message):
    
   move_add(repo_owner, repo_name, relative, file, message)
   
   commit_push(repo_owner, repo_name, relative, file, message)
    
def move_add_commit_push_ONCE(repo_owner, repo_name, relative, file, message):
        
    if ( os.path.isfile("student-data/repos/" + repo_owner + "/" + file) or os.path.isdir("student-data/repos/" + repo_owner + "/" + file) ):
        
        return
    
    move_add_commit_push(repo_owner, repo_name, relative, file, message); 
                
def clone_all(repo_owners):

    for owner in repo_owners:
              
        if ( not os.path.isdir("student-data/repos/" + owner + "/") ):
            
            clone(owner, "ppa-major-coursework");
            
def pull_all(repos_list):

    for owner in repos_list:
            
        pull(owner);
        
def fetch_all(repos_list):

    for owner in repos_list:
            
        fetch(owner);
        
def switch_all(repos_list):
    
    for owner in repos_list:
            
        switch_branch(owner, "master")

def create_branch_all(repos_list, branch_name):

    for owner in repos_list:
        
        create_branch(owner, branch_name);
        
def switch_branch_all(repos_list, branch_name):

    for owner in repos_list:
        
        switch_branch(owner, branch_name);
        
def add_ripley_all(repos_list):

    for owner in repos_list:
        
        add_ripley(owner);
        
def add_ripley_docs_all(repos_list):

    for owner in repos_list:
            
        add_ripley_docs(owner);
        
def add_auth_info_all(repos_list):

    for owner in repos_list:
            
        add_auth_info(owner)
        
def commit_push_all(repos_list):

    for owner in repos_list:
            
        commit_push(owner, "ppa-major-coursework", "Added your assessment materials.");

def send_materials_one_2017(repo_owner):
    
    clone(repo_owner, "ppa-major-coursework");
    
    create_branch(repo_owner, "assessment_materials");
    
    switch_branch(repo_owner, "assessment_materials");
    
    add_ripley(repo_owner);
    
    add_ripley_docs(repo_owner);
    
    add_auth_info(repo_owner);
    
    commit_push(owner, "ppa-major-coursework", "Added your assessment materials.");

if __name__ == '__main__':
    
    #repos_list = []
    
    #with open("all_student_repos.txt") as repo_owners:
        
        #for owner in repo_owners:
            
            #if ( owner != "" ):
                
                #repos_list.append(owner.replace("\n", ""))  
            
    send_materials_one_2017("[single k number]");