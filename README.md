# ippex
IPPEX Plasma Fusion Reactor Simulation (JS).

## How to get started:
### Install Node.JS runtime:
* [download it at nodejs.org](http://nodejs.org).

### Install Bower (a package manager for the web):
* `$npm install -g bower`

### Now you can simply use Bower to download all the dependencies:
(Make sure you're in your main local directory)
* `$bower install katex`
* `$bower install jquery`
* `$bower install pixi`
* `$bower install dat.gui`
* `$bower install stat.js`

### You should now have all the libraries to run the simulations.

### Pull all new updates from the rep to your local folder:
* `git status`
* `git pull origin master`

### **[NOTE!]** If your directory is not on a localhost server, you'll have to start up a quick local http-server to bypass crossOrigin javascript security (either that, or start up Chrome or Safari in unsafe mode):

### You can just install the `http-server` module by
* `$sudo npm i -g http-server`

### then start it by simply
* `hs .`
## Useful git commands:

* `git clone <url> <directory name>`
* `git status` Lists changes, including "staged" changes
* `git add <file>` Stages a file
* `git mv <file>` Rename a file
* `git rm`
* `git commit -m "did stuff"` Save your changes
* `git push origin master` Push your latest commit to the origin (github)
* `git pull origin master` Get the latest changes from github
* `git stash` Hide your changes for a moment, resets the working directory
* `git stash pop` Get your changes back
* `git remote add <name> <url>` Add a new remote repository
* `rm -rf <git directory>` Delete a git working directory without complaints
